package testCherry;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class launchTest {

	protected RemoteWebDriver driver;

	@Before
	public void setUp() {
		// location of the app
		File app = new File(
				"C:\\Users\\dlarzaba\\Desktop\\projet_cherry_2019\\MobileApp\\app\\build\\outputs\\apk\\debug\\app-debug.apk");

		// To create an object of Desired Capabilities
		DesiredCapabilities capability = new DesiredCapabilities();
		// OS Name
		capability.setCapability("device", "Android");
		// Mobile OS version. In My case its running on Android 4.2
		capability.setCapability(CapabilityType.VERSION, "7.1.1");
		capability.setCapability("app", app.getAbsolutePath());
		// To Setup the device name
		capability.setCapability("deviceName", "htc");
		capability.setCapability("platformName", "Android");

		// driver object with new Url and Capabilities
		try {
			setDriver(new RemoteWebDriver(new URL("http://0.0.0.0:4723/wd/hub"), capability));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void launchApplication() {

		String device = getDriver().getCapabilities().getCapability("device").toString();
		String deviceName = getDriver().getCapabilities().getCapability("deviceName").toString();
		// System.out.println(deviceName);
		String platformName = getDriver().getCapabilities().getCapability("platformName").toString();
		String version = getDriver().getCapabilities().getCapability(CapabilityType.VERSION).toString();
		assertEquals("verify capability device", "Android", device);
		//	assertEquals("verify capability deviceName", "HT4CCJT00098", deviceName);
		assertEquals("verify capability platformName", "LINUX", platformName);
		assertEquals("verify capability version", "7.1.1", version);
		String s = getTitleGame(1);
		assertEquals("accueil",s,"Cases mémoires");
		s = getTitleGame(2);
		System.out.println(s);
		assertEquals("accueil",s,"4 Images 1 Mot");
		s = getTitleGame(3);
		System.out.println(s);
		assertEquals("accueil",s,"Chorégraphie");
		s = getTitleGame(4);
		System.out.println(s);
		assertEquals("accueil",s,"Mouvements");
		s = getTitleGame(5);
		System.out.println(s);
		assertEquals("accueil",s,"Calculatrice");
		s = getTitleGame(6);
		System.out.println(s);
		assertEquals("accueil",s,"Blagues");
		s = getTitleGame(7);
		System.out.println(s);
		assertEquals("accueil",s,"Histoires");
	
	}

	@After
	public void tearDown() {
		driver.quit();

	}

	public RemoteWebDriver getDriver() {
		return driver;
	}

	public void setDriver(RemoteWebDriver driver) {
		this.driver = driver;
	}

	public String getTitleGame(int jeu) {
		return driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.GridView/android.widget.FrameLayout["+jeu+"]/android.widget.TextView\r\n").getText();
	}
 public void scrollDown() {
	 JavascriptExecutor js = (JavascriptExecutor)driver;
	 js.executeScript("scrollBy(0,500)");
 }
}