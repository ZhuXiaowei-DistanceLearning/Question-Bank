package com.zxw.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.File;

/**
 * @author zxw
 * @date 2022-01-19 22:38
 */
public class SeleniumDemo {
    public static void testSelenium() {
        File file = new File("chromedriver");
        System.out.println(file.getAbsolutePath());
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\zxw\\Desktop\\driver\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://huaban.com/");
        WebElement webElement = webDriver.findElement(By.xpath("/html"));
        System.out.println(webElement.getAttribute("outerHTML"));
        webDriver.close();
    }

    public static void main(String[] args) {
        testSelenium();
//        test2();
    }

    private static void test2() {
        Spider.create(new HuabanProcessor()).thread(5)
//                .scheduler(new RedisScheduler("localhost"))
                .addUrl("http://huaban.com/")
                .pipeline(new FilePipeline("/data/webmagic/test/"))
                .setDownloader(new SeleniumDownloader("C:\\Users\\zxw\\Desktop/chromedriver.exe"))
                .run();
    }

    public static class HuabanProcessor implements PageProcessor {

        private Site site;

        @Override
        public void process(Page page) {
            page.addTargetRequests(page.getHtml().links().regex("http://huaban\\.com/.*").all());
            if (page.getUrl().toString().contains("pins")) {
                page.putField("img", page.getHtml().xpath("//div[@id='pin_img']/img/@src").toString());
            } else {
                page.getResultItems().setSkip(true);
            }
        }

        @Override
        public Site getSite() {
            if (site == null) {
                site = Site.me().setDomain("huaban.com").setSleepTime(1000);
            }
            return site;
        }
    }

}
