package com.school.interfaces;

import java.util.List;

import javax.sql.DataSource;

import com.school.objects.Student;
 

public interface StudentDAOInterface {

	public DataSource getDataSource() ;
	
	public void setDataSource(DataSource data);
	
	public void create(String name,Integer age,String email,byte [] image);
	
	public Student getStudent(Integer id); 	
	
	public Student getDBStudentByName(String name);
	
	public void updateStudent(Integer id, String name, Integer age,String email,byte [] image);
	
	public void deleteStudent(Integer id);

	public byte[] getStudentImage(Integer id);
	
	public void setStudentImage(Integer id,byte[] image);
	
	public List<Student> getAllStudents();
 
	public void executeBatchObjectUpdate(final List<Student> students);

	

	
	

}
