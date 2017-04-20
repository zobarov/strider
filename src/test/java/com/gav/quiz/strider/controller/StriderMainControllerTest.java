package com.gav.quiz.strider.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.gav.quiz.strider.StriderApplication;

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
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }
	
	/////////Params acceptance checks:

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
	 
	 /////////Content checks by example:
	 
	 @Test
	 public void testContent_ExampleOne() throws Exception {
		mockMvc.perform(get("/strider")
	        		.param("sps", "3")
	        		.param("stairwell", "17"))
                 .andExpect(status().isOk())
                 .andExpect(content().contentType(contentType))
                 .andExpect(jsonPath("$.minimumStridesAmount", is(6)));
	 }
	 
	 @Test
	 public void testContent_ExampleTwo() throws Exception {
		mockMvc.perform(get("/strider")
	        		.param("sps", "3")
	        		.param("stairwell", "17,17"))
                 .andExpect(status().isOk())
                 .andExpect(content().contentType(contentType))
                 .andExpect(jsonPath("$.minimumStridesAmount", is(14)));
	 }
	 
	 @Test
	 public void testContent_ExampleThree() throws Exception {
		mockMvc.perform(get("/strider")
	        		.param("sps", "2")
	        		.param("stairwell", "4,9,8,11,7,20,14"))
                 .andExpect(status().isOk())
                 .andExpect(content().contentType(contentType))
                 .andExpect(jsonPath("$.minimumStridesAmount", is(50)));
	 }
	 
	 /////////Content checks in invalid requests:
	 @Test
	 public void testContent_InvalidZeroSps() throws Exception {
		mockMvc.perform(get("/strider")
	        		.param("sps", "0")
	        		.param("stairwell", "4,9,8,11,7,20,14"))
                 .andExpect(status().isOk())
                 .andExpect(content().contentType(contentType))
                 .andExpect(jsonPath("$.minimumStridesAmount", is(0)));
	 }
	 
	 @Test
	 public void testContent_InvalidNegativeSps() throws Exception {
		mockMvc.perform(get("/strider")
	        		.param("sps", "-3")
	        		.param("stairwell", "4,9,8,11,7,20,14"))
              .andExpect(status().isOk())
              .andExpect(content().contentType(contentType))
              .andExpect(jsonPath("$.minimumStridesAmount", is(0)));
	 }
	 
	 @Test
	 public void testContent_InvalidZeroInStairwell() throws Exception {
		mockMvc.perform(get("/strider")
	        		.param("sps", "2")
	        		.param("stairwell", "4,9,8,0,7,20,14"))
              .andExpect(status().isOk())
              .andExpect(content().contentType(contentType))
              .andExpect(jsonPath("$.minimumStridesAmount", is(0)));
	 }
	 
	 @Test
	 public void testContent_InvalidNegativeInStairwell() throws Exception {
		mockMvc.perform(get("/strider")
	        		.param("sps", "-3")
	        		.param("stairwell", "4,9,8,-11,7,20,14"))
              .andExpect(status().isOk())
              .andExpect(content().contentType(contentType))
              .andExpect(jsonPath("$.minimumStridesAmount", is(0)));
	 }
}
