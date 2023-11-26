import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        try {
            // Чтение файла poem.txt и создание файлов для каждого абзаца
            BufferedReader reader = new BufferedReader(new FileReader("poem.txt"));
            String line;
            int partNumber = 1;

            FileWriter currentWriter = new FileWriter("part" + partNumber + ".txt");

            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    // Новый абзац, создаем новый файл
                    currentWriter.close();
                    partNumber++;
                    currentWriter = new FileWriter("part" + partNumber + ".txt");
                } else {
                    // Записываем строку в текущий файл
                    currentWriter.write(line + System.lineSeparator());
                }
            }

            // Закрываем последний файл
            currentWriter.close();
            reader.close();

            // Чтение файла poem.txt и запись строк в очередь
            Queue<String> queue = new LinkedList<>();
            reader = new BufferedReader(new FileReader("poem.txt"));

            while ((line = reader.readLine()) != null) {
                queue.add(line);
            }

            reader.close();

            // Вывод строк из очереди с рандомной задержкой от 1 до 3 секунд
            while (!queue.isEmpty()) {
                String nextLine = queue.poll();
                System.out.println(nextLine);
                Thread.sleep(new Random().nextInt(3000) + 1000); // Задержка от 1 до 3 секунд
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
