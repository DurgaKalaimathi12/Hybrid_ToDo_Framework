package Utility;

import org.testng.annotations.DataProvider;
import java.io.IOException;

public class TestDataProvider {

    @DataProvider(name = "taskDataByActionAndTestCaseAdd")
    public Object[][] taskDataByActionAndTestCaseAdd() throws IOException {
        return ExcelReader.getDataFromExcel("ToDosTasks", "ADD", "testAddToDos");
    }

    @DataProvider(name = "taskDataForMarkCompleted")
    public Object[][] getTaskDataForMarkCompleted() throws IOException {
        return ExcelReader.getDataFromExcel("ToDosTasks", "COMPLETE", "testMarkToDosAsCompleted");
    }

    @DataProvider(name = "taskDataForDelete")
    public Object[][] getTaskDataForDelete() throws IOException {
        return ExcelReader.getDataFromExcel("ToDosTasks", "DELETE", "testDeleteToDos");
    }
    
    @DataProvider(name = "taskDataForEdit")
    public Object[][] getTaskDataForEdit() throws IOException {
        return ExcelReader.getDataFromExcel("ToDosTasks", "EDIT", "testEditToDos");
    }
    
    
    @DataProvider(name = "taskDataByActionAndTestCaseAddFilter")
    public Object[][] taskDataByActionAndTestCaseAddFilter() throws IOException {
        return ExcelReader.getDataFromExcel("ToDosTasks", "ADD", "testFilterToDos");
    }
    
    @DataProvider(name = "taskDataByActionAndTestCaseCompleteFilter")
    public Object[][] taskDataByActionAndTestCaseCompleteFilter() throws IOException {
        return ExcelReader.getDataFromExcel("ToDosTasks", "COMPLETE", "testFilterToDos");
    }

}
