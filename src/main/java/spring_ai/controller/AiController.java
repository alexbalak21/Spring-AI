package spring_ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiController {

    private final ChatClient chatClient;

    public AiController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    // 1. Create a record for the JSON input
    public record ChatRequest(String message) {}

    // 2. Create a record for the JSON output
    public record ChatResponse(String response) {}

    @PostMapping("/")
    public ChatResponse ask(@RequestBody ChatRequest request) {
        // Get the response text from Gemini
        String aiAnswer = chatClient.prompt()
                .user(request.message())
                .call()
                .content();

        // Wrap the answer in your Response record so Spring returns it as JSON
        return new ChatResponse(aiAnswer);
    }
}