package com.school.interfaces;

import java.util.List;

import javax.sql.DataSource;

import com.school.objects.Student;
 

public interface StudentDAOInterface {

	public void setDataSource(DataSource data);
	
	public void create(String name,Integer age,String email,byte [] image);
	
	public List<Student> getAllStudents();
	
	public void updateStudent(Integer id,String name,Integer age);
	
	public void deleteStudent(Integer id);
	
	public Student getStudent(Integer id);

	public String getStudentName(Integer id);
	
	public void setStudentImage(Integer id,byte[] image);

	public void executeBatchUpdate(final List<Student> students);

	public void executeBatchObjectUpdate(final List<Student> students);
	

}
