DROP TABLE IF EXISTS question_tag;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS answer;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS friendship;
DROP TABLE IF EXISTS web_user;
GO

CREATE TABLE web_user (
    id int primary key IDENTITY(1,1),
    username varchar(20) NOT NULL ,
    password char(60) NOT NULL ,
    email varchar(100) NOT NULL,
    reputation int NOT NULL
)
GO
CREATE TABLE friendship(
    web_user_id int NOT NULL primary key foreign key REFERENCES web_user(id),
    friend_id int NOT NULL foreign key REFERENCES web_user(id),
    state varchar(10) NOT NULL
)
GO
CREATE TABLE tag(
    id int primary key IDENTITY (1,1),
    name varchar(50) NOT NULL
)
GO
CREATE TABLE question(
    id int primary key IDENTITY (1,1),
    user_id int NOT NULL foreign key REFERENCES web_user(id),
    title varchar(50) NOT NULL,
    message varchar(max),
)
GO
CREATE TABLE answer(
    id int primary key IDENTITY (1,1),
    user_id int NOT NULL foreign key REFERENCES web_user(id),
    question_id int NOT NULL foreign key REFERENCES question(id),
    message varchar(max)
)
GO
CREATE TABLE question_tag(
    question_id int foreign key references question(id),
    tag_id int foreign key references tag(id)
)
GO
CREATE TABLE comment(
    id int primary key IDENTITY (1,1),
    user_id int NOT NULL FOREIGN KEY REFERENCES web_user(id),
    question_id int foreign key REFERENCES question(id),
    answer_id int foreign key REFERENCES answer(id),
    message varchar(max)
)
GO

CREATE TRIGGER [DELETE_USER]
    ON dbo.[web_user]
    INSTEAD OF DELETE
    AS
BEGIN
    SET NOCOUNT ON;
    DELETE FROM friendship WHERE web_user_id IN (SELECT id FROM DELETED);
    DELETE FROM friendship WHERE friend_id IN (SELECT id FROM DELETED);
    DELETE FROM answer WHERE user_id IN (SELECT id FROM DELETED);
    DELETE FROM question WHERE user_id IN (SELECT id FROM DELETED);
    DELETE FROM comment WHERE user_id IN (SELECT id FROM DELETED);
    DELETE FROM web_user WHERE id IN (SELECT id FROM DELETED);
END
GO
CREATE TRIGGER [DELETE_QUESTION]
    ON dbo.[question]
    INSTEAD OF DELETE
    AS
BEGIN
    SET NOCOUNT ON;
    DELETE FROM [answer] WHERE question_id IN (SELECT id FROM DELETED);
    DELETE FROM [comment] WHERE question_id IN (SELECT id FROM DELETED);
    DELETE FROM [question_tag] WHERE question_id IN (SELECT id FROM DELETED);
    DELETE FROM [question] WHERE id IN (SELECT id FROM DELETED);
END
GO
CREATE TRIGGER [DELETE_ANSWER]
    ON dbo.[answer]
    INSTEAD OF DELETE
    AS
BEGIN
    SET NOCOUNT ON;
    DELETE FROM [comment] WHERE answer_id IN (SELECT id FROM DELETED);
    DELETE FROM [comment] WHERE id IN (SELECT id FROM DELETED);
END
GO
CREATE TRIGGER [DELETE_TAG]
    ON dbo.[tag]
    INSTEAD OF DELETE
    AS
BEGIN
   SET NOCOUNT ON;
   DELETE FROM [question_tag] WHERE tag_id IN (SELECT id FROM DELETED);
   DELETE FROM [tag] WHERE id IN(SELECT id FROM DELETED);
END
