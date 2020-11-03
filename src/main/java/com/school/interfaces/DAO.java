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
	 * Saves new @T record into database 
	 */
	public void create(Ocupation ocupation);

	/**
	 * Fetch @T record from database
	 * 
	 * @param id
	 * @return T
	 */
	public T getById(Integer id);

	/**
	 * Fetch @T record by name from database
	 * 
	 * @param name
	 * @return @T
	 */
	public T getByName(String name);

	/**
	 * Updates record
	 */
	public void update(Ocupation pcupation);

	/**
	 * Deletes @T record from database by passed @id
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * Returns image bytes from database from @T record by passed @id
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
	 * Returns a list of all @T records from database
	 * 
	 * @return List<T>
	 */
	public List<T> getAllRecords();

	/**
	 * Executes a large update into database
	 * 
	 * @param update
	 */
	public void executeBatchUpdate(final List<T> update);

}
