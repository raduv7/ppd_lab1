package lab1.convolution;

public class ParallelOnBlocksConvolutor extends Convolutor {
    private final Integer threadsNr;
    public ParallelOnBlocksConvolutor(Integer inputFileNr, Integer threadsNr) {
        super(inputFileNr);
        this.threadsNr = threadsNr;
    }

    @Override
    public int[][] convolute() {
        Thread[] threads = new Thread[threadsNr];

        for (int i = 0; i < threadsNr; ++i) {       /// important: n * m * threadsNr must not exceed Integer.MAX_VALUE
            threads[i] = new ParallelOnBlocksConvolutor.OnBlocksThread(i * n * m / threadsNr, (i + 1) * n * m / threadsNr);
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

    public class OnBlocksThread extends Thread {
        private final int startIndex, endIndex;

        public OnBlocksThread(int startIndex, int endIndex) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        @Override
        public void run() {
            for(int index=startIndex; index<endIndex; ++index) {
                int x = linIndexToX(index);
                int y = linIndexToY(index);
                convoluteElementInMatrix(x, y);
            }
        }

        private int linIndexToX(int index) {
            return index / m;
        }

        private int linIndexToY(int index) {
            return index % m;
        }
    }
}
