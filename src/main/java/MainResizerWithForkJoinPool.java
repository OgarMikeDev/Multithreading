import java.io.File;
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
    File[] filesAllSounds;
    long start;

    public ChangeQualitySound(String pathToDstFolder, int newWidth, File[] filesAllSounds, long start) {
        this.pathToDstFolder = pathToDstFolder;
        this.filesAllSounds = filesAllSounds;
        this.start = start;
    }

    @Override
    public void run() {
        try {
            for (File currentFileSound : filesAllSounds) {
                AudioInputStream originalAudioInputStream = AudioSystem.getAudioInputStream(currentFileSound);
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

                File output = new File(pathToDstFolder + "/" + currentFileSound.getName());
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