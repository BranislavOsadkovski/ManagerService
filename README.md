# StudentManager
**Java EE web app implementing Jersey JAX-RS RESTful API and Spring Data JDBC for data management repository through Spring Core Dependency Injection**

* JDBC is a low level standard for interaction with databases. Spring Data JDBC has less abstractions than Spring Data JPA, but uses Spring Data concepts to make it easier to do CRUD operations. JDBC allows you to do more things with the Database directly, but it requires more attention.

* The idea behind Spring Data JDBC is to provide access to relational databases without submitting to the complexities of JPA. JPA offers such features as lazy loading, caching, and dirty tracking. While these are great if you need them, they can actually make thinking about JPA and its behavior harder than it has to be.

* Spring Data JDBC aims at a much simpler model. There won’t be caching, dirty tracking, or lazy loading. Instead, SQL statements are issued when and only when you invoke a repository method. The object returned as result of that method is fully loaded before the method returns. There is no "session" and no proxies for entities. All this should make Spring Data JDBC easier to reason about.

* When you query the application using Spring Data JDBC, instead of entities, you will receive the entire aggregate. This makes the application easier to understand. The application doesn’t need to rely on an application context to get the state of properties of returned entities. Because the entire objects are fetched, there are no extra calls needed to receive the field values of objects that were not loaded yet because all the fields are already filled in. The disadvantage of this system could be that too much data will be loaded. But if that happens, it could be that the boundary of your aggregate is too big and it is possible that you need to split up your aggregate.

- Because Spring Data JDBC does not contain a persistence context like Spring Data JPA, it does not know which part of the aggregate is updated. Therefore it will update the aggregate root and delete all the referenced entities and save them all again. As a downside, entities will sometimes be deleted and inserted even if they were not updated, which could be a waste of resources. The big advantage is that you are sure that the entire entity will be up to date after saving the aggregate.

Spring Data JDBC does not have a persistence context like Spring Data JPA. This makes Spring Data JDBC in my opinion more straightforward than Spring Data JPA. If you want to make changes to the data, you are responsible for handling the persistence.

## REST API system for students data management
through exposed public HTTP methods

* POST - StudentService/newstudent -creates new student
* GET - StudentService/student/{id} -returns student with the requsted id
* GET - StudentService/student/{name} -returns student with the same name
* PUT - StudentService/student - updates student record with new values
* DELETE -StudentService/student -deletes student
* GET - StudentService/{id}/studentimage - returns student image from database
* PUT - StudentService/studentimage - creates new or updates student image
* GET - StudentService/students - returns list of all students
* PUT - StudentService/students - executes a large batch of data updates
