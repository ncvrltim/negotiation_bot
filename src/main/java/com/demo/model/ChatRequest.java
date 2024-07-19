package com.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatRequest {
	
	private String chatId;
	private String prompt;
	private long carId;
}
