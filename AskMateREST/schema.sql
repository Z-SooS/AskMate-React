DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS answer;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS web_user;
DROP TABLE IF EXISTS friendship;

CREATE TABLE web_user (
    id int primary key IDENTITY(1,1),
    username varchar(20) NOT NULL ,
    password char(60) NOT NULL ,
    reputation int NOT NULL
);

CREATE TABLE friendship(
    web_user_id int primary key foreign key REFERENCES web_user(id),
    friend_id int NOT NULL foreign key REFERENCES web_user(id),
    state varchar(10) NOT NULL
);

CREATE TABLE tag(
    id int primary key IDENTITY (1,1),
    name varchar(50) NOT NULL
)

CREATE TABLE question(
    id int primary key IDENTITY (1,1),
    user_id int NOT NULL foreign key REFERENCES web_user(id),
    title varchar(50) NOT NULL,
    message varchar(max),
)

CREATE TABLE answer(
    id int primary key IDENTITY (1,1),
    user_id int NOT NULL foreign key REFERENCES web_user(id),
    question_id int NOT NULL foreign key REFERENCES question(id),
    message varchar(max)
)

CREATE TABLE comment(
    id int primary key IDENTITY (1,1),
    user_id int NOT NULL FOREIGN KEY REFERENCES web_user(id),
    question_id int foreign key REFERENCES question(id),
    answer_id int foreign key REFERENCES answer(id),
    message varchar(max)
)
