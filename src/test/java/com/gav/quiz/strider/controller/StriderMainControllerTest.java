/**
 * 
 */
package com.gav.quiz.strider.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.gav.quiz.strider.StriderApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

/**
 * @author alex
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StriderApplication.class)
@WebAppConfiguration
public class StriderMainControllerTest {
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	@Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }
	
	 @Test
	 public void shouldBadRequestForStriderOnly() throws Exception {
	        mockMvc.perform(get("/strider"))
	                .andExpect(status().isBadRequest());	                
	 }
	 
	 @Test
	 public void shouldNotFoundForNonStrider() throws Exception {
	        mockMvc.perform(get("/non-strider"))
	                .andExpect(status().isNotFound());	                
	 }
	 
	 @Test
	 public void shouldRequireSpsBadRequest() throws Exception {
	        mockMvc.perform(get("/strider").param("non-sps", "3"))
                   .andExpect(status().isBadRequest());
	                
	 }

	 @Test
	 public void shouldRequireSpsOk() throws Exception {
	        mockMvc.perform(get("/strider").param("sps", "3"))
                   .andExpect(status().isOk());
	                
	 }
}
