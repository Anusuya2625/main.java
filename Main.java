import java.util.*;

public class Main {
    public static void main(String[] args) {
        InventoryManager inventoryManager = new InventoryManager();
        inventoryManager.run();
    }
}

class Product {
    private String id;
    private String name;
    private int stock;
    private double price;
    private int reorderLevel;

    public Product(String id, String name, int stock, double price, int reorderLevel) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.reorderLevel = reorderLevel;
    }
    public String getId() { 
        return id; 
    }
    public String getName() { 
        return name; 
    }
    public int getStock() { 
        return stock; 
    }
    public double getPrice() { 
        return price; 
    }
    public void sell(int quantity) {
        if (stock >= quantity) {
            stock -= quantity;
            System.out.println(quantity + " units of " + name + " sold.");
        } 
        else {
            System.out.println("Insufficient stock for " + name);
        }
    }

    public void restock(int quantity) {
        stock += quantity;
        System.out.println(name + " restocked by " + quantity + " units.");
    }

    public boolean needsReorder() {
        return stock <= reorderLevel;
    }

    public void display() {
        System.out.println("ID: " + id + ", Name: " + name +
                ", Stock: " + stock + ", Price: $" + price +
                ", Reorder Level: " + reorderLevel);
    }
}

class InventoryManager {
    private Map<String, Product> inventory = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);
    
    public InventoryManager() {
        // Sample products added to the inventory.
        inventory.put("P001", new Product("P001", "Laptop", 10, 899.99, 3));
        inventory.put("P002", new Product("P002", "Mouse", 50, 19.99, 10));
        inventory.put("P003", new Product("P003", "Keyboard", 20, 29.99, 5));
    }

    public void run() {
        int choice = -1;
        do {
            System.out.println("\n=== Product Inventory Menu ===");
            System.out.println("1. Display All Products");
            System.out.println("2. Sell Product");
            System.out.println("3. Restock Product");
            System.out.println("4. Show Reorder Alerts");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    displayAllProducts();
                    break;
                case 2:
                    sellProduct();
                    break;
                case 3:
                    restockProduct();
                    break;
                case 4:
                    showReorderAlerts();
                    break;
                case 0:
                    System.out.println("Exiting system.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } 
        while (choice != 0);
    }

    private void displayAllProducts() {
        System.out.println("\n--- Product List ---");
        for (Product p : inventory.values()) {
            p.display();
        }
    }

    private void sellProduct() {
        System.out.print("Enter Product ID to sell: ");
        String id = scanner.nextLine();
        if (inventory.containsKey(id)) {
            System.out.print("Enter quantity to sell: ");
            int quantity = Integer.parseInt(scanner.nextLine());
            inventory.get(id).sell(quantity);
        } else {
            System.out.println("Product ID not found.");
        }
    }

    private void restockProduct() {
        System.out.print("Enter Product ID to restock: ");
        String id = scanner.nextLine();
        if (inventory.containsKey(id)) {
            System.out.print("Enter quantity to restock: ");
            int quantity = Integer.parseInt(scanner.nextLine());
            inventory.get(id).restock(quantity);
        } else {
            System.out.println("Product ID not found.");
        }
    }

    private void showReorderAlerts() {
        System.out.println("\n--- Reorder Alerts ---");
        boolean alertFound = false;
        for (Product p : inventory.values()) {
            if (p.needsReorder()) {
                System.out.println("Reorder needed for: " + p.getName() + " (ID: " + p.getId() + ")");
                alertFound = true;
            }
        }
        if (!alertFound) {
            System.out.println("No products need reordering.");
        }
    }
}
