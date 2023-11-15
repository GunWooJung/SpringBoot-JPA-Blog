
function createOverlayContent(place) {
    return `
        <div class="custom-overlay">
            <div class="overlay-inner">
                <h3>${place.name}</h3>
                <p>Address: ${place.address}</p>
                <p>Average of StarRating: ${place.averageOfStarRating}</p>
                <p>Number of StarRating: ${place.numberOfStarRating}</p>
                <p>Number of Comments: ${place.numberOfComments}</p>
                <a href="view_details.html?id=${place.id}" target="_blank">More details</a>
                <button class="overlay-close-btn" onclick="closeCurrentOverlay()">Close</button>
            </div>
        </div>
    `;
}

function createPlaceOverlay(place, map) {
    var overlayContent = createOverlayContent(place);
    var overlay = new kakao.maps.CustomOverlay({
        content: overlayContent,
        map: map,
        position: new kakao.maps.LatLng(place.lat, place.lng),
        xAnchor: 0.3,
        yAnchor: 0.91
    });
    return overlay;
}

// This function needs to be in the global scope
function closeCurrentOverlay() {
    if (window.currentOverlay) {
        window.currentOverlay.setMap(null);
        window.currentOverlay = null;
    }
}

// Expose the function to the global scope
window.closeCurrentOverlay = closeCurrentOverlay;
