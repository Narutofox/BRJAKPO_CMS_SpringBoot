INSERT INTO AppUser(ime, prezime,username,password,enabled)
VALUES ('Ivan','Cicek','icicek','pwd', true);
INSERT INTO AppUser(ime, prezime,username,password,enabled)
VALUES ('Marko','Markic','mmarkic','pwd', true);
INSERT INTO AppUser(ime, prezime,username,password,enabled)
VALUES ('Ivo','Ivic','iivic','pwd', true);



INSERT INTO UserRole (username, role)
VALUES ('mmarkic', 'ROLE_USER');

INSERT INTO UserRole (username, role)
VALUES ('iivic', 'ROLE_USER');

INSERT INTO UserRole (username, role)
VALUES ('icicek', 'ROLE_ADMIN');

INSERT INTO Menus (menuName, parentId)
VALUES ('Main1', null);

INSERT INTO Menus (menuName, parentId)
VALUES ('Main2', null);

INSERT INTO Menus (menuName, parentId)
VALUES ('Main3', null);

INSERT INTO Menus (menuName, parentId)
VALUES ('SubMain1-1', 1);

INSERT INTO Menus (menuName, parentId)
VALUES ('SubMain1-2', 1);

INSERT INTO Menus (menuName, parentId)
VALUES ('SubMain2-1', 4);

INSERT INTO Menus (menuName, parentId)
VALUES ('SubMain3-1', 5);