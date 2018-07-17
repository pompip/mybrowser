package cn.pompip.browser;

import cn.pompip.browser.task.GetNewListTask;
import cn.pompip.browser.util.PropertiesFileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BrowserApplicationTests {

    @Test
    public void contextLoads() {

        GetNewListTask task = new GetNewListTask();
        task.run();
    }

}
