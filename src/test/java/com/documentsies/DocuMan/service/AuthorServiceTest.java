package com.documentsies.DocuMan.service;

import com.documentsies.DocuMan.exception.ResourceNotFoundException;
import com.documentsies.DocuMan.model.Author;
import com.documentsies.DocuMan.repository.AuthorRepository;
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

class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        author = new Author("John", "Doe");
    }

    @Test
    void testFindAllAuthors() {
        List<Author> authors = Arrays.asList(author);
        when(authorRepository.findAll()).thenReturn(authors);

        List<Author> result = authorService.findAllAuthors();
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
    }

    @Test
    void testSaveAuthor() {
        when(authorRepository.save(author)).thenReturn(author);

        Author savedAuthor = authorService.saveAuthor(author);
        assertNotNull(savedAuthor);
        assertEquals("John", savedAuthor.getFirstName());
    }

    @Test
    void testFindAuthorById_Success() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));

        Author foundAuthor = authorService.findAuthorById(1L);
        assertNotNull(foundAuthor);
        assertEquals("John", foundAuthor.getFirstName());
    }

    @Test
    void testFindAuthorById_NotFound() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> authorService.findAuthorById(1L));
    }

    @Test
    void testDeleteAuthor_Success() {
        when(authorRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(authorRepository).deleteById(anyLong());

        authorService.deleteAuthor(1L);
        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAuthor_NotFound() {
        when(authorRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> authorService.deleteAuthor(1L));
    }
}
