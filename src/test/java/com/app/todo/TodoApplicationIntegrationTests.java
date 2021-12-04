package com.app.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class TodoApplicationIntegrationTests {

	// Start app test e2e integration with database

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testGetToDoListsByUsername() throws Exception {

		mockMvc.perform(get("/list/{username}", "jsmith"))
				.andExpect(status().isOk())
				.andReturn();
	}
}
