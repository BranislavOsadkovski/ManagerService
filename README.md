# StudentManager
** jersey REST API with Spring Core DI and Spring JDBC data repository **

JDBC is a low level standard for interaction with databases. Spring Data JDBC has less abstractions than Spring Data JPA, but uses Spring Data concepts to make it easier to do CRUD operations. JDBC allows you to do more things with the Database directly, but it requires more attention.

REST API system for storing students data
name,
age, 
email,
image,

## Exposed public HTTP methods
* POST - StudentService/newstudent -creates new student
* GET - StudentService/student/{id} -returns student with the same id
* GET - StudentService/student/{name} -returns student with the same name
* PUT - StudentService/student - updates student record with new values
* DELETE -StudentService/student -deletes student
* GET - StudentService/{id}/studentimage - returns student image from database
* PUT - StudentService/studentimage - creates new or updates student image
* GET - StudentService/students - returns list of all students
* PUT - StudentService/students - insert a large batch of updates received by XML file




