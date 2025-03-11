$(function () {
    // 헤더 요소 캐싱
    const $header = $('.kseta-header');
    const $toggler = $('.kseta-header__toggler');
    const $menu = $('.kseta-header__menu');
    const $menuItems = $('.kseta-header__menu-item');
    const $menuLinks = $('.kseta-header__menu-link');
    const $submenuLinks = $('.kseta-header__submenu-link');
    const $submenu = $('.kseta-header__submenu');
    const $logoImg = $('.kseta-header__logo-img');
    // 로그인 버튼 요소 선택자 변경 - li 요소를 선택
    const $authButtonsLi = $('li.kseta-header__auth-buttons');

    // 로고 이미지 경로
    const mobileLogoPath = '/assets/img/kseta_mobile_logo.png'; // 모바일용 로고 경로
    const desktopLogoPath = '/assets/img/kseta_rogo.png'; // 데스크탑용 로고 경로

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

    // 2. 모바일 토글 버튼 클릭 이벤트 (전체 메뉴 토글)
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
            // 로그인 버튼이 모바일 메뉴 하단에 표시되도록 복제
            $authButtonsLi.clone().appendTo($menu);
            // 원래 위치의 로그인 버튼은 숨김
            $authButtonsLi.hide();
        } else {
            // 메뉴에 추가된 로그인 버튼 제거
            $menu.find('li.kseta-header__auth-buttons').remove();
            // 원래 위치의 로그인 버튼 표시
            $authButtonsLi.show();
        }
    });

    // 3. 서브메뉴 앵커 링크 클릭 이벤트 - 메뉴 닫기
    $submenuLinks.on('click', function () {
        if (window.innerWidth < 992) {
            // 약간의 지연 후 메뉴 닫기 (링크 이동이 먼저 처리되도록)
            setTimeout(function () {
                // 모바일 환경에서 앵커 링크 클릭 시 메뉴 닫기
                $menu.removeClass('kseta-header__menu--active');
                $toggler.removeClass('kseta-header__toggler--active');

                // 메뉴에 추가된 로그인 버튼 제거
                $menu.find('li.kseta-header__auth-buttons').remove();
                // 원래 위치의 로그인 버튼 표시
                $authButtonsLi.show();

                // 아이콘 리셋 (필요시)
                $toggler.find('i').removeClass('fa-times').addClass('fa-bars');
            }, 100);
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
        // 로고 업데이트
        updateLogo();

        if (window.innerWidth >= 992) {
            // 데스크톱 모드로 전환 시 메뉴 초기화
            $menu.removeClass('kseta-header__menu--active');

            // 서브메뉴 초기화 - 데스크톱 모드에서는 숨김
            $submenu.removeAttr('style');
            $submenu.css({
                'display': 'none',
                'opacity': '0',
                'visibility': 'hidden'
            });

            // 모바일 메뉴 내 로그인 버튼 제거
            $menu.find('li.kseta-header__auth-buttons').remove();
            // 원본 로그인 버튼 표시
            $authButtonsLi.show();

            // 스크롤 위치에 따라 헤더 배경색 설정
            if ($(window).scrollTop() <= 20) {
                $header.removeClass('kseta-header--white');
            }
        } else {
            // 모바일 모드로 전환 시 서브메뉴를 펼침 상태로 설정
            $submenu.css({
                'display': 'block',
                'opacity': '1',
                'visibility': 'visible',
                'position': 'static',
                'box-shadow': 'none',
                'padding-left': '2rem'
            });

            // 데스크톱처럼 서브메뉴가 위로 뜨지 않도록 스타일 추가
            $submenu.find('li a').css({
                'padding': '0.5rem 0'
            });

            // 메인 메뉴 아이템 스타일 조정
            $menuLinks.css({
                'font-weight': 'bold'
            });
        }
    });

    // 6. 모바일/데스크탑에 따른 로고 변경 함수
    function updateLogo() {
        if (window.innerWidth < 992) {
            // 모바일 환경에서 로고 변경
            if ($logoImg.attr('src') !== mobileLogoPath) {
                $logoImg.attr('src', mobileLogoPath);
                // 모바일 로고 크기 조정 (필요한 경우)
                $logoImg.css({
                    'height': '80px',
                    'width': 'auto'
                });
            }
        }
    }

    // 7. 페이지 로드 시 초기 설정
    if ($(window).scrollTop() > 20) {
        $header.addClass('kseta-header--white');
    }

    // 8. 모바일 모드 초기 설정
    if (window.innerWidth < 992) {
        // 모바일에서는 서브메뉴를 항상 펼침
        $submenu.css({
            'display': 'block',
            'opacity': '1',
            'visibility': 'visible',
            'position': 'static',
            'box-shadow': 'none',
            'padding-left': '2rem'
        });

        // 서브메뉴 링크 스타일 조정
        $submenu.find('li a').css({
            'padding': '0.5rem 0'
        });

        // 메인 메뉴 아이템 스타일 조정
        $menuLinks.css({
            'font-weight': 'bold'
        });
    }

    // 9. 초기 로고 설정
    updateLogo();
});