# Demo of Selenium API Usage

Project is to demo how to use Selenium API to monitor and display the BTC and its converted USD amount on the same web page.

## Getting Started

Just run the Java project in Eclipse as Java application. It will automatically connect to my SauceLabs cloud testing lab to launch the browser and run the monitoring tool.

### Prerequisites

Java 8 needed to run the code.

## Running the tool

Just run the Java project in Eclipse as Java application. It will automatically connect to my SauceLabs cloud testing lab to launch the browser and run the monitoring tool. The code will invoke Selenium api to initialize the panels to display the value of the BTC and USD amount collected by Selenium api with ExpectedConditions as the trigger of collecting operations.
If the USD price is changed, Selenium ExpectedCondition will be met and then trigger Selenium to read the USD price and then display it on the web page.

Eclipse console can accept user commands including,
* Type any number and [Enter], Selenium will change the field of BTC amount
* Type Q or q and [Enter], the program will stop and change the field of BTC amount to 1
* Type any other information, the program will display Unreganized User Command message 

## Authors

* **Peidong Hu** - *Initial work* - [peidong-hu](https://github.com/peidong-hu)


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

