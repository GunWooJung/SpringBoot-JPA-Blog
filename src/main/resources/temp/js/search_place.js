// Event listener for the button
document.getElementById('search-button').addEventListener('click', function () {
    var keyword = document.getElementById('keyword').value;
    if (keyword.trim() !== '') {
        var center = map.getCenter();
        clearMarkers();
        searchNearby(keyword, center);
    } else {
        alert('Please enter a keyword to search.');
    }
});

