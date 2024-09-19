package com.documentsies.DocuMan.controller;

import com.documentsies.DocuMan.exception.ResourceNotFoundException;
import com.documentsies.DocuMan.model.Document;
import com.documentsies.DocuMan.service.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                .setControllerAdvice(new com.documentsies.DocuMan.exception.GlobalExceptionHandler())
                .build();
    }

    @Test
    void testGetDocumentById_Success() throws Exception {
        Document document = new Document("Sample Title", "Sample Body", null, null);
        when(documentService.findDocumentById(anyLong())).thenReturn(Optional.of(document));

        mockMvc.perform(get("/api/documents/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetDocumentById_NotFound() throws Exception {
        when(documentService.findDocumentById(anyLong())).thenThrow(new ResourceNotFoundException("Document not found"));

        mockMvc.perform(get("/api/documents/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteDocument_Success() throws Exception {
        mockMvc.perform(delete("/api/documents/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteDocument_NotFound() throws Exception {
        when(documentService.findDocumentById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/documents/1"))
                .andExpect(status().isNotFound());
    }
}
