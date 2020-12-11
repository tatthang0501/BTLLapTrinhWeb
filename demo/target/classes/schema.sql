-- drop if exists database dormdb;
create database dormdb CHARACTER SET utf8 COLLATE utf8_unicode_ci;
use dormdb;

select * from tblRoom;

create table tblRoom(
	id int primary key auto_increment,
    roomNumber varchar(50),
    `type` varchar(50),
    price float,
    amountPeople int
);

create table tblStudent(
	id int primary key auto_increment,
    studentID varchar(50),
    studentName varchar(50),
    idCard varchar(50),
    dob date,
    classroom varchar(50),
    studentAddress varchar(50),
    roomid int,
    foreign key (roomid) references tblroom(id)
);

create table tblGuest(
	id int primary key auto_increment,
    idCard varchar(20),
    `name` varchar(20),
    dob date,
    studentid int,
    foreign key (studentid) references tblstudent(id)
);

create table tblService(
	id int primary key auto_increment,
    `name` varchar(50),
    price float,
    `time` float
);

create table tblStudentService(
	id int primary key auto_increment,
    quantity int,
    studentid int,
    serviceid int,
    foreign key (serviceid) references tblService(id),
    foreign key (studentid) references tblStudent(id)
);

create table tblMotorbike(
	id int primary key auto_increment,
    motorbikeName varchar(50),
    price float,
    licensePlates varchar(50),
    studentid int,
    foreign key (studentid) references tblStudent(id)
);
drop table tblMotorbike;
select * from tblRoom;

create table tblCheckin(
	id int primary key auto_increment,
    `time` datetime,
    motorBikeid int,
    foreign key (motorBikeid) references tblMotorbike(id)
);

create table tblCheckout(
	id int primary key auto_increment,
    `time` datetime,
    checkinid int,
    foreign key (checkinid) references tblCheckin(id)
);

create table tblMonthlyTicket(
	id int primary key auto_increment,
	studentid int,
    `month` int,
    motorBikeid int,
    foreign key (motorBikeid) references tblMotorBike(id),
    foreign key (studentid) references tblStudent(id)
);

-- insert into tblRoom(roomNumber, `type`, price, amountPeople)
-- values('105', 'phong thuong', 500000, 6),
-- ('106', 'phong thuong', 300000, 8),
-- ('107', 'phong thuong', 700000, 4),
-- ('201', 'phong vip', 1000000, 8),
-- ('202', 'phong vip', 2000000, 6),
-- ('203', 'phong vip', 3000000, 4),
-- ('304', 'phong thuong', 800000, 6),
-- ('305', 'phong thuong', 900000, 4),
-- ('306', 'phong thuong', 500000, 6);

-- insert into tblStudent(studentID, studentName, idCard, dob, classroom, studentAddress, roomid)
-- values('B17DCCN335', 'Hoàng Tăng Khải', '123456789', 19990128,'CNPM05', 'Hà Nội', 1),
-- ('B17DCCN359', 'Phạm Trung Kiên', '123456987', 19990127,'CNPM05', 'Quảng Ninh', 1),
-- ('B17DCCN563', 'Nguyễn Tất Thắng', '123458769', 19990126,'CNPM05', 'Thái Bình', 2),
-- ('B17DCCN335', 'Hoàng Minh Tâm', '123476589', 19990125,'CNPM05', 'Hà Nội', 2),
-- ('B17DCCN335', 'Bùi Xuân Quang', '125436789', 19990124,'CNPM05', 'Thái Bình', 3);


-- insert into tblGuest(idCard, `name`, dob, studentid)
-- values('123459768', 'Nguyễn Quang Huy', 19991208, 1),
-- ('123459687', 'Nguyễn Ngọc Tuấn', 19990819, 2),
-- ('123453454', 'Nguyễn Đức Thủy', 19950819, 3),
-- ('123454325', 'Nguyễn Tuấn Nghĩa', 19990615, 4),
-- ('123475674', 'Hoàng Tùng Lâm', 20000820, 2),
-- ('543453214', 'Hoàng Hải An', 20010919, 1),
-- ('543459687', 'Nguyễn Ngọc Ánh', 19990819, 3),
-- ('876459768', 'Quách Gia Huy', 19990708, 5);

-- insert into tblService(`name`, price, `time`)
-- values('Trông xe', 3000, 8),
-- ('Thuê xe', 100000, 24),
-- ('Giặt là', 200000, 2),
-- ('Thuê phòng ăn', 50000, 2);

-- insert into tblMotorBike(licensePlates, studentid)
-- values('29Y5 12345', 1),
-- ('14Y5 03739', 2),
-- ('17Y5 12134', 3),
-- ('29Z6 7481', 4),
-- ('17Y5 84353', 5);


-- insert into tblStudentService(quantity, studentid, serviceid)
-- values(1, 1, 1),
-- (2, 2, 2),
-- (1, 3, 2),
-- (1, 1, 3),
-- (1, 4, 4);

-- insert into tblMonthlyTicket(studentid, `month`, motorBikeid)
-- values(1, 11, 1),
-- (2, 11, 2),
-- (3, 12, 3),
-- (4, 12, 4);

-- insert into tblCheckin(motorBikeid, `time`)
-- values(1, 20201119080000),
-- (2, 20201111070000),
-- (3, 20201119090000),
-- (4, 20201116100000),
-- (5, 20201113160000);

-- insert into tblCheckout(checkinid, `time`)
-- values(2, 20201117070000),
-- (4, 20201119080000),
-- (5, 20201114070000);

-- select * from tblmonthlyticket;
-- select * from tblcheckin;