package lab1.convolution;

import java.util.HashMap;
import java.util.Map;

public class ParallelHashConvolutor extends Convolutor {
    private final Integer threadsNr;
    public ParallelHashConvolutor(Integer inputFileNr, Integer threadsNr) {
        super(inputFileNr);
        this.threadsNr = threadsNr;
    }

    @Override
    public int[][] convolute() {
        Thread[] threads = new Thread[threadsNr];
        HashMap<Integer, Integer> hashMap = populateHashMap();

        for (int i = 0; i < threadsNr; ++i) {
            threads[i] = new ParallelHashConvolutor.HashThread(i, hashMap);
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

    private int hashMultiplicative(int value) {
        double A = (Math.sqrt(5) - 1) / 2; // golden ratio fraction
        return (int) (threadsNr * (value * A % 1));
    }
    private HashMap<Integer, Integer> populateHashMapMultiplicative() {
        HashMap<Integer, Integer> hashMap = new HashMap<>();

        for(int i = 0; i < n*m; ++i) {
            int hash = hashMultiplicative(i);
            hashMap.put(i, hash);
        }

        return hashMap;
    }

    private int hashBitwiseXor(int value) {
        int hash = value;
        hash = (hash ^ (hash >>> 8)) & (threadsNr - 1); // Assuming x is a power of 2
        return hash;
    }
    private HashMap<Integer, Integer> populateHashMapBitwise() {
        HashMap<Integer, Integer> hashMap = new HashMap<>();

        for(int i = 0; i < n*m; ++i) {
            int hash = hashBitwiseXor(i);
            hashMap.put(i, hash);
        }

        return hashMap;
    }

    private HashMap<Integer, Integer> populateHashMap() {
        return populateHashMapMultiplicative();
//        return populateHashMapBitwise();
    }

    public class HashThread extends Thread {
        private final int id;
        private final HashMap<Integer, Integer> hashMap;

        public HashThread(int id, HashMap<Integer, Integer> hashMap) {
            this.id = id;
            this.hashMap = hashMap;
        }

        @Override
        public void run() {
            hashMap.entrySet()
                    .stream()
                    .filter(hashMapEntry -> hashMapEntry.getValue() == id)
                    .forEach(hashMapEntry -> {
                        int x = linIndexToX(hashMapEntry.getKey());
                        int y = linIndexToY(hashMapEntry.getKey());
                        convoluteElementInMatrix(x, y);
                    });
        }

        private int linIndexToX(int index) {
            return index / m;
        }

        private int linIndexToY(int index) {
            return index % m;
        }
    }
}
