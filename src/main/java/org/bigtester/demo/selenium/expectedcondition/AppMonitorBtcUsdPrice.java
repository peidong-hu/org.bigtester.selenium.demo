/*******************************************************************************
 * ATE, Automation Test Engine
 *
 * Copyright 2018, Montreal PROT, or individual contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Montreal PROT.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.bigtester.demo.selenium.expectedcondition;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.bigtester.selenium.driverbuilder.SauceLabDriverBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * The Class AppMonitorBtcUsdPrice monitor the price of BTC in USD
 *
 * @author Peidong Hu
 */
public class AppMonitorBtcUsdPrice {
	
	/** The num btc. */
	static int numBtc = 1;
	
	/** The stop monitor. */
	static boolean stopMonitor = false;
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		SauceLabDriverBuilder sDriver = new SauceLabDriverBuilder("zhouyibhic",
				"5b66a725-2fd8-4d53-9328-d8c988ac58c6");
		final WebDriver driver = sDriver.getWebDriverInstance(true);
		WebDriverWait wait = new WebDriverWait(driver, 3600);
		driver.navigate().to("https://preev.com/");

		try {
			Runnable keyPressThread = new AppMonitorBtcUsdPrice.KeyPressThread(
					driver);
			Thread t = new Thread(keyPressThread);
			t.start();
			
			UIChanger uiChanger = new AppMonitorBtcUsdPrice.UIChanger(driver);
			uiChanger.initNumberOfBtcDiv("1");
			uiChanger.initNumberOfUsdDiv(driver.findElement(By
						.id("numField")).getAttribute("value"));
			uiChanger.initCommandMessageDiv();
			final WebElement btcNumberElement = driver.findElement(By.id("invField"));
			final StringBuffer usdNumber = new StringBuffer("0");
			while (!stopMonitor) {

				WebElement usdNumberElement = driver.findElement(By
						.id("numField"));
				wait.until(ExpectedConditions
						.refreshed(new ExpectedCondition<Boolean>() {
							public Boolean apply(WebDriver d) {
								WebElement usdNumberElement = driver
										.findElement(By.id("numField"));
								return !usdNumberElement.getAttribute("value")
										.equals(usdNumber.toString());
							}
						}));
				usdNumber.replace(0, usdNumber.length(),
						usdNumberElement.getAttribute("value"));
				if (!stopMonitor) {
					uiChanger.updateNumberOfBtcDiv(btcNumberElement.getAttribute("value"));
					uiChanger.updateNumberOfUsdDiv(usdNumber.toString());
				}
				//System.out.println(usdNumber);
			}
		} catch (Throwable thr) {
			thr.printStackTrace();
			driver.quit();
		} finally {
			driver.quit();
		}

	}

	/**
	 * The Class KeyPressThread.
	 *
	 * @author Peidong Hu
	 */
	public static class KeyPressThread implements Runnable {

		/** The input reader. */
		Scanner inputReader = new Scanner(System.in);
		
		/** The driver. */
		private WebDriver driver;

		// Method that gets called when the object is instantiated
		/**
		 * Instantiates a new key press thread.
		 *
		 * @param driver the driver
		 */
		public KeyPressThread(WebDriver driver) {
			this.driver = driver;
		}

		/**
		 * {@inheritDoc}
		 */
		public void run() {
			WebElement btcNumberElement = driver.findElement(By.id("invField"));
			UIChanger uiChanger = new UIChanger(this.driver);
			while (true) {
				String btcNumberString = btcNumberElement.getAttribute("value");

				if (inputReader.hasNext()) {
					String input = inputReader.next();
					if (StringUtils.isNumeric(input)
							&& Integer.valueOf(input) != Integer
									.valueOf(btcNumberString)) {
						btcNumberElement.clear();
						btcNumberElement.sendKeys(input);
						uiChanger.updateCommandMessageDiv(input + " - Change BTC number!");
					} else if (input.equalsIgnoreCase("Q")) {
						btcNumberElement.clear();
						btcNumberElement.sendKeys("1");
						stopMonitor = true;
						uiChanger.updateCommandMessageDiv(input + " - Stop signal!");
						uiChanger.deleteNumberOfBtcDiv();
						uiChanger.deleteNumberOfUsdDiv();
						//System.out.println("Stop signal received!");
						break; // stop everything;
					} else {
						uiChanger.updateCommandMessageDiv("Unregconized user console command!");
						//System.out.println("Unregconized input!");
					}
				}
			}
		}

	}
	
	/**
	 * The Class UIChanger.
	 *
	 * @author Peidong Hu
	 */
	public static class UIChanger {
		
		/** The js. */
		private JavascriptExecutor js;
		
		/**
		 * Instantiates a new UI changer.
		 *
		 * @param driver the driver
		 */
		public UIChanger(WebDriver driver) {
			this.js = (JavascriptExecutor) driver;;
		}
		
		/**
		 * Inits the number of btc.
		 *
		 * @param numBtc the num btc
		 */
		public void initNumberOfBtcDiv(String numBtc ) {
			js.executeScript("var numBtcDiv = document.createElement('div'); numBtcDiv.id='btcDivId'; document.getElementById('ratePane').appendChild(numBtcDiv); numBtcDiv.innerHTML='BTC amount collected by Selenium is: "+ numBtc +"'");
		}
		
		/**
		 * Inits the number of usd.
		 *
		 * @param numUsd the num usd
		 */
		public void initNumberOfUsdDiv(String numUsd) {
			js.executeScript("var numUsdDiv = document.createElement('div'); numUsdDiv.id='usdDivId'; document.getElementById('ratePane').appendChild(numUsdDiv); numUsdDiv.innerHTML='USD price collected by Selenium is: "+ numUsd +"'");
		}
		
		/**
		 * Inits the number of btc.
		 *
		 * @param numBtc the num btc
		 */
		public void deleteNumberOfBtcDiv() {
			js.executeScript("var numBtcDiv = document.getElementById('btcDivId'); numBtcDiv.parentNode.removeChild(numBtcDiv)");
		}
		
		/**
		 * Inits the number of usd.
		 *
		 * @param numUsd the num usd
		 */
		public void deleteNumberOfUsdDiv() {
			js.executeScript("var numUsdDiv = document.getElementById('usdDivId'); numUsdDiv.parentNode.removeChild(numUsdDiv)");
		}
		
		/**
		 * Inits the command message div.
		 */
		public void initCommandMessageDiv() {
			js.executeScript("var msgDiv = document.createElement('div'); msgDiv.id='msgDivId'; document.getElementById('ratePane').appendChild(msgDiv); msgDiv.innerHTML='User console command displays here.'");
		}
		
		/**
		 * Update command message div.
		 *
		 * @param commandsFromConsole the commands from console
		 */
		public void updateCommandMessageDiv(String commandsFromConsole) {
			js.executeScript("var msgDiv = document.getElementById('msgDivId'); msgDiv.innerHTML='User command ["+ commandsFromConsole +"]'");
		}
	
		/**
		 * Update number of btc.
		 *
		 * @param numBtc the num btc
		 */
		public void updateNumberOfBtcDiv(String numBtc ) {
			js.executeScript("var numBtcDiv = document.getElementById('btcDivId'); numBtcDiv.innerHTML='BTC amount collected by Selenium is: "+ numBtc +"'");
		}
		
		/**
		 * Update number of usd.
		 *
		 * @param numUsd the num usd
		 */
		public void updateNumberOfUsdDiv(String numUsd) {
			js.executeScript("var numUsdDiv = document.getElementById('usdDivId'); numUsdDiv.innerHTML='USD price collected by Selenium is: "+ numUsd +"'");
		}
		
	}

}
