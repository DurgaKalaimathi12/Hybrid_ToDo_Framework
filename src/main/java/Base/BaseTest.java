package Base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import Utility.ConfigReader;
import Utility.Utilities;

public class BaseTest {
	public WebDriver driver;
    private ConfigReader configReader;
    public static ExtentReports extent;
    public static ExtentTest test;
    
    @BeforeSuite
    public void setupExtentReport() {
    	ExtentSparkReporter  htmlReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
        htmlReporter.config().setDocumentTitle("To-Do Automation Reports");
        htmlReporter.config().setReportName("To-Do List Tests Execution");
        htmlReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }


    @Parameters("browser")
    @BeforeClass
    public void setup(String browser) throws InterruptedException, IOException {
    	
    	configReader = new ConfigReader(Paths.get(Utilities.getSystemPath(), "src", "test", "resources", "config.properties").toString());
        
        String appUrl = configReader.getAppUrl();
       
        if (browser.equalsIgnoreCase("chrome")) {
        	System.setProperty("webdriver.chrome.driver", Paths.get(Utilities.getSystemPath(), "extlib", "chromedriver.exe").toString()); //add driver
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
        	System.setProperty("webdriver.firefox.driver", Paths.get(Utilities.getSystemPath(), "extlib", ".exe").toString()); //add driver
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            System.setProperty("webdriver.edge.driver", Paths.get(Utilities.getSystemPath(), "extlib", "msedgedriver.exe").toString());
            driver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Browser not supported");
        }

        driver.manage().window().maximize();
        driver.get(appUrl);  // Use URL from configReader
        //Thread.sleep(6000);
    }
    
    public String captureScreenshot(String testName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File srcFile = ts.getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String destPath = System.getProperty("user.dir") + "/screenshots/" + testName + "_" + timestamp + ".png";
        
        try {
            FileUtils.copyFile(srcFile, new File(destPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return destPath;
    }

	@AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    @AfterSuite
    public void closeExtentReport() {
        extent.flush(); // Generate the final report
    }
}
