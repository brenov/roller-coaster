/*
 * GNU License.
 */
package rollercoaster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents the Roller Coaster car.
 *
 * @author Breno Viana
 * @version 20/05/2017
 */
public class Car {

    // Singleton
    private static Car instance = new Car(2, 4);

    // Maximum number of rides
    private final int maximumNumberOfRides;
    // Total number of rides daily
    private int totalRides;
    // Car capacity
    private final int capacity;
    // Is in moving
    private boolean moving;
    // Allow boarding
    private boolean allowBoarding;
    // Allow unboarding
    private boolean allowUnboarding;
    // When the car is ready
    private boolean ready;
    // List of passengers
    private List<Passenger> passengers;

    /**
     * Constructor.
     *
     * @param maximumNumberOfRides Maximum number of rides
     * @param capacity Car capacity
     */
    private Car(int maximumNumberOfRides, int capacity) {
        this.maximumNumberOfRides = maximumNumberOfRides;
        this.totalRides = 0;
        this.capacity = capacity;
        this.moving = false;
        this.allowBoarding = false;
        this.allowUnboarding = false;
        this.ready = false;
        this.passengers = new ArrayList<>();
    }

    /**
     * Get instance of car.
     *
     * @return Instance of car
     */
    public static Car getInstance() {
        return instance;
    }

    /**
     * Add passenger in the car.
     *
     * @param passenger The passenger
     */
    public void addPassenger(Passenger passenger) {
        synchronized (this) {
            // Check if the car isn't full
            if (!this.isFull() && !this.passengers.contains(passenger)) {
                this.passengers.add(passenger);
                // Check if the car full
                if (this.isFull()) {
                    this.allowBoarding = false;
                    this.ready = true;
                }
            }
        }
    }

    /**
     * Remove passenger from the car.
     *
     * @param passenger The passenger
     */
    public void removePassenger(Passenger passenger) {
        synchronized (this) {
            // Check if the car in't empty
            if (!this.passengers.isEmpty()) {
                this.passengers.remove(passenger);
                // Check if the car is empty
                if (this.passengers.isEmpty()) {
                    this.allowUnboarding = false;
                }
            }
        }
    }

    /**
     * Get true if the passenger is in the car and false otherwise.
     *
     * @param passenger The passenger
     *
     * @return True if the passenger is in the car false otherwise
     */
    public boolean isInTheCar(Passenger passenger) {
        return this.passengers.contains(passenger);
    }

    /**
     * Get true if the car allows boarding and false otherwise.
     *
     * @return True if the car allows boarding and false otherwise
     */
    public boolean isAllowBoarding() {
        return this.allowBoarding;
    }

    /**
     * Get true if the car allows unboarding and false otherwise.
     *
     * @return True if the car allows unboarding and false otherwise
     */
    public boolean isAllowUnboarding() {
        return this.allowUnboarding;
    }

    /**
     * Get true if the car is ready and false otherwise.
     *
     * @return True if the car is ready and false otherwise
     */
    public boolean isReady() {
        return this.ready;
    }

    /**
     * Get true if the car is full and false otherwise.
     *
     * @return True if the car is full and false otherwise
     */
    public boolean isFull() {
        return this.capacity == this.passengers.size();
    }

    /**
     * Get true if the car is empty and false otherwise.
     *
     * @return True if the car is empty and false otherwise
     */
    public boolean isEmpty() {
        return this.passengers.isEmpty();
    }

    /**
     * Get true if the car is moving and false otherwise.
     *
     * @return True if the car is moving and false otherwise
     */
    public boolean isMoving() {
        return this.moving;
    }

    /**
     * Get true if the car is stopped and false otherwise.
     *
     * @return True if the car is stopped and false otherwise.
     */
    public boolean isStopped() {
        return !this.isMoving();
    }

    /**
     * Get true if the total number of rides is less than maximum number of
     * rides.
     *
     * @return True if the total number of rides is less than maximum number of
     * rides.
     */
    public boolean isWorking() {
        return this.maximumNumberOfRides > this.totalRides;
    }

    /**
     * Allows passengers to board.
     */
    public void load() {
        // Allow boarding
        System.out.println("Boarding...");
        this.allowBoarding = true;
    }

    /**
     * Allows passengers to unboard.
     */
    public void unload() {
        // Check if the car is stopped
        if (this.isStopped() && this.isFull()) {
            // Allow unboarding
            System.out.println("Unboarding...");
            this.allowUnboarding = true;
        }
    }

    /**
     * Run.
     */
    public void run() {
        // Check if the car will still work
        if (this.isWorking() && this.isReady()) {
            try {
                // Starts moving
                this.ready = false;
                this.moving = true;
                // Ride
                System.out.println("Ride started.");
                this.totalRides++;
                TimeUnit.SECONDS.sleep((new Random()).nextInt(4) + 1);
                // Stops moving
                this.moving = false;
                System.out.println("Ride ended.");
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
