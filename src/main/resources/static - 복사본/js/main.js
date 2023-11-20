var mapContainer = document.getElementById('map'),
    mapOption = {
        center: new kakao.maps.LatLng(37.504937827895866, 126.9576790776909),
        level: 2
    };
// 준원
var map = new kakao.maps.Map(mapContainer, mapOption);
var currentInfowindow = null;
var markers = [];
// var overlays = [];

// 지도에 마커와 인포윈도우를 표시
function displayMarker(locPosition, message) {

    var marker = new kakao.maps.Marker({
        map: map,
        position: locPosition
    });

    var iwContent = message, // 인포윈도우에 표시할 내용
        iwRemoveable = true;

    // 인포윈도우를 생성
    var infowindow = new kakao.maps.InfoWindow({
        content: iwContent,
        removable: iwRemoveable
    });

    // 인포윈도우를 마커위에 표시
    infowindow.open(map, marker);

    // 지도 중심좌표를 접속위치로 변경
    map.setCenter(locPosition);
}

function clearMarkers() {
    for (let i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
    // closeCurrentOverlay(); // 이거 없애면 일단 오버레이는 안없어짐
}




// function convertToPlaceFormat(dbData) {
//     return dbData.map(entry => {
//         return {
//             name: entry.name,
//             lat: parseFloat(entry.latitude),
//             lng: parseFloat(entry.longitude)
//         };
//     });
// }

//위 주석 처리는 프론트에서 쓰던거, 아래 부분은 백엔드 코드
function convertToPlaceFormat(dbData) {
    return dbData.map(place => {
	return{
        id: place.id,
        name: place.name,
        address: place.address,
        lat: parseFloat(place.latitude),
        lng: parseFloat(place.longitude),
        opentime: place.opentime,
        averageOfStarRating: place.star_average,
        numberOfStarRating: place.star_count,
        numberOfComments: place.comment_count 
 	 	};
	});
}



const mockData = { // 이건 그냥 내가 보려고 넣은 가상 데이터, 학교 앞 중앙대점 누르면 볼 수 있음
    id: 'mock1', 
    name: 'Starbucks Coffee Shop',
    address: '123 Coffee Lane, Beanstown, CA',
    starRating: 'Capricorn',
    comments: 'Great ambiance and Wi-Fi.',
    numberOfStarRatingReviews: 42,
    lat: 37.504937827895866,
    lng: 126.9576790776909
};

function handleMarkerClick(marker) {
    const useBackend = true; // 백엔드 쓸때는 true로 바꿔
	var place = marker.data;
	console.log('Clicked Marker ID:', place.id);
    if (useBackend) {
        fetch(`/place/detail?id=${place.id}`)
            .then(response => response.json())
            .then(data => {
			const convertedData = {
			id: data.id,
            name: data.name,
            address: data.address,
            lat: parseFloat(data.latitude),
            lng: parseFloat(data.longitude),
            opentime: data.opentime,
            averageOfStarRating: data.star_average,
		    numberOfStarRating:  data.star_count,
    		numberOfComments:  data.comment_count
			};
                createAndShowOverlay(convertedData);
            })
            .catch(error => {
                console.error('Error fetching place details:', error);
            });
    } else {
        createAndShowOverlay(mockData);
    }
}


function createAndShowOverlay(placeData) {
    const overlay = createPlaceOverlay(placeData, map);
    if (window.currentOverlay) {
        window.currentOverlay.setMap(null);
    }
    overlay.setMap(map);
    window.currentOverlay = overlay;
}


function markPlaces(places) {
    clearMarkers();

    places.forEach(function (place) {
        var markerPosition = new kakao.maps.LatLng(place.lat, place.lng);
        var marker = new kakao.maps.Marker({
            position: markerPosition,
            title: place.name
        });
        marker.setMap(map);
        markers.push(marker);
        marker.data = place;
        kakao.maps.event.addListener(marker, 'click', function () {
            handleMarkerClick(marker);
        });
    });
}

var initialSearchDone = false;

function searchNearby(keyword, location, page = 1) {
    fetch(`/place/search?keyword=${keyword}&lat=${location.getLat()}&lng=${location.getLng()}`)
        .then(response => response.json())
        .then(data => {
            const convertedData = convertToPlaceFormat(data);
            markPlaces(convertedData);
            if (!initialSearchDone && convertedData.length > 0) {
                map.panTo(new kakao.maps.LatLng(convertedData[0].lat, convertedData[0].lng));
                initialSearchDone = true; // Set the flag so the map doesn't re-center on subsequent data fetches
            }
        })
        .catch(error => {
            console.error("Error fetching places:", error);
        });
}

// Call this function when you want to perform a new search and reset the initial search flag
function performNewSearch(keyword) {
    var center = map.getCenter();
    clearMarkers();
    initialSearchDone = false; // Reset the flag so the map will center on new searches
    searchNearby(keyword, center);
}


//백엔드에서 정보 가져오기
function fetchPlacesFromBackend(lat, lng) {


    var center = map.getCenter();
    fetch(`/place/show`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
        disabled_person: document.getElementById('disabled_person').checked,
        changing_table_man: document.getElementById('changing_table_man').checked,
        changing_table_woman: document.getElementById('changing_table_woman').checked,
        emergency_bell_man: document.getElementById('emergency_bell_man').checked,
        emergency_bell_woman: document.getElementById('emergency_bell_woman').checked,
        emergency_bell_disabled: document.getElementById('emergency_bell_disabled').checked,
                lat: center.getLat(),
                lng: center.getLng()
        })
    })
    .then(response => response.json())
    .then(data => {
        // convertToPlaceFormat 함수를 이용해 백엔드로부터 받은 데이터를 마커로 변환
        const convertedData = convertToPlaceFormat(data);
        markPlaces(convertedData);
    })
    .catch(error => {
        console.error('Error fetching filtered places:', error);
    });


}

function updateCenterAndSearch(keyword) {
    var center = map.getCenter();
    clearMarkers();
    fetchPlacesFromBackend(center.getLat(), center.getLng());
    // 스타벅스 부분, 프론트 개발시 주석 해제
    // searchNearby(keyword || 'StarBucks', center); 
    // searchNearby(keyword , center); 
}


document.getElementById('search-button').addEventListener('click', function () {
    var keyword = document.getElementById('keyword').value;
    if (keyword.trim() !== '') {
        performNewSearch(keyword);
    } else {
        alert('Please enter a keyword to search.');
    }
});

function fetchAndUpdatePlaces() {
    var center = map.getCenter();
    clearMarkers();
    // 스타벅스, 프론트 개발시 주석 해제
    //searchNearby('Starbucks', center); 
}
      kakao.maps.event.addListener(map, 'dragend', function() {
            updateCenterAndSearch();
        });

// kakao.maps.event.addListener(map, 'dragend', function () {
//     var center = map.getCenter();
//     clearMarkers();
//     fetchPlacesFromBackend(center.getLat(), center.getLng());
// });

updateCenterAndSearch();