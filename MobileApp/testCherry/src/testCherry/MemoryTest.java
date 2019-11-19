package testCherry;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MemoryTest extends launchTest {

	public void launchGame()
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.GridView/android.widget.FrameLayout[1]/android.widget.ImageView").click();
	}
	// verify memory game is launch
	//@Test
	public void launchMemory()
	{
		launchGame();
		System.out.println(driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.LinearLayout/android.widget.TextView[1]\r\n" ).getText());
		assertEquals("Mémory Test", "Memory Game", driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.LinearLayout/android.widget.TextView[1]\r\n" ).getText());
	
	}
	
	//verify accueil boutton is work
	//@Test
	public void accueuilBoutton()
	{
		launchGame();
		driver.findElementByXPath("	/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.widget.ImageButton[1]").click();
		String s = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.GridView/android.widget.FrameLayout[1]/android.widget.TextView\r\n").getText();
		System.out.println(s);
		assertEquals("accueil",s,"Cases mémoires");
	}

	//verify setting boutton is work
	@Test
	public void settingButton()
	{
		launchGame();
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.widget.ImageButton[2]").click();
		String s = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.GridView/android.widget.FrameLayout[2]/android.widget.TextView\r\n").getText();
		System.out.println(s);
		
	}
	
	@Test
	public void modeMemoryEasy()
	{Boolean isPresent = false;
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.GridView/android.widget.FrameLayout[1]/android.widget.ImageView").click();
		// verification card's Number
//	int nbr_case = driver.findElementsByXPath("hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.GridView\r\n");
//	System.out.println(nbr_case);	
	for (int i=1;i<13;i++)
		{

			for (int j=1;j<13;j++)
			{
				driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.LinearLayout/android.widget.GridView/android.widget.LinearLayout["+i+"]/android.widget.ImageView\r\n").click();

				if (i!=j)
				{
					driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.LinearLayout/android.widget.GridView/android.widget.LinearLayout["+j+"]/android.widget.ImageView\r\n").click();
			
					
				System.out.println("i = "+ i);
				System.out.println("j = "+ j);
				}
				}
			}
			
	
		
		//assertEquals("gagné", arg1, arg2);
		System.out.println("fin du game");
		String win = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.TextView").getText();
		System.out.println(win);
	}

	@Test
	public void modeMemoryNormal()
	{
		Boolean isPresent = false;
		
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.GridView/android.widget.FrameLayout[1]/android.widget.ImageView").click();
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.Button[2]").click();
		// verification card's Number
	
		for (int i=1;i<17;i++)
		{

			for (int j=1;j<17;j++)
			{
				driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.LinearLayout/android.widget.GridView/android.widget.LinearLayout["+i+"]/android.widget.ImageView\r\n").click();

				if (i!=j)
				{
					driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.LinearLayout/android.widget.GridView/android.widget.LinearLayout["+j+"]/android.widget.ImageView\r\n").click();
			
					
				System.out.println("i = "+ i);
				System.out.println("j = "+ j);
				}
				}
			}
			
	
		
		//assertEquals("gagné", arg1, arg2);
		System.out.println("fin du game");
		String win = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.TextView").getText();
		System.out.println(win);
	}
	
	@Test
	public void returnCard()
	{
		
	}

	public void okDouble()
	{
		
	}
	
	public void koDouble()
	{
		
	}
}
