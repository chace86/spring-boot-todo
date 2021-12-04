package com.app.todo.list;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ToDoListController.class)
public class ToDoListControllerTests {

    private static final ToDoList TEST_LIST = new ToDoList(1, "canderson", "shopping", null);
    private static final String TEST_LIST_STRING = "{\"id\":1,\"username\":\"canderson\",\"title\":\"shopping\",\"items\":null}";

    @MockBean
    private ToDoListService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void testSaveToDoList() throws Exception {
        given(service.saveToDoList(TEST_LIST))
                .willReturn(true);

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

    @Test
    void testUpdateToDoList() throws Exception {
        given(service.updateToDoList(1, "example"))
                .willReturn(true);

        mockMvc.perform(put("/list/{id}", 1).param("title", "example"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateToDoListNotFound() throws Exception {
        given(service.updateToDoList(1, "example"))
                .willReturn(false);

        mockMvc.perform(put("/list/{id}", 1).param("title", "example"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindAllListsByUsername() throws Exception {
        List<ToDoList> list = new ArrayList<>();
        list.add(TEST_LIST);
        given(service.findAllToDoListsByUsername("canderson"))
                .willReturn(list);

        mockMvc.perform(get("/list/{username}", "canderson"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(list)));
    }

    @Test
    void testDeleteToDoList() throws Exception {
        given(service.deleteToDoList(1))
                .willReturn(true);

        mockMvc.perform(delete("/list/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteToDoListNotFound() throws Exception {
        given(service.deleteToDoList(1))
                .willReturn(false);

        mockMvc.perform(delete("/list/{id}", 1))
                .andExpect(status().isNotFound());
    }
}
