package budget;

import java.io.*;

public class Navigator {
    public void run() throws IOException, ClassNotFoundException {
        DataStorage dataStorage = new DataStorage();

        int currentChoice = dataStorage.getCurrentChoice();
        System.out.println();
        while (true) {
            if (currentChoice == 1) {
                dataStorage.addIncome();
            } else if (currentChoice == 2) {
                dataStorage.buyItem();
            } else if (currentChoice == 3) {
                dataStorage.showList();
            } else if (currentChoice == 4) {
                dataStorage.showBalance();
            } else if (currentChoice == 5) {
                save(dataStorage);
            } else if (currentChoice == 6) {
                dataStorage = load();
            } else if (currentChoice == 7) {
                dataStorage.sort();
            } else {
                System.out.println("Bye!");
                break;
            }
            currentChoice = dataStorage.getCurrentChoice();
            System.out.println();
        }
    }

    private void save(DataStorage dataStorage) throws IOException {
        File file = new File("purchases.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(dataStorage);
        objectOutputStream.close();
        System.out.println("Purchases were saved!\n");
    }

    private DataStorage load() throws IOException, ClassNotFoundException {
        File file = new File("purchases.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        System.out.println("Purchases were loaded!\n");
        return (DataStorage) objectInputStream.readObject();
    }
}