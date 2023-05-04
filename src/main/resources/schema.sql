DROP TABLE IF EXISTS NOTICE;

create table NOTICE
(
    ID BIGINT auto_increment primary key,
    TITLE VARCHAR(255),
    DESCRIPTION VARCHAR(255),

    WATCH BIGINT,
    LIKES BIGINT,
    REG_DATE TIMESTAMP
-- 띄어쓰기 되는 단어는 자동으로 _ 가 들어가는 수정이 발생한다. 이 부분을 고치지 않으면 부적절한 값이 들어간 것으로 간주된다.
);