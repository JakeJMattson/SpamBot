/*
 * Class Description:
 * Creates and controls an individual browser.
 */

package spambot;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Spammer implements Runnable
{
	private WebDriver bot;
	private final int totalBrowsers;
	private final int browserIndex;
	private boolean isAlive = true;

	public Spammer(int totalBrowsers, int browserIndex)
	{
		this.totalBrowsers = totalBrowsers;
		this.browserIndex = browserIndex;
	}

	public boolean isAlive()
	{
		return isAlive;
	}

	@Override
	public void run()
	{
		//Initial setup
		createBot();
		placeBrowser();
		navigateToSpam();

		//Thread is kept alive until the browser closes
		keepAlive();

		//Call automatically if this browser is closed
		kill();
	}

	private void createBot()
	{
		//Allows use of ChromeDriver
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

		//Creates window
		bot = new ChromeDriver();
	}

	private void navigateToSpam()
	{
		//Put your spam here
		bot.get("https://github.com/mattson543");
	}

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

	private void keepAlive()
	{
		boolean running = true;
		while (running)
		{
			running = !isClosed(bot);

			if (Thread.interrupted())
				return;
		}
	}

	private boolean isClosed(WebDriver bot)
	{
		//Check to see if browser is closed
		//There is currently no good way to do this
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