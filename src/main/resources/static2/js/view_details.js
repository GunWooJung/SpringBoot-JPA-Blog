document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const placeId = urlParams.get('id');
    const amendLink = document.getElementById('amendLink');

    // 모달
    // function loadAmendInformationModal() {
    //     fetch(`/amend_information?id=${placeId}`)
    //         .then(response => response.text())
    //         .then(html => {
    //             document.getElementById('amendModalContent').innerHTML = html;
    //             document.getElementById('amendModal').style.display = 'block';
    
    //             // Close button event listener
    //             document.querySelector('#amendModal .close').addEventListener('click', function() {
    //                 document.getElementById('amendModal').style.display = 'none';
    //             });
            
    //         })
    //         .catch(error => {
    //             console.error('Error loading amend information:', error);
    //         });
    //     }
    
    
    amendLink.addEventListener('click', function(event) {
        // 기본 동작(링크 이동) 방지
        event.preventDefault();
        // 예: 새로운 페이지 열기 << 모달 추가하면서 이 부분 주석 처리함
        window.open(`/amend_information?id=${placeId}`, '_blank');

        // loadAmendInformationModal();
    });

    //place details 준원
    fetch(`/place/detail?id=${placeId}`)
        .then(response => response.json())
        .then(place => {
            document.getElementById('placeName').textContent = place.name;
            document.getElementById('starRating').textContent = place.star_average;
            document.getElementById('address').textContent = place.address;
            //document.getElementById('toiletInfo').textContent = place.toiletInfo;
        })
        .catch(error => {
            console.error('Error:', error);
        });

    // 리뷰 받아오기
    fetch(`/comment/show?id=${placeId}`)
        .then(response => response.json())
        .then(comments => {
            const commentsContainer = document.getElementById('reviews');
            comments.forEach(comment => {
                const commentElement = document.createElement('p');
                commentElement.textContent = `${comment.username} : ${comment.content}`; // Assuming 'content' is a field in your comment object
                commentsContainer.appendChild(commentElement);
            });
        })
        .catch(error => {
            console.error('Error:', error);
        });

    // 제출 버튼 클릭
    document.getElementById('submitReview').addEventListener('click', function() {
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const reviewText = document.getElementById('reviewText').value;
        const commentData = {
            username: username,
            password: password,
            placeId: placeId,
            content: reviewText
        };

        console.log('Username:', username);
        console.log('Password:', password);
        console.log('Review:', reviewText);

        // 입력한 리뷰 보내기
        fetch('/comment/enroll', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(commentData)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(() => {
            //리뷰 창 업데이트?
            const commentsContainer = document.getElementById('reviews');
            const commentElement = document.createElement('p');
            commentElement.textContent = `Username: ${username}, Comment: ${reviewText}`;
            commentsContainer.appendChild(commentElement);
        })
        .catch(error => {
            console.error('Error:', error);
        });

        // 리뷰창 비우기
        document.getElementById('username').value = '';
        document.getElementById('password').value = '';
        document.getElementById('reviewText').value = '';

        location.reload(); 

        window.onclick = function(event) {
            let modal = document.getElementById('amendModal');
            if (event.target == modal) {
                modal.style.display = 'none';
            }
        };

        // 이건 필터창 모달 부분인데 정보 수정 제안에서 바깥에 눌러서 안닫히면 
        // 아래 부분으로 테스트 해줘
        // window.onclick = function(event) {
        //     if (event.target == modal) {
        //         modal.style.display = 'none';
        //     }
        // }
    });

    document.getElementById('submitRating').addEventListener('click', function() {
        const selectedRating = document.querySelector('input[name="rating"]:checked').value;
        const ratingData = {
            placeId: placeId,
            rating: selectedRating
        };

        console.log('Selected Star Rating:', selectedRating);

        // 백엔드 경로 설정 필요
        //11.19수정 ,fetch(`/submit/rating`, {
		fetch(`/starrating/enroll`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(ratingData)

        })

        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })

        // .then(() => {
            
        // })
        .catch(error => {
            console.error('Error:', error);
        });
    });
});