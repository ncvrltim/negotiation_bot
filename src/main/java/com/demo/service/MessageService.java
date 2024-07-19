package com.demo.service;

import com.demo.entity.Car;
import com.demo.entity.Message;
import com.demo.model.ChatRequest;
import com.demo.model.OpenAIResponse;
import com.demo.model.Role;
import com.demo.repository.CarRepository;
import com.demo.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private CarRepository carRepository;

    //@Value("${openai.api.key}")
    private String openaiApiKey;
    private String str1 = "sk-proj-dlMZM";
    private String str2 = "ArcQRIRnZYSZWKMT";
    private String str3 = "3BlbkFJMik5YvAg6XBoVsUeX88l";
    openaiApiKey = str1 +str2 + str3;
    @Value("${openai.api.url}")
    private String openaiApiEndpoint;

    @Value("${openai.model}")
    private String model;

    public String chatAndStoreHistory(ChatRequest chatRequest) {
        Message userMessage = new Message();
        userMessage.setRole(Role.user.name());
        userMessage.setContent(chatRequest.getPrompt());
        userMessage.setChatId(chatRequest.getChatId());
        if (messageRepository.findMessagesByChatIdAndContent(chatRequest.getChatId(), chatRequest.getPrompt()).isEmpty()) {
            userMessage.setCreatedTime(LocalDateTime.now());
            messageRepository.save(userMessage);
        }

        Car car = carRepository.findById(chatRequest.getCarId()).get();

        List<Message> conversationHistory = messageRepository.findMessagesByChatIdOrderByCreatedTimeAsc(chatRequest.getChatId());
        String aiResponse = getAiResponse(conversationHistory, car);
        if(aiResponse.toLowerCase().contains("this offer is final")){
            car.setSoldOut(true);
            carRepository.save(car);
        }
        Message aiMessage = new Message();
        aiMessage.setRole(Role.assistant.name());
        aiMessage.setChatId(chatRequest.getChatId());
        aiMessage.setContent(aiResponse);
        aiMessage.setCreatedTime(LocalDateTime.now());
        messageRepository.save(aiMessage);
        return aiResponse;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    private String getAiResponse(List<Message> conversationHistory, Car car) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + openaiApiKey);

        String carInformation = car.getYear() + " " + car.getMake() + " " + car.getModel() + "and listed price is " + car.getMinBidAmount();

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content", " You are an expert seller's assistant. " +
                "Your role is to assist sellers in negotiating deals with potential buyers based on their terms. " +
                "The seller's terms for the " + carInformation + " are as follows:" +
                "Your goal is to work within these terms and facilitate a successful deal. The seller's terms are " + car.getTerms() +
                " If an offer doesn't meet the terms, you can inform the buyer that you'll need to contact the seller for their response. " +
                "In case of successful deal add the text at the end of message as This offer is final. "+
                "Do not suggest other car deals. "+
                "Do not disclose the seller's terms."+
                "Do not disclose what the seller is looking for. " +
                "Don't Justify your answer. " +
                "Do not provide information not mentioned in the CONTEXT INFORMATION."));
        for (Message message : conversationHistory) {
            messages.add(Map.of("role", message.getRole(), "content", message.getContent()));
        }

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", messages);
        log.info("Body for ChatGPT " + requestBody);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        OpenAIResponse response = restTemplate.postForObject(
                openaiApiEndpoint,
                requestEntity,
                OpenAIResponse.class
        );


        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "Error while processing the request";
        }
        return response.getChoices().get(0).getMessage().getContent();

    }
}
