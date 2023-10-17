package lab1.convolution;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class Convolutor {
    protected int n, m, k, kHalf;
    protected int[][] matrix, convolutionMatrix, resultMatrix;

    public Convolutor(Integer inputFileNr) {
        this.readFromFile("input/data" + inputFileNr + ".txt");
    }

    private void readFromFile(String fileName) {
        File file = new File(fileName);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        readSizes(scanner);
        readMatrix(scanner);
        readConvolutionalMatrix(scanner);
        resultMatrix = new int[n][m];

        scanner.close();
    }

    private void readSizes(Scanner scanner) {
        n = scanner.nextInt();
        m = scanner.nextInt();
        k = scanner.nextInt();
        kHalf = k / 2;
    }

    private void readMatrix(Scanner scanner) {
        matrix = new int[n + k - 1][m + k - 1];
        for(int i=0; i < n; i++) {
            for(int j=0; j < m; j++) {
                matrix[i + kHalf][j + kHalf] = scanner.nextInt();
            }
        }
    }

    private void readConvolutionalMatrix(Scanner scanner) {
        convolutionMatrix = new int[k][k];
        for(int i=0; i<k; i++) {
            for(int j=0; j<k; j++) {
                convolutionMatrix[i][j] = scanner.nextInt();
            }
        }
    }

    public abstract int[][] convolute();

    protected void convoluteUnit(int i, int j, int di, int dj) {
        resultMatrix[i][j] += matrix[i + di][j + dj] * convolutionMatrix[di][dj];
    }
}
