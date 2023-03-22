package TestModels;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser(username = "admin", password = "pwd", roles = { "EMPLOYEE", "ADMIN" })
	public void shouldReturn200WhenSendingRequestToControllerWithRoleUser() throws Exception {
		mockMvc.perform(get("/api/V1/")).andExpect(status().isOk());
	}
}