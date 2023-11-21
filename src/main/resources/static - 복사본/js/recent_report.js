document.addEventListener('DOMContentLoaded', function () {
    const placeId = new URLSearchParams(window.location.search).get('id');
    const reportsContainer = document.getElementById('previous_reports_container');

    function fetchLatestReports() {
        fetch(`/report/show?id=${placeId}`) // Adjust this URL to match your backend endpoint
            .then(response => response.json())
            .then(reports => {
                // Clear existing content
                reportsContainer.innerHTML = '';

                // Process and display up to the first three reports
                reports.slice(0, 3).forEach(report => {
                    const reportElement = createReportElement(report);
                    reportsContainer.appendChild(reportElement);
                });
            })
            .catch(error => console.error('Error fetching reports:', error));
    }

    function createReportElement(report) {
        // Create a new HTML element for each report
        const reportDiv = document.createElement('div');
        reportDiv.className = 'body_content_recent';
        reportDiv.innerHTML = `
            <div class="recent_textbox">
                <div class="recent_textbox_title">${report.type}</div>
                <div class="recent_textbox_detail">
                    <div class="textbox_detail_text">${report.content}</div>
                    <div class="textbox_detail_date">${new Date(report.createDate).toLocaleDateString('ko-KR')}</div>
                </div>
            </div>
            <div class="recent_likeButton">
                <div class="recent_likeButton_number">+ ${report.count}</div>
                <div class="recent_likeButton_heart" data-report-id="${report.id}">♡</div>
            </div>
        `;


        const heartIcon = reportDiv.querySelector('.recent_likeButton_heart');
        heartIcon.addEventListener('click', function (event) {
            if (event.target.classList.contains('recent_likeButton_heart')) {
                const reportId = event.target.getAttribute('data-report-id');
                fetch(`/report/clickheart?id=${reportId}`)
                    .then(response => {
                        return response.text(); // Assuming the server responds with plain text
                    })
                    .then(body => {
                        if (body.trim().toLowerCase() === "ip") {
                            // Handle duplicated IP case
                            alert('이미 신고 횟수가 추가 되었습니다.');
                        }
                        else {
                            // Handle successful submission	        // Uncomment the following lines if you want to display an alert and reload the page
                            alert('신고 횟수가 증가되었습니다.');
                            location.reload();
                        }
                    });
            }
        });//리스너

        return reportDiv;
    }

    document.addEventListener("reportSubmitted", function () {
        fetchLatestReports();
    });

    fetchLatestReports();
});