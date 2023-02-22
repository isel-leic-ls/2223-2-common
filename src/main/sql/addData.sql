insert into courses(name) values ('LEIC');
insert into students(course, number, name) values (1, 12345, 'Alice');
insert into students(course, number, name) select cid as course, 12346 as number, 'Bob' as name from courses where name = 'LEIC'