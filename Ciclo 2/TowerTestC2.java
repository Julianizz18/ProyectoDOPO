import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TowerTestC2 {

    @Test
    public void constructorShouldCreateEmptyTower() {
        Tower t = new Tower(3);
        assertEquals(9, t.height());
    }

    @Test
    public void constructorShouldRespectMaxCups() {
        Tower t = new Tower(2);
        t.swap(new String[]{"cup","1"}, new String[]{"cup","2"});
        assertTrue(t.ok()); 
    }

    @Test
    public void swapShouldWorkWithValidObjects() {
        Tower t = new Tower(3);
        t.cover(); 
        t.swap(new String[]{"cup","1"}, new String[]{"cup","2"});
        assertTrue(t.ok());
    }

    @Test
    public void swapShouldFailWithInvalidCup() {
        Tower t = new Tower(3);
        t.cover();
        t.swap(new String[]{"cup","1"}, new String[]{"cup","99"});
        assertFalse(t.ok());
    }

    @Test
    public void coverShouldWorkWhenPossible() {
        Tower t = new Tower(3);
        t.cover();
        assertTrue(t.ok());
    }

    @Test
    public void coverShouldNotCrashWhenNoSpace() {
        Tower t = new Tower(0);
        t.cover();
        assertTrue(t.ok());
    }

    @Test
    public void swapToReduceShouldReturnMatrix() {
        Tower t = new Tower(3);
        String[][] result = t.swapToReduce();
        assertNotNull(result);
    }

    
    @Test
    public void swapToReduceShouldReturnEmptyIfNoReductionPossible() {
        Tower t = new Tower(1);
        String[][] result = t.swapToReduce();
        assertTrue(result.length == 0 || result == null);
    }
}