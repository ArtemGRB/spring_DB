select student.name, student.age, faculty.name from student inner join faculty
on student.student_id = faculty.id;

select student.name, student.age from student inner join avatar
on student.id = avatar.student_id;