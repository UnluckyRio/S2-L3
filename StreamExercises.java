import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe che contiene gli esercizi sui Java Streams
 */
public class StreamExercises {
    
    /**
     * Esercizio #1: Ottenere una lista di prodotti che appartengono alla categoria "Books" 
     * ed hanno un prezzo > 100
     */
    public static List<Product> getExpensiveBooks(List<Product> products) {
        return products.stream()
                .filter(product -> "Books".equals(product.getCategory())) // Filtra per categoria "Books"
                .filter(product -> product.getPrice() > 100) // Filtra per prezzo > 100
                .collect(Collectors.toList()); // Raccoglie in una lista
    }
    
    /**
     * Esercizio #2: Ottenere una lista di ordini con prodotti che appartengono alla categoria "Baby"
     */
    public static List<Order> getOrdersWithBabyProducts(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getProducts().stream() // Per ogni ordine, controlla i suoi prodotti
                        .anyMatch(product -> "Baby".equals(product.getCategory()))) // Se almeno un prodotto è categoria "Baby"
                .collect(Collectors.toList()); // Raccoglie in una lista
    }
    
    /**
     * Esercizio #3: Ottenere una lista di prodotti che appartengono alla categoria "Boys" 
     * ed applicare 10% di sconto al loro prezzo
     */
    public static List<Product> getBoysProductsWithDiscount(List<Product> products) {
        return products.stream()
                .filter(product -> "Boys".equals(product.getCategory())) // Filtra per categoria "Boys"
                .map(product -> {
                    // Crea un nuovo prodotto con il prezzo scontato del 10%
                    Product discountedProduct = new Product(
                            product.getId(),
                            product.getName(),
                            product.getCategory(),
                            product.getPrice() * 0.9 // Applica sconto del 10%
                    );
                    return discountedProduct;
                })
                .collect(Collectors.toList()); // Raccoglie in una lista
    }
    
    /**
     * Esercizio #4: Ottenere una lista di prodotti ordinati da clienti di livello (tier) 2 
     * tra l'01-Feb-2021 e l'01-Apr-2021
     */
    public static List<Product> getProductsFromTier2CustomersBetweenDates(List<Order> orders) {
        LocalDate startDate = LocalDate.of(2021, 2, 1); // 01-Feb-2021
        LocalDate endDate = LocalDate.of(2021, 4, 1);   // 01-Apr-2021
        
        return orders.stream()
                .filter(order -> order.getCustomer().getTier() == 2) // Filtra per clienti tier 2
                .filter(order -> !order.getOrderDate().isBefore(startDate) && 
                               !order.getOrderDate().isAfter(endDate)) // Filtra per date nel range
                .flatMap(order -> order.getProducts().stream()) // Estrae tutti i prodotti dagli ordini
                .distinct() // Rimuove eventuali duplicati
                .collect(Collectors.toList()); // Raccoglie in una lista
    }
    
    /**
     * Metodo main per testare gli esercizi
     */
    public static void main(String[] args) {
        // Creazione dati di test
        
        // Clienti
        Customer customer1 = new Customer(1L, "Mario Rossi", 1);
        Customer customer2 = new Customer(2L, "Luigi Verdi", 2);
        Customer customer3 = new Customer(3L, "Anna Bianchi", 2);
        
        // Prodotti
        Product book1 = new Product(1L, "Java Programming", "Books", 120.0);
        Product book2 = new Product(2L, "Python Guide", "Books", 80.0);
        Product book3 = new Product(3L, "Advanced Java", "Books", 150.0);
        Product baby1 = new Product(4L, "Baby Bottle", "Baby", 25.0);
        Product baby2 = new Product(5L, "Baby Toy", "Baby", 15.0);
        Product boys1 = new Product(6L, "Boys T-Shirt", "Boys", 30.0);
        Product boys2 = new Product(7L, "Boys Shoes", "Boys", 50.0);
        Product electronics1 = new Product(8L, "Smartphone", "Electronics", 500.0);
        
        List<Product> allProducts = Arrays.asList(book1, book2, book3, baby1, baby2, boys1, boys2, electronics1);
        
        // Ordini
        Order order1 = new Order(1L, "DELIVERED", LocalDate.of(2021, 3, 15), 
                                 LocalDate.of(2021, 3, 20), Arrays.asList(book1, baby1), customer2);
        Order order2 = new Order(2L, "SHIPPED", LocalDate.of(2021, 2, 10), 
                                 LocalDate.of(2021, 2, 15), Arrays.asList(book3, boys1), customer3);
        Order order3 = new Order(3L, "PENDING", LocalDate.of(2021, 1, 5), 
                                 LocalDate.of(2021, 1, 10), Arrays.asList(electronics1), customer1);
        Order order4 = new Order(4L, "DELIVERED", LocalDate.of(2021, 3, 25), 
                                 LocalDate.of(2021, 3, 30), Arrays.asList(baby2, boys2), customer2);
        
        List<Order> allOrders = Arrays.asList(order1, order2, order3, order4);
        
        // Test degli esercizi
        System.out.println("=== ESERCIZIO #1: Libri con prezzo > 100 ===");
        List<Product> expensiveBooks = getExpensiveBooks(allProducts);
        expensiveBooks.forEach(System.out::println);
        
        System.out.println("\n=== ESERCIZIO #2: Ordini con prodotti Baby ===");
        List<Order> ordersWithBaby = getOrdersWithBabyProducts(allOrders);
        ordersWithBaby.forEach(order -> System.out.println("Ordine ID: " + order.getId() + 
                                                          ", Cliente: " + order.getCustomer().getName()));
        
        System.out.println("\n=== ESERCIZIO #3: Prodotti Boys con sconto 10% ===");
        List<Product> boysWithDiscount = getBoysProductsWithDiscount(allProducts);
        boysWithDiscount.forEach(System.out::println);
        
        System.out.println("\n=== ESERCIZIO #4: Prodotti da clienti tier 2 (Feb-Apr 2021) ===");
        List<Product> tier2Products = getProductsFromTier2CustomersBetweenDates(allOrders);
        tier2Products.forEach(System.out::println);
    }
}