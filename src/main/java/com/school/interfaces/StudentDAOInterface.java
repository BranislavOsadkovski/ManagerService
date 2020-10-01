package com.school.interfaces;

import java.util.List;

import javax.sql.DataSource;

import com.school.objects.Student;

/**
 * @author Branislav
 *
 */
public interface StudentDAOInterface<T> {

	/**
	 * Returns DataSource
	 * 
	 * @return dataSource
	 */
	public DataSource getDataSource();

	/**
	 * Set DataSource data
	 * 
	 * @param data
	 */
	public void setDataSource(DataSource data);

	/**
	 * Saves new Student record into database
	 * 
	 * @param name
	 * @param age
	 * @param email
	 * @param image
	 */
	public void create(Student student);

	/**
	 * Fetch Student record from database
	 * 
	 * @param id
	 * @return Student
	 */
	public T getStudent(Integer id);

	/**
	 * Fetch Student record by name from database
	 * 
	 * @param name
	 * @return Student
	 */
	public T getDBStudentByName(String name);

	/**
	 * Updates new values for Student record by passed @id value into database
	 * 
	 * @param id
	 * @param name
	 * @param age
	 * @param email
	 * @param image
	 */
	public void updateStudent(Student student);

	/**
	 * Deletes Student record from database by passed @id
	 * 
	 * @param id
	 */
	public void deleteStudent(Integer id);

	/**
	 * Returns image bytes from database from Student record by passed @id
	 * 
	 * @param id
	 * @return byte[]
	 */
	public byte[] getStudentImage(Integer id);

	/**
	 * Saves image bytes into database
	 * 
	 * @param id
	 * @param image
	 */
	public void setStudentImage(Integer id, byte[] image);

	/**
	 * Returns a list of all Student records from database
	 * 
	 * @return List<T>
	 */
	public List<T> getAllStudents();

	/**
	 * Executes a large update into database
	 * 
	 * @param students
	 */
	public void executeBatchObjectUpdate(final List<T> students);

}
