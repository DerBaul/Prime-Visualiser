import org.example.ImageCreator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ImageCreatorTest {
private final String BASE_PATH = "imageOut/testOut/";
    @Test
    void testCreateImageFromBooleanArray_normalInput() {
        boolean[] pixels = new boolean[16*9];
        for (int i = 0; i < pixels.length; i++) {
            if (i % 2 == 0){
                pixels[i] = true;
            } else {
                pixels[i] = false;
            }
        }

        String filePath = BASE_PATH + "output_test1.png";
        //assertDoesNotThrow(() -> {}) erklärt.
        //https://www.petrikainulainen.net/programming/testing/junit-5-tutorial-writing-assertions-with-junit-5-api/
        assertDoesNotThrow(() -> ImageCreator.createImageFromBooleanArray(pixels, 16, 9, filePath));
        assertTrue(new File(filePath).exists());
    }

    @Test
    void testCreateImageFromBooleanArray_emptyArray() {
        boolean[] pixels = new boolean[0];

        String filePath = BASE_PATH + "output_test2.png";
        assertThrowsExactly(IllegalArgumentException.class, () -> ImageCreator.createImageFromBooleanArray(pixels, 16, 9, BASE_PATH));
    }

    @Test
    void testCreateImageFromBooleanArray_largeArray() {
        boolean[] pixels = new boolean[10_000_000]; // 10 Millionen Pixel
        for (int i = 0; i < pixels.length; i++) {
            if (i % 10 == 0){
                pixels[i] = true;
            } else {
                pixels[i] = false;
            }
        }

        String filePath = BASE_PATH + "output_test3.png";
        assertDoesNotThrow(() -> ImageCreator.createImageFromBooleanArray(pixels, 1, 1, filePath));
        assertTrue(new File(filePath).exists());
    }

    @Test
    void testCreateImageFromBooleanArray_negativeAspektRatio() {
        boolean[] pixels = new boolean[9];
        Arrays.fill(pixels, true);

        String filePath = BASE_PATH + "output_test4.png";
        assertThrowsExactly(IllegalArgumentException.class, () -> ImageCreator.createImageFromBooleanArray(pixels, -1, 1, BASE_PATH));
    }

    @Test
    void testCreatePolarImageFromIntArray_normalInput() {
        int[] pixels = {10, 20, 30, 40, 50};
        String filePath = BASE_PATH + "polar_test1.png";

        assertDoesNotThrow(() -> ImageCreator.createPolarImageFromIntArray(pixels, 1.0, 5, filePath));
        assertTrue(new File(filePath).exists());
    }

    @Test
    void testCreatePolarImageFromIntArray_wrongInputs() {
        int[] pixels = new int[0];
        String filePath = BASE_PATH + "polar_test2.png";
        assertThrowsExactly(IllegalArgumentException.class, () -> ImageCreator.createPolarImageFromIntArray(pixels, 1.0, 5, filePath));

        int[] pixels2 = new int[9];
        //Scale über 1
        assertThrowsExactly(IllegalArgumentException.class, () -> ImageCreator.createPolarImageFromIntArray(pixels2, 2.0, 5, filePath));
        //PointWidth 0
        assertThrowsExactly(IllegalArgumentException.class, () -> ImageCreator.createPolarImageFromIntArray(pixels2, 1.0, 0, filePath));
    }

    @Test
    void testCreatePolarImageFromIntArray_largePoints() {
        // Es gibt keinen technischen Grund warum man die Point Größe beschränken sollte.
        int[] pixels = {100, 200, 300, 400, 500};
        String filePath = BASE_PATH + "polar_test4.png";

        assertDoesNotThrow(() -> ImageCreator.createPolarImageFromIntArray(pixels, 1.0, 5000, filePath));
        assertTrue(new File(filePath).exists());
    }

    @Test
    void testCreatePolarImageFromIntArray_extremeValues() {
        int[] pixels = { Integer.MAX_VALUE, 3};
        String filePath = BASE_PATH + "polar_test5.png";

        assertThrowsExactly(IllegalArgumentException.class, () -> ImageCreator.createPolarImageFromIntArray(pixels, 1.0, 5, filePath));
    }
}
