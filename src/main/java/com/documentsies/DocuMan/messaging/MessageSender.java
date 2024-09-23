package com.documentsies.DocuMan.messaging;

import com.documentsies.DocuMan.model.Author;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendAuthorDeletionMessage(Author author) {
        amqpTemplate.convertAndSend("author-deletion-queue", author);
    }
}
