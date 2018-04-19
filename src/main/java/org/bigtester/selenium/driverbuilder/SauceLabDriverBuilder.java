package org.bigtester.selenium.driverbuilder;

import org.openqa.selenium.Platform;

/**
 * The Class MyChromeDriver defines ....
 *
 * @author Peidong Hu
 */
public class SauceLabDriverBuilder extends AbstractRemoteWebDriverBuilder {

	/** The caps. */
	private String userName;
	/** The url. */
	private String accesskey;

	/**
	 * Instantiates a new my Chrome driver.
	 */
	public SauceLabDriverBuilder(String userName, String accesskey) {
		super("chrome", "43", Platform.ANY.toString(), "https://" + userName
				+ ":" + accesskey + "@ondemand.saucelabs.com:443/wd/hub");
		this.setUserName(userName);
		this.setAccesskey(accesskey);
	}

	/**
	 * Instantiates a new my sauce lab driver.
	 *
	 * @param browserName
	 *            the browser name
	 * @param version
	 *            the version
	 * @param userName
	 *            the user name
	 * @param accesskey
	 *            the accesskey
	 */
	public SauceLabDriverBuilder(String browserName, String version,
			String userName, String accesskey) {
		super(browserName, version, Platform.ANY.toString(), "https://"
				+ userName + ":" + accesskey
				+ "@ondemand.saucelabs.com:443/wd/hub");
		this.setUserName(userName);
		this.setAccesskey(accesskey);
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the accesskey
	 */
	public String getAccesskey() {
		return accesskey;
	}

	/**
	 * @param accesskey
	 *            the accesskey to set
	 */
	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}

}
