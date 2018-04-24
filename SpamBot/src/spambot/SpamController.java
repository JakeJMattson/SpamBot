/*
 * Project Description:
 * Create 'n' browsers, if any of the browsers in the set is closed,
 * create (n + 1) browsers
 */

package spambot;

/**
 * Creates a group of browsers
 *
 * @author mattson543
 */
public class SpamController
{
	public static void main(String[] args)
	{
		//Arbitrary limit - can be changed
		int browserLimit = 12;

		//Create initial window
		Browser[] browsers = createBrowsers(1);

		while (browsers.length <= browserLimit)
			for (Browser browser : browsers)
				if (!browser.isAlive())
				{
					//If any browsers were closed, kill the others
					for (Browser browserInSet : browsers)
						browserInSet.kill();

					//Resize array and create new browsers
					browsers = createBrowsers(browsers.length + 1);

					break;
				}
	}

	/**
	 * Create and start an array of browsers for monitoring
	 *
	 * @param browserCount
	 *            Number of browsers to create
	 * @return Browsers
	 */
	private static Browser[] createBrowsers(int browserCount)
	{
		Browser[] browsers = new Browser[browserCount];

		for (int i = 0; i < browsers.length; i++)
		{
			//Create Browser(total, index)
			browsers[i] = new Browser(browsers.length, i);

			//Start a thread for each browser
			new Thread(browsers[i]).start();
		}

		return browsers;
	}
}