package team.mediasoft.warehouse.datagenerator;

import java.util.Random;

public class RandomService {
    private static final Random RANDOM = new Random();

    public static int getRandomInt() {
        return RANDOM.nextInt(0, 1000);
    }

    public static long getRandomLong() {
        return RANDOM.nextLong(0, 1000);
    }

    public static int getRandomPrice() {
        return RANDOM.nextInt(0, 1000) * 100;
    }

    public static int getRandomArticle() {
        return RANDOM.nextInt(0, 10) * 10;
    }
}