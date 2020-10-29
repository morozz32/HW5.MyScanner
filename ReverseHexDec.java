import java.util.Arrays;
import java.io.*;

public class ReverseHexDec {
    public static void main(String[] args) {
        try (MyScanner sc = new MyScanner(System.in, new MyScanner.Separator() {
                public boolean isSeparator(char c) {
                    return Character.isWhitespace(c);
                }
        })) {
            int f = 0;
            int[][] matrix = new int[1][];
            matrix[0] = new int[1];
            int[] numCount = new int[1];
            while (sc.hasNext()) {
                Integer elem = sc.nextInt();
                if (sc.getCounter() > 0) {
                    f += sc.getCounter();
                    matrix = doubleArray(matrix, f);
                    numCount = doubleArray(numCount, f);
                    matrix[f] = new int[1];
                    sc.setCounter(0);
                } else {
                    matrix[f] = doubleArray(matrix[f], numCount[f]);
                }
                matrix[f][numCount[f]] = elem;
                numCount[f]++;
            }
            f += sc.getCounter();
            numCount = doubleArray(numCount, f);
            
            for (int i = f - 1; i >= 0; i--) {
                for (int j = numCount[i] - 1; j >= 0; j--) {
                    System.out.print(matrix[i][j]);
                    System.out.print(" ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Input exception");
        } 
    }
    
    public static int[] doubleArray(int[] arr, int size) {
        if (size >= arr.length) {
            arr = Arrays.copyOf(arr, 2 * size);
        }
        return arr;
    }
    
    public static int[][] doubleArray(int[][] arr, int size) {
        if (size >= arr.length) {
            arr = Arrays.copyOf(arr, 2 * size);
        }
        return arr;
    }
}
