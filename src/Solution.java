import java.io.*;

public class Solution {

    public static final String intOutputFile = "bin/integers.txt";
    public static final String floatOutputFile = "bin/floats.txt";
    public static final String stringOutputFile = "bin/strings.txt";

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.print("Не найдено входных файлов");
            return;
        }

        BufferedWriter intWriter = null;
        BufferedWriter floatWriter = null;
        BufferedWriter stringWriter = null;

        for (String filename : args) {

            try (BufferedReader reader = new BufferedReader(new FileReader("bin/input.txt"))) {
                String line;

                while ((line = reader.readLine()) != null) {

                    if (typeSorter(line) == 0) {  // integer
                        if (intWriter == null) {
                            intWriter = new BufferedWriter(new FileWriter(intOutputFile));
                        }
                        intWriter.write(line);
                        intWriter.newLine();
                    } else if (typeSorter(line) == 1) {  // float
                        if (floatWriter == null) {
                            floatWriter = new BufferedWriter(new FileWriter(floatOutputFile));
                        }
                        floatWriter.write(line);
                        floatWriter.newLine();
                    } else if (typeSorter(line) == 2) {  // string
                        if (stringWriter == null) {
                            stringWriter = new BufferedWriter(new FileWriter(stringOutputFile));
                        }
                        stringWriter.write(line);
                        stringWriter.newLine();
                    } else {
                        throw new RuntimeException("Вы не должны были сюда попасть");
                    }
                }

            } catch (IOException e) {
                System.err.println("Ошибка чтения файла " + ": " + e.getMessage());
            }
        }
        try {
            intWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            floatWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            stringWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Данные файлов отсортированы");
    }

    private static int typeSorter(String line) {
        try {
            int intValue = Integer.parseInt(line);
            return 0; // if integer
        } catch (NumberFormatException e1) {
            try {
                float floatValue = Float.parseFloat(line);
                return 1; // if float
            } catch (NumberFormatException e2) {
                return 2;  // if string
            }
        }
    }

}