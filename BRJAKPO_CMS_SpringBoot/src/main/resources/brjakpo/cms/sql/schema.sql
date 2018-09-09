drop table if exists user;
drop table if exists user_role;
--SET COLLATION utf8mb4_unicode_ci;

CREATE TABLE AppUser (
  id INT(11) IDENTITY PRIMARY KEY,
  ime NVARCHAR(50) NOT NULL,
  prezime NVARCHAR(50) NOT NULL,
  username NVARCHAR(45),
  password NVARCHAR(45) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
);

CREATE TABLE UserRole (
  userRoleId int(11) IDENTITY PRIMARY KEY,
  username NVARCHAR(45) NOT NULL,
  role NVARCHAR(45) NOT NULL,
  FOREIGN KEY (username) REFERENCES AppUser (username)
);

CREATE TABLE Menus (
  menuId int(11) IDENTITY PRIMARY KEY,
  menuName NVARCHAR(50) NOT NULL,
  parentId int NULL
);

CREATE TABLE Pages (
  pageId int(11) IDENTITY PRIMARY KEY,
  title NVARCHAR(50) NOT NULL,
  content NVARCHAR(MAX) NOT NULL,
  menuId int NOT NULL,
  allowComments bit NOT NULL DEFAULT(0),
  FOREIGN KEY (menuId) REFERENCES Menus (menuId),
);


CREATE TABLE Posts (
  postId int(11) IDENTITY PRIMARY KEY,
  pageId int NOT NULL,
  userId int NOT NULL,
  content NVARCHAR(MAX) NOT NULL,
  inputDate TIMESTAMP NOT NULL DEFAULT(CURRENT_TIMESTAMP()), 
  FOREIGN KEY (userId) REFERENCES AppUser (id),
  FOREIGN KEY (pageId) REFERENCES Pages (pageId)
);
/*
WITH RECURSIVE MyTree AS (
    SELECT * FROM Menus WHERE ParentId IS NULL
    UNION ALL
    SELECT m.* FROM ParentId AS m JOIN MyTree AS t ON m.ParentId = t.Id
)
SELECT * FROM MyTree;*/