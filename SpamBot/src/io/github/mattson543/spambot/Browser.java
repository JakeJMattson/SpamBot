package io.github.mattson543.spambot;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Creates and controls an individual browser.
 *
 * @author mattson543
 */
public class Browser implements Runnable
{
	/**
	 * Total number of active browsers
	 */
	private final int totalBrowsers;
	/**
	 * The index of this browser
	 */
	private final int browserIndex;

	public Browser(int totalBrowsers, int browserIndex)
	{
		this.totalBrowsers = totalBrowsers;
		this.browserIndex = browserIndex;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		//Create window
		WebDriver bot = new ChromeDriver();

		//Initial setup
		placeBrowser(bot);
		navigateToSpam(bot);

		//Keep thread alive until the browser closes
		keepAlive(bot);
	}

	/**
	 * Destination of the browser.
	 *
	 * @param bot
	 *            WebDriver instance
	 */
	private void navigateToSpam(WebDriver bot)
	{
		//Put your spam here
		bot.get("https://github.com/mattson543");
	}

	/**
	 * Dynamically place the browser on the screen.
	 *
	 * @param bot
	 *            WebDriver instance
	 */
	private void placeBrowser(WebDriver bot)
	{
		//Determine number of rows
		int numOfRows = (int) Math.sqrt(totalBrowsers);

		//Determine number of browsers in each row
		int browsersPerRow = (int) Math.ceil((double) totalBrowsers / numOfRows);

		//Determine number of browsers in last row
		int lastRow = totalBrowsers % browsersPerRow;

		if (lastRow == 0)
			lastRow = browsersPerRow;

		//Determine size
		Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		double browserHeight = Math.round(screenSize.getHeight() / numOfRows);
		double browserWidth = screenSize.getWidth();

		int divisor = browserIndex + 1 > totalBrowsers - lastRow ? lastRow : browsersPerRow;
		browserWidth = Math.round(browserWidth / divisor);

		//Set size
		bot.manage().window().setSize(new Dimension((int) browserWidth, (int) browserHeight));

		//Determine position
		int widthSlot = (int) (browserWidth * (browserIndex % browsersPerRow));
		int heightSlot = (int) (browserHeight * (browserIndex / browsersPerRow));

		//Set position
		Point browserPosition = new Point(widthSlot, heightSlot);
		bot.manage().window().setPosition(browserPosition);
	}

	/**
	 * Prevent the Thread from completing and closing the browser.
	 *
	 * @param bot
	 *            WebDriver instance
	 */
	private void keepAlive(WebDriver bot)
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

		//Cleanup when finished
		bot.quit();
	}
}