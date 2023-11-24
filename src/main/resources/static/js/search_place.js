// // Event listener for the button
// document.getElementById('search-button').addEventListener('click', function () {
//     var keyword = document.getElementById('keyword').value;
//     if (keyword!=null&&keyword.trim() !== '') {
//         var center = map.getCenter();
//         clearMarkers();
//         searchNearby(keyword, center);
//     } else {
//         alert('Please enter a keyword to search.');
//     }
// });


// //11-21, 04:04 엔터 기능 추가
// document.getElementById('keyword').addEventListener('keypress', function (event) {
//     if (event.key === 'Enter') {
//         var keyword = document.getElementById('keyword').value;
//         if (keyword.trim() !== '') {
//             performNewSearch(keyword);
//         } else {
//             alert('Please enter a keyword to search.');
//         }
//     }
// });

function performNewSearch(keyword) {
    var center = map.getCenter();
    clearMarkers();
    searchNearby(keyword, center);
    saveCurrentMapCenter(); // 검색 후 중심 위치 저장
}

// 검색 버튼 클릭 이벤트
document.getElementById('search-button').addEventListener('click', function () {
    var keyword = document.getElementById('keyword').value;
    if (keyword!=null && keyword.trim() !== '') {
        performNewSearch(keyword);
    } else {
        alert('Please enter a keyword to search.');
    }
});

// 키보드 엔터키 이벤트
document.getElementById('keyword').addEventListener('keypress', function (event) {
    if (event.key === 'Enter') {
        var keyword = document.getElementById('keyword').value;
        if (keyword.trim() !== '') {
            performNewSearch(keyword);
        } else {
            alert('Please enter a keyword to search.');
        }
    }
});

// 지도 중심 위치를 세션 스토리지에 저장
function saveCurrentMapCenter() {
    var center = map.getCenter();
    sessionStorage.setItem('lastViewedPlace', JSON.stringify({lat: center.getLat(), lng: center.getLng()}));
}
