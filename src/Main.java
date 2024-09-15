public class Main {
    public static void main(String[] args) {
        int numTellers = 3;
        int maxBankQueueSize = 5;
        int numQueues = 3;
        int maxGroceryQueueSize = 2;
        int minutes = 120; // Simulation time in minutes

        BankQueue bankQueue = new BankQueue(numTellers, maxBankQueueSize);
        GroceryQueues groceryQueues = new GroceryQueues(numQueues, maxGroceryQueueSize);

        QueueSimulator simulator = new QueueSimulator(bankQueue, groceryQueues);
        simulator.startSimulation(minutes);
    }
}
