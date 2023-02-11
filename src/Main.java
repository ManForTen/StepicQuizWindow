import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import org.json.*;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ответьте на 8 вопросов викторины");
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("http://jservice.io/api/random?count=8")).GET().build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String jsonString = response.body();
        JSONArray array = new JSONArray(jsonString);
        int counter = 0;
        for (int i = 0; i < 8; i++){
            JSONObject obj = array.getJSONObject(i);
            String answer = (String) obj.get("answer");
            String question = (String) obj.get("question");
            System.out.println("Вопрос №" + (i + 1) + ": " +  question + "? Правильный ответ: " + answer);
            String choice = scanner.nextLine();
            if (choice.equals(answer))
                counter++;
        }
        System.out.println("Вы ответили верно на " + counter + " из 8 вопросов");
    }
}