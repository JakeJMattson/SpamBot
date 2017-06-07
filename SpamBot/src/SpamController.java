public class SpamController
{	
	public static void main(String[] args)
	{
		SpamController driver = new SpamController();
		driver.beginSpam();
	}
	
	public void beginSpam()
	{
		int numOfSpammers = 1;
		int previousSpammers = 0;
		int spammerLimit = 25;
		
		//Create the initial arrays
		Spammer[] spammers = new Spammer[numOfSpammers];
		Thread[] threads = new Thread[numOfSpammers];
		
		boolean closedBrowsers = false;
		
		while (numOfSpammers <= spammerLimit)
		{
			//Only create new threads once when the numbers change
			if (numOfSpammers != previousSpammers)
			{
				//Resize arrays
				spammers = new Spammer[numOfSpammers];
				threads = new Thread[numOfSpammers];
				
				for (int i = 0; i < numOfSpammers; i++)
				{
					//Create Spammer(total, index)
					spammers[i] = new Spammer(numOfSpammers, i);
					
					//Create threads for each Spammer
					threads[i] = new Thread(spammers[i]);
				}
				
				for (int j = 0; j < numOfSpammers; j++)
				{
					//Start all threads
					threads[j].start();
				}
				
				//Set numbers equal once threads have been created
				previousSpammers = numOfSpammers;
			}
			
			for (int k = 0; k < numOfSpammers; k++)
			{				
				//Check to see if each thread is still alive and that each browser is still open
				if (!threads[k].isAlive() || !spammers[k].isAlive())
				{
					closedBrowsers = true;
				}
			}
			
			//If any browsers were closed (or threads died), increase the total by 1
			if (closedBrowsers)
				numOfSpammers++;
			
			//Reset the indicator
			closedBrowsers = false;
			
			try
			{
				//Adds delay
				Thread.sleep(2000);
			}
			
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}