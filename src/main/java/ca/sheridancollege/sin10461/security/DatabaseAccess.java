package ca.sheridancollege.sin10461.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


import ca.sheridancollege.sin10461.beans.Employee;

@Repository

public class DatabaseAccess {

	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	
	public void addData(Employee employee) {
		
		String query="INSERT INTO employee(name,message) values(:name,:message)";
MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
namedParameters.addValue("name", employee.getName());
	namedParameters.addValue("message", employee.getMessage());


		
		
	
	jdbc.update(query, namedParameters);
		
		
	}
	
	
	public ArrayList<Employee> getData() {
		String query = "SELECT id, name, message FROM employee ";
		ArrayList<Employee> list = new ArrayList<Employee>();
		List<Map<String, Object>> rows = jdbc.queryForList(
			query, new HashMap<String,Object>());
		for (Map<String, Object> row : rows) {
			list.add(new Employee((Long) row.get("id"), (String) row.get("name"), (String) row.get("message") ));
			}return list;
			}
	
	}


