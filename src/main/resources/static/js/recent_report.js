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
                <div class="recent_likeButton_heart">♡</div>
            </div>
        `;
        return reportDiv;
    }

    document.addEventListener("reportSubmitted", function() {
        fetchLatestReports();
    });

    fetchLatestReports();
});

