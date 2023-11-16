
// function createOverlayContent(place) {
//     return `
//         <div class="custom-overlay">
//             <div class="overlay-inner">
//                 <h3>${place.name}</h3>
//                 <p>Address: ${place.address}</p>
//                 <p>StarRating: ${place.starRating}</p>
//                 <p>Number of StarRating Reviews: ${place.numberOfStarRatingReviews}</p>
//                 <p>Comments: ${place.comments}</p>
//                 <a href="view_details.html?id=${place.id}" target="_blank">More details</a>
//                 <button class="overlay-close-btn" onclick="window.closeCurrentOverlay()">Close</button>
//             </div>
//         </div>
//     `;
// }


/// 위에 주석 처리한건 프론트에서 쓰던 코드 11-16, 21시 30분 

function createOverlayContent(place) {
    return `
        <div class="custom-overlay">
            <div class="overlay-inner">
                <h3>${place.name}</h3>
                <p>Address: ${place.address}</p>
                <p>Average of StarRating: ${place.averageOfStarRating}</p>
                <p>Number of StarRating: ${place.numberOfStarRating}</p>
                <p>Number of Comments: ${place.numberOfComments}</p>
                <a href="/viewdetails?id=${place.id}" target="_blank">More details</a>
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

//아래는 프론트에서 쓰던 코드 11-16, 21시 30분
// function createAndShowOverlay(place, map) {
//     var overlayContent = createOverlayContent(place);
//     var overlay = new kakao.maps.CustomOverlay({
//         content: overlayContent,
//         map: map,
//         position: new kakao.maps.LatLng(place.lat, place.lng),
//         xAnchor: 0.5,
//         yAnchor: 0.5
//     });

//     window.currentOverlay = overlay;
// }

// window.closeCurrentOverlay = function() {
//     if (window.currentOverlay) {
//         window.currentOverlay.setMap(null);
//         window.currentOverlay = null;
//     }
// };
