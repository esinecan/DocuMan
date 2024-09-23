package com.documentsies.DocuMan.messaging;

import org.springframework.stereotype.Service;  

@Service
public class MessageSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendAuthorDeletionMessage(Author author) {
        amqpTemplate.convertAndSend("author-deletion-queue", author);
    }
}
