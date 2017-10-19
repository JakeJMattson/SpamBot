import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Spammer implements Runnable
{
	boolean isAlive = true;
	int totalBrowsers;
	int browserIndex;
	
	public Spammer(int totalBrowsers, int browserIndex)
	{
		this.totalBrowsers = totalBrowsers;
		this.browserIndex = browserIndex;
	}
	
	@Override
	public void run()
	{
		WebDriver bot = null;
		
		try
		{
			bot = createWindow();
			placeBrowsers(bot);
			navigateToSpam(bot);
			
			//Thread is kept alive until the browser closes
			keepAlive(bot);
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		isAlive = false;
		bot.quit();
	}

	private WebDriver createWindow()
	{	
		//Allows use of ChromeDriver
		File folder = new File("./chromedriver.exe");
		try
		{
			System.setProperty("webdriver.chrome.driver", folder.getCanonicalPath());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		//Blocks developer pop-up
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-extensions");

		//Creates window
		WebDriver bot = new ChromeDriver(options);
		
		return bot;
	}
	
	private void navigateToSpam(WebDriver bot)
	{
		//Put your spam here
		bot.get("https://github.com/mattson543");
	}
	
	private void placeBrowsers(WebDriver bot)
	{
		//Local Variables
		Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		double widthOfScreen = screenSize.getWidth(), heightOfScreen = screenSize.getHeight();
		double browserWidth, browserHeight;
		int widthSlot = 0, heightSlot = 0, numOfRows, lastRow, browsersPerRow;
		Point browserPosition;
 
		//Determine number of rows
		numOfRows = (int)Math.sqrt(totalBrowsers);
		
		//Determine number of browsers in each row
		browsersPerRow = (int) Math.ceil((double)totalBrowsers / numOfRows);
		
		//Determine number of browsers in last row
		if (totalBrowsers == browsersPerRow)
			lastRow = browsersPerRow;
		else
			lastRow = totalBrowsers % browsersPerRow;
		
		//Determine size
		if (browserIndex + 1 > (totalBrowsers - lastRow))
			browserWidth = widthOfScreen/lastRow;
		else
			browserWidth = widthOfScreen/browsersPerRow;
		
		browserHeight = heightOfScreen/numOfRows;
		
		//Set size
		bot.manage().window().setSize(new Dimension((int)browserWidth, (int)browserHeight));
		
		//Determine position
		widthSlot = (int) (browserWidth * (browserIndex % browsersPerRow));
		heightSlot = (int) (browserHeight * (browserIndex / browsersPerRow));
		
		//Set position
		browserPosition = new Point(widthSlot, heightSlot);
		bot.manage().window().setPosition(browserPosition);
	}
	
	private void keepAlive(WebDriver bot)
	{		
		boolean running = true;
	    while(running) 
	    {
	        running = !isClosed(bot);
	        if (Thread.interrupted())
	        {
	            return;
	        }
	    }
	}
	
	private boolean isClosed(WebDriver bot) 
	{
		//Check to see if browser is closed
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
	
	public boolean isAlive()
	{
		return this.isAlive;
	}
}