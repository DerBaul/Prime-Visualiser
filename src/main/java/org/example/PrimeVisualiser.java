package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.OptionalInt;
import java.util.Vector;

public class PrimeVisualiser {
    //"imageOut/actualOut/"
    // Gewollten Pfad einfügen
    private static final String BASE_PATH = "imageOut/actualOut/";


    public static void main(String[] args) throws InterruptedException {

        SieveOfEratosthenesVis();
        PrimsPolarVis(400_000, 0.05, 20,"polar400_000.png" );
        PrimsPolarVis(40_000, 0.1, 5,"polar40_000.png" );
        PrimsPolarVis(4_000, 0.1, 5,"polar4_000.png" );

        SieveOfEratosthenesOverTimeVis();


    }

    /**
     * Erzeugt ein Bild der Primzahlen in Polardarstellung.
     * Werte sind so gewählt das ein möglichst ansprechendes Bild entsteht.
     */
    private static void PrimsPolarVis(int ceiling, double scale, int pointWidth, String fileName ) throws InterruptedException {
        Vector<Integer> primes = new Vector<>();
        try {
            primes = SieveOfEratosthenes.sieveOfEratosthenes(10, ceiling);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }

        int[] primesArray = new int[primes.size()];
        for (int i = 0; i < primes.size(); i++) {
            primesArray[i] = primes.get(i);
        }


        try{
            ImageCreator.createPolarImageFromIntArray(primesArray, scale, pointWidth, BASE_PATH+fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Erzeugt ein Bild der Prim- und Nichtprimzahlen.
     * Die Zahlen sind in einem 2D Array angeordnet.
     * Werte sind so gewählt das ceiling und Seitenverhältnis keiner Füllpixel bedarf.
     */
    private static void SieveOfEratosthenesVis() {
        int ceiling = 40_000;
        try {
            // Vom ceiling wird eins abgezogen damit das Array am Ende Ceiling viele Werte enthält.
            // Erleichtert das nachdenken über Seitenverhältnisse.
            boolean[] primes = SieveOfEratosthenes.sieveOfEratosthenesBool(5,ceiling-1, OptionalInt.empty());

            try {
                // Create an image and save it as "output.png"
                ImageCreator.createImageFromBooleanArray(primes, 1, 1, BASE_PATH + "primesGrid.png");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Erzeugt mehrere Bilder im Ordner "/sieveAlgoOverTime"
     * Die Bilder zeigen in ihrem Verlauf das Vorgehen des Algorithmus.
     * Um nicht unnötig viele Bilder zu generieren, wird die Schrittweite
     */
    private static void SieveOfEratosthenesOverTimeVis() {
        int ceiling = 40_000;
        try {

            // Die Fibonacci-Zahl bieten im unteren Bereich mehr Detail als z.B. 2^n
            int fiba = 0;
            int fibb = 1;
            while (true) {
                if (fiba > ceiling){
                    break;
                }
                fiba += fiba;
                int fibnext = fiba+fibb;
                fiba=fibb;
                fibb = fibnext;
                // Vom ceiling wird eins abgezogen damit das Array am Ende Ceiling viele Werte enthält.
                // Erleichtert das nachdenken über Seitenverhältnisse.
                boolean[] primes = SieveOfEratosthenes.sieveOfEratosthenesBool(5, ceiling - 1, OptionalInt.of(fiba));

                try {
                    // Erstell den Ordner in den dei Bilder gespeichert werden.
                    Path newFolderPath = Paths.get(BASE_PATH + "sieveAlgoOverTime");
                    if (!(Files.exists(newFolderPath) && Files.isDirectory(newFolderPath))) {
                        Files.createDirectories(newFolderPath);
                    }

                    String path = BASE_PATH+"sieveAlgoOverTime/PrimesUpTo" + fiba + ".png";
                    ImageCreator.createImageFromBooleanArray(primes, 1, 1, path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}