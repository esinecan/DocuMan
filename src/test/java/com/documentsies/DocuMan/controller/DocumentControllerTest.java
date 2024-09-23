package com.documentsies.DocuMan.controller;

import com.documentsies.DocuMan.exception.ResourceNotFoundException;
import com.documentsies.DocuMan.model.Document;
import com.documentsies.DocuMan.service.DocumentService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DocumentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DocumentService documentService;

    @InjectMocks
    private DocumentController documentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(documentController)
                .setControllerAdvice(new com.documentsies.DocuMan.exception.GlobalExceptionHandler()).build();
    }

    @Test
    void testGetAllDocuments() throws Exception {
        List<Document> documents = Arrays.asList(new Document("Title1", "Body1", null, null),
                new Document("Title2", "Body2", null, null));
        when(documentService.findAllDocuments()).thenReturn(documents);

        mockMvc.perform(get("/api/documents")).andExpect(status().isOk());
    }

    @Test
    void testGetDocumentById_Success() throws Exception {
        Document document = new Document("Title", "Body", null, null);
        when(documentService.findDocumentById(anyLong())).thenReturn(Optional.of(document));

        mockMvc.perform(get("/api/documents/1")).andExpect(status().isOk());
    }

    @Test
    void testGetDocumentById_NotFound() throws Exception {
        when(documentService.findDocumentById(anyLong()))
                .thenThrow(new ResourceNotFoundException("Document not found"));

        mockMvc.perform(get("/api/documents/1")).andExpect(status().isNotFound());
    }

    @Test
    void testDeleteDocument_Success() throws Exception {
        when(documentService.findDocumentById(anyLong()))
                .thenReturn(Optional.of(new Document("Title", "Body", null, null)));
        doNothing().when(documentService).deleteDocument(anyLong());

        mockMvc.perform(delete("/api/documents/1")).andExpect(status().isNoContent());
    }

    @Test
    void testDeleteDocument_NotFound() throws Exception {
        when(documentService.findDocumentById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/documents/1")).andExpect(status().isNotFound());
    }

    private void printResponse(MvcResult result) throws Exception {
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void shouldReturnBadRequestWhenDocumentTitleIsInvalid() throws Exception {
        String invalidDocumentJson = "{\"title\": \"\", \"body\": \"Valid Body\"}";
        MvcResult result = mockMvc
                .perform(post("/api/documents").contentType(MediaType.APPLICATION_JSON).content(invalidDocumentJson))
                .andExpect(status().isBadRequest()).andReturn();
        printResponse(result);
    }

    @Test
    public void shouldReturnBadRequestWhenDocumentBodyIsInvalid() throws Exception {
        String invalidDocumentJson = "{\"title\": \"Valid Title\", \"body\": \"\"}";
        MvcResult result = mockMvc
                .perform(post("/api/documents").contentType(MediaType.APPLICATION_JSON).content(invalidDocumentJson))
                .andExpect(status().isBadRequest()).andReturn();
        printResponse(result);
    }

    @Test
    public void shouldReturnOkWhenDocumentDataIsValid() throws Exception {
        String validDocumentJson = "{\"title\": \"Valid Title\", \"body\": \"Valid Body\"}";
        MvcResult result = mockMvc
                .perform(post("/api/documents").contentType(MediaType.APPLICATION_JSON).content(validDocumentJson))
                .andExpect(status().isOk()).andReturn();
        printResponse(result);
    }

    @Test
    public void shouldReturnBadRequestWhenBothFieldsAreInvalid() throws Exception {
        String invalidDocumentJson = "{\"title\": \"\", \"body\": \"\"}";
        MvcResult result = mockMvc
                .perform(post("/api/documents").contentType(MediaType.APPLICATION_JSON).content(invalidDocumentJson))
                .andExpect(status().isBadRequest()).andReturn();
        printResponse(result);
    }
}