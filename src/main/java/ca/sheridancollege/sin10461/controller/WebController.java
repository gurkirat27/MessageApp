package ca.sheridancollege.sin10461.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ca.sheridancollege.sin10461.beans.Employee;
import ca.sheridancollege.sin10461.security.DatabaseAccessWeb;

@Controller
@RequestMapping("/employees")
public class WebController {

	@Autowired
	DatabaseAccessWeb daw;
	
	//Get for collection
	@GetMapping
	public  List<Employee> getEmployeeCollection(){
		
		return daw.findAll();
	}
	
	//Get for element
	@GetMapping("/{id}")
	public Employee getEmployee(@PathVariable Long id){
		
		return daw.findById(id).get(0);
		
	}
	
	//POST 
	@PostMapping(consumes="application/json")
	public Long postStudent(@RequestBody Employee employee) {
	return daw.save(employee);
	}
	
	//PUT for collection
	
	@PutMapping(consumes="application/json")
	public String replaceEmployeeCollection(@RequestBody List<Employee> employeeList) {
		daw.deleteAll();
		daw.saveAll(employeeList);
		return "Employee list updated";
	}
	
	@PutMapping(path="/{id}")
	public String replaceEmployee(@PathVariable Long id, @RequestBody Employee employee) {
		
		return daw.updateEmployee1(id, employee);
	}
	
	// this is DELETE - Replace the entire collection
	@DeleteMapping
	public String deleteEmployeeCollection() {
	daw.deleteAll();
	return "Recoreds removed. Total Records: " + daw.count();
	}
	
	
	// this is DELETE - Remove an individual entry from the collection
	@DeleteMapping("/{id}")
	public String deleteEmployee(@PathVariable Long id) {
	daw.deleteEmployee(id);
	return "Employee removed.";
	}
	
	
	
	

}
