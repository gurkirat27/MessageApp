package ca.sheridancollege.sin10461.controller;



import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import ca.sheridancollege.sin10461.beans.Employee;
import ca.sheridancollege.sin10461.security.DatabaseAccess;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestController {
	@Autowired
	 private DatabaseAccess databaseAccess;

	

@Autowired
private MockMvc mockMvc;


@Test
public void testLoadingAddPostPage() throws Exception {
this.mockMvc.perform(get("/calculation"))
.andExpect(status().isOk())
.andExpect(view().name("calculation.html"));
}

@Test
public void testSaveDrinkWithDatabase() {
Employee employee=new Employee();
int originalsize = databaseAccess
.getData().size();

databaseAccess.addData(employee);
int foundsize = databaseAccess
.getData().size();

// test
assertThat(foundsize)
.isEqualTo(originalsize+1);
}






}