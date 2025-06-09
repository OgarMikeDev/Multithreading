import java.util.Scanner;

public class MainVolatile {
    public static void main(String[] args) {
        Task task = new Task();
        new Thread(task).start();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        task.stop();
        System.out.println("Main: " + task.getCounter());
    }
}
class Task implements Runnable {
    private long counter;
    private volatile boolean isRunning;

    public Task() {
        isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            counter++;
        }
        System.out.println("Task: " + counter);
    }

    public void stop() {
        isRunning = false;
    }

    public long getCounter() {
        return counter;
    }
}