package ca.sheridancollege.sin10461.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.sin10461.beans.Employee;
import ca.sheridancollege.sin10461.beans.UserRegister;
import ca.sheridancollege.sin10461.security.DatabaseAccess;

@Controller
public class MyController {
	
	@Autowired
	DatabaseAccess da;
	
	@Autowired
	Employee employee;
	
	@Autowired
	 JdbcUserDetailsManager jdbcUserDetailsManager;
	
@GetMapping("/")
public String getIndex() {
	return "index";
}

@GetMapping("/register")
public String getRegister(Model model,UserRegister user) {
	model.addAttribute("user", user);
	
	return "register";
}

@GetMapping("/calculation")
public String getCalc(Model model,Employee employee) {
	
	model.addAttribute("employee",employee);
	
	return "calculation";
}



@GetMapping("/admin")
public String getAdmin(Model model,@ModelAttribute Employee employee) {
	ArrayList<Employee> list=da.getData();
	model.addAttribute("list", list);
	
	return "admin";
}

@GetMapping("/login")
public String getLogin() {
	
	return "login";
}

@PostMapping("/register")
public String postRegister(@ModelAttribute UserRegister user) {
	
	List<GrantedAuthority> authorities = new
			ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

	String passwordEncoder=new BCryptPasswordEncoder().encode(user.getPassword());
			
			
	User newUser=new User(user.getUsername(),passwordEncoder,authorities);
	jdbcUserDetailsManager.createUser(newUser);
	
	
	return "redirect:/";
}

@PostMapping("/calculation")
public String postCalc(Model model,@ModelAttribute Employee employee,HttpSession sess ,UserRegister user) {
	
	da.addData(employee);
	model.addAttribute("employee",employee);
	
	ArrayList<Employee> list=da.getData();
	model.addAttribute("list", list);
	return "calculation";
}



}
