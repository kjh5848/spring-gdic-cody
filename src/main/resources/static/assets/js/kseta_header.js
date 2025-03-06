$(function () {
    // 헤더 요소 캐싱
    const $header = $('.kseta-header');
    const $toggler = $('.kseta-header__toggler');
    const $menu = $('.kseta-header__menu');
    const $menuItems = $('.kseta-header__menu-item');
    const $menuLinks = $('.kseta-header__menu-link');
    const $authButtons = $('.kseta-header__auth-buttons');

    // 스크롤 변수
    let lastScrollY = 0;
    let isScrolling = false;
    let scrollTimer = null;

    // 1. 데스크톱 메뉴 호버 이벤트 (서브메뉴 표시)
    $menuItems.on("mouseover", function () {
        if (window.innerWidth >= 992) {
            $(this).find('.kseta-header__submenu').css({
                'display': 'block',
                'opacity': '1',
                'visibility': 'visible'
            });
        }
    });

    $menuItems.on("mouseout", function () {
        if (window.innerWidth >= 992) {
            $(this).find('.kseta-header__submenu').css({
                'display': 'none',
                'opacity': '0',
                'visibility': 'hidden'
            });
        }
    });

    // 2. 모바일 토글 버튼 클릭 이벤트
    $toggler.on('click', function () {
        // 아이콘 변경 (아이콘이 있는 경우)
        $(this).find('i').toggleClass('fa-bars fa-times');

        // 토글 버튼 활성화 스타일
        $(this).toggleClass('kseta-header__toggler--active');

        // 메뉴 표시/숨김
        $menu.toggleClass('kseta-header__menu--active');

        // 토글 버튼 클릭 시 헤더 배경색 변경
        if ($menu.hasClass('kseta-header__menu--active')) {
            $header.addClass('kseta-header--white');
        } else {
            // 스크롤 위치가 상단인 경우에만 배경색 제거
            if ($(window).scrollTop() <= 20) {
                $header.removeClass('kseta-header--white');
            }
        }

        // 로그인 버튼 위치 조정: 모바일 메뉴에 추가
        if ($menu.hasClass('kseta-header__menu--active')) {
            // 로그인 버튼이 모바일 메뉴 하단에 표시되도록 이동
            $authButtons.appendTo($menu);
        } else {
            // 메뉴 닫히면 원래 위치로 복원
            $authButtons.appendTo('.kseta-header__container');
        }
    });

    // 3. 모바일 서브메뉴 토글
    $menuLinks.on('click', function (e) {
        if (window.innerWidth < 992) {
            const $submenu = $(this).siblings('.kseta-header__submenu');
            if ($submenu.length) {
                e.preventDefault();

                // 다른 서브메뉴 닫기 (옵션)
                $('.kseta-header__submenu').not($submenu).slideUp();
                $menuLinks.not($(this)).removeClass('kseta-header__menu-link--active');

                // 현재 서브메뉴 토글
                $submenu.slideToggle();
                $(this).toggleClass('kseta-header__menu-link--active');
            }
        }
    });

    // 4. 스크롤 이벤트 핸들러
    $(window).on('scroll', function () {
        const currentScrollY = $(window).scrollTop();

        // 스크롤 20px 이상시 배경색 변경
        if (currentScrollY > 20) {
            $header.addClass('kseta-header--white');
        } else {
            // 모바일 메뉴가 열려있지 않은 경우에만 배경색 제거
            if (!$menu.hasClass('kseta-header__menu--active')) {
                $header.removeClass('kseta-header--white');
            }
        }

        // 스크롤 방향 감지 및 헤더 숨김/표시
        if (currentScrollY > lastScrollY && currentScrollY > 64) {
            if (!isScrolling) {
                $header.addClass('kseta-header--hidden');
            }
        } else {
            $header.removeClass('kseta-header--hidden');
        }

        lastScrollY = currentScrollY;
        isScrolling = true;

        // 스크롤 멈춤 감지
        clearTimeout(scrollTimer);
        scrollTimer = setTimeout(function () {
            isScrolling = false;
            $header.removeClass('kseta-header--hidden');
        }, 150);
    });

    // 5. 리사이즈 이벤트 처리
    $(window).on('resize', function () {
        if (window.innerWidth >= 992) {
            // 데스크톱 모드로 전환 시 메뉴 초기화
            $menu.removeClass('kseta-header__menu--active');
            $('.kseta-header__submenu').removeAttr('style');
            $authButtons.appendTo('.kseta-header__container');

            // 스크롤 위치에 따라 헤더 배경색 설정
            if ($(window).scrollTop() <= 20) {
                $header.removeClass('kseta-header--white');
            }
        }
    });

    // 6. 페이지 로드 시 초기 설정
    if ($(window).scrollTop() > 20) {
        $header.addClass('kseta-header--white');
    }
});