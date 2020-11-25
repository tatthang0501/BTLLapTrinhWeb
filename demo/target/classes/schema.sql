create table if not exists tblphong(
	id int primary key auto_increment,
    sophong varchar(50),
    loaiphong varchar(50),
    dongia float,
    songuoi int
);

-- create table if not exists  tblsinhvien(
-- 	id int primary key auto_increment,
--     masv varchar(50),
--     ten varchar(50),
--     socmt varchar(50),
--     ngaysinh date,
--     lop varchar(50),
--     quequan varchar(50),
--     phongid int,
--     foreign key (phongid) references tblphong(id)
-- );

create table if not exists tbl_Student(
	id int primary key auto_increment,
    studentID varchar(50),
    student_Name varchar(50),
    id_Card varchar(50),
    DOB varchar(10),
    classroom varchar(50),
    student_Address varchar(50)
);
-- select * from tbl_Student;


create table  if not exists tblkhach(
	id int primary key auto_increment,
    socmt varchar(20),
    ten varchar(20),
    ngaysinh date,
    sinhvienid int,
    foreign key (sinhvienid) references tbl_Student(id)
);

create table  if not exists tbldichvu(
	id int primary key auto_increment,
    ten varchar(50),
    dongia float,
    thoigian float
);

create table  if not exists tblStudentdichvu(
	id int primary key auto_increment,
    soluong int,
    sinhvienid int,
    dichvuid int,
    foreign key (dichvuid) references tbldichvu(id),
    foreign key (sinhvienid) references tbl_Student(id)
);

create table if not exists  tblxe(
	id int primary key auto_increment,
    loaixe varchar(50),
    giatri float,
    bienso varchar(50),
    sinhvienid int,
    foreign key (sinhvienid) references tbl_Student(id)
);

create table if not exists  tblcheckin(
	id int primary key auto_increment,
    thoigian datetime,
    xeid int,
    foreign key (xeid) references tblxe(id)
);

create table if not exists  tblcheckout(
	id int primary key auto_increment,
    thoigian datetime,
    checkinid int,
    foreign key (checkinid) references tblcheckin(id)
);

create table if not exists  tblmonthly(
	id int primary key auto_increment,
    sinhvienid int,
    thang int,
    xeid int,
    foreign key (xeid) references tblxe(id),
    foreign key (sinhvienid) references tbl_Student(id)
);

ALTER TABLE tblphong AUTO_INCREMENT = 1;
ALTER TABLE tbl_Student AUTO_INCREMENT = 1;
ALTER TABLE tblkhach AUTO_INCREMENT = 1;
ALTER TABLE tblStudentdichvu AUTO_INCREMENT = 1;
ALTER TABLE tblxe AUTO_INCREMENT = 1;
ALTER TABLE tblcheckin AUTO_INCREMENT = 1;
ALTER TABLE tblcheckout AUTO_INCREMENT = 1;
ALTER TABLE tblmonthly AUTO_INCREMENT = 1;
ALTER TABLE tbldichvu AUTO_INCREMENT = 1;




    