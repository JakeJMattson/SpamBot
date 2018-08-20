package io.github.JakeJMattson.spambot;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Creates a group of browsers.
 *
 * @author JakeJMattson
 */
public class SpamController
{
	public static void main(String[] args)
	{
		//Allow use of WebDrivers
		WebDriverManager.chromedriver().setup();

		//Arbitrary limit
		int threadLimit = 12;

		//Create initial window
		Thread[] threads = createThreads(1);

		while (threads.length < threadLimit)
			for (Thread currentThread : threads)
				if (!currentThread.isAlive())
				{
					//If any browsers in the set were closed, kill the others
					for (Thread deadThread : threads)
						deadThread.interrupt();

					//Create new set of threads
					threads = createThreads(threads.length + 1);

					break;
				}
	}

	/**
	 * Create and start an array of threads for monitoring.
	 *
	 * @param threadCount
	 * 		Number of threads to create
	 *
	 * @return Threads
	 */
	private static Thread[] createThreads(int threadCount)
	{
		Thread[] threads = new Thread[threadCount];

		for (int i = 0; i < threads.length; i++)
		{
			//Create a Browser for each thread
			threads[i] = new Thread(new Browser(threads.length, i));

			//Start
			threads[i].start();
		}

		return threads;
	}
}