DROP TABLE IF EXISTS web_user_friendships;
DROP TABLE IF EXISTS friendship;
DROP TABLE IF EXISTS post_tags;
DROP TABLE IF EXISTS answer;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS web_user;
GO

create table answer (id bigint not null, image_url varchar(255), message varchar(255), post_id bigint, user_username varchar(255), primary key (id));
create table friendship (id bigint not null, status int, friend_username varchar(255), primary key (id));
create table post (id bigint not null, image_url varchar(255), message varchar(255), title varchar(255), user_username varchar(255), primary key (id));
create table post_tags (post_id bigint not null, tags_id bigint not null, primary key (post_id, tags_id));
create table tag (id bigint not null, name varchar(255), primary key (id));
create table web_user (username varchar(255) not null, email varchar(255), password varchar(60), reputation integer default 0, primary key (username));
create table web_user_friendships (web_user_username varchar(255) not null, friendships_id bigint not null, primary key (web_user_username, friendships_id));
alter table post_tags add constraint UK_gkrti9gmprcnjqtudiqjad5db unique (tags_id);
alter table web_user add constraint UK_kuqpi3q2x3r3e9gukuvnm6fho unique (email);
alter table web_user_friendships add constraint UK_ndaq3dwv3sko7lkr0sy0mocuj unique (friendships_id);
alter table answer add constraint FKgbthr3g8nq17pjpwrnd3592x foreign key (post_id) references post;
alter table answer add constraint FKhuyiu7vhiudcltw6vbenpld2m foreign key (user_username) references web_user;
alter table friendship add constraint FK90etjqltx10poau78hrg8dw28 foreign key (friend_username) references web_user;
alter table post add constraint FKijhhi7c1a6qyt8ktqebv5niwt foreign key (user_username) references web_user;
alter table post_tags add constraint FKpoyg307ed2w6nbcthawvphds4 foreign key (tags_id) references tag;
alter table post_tags add constraint FKmmtgl185ka210lj8kenewllt1 foreign key (post_id) references post;
alter table web_user_friendships add constraint FK95q0lx5pddcfgj3r6wkyo839e foreign key (friendships_id) references friendship;
alter table web_user_friendships add constraint FK4a5lhkpla7sbljhij4hugxm9n foreign key (web_user_username) references web_user;
