package com.shoemaster.application.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ChatGPTService {

    @Value("${OPENAI_API_KEY}")
    private String apiKey;

    public String askGPT(String content) throws IOException, InterruptedException {
        var body = """
                {
                    "model": "gpt-4",
                    "messages": [
                        {
                            "role": "user",
                            "content": "%s"
                        }
                    ]
                }""".formatted(content);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
