var kseta_header = ``;


var kseta_footer = `


`;
$(document).ready(function () {
    initDesktopMenu();
    initMobileMenu();
    initResizeHandler();
    toggleBoard();
});

// ✅ 데스크톱 메뉴 호버 이벤트
function initDesktopMenu() {
    $(document).on("mouseover", ".kseta-header__menu-item", function () {
        if (window.innerWidth >= 992) {
            $(this).find('.kseta-header__submenu').css({
                'display': 'block',
                'opacity': '1',
                'visibility': 'visible'
            });
        }
    });

    $(document).on("mouseout", ".kseta-header__menu-item", function () {
        if (window.innerWidth >= 992) {
            $(this).find('.kseta-header__submenu').css({
                'display': 'none',
                'opacity': '0',
                'visibility': 'hidden'
            });
        }
    });
}

// ✅ 모바일 메뉴 토글
function initMobileMenu() {
    $('.kseta-header__toggler').on('click', function () {
        $(this).find('i').toggleClass('fa-bars fa-times');
        $('.kseta-header__menu').toggleClass('kseta-header__menu--active');
    });

    $('.kseta-header__menu-link').on('click', function (e) {
        if (window.innerWidth < 992) {
            const $submenu = $(this).siblings('.kseta-header__submenu');
            if ($submenu.length) {
                e.preventDefault();
                $('.kseta-header__submenu').not($submenu).slideUp();
                $('.kseta-header__menu-link').not($(this)).removeClass('active');
                $submenu.slideToggle();
                $(this).toggleClass('active');
            }
        }
    });
}

// ✅ 화면 크기 변경 시 이벤트 처리
function initResizeHandler() {
    $(window).on('resize', function () {
        if (window.innerWidth >= 992) {
            $('.kseta-header__menu').removeClass('kseta-header__menu--active');
            $('.kseta-header__submenu').removeAttr('style');
        }
    });
}

// ✅ 특정 요소 보이기/숨기기 (기존 `board_show` 기능 확장)
function toggleBoard(showMore) {
    if (showMore) {
        $('.border_more_area').show();
        $('.border_list_area').hide();
    } else {
        $('.border_more_area').hide();
        $('.border_list_area').show();
    }
}


function board_show() {
    $('.border_more_area').show();
    $('.border_list_area').hide();
}

function board_hide() {
    $('.border_more_area').hide();
    $('.border_list_area').show();
}

function footer_slide_on() {
    $('.footer_slide_box').slick({
        slide: 'li',		//슬라이드 되어야 할 태그 ex) div, li 
        infinite: true, 	//무한 반복 옵션
        slidesToShow: 3,		// 한 화면에 보여질 컨텐츠 개수
        speed: 350,	 // 다음 버튼 누르고 다음 화면 뜨는데까지 걸리는 시간(ms)
        arrows: true, 		// 옆으로 이동하는 화살표 표시 여부
        dots: false, 		// 스크롤바 아래 점으로 페이지네이션 여부
        autoplay: true,			// 자동 스크롤 사용 여부
        autoplaySpeed: 5000, 		// 자동 스크롤 시 다음으로 넘어가는데 걸리는 시간 (ms)
        pauseOnHover: true,		// 슬라이드 이동	시 마우스 호버하면 슬라이더 멈추게 설정
        draggable: true, 	//드래그 가능 여부
    })
}