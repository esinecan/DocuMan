package com.documentsies.DocuMan.controller;

import com.documentsies.DocuMan.messaging.MessageSender;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.documentsies.DocuMan.model.Author;
import com.documentsies.DocuMan.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Author Controller", description = "Controller for managing author operations")
@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final MessageSender messageSender;
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Operation(summary = "Get all authors", description = "Retrieve a list of all authors.")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of author list")
    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.findAllAuthors();
        return ResponseEntity.ok(authors);
    }

    @Operation(summary = "Get an author by ID", description = "Retrieve a specific author by their unique ID.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved the author"),
            @ApiResponse(responseCode = "404", description = "Author not found")})
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        return authorService.findAuthorById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Add a new author", description = "Create a new author in the system.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Successfully created the new author"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")})
    @PostMapping("")
    public ResponseEntity<Object> createAuthor(@Valid @RequestBody Author author, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        Author savedAuthor = authorService.saveAuthor(author);
        return ResponseEntity.ok(savedAuthor);
    }

    @Operation(summary = "Update an author", description = "Update the details of an existing author by their ID.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully updated the author"),
            @ApiResponse(responseCode = "404", description = "Author not found")})
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable Long id, @Valid @RequestBody Author author,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        Author updatedAuthor = authorService.updateAuthor(id, author);
        return ResponseEntity.ok(updatedAuthor);
    }

    @Operation(summary = "Delete an author", description = "Delete an author from the system by their ID.")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Successfully deleted the author"),
            @ApiResponse(responseCode = "404", description = "Author not found")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete all documents by author ID using RabbitMQ")
    @ApiResponse(responseCode = "200", description = "Documents deletion requested successfully")
    @ApiResponse(responseCode = "404", description = "Author not found")
    @DeleteMapping("/{id}/documents/delete")
    public ResponseEntity<String> deleteDocumentsByAuthor(@PathVariable Long id) {
        // Send the author ID to the RabbitMQ queue to trigger document deletion
        messageSender.sendAuthorDeletionMessage(id);
        return ResponseEntity.ok("Documents deletion for author " + id + " has been requested.");
    }
}
