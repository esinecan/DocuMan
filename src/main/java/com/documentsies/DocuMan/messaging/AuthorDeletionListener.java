package com.documentsies.DocuMan.messaging;

import com.documentsies.DocuMan.model.Author;
import com.documentsies.DocuMan.service.AuthorService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorDeletionListener {

    private final AuthorService authorService;

    @Autowired
    public AuthorDeletionListener(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RabbitListener(queues = "author-deletion-queue")
    public void handleAuthorDeletion(Author author) {
        authorService.deleteAuthorAndDocuments(author);
    }
}
