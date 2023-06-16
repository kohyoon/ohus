--상품관리  
CREATE TABLE item(
	item_num number primary key,--상품번호
	item_name varchar2(600) unique not null,--상품명
	item_category number(1) not null,--상품카테고리(0:침대,1:소파,2:옷장,3:서랍/수납장,4:테이블,5:의자)
	item_price number(9) not null,--상품 가격
	item_content clob not null,--상품 상세내용
	item_stock number(3) not null,--상품 재고
	item_hit number(9) default 0 not null,--상품 조회수
	item_fav number(9) default 0 not null,--좋아요
	item_score number(9) default 0 not null,--상품평점
	item_origin varchar2(50) not null,--원산지
	item_photo1 varchar2(50) not null,--상품대표사진
	item_photo2 varchar2(50) not null,--상품사진1
	item_photo3 varchar2(50),--상품사진2
	item_regdate date default sysdate not null,--상품등록일
	item_mdate date,--상품정보 수정일
	item_status number(1) not null--상품 판매 가능 여부(1:미표시, 2:표시)
);
CREATE SEQUENCE item_seq;

--상품리뷰
CREATE TABLE item_review(
	review_num number primary key,--리뷰번호
	mem_num number foreign key omember (mem_num),--리뷰작성회원번호
	item_num number foreign key item (item_num),--리뷰한 상품번호
	item_score number(1) not null,--상품점수
	review_content clob not null,--상품리뷰내용
	review_photo varchar2(50),--리뷰 사진
	review_regdate date default sysdate not null--리뷰작성일
);
CREATE SEQUENCE item_review_seq;

CREATE TABLE item_fav(
	fav_num number primary key,--상품찜번호
	mem_num number foreign key omember (mem_num),--좋아요한 회원번호
	item_num number foreign key item (item_num)--상품번호
);
CREATE SEQUENCE item_fav_seq;