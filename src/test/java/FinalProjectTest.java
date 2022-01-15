import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FinalProjectTest {
     public WebDriver driver;
    @BeforeTest
    @Parameters("browser")
    public void setup(String browser) {
        if(browser.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        else if(browser.equalsIgnoreCase("Edge")){
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }

    }
     @Test
    public void test(){
         driver.navigate().to("http://automationpractice.com/index.php");
         driver.manage().window().maximize();

         JavascriptExecutor js = (JavascriptExecutor) driver;
         WebDriverWait wait = new WebDriverWait(driver,5);
         Actions builder = new Actions(driver);

         TakesScreenshot ts = (TakesScreenshot)driver;


         File file = ts.getScreenshotAs(OutputType.FILE);

         try {

             FileUtils.copyFile(file, new File("./ScreenShot_Folder/Test1_Login.png"));
         } catch (IOException e) {
             e.printStackTrace();
         }


         driver.findElement(By.cssSelector("a[title='Women']")).click();
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"categories_block_left\"]/div/ul/li[1]/span")));
         driver.findElement(By.xpath("//*[@id=\"categories_block_left\"]/div/ul/li[1]/span")).click();
         driver.findElement(By.xpath("//*[@id=\"categories_block_left\"]/div/ul/li[1]/span/following::li[1]")).click();

         WebElement productContainer = driver.findElement(By.cssSelector("div.product-container"));
         js.executeScript("arguments[0].scrollIntoView();",productContainer);
         builder.moveToElement(productContainer).perform();
         WebElement quickView = driver.findElement(By.cssSelector(" a.quick-view"));
         wait.until(ExpectedConditions.visibilityOf(quickView));
         quickView.click();

         wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));
         By thumbnail = By.cssSelector("ul#thumbs_list_frame > li > a > img");
         List<WebElement> thumbnails = driver.findElements(thumbnail);

         for(WebElement image : thumbnails) {
              builder.moveToElement(image).perform();
             System.out.println(image.getAttribute("src"));
         }

         driver.findElement(By.xpath("//p[@id = 'quantity_wanted_p']//a[2]")).click();
         Select size = new Select(driver.findElement(By.cssSelector("select#group_1")));
         size.selectByVisibleText("M");
         driver.findElement(By.cssSelector("button.exclusive")).submit();

         WebElement continueShopping = driver.findElement(By.cssSelector("a[title=\"Continue shopping\"]"));
         js.executeScript("arguments[0].scrollIntoView();",continueShopping);
         continueShopping.click();

         driver.switchTo().defaultContent();
         try{
             driver.findElement(By.cssSelector("a.fancybox-item")).click();
         }
        catch (NoSuchElementException e){
            System.out.println("there was no close button");
        }
         WebElement logo = driver.findElement(By.cssSelector("div#header_logo"));
         js.executeScript("arguments[0].scrollIntoView();",logo);
         logo.click();

         builder.moveToElement(driver.findElement(By.xpath("//*[@id=\"block_top_menu\"]/ul/li[2]/a"))).perform();
         driver.findElement(By.xpath("//li[@class]/ul/li/a[@title = \"Casual Dresses\"]")).click();
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.product-container")));
         js.executeScript("arguments[0].scrollIntoView();",driver.findElement(By.cssSelector("div.product-container")));
         builder.moveToElement(driver.findElement(By.cssSelector("div.product-container"))).perform();
         driver.findElement(By.cssSelector("a[title = \"Add to cart\"]")).click();
         WebElement continueShopping2 = driver.findElement(By.xpath("//span[@title = \"Continue shopping\"]/span"));
         wait.until(ExpectedConditions.visibilityOf(continueShopping2));
         continueShopping2.click();

         js.executeScript("arguments[0].scrollIntoView();",driver.findElement(By.xpath("//a[@title = 'View my shopping cart']")));
         builder.moveToElement(driver.findElement(By.xpath("//a[@title = 'View my shopping cart']"))).perform();
         WebElement cartCheckout = driver.findElement(By.cssSelector("a#button_order_cart"));
         wait.until(ExpectedConditions.visibilityOf(cartCheckout));
         cartCheckout.click();
         By description = By.xpath("//tbody//td[2]");
         List<WebElement> descriptions = driver.findElements(description);
         for(WebElement desc : descriptions) {
             System.out.println(desc);
         }
         driver.findElement(By.xpath("//p/a[@title = 'Proceed to checkout']")).click();
         try {
             driver.findElement(By.cssSelector("input#email_create")).sendKeys("saxeligvari@mail.com");
             driver.findElement(By.cssSelector("button#SubmitCreate")).submit();
             wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#firstname")));
             driver.findElement(By.cssSelector("input#customer_firstname")).sendKeys("saxeli");
             driver.findElement(By.cssSelector("input#customer_lastname")).sendKeys("gvari");
             driver.findElement(By.cssSelector("input#passwd")).sendKeys("paroli123");

             Select days = new Select(driver.findElement(By.cssSelector("select#days")));
             days.selectByIndex(2);
             Select months = new Select(driver.findElement(By.cssSelector("select#months")));
             months.selectByIndex(2);
             Select years = new Select(driver.findElement(By.cssSelector("select#years")));
             years.selectByIndex(2);

             driver.findElement(By.cssSelector("input#company")).sendKeys("beep");
             driver.findElement(By.cssSelector("input#address1")).sendKeys("beep");
             driver.findElement(By.cssSelector("input#city")).sendKeys("beep");
             driver.findElement(By.cssSelector("input#postcode")).sendKeys("12341");

             driver.findElement(By.cssSelector("textarea#other")).sendKeys("sxva");
             driver.findElement(By.cssSelector("input#phone")).sendKeys("1234566789");

             Select state = new Select(driver.findElement(By.cssSelector("select#id_state")));
             state.selectByIndex(4);
             driver.findElement(By.xpath("//button[@name = 'submitAccount']")).submit();
         }
         catch (TimeoutException e){
             driver.findElement(By.cssSelector("input#email")).sendKeys("saxeligvari@mail.com");
             driver.findElement(By.cssSelector("input#passwd")).sendKeys("paroli123");
             driver.findElement(By.xpath("//button[@name = 'SubmitLogin']")).submit();
         }
         js.executeScript("arguments[0].scrollIntoView();",driver.findElement(By.xpath("//button[@name = 'SubmitLogin']")));
         driver.findElement(By.xpath("//button[@name = 'SubmitLogin']")).click();
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@name = 'processAddress']")));
         driver.findElement(By.xpath("//button[@name = 'processAddress']")).submit();

         //driver.findElement(By.xpath("//button[@name = 'processCarrier']")).submit();
        // driver.findElement(By.xpath("//*[@id=\"order\"]/div[2]/div/div/a")).click();

         driver.findElement(By.cssSelector("input#cgv")).click();
         driver.findElement(By.xpath("//button[@name = 'processCarrier']")).click();
         System.out.println(driver.findElement(By.cssSelector("span#total_price")).getText());
         driver.findElement(By.cssSelector("a.cheque")).click();
         js.executeScript("arguments[0].scrollIntoView();",driver.findElement(By.xpath("//span[text()= 'I confirm my order']")));
         driver.findElement(By.xpath("//span[text()= 'I confirm my order']")).click();

         TakesScreenshot ts1 = (TakesScreenshot)driver;
         File file1 = ts1.getScreenshotAs(OutputType.FILE);

         try {
             FileUtils.copyFile(file1, new File("./ScreenShot_Folder/Test2_SearchUser.png"));
         } catch (IOException e) {
             e.printStackTrace();
         }


    }
    @AfterTest
    public void tearDown(){
        driver.quit();
    }

}
