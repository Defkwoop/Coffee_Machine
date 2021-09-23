package machine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static java.lang.Math.min;
import static machine.CoffeeMachine.CoffeeProduct.*;
import static java.lang.Integer.parseInt;

public class CoffeeMachine {

    // Commands
    private static final String BUY = "buy";
    private static final String FILL = "fill";
    private static final String TAKE = "take";
    private static final String REMAINING = "remaining";
    private static final String EXIT = "exit";
    private static final String ADMIN = "admin";

    // Credentials
    private static final String ADMIN_NAME = "admin";
    private static final String ADMIN_PW = "admin12345";

    private final Scanner consoleScanner;
    private final String filename;
    private boolean running = true;

    //Amount of water (ml), milk (ml), coffee beans (g),
    //Amount of disposable cups and money hold by the machine.
    private int water, milk, beans, disposableCups, money;

    public CoffeeMachine(String filename) {
        this.consoleScanner = new Scanner(System.in);
        this.filename = filename;
        readStateFromFile();

    }

    public static void main(String[] args) {
        CoffeeMachine coffeeM = new CoffeeMachine("UwU");
        while (coffeeM.running) {
            coffeeM.actions();
        }
    }

    private void actions() {
        System.out.println("Write action (buy, admin):");
        switch (consoleScanner.next()) {
            case BUY:
                buy();
                break;
            case ADMIN:
                admin();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void admin() {
        if (!authenticate()) {
            System.out.println("Username or password were incorrect");
            System.out.println("fucking scrub");
            return;
        }

        System.out.println("Write action (fill, take, remaining, exit):");
        switch (consoleScanner.next()) {
            case FILL:
                fill();
                break;
            case TAKE:
                take();
                break;
            case REMAINING:
                remaining();
                break;
            case EXIT:
                exit();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void take() {
        System.out.printf("I gave you $%d of money%n", money);
        money = 0;

    }

    private void fill() {
        System.out.println("Write how many ml of water you want to add:");
        water += consoleScanner.nextInt();
        System.out.println("Write how many ml of milk you want to add:");
        milk += consoleScanner.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add:");
        beans += consoleScanner.nextInt();
        System.out.println("Write how many disposable cups you want to add:");
        disposableCups += consoleScanner.nextInt();
    }

    private void buy() {
        System.out.println("What do you want to buy 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
        CoffeeProduct product;
        switch (consoleScanner.next()) {
            case "1":
                product = ESPRESSO;
                break;
            case "2":
                product = LATTE;
                break;
            case "3":
                product = CAPPUCCINO;
                break;
            case "back":
                return;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        boolean enoughWater = water >= product.getWater();
        boolean enoughMilk = milk >= product.getMilk();
        boolean enoughBeans = beans >= product.getBeans();
        boolean enoughCups = disposableCups > 0;

        if (!enoughWater) {
            System.out.println("Sorry, not enough water!");
            return;
        }
        if (!enoughMilk) {
            System.out.println("Sorry, not enough milk!");
            return;
        }
        if (!enoughBeans) {
            System.out.println("Sorry, not enough beans!");
            return;
        }
        if (!enoughCups) {
            System.out.println("Sorry, not enough cups!");
            return;
        }

        System.out.println("I have enough resources, making you a coffee!");

        water -= product.getWater();
        milk -= product.getMilk();
        beans -= product.getBeans();
        disposableCups--;
        money += product.getPrice();
    }

    private void remaining() {
        System.out.printf("The Coffee machine has:\n" +
                "%d ml of water\n" +
                "%d ml of milk\n" +
                "%d g of coffee beans\n" +
                "%d disposable cups\n" +
                "$%d of money\n" +
                "%n", water, milk, beans, disposableCups, money);
    }

    private void exit() {
        running = false;
        serializeState();
    }

    private boolean authenticate() {
        consoleScanner.nextLine(); //consumes newline
        System.out.println("Please login first. Please enter user name");
        final String name = consoleScanner.nextLine();
        System.out.println("Please enter password");
        final String pass = consoleScanner.nextLine();
        return name.equals(ADMIN_NAME) && pass.equals(ADMIN_PW);
    }

    private void useDefaultValues() {
        water = 400;
        milk = 540;
        beans = 120;
        disposableCups = 9;
        money = 550;
    }

    private void readStateFromFile() {
        try (Scanner scanner = new Scanner(new File(filename))) {
            if (scanner.hasNextLine()) {
                final String serializedState = scanner.nextLine();
                deserializeState(serializedState);
            } else {
                System.out.printf("Could not find data in file %s. Reverting to default values%n", filename);
                useDefaultValues();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.printf("Could not find file %s. Reverting to default values%n", filename);
            useDefaultValues();
        }
    }

    private void deserializeState(String serializedState) {
        final String[] fields = serializedState.split("; ");
        if (fields.length == 5) {
            water = parseInt(fields[0]);
            milk = parseInt(fields[1]);
            beans = parseInt(fields[2]);
            disposableCups = parseInt(fields[3]);
            money = parseInt(fields[4]);
        } else {
            System.out.println("File did not hat expected format");
            useDefaultValues();
        }
    }

    private void serializeState() {

        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write(String.format("%d; %d; %d; %d; %d", water, milk, beans, disposableCups, money));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public enum CoffeeProduct {

        ESPRESSO(250, 0, 16, 4),
        LATTE(350, 75, 20, 7),
        CAPPUCCINO(200, 100, 12, 6);

        private final int water;
        private final int milk;
        private final int beans;
        private final int price;

        CoffeeProduct(int water, int milk, int beans, int price) {
            this.water = water;
            this.milk = milk;
            this.beans = beans;
            this.price = price;
        }


        public int getWater() {
            return water;
        }

        public int getMilk() {
            return milk;
        }

        public int getBeans() {
            return beans;
        }

        public int getPrice() {
            return price;
        }
    }

}
