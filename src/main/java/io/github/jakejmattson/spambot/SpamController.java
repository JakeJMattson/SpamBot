/**
 * The MIT License
 * Copyright © 2017 Jake Mattson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.jakejmattson.spambot;

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