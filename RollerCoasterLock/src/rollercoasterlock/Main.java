/*
 * GNU License.
 */
package rollercoasterlock;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import rollercoasterlock.rollercoaster.RollerCoasterCar;
import rollercoasterlock.rollercoaster.RollerCoasterHandler;

/**
 * This is the main class of Roller Coaster.
 *
 * @author Breno & Patrícia
 * @version 27/05/2017
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Roller Coaster car
        RollerCoasterCar car = RollerCoasterCar.getInstance();
        // Passenger list
        List<Passenger> passengers = new ArrayList<>();
        // Creates the passengers
        for (int i = 0; i < ((new Random()).nextInt(10) + 15); i++) {
            passengers.add(new Passenger(i + 1, car));
        }
        // Runs passengers
        passengers.stream().map((passenger)
                -> new Thread(passenger)).forEach((t) -> {
            t.start();
        });
        // Roller Coaster handler
        RollerCoasterHandler handler = new RollerCoasterHandler(car);
        handler.run();
    }
}
