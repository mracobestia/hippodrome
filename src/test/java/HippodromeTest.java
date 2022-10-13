import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {

    Hippodrome hippodrome;

    @Test
    public void nullListConstructorTest() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            hippodrome = new Hippodrome(null);
        });

        assertEquals("Horses cannot be null.", exception.getMessage());

    }

    @Test
    public void emptyListConstructorTest() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            hippodrome = new Hippodrome(new ArrayList());
        });

        assertEquals("Horses cannot be empty.", exception.getMessage());

    }

    @Test
    void getHorses() {

        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Name" + (i+1), i, i));
        }

        hippodrome = new Hippodrome(horses);

        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    void move() {

        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }

        hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (Horse horse : horses) {
            Mockito.verify(horse).move();
        }

    }

    @Test
    void getWinner() {

        Horse horse1 = new Horse("Name1", 1, 1);
        Horse horse2 = new Horse("Name2", 1, 1.5);
        Horse horse3 = new Horse("Name3", 1, 2);

        hippodrome = new Hippodrome(List.of(horse1, horse2, horse3));

        assertSame(horse3, hippodrome.getWinner());

    }
}