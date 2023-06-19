-- 상추 거래글 테이블
create table market(
	market_num number,
	market_title varchar2(60) not null,
	market_content clob not null,
	market_hit number(4),
 	market_regdate date default SYSDATE not null,
	market_modifydate date,
	market_status number(1) not null,
	market_photo1 varchar2(300) not null,
	market_photo2 varchar2(300) not null,
	mem_num number not null,
	constraint market_pk primary key (market_num),
	constraint market_fk foreign key (mem_num) references omember (mem_num)
);
create sequence market_seq;

-- 채팅방 테이블
create table chatroom(
	chatroom_num number,
	market_num number not null,
	seller_num number not null,
	buyer_num number not null,
	constraint chatroom_pk primary key (chatroom_num),
	constraint chatroom_fk1 foreign key (market_num) references market (market_num),
	constraint chatroom_fk2 foreign key (seller_num) references omember (mem_num),
	constraint chatroom_fk3 foreign key (buyer_num) references omember (mem_num)
);
create sequence chatroom_seq;

-- 채팅 내역 테이블
create table chat(
	chat_num number,
	chatroom_num number not null,
	mem_num number not null,
	message varchar2(900) not null,
	reg_date date default SYSDATE not null,
	read_check number(1) not null,
	constraint chat_pk primary key (chat_num),
	constraint chat_fk1 foreign key (chatroom_num) references chatroom (chatroom_num),
	constraint chat_fk2 foreign key (mem_num) references omember (mem_num)
);
create sequence chat_seq;
