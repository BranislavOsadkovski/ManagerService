package com.school.interfaces;

import java.util.List;

import javax.sql.DataSource;
 

/**
 * @author Branislav
 *
 */
public interface DAO<T> {

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
	 * Saves new Object record into database
	 * 
	 * @param name
	 * @param age
	 * @param email
	 * @param image
	 */
	public void create(Ocupation ocupation);

	/**
	 * Fetch Object record from database
	 * 
	 * @param id
	 * @return Student
	 */
	public T getById(Integer id);

	/**
	 * Fetch Object record by name from database
	 * 
	 * @param name
	 * @return Student
	 */
	public T getByName(String name);

	/**
	 * Updates new values for Student record by passed @id value into database
	 * 
	 * @param id
	 * @param name
	 * @param age
	 * @param email
	 * @param image
	 */
	public void update(Ocupation pcupation);

	/**
	 * Deletes Student record from database by passed @id
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * Returns image bytes from database from Student record by passed @id
	 * 
	 * @param id
	 * @return byte[]
	 */
	public byte[] getImage(Integer id);

	/**
	 * Saves image bytes into database
	 * 
	 * @param id
	 * @param image
	 */
	public void setImage(Integer id, byte[] image);

	/**
	 * Returns a list of all Student records from database
	 * 
	 * @return List<T>
	 */
	public List<T> getAllRecords();

	/**
	 * Executes a large update into database
	 * 
	 * @param students
	 */
	public void executeBatchUpdate(final List<T> students);

}
