import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class HorseTest {

    Horse horse;
    @Test
    public void nullNameConstructorTest() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            horse = new Horse(null, 5);
        });

        assertEquals("Name cannot be null.", exception.getMessage());

    }

    @ParameterizedTest(name = "{index} - {0} is a name of horse.")
    @ValueSource(strings = {"", " ", "\n\n", "\t"})
    public void blankNameConstructorTest(String name){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            horse = new Horse(name, 5);
        });

        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    public void negativeSpeedConstructorTest() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            horse = new Horse("Name", -5);
        });

        assertEquals("Speed cannot be negative.", exception.getMessage());

    }

    @Test
    public void negativeDistanceConstructorTest() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            horse = new Horse("Name", 5, -10);
        });

        assertEquals("Distance cannot be negative.", exception.getMessage());

    }

    @Test
    void getName() {

        String expectedHorseName = "Name";

        horse = new Horse(expectedHorseName, 5, 10);

        assertEquals(expectedHorseName, horse.getName());
    }

    @Test
    void getSpeed() {

        double expectedSpeed = 5;

        horse = new Horse("Name", expectedSpeed, 10);

        assertEquals(expectedSpeed, horse.getSpeed());

    }

    @Test
    void getDistance() {

        double expectedDistance = 10;

        horse = new Horse("Name", 5, expectedDistance);
        assertEquals(expectedDistance, horse.getDistance());

        horse = new Horse("Name", 5);
        assertEquals(0, horse.getDistance());
    }

    @Test
    void moveUsedGetRandomDouble() {

        try (MockedStatic mock = mockStatic(Horse.class)) {

            // выполнение метода, для которого написан тест
            horse = new Horse("Name", 5);
            horse.move();

            // проверка, что внутри move() был вызов getRandomDouble(0.2, 0.9)
            mock.verify(() -> Horse.getRandomDouble(0.2, 0.9));

        }

    }

    @ParameterizedTest(name = "{index} - {0} is a random number.")
    @ValueSource(doubles = {0.3, 0.4, 0.5})
    void move(double randomNumber) {

        int speed = 5;
        int firstDistance = 10;

        horse = new Horse("Name", speed, firstDistance);

        try (MockedStatic mock = mockStatic(Horse.class)) {
            mock.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomNumber);

            horse.move();
            assertEquals((firstDistance + speed * randomNumber), horse.getDistance());
        }

    }

}