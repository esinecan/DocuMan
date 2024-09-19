package com.documentsies.DocuMan.service;

import com.documentsies.DocuMan.model.Document;
import com.documentsies.DocuMan.repository.DocumentRepository;
import com.documentsies.DocuMan.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<Document> findAllDocuments() {
        return documentRepository.findAll();
    }

    public Optional<Document> findDocumentById(Long id) {
        return documentRepository.findById(id);
    }

    public Document saveDocument(Document document) {
        return documentRepository.save(document);
    }

    public void deleteDocument(Long id) {
        boolean exists = documentRepository.existsById(id);
        System.out.println("Document exists: " + exists);  // Add logging
        if (!exists) {
            ResourceNotFoundException ex = new ResourceNotFoundException("Document not found with id: " + id);
            System.out.println("Exception occurred: " + ex.getMessage());
            throw ex;
        }
        documentRepository.deleteById(id);
    }

    public Document updateDocument(Long id, Document updatedDocument) {
        Document existingDocument = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + id));
        existingDocument.setTitle(updatedDocument.getTitle());
        existingDocument.setBody(updatedDocument.getBody());
        return documentRepository.save(existingDocument);
    }
}
