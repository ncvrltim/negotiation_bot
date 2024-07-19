package com.demo.model;

import lombok.Data;

@Data
public class ChatResponse {
    private String answer;
    private String error;
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
    
    
}
