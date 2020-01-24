package me.jakejmattson.spambot;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.awt.Rectangle;

/**
 * Creates and controls an individual browser.
 *
 * @author JakeJMattson
 */
class Browser implements Runnable
{
	/**
	 * Total number of active browsers
	 */
	private final int totalBrowsers;
	/**
	 * The index of this browser
	 */
	private final int browserIndex;

	Browser(int totalBrowsers, int browserIndex)
	{
		this.totalBrowsers = totalBrowsers;
		this.browserIndex = browserIndex;
	}

	@Override
	public void run()
	{
		WebDriver bot = new ChromeDriver();
		placeBrowser(bot);
		navigateToSpam(bot);
		keepAlive(bot);
	}

	/**
	 * Destination of the browser.
	 *
	 * @param bot
	 * 		WebDriver instance
	 */
	private static void navigateToSpam(WebDriver bot)
	{
		//TODO Put your spam here
		bot.get("https://github.com/JakeJMattson");
	}

	/**
	 * Dynamically place the browser on the screen.
	 *
	 * @param bot
	 * 		WebDriver instance
	 */
	private void placeBrowser(WebDriver bot)
	{
		int numOfRows = (int) Math.sqrt(totalBrowsers);
		int browsersPerRow = (int) Math.ceil((double) totalBrowsers / numOfRows);
		int lastRow = totalBrowsers % browsersPerRow;

		if (lastRow == 0)
			lastRow = browsersPerRow;

		Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		double browserHeight = Math.round(screenSize.getHeight() / numOfRows);
		double browserWidth = screenSize.getWidth();

		int divisor = browserIndex + 1 > totalBrowsers - lastRow ? lastRow : browsersPerRow;
		browserWidth = Math.round(browserWidth / divisor);

		bot.manage().window().setSize(new Dimension((int) browserWidth, (int) browserHeight));

		int widthSlot = (int) (browserWidth * (browserIndex % browsersPerRow));
		int heightSlot = (int) (browserHeight * (browserIndex / browsersPerRow));

		Point browserPosition = new Point(widthSlot, heightSlot);
		bot.manage().window().setPosition(browserPosition);
	}

	/**
	 * Prevent the Thread from completing and closing the browser.
	 *
	 * @param bot
	 * 		WebDriver instance
	 */
	private static void keepAlive(WebDriver bot)
	{
		while (!Thread.interrupted())
			try
			{
				bot.getCurrentUrl();
			}
			catch (WebDriverException e)
			{
				Thread.currentThread().interrupt();
			}

		bot.quit();
	}
}