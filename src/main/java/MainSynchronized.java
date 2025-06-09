import java.util.ArrayList;

public class MainSynchronized {
    public static ArrayList<Double> arrayListNumbers = new ArrayList<>();
    public static void main(String[] args) {
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(MainSynchronized::someHeavyMethod));
        }

        threads.forEach(t -> t.start());
    }

    public synchronized static void someHeavyMethod() {
        for (int i = 0; i < 1_000_000; i++) {
            arrayListNumbers.add(Math.random() / Math.random());
        }
        System.out.println("Size array list: " + arrayListNumbers.size());
        arrayListNumbers.clear();
    }
}
