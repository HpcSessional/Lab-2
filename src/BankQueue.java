import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class BankQueue {
    private final Queue<Customer> queue = new LinkedList<>();
    private final int maxSize;
    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore items;
    private final Semaphore space;

    public BankQueue(int numTellers, int maxQueueSize) {
        this.maxSize = maxQueueSize;
        this.items = new Semaphore(numTellers);
        this.space = new Semaphore(maxQueueSize);
    }

    public void addCustomer(Customer customer) {
        try {
            if (space.tryAcquire()) {
                mutex.acquire();
                queue.add(customer);
                if (queue.size() == 1) {
                    items.release();
                }
                mutex.release();
            }
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
}
