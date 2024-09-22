package com.documentsies.DocuMan.controller;

import com.documentsies.DocuMan.model.Document;
import com.documentsies.DocuMan.service.DocumentService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Document Controller", description = "Controller for managing document operations")
@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Operation(summary = "Get all documents", description = "Retrieve a list of all documents in the system.")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of the document list")
    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {
        List<Document> documents = documentService.findAllDocuments();
        return ResponseEntity.ok(documents);
    }

    @Operation(summary = "Get a document by ID", description = "Retrieve a specific document by its unique ID.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved the document"),
            @ApiResponse(responseCode = "404", description = "Document not found")})
    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {
        return documentService.findDocumentById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Add a new document", description = "Create a new document in the system.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Successfully created the new document"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")})
    @PostMapping("")
    public ResponseEntity<Object> createDocument(@Valid @RequestBody Document document, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        Document savedDocument = documentService.saveDocument(document);
        return ResponseEntity.ok(savedDocument);
    }

    @Operation(summary = "Update a document", description = "Update the details of an existing document by its ID.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully updated the document"),
            @ApiResponse(responseCode = "404", description = "Document not found")})
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDocument(@PathVariable Long id, @Valid @RequestBody Document document,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        Document updatedDocument = documentService.updateDocument(id, document);
        return ResponseEntity.ok(updatedDocument);
    }

    @Operation(summary = "Delete a document", description = "Delete a document from the system by its ID.")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Successfully deleted the document"),
            @ApiResponse(responseCode = "404", description = "Document not found")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDocument(@PathVariable Long id) {
        return documentService.findDocumentById(id).map(document -> {
            documentService.deleteDocument(id);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
