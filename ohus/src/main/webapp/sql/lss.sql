-- 커뮤니티
CREATE TABLE cboard (
  cboard_num NUMBER NOT NULL, -- 게시물 번호
  cboard_title VARCHAR2(12) NOT NULL, -- 게시물 제목
  cboard_hit NUMBER(5) DEFAULT 0 NOT NULL, -- 게시물 조회수
  cboard_category NUMBER(1) NOT NULL, -- 커뮤니티 카테고리 / 0:일상 / 1:취미 / 2:자랑 / 3:추천
  cboard_content CLOB NOT NULL, -- 게시물 내용
  cboard_photo1 VARCHAR2(50) NOT NULL, -- 사진1
  cboard_photo2 VARCHAR2(50), -- 사진2
  cboard_regdate DATE default sysdate NOT NULL, -- 게시물 등록일
  cboard_modifydate DATE, -- 게시물 최종 수정일
  cboard_ip VARCHAR2(16) NOT NULL, -- ip 주소
  order_num NUMBER, -- 주문 번호
  mem_num NUMBER NOT NULL, -- 회원 번호
  cboard_fav NUMBER(5) NOT NULL, -- 게시물 좋아요
  favCount NUMBER(5), -- 좋아요 갯수
  CONSTRAINT cboard_pk PRIMARY KEY (cboard_num),
  CONSTRAINT cboard_fk1 FOREIGN KEY (order_num) REFERENCES orders (order_num),
  CONSTRAINT cboard_fk2 FOREIGN KEY (mem_num) REFERENCES omember (mem_num)
);
create sequence cboard_seq; 



-- 커뮤니티 댓글
CREATE TABLE cboard_reply (
  re_num NUMBER NOT NULL, -- 댓글 번호
  re_content VARCHAR2(30) NOT NULL, -- 댓글 내용
  re_date DATE default sysdate NOT NULL, -- 댓글 작성일
  re_modifydate DATE, -- 댓글 수정일
  re_ip VARCHAR2(16) NOT NULL, -- 댓글 ip
  re_fav NUMBER(5), -- 댓글 좋아요
  cboard_num NUMBER NOT NULL, -- 게시글 번호
  mem_num NUMBER NOT NULL, -- 회원 번호
  CONSTRAINT reply_pk PRIMARY KEY (re_num),
  CONSTRAINT reply_fk1 FOREIGN KEY (mem_num) REFERENCES omember (mem_num),
  CONSTRAINT reply_fk2 FOREIGN KEY (cboard_num) REFERENCES cboard (cboard_num)
);
create sequence cboard_reply_seq; 

-- 커뮤니티 댓글 신고
CREATE TABLE cboard_report (
  dec_num NUMBER NOT NULL, -- 신고 번호
  dec_category NUMBER NOT NULL, -- 신고 카테고리 / 1:주제와 맞지 않음 / 2:광고 / 3:저작권 침해 / 4:욕설/비방 /  5:개인정보 노출 / 6:기타
  dec_regdate DATE default sysdate NOT NULL, -- 신고 날짜
  mem_num NUMBER NOT NULL, -- 회원 번호
  re_num NUMBER NOT NULL, -- 댓글 번호
  CONSTRAINT report_pk PRIMARY KEY (dec_num),
  CONSTRAINT report_fk1 FOREIGN KEY (mem_num) REFERENCES omember (mem_num),
  CONSTRAINT report_fk2 FOREIGN KEY (re_num) REFERENCES cboard_reply (re_num)
);
create sequence cboard_report_seq;



-- 커뮤니티 스크랩
CREATE TABLE cboard_fav ( 
fav_num NUMBER NOT NULL, -- 커뮤니티 스크랩 번호 
cboard_num NUMBER NOT NULL,  -- 게시물 번호
mem_num NUMBER NOT NULL, -- 회원 번호
CONSTRAINT fav_pk PRIMARY KEY (fav_num), 
CONSTRAINT fav_fk1 FOREIGN KEY (cboard_num) REFERENCES cboard (cboard_num), 
CONSTRAINT fav_fk2 FOREIGN KEY (mem_num) REFERENCES omember (mem_num) 
);
create sequence cboard_fav;