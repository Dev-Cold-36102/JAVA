create database lab3;
use lab3;
create table Class(
classID int not null,
classname nvarchar(255) not null,
startdate date not null,
statuss bit,
constraint classid_pk primary key(classID)
);
alter table class
modify startdate datetime;
drop database lab3;
drop table Class;

create table student(
studentID int not null,
studentname nvarchar(30) not null,
address nvarchar(50),
phone varchar(20),
statuss bit,
classID int not null,
constraint studentID_pk primary key(studentID)
);

create table subject(
subID int not null,
subname nvarchar(30),
credit tinyint not null default 1 check(credit>=1),
constraint subID_pk primary key(subID) 
);

alter table subject
add status bit default 1;

drop table ssubject;
create table mark(
markID int not null,
subID int,
studentID int,
mark float default 0 check (mark between 0 and 100),
examtimes tinyint default 1
);
drop table mark;
/*3.
a.*/
alter table student
add constraint classID_fk foreign key (classID) references class(classID);

/*b.*/
Alter table class
Add check(startdate = current_date());

/*c.*/
alter table student
alter statuss set default 1;

/*d.*/
alter table mark
add constraint subID_fk foreign key (subID) references subject(subID),
add constraint studentID_fk foreign key(studentID) references student(studentID);

/*4 them du lieu vao bang*/
insert class values 
(1,'A1','2008/12/20',1),
(2,'A2','2008/12/22',1),
(3,'B3',current_date(),0);
select * from class;

insert student values
(1,'Hung','Ha Noi',09654836485,1,1),
(2,'Hoa','Hai Phong',094658264,1,1),
(3,'Manh','HCM',056284658,0,2);

insert subject values
(1,'CF',5,1),
(2,'C',6,1),
(3,'HDJ',5,1),
(4,'RDBMS',10,1);

insert mark values
(1,1,1,8,1),
(2,1,2,10,2),
(3,2,1,12,1);

/*5.*/
/*a.*/
set SQL_SAFE_UPDATES=0;
update student
set classID=2 
where studentname='Hung';

/*b*/
update student
set phone='No phone'
where phone=null;
/*c.*/
update class set classname=concat('New',classname) where statuss=0;

/*d*/
update class 
set classname=replace(classname,'New','old')
where classname like('New%');

/*e*/
update class
set statuss=0
where classID not in(select classID from student);

/*f*/
update subject
set status=0
where subID not in(select subID from mark);

/*6*/
/*a.*/
select studentname as Name
from student
where studentname like('h%');

/*b.*/
select * from class 
where extract(month from startdate)=12;

/*c.*/
select max(credit) as max
from subject;

/*d*/
select * from subject
where Credit = (Select MAX(credit) from subject);

/*e*/
select *from subject
having credit between 3 and 5;

/*f*/
select class.classID, class.className,student.studentname,student.address
from class  join student
Student ON Class.ClassID = Student.ClassID;

/*g.*/
select * from subject
where subID not in (select subID from mark);

/*h.*/
select * from subject
where subID in (select subID 
                from mark 
                where mark.mark= (select max(mark) from mark));

/*i*/
 select student.studentID, studentname,address,phone,statuss,classID, avg(mark) as 'diem trung binh' 
 from student left join mark
 on student.studentID=mark.studentId
 group by student.studentID;

/*j*/
 select student.studentID, studentname,address,phone,statuss,classID, avg(mark) as 'diem trung binh',rank() 
 over (partition by studentID order by avg(mark )  DESC  ) as 'xep hang'
 from student left join mark
 on student.studentID=mark.studentId
 group by student.studentID;
 
 /*k*/
 select student.studentID, studentname,address,phone,statuss,classID, avg(mark) as 'diem trung binh' 
 from student left join mark
 on student.studentID=mark.studentId
 group by student.studentID
 having avg(mark)>10;
 
 /*l.*/
 select student.studentname, subname,mark
 from student join mark
 on student.studentID= mark.studentID
 join subject on mark.subID =subject.subID
 order by mark.mark DESC, student.studentname asc;
 
 /*7.*/
 /*a.*/
 delete from class 
 where statuss=0;
 
 /*b.*/
 delete from subject
 where subID not in (select subID from mark);
 
 /*8*/
 /*a.*/
 alter table mark
 drop column examtimes;
 
 /*b*/
 alter table class
 change statuss ClassStatus bit;
 
 /*c*/
 rename table mark to SubjectTest;
 
 /*d*/
 use Master;
 
 /*e*/
 drop database lab3;
 
 
 



