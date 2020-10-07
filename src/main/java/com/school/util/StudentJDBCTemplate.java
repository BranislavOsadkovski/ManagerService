package com.school.util;

import java.io.ByteArrayInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate; 
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.util.Assert;
 
import com.school.interfaces.Ocupation;
import com.school.interfaces.StudentDAOInterface;
import com.school.objects.Student;

/**
 * StudentJDBCTemplate is Data Repository class that is used to communicate with
 * database.
 * 
 * @author Branislav
 *
 */
public class StudentJDBCTemplate implements StudentDAOInterface<Student> {
	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;
	SimpleJdbcInsert jdbcInsert; // multi-threaded reusable object providing easy insert capabilities for a table
	final static Logger logger = Logger.getLogger(StudentJDBCTemplate.class);

	/**
	 * Set DataSource data
	 * 
	 * @param data
	 */
	@Override
	@Autowired(required = true)
	public void setDataSource(DataSource data) {
		this.dataSource = data;
		this.jdbcTemplate = new JdbcTemplate(data);
		this.jdbcInsert = new SimpleJdbcInsert(data).withTableName("student");
	}

	/**
	 * Returns DataSource
	 * 
	 * @return dataSource
	 */
	@Override
	public DataSource getDataSource() {
		return this.dataSource;
	}

	/**
	 * Saves new Student record into database
	 * 
	 * @param name
	 * @param age
	 * @param email
	 * @param image
	 */
	@Override
	public void create(Ocupation student) {
		Assert.notNull(student, "Student object can not be null");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", student.getName());
		parameters.put("age", student.getAge());
		parameters.put("email", student.getEmail());
		parameters.put("image", student.getImage());
		try {
			jdbcInsert.execute(parameters);
			logger.info("Saved new record into database");

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

	}

	/**
	 * Fetch Student record from database
	 * 
	 * @param id
	 * @return Student
	 */
	@Override
	public Student getStudent(Integer id) {
		Assert.notNull(id, "Id can not be null");
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("getRecord");
		SqlParameterSource in = new MapSqlParameterSource().addValue("in_id", id);
		Map<String, Object> out = null;
		Student student = new Student();
		try {
			out = jdbcCall.execute(in);
			student.setId(id);
			student.setName((String) out.get("out_name"));
			student.setAge((Integer) out.get("out_age"));
			student.setEmail((String) out.get("out_email"));
			student.setImage((byte[]) out.get("out_image"));
			logger.info("Found record from database by id");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			student = null;
		}
		return student;
	}

	/**
	 * Fetch Student record by name from database
	 * 
	 * @param name
	 * @return Student
	 */
	@Override
	public Student getDBStudentByName(String name) {
		Assert.notNull(name, "name can not be null");
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("getRecordByName");
		SqlParameterSource in = new MapSqlParameterSource().addValue("in_name", name);
		Map<String, Object> out = null;
		Student student = new Student();
		try {
			out = jdbcCall.execute(in);

			student.setId((Integer) out.get("out_id"));
			student.setName(name);
			student.setAge((Integer) out.get("out_age"));
			student.setEmail((String) out.get("out_email"));
			student.setImage((byte[]) out.get("out_image"));
			logger.info("Found record from database by name");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			student = null;
		}

		return student;

	}

	/**
	 * Updates new values for Student record by passed @id value into database
	 * 
	 * @param id
	 * @param name
	 * @param age
	 * @param email
	 * @param image
	 */
	@Override
	public void updateStudent(Ocupation student) {
		Assert.notNull(student, "Student object can not be null");
		String SQL = "update student set name=?,age=?,email=?,image=? where id = ?";
		try {
			jdbcTemplate.update(SQL, student.getName(), student.getAge(), student.getEmail(), student.getImage(),
					student.getId());
			logger.info("Updated record in dabatase");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

	}

	/**
	 * Deletes Student record from database by passed @id
	 * 
	 * @param id
	 */
	@Override
	public void deleteStudent(Integer id) {
		Assert.notNull(id, "id can not be null");
		String SQL = "delete from student where id = ?";
		try {
			jdbcTemplate.update(SQL, id);
			logger.info("Deleted record from database");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

	}

	/**
	 * Returns image bytes from database from Student record by passed @id
	 * 
	 * @param id
	 * @return byte[]
	 */
	@Override
	public byte[] getStudentImage(Integer id) {
		Assert.notNull(id, "id can not be null"); 
		byte[] image = null;
		LobHandler lobHandler = new DefaultLobHandler();
		String SQL = "SELECT image FROM student WHERE id="+id;
		List<byte[]> list = jdbcTemplate.query(SQL, new RowMapper<byte[]>() {
			@Override
			public byte[] mapRow(ResultSet rs, int rowNum) throws SQLException {

				byte[] requestData = lobHandler.getBlobAsBytes(rs, "image");
				
				return requestData;
			}
		});
		image = list.get(0);
		return image;
	}

	/**
	 * Saves image bytes into database
	 * 
	 * @param id
	 * @param image
	 */
	@Override
	public void setStudentImage(Integer id, byte[] image) {
		Assert.notNull(id, "id can not be null");
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("id", id);
		in.addValue("image", new SqlLobValue(new ByteArrayInputStream(image), image.length, new DefaultLobHandler()),
				Types.BLOB);

		String SQL = "update student set image=:image where id= :id";
		NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(dataSource);
		try {
			jdbcTemplateObject.update(SQL, in);
			logger.info("Saved image to database");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

	}

	/**
	 * Returns a list of all Student records from database
	 * 
	 * @return List<T>
	 */
	@Override
	public List<Student> getAllStudents() {

		String SQL = "select * from student;";
		List<Student> list = null;
		try {
			list = jdbcTemplate.query(SQL, new StudentMapper());
			logger.info("Selected all records from database");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		return list;

	}

	/**
	 * Executes a large update into database
	 * 
	 * @param students
	 */
	@Override
	public void executeBatchObjectUpdate(final List<Student> students) {
		Assert.notNull(students, "students list can not be null");
		String SQL = "update student set name=:name,age=:age,email=:email,image=:image where id=:id";
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(students.toArray());
		NamedParameterJdbcTemplate templateObject = new NamedParameterJdbcTemplate(dataSource);
		try {
			templateObject.batchUpdate(SQL, batch);
			logger.info("Executed large BATCH update; BATCH size=" + students.size());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

	}

}
