package org.example;

import java.net.InterfaceAddress;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.Vector;

//Zum Verständnis
//http://www.shodor.org/media/content//petascale/materials/UPModules/sieveOfEratosthenes/module_document_pdf.pdf
public class SieveOfEratosthenes {

    /**
     * Berechnet alle Primzahlen bis zur gegebenen Obergrenze.
     *
     * @param threadCount Anzahl der verwendeten Threads
     * @param ceiling   Obergrenze für Primzahl berechnung
     * @return Gibt Primzahlen als Vector<Integer> aus
     */
    public static Vector<Integer> sieveOfEratosthenes(int threadCount, int ceiling) throws InterruptedException {
        boolean[] boolArray = sieveOfEratosthenesBool(threadCount, ceiling, OptionalInt.empty());

        Vector<Integer> indexArray = new Vector<>();
        for (int i = 2; i < boolArray.length; i++) {
            if (boolArray[i] ){
                indexArray.add(i);
            }
        }
        return indexArray;
    }

    /**
     * Berechnet alle Primzahlen bis zur gegebenen Obergrenze.
     *
     * @param threadCount Anzahl der verwendeten Threads
     * @param ceiling   Obergrenze für Primzahl berechnung
     * @param stopAt    Limit für das Durchlaufen des Algorithmus.
     * @return boolean[] in der Länge der Obergrenze. Primzahlen sind als True markiert.
     */
    public static boolean[] sieveOfEratosthenesBool(int threadCount, int ceiling, OptionalInt stopAt) throws InterruptedException {

        if(threadCount < 1) {
            throw new IllegalArgumentException("Thread count must be greater than 0.");
        }
        if(ceiling <= 2) {
            throw new IllegalArgumentException("Ceiling must be greater than 2.");
        }


        boolean[] isPrime = new boolean[ceiling+1];
        int blockSize = (ceiling + threadCount - 1) / threadCount;
        Vector<Thread> threads = new Vector<>();


        Arrays.fill(isPrime, true);
        isPrime[0] = false;
        isPrime[1] = false;

        for (int i=0; i<threadCount; i++) {
            int blockStart = i*blockSize;
            int blockEnd = Math.min(blockStart + blockSize, ceiling);


            Thread thread = new Thread(() -> {
                for (int j = 2; j*j < blockEnd; j++) {
                    if(stopAt.isPresent() && stopAt.getAsInt() <= j) {
                        break;
                    }
                    if (isPrime[j]) {
                        int start = Math.max(j * j, (blockStart + j - 1) / j * j); // Start innerhalb des Blocks
                        for (int x = start; x < blockEnd; x += j) {
                            isPrime[x] = false;
                        }
                    }
                }
            });

            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        return isPrime;
    }
}
