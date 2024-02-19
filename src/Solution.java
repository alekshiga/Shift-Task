import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Solution {

    public static void main(@NotNull String[] args) {

        if (args.length < 1) {
            System.out.print("Не найдено входных файлов");
            return;
        }

        List<String> inputFileNames = new ArrayList<>(List.of(args));
        String outputPath = "";
        String filePrefix = "";
        boolean appendMode = false;
        boolean fullStats = false;
        boolean shortStats = false;

        int stringCount = 0;
        int minLength = Integer.MAX_VALUE;
        int maxLength = 0;

        int intCount = 0;
        int minInt = Integer.MAX_VALUE;
        int maxInt = Integer.MIN_VALUE;
        double sumInt = 0;
        double averageInt = 0;

        int floatCount = 0;
        float minFloat = Float.MAX_VALUE;
        float maxFloat = Float.MIN_VALUE;
        double sumFloat = 0;
        double averageFloat = 0;

        for (int i = 0; i < args.length; ++i) {
            if (args[i].equals("-o") && i < args.length - 1) {
                outputPath = args[i + 1];
                inputFileNames.remove(i);
                inputFileNames.remove(i + 1);
            } else if (args[i].equals("-p") && i < args.length - 1) {
                filePrefix = args[i + 1];
                inputFileNames.remove(i);
                inputFileNames.remove(i + 1);
            }
            else if (args[i].equals("-a")) {
                appendMode = true;
                inputFileNames.remove(i);
            }
            else if (args[i].equals("-s")) {
                shortStats = true;
                inputFileNames.remove(i);
            }
            else if (args[i].equals("-f")) {
                fullStats = true;
                inputFileNames.remove(i);
            }
        }

        final String intOutputFile = outputPath + File.separator + filePrefix + "integers.txt";
        final String floatOutputFile = outputPath + File.separator + filePrefix + "floats.txt";
        final String stringOutputFile = outputPath + File.separator + filePrefix + "strings.txt";

        BufferedWriter intWriter = null;
        BufferedWriter floatWriter = null;
        BufferedWriter stringWriter = null;

        for (String filename : inputFileNames) {

            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;

                while ((line = reader.readLine()) != null) {

                    if (typeSorter(line) == 0) {  // integer
                        if (intWriter == null) {
                            intWriter = new BufferedWriter(new FileWriter(intOutputFile, appendMode));
                            if (shortStats || fullStats) {
                                intCount++;
                            }
                            if (fullStats) {
                                int num = Integer.parseInt(line);
                                minInt = Integer.min(num, minInt);
                                maxInt = Integer.max(num, maxInt);
                                sumInt += num;
                            }
                        }
                        intWriter.write(line);
                        intWriter.newLine();
                    } else if (typeSorter(line) == 1) {  // float
                        if (floatWriter == null) {
                            floatWriter = new BufferedWriter(new FileWriter(floatOutputFile, appendMode));
                            if (shortStats || fullStats) {
                                floatCount++;
                            }
                            if (fullStats) {
                                float num = Float.parseFloat(line);
                                minFloat = Float.min(num, minFloat);
                                maxFloat = Float.max(num, maxFloat);
                                sumFloat += num;
                            }
                        }
                        floatWriter.write(line);
                        floatWriter.newLine();
                    } else if (typeSorter(line) == 2) {  // string
                        if (stringWriter == null) {
                            stringWriter = new BufferedWriter(new FileWriter(stringOutputFile, appendMode));
                            if (shortStats || fullStats) {
                                stringCount++;
                            }
                            if (fullStats) {
                                minLength = Integer.min(minLength, line.length());
                                maxLength = Integer.max(maxLength, line.length());
                            }
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
            assert intWriter != null;
            intWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            assert floatWriter != null;
            floatWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            assert stringWriter != null;
            stringWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (shortStats || fullStats) {
            System.out.println("            Краткая статистика:");
            System.out.println(String.format("%8d", "int") + String.format("%8d", "float") + String.format("%8d", "string"));
            System.out.println("Кол-во " + String.format("%8d", intCount) + String.format("%8d", floatCount) + String.format("%8d", stringCount));
        }

        if (fullStats) {
            System.out.println("            Полная статистика");
            System.out.println("min " + String.format("%8c", minInt) + String.format("%8d", minFloat) + String.format("%8d", minLength));
            System.out.println("max " + String.format("%8d", maxInt) + String.format("%8d", maxFloat) + String.format("%8d", maxLength));
            System.out.println("sum " + String.format("%8d", sumInt) + String.format("%8d", sumFloat));
            System.out.println("avg " + String.format("%8d", averageInt) + String.format("%8d", averageFloat));
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