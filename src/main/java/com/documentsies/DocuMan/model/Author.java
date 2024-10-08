package com.documentsies.DocuMan.model;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.ArrayList;

/**
 * Entity representing an Author.
 * <p>
 * The {@code id} field is immutable and managed by the database, while other fields such as {@code firstName},
 * {@code lastName}, and {@code documents} are mutable for flexibility in handling data updates.
 */
@Schema(description = "Entity representing an author of documents")
@Entity
public class Author {

    @Schema(description = "Unique identifier of the author", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Immutable field

    @Schema(description = "First name of the author", example = "John")
    @NotBlank(message = "First name cannot be empty")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @Schema(description = "Last name of the author", example = "Doe")
    @NotBlank(message = "Last name cannot be empty")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents = new ArrayList<>();

    /**
     * Constructor for creating a new Author with required fields.
     * 
     * @param firstName
     *            Author's first name.
     * @param lastName
     *            Author's last name.
     */
    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Empty constructor needed by JPA
    protected Author() {
    }

    /** @return The immutable ID of the Author. */
    public Long getId() {
        return id;
    }

    /** @return The first name of the Author. */
    public String getFirstName() {
        return firstName;
    }

    /** Sets the first name of the Author. */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /** @return The last name of the Author. */
    public String getLastName() {
        return lastName;
    }

    /** Sets the last name of the Author. */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /** @return The list of documents authored by this Author. */
    public List<Document> getDocuments() {
        return documents;
    }

    /** Sets the list of documents for this Author. */
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}