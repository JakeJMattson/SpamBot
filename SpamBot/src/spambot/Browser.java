package spambot;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Creates and controls an individual browser.
 *
 * @author mattson543
 */
public class Browser implements Runnable
{
	/**
	 * WebDriver to control the browser
	 */
	private WebDriver bot;
	/**
	 * Total number of active browsers
	 */
	private final int totalBrowsers;
	/**
	 * The index of this browser
	 */
	private final int browserIndex;
	/**
	 * Whether or not the browser has closed
	 */
	private boolean isAlive = true;

	public Browser(int totalBrowsers, int browserIndex)
	{
		this.totalBrowsers = totalBrowsers;
		this.browserIndex = browserIndex;
	}

	/**
	 * Externally called - check if browser is still open
	 *
	 * @return Status
	 */
	public boolean isAlive()
	{
		return isAlive;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		//Creates window
		bot = new ChromeDriver();

		//Initial setup
		placeBrowser();
		navigateToSpam();

		//Thread is kept alive until the browser closes
		keepAlive();

		//Call automatically if this browser is closed
		kill();
	}

	/**
	 * Destination of the browser
	 */
	private void navigateToSpam()
	{
		//Put your spam here
		bot.get("https://github.com/mattson543");
	}

	/**
	 * Dynamically place the browser on the screen
	 */
	private void placeBrowser()
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
		double browserHeight = screenSize.getHeight() / numOfRows;
		double browserWidth = screenSize.getWidth();

		if (browserIndex + 1 > totalBrowsers - lastRow)
			browserWidth /= lastRow;
		else
			browserWidth /= browsersPerRow;

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
	 * Prevent the Thread from completing and closing the browser
	 */
	private void keepAlive()
	{
		boolean running = true;
		while (running)
		{
			running = !isClosed();

			if (Thread.interrupted())
				return;
		}
	}

	/**
	 * Determine whether or not the browser has already closed
	 *
	 * @return Status
	 */
	private boolean isClosed()
	{
		//Check to see if browser is closed
		//There is currently no official way to do this
		try
		{
			bot.getCurrentUrl();
			return false;
		}
		catch (Exception e)
		{
			return true;
		}
	}

	/**
	 * Dispose of the browser and the driver
	 */
	public void kill()
	{
		isAlive = false;

		try
		{
			bot.quit();
		}
		catch (Exception e)
		{

		}
	}
}