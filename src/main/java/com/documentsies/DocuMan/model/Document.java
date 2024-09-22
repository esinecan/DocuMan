package com.documentsies.DocuMan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 * Entity representing a Document.
 * <p>
 * The {@code id} field is immutable and managed by the database, while other fields such as {@code title},
 * {@code body}, and {@code references} are mutable to allow flexibility in data updates.
 */
@Schema(description = "Entity representing a document with its metadata")
@Entity
public class Document {

    @Schema(description = "Unique identifier of the document", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Immutable field

    @Schema(description = "Title of the document", example = "Spring Boot Guide")
    @NotBlank(message = "Title cannot be empty")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @Schema(description = "Content body of the document", example = "This is a comprehensive guide on Spring Boot.")
    @NotBlank(message = "Body cannot be empty")
    @Size(min = 10, message = "Body must have at least 10 characters")
    private String body;

    @Schema(description = "Author associated with the document")
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @Schema(description = "List of references for the document", example = "[\"reference1\", \"reference2\"]")
    @ElementCollection
    private List<String> references;

    /**
     * Constructor for creating a new Document with required fields.
     * 
     * @param title
     *            The title of the document.
     * @param body
     *            The body text of the document.
     * @param author
     *            The author of the document.
     * @param references
     *            A list of references for the document.
     */
    public Document(String title, String body, Author author, List<String> references) {
        this.title = title;
        this.body = body;
        this.author = author;
        this.references = references;
    }

    // Empty constructor needed by JPA
    protected Document() {
    }

    /** @return The immutable ID of the Document. */
    public Long getId() {
        return id;
    }

    /** @return The title of the Document. */
    public String getTitle() {
        return title;
    }

    /** Sets the title of the Document. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return The body text of the Document. */
    public String getBody() {
        return body;
    }

    /** Sets the body text of the Document. */
    public void setBody(String body) {
        this.body = body;
    }

    /** @return The author of the Document. */
    public Author getAuthor() {
        return author;
    }

    /** Sets the author of the Document. */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /** @return The list of references for the Document. */
    public List<String> getReferences() {
        return references;
    }

    /** Sets the list of references for the Document. */
    public void setReferences(List<String> references) {
        this.references = references;
    }
}
