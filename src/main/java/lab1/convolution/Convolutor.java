package lab1.convolution;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class Convolutor {
    protected int n, m, k;
    protected int[][] matrix, convolutionMatrix;

    public Convolutor(Integer inputFileNr) throws FileNotFoundException {
        this.readFromFile("input/data" + inputFileNr + ".txt");
    }

    private void readFromFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        readSizes(scanner);
        readMatrix(scanner);
        readConvolutionalMatrix(scanner);

        scanner.close();
    }

    private void readSizes(Scanner scanner) {
        n = scanner.nextInt();
        m = scanner.nextInt();
        k = scanner.nextInt();
    }

    private void readMatrix(Scanner scanner) {
        matrix = new int[n + k - 1][m + k - 1];
        for(int i=0, k2 = k / 2; i < n; i++) {
            for(int j=0; j < m; j++) {
                matrix[i + k2][j + k2] = scanner.nextInt();
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
}
