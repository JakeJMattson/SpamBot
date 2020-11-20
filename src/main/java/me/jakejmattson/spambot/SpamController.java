package me.jakejmattson.spambot;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Creates a group of browsers.
 */
public class SpamController {
    public static void main(String[] args) {
        //Allow use of WebDrivers
        WebDriverManager.chromedriver().setup();

        //Arbitrary limit
        final int MAX_THREADS = 12;

        Thread[] threads = createThreads(1);

        while (threads.length < MAX_THREADS)
            for (Thread currentThread : threads)
                if (!currentThread.isAlive()) {
                    //If any browsers in the set were closed, kill the others
                    for (Thread deadThread : threads)
                        deadThread.interrupt();

                    threads = createThreads(threads.length + 1);
                    break;
                }
    }

    /**
     * Create and start an array of threads for monitoring.
     *
     * @param threadCount Number of threads to create
     *
     * @return Newly created threads
     */
    private static Thread[] createThreads(int threadCount) {
        Thread[] threads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(new Browser(threads.length, i));
            threads[i].start();
        }

        return threads;
    }
}