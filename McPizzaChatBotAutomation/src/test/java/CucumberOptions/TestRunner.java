package CucumberOptions;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

// Used this git for reporting part https://github.com/damianszczepanik/maven-cucumber-reporting

@RunWith(Cucumber.class)
@CucumberOptions(
		features="src/test/java/features",
		//plugin = "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
		plugin="json:target/jsonReports/cucumber-report.json",
		glue="stepDefinations",
		publish = true)
		//tags= "@DeletePlace")
public class TestRunner {

}
