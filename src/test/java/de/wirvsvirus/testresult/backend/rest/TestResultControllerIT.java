package de.wirvsvirus.testresult.backend.rest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import de.wirvsvirus.testresult.backend.persistence.TestResultRepo;

@AutoConfigureMockMvc
@SpringBootTest
class TestResultControllerIT {

	@Autowired
	public MockMvc mvc;

	@Autowired
	TestResultRepo repo;

	private static final String RESULTID = "123";

	@BeforeEach
	public void cleanUp() {
		repo.deleteAll();
	}

	@Test
	public void postAndGetTestResult() throws Exception {

		Document postBody = new Document();
		postBody.put("status", "NEGATIVE");

		mvc.perform(post("/tests/" + RESULTID)
				.with(user("user").password("password").roles("POSTUSER"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(postBody.toJson()))
		.andExpect(status().isOk());

		mvc.perform(get("/tests/" + RESULTID))
		.andExpect(status().isOk())
		.andDo(r -> {
			Document getResult = Document.parse(r.getResponse().getContentAsString());
			assertAll("content contains all the values of a new record", 
					() -> assertTrue(getResult.containsKey("id")),
					() -> assertEquals("123", getResult.getString("id")),

					() -> assertTrue(getResult.containsKey("status")),
					() -> assertEquals("NEGATIVE", getResult.getString("status")),

					() -> assertTrue(getResult.containsKey("contact")), () -> assertNull(getResult.get("contact")),

					() -> assertTrue(getResult.containsKey("notified")),
					() -> assertEquals(Boolean.FALSE, getResult.getBoolean("notified")));
		});
	}
}