--1. LOGIN to HR ACCOUNT
--2. RUN this CREATE TABLE
create table user_acct
(
    user_name VARCHAR(100) NOT NULL,
    email_address VARCHAR(255) NOT NULL UNIQUE
);

--3.  CREATE A sample INSERT:
insert into user_acct values ('John Wick', 'john.wick@yahoo.com'); commit;

--4. Select to test the sample data.
select * from hr.user_acct;

--5. Go back to Eclipse IDE, and proceed to test the Java application given 