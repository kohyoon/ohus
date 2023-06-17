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
 pw varchar2(15) not null,
 phone varchar2(15) not null,
 email varchar2(50) not null,
 zipcode varchar2(5) not null,
 address1 varchar2(90) not null,
 address2 varchar2(90) not null,
 ip varchar2(30) not null,
 photo varchar2(150),
 reg_date date default sysdate not null,
 modify_date date,
 constraint omember_detail_pk primary key (mem_num),
 constraint omember_detail_fk foreign key (mem_num) references omember (mem_num)
);
create sequence omember_seq;
