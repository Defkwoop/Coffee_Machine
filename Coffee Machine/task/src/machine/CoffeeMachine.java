package machine;

import java.util.Scanner;
import static java.lang.Math.min;


public class CoffeeMachine {

    private  final Scanner scanner;

    public CoffeeMachine(Scanner scanner) {
        this.scanner = scanner;

    }
    public static void main(String[] args) {
        CoffeeMachine coffee = new CoffeeMachine(new Scanner(System.in));
        coffee();
    }

    public static void coffee() {

        Scanner scanner = new Scanner(System.in);

        int water, milk, beans, requestedCups, availableCups;

        System.out.println("Write how many ml of water the coffee machine has: ");
        water = scanner.nextInt();
        System.out.println("Write how many ml of milk the coffee machine has:  ");
        milk = scanner.nextInt();
        System.out.println("Write how many grams of coffee beans the coffee machine has: ");
        beans = scanner.nextInt();
        System.out.println("Write how many cups of coffee you will need: ");
        requestedCups = scanner.nextInt();

        availableCups = availableCoffee(water, milk, beans);

        if (requestedCups == availableCups) {
            System.out.println("Yes, I can make that amount of coffee");
        } else if (requestedCups > availableCups) {
            System.out.println("No I can only make " + availableCups + " cup(s) of coffee");
        } else if (requestedCups < availableCups) {
            System.out.println("Yes I can make that amount of coffee + (and even) " + (availableCups - requestedCups) + " more than that)");
        }
    }
    public static int availableCoffee(int water, int milk, int beans) {
        return min(water / 200, min (milk / 50, beans /15));
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}