package com.gav.quiz.strider.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.gav.quiz.strider.StriderApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

/**
 * @author alex.gera
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StriderApplication.class)
@WebAppConfiguration
@TestPropertySource(locations = "classpath:./app_common.test.properties")
public class StriderMainControllerTest {
	@Autowired
    private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

	 @Test
	 public void testAppNameOnly_badRequest() throws Exception {
	        mockMvc.perform(get("/strider"))
	        	.andExpect(status().isBadRequest());
	 }

	 @Test
	 public void testWrongAppNameOnly_notFound() throws Exception {
	        mockMvc.perform(get("/non-strider"))
	        	.andExpect(status().isNotFound());
	 }

	 @Test
	 public void testNonSpsParamOnly_badRequest() throws Exception {
	        mockMvc.perform(get("/strider")
	        		.param("non-sps", "3"))
            	.andExpect(status().isBadRequest());
	 }

	 @Test
	 public void testSpsParamOnly_badRequest() throws Exception {
	        mockMvc.perform(get("/strider")
	        		.param("sps", "3"))
            	.andExpect(status().isBadRequest());
	 }

	 @Test
	 public void testSpsAndStairwellParam_Ok() throws Exception {
	        mockMvc.perform(get("/strider")
	        		.param("sps", "3")
	        		.param("stairwell", "1,2,3"))
                 .andExpect(status().isOk());
	 }

	 @Test
	 public void testSpsAndNonStairwellParam_badRequest() throws Exception {
	        mockMvc.perform(get("/strider")
	        		.param("sps", "3")
	        		.param("non-stairwell", "1,2,3"))
                 .andExpect(status().isBadRequest());
	 }

	 @Test
	 public void testSpsAndStairwellAndExtraParam_Ok() throws Exception {
	        mockMvc.perform(get("/strider")
	        		.param("sps", "3")
	        		.param("stairwell", "1,2,3")
	        		.param("extra-param", "extra"))
                 .andExpect(status().isOk());
	 }
}
