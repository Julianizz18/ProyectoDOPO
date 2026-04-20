import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class TowerTest {

    private Tower tower;

    @BeforeEach
    public void setUp() {
        tower = new Tower(10, 20);
    }

    @Test
    public void testPushCupNormal() {
        tower.pushCup(1, "normal");
        assertTrue(tower.ok());
    }

    @Test
    public void testPushCupOpener() {
        tower.pushCup(1, "opener");
        assertTrue(tower.ok());
    }

    @Test
    public void testPushCupDuplicado() {
        tower.pushCup(1, "normal");
        tower.pushCup(1, "normal");
        assertFalse(tower.ok());
    }

    @Test
    public void testPopCup() {
        tower.pushCup(1, "normal");
        tower.popCup();
        assertTrue(tower.ok());
    }

    @Test
    public void testPopCupVacia() {
        tower.popCup();
        assertFalse(tower.ok());
    }

    @Test
    public void testPushLid() {
        tower.pushCup(1, "normal");
        tower.pushLid(1, "normal");
        assertTrue(tower.ok());
    }

    @Test
    public void testHeight() {
        tower.pushCup(1, "normal");
        assertTrue(tower.height() > 0);
    }

    @Test
    public void testOrderTower() {
        tower.pushCup(2, "normal");
        tower.pushCup(1, "normal");
        tower.orderTower();
        assertTrue(tower.ok());
    }

    @Test
    public void testCover() {
        tower.pushCup(1, "normal");
        tower.pushCup(2, "normal");
        tower.cover();
        assertTrue(tower.ok());
    }

    @Test
    public void testLidedCups() {
        tower.pushCup(1, "normal");
        tower.pushLid(1, "normal");
        assertEquals(1, tower.lidedCups().length);
    }

    @Test
    public void testRemoveCup() {
        tower.pushCup(1, "normal");
        tower.removeCup(1);
        assertTrue(tower.ok());
    }

    @Test
    public void testReverseTower() {
        tower.pushCup(1, "normal");
        tower.pushCup(2, "normal");
        tower.reverseTower();
        assertTrue(tower.ok());
    }

    @Test
    public void testSwap() {
        tower.pushCup(1, "normal");
        tower.pushCup(2, "normal");
        tower.swap(new String[]{"cup","1"}, new String[]{"cup","2"});
        assertTrue(tower.ok());
    }

    @Test
    public void testStackingItems() {
        tower.pushCup(1, "normal");
        tower.pushCup(2, "normal");
        String[][] items = tower.stackingItems();
        assertEquals(2, items.length);
    }

    @Test
    public void testSwapToReduce() {
        tower.pushCup(1, "normal");
        tower.pushCup(2, "normal");
        tower.pushLid(1, "normal");
        tower.swapToReduce();
        assertTrue(tower.ok());
    }

    @Test
    public void testMakeVisible() {
        tower.pushCup(1, "normal");
        tower.makeVisible();
        assertTrue(tower.ok());
    }

    @Test
    public void testMakeInvisible() {
        tower.makeVisible();
        tower.makeInvisible();
        assertTrue(tower.ok());
    }

    @Test
    public void testPopLid() {
        tower.pushCup(1, "normal");
        tower.pushLid(1, "normal");
        tower.popLid();
        assertTrue(tower.ok());
    }

    @Test
    public void testRemoveLid() {
        tower.pushCup(1, "normal");
        tower.pushLid(1, "normal");
        tower.removeLid(1);
        assertTrue(tower.ok());
    }

    @Test
    public void testPushCupHierarchical() {
        tower.pushCup(1, "hierarchical");
        assertTrue(tower.ok());
    }

    @Test
    public void testPushCupCleaner() {
        tower.pushCup(1, "normal");
        tower.pushCup(2, "cleaner");
        assertTrue(tower.ok());
    }

    @Test
    public void testFearfulLid() {
        tower.pushCup(1, "normal");
        tower.pushLid(1, "fearful");
        assertTrue(tower.ok());
    }

    @Test
    public void testSwapInvalido() {
        tower.pushCup(1, "normal");
        tower.swap(new String[]{"lid","1"}, new String[]{"cup","1"});
        assertFalse(tower.ok());
    }

    @Test
    public void testRemoveLidFearful() {
        tower.pushCup(1, "normal");
        tower.pushLid(1, "fearful");
        tower.removeLid(1);
        assertFalse(tower.ok());
    }

    @Test
    public void testHierarchicalCannotBeRemoved() {
        tower.pushCup(1, "hierarchical");
        tower.removeCup(1);
        assertFalse(tower.ok());
    }

    @Test
    public void testCleanerEliminaInferiores() {
        tower.pushCup(1, "normal");
        tower.pushCup(2, "normal");
        tower.pushCup(3, "cleaner");
        assertTrue(tower.ok());
    }
    
    @Test
    public void testCrazyLid() {
        tower.pushCup(1, "normal");
        tower.pushLid(1, "crazy");
        assertFalse(tower.ok());
    }

    @Test
    public void testTowerConstructorCups() {
        Tower t2 = new Tower(3);
        assertTrue(t2.ok());
    }

    @Test
    public void testStackingItemsConLid() {
        tower.pushCup(1, "normal");
        tower.pushLid(1, "normal");
        String[][] items = tower.stackingItems();
        assertEquals(2, items.length);
    }

    @Test
    public void testRemoveCupInexistente() {
        tower.removeCup(99);
        assertFalse(tower.ok());
    }

    @Test
    public void testPopLidSinTapa() {
        tower.pushCup(1, "normal");
        tower.popLid();
        assertFalse(tower.ok());
    }

    @Test
    public void testPushLidTazaInexistente() {
        tower.pushLid(99, "normal");
        assertFalse(tower.ok());
    }
    
    @Test
    public void testOpenerCupEliminaTapas() {
        tower.pushCup(1, "normal");
        tower.pushCup(2, "normal");
        tower.pushLid(1, "normal");
        tower.pushLid(2, "normal");
        tower.pushCup(3, "opener");
        assertTrue(tower.ok());
        assertEquals(0, tower.lidedCups().length);
    }

    @Test
    public void testHeightConLid() {
        tower.pushCup(1, "normal");
        tower.pushLid(1, "normal");
        int h = tower.height();
        assertTrue(h > 1);
    }

    @Test
    public void testSwapToReduceVacio() {
        String[][] result = tower.swapToReduce();
        assertEquals(0, result.length);
    }

    @Test
    public void testCoverTorreVacia() {
        tower.cover();
        assertTrue(tower.ok());
    }

    @Test
    public void testMakeVisibleInvisible() {
        tower.pushCup(1, "normal");
        tower.pushCup(2, "normal");
        tower.makeVisible();
        tower.pushCup(3, "normal");
        tower.makeInvisible();
        assertTrue(tower.ok());
    }

    @Test
    public void testPushLidDuplicado() {
        tower.pushCup(1, "normal");
        tower.pushLid(1, "normal");
        tower.pushLid(1, "normal");
        assertFalse(tower.ok());
    }

    @Test
    public void testFearfulLidNoPuedeSalir() {
        tower.pushCup(1, "normal");
        tower.pushLid(1, "fearful");
        tower.popLid();
        assertFalse(tower.ok());
    }

    @Test
    public void testHierarchicalOrden() {
        tower.pushCup(3, "normal");
        tower.pushCup(1, "normal");
        tower.pushCup(2, "hierarchical");
        assertTrue(tower.ok());
    }

    @Test
    public void testSwapToReduceConLids() {
        tower.pushCup(2, "normal");
        tower.pushCup(1, "normal");
        tower.pushLid(2, "normal");
        String[][] result = tower.swapToReduce();
        assertTrue(result.length == 0 || result.length == 2);
    }

    @Test
    public void testTorreConstructorCupsVisible() {
        Tower t2 = new Tower(3);
        t2.makeVisible();
        assertTrue(t2.ok());
    }

    @Test
    public void testRemoveLidInexistente() {
        tower.pushCup(1, "normal");
        tower.removeLid(1);
        assertFalse(tower.ok());
    }

    @Test
    public void testCleanerEliminaVarios() {
        tower.pushCup(1, "normal");
        tower.pushCup(2, "normal");
        tower.pushCup(3, "normal");
        tower.pushCup(4, "cleaner");
        assertTrue(tower.ok());
    }
}