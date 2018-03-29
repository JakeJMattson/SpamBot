package spambot;

public class SpamController
{
	public static void main(String[] args)
	{
		SpamController driver = new SpamController();
		driver.beginSpam();
	}

	public void beginSpam()
	{
		//Arbitrary limit - can be changed
		int spammerLimit = 12;

		//Create initial window
		Spammer[] spammers = createSpammers(1);

		while (spammers.length <= spammerLimit)
			for (Spammer spammer : spammers)
				if (!spammer.isAlive())
				{
					//If any browsers were closed, kill the others
					for (Spammer spammerInSet : spammers)
						spammerInSet.kill();

					//Resize array and create new browsers
					spammers = createSpammers(spammers.length + 1);

					break;
				}
	}

	private Spammer[] createSpammers(int spammerCount)
	{
		Spammer[] spammers = new Spammer[spammerCount];

		for (int i = 0; i < spammers.length; i++)
		{
			//Create Spammer(total, index)
			spammers[i] = new Spammer(spammers.length, i);

			//Start a thread for each Spammer
			new Thread(spammers[i]).start();
		}

		return spammers;
	}
}