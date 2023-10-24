package lab1.convolution;

import jdk.jshell.spi.ExecutionControl;

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
        borderMatrix();
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

    private int insideOnY(int j) {
        if(j < kHalf) {
            return kHalf;
        }
        if(j >= m + kHalf) {
            return m + kHalf - 1;
        }
        return j;
    }

    private void borderMatrix() {
        for(int i = 0; i < kHalf; ++i) {
            for(int j = 0; j < m + k - 1; ++j) {
                matrix[i][j] = matrix[kHalf][insideOnY(j)];
                matrix[n + k - 2 - i][j] = matrix[n + kHalf - 1][insideOnY(j)];
            }
        }
        for(int j = 0; j < kHalf; ++j) {
            for(int i = kHalf; i < n + kHalf; ++i) {
                matrix[i][j] = matrix[i][kHalf];
                matrix[i][m + k - 2 - j] = matrix[i][n + kHalf - 1];
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

    private void convoluteUnit(int i, int j, int di, int dj) {
        resultMatrix[i][j] += matrix[i + di][j + dj] * convolutionMatrix[di][dj];
    }

    protected void convoluteElementInMatrix(int i, int j) {
        for(int di = 0; di < k; ++di) {
            for(int dj = 0; dj < k; ++dj) {
                convoluteUnit(i, j, di, dj);
            }
        }
    }
}
