import java.util.concurrent.atomic.AtomicInteger;

public class AtomicMain {
    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100_000; j++) {
                    ValueStorage.incrementValue();
                }
                System.out.println(ValueStorage.getValue());
            }).start();
        }
    }
}

class ValueStorage {
    static AtomicInteger value = new AtomicInteger();
    static void incrementValue() {
        value.incrementAndGet();
    }

    public static int getValue() {
        return value.intValue();
    }
}
