import java.io.*;

public class Solution {

    public static final String intOutput = "integers.txt";
    public static final String floatOutput = "floats.txt";
    public static final String stringOutput = "strings.txt";

    public static BufferedWriter intWriter;

{
    try {
        intWriter = new BufferedWriter(new FileWriter(intOutput));
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}

    public static BufferedWriter floatWriter;

{
    try {
        floatWriter = new BufferedWriter(new FileWriter(floatOutput));
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}

    public static BufferedWriter stringWriter;

{
    try {
        stringWriter = new BufferedWriter(new FileWriter(stringOutput));
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}

public static void main(String[] args) {

        if (args.length < 1) {
            System.out.print("Не найдено входных файлов");
            return;
        }

        for (String filename : args) {

            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    typeSorter(line);
                }

            } catch (IOException e) {
                System.err.println("Ошибка чтения файла " + filename + ": " + e.getMessage());
            }
        }
        System.out.println("Данные файлов отсортированы");
    }

    public static void typeSorter(String line) {
        try {
            int intValue = Integer.parseInt(line);
            intWriter.write(line);
            intWriter.newLine();
        } catch (NumberFormatException e1) {
            try {
                float floatValue = Float.parseFloat(line);
                //todo if value type is float
            } catch (NumberFormatException e2) {
                //todo if value type is String
            }
        } catch (IOException e) {
            throw new RuntimeException(e); // Файл без доступа к записи
        }
    }
}