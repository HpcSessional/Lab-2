import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class GroceryQueue {
    private final Queue<Customer> queue = new LinkedList<>();
    private final int maxSize;
    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore items;
    private final Semaphore space;

    public GroceryQueue(int maxSize) {
        this.maxSize = maxSize;
        this.items = new Semaphore(0);
        this.space = new Semaphore(maxSize);
    }

    public void addCustomer(Customer customer) {
        try {
            space.acquire();
            mutex.acquire();
            queue.add(customer);
            if (queue.size() == 1) {
                items.release();
            }
            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void serveCustomers(int currentTime) {
        try {
            items.acquire();
            mutex.acquire();
            Customer customer = queue.poll();
            if (customer != null) {
                customer.setServed(true);
            }
            if (queue.size() < maxSize) {
                space.release();
            }
            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getQueueSize() {
        return queue.size();
    }

    public int getMaxSize() {
        return maxSize;
    }
}
