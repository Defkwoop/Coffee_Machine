package machine;
import java.util.Scanner;
import static java.lang.Math.min;
import static machine.CoffeeMachine.CoffeeProduct.*;

public class CoffeeMachine {

    private static final String BUY = "buy";
    private static final String FILL = "fill";
    private static final String TAKE = "take";

    private final Scanner scanner;

    //Amount of water (ml), milk (ml), coffee beans (g),
    // amount of disposable cups and money hold by the machine.
    private int water, milk, beans, disposableCups, money;

    public CoffeeMachine(Scanner scanner) {
        this.scanner = scanner;
        water = 400;
        milk = 540;
        beans = 120;
        disposableCups = 9;
        money = 550;
    }


    public static void main(String[] args) {
        CoffeeMachine coffee = new CoffeeMachine(new Scanner(System.in));
        coffee.info();
        coffee.action();
        coffee.info();

    }

    public void info() {
        System.out.printf("The Coffee machine has:\n" +
                "%d ml of water\n" +
                "%d ml of milk\n" +
                "%d g of coffee beans\n" +
                "%d disposable cups\n" +
                "$%d of money\n" +
                "%n", water, milk, beans, disposableCups, money);
    }

    private void action() {
        System.out.println("Write action (buy, fill, take):");
        switch (scanner.nextLine()) {
            case BUY:
                buy();
                break;
            case FILL:
                fill();
                break;
            case TAKE:
                take();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                action();
                break;
        }
    }

    private void take() {
        System.out.printf("I gave you $%d of money%n", money);
        money = 0;

    }

    private void fill() {
        System.out.println("Write how many ml of water you want to add:");
        water += scanner.nextInt();
        System.out.println("Write how many ml of milk you want to add:");
        milk += scanner.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add:");
        beans += scanner.nextInt();
        System.out.println("Write how many disposable cups you want to add:");
        disposableCups += scanner.nextInt();
    }

    private void buy() {

        System.out.println("What do you want to buy 1 - espresso, 2 - latte, 3 - cappuccino:");

        CoffeeProduct product;
        switch (scanner.nextInt()) {
            case 1:
                product = ESPRESSO;
                break;
            case 2:
                product = LATTE;
                break;
            case 3:
                product = CAPPUCCINO;
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        water -= product.getWater();
        milk -= product.getMilk();
        beans -= product.getBeans();
        disposableCups--;
        money += product.getPrice();
    }

    private int availableCoffee(int water, int milk, int beans) {
        // 1 coffee = 200ml water | 50ml milk | 15g coffee beans
        return min(water / 200, min(milk / 50, beans / 15));
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

