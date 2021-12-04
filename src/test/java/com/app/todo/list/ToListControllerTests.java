package com.app.todo.list;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ToDoListController.class)
public class ToListControllerTests {

    private static final ToDoList TEST_LIST = new ToDoList(1, "canderson", "shopping", null);
    private static final String TEST_LIST_STRING = "{\"id\":1,\"username\":\"canderson\",\"title\":\"shopping\",\"items\":null}";

    @MockBean
    private ToDoListService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        given(service.saveToDoList(TEST_LIST))
                .willReturn(true);
    }

    @Test
    void testSaveToDoList() throws Exception {

        mockMvc.perform(post("/list")
                .content(TEST_LIST_STRING)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", "http://localhost/list/" + TEST_LIST.getId()));
    }

    @Test
    void testSaveToDoListBadRequest() throws Exception {
        String badRequestBody = "{\"id\":2,\"title\":\"cleaning\",\"items\":null}";
        given(service.saveToDoList(TEST_LIST))
                .willReturn(true);

        mockMvc.perform(post("/list")
                        .content(badRequestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSaveToDoListAlreadyExistsConflict() throws Exception {
        given(service.saveToDoList(TEST_LIST))
                .willReturn(false); // force conflict returned

        mockMvc.perform(post("/list")
                        .content(TEST_LIST_STRING)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }
}
