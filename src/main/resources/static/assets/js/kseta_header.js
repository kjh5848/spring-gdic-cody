$(function () {
    // 데스크톱 메뉴 호버 이벤트
    $(document).on("mouseover", ".kseta-header__menu-item", function () {
        if (window.innerWidth >= 992) {  // 데스크톱에서만 호버 효과 적용
            $(this).find('.kseta-header__submenu').css({
                'display': 'block',
                'opacity': '1',
                'visibility': 'visible'
            });
        }
    });

    $(document).on("mouseout", ".kseta-header__menu-item", function () {
        if (window.innerWidth >= 992) {  // 데스크톱에서만 호버 효과 적용
            $(this).find('.kseta-header__submenu').css({
                'display': 'none',
                'opacity': '0',
                'visibility': 'hidden'
            });
        }
    });

    // 모바일 토글 버튼 클릭 이벤트
    $('.kseta-header__toggler').on('click', function () {
        $(this).find('i').toggleClass('fa-bars fa-times');
        $('.kseta-header__menu').toggleClass('kseta-header__menu--active');
    });

    // 모바일 서브메뉴 토글
    $('.kseta-header__menu-link').on('click', function (e) {
        if (window.innerWidth < 992) {  // 모바일에서만 적용
            const $submenu = $(this).siblings('.kseta-header__submenu');
            if ($submenu.length) {
                e.preventDefault();
                $('.kseta-header__submenu').not($submenu).slideUp(); // 다른 서브메뉴 닫기
                $('.kseta-header__menu-link').not($(this)).removeClass('active');
                $submenu.slideToggle();
                $(this).toggleClass('active');
            }
        }
    });


    // 리사이즈 이벤트 처리
    $(window).on('resize', function () {
        if (window.innerWidth >= 992) {
            $('.kseta-header__menu').removeClass('kseta-header__menu--active');
            $('.kseta-header__submenu').removeAttr('style');
        }
    });

    $(document).ready(function () {
        let lastScrollTop = 0;
        let isScrolling;

        $(window).on("scroll", function () {
            clearTimeout(isScrolling);

            let scrollTop = $(this).scrollTop();

            if (scrollTop > lastScrollTop) {
                $(".kseta-header").addClass("kseta-header--hidden"); // 아래로 스크롤 시 숨김
            } else {
                $(".kseta-header").removeClass("kseta-header--hidden"); // 위로 스크롤 시 다시 보이게
            }

            lastScrollTop = scrollTop;

            // 스크롤이 멈춘 후 헤더 다시 보이게 설정 (0.3초 후)
            isScrolling = setTimeout(function () {
                $(".kseta-header").removeClass("kseta-header--hidden");
            }, 300);
        });
    });

});
