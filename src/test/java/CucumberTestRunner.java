import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources/features/costCalculations.feature",
        "src/test/resources/features/checkout.feature"})
public class CucumberTestRunner {
}
