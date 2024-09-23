package com.documentsies.DocuMan.service;

import com.documentsies.DocuMan.exception.ResourceNotFoundException;
import com.documentsies.DocuMan.model.Author;
import com.documentsies.DocuMan.repository.AuthorRepository;
import java.util.List;
import java.util.Optional;

import com.documentsies.DocuMan.repository.DocumentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final DocumentRepository documentRepository;

    public AuthorService(AuthorRepository authorRepository, DocumentRepository documentRepository) {
        this.authorRepository = authorRepository;
        this.documentRepository = documentRepository;
    }

    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> findAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Transactional // due to cascade deletion.
    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            ResourceNotFoundException ex = new ResourceNotFoundException("Author not found with id: " + id);
            throw ex;
        }
        authorRepository.deleteById(id);
    }

    public Author updateAuthor(Long id, Author updatedAuthor) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        existingAuthor.setFirstName(updatedAuthor.getFirstName());
        existingAuthor.setLastName(updatedAuthor.getLastName());
        return authorRepository.save(existingAuthor);
    }

    @Transactional
    public void deleteAuthorAndDocuments(Long authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new ResourceNotFoundException("Author with id " + authorId + " not found");
        }
        authorRepository.deleteById(authorId); // This will also delete related documents since cascading is configured
    }

}
