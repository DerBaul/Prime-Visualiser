package org.example;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class ImageCreator {

    /**
     * Nimmt ein Boolean Array und generiert ein Bild daraus.
     * Lassen sich die Pixel nicht gut auf das Seitenverhältnis aufteilen, wird mit Schwarz aufgefüllt.
     *
     * @param pixels   Das Boolean Array, das als Pixel interpretiert wird.
     * @param widthRatio    Breite vom Seitenverhältnis
     * @param heightRatio   Höhe vom Seitenverhältnis
     * @param filePath Wo das Bild abgespeichert wird.
     * @throws IOException
     */
    public static void createImageFromBooleanArray(boolean[] pixels, int widthRatio, int heightRatio, String filePath) throws IOException {
        if (widthRatio<1 || heightRatio<1) {
            throw new IllegalArgumentException("Width and height must be positive");
        }

        //Seitenverhältnis berechnen
        int pixelAnzhal = pixels.length;
        double aspectRatio = (double) widthRatio / heightRatio;
        int width = (int) Math.sqrt(pixelAnzhal * aspectRatio);
        int height = (int) Math.ceil((double) pixelAnzhal / width);

        // Create a BufferedImage
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

        // Setzt jeden Pixel auf Schwarz
        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.dispose();

        // Jeden Pixel abgehen und dessen Wert im Bool Array checken.
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                boolean value;
                if (index<pixelAnzhal){
                     value = pixels[index];
                }else {
                     value = false;
                }
                if (value){
                    image.setRGB(x, y, 0xFFFFFF);
                }
            }
        }


        File outputFile = new File(filePath);
        try{
            ImageIO.write(image, "png", outputFile);
        }catch (IOException e){
            throw new IOException("Beim speichern des Bildes ist ein Fehler aufgetreten.");
        }

        System.out.println("Bild erstellt: " + filePath);
    }


    /**
     * Nimmt ein int Array und generiert ein Bild in dem die Werte des Arrays, in Polarkoordinaten angezeigt sind.
     *
     * @param pixels    Array von positiven int Werten
     * @param scale     Wert zwischen 1 und 0
     * @param pointWidth    Wie dick jeder Punkt gezeichnet wird.
     * @param filePath      Wo das Bild gespeichert wird.
     */
    public static void createPolarImageFromIntArray(int[] pixels, double scale, int pointWidth, String filePath ) throws IOException {
        if (pixels.length<1){
            throw new IllegalArgumentException("Pixels must have at least one pixel");
        } else if (scale>1 || scale<=0) {
            throw new IllegalArgumentException("Scale must be between 1 and 0");
        } else if (pointWidth<1) {
            throw new IllegalArgumentException("PointWidth must be positive");
        } else if (Arrays.stream(pixels).max().getAsInt()*scale*2 > 46_000 ) {
            throw new IllegalArgumentException("pixels has Values that are to big for BufferedImage to handle.");
            
        }

        //Das Bild muss so Breit sein wie der größte Wert entfernt ist.
        int width = (int) (Arrays.stream(pixels).max().getAsInt()*2*scale);
        int center = (int) (width/2);

        BufferedImage image = new BufferedImage(width, width, BufferedImage.TYPE_BYTE_BINARY);
        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, width);

        g.setColor(Color.WHITE);
        for (int i : pixels) {
            int x = (int) (center + i * Math.cos(i)*scale);
            int y = (int) (center + i * Math.sin(i)*scale);
            g.fillRect(x-pointWidth/2, y-pointWidth/2, pointWidth, pointWidth);
        }

        File outputFile = new File(filePath);
        try{
            ImageIO.write(image, "png", outputFile);
        }catch (IOException e){
            throw new IOException("Beim Speichern des Bildes ist ein Fehler aufgetreten.");
        }

        System.out.println("Bild erstellt: " + filePath);

    }
}
