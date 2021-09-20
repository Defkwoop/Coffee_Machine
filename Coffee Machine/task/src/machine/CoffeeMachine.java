package machine;

import java.util.Scanner;

public class CoffeeMachine {
    public static void main(String[] args) {

        System.out.println("Starting to make a coffee");
        System.out.println("Grinding coffee beans");
        System.out.println("Boiling water");
        System.out.println("Mixing boiled water with crushed coffee beans");
        System.out.println("Pouring coffee into the cup");
        System.out.println("Pouring some milk into the cup");
        System.out.println("Coffee is ready!");

        Scanner scanner = new Scanner(System.in);
        int a;

        System.out.println("Write how many cups of coffee you will need: ");
        a = scanner.nextInt();

        System.out.println("For " + a + " cups of coffee you will need: ");
        int water = a * 200; //calc water
        int milk = a * 50;  // calc milk
        int beans = a * 15; // calc beans

        System.out.println("" + water + " ml of water");
        System.out.println("" + milk + " ml of milk");
        System.out.println("" + beans + " g of coffee beans");

    }
}