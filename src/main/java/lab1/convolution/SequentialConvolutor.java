package lab1.convolution;

public class SequentialConvolutor extends Convolutor {
    public SequentialConvolutor(Integer inputFileNr) {
        super(inputFileNr);
    }

    @Override
    public int[][] convolute() {
        for(int i=0; i<n; ++i) {
            for(int j=0; j<m; ++j) {
                convoluteElementInMatrix(i, j);
            }
        }

        return resultMatrix;
    }


}
