package lab1;

public class Main {
    public static void main(String[] args) {
        assert args.length >= 2;

        Integer strategyNr = Integer.parseInt(args[0]);
        assert strategyNr >= 0 && strategyNr < 4;
        Integer threadsNr = Integer.parseInt(args[0]);
        assert threadsNr >= 0 && threadsNr < 100;
        Integer inputFileNr = Integer.parseInt(args[1]);
        assert inputFileNr >= 0 && inputFileNr < 4;



        long startTime = System.currentTimeMillis();

        long endTime = System.currentTimeMillis();

        System.out.println(((double) startTime - endTime) / 1000);
    }
}