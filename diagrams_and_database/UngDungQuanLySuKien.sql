CREATE TABLE UserInfor(
user_id INT NOT NULL Primary Key,
username VARCHAR(30) NOT NULL,
password VARCHAR(50) NOT NULL,
first_name VARCHAR(50) NOT NULL,
last_name VARCHAR(50) NOT NULL,
phone_number INT);


CREATE TABLE Event(
event_id INT NOT NULL Primary Key,
event_name VARCHAR(20) NOT NULL,
location VARCHAR(30) NOT NULL,
start_date DATETIME NOT NULL,
end_date DATETIME NOT NULL,
type VARCHAR(10) NOT NULL CHECK (type IN ('public', 'private')),
organizer_id INT NOT NULL references UserInfor(user_id),
description VARCHAR(50) 
);


CREATE TABLE Invitation(
sender_id INT REFERENCES UserInfor(user_id),
event_id INT REFERENCES Event(event_id),
recipient_id INT REFERENCES UserInfor(user_id)
);


CREATE TABLE Request(
sender_id INT REFERENCES UserInfor(user_id),
event_id INT REFERENCES Event(event_id));
