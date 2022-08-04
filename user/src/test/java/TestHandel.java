import org.junit.Test;

import java.util.Random;

/**
 * @author liuyongkang
 * @date Create in 2022/6/23 17:23
 */
public class TestHandel {

    @Test
    public void test(){
        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            int nextLong = random.nextInt(5000);
            System.out.println(nextLong);
        }
    }

}
