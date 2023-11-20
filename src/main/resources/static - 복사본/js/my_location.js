if (navigator.geolocation) {

    // GeoLocation을 이용해서 접속 위치 얻어오기
    navigator.geolocation.getCurrentPosition(function (position) {

        var lat = position.coords.latitude, 
            lon = position.coords.longitude; 

        var locPosition = new kakao.maps.LatLng(lat, lon),
            message = '<div style="padding:5px;">여기에 계신가요?!</div>'; 

        displayMarker(locPosition, message);

    });

} else {

    var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),
        message = 'geolocation을 사용할수 없어요..'

    displayMarker(locPosition, message);
}


function moveToCurrentLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var lat = position.coords.latitude,
                lng = position.coords.longitude;
            var newCenter = new kakao.maps.LatLng(lat, lng);
            map.setCenter(newCenter);
        }, function (error) {
            console.error("Error: " + error.message);
        });
    } else {
        console.error("Geolocation is not supported by this browser.");
    }
}

// Recenter button event listener
document.getElementById('recenter-map').addEventListener('click', function () {
    moveToCurrentLocation();
});

moveToCurrentLocation();

