package engine.tools.listeners;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

public class WebListener implements WebDriverListener {

	@Override
	public void beforeGet (WebDriver driver, String url) {
		System.out.println("Before Get:[" + url + "]");
	}

	@Override
	public void afterGet (WebDriver driver, String url) {
		System.out.println("After Get:[" + url + "]");
	}

	@Override
	public void beforeGetCurrentUrl (WebDriver driver) {
	}

	@Override
	public void afterGetCurrentUrl (String result, WebDriver driver) {
	}


	@Override
	public void beforeGetTitle (WebDriver driver) {
	}

	@Override
	public void afterGetTitle (WebDriver driver, String result) {
	}

	@Override
	public void beforeClick (WebElement element) {
	}

	@Override
	public void afterClick (WebElement element) {
	}

	@Override
	public void beforeSendKeys (WebElement element, CharSequence... keysToSend) {
	}

	@Override
	public void afterSendKeys (WebElement element, CharSequence... keysToSend) {
	}

	@Override
	public void beforeClear (WebElement element) {
	}

	@Override
	public void afterClear (WebElement element) {
	}
}
