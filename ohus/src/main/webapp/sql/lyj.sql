--회원관리  
--회원테이블           
create table omember(
 mem_num number,
 id varchar2(12) unique not null,
 auth number(1) default 2 not null,
 constraint member_pk primary key(mem_num)
);
--회원상세테이블
create table omember_detail(
 mem_num number,
 name varchar2(30) not null,
 auth number(1) default 2 not null,
 reports number(3),
 password varchar2(15) not null,
 phone varchar2(15) not null,
 email varchar2(50) not null,
 zipcode varchar2(5) not null,
 address1 varchar2(90) not null,
 address2 varchar2(90) not null,
 photo varchar2(150),
 reg_date date default sysdate not null,
 modify_date date,
 constraint omember_detail_pk primary key (mem_num),
 constraint omember_detail_fk foreign key (mem_num) references omember (mem_num)
);

alter table omember_detail RENAME COLUMN pw TO password;
create sequence omember_seq;
--디테일 시퀀스
--이벤트
--이벤트 테이블
create table oevent(
 event_num number,
 event_title varchar2(150) not null,
 event_photo varchar2(50),
 event_content clob not null,
 event_start varchar2(10) not null,  
 event_end varchar2(10) not null,
 event_regdate default sysdate not null, --이벤트 글 작성일
 event_modifydate, --이벤트 글 수정일
 event_status number(1) default 2 not null, --이벤트 상태(진행중, 종료) 1:종료 2: 진행중
 event_hit number default 0 not null, --조회수
 mem_num number not null, 
 winner_count number not null, --이벤트 당첨자 수
 constraint oevent_pk primary key(event_num),
 constraint oevent_fk foreign key(mem_num) references omember(mem_num)


);
create sequence oevent_seq;
--이벤트 댓글 테이블
create table oevent_reply(
 re_num number, --댓글 식별번호
 re_content varchar2(900) not null,
 re_date Date default sysdate not null, --댓글 작성일
 re_modifydate Date, --댓글 수정일
 re_ip varchar(40) not null, --댓글 작성자 ip
 event_num number not null,
 mem_num number not null,
 constraint oevent_reply_pk primary key(re_num),
 constraint oevent_reply_fk1 foreign key(event_num) references oevent(event_num), --부모글
 constraint oevent_reply_fk2 foreign key(mem_num) references omember(mem_num) --회원번호

);
create sequence oevent_reply_seq;