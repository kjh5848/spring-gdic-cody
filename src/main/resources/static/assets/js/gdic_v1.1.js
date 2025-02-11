var gdic_header = `
<div class="container">
    <div class="row">
        <div class="col d-flex justify-content-between align-items-center">
            <a  href="/"><h1><img  class="responsive-logo"  src="/assets/img/kseta_rogo.png" /></h1></a>
            <nav class="gdic_nav">
                <ul class="d-flex f_s3">
                    <li class="nav_list1">
                        <a href="m1_1_planning.html" class="main_link">협회소개</a>
                        <ul class="sub_menu f_s1">
                            <li><a href="m1_1_planning.html">사업개요</a></li>
                        </ul>
                    </li>
                    <li class="nav_list2">
                        <a href="m2_1_vision.html" class="main_link">융합교육</a>
                        <ul class="sub_menu f_s1">
                            <li><a href="m2_1_vision.html">위치 및 투자가치</a></li>
                            <li><a href="m2_2_benefit.html">세제혜택</a></li>
                        </ul>
                    </li>
                    <li class="nav_list3">
                        <a href="m3_1_sale01.html" class="main_link">자격검정</a>
                        <ul class="sub_menu f_s1">
                            <li><a href="m3_1_sale01.html">토지이용계획</a></li>
                            <li><a href="m3_2_sale02.html">공급 정보</a></li>
                            <li><a href="m3_3_sale03.html">공급공고</a></li>
                        </ul>
                    </li>
                    <li class="nav_list4">
                        <a href="m4_1_notice.html" class="main_link">기타사업</a>
                        <ul class="sub_menu f_s1">
                            <li><a href="m4_1_notice.html">공지사항</a></li>
                            <!--
                            <li><a href="m4_2_pds.html">자료실</a></li>
                            <li><a href="m4_3_faq.html">FAQ</a></li>
                            <li><a href="m4_4_guest2.html">공급문의</a></li>
                            -->
                            <li><a href="m4_4_guest2.html">관심고객등록</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
            <div class="head_tel_box f_c_b">
                <p class="f_s3">용지분양문의</p>
                <p class="ft_s2 fw_pb">1577-9333</p>
            </div>
        </div>
    </div>
</div>
<div class="menu_more_bg"></div>`;

var gdic_footer = `
<div class="container">
    <div class="row">
        <div class="footer_slide">
            <ul class="footer_slide_box">
                <li><img src="/assets/img/company01.png"></li>
                <li><img src="/assets/img/company02.png"></li>
                <li><img src="/assets/img/company03.png"></li>
                <li><img src="/assets/img/company04.png"></li>
                <li><img src="/assets/img/company05.png"></li>
            </ul>
        </div>

        <div class="col d-flex justify-content-between">
            <div class="f_c_g2 f_s0">
                <p class="mb_10">시행.김해대동첨단산업단지(주) | 경상남도 김해시 경원로 73번길 15, 2층 | T. 055.329.0460 | F. 055.329.0463 <br />
                온라인 대행 :  | 대표 : | 사업자등록번호 : <br />
                본 웹사이트에서의 제공되는 모든 콘텐츠 및 서비스 저작권은 법적으로 보호받고 있으며 불법적인 정보의 무단도용등은 불법임을 명시합니다.
                </p>
                <p>Powered (c) 2018 by thedaham. all right reserved.</p>
            </div>
            <div><img src="/assets/img/gdic_logo.png" /></div>
        </div>
    </div>
</div>

`;

var gdic_board = `
<div class="board_top1 border-bottom f_s0 f_c_g2">
    <p>2023년 04월 공사진행 현황 알림</p>
</div>
<div class="board_top1 mb_10 f_s0 f_c_g2">
    <p><span class="mr_10">작성자 : 최고관리자</span> <span class="mr_10">23-05-12 09:33</span> <span class="mr_10">조회수 : 266회</span> <span class="mr_10">댓글 : 0건</span></p>
</div>
<div class="d-flex justify-content-between align-items-center">
    <button class="board_btn1 f_s1">다음글</button>

    <button class="board_btn1 f_s1">이전글</button>
</div>

<div class="border_con_box">
    <p>전경사진</p>
    <p>전경사진</p>
    <p>전경사진</p>
    <p>전경사진</p>
    <p>전경사진</p>
    <p>전경사진</p>
    <p>전경사진</p>
    <p>전경사진</p>
    <p>전경사진</p>
</div>

<div class="f_c_g1 mb_20 f_s1">
    <p>댓글목록</p>
</div>

<div class="f_c_g2 mb_30 f_s0 text-center">
    <p>등록된 댓글이 없습니다.</p>
</div>

<div class="d-flex justify-content-end align-items-center">
    <button class="board_btn1 f_s1" onclick="board_hide()">목록으로</button>
</div>
`

$(function(){
    $(document).on("mouseover", ".gdic_nav", function () {
        $('#gdic_header').addClass('sub_show');
        $('.gdic_nav .sub_menu').addClass('show');
    });

    $(document).on("mouseout", ".gdic_nav", function () {
        $('#gdic_header').removeClass('sub_show');
        $('.gdic_nav .sub_menu').removeClass('show');
    });
})

function board_show(){
    $('.border_more_area').show();
    $('.border_list_area').hide();
}

function board_hide(){
    $('.border_more_area').hide();
    $('.border_list_area').show();
}

function footer_slide_on(){
    $('.footer_slide_box').slick({
        slide: 'li',		//슬라이드 되어야 할 태그 ex) div, li 
        infinite : true, 	//무한 반복 옵션
        slidesToShow : 3,		// 한 화면에 보여질 컨텐츠 개수
        speed : 350,	 // 다음 버튼 누르고 다음 화면 뜨는데까지 걸리는 시간(ms)
        arrows : true, 		// 옆으로 이동하는 화살표 표시 여부
        dots : false, 		// 스크롤바 아래 점으로 페이지네이션 여부
        autoplay : true,			// 자동 스크롤 사용 여부
        autoplaySpeed : 5000, 		// 자동 스크롤 시 다음으로 넘어가는데 걸리는 시간 (ms)
        pauseOnHover : true,		// 슬라이드 이동	시 마우스 호버하면 슬라이더 멈추게 설정
        draggable : true, 	//드래그 가능 여부
    })
}