package PageObject;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.list.LazyList;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

import Utility.ConfigReader;
import Utility.Utilities;

public class ToDoPage {
	 	private WebDriver driver;
	 	private ExtentTest test;
	    private ConfigReader configReader;
	    private WebDriverWait wait;
	    private By inputField;
	    private By allTab;
	    private By activeTab;
	    private By completedTab;
	    private By taskslist;
	    private By markAsCompletedButton;
	    private By taskButton;
	    private By deleteButton;
	    private Actions actions;
	    

	    public ToDoPage(WebDriver driver) throws IOException {
	        this.driver = driver;
	        configReader = new ConfigReader(Paths.get(Utilities.getSystemPath(), "src", "test", "resources", "config.properties").toString());
	        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        actions = new Actions(driver);
	       
	        inputField = By.xpath(configReader.getXPath("todo.inputField.xpath"));
	        allTab = By.xpath(configReader.getXPath("todo.allTab.xpath"));
	        activeTab = By.xpath(configReader.getXPath("todo.activeTab.xpath"));
	        completedTab = By.xpath(configReader.getXPath("todo.completedTab.xpath"));
	        taskslist = By.xpath(configReader.getXPath("todo.getTasksList.xpath"));
	    }

	    public void addToDo(String task) {
	        wait.until(ExpectedConditions.visibilityOfElementLocated(inputField)).sendKeys(task);
	        new Actions(driver).sendKeys(Keys.RETURN).perform();
	    }

	    public void markAsCompleted(String task) {
	    	markAsCompletedButton = By.xpath(String.format(configReader.getXPath("todo.markAsCompleted.xpath"), task));
	        actions.moveToElement(driver.findElement(markAsCompletedButton)).click().perform();
	    }

	    public void deleteToDo(String task) throws InterruptedException {
	    	taskButton = By.xpath(String.format(configReader.getXPath("todo.taskElement.xpath"), task));
	        deleteButton = By.xpath(String.format(configReader.getXPath("todo.deleteButton.xpath"), task));;
	        
	        Thread.sleep(5000); //handled to adjust the slowness in webpage as mentioned in email
	        actions.moveToElement(driver.findElement(allTab)).click().perform();
	        wait.until(ExpectedConditions.visibilityOfElementLocated(allTab));

	        wait.until(ExpectedConditions.visibilityOfElementLocated(taskButton));
	        actions.moveToElement(driver.findElement(taskButton)).perform();
	        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
	        
	        wait.until(ExpectedConditions.invisibilityOfElementLocated(taskButton));
	    }

	    public List<String> getAllTasks() {
	        driver.findElement(allTab).click();
	        return getTasksList();
	    }

	    public List<String> getActiveTasks() {
	        driver.findElement(activeTab).click();
	        return getTasksList();
	    }

	    public List<String> getCompletedTasks() {
	        driver.findElement(completedTab).click();
	        return getTasksList();
	    }

	    private List<String> getTasksList() {
	        List<WebElement> elements = driver.findElements(taskslist);
	        List<String> tasks = new ArrayList<>();
	        for (WebElement e : elements) {
	            tasks.add(e.getText());
	        }
	        return tasks;
	    }

		public void EditToDo(String task, String appendText) throws InterruptedException {
			actions.moveToElement(driver.findElement(allTab)).click().perform();
		    taskButton = By.xpath(String.format(configReader.getXPath("todo.taskElement.xpath"), task));
		    WebElement taskElement = driver.findElement(taskButton);

		    actions.moveToElement(taskElement).doubleClick().sendKeys(" " + appendText).sendKeys(Keys.RETURN).perform();
		    Thread.sleep(2000); 
		}
}
