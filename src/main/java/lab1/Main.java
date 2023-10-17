package labs;

import java.time.Clock;

public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        long endTime = System.currentTimeMillis();

        System.out.println(((double) startTime - endTime) / 1000);
    }
}