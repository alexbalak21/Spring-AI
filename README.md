Spring‑AI
=========

Spring Boot project using **Spring AI** with **Google Gemini**.

🚀 Overview
-----------

This project exposes a simple REST API that sends user messages to Google Gemini using Spring AI’s `ChatClient`.

📦 Requirements
---------------

*   Java 21
    
*   Spring Boot 3.5.x
    
*   Spring AI 1.1.x
    
*   A valid **Gemini API Key**
    

🔧 Project Setup
----------------

### [`pom.xml`](https://pom.xml)

    <properties>
        <java.version>21</java.version>
        <spring-ai.version>1.1.2</spring-ai.version>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-starter-model-google-genai</artifactId>
        </dependency>
    
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.ai</groupId>
                <artifactId>spring-ai-bom</artifactId>
                <version>${spring-ai.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

⚙️ Configuration
----------------

### Environment variables

#### Bash

    export GEMINI_API_KEY="your-key-here"

#### PowerShell

    setx GEMINI_API_KEY "your-key-here"

### [`application.properties`](https://application.properties)

    spring.application.name=spring-ai
    spring.ai.google.genai.api-key=${GEMINI_API_KEY}
    spring.ai.google.genai.chat.options.model=gemini-2.5-flash

🧠 AI Controller
----------------

    @RestController
    public class AiController {
    
        private final ChatClient chatClient;
    
        public AiController(ChatClient.Builder builder) {
            this.chatClient = builder.build();
        }
    
        public record ChatRequest(String message) {}
        public record ChatResponse(String response) {}
    
        @PostMapping("/")
        public ChatResponse ask(@RequestBody ChatRequest request) {
            String aiAnswer = chatClient.prompt()
                    .user(request.message())
                    .call()
                    .content();
    
            return new ChatResponse(aiAnswer);
        }
    }

📡 Example Request
------------------

    curl -X POST http://localhost:8080/ \
         -H "Content-Type: application/json" \
         -d '{"message": "Hello Gemini"}'

📘 Example Response
-------------------

    {
      "response": "Hello! How can I assist you today?"
    }

🏁 Run the Application
----------------------

    ./mvnw spring-boot:run