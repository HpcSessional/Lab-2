import java.util.Random;

public class QueueSimulator {
    private final BankQueue bankQueue;
    private final GroceryQueues groceryQueues;
    private final Random random = new Random();
    private int totalCustomersArrived;
    private int totalCustomersLeft;
    private int totalCustomersServed;
    private int totalServiceTime;

    public QueueSimulator(BankQueue bankQueue, GroceryQueues groceryQueues) {
        this.bankQueue = bankQueue;
        this.groceryQueues = groceryQueues;
    }

    public void startSimulation(int minutes) {
        int currentTime = 0;
        while (currentTime < minutes * 60) {
            int arrivalInterval = random.nextInt(41) + 20; // 20-60 seconds
            int serviceTime = random.nextInt(241) + 60; // 60-300 seconds

            currentTime += arrivalInterval;
            Customer customer = new Customer(currentTime, serviceTime);
            totalCustomersArrived++;

            bankQueue.addCustomer(customer);
            groceryQueues.addCustomer(customer);

            bankQueue.serveCustomers(currentTime);
            groceryQueues.serveCustomers(currentTime);

            if (!customer.isServed()) {
                totalCustomersLeft++;
            } else {
                totalCustomersServed++;
                totalServiceTime += serviceTime;
            }
        }

        printStatistics();
    }

    private void printStatistics() {
        double averageServiceTime = totalCustomersServed > 0 ? (double) totalServiceTime / totalCustomersServed : 0;
        System.out.println("BankQueue Statistics:");
        System.out.println("Total Customers Arrived: " + totalCustomersArrived);
        System.out.println("Total Customers Left Without Being Served: " + totalCustomersLeft);
        System.out.println("Total Customers Served: " + totalCustomersServed);
        System.out.println("Average Service Time: " + averageServiceTime);

        // Reset statistics for GroceryQueues
        totalCustomersArrived = 0;
        totalCustomersLeft = 0;
        totalCustomersServed = 0;
        totalServiceTime = 0;

        System.out.println("GroceryQueues Statistics:");
        System.out.println("Total Customers Arrived: " + totalCustomersArrived);
        System.out.println("Total Customers Left Without Being Served: " + totalCustomersLeft);
        System.out.println("Total Customers Served: " + totalCustomersServed);
        System.out.println("Average Service Time: " + averageServiceTime);
    }
}
