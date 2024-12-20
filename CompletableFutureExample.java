import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureExample {

    public static void main(String[] args) {
        CompletableFuture<int[][]> generateArrayFuture = CompletableFuture.supplyAsync(() -> {
            long startTime = System.nanoTime();
            int[][] array = new int[3][3];
            Random random = new Random();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    array[i][j] = random.nextInt(100);
                }
            }
            long endTime = System.nanoTime();
            System.out.println("Масив згенеровано за: " + TimeUnit.NANOSECONDS.toMicros(endTime - startTime) + " мкс");
            return array;
        });

        CompletableFuture<Void> printColumn1Future = generateArrayFuture.thenAcceptAsync(array -> {
            long startTime = System.nanoTime();
            System.out.print("Перший стовпець: ");
            for (int i = 0; i < 3; i++) {
                System.out.print(array[i][0] + ", ");
            }
            System.out.println();
            long endTime = System.nanoTime();
            System.out.println("Виведено перший стовпець за: " + TimeUnit.NANOSECONDS.toMicros(endTime - startTime) + " мкс");
        });

        CompletableFuture<Void> printColumn2Future = generateArrayFuture.thenAcceptAsync(array -> {
            long startTime = System.nanoTime();
            System.out.print("Другий стовпець: ");
            for (int i = 0; i < 3; i++) {
                System.out.print(array[i][1] + ", ");
            }
            System.out.println();
            long endTime = System.nanoTime();
            System.out.println("Виведено другий стовпець за: " + TimeUnit.NANOSECONDS.toMicros(endTime - startTime) + " мкс");
        });

        CompletableFuture<Void> printColumn3Future = generateArrayFuture.thenAcceptAsync(array -> {
            long startTime = System.nanoTime();
            System.out.print("Третій стовпець: ");
            for (int i = 0; i < 3; i++) {
                System.out.print(array[i][2] + ", ");
            }
            System.out.println();
            long endTime = System.nanoTime();
            System.out.println("Виведено третій стовпець за: " + TimeUnit.NANOSECONDS.toMicros(endTime - startTime) + " мкс");
        });

        CompletableFuture<Void> thenRunFuture = printColumn3Future.thenRunAsync(() -> {
            long startTime = System.nanoTime();
            System.out.println("Дія виконана після виведення всіх стовпців (thenRunAsync)");
            long endTime = System.nanoTime();
            System.out.println("Виконано thenRunAsync за: " + TimeUnit.NANOSECONDS.toMicros(endTime - startTime) + " мкс");
        });

        CompletableFuture<Void> printInitialArrayFuture = generateArrayFuture.thenAcceptAsync(array -> {
            long startTime = System.nanoTime();
            System.out.println("Початковий масив:");
            for (int[] row : array) {
                System.out.println(Arrays.toString(row));
            }
            long endTime = System.nanoTime();
            System.out.println("Виведено початковий масив за: " + TimeUnit.NANOSECONDS.toMicros(endTime - startTime) + " мкс");
        });

        CompletableFuture.allOf(generateArrayFuture, printColumn1Future, printColumn2Future, printColumn3Future, thenRunFuture, printInitialArrayFuture).join();
    }
}