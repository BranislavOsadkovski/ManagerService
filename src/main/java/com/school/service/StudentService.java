/**
 * Copyright the original author or authors.
 */
package com.school.service;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.school.factories.AbstractFactory;
import com.school.factories.OcupationFactory;
import com.school.objects.ImageImpl;
import com.school.objects.Ocupation;
import com.school.objects.Student;
import com.school.proxyimage.ProxyImage;
import com.school.util.StudentJDBCTemplate;
import com.school.validations.OcupationType;
import com.school.validations.StudentException;
import com.school.validations.StudentValidator;

/**
 * @author Branislav
 *
 */
public class StudentService {

	final static Logger logger = Logger.getLogger(StudentService.class);
	private StudentJDBCTemplate studentTemplate;
	private Student student;
	private final AbstractFactory factory = new OcupationFactory();

	/**
	 * @param studentTemplate the studentTemplate to set
	 */
	@Autowired(required = true)
	public StudentService(StudentJDBCTemplate studentTemplate) {
		this.studentTemplate = studentTemplate;
	}

	/**
	 * Validates parameters from the Request if invalid throws StudentException.
	 * Creates Student object and saves it into database. Returns true if operation
	 * is successful.
	 * 
	 * @param name
	 * @param age
	 * @param email
	 * @param stream
	 * @return boolean
	 */
	public boolean createStudent(String name, String age, String email, byte[] imageBytes) {
		try {
			Ocupation student = (Student) factory.getOcupation(OcupationType.STUDENT);

			if (StudentValidator.validateStudent(name, age, email)) {
				student.setName(name);
				student.setAge(Integer.valueOf(age));
				student.setEmail(email);
				student.setImage(imageBytes);
				studentTemplate.create(student);
				return true;
			}

		} catch (StudentException se) {
			logger.error(se.getMessage(), se);
		} catch (Exception se) {
			logger.error(se.getMessage(), se);
		}
		return false;
	}

	/**
	 * Validates parameters from the Request if invalid throws StudentException.
	 * Returns Student record from database.
	 * 
	 * @param id
	 * @return Student
	 */
	public Student getStudent(String id) {
		try {
			if (StudentValidator.validateId(id)) {
				student = studentTemplate.getById(Integer.valueOf(id));
			}
		} catch (StudentException se) {
			logger.error(se.getMessage(), se);

		}
		return student;
	}

	/**
	 * Validates parameters if invalid throws StudentException. Returns Student
	 * record from database.
	 * 
	 * @param name
	 * @return Student
	 */
	public Student getStudentByName(String name) {

		try {
			if (StudentValidator.validatePathName(name)) {
				student = studentTemplate.getByName(name);
			}
		} catch (StudentException e) {
			logger.error(e.getMessage(), e);
		}
		return student;
	}

	/**
	 * Validates parameters from the Request if invalid throws StudentException.
	 * Updates Student record in database.
	 * 
	 * @param id
	 * @param name
	 * @param age
	 * @param email
	 * @param stream
	 */
	public void updateStudent(String id, String name, String age, String email) {

		Ocupation student = factory.getOcupation(OcupationType.STUDENT);
		try {
			if (StudentValidator.validateStudent(id, name, age, email)) {
				student.setId(Integer.valueOf(id));
				student.setAge(Integer.valueOf(age));
				student.setEmail(email);
				student.setName(name);

				studentTemplate.update(student);
			}
		} catch (StudentException se) {
			logger.error(se.getMessage(), se);
		}

	}

	/**
	 * 
	 * Validates parameters if invalid throws StudentException. Deletes Student
	 * record from database.
	 * 
	 * @param id
	 * @return Response
	 */
	public void deleteStudent(String id) {
		try {
			if (StudentValidator.validateId(id)) {

				studentTemplate.delete(Integer.valueOf(id));
			}
		} catch (NumberFormatException e) {
			logger.error(e.getMessage(), e);
		} catch (StudentException e) {
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * Validates parameters if invalid throws StudentException. Returns image record
	 * from database.
	 * 
	 * @param id
	 * @return
	 * @throws StudentException
	 */
	public byte[] getStudentImage(String id) {

		try {
			if (StudentValidator.validateId(id)) {
				ImageImpl img = ProxyImage.getProxyImage(Integer.valueOf(id), studentTemplate);
				return img.getImageBytes();
			}

		} catch (StudentException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Validates parameters from the Request if invalid throws StudentException.
	 * Updates image record in database.
	 * 
	 * @param id
	 * @param stream
	 * 
	 */
	public void setImage(String id, byte[] imageBytes) {
		try {

			if (StudentValidator.validateId(id)) {
				if (imageBytes.length > 0) {
					studentTemplate.setImage(Integer.valueOf(id), imageBytes);
				} else {
					throw new NullPointerException("No image found;");
				}
			}
		} catch (StudentException se) {
			logger.error(se.getMessage(), se);
		} catch (NullPointerException npe) {
			logger.error(npe.getMessage(), npe);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * Validates parameters from the Request if invalid throws StudentException.
	 * Inserts a large list of records as batch update into database
	 * 
	 * 
	 * @param batch
	 * 
	 */
	public void batchUpdate(List<Student> batch) {
		try {
			if (batch.size() == 0) {
				throw new IllegalArgumentException("Batch update can not be empty");

			}

			List<Student> students = batch;
			Assert.notNull(students, "Batch update can not be null");
			for (Student s : students) {
				try {
					StudentValidator.validateStudent(String.valueOf(s.getId()), s.getName(), String.valueOf(s.getAge()),
							s.getEmail());
				} catch (StudentException e) {
					logger.error(e.getMessage(), e);
				}
			}

			studentTemplate.executeBatchUpdate(students);

		} catch (IllegalArgumentException e) {

			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * Returns a List of Student records from database. Fetch a list of all Student
	 * records from database in xml form.
	 * 
	 * @return list
	 */
	public List<Student> getAllRecords() {
		List<Student> list = null;
		try {
			list = studentTemplate.getAllRecords();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return list;
	}

	/**
	 * Receives InputStream byte stream and writes as characters into StringBuilder
	 * and returns as byte array
	 * 
	 * @param inputStream
	 * @return array
	 */
	public byte[] imageBytes(InputStream inputStream) {
		StringBuffer img = new StringBuffer();

		try (BufferedInputStream bos = new BufferedInputStream(inputStream)) {

			int content = bos.read();
			while (content != -1) {
				img.append((char) content);
				content = bos.read();
			}

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		byte[] array = img.toString().getBytes();
		if (array.length < 1) {
			array = null;
		}
		return array;
	}
}
