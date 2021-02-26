package resources.tools;

import java.util.Random;

public class RandomNumberGenerator {

    /**
     *Generates a random number.
     */
    public static int getRandomNum(int range) {

        Random random = new Random();

        return random.nextInt(range);
    }
}
