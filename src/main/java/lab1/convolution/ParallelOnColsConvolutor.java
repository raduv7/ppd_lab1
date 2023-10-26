package lab1.convolution;

public class ParallelOnColsConvolutor extends Convolutor {
    private final Integer threadsNr;
    public ParallelOnColsConvolutor(Integer inputFileNr, Integer threadsNr) {
        super(inputFileNr);
        this.threadsNr = threadsNr;
    }

    @Override
    public int[][] convolute() {
        Thread[] threads = new Thread[threadsNr];

        for (int i = 0; i < threadsNr; ++i) {
            threads[i] = new OnColsThread(i * n / threadsNr, (i + 1) * n / threadsNr);
            threads[i].start();
        }
        for (int i = 0; i < threadsNr; ++i) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return resultMatrix;
    }

    public class OnColsThread extends Thread {
        private final int startCol, endCol;

        public OnColsThread(int startCol, int endCol) {
            this.startCol = startCol;
            this.endCol = endCol;
        }

        @Override
        public void run() {
            for(int i=0; i<n; ++i) {
                for(int j=startCol; j<endCol; ++j) {
                    convoluteElementInMatrix(i, j);
                }
            }
        }
    }
}
