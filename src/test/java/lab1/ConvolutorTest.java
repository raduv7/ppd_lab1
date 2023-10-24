package lab1;

import lab1.convolution.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ConvolutorTest {
    private static long targetSum;
    private static int n, m, k, kHalf;

    @BeforeAll
    public static void setUp() {
        File file = new File("input/data1.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        readSizes(scanner);

        scanner.close();

        Convolutor convolutor = new SequentialConvolutor(1);
        targetSum = sumMatrix(convolutor.convolute(), n, m);
    }

    private static void readSizes(Scanner scanner) {
        n = scanner.nextInt();
        m = scanner.nextInt();
        k = scanner.nextInt();
        kHalf = k / 2;
    }

    @Test
    public void testParallelHashConvolutor() {
        Convolutor convolutor = new ParallelHashConvolutor(1, 8);
        assertConvolution(convolutor.convolute());
    }

    @Test
    public void testParallelOnLinesConvolutor() {
        Convolutor convolutor = new ParallelOnLinesConvolutor(1, 8);
        assertConvolution(convolutor.convolute());
    }

    @Test
    public void testParallelOnBlocksConvolutor() {
        Convolutor convolutor = new ParallelOnBlocksConvolutor(1, 8);
        assertConvolution(convolutor.convolute());
    }

    @Test
    public void testSequentialConvolutor() {
        Convolutor convolutor = new SequentialConvolutor(1);
        assertConvolution(convolutor.convolute());
    }

    private void assertConvolution(int[][] resultMatrix) {
        assert sumMatrix(resultMatrix, n, m) == targetSum;
    }

    private static long sumMatrix(int[][] resultMatrix, int n, int m) {
        long sum = 0;

        for(int i=0; i < n; i++) {
            for(int j=0; j < m; j++) {
                sum += resultMatrix[i][j];
            }
        }

        return sum;
    }
}
