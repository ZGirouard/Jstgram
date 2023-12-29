use username;
drop table if exists Post;
drop table if exists Visibility;
drop table if exists User;

-- User table
CREATE TABLE User (
    userId INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);

-- Post table
CREATE TABLE Post (
    postId INT AUTO_INCREMENT PRIMARY KEY,
    postText VARCHAR(50),
    postTime VARCHAR(50),
    author VARCHAR(50),
    FOREIGN KEY (author) REFERENCES User(username)
);

-- Visibility table
CREATE TABLE Visibility (
    visibilityID INT PRIMARY KEY AUTO_INCREMENT,
    userID INT,
    visibleToUserID INT,
    FOREIGN KEY (userID) REFERENCES User(userID)
);

-- Insert into User Table
insert into User values (0,'Alice','Alice123');
insert into User values (0,'Bob','Bob123');
insert into User values (0,'Crystal','Crystal123');
insert into User values (0,'David','David123');


-- Insert into Post table
insert into Post values (0, 'Project deadline extended?', '2023-12-10 19:00:00', 'Alice');
insert into Post values (0, 'Yep', '2023-10-12 19:01:00', 'Bob');
insert into Post values (0, 'Fall break', '2023-10-16 09:00:00', 'David');
insert into Post values (0, 'Lab due tonight?', '2023-10-27 23:30:00', 'Alice');
insert into Post values (0, "No it's due next week", '2023-10-27 23:35:00', 'Crystal');

-- Insert into UserVisibility Table
-- FIX
insert into Visibility values (1, 1, 1);  -- Alice can see Alice
insert into Visibility values (2, 1, 2);  -- Bob can see Alice
insert into Visibility values (3, 1, 3);  -- Crystal can see Alice
insert into Visibility values (4, 2, 1);  -- Alice can see Bob
insert into Visibility values (5, 2, 2);  -- Bob can see Bob
insert into Visibility values (6, 2, 3);  -- Crystal can see Bob
insert into Visibility values (7, 3, 1);  -- Alice can see Crystal
insert into Visibility values (8, 3, 3);  -- Crystal can see Crystal
insert into Visibility values (9, 4, 4);  -- David can see David

select * from Visibility;