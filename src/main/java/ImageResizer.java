import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageResizer extends Thread {
    String pathToDstFolder;
    int newWidth;
    File[] filesAllImages;
    long start;

    public ImageResizer(String pathToDstFolder, int newWidth, File[] filesAllImages, long start) {
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
