package com.alexmita.ordermanagementsystem.seed;

import com.alexmita.ordermanagementsystem.domain.product.Product;
import com.alexmita.ordermanagementsystem.domain.product.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DatabaseSeeder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {
            System.out.println("Starting database seeding for products...");

            Product product1 = new Product(null, "Wireless Mechanical Keyboard", 129.99, 50);
            Product product2 = new Product(null, "Gaming Mouse 16000 DPI", 79.50, 120);
            Product product3 = new Product(null, "27-inch 4K Monitor", 349.99, 30);
            Product product4 = new Product(null, "USB-C Dual Docking Station", 89.99, 75);
            Product product5 = new Product(null, "Noise Cancelling Headphones", 249.99, 45);
            Product product6 = new Product(null, "Ergonomic Office Chair", 199.50, 20);
            Product product7 = new Product(null, "1TB NVMe M.2 SSD", 89.99, 150);
            Product product8 = new Product(null, "External 2TB HDD", 64.99, 80);
            Product product9 = new Product(null, "Webcam 1080p 60fps", 49.99, 65);
            Product product10 = new Product(null, "Streaming Microphone USB", 119.99, 40);
            Product product11 = new Product(null, "RGB Gaming Mouse Pad", 29.99, 200);
            Product product12 = new Product(null, "Dual Monitor Desk Mount", 45.00, 35);
            Product product13 = new Product(null, "Graphics Tablet for Drawing", 79.99, 25);
            Product product14 = new Product(null, "Wi-Fi 6 Router", 129.50, 60);
            Product product15 = new Product(null, "Bluetooth Smart Watch", 179.99, 90);
            Product product16 = new Product(null, "Portable Power Bank 20000mAh", 39.99, 110);
            Product product17 = new Product(null, "Vertical Wireless Mouse", 34.99, 85);
            Product product18 = new Product(null, "34-inch Ultrawide Curved Monitor", 499.99, 15);
            Product product19 = new Product(null, "Mechanical Keyboard Wrist Rest", 19.99, 130);
            Product product20 = new Product(null, "Ring Light with Tripod Stand", 34.50, 70);
            Product product21 = new Product(null, "Bluetooth Conference Speaker", 89.00, 40);
            Product product22 = new Product(null, "Cat6 Ethernet Cable 15m", 12.99, 250);
            Product product23 = new Product(null, "Smart LED Desk Lamp", 44.99, 55);
            Product product24 = new Product(null, "Wireless Earbuds with ANC", 149.99, 105);
            Product product25 = new Product(null, "VR Headset Stand", 24.99, 30);
            Product product26 = new Product(null, "Internal PC Fan 3-Pack RGB", 39.99, 95);
            Product product27 = new Product(null, "DDR5 RAM 32GB Kit", 159.99, 70);
            Product product28 = new Product(null, "USB-C to HDMI Adapter", 15.50, 180);
            Product product29 = new Product(null, "Laptop Cooling Pad", 27.99, 80);
            Product product30 = new Product(null, "Smart Home Plug 4-Pack", 32.99, 115);
            Product product31 = new Product(null, "Graphics Card RTX 4060", 299.99, 12);
            Product product32 = new Product(null, "CPU Cooler Liquid AIO", 109.99, 28);
            Product product33 = new Product(null, "MicroSDXC Card 256GB", 22.99, 210);
            Product product34 = new Product(null, "Anti-Blue Light Glasses", 18.00, 140);

            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);
            productRepository.save(product4);

            System.out.println("Database seeding completed successfully.");
        } else {
            System.out.println("Database already contains product data. Seeding skipped.");
        }
    }
}