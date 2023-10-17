package lab1.convolution;

import java.io.FileNotFoundException;

public class ParallelConvolutor extends Convolutor {
    private Integer threadsNr;
    public ParallelConvolutor(Integer inputFileNr, Integer threadsNr) throws FileNotFoundException {
        super(inputFileNr);
        this.threadsNr = threadsNr;
    }

    @Override
    public int[][] convolute() {
        return new int[0][];
    }
}
