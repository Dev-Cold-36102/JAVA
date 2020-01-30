public class SharedMemory {
    int currentNumber = 0;

    void changeCurrent(int newNumber) {
        this.currentNumber = newNumber;
    }

     void showMemory() {
        System.out.println(this.currentNumber);
    }
}
