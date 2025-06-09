import java.io.File;

public class Main {
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
        ImageResizer imageResizer1 = new ImageResizer(pathToDstFolder, newWidth, filesImages1, start);
        imageResizer1.start();

        File[] filesImages2 = new File[filesAllImages.length - filesImages1.length];
        System.arraycopy(filesAllImages, middle, filesImages2, 0, filesImages2.length);
        ImageResizer imageResizer2 = new ImageResizer(pathToDstFolder, newWidth, filesImages2, start);
        imageResizer2.start();
    }
}
