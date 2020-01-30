import java.io.IOException;
public class OurMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        SharedMemory memory = new SharedMemory();

        Thread t1 = new Thread(new OurThread(memory, 10));
        Thread t2 = new Thread(new OurThread(memory, 5));

        t1.start();
        t2.start();
     clrscr();
        System.out.println(3);
    }
    public static void clrscr(){
        //Clears Screen in java
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {}
    }
}
