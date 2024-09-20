package com.documentsies.DocuMan.controller;

import com.documentsies.DocuMan.exception.ResourceNotFoundException;
import com.documentsies.DocuMan.model.Author;
import com.documentsies.DocuMan.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AuthorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authorController)
                .setControllerAdvice(new com.documentsies.DocuMan.exception.GlobalExceptionHandler())
                .build();
    }

    /*@Test
    void testGetAllAuthors() throws Exception {
        List<Author> authors = Arrays.asList(
                new Author("John", "Doe"),
                new Author("Jane", "Smith")
        );
        when(authorService.findAllAuthors()).thenReturn(authors);

        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAuthorById_Success() throws Exception {
        Author author = new Author("John", "Doe");
        when(authorService.findAuthorById(anyLong())).thenReturn(Optional.of(author));

        mockMvc.perform(get("/api/authors/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAuthorById_NotFound() throws Exception {
        when(authorService.findAuthorById(anyLong())).thenThrow(new ResourceNotFoundException("Author not found"));

        mockMvc.perform(get("/api/authors/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteAuthor_Success() throws Exception {
        mockMvc.perform(delete("/api/authors/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteAuthor_NotFound() throws Exception {
        when(authorService.findAuthorById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/authors/1"))
                .andExpect(status().isNoContent());
    }*/

    @Test
    public void shouldReturnBadRequestWhenAuthorNameIsInvalid() throws Exception {
        String invalidAuthorJson = "{\"firstName\": \"\", \"lastName\": \"Doe\"}";

        mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidAuthorJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("First name must be between 2 and 50 characters"));
    }

}
