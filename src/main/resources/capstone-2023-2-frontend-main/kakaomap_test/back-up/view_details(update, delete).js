document.addEventListener('DOMContentLoaded', function () {
    const urlParams = new URLSearchParams(window.location.search);
    const placeId = urlParams.get('id');
    const amendLink = document.getElementById('amendLink');
    const commentsContainer = document.getElementById('reviews');

    document.getElementById('backButton').addEventListener('click', function () {
        window.location.href = `/`; // 현재 창에서 index.html로 이동
    });

    amendLink.addEventListener('click', function (event) {
        // 기본 동작(링크 이동) 방지
        event.preventDefault();
        // window.open(`/amend_information?id=${placeId}`, '_blank');
        window.location.href = `/amend_information?id=${placeId}`;

    });


    //// 이 부분 추가함 11-19, 19시 38분
    function initMap(place) {
        var mapContainer = document.getElementById('map');
        var mapOption = {
            center: new kakao.maps.LatLng(place.latitude, place.longitude),
            level: 3,
            draggable: false // 드래그 기능 비활성화
        };

        var map = new kakao.maps.Map(mapContainer, mapOption);

        // 지도 중심에 마커 표시
        new kakao.maps.Marker({
            map: map,
            position: map.getCenter()
        });
    }

    //place details 준원
    fetch(`/place/detail?id=${placeId}`)
        .then(response => response.json())
        .then(place => {
            document.getElementById('placeName').textContent = place.name;
            document.getElementById('starRating').textContent = place.star_average;
            document.getElementById('address').textContent = place.address;
            //document.getElementById('toiletInfo').textContent = place.toiletInfo;


            initMap(place);

        })
        .catch(error => {
            console.error('Error:', error);
        });


    // 댓글 수정 UI 생성 및 수정 요청 처리
    function createEditInterface(comment) {
        const editContainer = document.createElement('div');
        editContainer.classList.add('comment-edit-container');

        const editInput = document.createElement('input');
        editInput.type = 'text';
        editInput.value = comment.content;
        editInput.classList.add('comment-edit-input');

        const saveButton = document.createElement('button');
        saveButton.textContent = '저장';
        saveButton.classList.add('comment-save-button');

        const cancelButton = document.createElement('button');
        cancelButton.textContent = '취소';
        cancelButton.classList.add('comment-cancel-button');

        saveButton.addEventListener('click', function () {
            updateComment(comment.id, editInput.value, comment, editContainer);
        });

        cancelButton.addEventListener('click', function () {
            commentsContainer.replaceChild(createCommentElement(comment), editContainer);
        });

        editContainer.appendChild(editInput);
        editContainer.appendChild(saveButton);
        editContainer.appendChild(cancelButton);

        return editContainer;
    }

    // 댓글 수정 요청 처리
    function updateComment(commentId, newContent, comment, editContainer) {
        fetch(`/comment/update?id=${commentId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ content: newContent })
        })
            .then(response => response.json())
            .then(data => {
                const updatedComment = createCommentElement({
                    ...comment,
                    content: newContent
                });
                commentsContainer.replaceChild(updatedComment, editContainer);
            })
            .catch(error => {
                console.error('Error:', error);
                alert('댓글 수정 중 오류가 발생했습니다.');
            });
    }

    // 댓글 삭제 요청 처리
    function deleteComment(commentId, commentElement) {
        if (!confirm('이 댓글을 삭제하시겠습니까?')) {
            return;
        }

        fetch(`/comment/delete?id=${commentId}`, {
            method: 'GET'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                commentsContainer.removeChild(commentElement);
            })
            .catch(error => {
                console.error('Error:', error);
                alert('댓글 삭제 중 오류가 발생했습니다.');
            });
    }

    // 댓글 DOM 요소 생성
    function createCommentElement(comment) {
        const commentElement = document.createElement('div');
        commentElement.classList.add('comment-container');
        commentElement.id = `comment-${comment.id}`;

        const commentText = document.createElement('span');
        commentText.textContent = `${comment.username}: ${comment.content}`;

        const editButton = document.createElement('button');
        editButton.textContent = '수정';
        const deleteButton = document.createElement('button');
        deleteButton.textContent = '삭제';

        editButton.addEventListener('click', function () {
            const editInterface = createEditInterface(comment);
            commentsContainer.replaceChild(editInterface, commentElement);
        });

        deleteButton.addEventListener('click', function () {
            deleteComment(comment.id, commentElement);
        });

        commentElement.appendChild(commentText);
        commentElement.appendChild(editButton);
        commentElement.appendChild(deleteButton);

        return commentElement;
    }

    console.log('Username:', username);
    console.log('Password:', password);
    console.log('Review:', reviewText);




    // 댓글 불러오기 및 DOM에 추가
    fetch(`/comment/show?id=${placeId}`)
        .then(response => response.json())
        .then(comments => {
            comments.forEach(comment => {
                commentsContainer.appendChild(createCommentElement(comment));
            });
        })
        .catch(error => {
            console.error('Error:', error);
        });

    // 리뷰 제출 버튼 클릭 이벤트 처리
    document.getElementById('submitReview').addEventListener('click', function () {
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const reviewText = document.getElementById('reviewText').value;

        if (!username || !password || !reviewText) {
            alert('모든 필드를 채워주세요.');
            return;
        }

        const commentData = { username, password, placeId, content: reviewText };

        fetch('/comment/enroll', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(commentData)
        })
            .then(response => response.json())
            .then(newComment => {
                commentsContainer.appendChild(createCommentElement(newComment));
                document.getElementById('username').value = '';
                document.getElementById('password').value = '';
                document.getElementById('reviewText').value = '';
            })
            .catch(error => {
                console.error('Error:', error);
                alert('댓글 등록 중 오류가 발생했습니다.');
            });
    });


    // 별점 제출
    document.getElementById('submitRating').addEventListener('click', function () {
        const selectedRating = document.querySelector('input[name="rating"]:checked').value;
        const ratingData = {
            placeId: placeId,
            rating: selectedRating
        };

        console.log('Selected Star Rating:', selectedRating);

        // 백엔드 경로 설정 필요
        fetch(`/starrating/enroll`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(ratingData)

        })
            .then(() => {
                // alert으로 별점 제출 알림
                alert(`제출된 별점: ${selectedRating}점`);
                location.reload();
            })
            .catch(error => {
                console.error('Error:', error);
                alert('별점 제출 중 오류가 발생했습니다.');
            });
    });
});