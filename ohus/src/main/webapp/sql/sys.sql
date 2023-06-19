-- 주문관리
CREATE TABLE order(
	order_num number,-- 주문번호
	item_name varchar2(600) not null,-- 대표상품명
	mem_num number not null,-- 회원번호
	order_payment number not null,-- 결제 방법(0 : 무통장, 1: 카드)
	order_status number not null,-- 배송상태(0: 결제 완료, 1: 주문 접수, 2: 배송중, 3: 배송완료)
	order_modifydate date,-- 배송상태 수정일
	order_name varchar2(30) not null,-- 수령자 이름
	order_zipcode varchar2(5) not null, -- 배송지 우편번호
	order_address1 varchar2(50) not null, -- 주소
	order_address2 varchar2(50) not null, -- 상세주소
	mem_phone varchar2(15) not null, -- 주문자(회원)전화번호
	order_regdate date default sysdate not null, -- 주문날짜
	order_notice varchar2(4000), -- 배송메시지
	order_total number(9) not null, -- 주문가격
	order_quantity number(9) not null, -- 주문수량
	constraint order_pk primary key (order_num),
	constraint order_fk foreign key (mem_num) references member (mem_num)
);
CREATE sequence order_seq;

-- 주문 상세(단품 주문 상세)
CREATE TABLE order_detail(
	detail_num number, -- 주문상세번호
	mem_num number not null, -- 회원 번호
	item_num number not null, -- 상품 번호
	item_name varchar2() not null,-- 상품명
	item_price number() not null, -- 상품가격
	item_quantity number() not null, -- 상품개수
	order_quantity number(7) not null, -- 주문개수
	item_total number(8) not null, -- 총상품금액(동일상품 합산 금액)
	order_num number not null, -- 주문번호
	constraint order_detail_pk primary key (detail_num),
	constraint order_detail_fk1 foreign key (order_num) references order (order_num),
	constraint order_detail_fk2 foreign key (mem_num) references order (mem_num),
	constraint order_detail_fk3 foreign key (item_num) references order (item_num)
);
CREATE sequence order_detail_seq;

-- 장바구니
CREATE TABLE cart(
	cart_num number, -- 장바구니번호
	item_num number not null, -- 상품번호
	order_quantity number(5) not null, -- 주문수량
	reg_date date default sysdate not null, -- 장바구니에 넣은 날짜
	mem_num number not null, -- 회원번호
	constraint cart_pk primary key (cart_num),
	constraint cart_fk1 foreign key (item_num) references item (item_num),
	constraint cart_fk2 foreign key (mem_num) references member (mem_num),
);
CREATE sequence cart_seq;
