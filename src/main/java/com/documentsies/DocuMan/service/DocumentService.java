package com.documentsies.DocuMan.service;

import com.documentsies.DocuMan.model.Document;
import com.documentsies.DocuMan.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<Document> findAllDocuments() {
        return documentRepository.findAll();
    }

    public Document saveDocument(Document document) {
        return documentRepository.save(document);
    }

    public Document findDocumentById(Long id) {
        return documentRepository.findById(id).orElseThrow(() -> new RuntimeException("Document not found"));
    }

    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }
}
