// Event listener for the button
document.getElementById('search-button').addEventListener('click', function () {
    var keyword = document.getElementById('keyword').value;
    if (keyword!=null&&keyword.trim() !== '') {
        var center = map.getCenter();
        clearMarkers();
        searchNearby(keyword, center);
    } else {
        alert('Please enter a keyword to search.');
    }
});


//11-21, 04:04 엔터 기능 추가
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