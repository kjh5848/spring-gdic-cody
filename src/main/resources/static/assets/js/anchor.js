$(document).ready(function () {
    let lastClickedAnchor = null; // 마지막으로 클릭한 앵커 저장 (같은 앵커 클릭 시 처리하기 위해 사용)
    const headerHeight = $('.kseta-header').outerHeight() || 80; // 헤더 높이 계산 (감지되지 않으면 기본값 80px 사용)

    // 📌 [1] 탭 전환 기능
    $('.kseta-certificates__tab').on('click', function () {
        const tabId = $(this).data('tab'); // 클릭한 탭의 ID 가져오기

        // 모든 탭에서 active 클래스 제거 후, 현재 클릭한 탭에 추가
        $('.kseta-certificates__tab').removeClass('active');
        $(this).addClass('active');

        // 모든 탭 콘텐츠 숨김 후, 선택한 탭의 콘텐츠 활성화
        $('.kseta-certificates__tab-content').removeClass('active');
        $(`#${tabId}-content`).addClass('active');

        // // 페이지 최상단으로 이동 (부드러운 스크롤 효과 적용)
        // $('html, body').animate({scrollTop: 0}, 300);

        // URL 해시값 업데이트 (탭의 ID 저장)
        window.location.hash = tabId;

        // 만약 탭 변경 후 특정 섹션(목차)으로 이동해야 한다면 실행
        setTimeout(function () {
            handleAnchorScroll();
        }, 400);
    });

    // 📌 [2] URL 해시 변경 시 자동으로 해당 탭과 섹션 이동 처리
    function handleHashChange() {
        const hash = window.location.hash.substr(1); // '#' 제외한 해시값 가져오기

        if (hash) {
            const parts = hash.split('-'); // 해시를 '-' 기준으로 나눠 탭과 섹션 분리
            const tabId = parts[0]; // 탭 ID 추출 (예: "bigdata", "ai", "certificate")
            const anchorId = parts.length > 1 ? parts.slice(1).join('-') : null; // 특정 섹션 ID (예: "exam_bigdata")

            // 유효한 탭인지 확인 후 활성화
            if (['bigdata', 'ai', 'certificate'].includes(tabId)) {
                $(`.kseta-certificates__tab[data-tab="${tabId}"]`).click();

                // 특정 섹션이 있으면 해당 섹션으로 이동
                setTimeout(function () {
                    if (anchorId) {
                        scrollToAnchor(anchorId, true);
                    }
                }, 400);
            }
        }
    }

    // 📌 [4] 현재 URL 해시값을 확인하고 특정 섹션(목차)으로 이동
    function handleAnchorScroll() {
        const currentHash = window.location.hash.substr(1);
        if (currentHash.includes('-')) {
            scrollToAnchor(currentHash.split('-')[1], true);
        }
    }

    // 📌 [5] 페이지 로드 시, URL 해시값이 있으면 해당 탭과 섹션으로 이동
    handleHashChange();

    // 📌 [7] 해시 변경 시 자동으로 해당 섹션 이동 처리
    $(window).on('hashchange', function () {
        handleHashChange();
    });
});
