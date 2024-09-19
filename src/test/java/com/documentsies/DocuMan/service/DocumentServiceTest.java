package com.documentsies.DocuMan.service;

import com.documentsies.DocuMan.exception.ResourceNotFoundException;
import com.documentsies.DocuMan.model.Document;
import com.documentsies.DocuMan.model.Author;
import com.documentsies.DocuMan.repository.DocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DocumentServiceTest {

    private static final String TITLE = "Sample Document";
    private static final String BODY = "This is a sample document body.";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentService documentService;

    private Document document;
    private Author author;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        author = new Author(FIRST_NAME, LAST_NAME);
        document = new Document(TITLE, BODY, author, null);
    }

    @Test
    void testFindAllDocuments() {
        List<Document> documents = Arrays.asList(document);
        when(documentRepository.findAll()).thenReturn(documents);

        List<Document> result = documentService.findAllDocuments();
        assertEquals(1, result.size());
        assertDocumentDetails(result.get(0));
    }

    @Test
    void testSaveDocument() {
        when(documentRepository.save(document)).thenReturn(document);

        Document savedDocument = documentService.saveDocument(document);
        assertNotNull(savedDocument);
        assertDocumentDetails(savedDocument);
    }

    @Test
    void testFindDocumentById_Success() {
        when(documentRepository.findById(anyLong())).thenReturn(Optional.of(document));

        Document foundDocument = documentService.findDocumentById(1L);
        assertNotNull(foundDocument);
        assertDocumentDetails(foundDocument);
    }

    @Test
    void testFindDocumentById_NotFound() {
        when(documentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> documentService.findDocumentById(1L));
    }

    @Test
    void testDeleteDocument_Success() {
        when(documentRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(documentRepository).deleteById(anyLong());

        documentService.deleteDocument(1L);
        verify(documentRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteDocument_NotFound() {
        when(documentRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> documentService.deleteDocument(1L));
    }

    // Helper method to check document details
    private void assertDocumentDetails(Document document) {
        assertEquals(TITLE, document.getTitle());
        assertEquals(BODY, document.getBody());
        assertEquals(FIRST_NAME, document.getAuthor().getFirstName());
        assertEquals(LAST_NAME, document.getAuthor().getLastName());
    }
}
