package com.example.inventorymanagementsystem;
import com.example.inventorymanagementsystem.model.Order;
import com.example.inventorymanagementsystem.model.Product;
import com.example.inventorymanagementsystem.service.CustomerService;
import com.example.inventorymanagementsystem.service.OrderService;
import com.example.inventorymanagementsystem.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

@SpringBootApplication
public class InventoryManagementSystemApplication implements CommandLineRunner{

    private final ProductService productService;
    private final CustomerService customerService;
    private final OrderService orderService;

    public InventoryManagementSystemApplication(ProductService productService, CustomerService customerService, OrderService orderService) {
        this.productService = productService;
        this.customerService = customerService;
        this.orderService = orderService;
    }

    public static void main(String[] args) {
        SpringApplication.run(InventoryManagementSystemApplication.class, args);
        final Logger logger = LogManager.getLogger(InventoryManagementSystemApplication.class);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n====== GLOLogistics Inventory System ======");
            System.out.println("1. Customer");
            System.out.println("2. Admin");
            System.out.println("0. Exit");
            System.out.print("Choose role: ");
            int role = scanner.nextInt();

            switch (role) {
                case 1 -> customerMenu(scanner);
                case 2 -> adminMenu(scanner);
                case 0 -> {
                    System.out.println("Exiting... Bye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void customerMenu(Scanner scanner) {
        System.out.println("\n====== CUSTOMER MENU ======");
        System.out.println("1. View All Products");
        System.out.println("2. Generate Order");
        System.out.println("3. View All Orders");
        System.out.println("4. Delete Order (if not approved)");
        System.out.print("Choose option: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> productService.getAllProducts().forEach(System.out::println);
            case 2 -> {
                System.out.print("Enter customerId: ");
                Long custId = scanner.nextLong();
                System.out.print("Enter productId: ");
                Long prodId = scanner.nextLong();
                System.out.print("Enter quantity: ");
                int qty = scanner.nextInt();
                try {
                    Order order = orderService.generateOrder(custId, prodId, qty);
                    System.out.println("Order created: " + order);
                } catch (RuntimeException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            case 3 -> {
                System.out.print("Enter customerId: ");
                Long custId = scanner.nextLong();
                customerService.viewAllOrders(custId).forEach(System.out::println);
            }
            case 4 -> {
                System.out.print("Enter orderId to delete: ");
                Long orderId = scanner.nextLong();
                try {
                    orderService.deleteOrder(orderId);
                    System.out.println("Order deleted if it was pending.");
                } catch (RuntimeException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            default -> System.out.println("Invalid option.");
        }
    }

    private void adminMenu(Scanner scanner) {
        System.out.println("\n====== ADMIN MENU ======");
        System.out.println("1. Add Product");
        System.out.println("2. Update Product Quantity");
        System.out.println("3. Approve/Reject Order");
        System.out.println("4. View All Orders");
        System.out.print("Choose option: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> {
                Product product = new Product();
                System.out.print("Enter product name: ");
                product.setProductName(scanner.next());
                System.out.print("Enter price: ");
                product.setProductPrice(scanner.nextDouble());
                System.out.print("Enter quantity: ");
                product.setProductQuantityInStock(scanner.nextInt());
                productService.addProduct(product);
                System.out.println("Product added.");
            }
            case 2 -> {
                System.out.print("Enter productId: ");
                Long prodId = scanner.nextLong();
                System.out.print("Enter new quantity: ");
                int qty = scanner.nextInt();
                Product updated = new Product();
                updated.setProductQuantityInStock(qty);
                productService.updateProduct(prodId, updated);
                System.out.println("Product updated.");
            }
            case 3 -> {
                System.out.print("Enter orderId: ");
                Long orderId = scanner.nextLong();
                System.out.print("Approve or Reject? ");
                String status = scanner.next();
                try {
                    orderService.updateOrder(orderId, status.toUpperCase());
                    System.out.println("Order updated.");
                } catch (RuntimeException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            case 4 -> orderService.getAllOrders().forEach(System.out::println);
            default -> System.out.println("Invalid option.");
        }
    }

}
