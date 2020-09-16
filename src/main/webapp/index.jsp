 
<html>
<body>
<h2>Hello World!</h2>
<!-- <img src="http://localhost:8080/school/rest/StudentService/2/studentImage" alt="hell"/> -->
<form enctype="multipart/form-data" action="http://localhost:8080/school/rest/StudentService/newstudent" method="POST">
		<input type="text" name="id">
			<input type="text" name="name">
	<!-- 	<input type="file" name="image"> -->

	<input type="submit" value="submit">
</form>

</body>
</html>
