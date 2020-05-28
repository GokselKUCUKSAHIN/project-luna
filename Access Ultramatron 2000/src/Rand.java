
import java.util.Random;

public class Rand
{

    private static Random random = new Random(); //static random generator //one generator for everything!

    static int getInt(int start, int end)
    {
        return random.nextInt(end - start) + start;
    }

    static int getInt(int limit)
    {
        return random.nextInt(limit);
    }

}