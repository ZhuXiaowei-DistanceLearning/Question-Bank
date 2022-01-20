package com.zxw.test;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Set;

/**
 * @author zxw
 * @date 2022-01-19 22:38
 */
public class SeleniumDemo {
    public static void testSelenium() throws InterruptedException {
//        System.setProperty("webdriver.chrome.driver", "D:\\code\\IDEA CODE\\Question-Bank\\chromedriver.exe");
//        ChromeDriverService chromeDriverService = new ChromeDriverService();
        ChromeOptions options = new ChromeOptions();
        WebDriver webDriver = new ChromeDriver();
        JavascriptExecutor js;
        try {
            js = (JavascriptExecutor) webDriver;
//            webDriver.get("http://management.s.znseed.top/passport?redirect=%2F");
            webDriver.get("https://videojs.com/");
            WebElement wait;
            while (true) {
                wait = webDriver.findElement(By.xpath("//*[@id=\"vjs_video_3_html5_api\"]"));
                if (wait != null) {
                    break;
                }
                Thread.sleep(1000);
            }
            js.executeScript("var video = document.getElementById('vjs_video_3_html5_api');console.log(video.muted = true);console.log(arguments[0].play());video.play();return video;",wait);
            System.out.println("页面加载完成");
            webDriver.manage().window().maximize();
            js.executeAsyncScript("alert(1)", "");
            // 回退操作
//        webDriver.navigate().back();
            // 刷新
//        webDriver.navigate().refresh();
            // 转发
//        webDriver.navigate().forward();
            // 前进
//        webDriver.navigate().to("");
            WebElement username = webDriver.findElement(By.id("username"));
            // 右键操作
            WebElement password = webDriver.findElement(By.id("password"));
            WebElement submit = webDriver.findElement(By.xpath("//*[@id=\"formLogin\"]/div[3]/div/div/span/button"));
            // 键盘模拟输入
            username.sendKeys("zhaolei");
            password.sendKeys("123456a");
            // 切换frame
//            webDriver.switchTo().frame(0);
            // 接受弹框
//            webDriver.switchTo().alert().accept();
            Actions actions = new Actions(webDriver);
            // 鼠标悬停
//            actions.contextClick(submit).moveToElement(submit);
            // 鼠标拖放
//            actions.contextClick(submit).dragAndDropBy(submit,0,0);
            // 执行action中所有的存储行为
//            actions.contextClick(submit).perform();
            submit.click();
            // 移动屏幕
            Set<Cookie> cookies = webDriver.manage().getCookies();
            cookies.forEach(e -> {
                System.out.println("key:" + e.getName() + " value:" + e.getValue());
            });
            Thread.sleep(5000);
            actions.moveByOffset(500, 500).perform();
            Thread.sleep(1000);
        } finally {
            if (webDriver != null) webDriver.close();
        }
    }

    public static void main(String[] args) throws InterruptedException {
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
