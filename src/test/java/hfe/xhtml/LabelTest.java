package hfe.xhtml;


import org.apache.tomee.embedded.EmbeddedTomEEContainer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import javax.ejb.embeddable.EJBContainer;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;

public class LabelTest {

    private WebDriver driver;

    @Before
    public void setup() {
        driver = new HtmlUnitDriver();

        Map<Object, Object> properties = new HashMap();
        properties.put(EJBContainer.PROVIDER, EmbeddedTomEEContainer.class);
        properties.put(EJBContainer.MODULES, new File[]{new File("src/main/webapp/")});
        properties.put(EJBContainer.APP_NAME, "hfe");
        System.setProperty("tomee.webapp.externalRepositories", "build/classes/main,build/classes/test");

        EmbeddedTomEEContainer.createEJBContainer(properties);
    }

    @After
    public void cleanup() {
        driver.close();
    }

    @Test
    public void requestHtmlPage_ThenBodyContainsPostConstructValue() {
        assertEquals("HELLO", getBodyValue());
    }

    @Test
    public void manipulateInjectedObjectAndRequestHtmlPage_ThenBodyContainsValueOfManipulatedInjectedObject() {
        // how is it possible to manipulate the injected object with value=MY_VALUE?
        assertEquals("MY_VALUE", getBodyValue());
    }

    private String getBodyValue() {
        driver.get("http://localhost:8080/hfe/faces/label.xhtml");
        WebElement body = driver.findElement(By.tagName("body"));
        return body.getText();
    }
}
