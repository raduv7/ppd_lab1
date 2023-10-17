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

        for (int i = 0; i < threadsNr; ++i) {
            threads[i] = new ParallelOnBlocksConvolutor.OnBlocksThread(i * n / threadsNr, (i + 1) * n / threadsNr);
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
        private final int startLine, endLine;

        public OnBlocksThread(int startLine, int endLine) {
            this.startLine = startLine;
            this.endLine = endLine;
        }

        @Override
        public void run() {
            for(int i=startLine; i<endLine; ++i) {
                for(int j=0; j<m; ++j) {
                    for(int di = 0; di < k; ++di) {
                        for(int dj = 0; dj < k; ++dj) {
                            convoluteUnit(i, j, di, dj);
                        }
                    }
                }
            }
        }
    }
}
