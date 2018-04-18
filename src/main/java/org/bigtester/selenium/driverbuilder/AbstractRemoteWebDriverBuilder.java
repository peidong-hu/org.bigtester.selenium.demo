package org.bigtester.selenium.driverbuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

// TODO: Auto-generated Javadoc
/**
 * The Class WebDriverBase defines ....
 *
 * @author Peidong Hu
 */
abstract public class AbstractRemoteWebDriverBuilder {

	protected WebDriver webDriver;

	/** The caps. */
	private Optional<DesiredCapabilities> caps = Optional.empty();

	/** The url. */
	private String url;

	/**
	 * Instantiates a new my Chrome driver.
	 */
	public AbstractRemoteWebDriverBuilder(String browserName, String version,
			String platform, String url) {

		super();
		if (StringUtils.isEmpty(browserName)) {
			caps = Optional.of(DesiredCapabilities.chrome());
			caps.get().setBrowserName("chrome");
		} else {
			switch (browserName) {
			case "chrome":
				caps = Optional.of(DesiredCapabilities.chrome());
				caps.get().setBrowserName("chrome");
				break;
			case "firefox":
				caps = Optional.of(DesiredCapabilities.firefox());
				caps.get().setBrowserName("firefox");
				break;
			default:
				break;
			}

		}
		if (!StringUtils.isEmpty(version))
			caps.get().setVersion(version);
		if (!StringUtils.isEmpty(platform))
			caps.get().setPlatform(Platform.valueOf(platform));
		caps.get().setCapability("maxDuration", 10800);
		caps.get().setCapability("screenResolution", "1024x768");
		caps.get().setCapability("takesScreenshot", false);
		caps.get().setCapability("takesElementScreenshot", false);
		if (!StringUtils.isEmpty(version))
			caps.get().setVersion(version);
		this.setUrl(url);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public WebDriver getWebDriverInstance(boolean forceToNew) {
		WebDriver retVal = getWebDriver();

		if (null == retVal
				|| forceToNew
				|| !(((EventFiringWebDriver) retVal).getWrappedDriver() instanceof RemoteWebDriver)) {

			try {
				RemoteWebDriver remoteVal = new RemoteWebDriver(new URL(url),
						caps.get());
				retVal = remoteVal;
			} catch (MalformedURLException e) {
				// TODO add handling for bad url
				e.printStackTrace(); // NOPMD
			}

			setWebDriver(retVal);
		}
		return retVal;

	}

	public WebDriver getWebDriver() {
		return webDriver;
	}

	public void setWebDriver(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

}
