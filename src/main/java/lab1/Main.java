package lab1;

import lab1.convolution.*;

public class Main {
    public static void main(String[] args) {
        assert args.length >= 3;

        Integer strategyNr = Integer.parseInt(args[0]);
        assert strategyNr >= 0 && strategyNr < 4;
        Integer inputFileNr = Integer.parseInt(args[1]);
        assert inputFileNr >= 0 && inputFileNr < 4;
        Integer threadsNr = Integer.parseInt(args[2]);
        assert threadsNr >= 0 && threadsNr < 100;

        Convolutor convolutor = null;
        if(strategyNr == 0) {
            convolutor = new SequentialConvolutor(inputFileNr);
        } else if(strategyNr == 1) {
            convolutor = new ParallelOnLinesConvolutor(inputFileNr, threadsNr);
        } else if(strategyNr == 2) {
            convolutor = new ParallelOnBlocksConvolutor(inputFileNr, threadsNr);
        } else if(strategyNr == 3) {
            convolutor = new ParallelHashConvolutor(inputFileNr, threadsNr);
        }

        long startTime = System.currentTimeMillis();

        convolutor.convolute();

        long endTime = System.currentTimeMillis();

        System.out.println(((double) startTime - endTime) / 1000);
    }
}