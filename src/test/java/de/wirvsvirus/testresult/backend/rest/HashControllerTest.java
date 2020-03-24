package de.wirvsvirus.testresult.backend.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class HashControllerIntegrationTest {

    @Autowired
    public MockMvc mvc;

    @Test
    public void testGetHashesHappyPath() throws Exception {
        mvc.perform(get("/hashes?sampleId=foo&name=bar&birthday=2004-12-23")).andExpect(status().isOk());
    }
}