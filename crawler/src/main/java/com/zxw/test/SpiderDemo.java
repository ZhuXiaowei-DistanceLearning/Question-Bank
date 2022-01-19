package com.zxw.test;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * @author zxw
 * @date 2022-01-19 20:35
 */
@Slf4j
public class SpiderDemo {
    public static void main(String[] args) {
        Spider.create(new TestProcessor())
                .thread(5)
                .addUrl("https://www.baidu.com/")
                .addPipeline(new ConsolePipeline()).run();
    }

    public static class TestProcessor implements PageProcessor {

        private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

        @Override
        public void process(Page page) {
            Selectable detail = page.getHtml();
            log.info("{}", detail.get());
            Selectable links = detail.xpath("/html/body/div[4]/div/div[3]/ul").links();
            List<String> all = links.all();
            page.addTargetRequests(all);
        }

        @Override
        public Site getSite() {
            return site;
        }
    }
}
