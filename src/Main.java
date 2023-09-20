import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger atomicInteger3 = new AtomicInteger(0);
    public static AtomicInteger atomicInteger4 = new AtomicInteger(0);
    public static AtomicInteger atomicInteger5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        List<Thread> threadList = new ArrayList<>();
        threadList.add(countNickname(texts, 3, atomicInteger3));
        threadList.add(countNickname(texts, 4, atomicInteger4));
        threadList.add(countNickname(texts, 5, atomicInteger5));

        for (Thread thread : threadList) {
            thread.start();
        }
        for (Thread thread : threadList) {
            thread.join();
        }

        System.out.println("Красивых слов с длиной 3: " + atomicInteger3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + atomicInteger4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + atomicInteger5.get() + " шт");

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static Thread countNickname(String[] texts, int length, AtomicInteger atomicInteger) throws InterruptedException {

        Thread thread = new Thread(() -> {
            for (String str : texts) {
                if (str.length() == length) {
                    if (isPalindromeReverseTheString(str)) {
                        atomicInteger.incrementAndGet();
                    }
                    if (isSameСharacters(str)) {
                        atomicInteger.incrementAndGet();
                    }
                    if (isSortedString(str)) {
                        atomicInteger.incrementAndGet();
                    }
                }
            }
        });
        return thread;
    }


    public static boolean isPalindromeReverseTheString(String text) {
        StringBuilder reverse = new StringBuilder();
        String clean = text.replaceAll("\\s+", "").toLowerCase();
        char[] plain = clean.toCharArray();
        for (int i = plain.length - 1; i >= 0; i--) {
            reverse.append(plain[i]);
        }
        return (reverse.toString()).equals(clean);
    }

    public static boolean isSameСharacters(String text) {
        String clean = text.replaceAll("\\s+", "").toLowerCase();
        char c = clean.charAt(0);
        for (int i = 1; i < clean.length(); i++) {
            if (clean.charAt(i) != c) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSortedString(String text) {
        String clean = text.replaceAll("\\s+", "").toLowerCase();
        char[] chars = clean.toCharArray();
        Arrays.sort(chars);
        String sorted = new String(chars);
        return (clean.equals(sorted));
    }
}