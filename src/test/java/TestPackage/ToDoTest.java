package TestPackage;

import java.io.IOException;
import java.util.List;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import Base.BaseTest;
import PageObject.ToDoPage;
import Utility.TestDataProvider;

public class ToDoTest extends BaseTest{
    
	
	@Test(priority = 1, dataProvider = "taskDataByActionAndTestCaseAdd", dataProviderClass = TestDataProvider.class)
	public void testAddToDos(String taskName, String action, String testCase, String newText) throws InterruptedException, IOException {
	    if (action.equalsIgnoreCase("ADD")) {
	        test = extent.createTest("Add To-Do: " + taskName);
	        ToDoPage todoPage = new ToDoPage(driver);

	        Reporter.log("Adding task: " + taskName, true);
	        //Thread.sleep(10000);
	        test.info("Adding task: " + taskName);
	        todoPage.addToDo(taskName);
	        String screenshotPath = captureScreenshot("testAddToDo_" + taskName);

	        try {
	            List<String> allTasks = todoPage.getAllTasks();
	            Assert.assertTrue(allTasks.contains(taskName), "Task '" + taskName + "' not found!");

	            test.pass("Successfully added task: " + taskName, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
	        } catch (AssertionError e) {
	            test.fail("Failed to add task: " + taskName + ". " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
	            throw e; 
	        }
	    }
	}

    
    @Test(priority = 2, dataProvider = "taskDataForMarkCompleted", dataProviderClass = TestDataProvider.class)
    public void testMarkTasksAsCompleted(String taskName, String action, String testCase, String newText) throws InterruptedException, IOException {
        if (action.equalsIgnoreCase("COMPLETE")) {
            test = extent.createTest("Mark To-Do as Completed: " + taskName);
            ToDoPage todoPage = new ToDoPage(driver);

            Reporter.log("Completing task: " + taskName, true);
            //Thread.sleep(10000);
            test.info("Completing task: " + taskName);
            todoPage.markAsCompleted(taskName);

            String screenshotPath = captureScreenshot("testMarkAsCompleted_" + taskName);

            try {
                List<String> completedTasks = todoPage.getCompletedTasks();
                Assert.assertTrue(completedTasks.contains(taskName), "Task '" + taskName + "' is not completed!");

                test.pass("Successfully completed task: " + taskName, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } catch (AssertionError e) {
                test.fail("Failed to complete task: " + taskName + ". " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                throw e;
            }
        }
    }


    @Test(priority = 3, dataProvider = "taskDataForDelete", dataProviderClass = TestDataProvider.class)
    public void testDeleteTasks(String taskName, String action, String testCase, String newText) throws InterruptedException, IOException {
        if (action.equalsIgnoreCase("DELETE")) {
            test = extent.createTest("Delete To-Do: " + taskName);
            ToDoPage todoPage = new ToDoPage(driver);

            Reporter.log("Deleting task: " + taskName, true);
            //Thread.sleep(10000);
            test.info("Deleting task: " + taskName);
            todoPage.deleteToDo(taskName);

            String screenshotPath = captureScreenshot("testDeleteToDo_" + taskName);

            try {
                List<String> allTasks = todoPage.getAllTasks();
                Assert.assertFalse(allTasks.contains(taskName), "Task '" + taskName + "' is still present!");

                test.pass("Successfully deleted task: " + taskName, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } catch (AssertionError e) {

                test.fail("Failed to delete task: " + taskName + ". " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                throw e;  
            }
        }
    }
    
    @Test(priority = 4, dataProvider = "taskDataForEdit", dataProviderClass = TestDataProvider.class)
    public void testEditTask(String taskName, String action, String testCase, String appendText) throws InterruptedException, IOException {
        if (action.equalsIgnoreCase("EDIT")) {
            test = extent.createTest("Edit To-Do: " + taskName);
            ToDoPage todoPage = new ToDoPage(driver);

            Reporter.log("Editing task: " + taskName, true);
            Reporter.log("Edit task now changed to: " + taskName + appendText, true);
            test.info("Editing task: " + taskName);
            test.info("Edit task now changed to: " + taskName + appendText);
            todoPage.EditToDo(taskName, appendText);  // Pass both taskName and newText to EditToDo method

            String screenshotPath = captureScreenshot("testEditToDo_" + taskName);

            try {
                List<String> allTasks = todoPage.getAllTasks();
                Assert.assertTrue(allTasks.contains(taskName + " " + appendText), "Task '" + taskName + " " + appendText + "' is not present after edit!");

                test.pass("Successfully edited task: " + taskName + " to " + taskName+" "+appendText, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } catch (AssertionError e) {

                test.fail("Failed to edit task: " + taskName + ". " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                throw e;
            }
        }
    }


 
    @Test(priority = 5, dataProvider = "taskDataByActionAndTestCaseAddFilter", dataProviderClass = TestDataProvider.class)
    public void testFilterToDos(String taskName, String action, String testCase, String newText) throws InterruptedException, IOException {
        if (testCase.equalsIgnoreCase("testFilterToDos")) {
            test = extent.createTest("Filter To-Dos Test for: " + taskName);
            ToDoPage todoPage = new ToDoPage(driver);

            Reporter.log("Filter check -> Adding task: " + taskName, true);
            //Thread.sleep(10000);
            test.info("Filter check -> Adding task: " + taskName);
            todoPage.addToDo(taskName);

            String screenshotPath = captureScreenshot("Filtercheck_testFilterToDos_" + taskName);

            try {
                Reporter.log("Filter check -> Verifying 'All' tab contains task: " + taskName, true);
                test.info("Filter check -> Verifying 'All' tab contains task: " + taskName);
                List<String> allTasks = todoPage.getAllTasks();
                Assert.assertTrue(allTasks.contains(taskName), "All tab does not contain '" + taskName + "'");

                Reporter.log("Filter check -> Verifying 'Active' tab contains task: " + taskName, true);
                test.info("Filter check -> Verifying 'Active' tab contains task: " + taskName);
                List<String> activeTasks = todoPage.getActiveTasks();
                Assert.assertTrue(activeTasks.contains(taskName), "Active tab does not contain '" + taskName + "'");

                test.pass("Successfully added and verified task '" + taskName + "'", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } catch (AssertionError e) {

                test.fail("Failed to verify task '" + taskName + "'. " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                throw e;  
            }
        }
    }


    
    @Test(priority = 6, dataProvider = "taskDataByActionAndTestCaseCompleteFilter", dataProviderClass = TestDataProvider.class)
    public void testFilterMarkTasksAsCompleted(String taskName, String action, String testCase, String newText) throws InterruptedException, IOException {
        if (action.equalsIgnoreCase("COMPLETE")) {
            Reporter.log("Filter check -> Mark To-Do as Completed: " + taskName, true);
            //Thread.sleep(10000);
            
            test = extent.createTest("Filter check ->  Mark To-Do as Completed: " + taskName);
            ToDoPage todoPage = new ToDoPage(driver);

            todoPage.markAsCompleted(taskName);
            String screenshotPath = captureScreenshot("Filtercheck_testMarkAsCompleted_" + taskName);

            try {
                List<String> completedTasks = todoPage.getCompletedTasks();
                Assert.assertTrue(completedTasks.contains(taskName), "Task '" + taskName + "' is not completed!");
                test.pass("Completed To-Do task '" + taskName + "'", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                
            } catch (AssertionError e) {
                test.fail("Failed to complete To-Do task '" + taskName + "'. " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                throw e;
            }
        }
    }

    
}
