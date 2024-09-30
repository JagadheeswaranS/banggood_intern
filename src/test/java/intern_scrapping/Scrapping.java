package intern_scrapping;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class Scrapping {
	static String ParrentWindow;
	static WebDriver d;
	static int k = 1; //for dynamic xpath	
	static int count = 1;
	static int NextPageSelectionCount = 59;
	static int pagenumint = 3;
	static int pagecount =89;
	public static void main(String [] args) {
		try {
		d = new ChromeDriver();		
		d.manage().window().maximize();
		
		d.get("https://www.banggood.in/Wholesale-RC-Car-ca-7008.html?cat_id=7008&page=1&direct=0&rec_uid=2566157916|1726302204&bid=81131&sort=1&sortType=desc");
		d.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		for (int i = 0 ; i <= 411 ; i++ ) {
					
			//Open page concate xpath
			String Openpage = "//ul[@class = 'goodlist']/li[@data-spm-masonry = '" + i +"']["+k+"]"; 
			WebElement OpenPageElement = d.findElement(By.xpath(Openpage));
			Actions act = new Actions(d);
			
			act.scrollToElement(OpenPageElement);
			
			act.keyDown(Keys.CONTROL).click(OpenPageElement).keyUp(Keys.CONTROL).perform();
			
			Set<String> windowsid = d.getWindowHandles();
			
			ParrentWindow = d.getWindowHandle();
			
			for(String handle : windowsid) {
				if(!handle.equals(ParrentWindow)) {
					d.switchTo().window(handle);
					break;
				}
			}			
			//product category
			List<WebElement> elements = d.findElements(By.xpath("//li[@property='itemListElement']"));
			WebElement fourthelement = elements.get(3);
			String category = fourthelement.getText();
			System.out.println("Category :" +category);
			
			//Page Title
			System.out.println("Page Title : " + d.getTitle()); 
			
			//Product Description
			System.out.println("Product Description :" + d.findElement(By.className("product-title-text")).getText());
			
			//Product Price
			System.out.println("Product Price : "+d.findElement(By.className("main-price")).getText()); 
			
			try {
			WebElement ShippingPriceinnerelement = d.findElement(By.className("shipping-price-em")); //US$00.00
			
			String ShippingPrice = ShippingPriceinnerelement.getAttribute("innerHTML");
			
			if(!ShippingPrice.equalsIgnoreCase("US$00.00")) {
				System.out.println("Shipping Price : "+ShippingPrice);
			}
			else{
				System.out.println("Not able to ship");
			}
			}
			catch (Exception excp) {
				System.out.println("May be Free shiping");
			}
			
			//image URL
			try {
			WebElement Imageurl1 = d.findElement(By.xpath("//div[@class='image-small']//li[1]//a//img"));
			WebElement Imageurl2 = d.findElement(By.xpath("//div[@class='image-small']//li[2]//a//img"));
			System.out.println("Image URL 1 : "+ Imageurl1.getAttribute("src") );
			System.out.println("Image URL 2 : "+ Imageurl2.getAttribute("src"));
			}catch(Exception e) {
				System.out.println("Image Not Available");
			}
			
			d.close();
			d.switchTo().window(ParrentWindow);
			d.switchTo().defaultContent();
			
			
			//next page
			if (i == pagecount) {
				
				String pagenum = String.valueOf(pagenumint);
				
				WebElement Pagenumtextbox = d.findElement(By.xpath("//div[@class = 'go']/input"));
				Pagenumtextbox.clear();
				Pagenumtextbox.click();
				Pagenumtextbox.sendKeys(pagenum);
				String pagenumgo = "//div[@class = 'go']/a";
				d.findElement(By.xpath(pagenumgo)).click();
				pagecount = pagecount+60;
				pagenumint++;
				}
			
			if(i==29 && count == 1) {
				
				act.scrollByAmount(0, 4000).perform();
				Thread.sleep(10000);				
				i=28;
				count=2;
				k=2;
			}
			
			if(i==29 && count == 1) {
				k=1;
			}
			
			//from 1st page to 2nd page
			if(i==29 && count == 2) {
				int pagenumint = 2;
				String pagenum = String.valueOf(pagenumint);
				WebElement Pagenumtextbox = d.findElement(By.xpath("//div[@class = 'go']/input"));
				Pagenumtextbox.clear();
				Pagenumtextbox.click();
				Pagenumtextbox.sendKeys(pagenum);
				String pagenumgo = "//div[@class = 'go']/a";
				d.findElement(By.xpath(pagenumgo)).click();	
				k=1;
			}
			//while page loading it shows only 30 products, so after 30 products need to scroll down
			if(i == NextPageSelectionCount) {
				act.scrollByAmount(0, 4000).perform();
				NextPageSelectionCount = NextPageSelectionCount + 60;
			}
			System.out.println("---***---"+i);
		}		
	}catch (Exception e) {
		e.printStackTrace();
	}
		}
}
