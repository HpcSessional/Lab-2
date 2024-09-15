import java.util.Random;

public class GroceryQueues {
    private final GroceryQueue[] queues;
    private final Random random = new Random();

    public GroceryQueues(int numQueues, int maxQueueSize) {
        queues = new GroceryQueue[numQueues];
        for (int i = 0; i < numQueues; i++) {
            queues[i] = new GroceryQueue(maxQueueSize);
        }
    }

    public void addCustomer(Customer customer) {
        for (int i = 0; i < 10; i++) { // Wait for up to 10 seconds for space
            GroceryQueue minQueue = getQueueWithFewestCustomers();
            if (minQueue.getQueueSize() < minQueue.getMaxSize()) {
                minQueue.addCustomer(customer);
                return;
            }
            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private GroceryQueue getQueueWithFewestCustomers() {
        GroceryQueue minQueue = queues[0];
        for (GroceryQueue queue : queues) {
            if (queue.getQueueSize() < minQueue.getQueueSize()) {
                minQueue = queue;
            }
        }
        return minQueue;
    }

    public void serveCustomers(int currentTime) {
        for (GroceryQueue queue : queues) {
            queue.serveCustomers(currentTime);
        }
    }
}
