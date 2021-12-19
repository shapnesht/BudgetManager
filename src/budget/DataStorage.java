package budget;

import java.io.*;
import java.util.*;

public class DataStorage implements Serializable {
    String[] myType = {"Food", "Clothes", "Entertainment", "Other","All"};

    double balance;
    Map<String, ArrayList<Product>> completeList;

    DataStorage() {
        completeList = new HashMap<>();
        completeList.put(myType[0], new ArrayList<>());
        completeList.put(myType[1], new ArrayList<>());
        completeList.put(myType[3], new ArrayList<>());
        completeList.put(myType[2], new ArrayList<>());
        completeList.put(myType[4], new ArrayList<>());
    }

    static Scanner scanner = new Scanner(System.in);

    void buyItem() {
        printFurther();
        scanner.nextLine();
        int userInput = scanner.nextInt();
        while (userInput != 5) {
            switch (userInput) {
                case 1 -> addPurchases("Food");
                case 2 -> addPurchases("Clothes");
                case 3 -> addPurchases("Entertainment");
                case 4 -> addPurchases("Other");
            }
            printFurther();
            userInput = scanner.nextInt();
            System.out.println();
        }
    }

    private void printFurther() {
        System.out.print("""
                Choose the type of purchase
                1) Food
                2) Clothes
                3) Entertainment
                4) Other
                5) Back
                """);
    }


    private void addPurchases(String type) {
        System.out.println("Enter purchase name:");
        scanner.nextLine();
        String str = scanner.nextLine();
        System.out.println("Enter its price:");
        double value = scanner.nextDouble();
        balance -= value;
        Product product = new Product(str, value);
        completeList.get(type).add(product);
        completeList.get("All").add(product);
        System.out.println("Purchase was added!\n");
    }

    void showList() {
        int ans = completeList.get("All").size();
        if (ans == 0) {
            System.out.println("The purchase list is empty");
        } else {
            printTypesForList();
            int n = scanner.nextInt();
            while (n != 6) {
                switch (n) {
                    case 1 -> printList("Food");
                    case 2 -> printList("Clothes");
                    case 3 -> printList("Entertainment");
                    case 4 -> printList("Other");
                    case 5 -> printList("All");
                }
                printTypesForList();
                n = scanner.nextInt();
                System.out.println();
            }
        }
        System.out.println();
    }

    private void printList(String str) {
        System.out.println(str + ":");
        double ans = 0;
        for (Product product : completeList.get(str)) {
            System.out.printf("%s $%.2f", product.getName(), product.getCost());
            System.out.println();
            ans += product.getCost();
        }
        System.out.printf("Total sum: $%.2f\n", ans);
        System.out.println();
    }

    private void printTypesForList() {
        System.out.println("""
                Choose the type of purchases
                1) Food
                2) Clothes
                3) Entertainment
                4) Other
                5) All
                6) Back
                """);
    }

    void showBalance() {
        System.out.printf("Balance: $%.2f\n", balance);
        System.out.println();
    }

    void addIncome() {
        System.out.println("Enter income:");
        balance += scanner.nextInt();
        System.out.println("Income was added!");
        System.out.println();
    }

    int getCurrentChoice() {
        printMenu();
        return scanner.nextInt();
    }

    private void printMenu() {
        System.out.println("""
                Choose your action:
                1) Add income
                2) Add purchase
                3) Show list of purchases
                4) Balance
                5) Save
                6) Load
                7) Analyze (Sort)
                0) Exit"""
        );
    }

    public void sort() {
        int choice = printMenuOfSort();
        while (choice != 4) {
            if (choice == 1) {
                sortAndPrintList("All");
            }
            else if (choice == 2) {
                SortedMap<Double, String> map = new TreeMap<>(Collections.reverseOrder());
                map.put(getSum("Food"), "Food");
                map.put(getSum("Clothes"), "Clothes");
                map.put(getSum("Entertainment"), "Entertainment");
                map.put(getSum("Other"), "Other");
                double sum = 0;
                System.out.println("Types:");
                for (Map.Entry<Double, String> element: map.entrySet()) {
                    System.out.printf("%s - $%.2f\n", element.getValue(), element.getKey());
                    sum += element.getKey();
                }
                System.out.printf("Total sum: $%.2f\n", sum);
                System.out.println();
            } else {
                System.out.println("""
                        Choose the type of purchase
                        1) Food
                        2) Clothes
                        3) Entertainment
                        4) Other""");
                int input = scanner.nextInt();
                System.out.println();
                if (input == 1) {
                    sortAndPrintList("Food");
                } else if (input == 2) {
                    sortAndPrintList("Clothes");
                } else if (input == 3) {
                    sortAndPrintList("Entertainment");
                }  else if (input == 4) {
                    sortAndPrintList("Other");
                }
            }

            choice = printMenuOfSort();
        }
    }

    private double getSum(String listType) {
        double sum = 0;
        for (Product product: completeList.get(listType)) {
            sum += product.getCost();
        }
        return sum;
    }

    private void sortAndPrintList(String listType) {
        List<Product> arr = completeList.get(listType);
        int ans = arr.size();
        if (ans == 0) {
            System.out.println("The purchase list is empty\n");
        } else {
            for (int i = 0; i < arr.size() - 1; i++) {
                for (int j = 0; j < arr.size() - i - 1; j++) {
                    if (arr.get(j).getCost() < arr.get(j+1).getCost()) {
                        Product product = arr.get(j);
                        arr.set(j, arr.get(j+1));
                        arr.set(j+1, product);
                    }
                }
            }
            printList(listType);
        }
    }

    private int printMenuOfSort() {
        System.out.println("""
                How do you want to sort?
                1) Sort all purchases
                2) Sort by type
                3) Sort certain type
                4) Back""");
        int ans = scanner.nextInt();
        System.out.println();
        return ans;
    }
}
