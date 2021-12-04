package com.app.todo.item;

import com.app.todo.list.ToDoList;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ToDoListItemController.class)
public class ToDoListItemControllerTests {

    private static final ToDoList TEST_LIST = new ToDoList(1, "canderson", "shopping", null);
    private static final ToDoListItem TEST_LIST_ITEM = new ToDoListItem(1, TEST_LIST, "testing", false);
    private static final String TEST_LIST_ITEM_STRING = "{\"id\":1,\"description\":\"testing\",\"completed\":false, \"list\": { \"id\": 1}}";

    @MockBean
    private ToDoListItemService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void testSaveToDoListItem() throws Exception {
        given(service.saveToDoListItem(mapper.readValue(TEST_LIST_ITEM_STRING, ToDoListItem.class)))
                .willReturn(true);

        mockMvc.perform(post("/item")
                        .content(TEST_LIST_ITEM_STRING)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/item/" + TEST_LIST_ITEM.getId()));
    }

    @Test
    void testSaveToDoListItemBadRequest() throws Exception {
        String badRequestBody = "{\"id\":2,\"description\":\"mow\"}";
        given(service.saveToDoListItem(TEST_LIST_ITEM))
                .willReturn(true);

        mockMvc.perform(post("/item")
                        .content(badRequestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSaveToDoListItemAlreadyExistsConflict() throws Exception {
        given(service.saveToDoListItem(TEST_LIST_ITEM))
                .willReturn(false); // force conflict returned

        mockMvc.perform(post("/item")
                        .content(TEST_LIST_ITEM_STRING)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void testUpdateToDoListItem() throws Exception {
        given(service.updateToDoListItem(1, "example again", true))
                .willReturn(true);

        mockMvc.perform(put("/item/{id}", 1)
                        .param("description", "example again")
                        .param("isCompleted", "true"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateToDoListItemNotFound() throws Exception {
        given(service.updateToDoListItem(1, "example again", false))
                .willReturn(false);

        mockMvc.perform(put("/item/{id}", 1)
                        .param("description", "example again")
                        .param("isCompleted", "false"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindToDoListItem() throws Exception {
        given(service.findToDoListItem(1))
                .willReturn(Optional.of(TEST_LIST_ITEM));

        mockMvc.perform(get("/item/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(TEST_LIST_ITEM)));
    }

    @Test
    void testFindToDoListItemNotFound() throws Exception {
        given(service.findToDoListItem(1))
                .willReturn(Optional.empty());

        mockMvc.perform(get("/item/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteToDoListItem() throws Exception {
        given(service.deleteToDoListItem(1))
                .willReturn(true);

        mockMvc.perform(delete("/item/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteToDoListItemNotFound() throws Exception {
        given(service.deleteToDoListItem(1))
                .willReturn(false);

        mockMvc.perform(delete("/item/{id}", 1))
                .andExpect(status().isNotFound());
    }
}
