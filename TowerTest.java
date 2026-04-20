import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Clase de pruebas unitarias para la clase Tower.
 * Se encarga de validar el comportamiento de la torre, la gestión de tazas y tapas,
 * así como los procesos de ordenamiento y visualización.
 * * @author Sara Arteaga - Julian Tinjacá
 * @version 2026.02
 */
public class TowerTest {

    private Tower tower;

    /**
     * Prepara el entorno de pruebas antes de cada método.
     * Crea una torre con ancho 5 y altura máxima 20.
     */
    @Before
    public void setUp() {
        tower = new Tower(5, 20);
    }

    /**
     * Verifica que al crear una torre, esta inicie con altura 0 y estado exitoso.
     */
    @Test
    public void shouldCreateEmptyTower() {
        assertEquals(0, tower.height());
        assertTrue(tower.ok());
    }

    /**
     * Valida que se pueda adicionar una taza correctamente y se calcule 
     * su altura según la fórmula (2*id - 1).
     */
    @Test
    public void shouldPushCup() {
        tower.pushCup(1);
        assertEquals(1, tower.height());
        assertTrue(tower.ok());
    }

    /**
     * Comprueba que no se permita adicionar dos tazas con el mismo identificador.
     */
    @Test
    public void shouldNotPushDuplicateCup() {
        tower.pushCup(1);
        tower.pushCup(1);
        assertFalse(tower.ok());
    }

    /**
     * Verifica la eliminación de la última taza adicionada a la torre.
     */
    @Test
    public void shouldPopCup() {
        tower.pushCup(1);
        tower.popCup();
        assertEquals(0, tower.height());
        assertTrue(tower.ok());
    }

    /**
     * Valida la eliminación de una taza específica mediante su identificador.
     */
    @Test
    public void shouldRemoveSpecificCup() {
        tower.pushCup(1);
        tower.pushCup(2); 
        tower.removeCup(1);
        assertEquals(3, tower.height()); 
        assertTrue(tower.ok());
    }

    /**
     * Verifica que se pueda adicionar una tapa a una taza existente e incremente la altura en 1.
     */
    @Test
    public void shouldPushLid() {
        tower.pushCup(2); 
        tower.pushLid(2); 
        assertEquals(4, tower.height());
        assertTrue(tower.ok());
    }

    /**
     * Comprueba que no se pueda adicionar una tapa si la taza no existe en la torre.
     */
    @Test
    public void shouldNotPushLidWithoutCup() {
        tower.pushLid(1);
        assertFalse(tower.ok());
    }

    /**
     * Valida la eliminación de la tapa del elemento superior que posea una.
     */
    @Test
    public void shouldPopLid() {
        tower.pushCup(1);
        tower.pushLid(1);
        tower.popLid();
        assertEquals(1, tower.height());
        assertTrue(tower.ok());
    }

    /**
     * Verifica la eliminación de la tapa de una taza específica por su identificador.
     */
    @Test
    public void shouldRemoveSpecificLid() {
        tower.pushCup(2);
        tower.pushLid(2);
        tower.removeLid(2);
        assertEquals(3, tower.height());
        assertTrue(tower.ok());
    }

    /**
     * Comprueba que la consulta de tazas tapadas retorne los IDs ordenados ascendentemente.
     */
    @Test
    public void shouldReturnLidedCupsSorted() {
        tower.pushCup(3);
        tower.pushCup(1);
        tower.pushLid(3);
        tower.pushLid(1);

        int[] result = tower.lidedCups();
        assertArrayEquals(new int[]{1, 3}, result);
    }

    /**
     * Valida que la información de apilamiento sea correcta en tipo e identificador.
     */
    @Test
    public void shouldReturnStackingItems() {
        tower.pushCup(2);
        tower.pushLid(2);

        String[][] items = tower.stackingItems();
        assertEquals("cup", items[0][0]);
        assertEquals("2", items[0][1]);
        assertEquals("lid", items[1][0]);
        assertEquals("2", items[1][1]);
    }

    /**
     * Verifica que el ordenamiento de la torre coloque los elementos de mayor a menor ID.
     */
    @Test
    public void shouldOrderTowerDescending() {
        tower.pushCup(1);
        tower.pushCup(3);
        tower.pushCup(2);

        tower.orderTower();

        String[][] items = tower.stackingItems();
        assertEquals("3", items[0][1]);
        assertEquals("2", items[1][1]);
        assertEquals("1", items[2][1]);
    }

    /**
     * Valida que la torre pueda invertir el orden de sus elementos.
     */
    @Test
    public void shouldReverseTower() {
        tower.pushCup(1);
        tower.pushCup(2);
        tower.reverseTower();

        String[][] items = tower.stackingItems();
        assertEquals("2", items[0][1]);
        assertEquals("1", items[1][1]);
    }

    /**
     * Comprueba que el cambio de visibilidad funcione y registre éxito en la operación.
     */
    @Test
    public void shouldMakeVisibleAndInvisible() {
        tower.makeVisible();
        assertTrue(tower.ok());

        tower.makeInvisible();
        assertTrue(tower.ok());
    }

    /**
     * Verifica que la torre rechace adicionar elementos que superen la altura máxima permitida.
     */
    @Test
    public void shouldFailWhenExceedingMaxHeight() {
        tower.pushCup(4); 
        tower.pushCup(5); 
        tower.pushCup(6); 
        assertFalse(tower.ok());
    }
}