package ca.sheridancollege.sin10461.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.sin10461.beans.Employee;

@Repository
public class DatabaseAccessWeb {
	
	
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;

	//Get all the collection
	
	public List<Employee> findAll() {
		MapSqlParameterSource namedParameters =
		new MapSqlParameterSource();
		String query = "SELECT * FROM employee";
		return jdbc.query(query,
		namedParameters,
		new BeanPropertyRowMapper<Employee>(Employee.class));
		}
	
	//Get Individual item from list
	
	public List<Employee> findById(Long id) {
		MapSqlParameterSource namedParameters =
		new MapSqlParameterSource();
		String query = "SELECT * FROM employee WHERE id = :id";
		namedParameters.addValue("id", id);
		return jdbc.query(query,
		namedParameters, 
		
		new BeanPropertyRowMapper<Employee>(Employee.class));
		}
	
	//Save a new student(post) to list
	
	public Long save(Employee employee) {
		MapSqlParameterSource namedParameters =
		new MapSqlParameterSource();
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		String query = "INSERT INTO employee(name,message) VALUES(:name,:message)";
		
		namedParameters.addValue("message", employee.getMessage());
		
		jdbc.update(query, namedParameters, generatedKeyHolder);
		return (Long) generatedKeyHolder.getKey();
		}
	
	//Update a employee(put on element)
	public String updateEmployee(Long id, Employee employee) {
		MapSqlParameterSource namedParameters =
		new MapSqlParameterSource();
		String query = "UPDATE employee SET message=:message where id=:id";
		namedParameters.addValue("id", id);

		namedParameters.addValue("message", employee.getMessage());
		
		jdbc.update(query, namedParameters);
		return "employee info updated";
		}
	
	//Update a employee
	public String updateEmployee1(Long id, Employee employee) {
		MapSqlParameterSource namedParameters =
		new MapSqlParameterSource();
		String query = "UPDATE employee SET message=:message where id=:id";
		namedParameters.addValue("id", id);

		namedParameters.addValue("message", employee.getMessage());
		
		jdbc.update(query, namedParameters);
		return "employee info updated";
		}

   //Delete a collection
	
	public void deleteAll() {
		MapSqlParameterSource namedParameters =
		new MapSqlParameterSource();
		String query = "DELETE FROM employee";
		jdbc.update(query, namedParameters);
		}
	
	//Delete a element
	
	public void deleteEmployee(Long id) {
		MapSqlParameterSource namedParameters =
		new MapSqlParameterSource();
		namedParameters.addValue("id", id);
		String query = "DELETE FROM employee where id = :id";
		jdbc.update(query, namedParameters);
		}
	
	//Count no of element
	public Long count() {
		MapSqlParameterSource namedParameters =
		new MapSqlParameterSource();
		String query = "SELECT count(*) FROM employee";
		return jdbc.queryForObject(query, namedParameters, Long.TYPE);
		}
	
	//Put on list(not sure)
	public void saveAll(List<Employee> employeeList) {
		for (Employee s : employeeList) {
		save(s);
		}
		}

}
