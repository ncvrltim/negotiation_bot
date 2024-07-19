package com.demo.controller;

import com.demo.entity.Message;
import com.demo.model.ChatRequest;
import com.demo.model.ChatResponse;
import com.demo.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@Slf4j
public class MessageController {

    @Autowired
    private MessageService messageService;

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest chatRequest) {
        String message = messageService.chatAndStoreHistory(chatRequest);
       // log.info("inside chat controller: prompt = {}", chatRequest.getPrompt());
        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setAnswer(message);
        return new ResponseEntity<>(chatResponse, HttpStatus.OK);
    }

    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }
}
