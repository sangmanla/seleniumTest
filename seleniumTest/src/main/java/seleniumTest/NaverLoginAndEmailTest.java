package seleniumTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NaverLoginAndEmailTest {
    public static String naverId = "myId";
    public static String naverPw = "myPw";
    public static String loginUrl = "https://nid.naver.com/nidlogin.login?mode=form&url=https%3A%2F%2Fwww.naver.com";
    
    public static void main(String[] args) {
	NaverLoginAndEmailTest mine = new NaverLoginAndEmailTest();
	mine.login();
	
	String btnClass = ".btn_upload";
	mine.waitForThePage(By.cssSelector(btnClass));
	
	mine.submitSaveLoginInfo(btnClass);
	mine.waitForThePage(By.className("ca_item"));
	
	mine.goToPageAndWriteEmail();
	
    }
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    public NaverLoginAndEmailTest(){
	System.setProperty("webdriver.chrome.driver","C:/Program Files (x86)/Google/Chrome/Application/chromedriver.exe");
	driver = new ChromeDriver();
	wait = new WebDriverWait(driver, 10);
    }

    private void login() {
        driver.get(loginUrl);
        WebElement id = driver.findElement(By.id("id"));
        WebElement pass = driver.findElement(By.id("pw"));
        WebElement button = driver.findElement(By.className("btn_global"));         

        id.sendKeys(naverId);
        pass.sendKeys(naverPw);
        button.submit();
    }
    
    private void goToPageAndWriteEmail() {
	driver.get("https://mail.naver.com");
	driver.findElement(By.xpath("//*[@id='nav_snb']/div[1]/a[2]/strong")).click();
	
	waitForThePage(By.xpath("//*[@id='subject']"));
	
	try{
	    WebElement subject = driver.findElement(By.xpath("//*[@id='subject']"));
	    subject.sendKeys("test email");
	    
	    driver.switchTo().frame("se2_iframe");
	    
	    driver.findElement(By.className("se2_inputarea")).sendKeys("my content");
	    
	    driver.switchTo().parentFrame();
	    driver.findElement(By.id("sendBtn")).click();
	}catch(Exception e){
	    e.printStackTrace();
	}
    }
    
    private void submitSaveLoginInfo(String btnClass) {
	WebElement button = driver.findElement(By.xpath("//*[@id='frmNIDLogin']/fieldset/span[1]/a"));
	button.click();
	WebElement button2 = driver.findElement(By.className("btn_maintain"));
	button2.click();
    }
    
    private void waitForThePage(By by){
	WebElement el = this.wait.until(ExpectedConditions.presenceOfElementLocated(by));
	this.wait.until(ExpectedConditions.visibilityOf(el));
    }
}