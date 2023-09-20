import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static int countLength3 = 0;
    static int countLength4 = 0;
    static int countLength5 = 0;

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        countLength3 = countNickname(texts, 3);
        countLength4 = countNickname(texts, 4);
        countLength5 = countNickname(texts, 5);

        System.out.println("Красивых слов с длиной 3: " + countLength3 + " шт");
        System.out.println("Красивых слов с длиной 4: " + countLength4 + " шт");
        System.out.println("Красивых слов с длиной 5: " + countLength5 + " шт");

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static int countNickname(String[] texts, int length) throws InterruptedException {

        AtomicInteger atomicInteger = new AtomicInteger(0);

        Thread thread1 = new Thread(() -> {
            for (String str : texts) {
                if (str.length() == length) {
                    if (isPalindromeReverseTheString(str)) {
                        atomicInteger.getAndIncrement();
                    }
                }
            }
        });
        thread1.start();
        thread1.join();

        Thread thread2 = new Thread(() -> {
            for (String str : texts) {
                if (str.length() == length) {
                    if (isSameСharacters(str)) {
                        atomicInteger.getAndIncrement();
                    }
                }
            }
        });
        thread2.start();
        thread2.join();

        Thread thread3 = new Thread(() -> {
            for (String str : texts) {
                if (str.length() == length) {
                    if (isSortedString(str)) {
                        atomicInteger.getAndIncrement();
                    }
                }
            }
        });
        thread3.start();
        thread3.join();

        return atomicInteger.get();
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