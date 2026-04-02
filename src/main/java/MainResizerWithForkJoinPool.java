import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class MainResizerWithForkJoinPool {
    private static final String pathToSrcFolder = "src/main/resources/data/src_folder_sound";
    private static final String pathToDstFolder = "src/main/resources/data/dst_folder_sound";
    private static final int newWidth = 300;
    private static final int THRESHOLD = 10; // Минимальный размер чанка для обработки

    public static void main(String[] args) {
    }
}

class ChangeQualitySound extends Thread {
    /*
        //Оригинальные сэмплы (каждый)
        for (int i = 0; i < originalSamples.length; i++) {
            newSamples[i] = originalSamples[i];
        }

        //Уменьшенная частота (каждый второй)
        for (int i = 0; i < newSamples.length; i++) {
            newSamples[i] = originalSamples[i * 2];  // берём с шагом 2
        }
     */

    String pathToDstFolder;
    File[] filesAllImages;
    long start;

    public ChangeQualitySound(String pathToDstFolder, int newWidth, File[] filesAllImages, long start) {
        this.pathToDstFolder = pathToDstFolder;
        this.filesAllImages = filesAllImages;
        this.start = start;
    }

    @Override
    public void run() {
        try {
            for (File currentFileImage : filesAllImages) {
                AudioInputStream originalAudioInputStream = AudioSystem.getAudioInputStream(currentFileImage);
                AudioFormat originAudioFormat = originalAudioInputStream.getFormat();

                //TODO Новый формат с половинной частотой
                AudioFormat newAudioFormat = new AudioFormat(
                        originAudioFormat.getEncoding(),      // 1. Тип кодирования
                        originAudioFormat.getSampleRate() / 2, // 2. Частота дискретизации
                        originAudioFormat.getSampleSizeInBits(), // 3. Разрядность (бит на сэмпл)
                        originAudioFormat.getChannels(),      // 4. Количество каналов
                        originAudioFormat.getFrameSize(),     // 5. Размер фрейма в байтах
                        originAudioFormat.getFrameRate() / 2, // 6. Частота фреймов
                        originAudioFormat.isBigEndian()       // 7. Порядок байтов
                );

                // Преобразование (упрощённо: прореживание сэмплов)
                AudioInputStream converted = AudioSystem.getAudioInputStream(newAudioFormat, originalAudioInputStream);

                File output = new File(pathToDstFolder + "/" + currentFileImage.getName());
                AudioSystem.write(converted, AudioFileFormat.Type.WAVE, output);
            }
            long end = System.currentTimeMillis();
            long difference = end - start;
            System.out.println("Time work program: " + difference);
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
}