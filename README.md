# StudentManager
jax.rs jersey RESTful API with Spring Core and Spring JDBC data repository
implementing Spring Data JDBC template for persisting data

REST API system for storing data about students
name,
age, 
email,
image,

## Exposed service HTTP methods
* POST - StudentService/newstudent -creates new student
* GET - StudentService/student/{id} -returns student with the same id
* GET - StudentService/student/{name} -returns student with the same name
* PUT - StudentService/student - updates student record with new values
* DELETE -StudentService/student -deletes student
* GET - StudentService/{id}/studentimage - returns student image from database
* PUT - StudentService/studentimage - creates new or updates student image
* GET - StudentService/students - returns list of all students
* PUT - StudentService/students - insert a large batch of updates received by XML file




