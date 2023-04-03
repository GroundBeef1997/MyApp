package TestModels;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	// test de controleur basique
	@Test
	void basicControllerTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/V1/hello")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	/* 
	@Test
	public void getFizzbuzzTest() throws Exception {

		Fizzbuzz testObject = new Fizzbuzz();	
		testObject.setEntryValue(3);
		service.getFizzbuzzFromValue(testObject);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/api/V1/fizzbuzz/" +  testObject.getId()).accept(
				MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();		
		String expected = "{\"id\":1, \"resultats\":[\"1\",\"2\",\"Fizz\"],\"entryValue\":3}";
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}*/

	@Test
	@WithMockUser(username = "admin", password = "pwd", roles = { "EMPLOYEE", "ADMIN" })
	public void shouldReturn200WhenSendingRequestToControllerWithRoleUser() throws Exception {
		mockMvc.perform(get("/api/V1/")).andExpect(status().isOk());
	}
}