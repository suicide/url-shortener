##GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';

CREATE USER 'shortener'@'localhost' IDENTIFIED BY 'shortener';
CREATE USER 'shortener'@'%' IDENTIFIED BY 'shortener';
GRANT ALL PRIVILEGES ON *.* TO 'shortener'@'localhost';
GRANT ALL PRIVILEGES ON *.* TO 'shortener'@'%';

create database shortener;

create table shortener.shorturl
(
  hash varchar(255) not null
    primary key,
  url varchar(1024) null,
  constraint shorturl_url_uindex
  unique (url)
)
;

