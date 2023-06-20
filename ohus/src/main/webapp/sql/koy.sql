-- 문의사항   
create table inquiry(
	inq_num number,
	inq_title varchar2(30) not null,
	inq_category number(1) not null, --1:사이트 문의 | 2:신고 문의
	inq_content clob not null,
	inq_regdate date default sysdate not null,
	inq_modifydate date,
	inq_ip varchar2(40) not null,
	inq_status number(1) default 1 not null, -- 1:처리전 | 2:처리완료
	mem_num number not null,
	constraint inquiry_pk primary key (inq_num),
	constraint inquiry_fk foreign key (mem_num) references omember (mem_num)
);

create sequence inquiry_seq;

-- 문의사항 답변
create table inquiry_answer(
	ans_num number,
	ans_content clob not null,
	ans_date date default sysdate not null,
	ans_mdate date,
	inq_num number not null,
	mem_num number not null,
	constraint inquiry_ans_pk primary key (ans_num),
	constraint inquiry_ans_fk1 foreign key (inq_num) references inquiry (inq_num),
	constraint inquiry_ans_fk foreign key (mem_num) references omember (mem_num)
);

create sequence inquiry_ans_seq;

-- 공지사항
create table notice (
	notice_num number,
	notice_title varchar2(30) not null,
	notice_content clob not null,
	notice_regdate date default sysdate not null,
	notice_mdate date,
	mem_num number not null,
	constraint notice_pk primary key (notice_num),
	constraint notice_fk foreign key (mem_num) references omember (mem_num)
);

create sequence notice_seq;

-- 상품문의
create table qna(
	qna_num number,
	qna_content clob not null,
	qna_regdate date default sysdate not null,
	qna_ip varchar2(40) not null,
	qna_status number(1) default 1 not null,
	mem_num number not null,
	detail_num not null,
	constraint qna_pk primary key (qna_num),
	constraint qna_fk foreign key (detail_num) references order_detail(detail_num)
);

create sequence qna_seq;

-- 상품문의 답변
create tabel qna_answer(
	qans_num number,
	qans_content clob not null,
	qans_date date default sysdate not null,
	qna_num number not null,
	mem_num number not null, -- 답변한 관리자
	constraint qna_ans_pk primary key (qna_num),
	constraint qna_ans_fk1 foreign key (qna_num) references qna (qna_num),
	constraint qna_ans_fk2 foreign key (mem_num) references omember (mem_num)
);

create sequence qna_ans_seq;