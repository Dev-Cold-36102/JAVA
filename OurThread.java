public class OurThread implements Runnable {
    SharedMemory memory;
    int newNumber;

    OurThread(SharedMemory memory, int newNumber) {
        this.memory = memory;
        this.newNumber = newNumber;
    }
    @Override
    public synchronized void run() {
        System.out.println(this.newNumber);
        this.memory.changeCurrent(this.newNumber);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
