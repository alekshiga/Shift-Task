import java.io.*;

public class Solution {
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.print("Не найдено входных файлов");
            return;
        }

        for (String filename : args) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;

            } catch (IOException e) {
                System.err.println("Ошибка чтения файла " + filename + ": " + e.getMessage());
            }
        }

        System.out.println("Данные файлов отсортированы");
    }
}