import java.io.*;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;

public class Homework5 {

  public static void main(String[] args) {
    int n = 10000000;
    long[] input = new long[n];
    if (args.length == 0) {
      for (int i = 0; i < n; i++) {
        input[i] = ThreadLocalRandom.current().nextLong(4294967296L);
      }
      compareSort(input, n);
    } else if (args.length == 1) {
      try {
        File file = new File(args[0]);
        Scanner reader = new Scanner(file);
        int i = 0;
        while (reader.hasNextLine()) {
          input[i] = Long.valueOf(reader.nextLine());
          i++;
        }
        compareSort(input, n, args[0]);
      } catch (FileNotFoundException e) {
        System.out.println("Input file not found.");
      }
    } else {
      try {
        File file = new File(args[0]);
        Scanner reader = new Scanner(file);
        int i = 0;
        while (reader.hasNextLine()) {
          input[i] = Long.valueOf(reader.nextLine());
          i++;
        }
        compareSort(input, n, args[0], args[1]);
      } catch (FileNotFoundException e) {
        System.out.println("Input file not found.");
      }
    }
  }

  private static long measureRadix(long[] input, int n) {
    long startTime = System.nanoTime();
    radixSort(input, n);
    long endTime = System.nanoTime();
    return endTime - startTime;
  }

  private static long measureQuick(long[] input, int n) {
    long startTime = System.nanoTime();
    quickSort(input, 0, n - 1);
    long endTime = System.nanoTime();
    return endTime - startTime;
  }

  private static void compareSort(long[] input, int n) {
    System.out.println("Running radixSort");
    long radixTime = measureRadix(input, n);
    System.out.println("Time to complete: " + radixTime + " nanoseconds");

    for (int i = 0; i < n; i++) {
      input[i] = ThreadLocalRandom.current().nextLong( 4294967296L);
    }

    System.out.println("Running quickSort");
    long quickTime = measureQuick(input, n);
    System.out.println("Time to complete: " + quickTime + " nanoseconds");
  }

  private static void compareSort(long[] input, int n, String fileName) {
    System.out.println("Running radixSort");
    long radixTime = measureRadix(input, n);
    System.out.println("Time to complete: " + radixTime + " nanoseconds");

    try {
      File file = new File(fileName);
      Scanner reader = new Scanner(file);
      int i = 0;
      while (reader.hasNextLine()) {
        input[i] = Long.valueOf(reader.nextLine());
        i++;
      }
      compareSort(input, n);
    } catch (FileNotFoundException e) {
      System.out.println("Input file not found.");
    }

    System.out.println("Running quickSort");
    long quickTime = measureQuick(input, n);
    System.out.println("Time to complete: " + quickTime + " nanoseconds");
  }

  private static void compareSort(long[] input, int n, String fileName, String outName) {
    System.out.println("Running radixSort");
    long radixTime = measureRadix(input, n);
    System.out.println("Time to complete: " + radixTime + " nanoseconds");

    try {
      File file = new File(fileName);
      Scanner reader = new Scanner(file);
      int i = 0;
      while (reader.hasNextLine()) {
        input[i] = Long.valueOf(reader.nextLine());
        i++;
      }
    } catch (FileNotFoundException e) {
      System.out.println("Input file not found.");
    }

    System.out.println("Running quickSort");
    long quickTime = measureQuick(input, n);
    System.out.println("Time to complete: " + quickTime + " nanoseconds");
    try {
      FileWriter fileWriter = new FileWriter(outName);
      PrintWriter printWriter = new PrintWriter(fileWriter);
      printWriter.println("Time to complete radixSort: " + radixTime + " nanoseconds");
      printWriter.println("");
      printWriter.println("Time to complete quickSort: " + quickTime + " nanoseconds");
      printWriter.close();
    } catch (IOException e) {
      System.out.println("IO exception encountered");
    }

  }

  static void radixSort(long input[], int n) {
    long m = 4294967296L;

    for (long e = 1L; m/e > 0L; e *= 128L) {
      countSort(input, n, e);
    }
  }

  static void countSort(long[] x, int n, long e) {
    long b[] = new long[n];
    int c[] = new int[128];
    Arrays.fill(c,0);
    for (int i = 0; i < n; i++) {
      c[Math.toIntExact((x[i] / e) % 128)]++;
    }
    for (int i = 1; i < 128; i++) {
      c[i] += c[i - 1];
    }
    for (int i = n - 1; i >= 0; i--) {
      b[c[Math.toIntExact((x[i]/e) % 128)] - 1] = x[i];
      c[Math.toIntExact((x[i]/e) % 128)]--;
    }
    for (int i = 0; i < n; i++) {
      x[i] = b[i];
    }
  }


  public static void quickSort(long[] input, int p, int r) {
    if (p < r){
      int q = partition(input, p, r);
      quickSort(input, p, q - 1);
      quickSort(input, q + 1, r);
    }
  }

  private static int partition(long[] input, int p, int r) {
    long x = input[r];
    int i = (p-1);
    for (int j=p; j<r; j++) {
      if (input[j] < x) {
        i++;
        long t = input[i];
        input[i] = input[j];
        input[j] = t;
      }
    }
    long t = input[i+1];
    input[i+1] = input[r];
    input[r] = t;
    return i+1;
  }


}


