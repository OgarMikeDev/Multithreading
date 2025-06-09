import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class MainRunnableAndThread {
    private static final String pathToSrcFolder = "src/main/resources/data/src_folder";
    private static final String pathToDstFolder = "src/main/resources/data/dst_folder";
    private static int newWidth = 300;
    private static long start = System.currentTimeMillis();

    public static void main(String[] args) {
        File fileSrcFolder = new File(pathToSrcFolder);
        File[] filesAllImages = fileSrcFolder.listFiles();

        int middle = filesAllImages.length / 2;
        File[] filesImages1 = new File[middle];
        System.arraycopy(filesAllImages, 0, filesImages1, 0, filesImages1.length);
        ImageResizerThread imageResizerThread1 = new ImageResizerThread(pathToDstFolder, newWidth, filesImages1, start);
        //TODO реализация потока через класс Thread
        //imageResizerThread1.start();
        //TODO реализация потока через интерфейс Runnable
        new Thread(imageResizerThread1).start();

        File[] filesImages2 = new File[filesAllImages.length - filesImages1.length];
        System.arraycopy(filesAllImages, middle, filesImages2, 0, filesImages2.length);
        ImageResizerThread imageResizerThread2 = new ImageResizerThread(pathToDstFolder, newWidth, filesImages2, start);
        //TODO реализация потока через класс Thread
        //imageResizerThread2.start();
        //TODO реализация потока через интерфейс Runnable
        new Thread(imageResizerThread2).start();
    }
}
class ImageResizerRunnable implements Runnable {
    String pathToDstFolder;
    int newWidth;
    File[] filesAllImages;
    long start;

    public ImageResizerRunnable(String pathToDstFolder, int newWidth, File[] filesAllImages, long start) {
        this.pathToDstFolder = pathToDstFolder;
        this.newWidth = newWidth;
        this.filesAllImages = filesAllImages;
        this.start = start;
    }
    @Override
    public void run() {
        try {
            for (File currentFileImage : filesAllImages) {
                BufferedImage originBufferedImage = ImageIO.read(currentFileImage);
                if (originBufferedImage == null) {
                    continue;
                }

                int newHeight = Math.round(
                        originBufferedImage.getHeight() / (originBufferedImage.getWidth() / newWidth)
                );

                BufferedImage endBufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

                int stepWidth = originBufferedImage.getWidth() / newWidth;
                int stepHeight = originBufferedImage.getHeight() / newHeight;

                for (int x = 0; x < newWidth; x++) {
                    for (int y = 0; y < newHeight; y++) {
                        int rgb = originBufferedImage.getRGB(x * stepWidth, y * stepHeight);
                        endBufferedImage.setRGB(x, y, rgb);
                    }
                }

                File endFileImage = new File(pathToDstFolder + "/" + currentFileImage.getName());
                ImageIO.write(endBufferedImage, "jpg", endFileImage);
            }
            long end = System.currentTimeMillis();
            long difference = end - start;
            System.out.println("Time work program: " + difference);
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
}

class ImageResizerThread extends Thread {
    String pathToDstFolder;
    int newWidth;
    File[] filesAllImages;
    long start;

    public ImageResizerThread(String pathToDstFolder, int newWidth, File[] filesAllImages, long start) {
        this.pathToDstFolder = pathToDstFolder;
        this.newWidth = newWidth;
        this.filesAllImages = filesAllImages;
        this.start = start;
    }
    @Override
    public void run() {
        try {
            for (File currentFileImage : filesAllImages) {
                BufferedImage originBufferedImage = ImageIO.read(currentFileImage);
                if (originBufferedImage == null) {
                    continue;
                }

                int newHeight = Math.round(
                        originBufferedImage.getHeight() / (originBufferedImage.getWidth() / newWidth)
                );

                BufferedImage endBufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

                int stepWidth = originBufferedImage.getWidth() / newWidth;
                int stepHeight = originBufferedImage.getHeight() / newHeight;

                for (int x = 0; x < newWidth; x++) {
                    for (int y = 0; y < newHeight; y++) {
                        int rgb = originBufferedImage.getRGB(x * stepWidth, y * stepHeight);
                        endBufferedImage.setRGB(x, y, rgb);
                    }
                }

                File endFileImage = new File(pathToDstFolder + "/" + currentFileImage.getName());
                ImageIO.write(endBufferedImage, "jpg", endFileImage);
            }
            long end = System.currentTimeMillis();
            long difference = end - start;
            System.out.println("Time work program: " + difference);
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
}
