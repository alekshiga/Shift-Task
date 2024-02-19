import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Solution {

    static boolean appendMode = false;
    static boolean fullStats = true;   //todo false, add for loop
    static boolean shortStats = false;

    static int stringCount = 0;
    static int minLength = Integer.MAX_VALUE;
    static int maxLength = 0;

    static int intCount = 0;
    static int minInt = Integer.MAX_VALUE;
    static int maxInt = Integer.MIN_VALUE;
    static double sumInt = 0;
    static double averageInt = 0;

    static int floatCount = 0;
    static float minFloat = Float.POSITIVE_INFINITY;
    static float maxFloat = Float.MIN_VALUE;
    static double sumFloat = 0;
    static double averageFloat = 0;
    static String outputPath = "";
    static String filePrefix = "";

    public static void main(@NotNull String[] args) {

        if (args.length < 1) {
            System.out.print("Не найдено входных файлов");
            return;
        }

        List<String> inputFileNames = new ArrayList<>(List.of(args));

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

        String intOutputFile = filePrefix + "integers.txt";
        String floatOutputFile = filePrefix + "floats.txt";
        String stringOutputFile = filePrefix + "strings.txt";

        if (!outputPath.isEmpty()) {
            intOutputFile = outputPath + File.separator + filePrefix + "integers.txt";
            floatOutputFile = outputPath + File.separator + filePrefix + "floats.txt";
            stringOutputFile = outputPath + File.separator + filePrefix + "strings.txt";
        }
        
        BufferedWriter intWriter = null;
        BufferedWriter floatWriter = null;
        BufferedWriter stringWriter = null;

        for (String filename : inputFileNames) {

            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;

                while ((line = reader.readLine()) != null) {

                    if (typeSorter(line) == 0) {  // integer
                        intCount++;
                        if (intWriter == null) {
                            intWriter = new BufferedWriter(new FileWriter(intOutputFile, appendMode));
                        }
                        if (fullStats) {
                            int num = Integer.parseInt(line);
                            minInt = Math.min(num, minInt);
                            maxInt = Math.max(num, maxInt);
                            sumInt += num;
                        }
                        intWriter.write(line);
                        intWriter.newLine();
                    } else if (typeSorter(line) == 1) {  // float
                        floatCount++;
                        if (floatWriter == null) {
                            floatWriter = new BufferedWriter(new FileWriter(floatOutputFile, appendMode));
                        }
                        if (fullStats) {
                            float num = Float.parseFloat(line);
                            minFloat = Math.min(num, minFloat);
                            maxFloat = Math.max(num, maxFloat);
                            sumFloat += num;
                        }
                        floatWriter.write(line);
                        floatWriter.newLine();
                    } else if (typeSorter(line) == 2) {  // string
                        stringCount++;
                        if (stringWriter == null) {
                            stringWriter = new BufferedWriter(new FileWriter(stringOutputFile, appendMode));
                        }
                        if (fullStats) {
                            if (line.length() < minLength) {
                                minLength = line.length();
                            }
                            if (line.length() > maxLength) {
                                maxLength = line.length();
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


            if (shortStats) {
                System.out.println("Краткая статистика:");
                System.out.println("            int    " + "    float    " + "  string  ");
                System.out.println("Кол-во " + String.format("%8d", intCount) + String.format("%13d", floatCount) + String.format("%12d", stringCount));
            } else if (fullStats) {
                System.out.println("\nПолная статистика");
                System.out.println("              int    " + "      float    " + "  string  ");
                System.out.println("Кол-во " + String.format("%10d", intCount) + String.format("%15d", floatCount) + String.format("%12d", stringCount));
                System.out.println("min " + String.format("%13d", minInt) + String.format("%15f", minFloat) + String.format("%12d", minLength));
                System.out.println("max " + String.format("%13d", maxInt) + String.format("%15f", maxFloat) + String.format("%12d", maxLength));
                System.out.println("sum " + String.format("%13.2f", sumInt) + String.format("%15.2f", sumFloat));
                System.out.println("avg " + String.format("%13.4f", averageInt) + String.format("%15.4f", averageFloat));
            }


        }
        System.out.println("\nДанные файлов отсортированы");
    }

    private static int typeSorter(String line) {
        try {
            @SuppressWarnings({"unused"})
            int intValue = Integer.parseInt(line);
            return 0; // if integer
        } catch (NumberFormatException e1) {
            try {
                @SuppressWarnings({"unused"})
                float floatValue = Float.parseFloat(line);
                return 1; // if float
            } catch (NumberFormatException e2) {
                return 2;  // if string
            }
        }
    }

}