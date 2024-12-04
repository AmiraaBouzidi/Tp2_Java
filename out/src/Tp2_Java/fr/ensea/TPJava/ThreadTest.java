package fr.ensea.TPJava;

public class ThreadTest extends Thread {
    private int counter = 0;

    @Override
    public void run() {
        while (true) {
            System.out.println(getName() + ": " + counter++);
            try {
                Thread.sleep(100); // pause de 100 ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}