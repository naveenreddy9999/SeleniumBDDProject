package stepDef.utilities;


import org.openqa.selenium.*;
import com.functions.util.ConfigReader;
import io.cucumber.java.*;

public class ApplicationHooks {

    public WebDriver driver;

    private ConfigReader configReader = ConfigReader.getInstance();
    private testCount count = testCount.getInstance();

    @Before(order = 0)
    public void beforeStart(Scenario scenario) {
        System.out.println("Scenario Name : " + scenario.getName());
        configReader.setScenarioContext("Scenario", scenario);
    }


    @After(order = 0)
    public void tearDown(Scenario scenario) {


        if (scenario.isFailed()) {
            scenario.log("scenario status : " + scenario.getStatus());
            String screenshotName = scenario.getName().replaceAll(" ", "_");
            byte[] sourcePath = ((TakesScreenshot) configReader.getDriver("driver")).getScreenshotAs(OutputType.BYTES);
            scenario.attach(sourcePath, "image/png", screenshotName);
            configReader.getDriver("driver").quit();

        } else {
            scenario.log("scenario status : " + scenario.getStatus());
            configReader.getDriver("driver").quit();
        }

        try {
            count.counter(scenario);
        } catch (Throwable e) {
            System.out.println("count is not working");
        }


    }
//    @After(order = 1)
//    public void tearDown(){
//        configReader.getDriver("driver").quit();
//    }

}
