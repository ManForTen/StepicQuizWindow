import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


public class MyForm extends JFrame{
    private JPanel panel;
    private JTextField textField1;
    private JButton button1;
    private JTextArea textArea1;
    private int counter = 0;
    private int numberQuestion = 0;
    List<String> answers = new ArrayList<>();
    List<String> questions = new ArrayList<>();

    MyForm() throws URISyntaxException, IOException, InterruptedException {
        super("Викторина");
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("http://jservice.io/api/random?count=8")).GET().build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String jsonString = response.body();
        JSONArray array = new JSONArray(jsonString);
        for (int i = 0; i < 8; i++) {
            JSONObject obj = array.getJSONObject(i);
            answers.add((String) obj.get("answer"));
            questions.add((String) obj.get("question"));
        }
        textArea1.setText("Вопрос №1: " + questions.get(0) + "? Правильный ответ: "  + answers.get(0));
        button1.addActionListener(e -> next());
        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    void next() {
        String choice = textField1.getText();
        if (choice.equals(answers.get(numberQuestion))) counter++;
        textField1.setText("");
        if (numberQuestion == 7) {
            textArea1.setText("Вы ответили верно на " + counter + " из 8 вопросов");
            return;
        }
        numberQuestion++;
        textArea1.setText("Вопрос №" + (numberQuestion + 1) + ": " + questions.get(numberQuestion) + "? Правильный ответ: " + answers.get(numberQuestion));
    }

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException{
        new MyForm();
    }
}
