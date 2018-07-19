package cn.pompip.browser;

import cn.pompip.browser.task.GetNewListTask;
import cn.pompip.browser.task.GetVideoListTask;
import cn.pompip.browser.util.HttpClientUtil;
import cn.pompip.browser.util.PropertiesFileUtil;
import org.junit.Test;

import static cn.pompip.browser.util.NumberUtil.moneyToChinese;


public class EasyTest {
    @Test
    public void testProp() {
        String value = PropertiesFileUtil.getValue("DEFAULT_PASSWORD");
        System.out.println(value);
    }

    @Test
    public void getMessage() throws Exception {
        String responsesStr = HttpClientUtil
                .get(PropertiesFileUtil.getValue("news_list_url") + "?type=" + "toutiao" + "&qid=" + PropertiesFileUtil.getValue("news_qid"));
        System.out.println(responsesStr);
    }

    @Test
    public void testNumberFormat() {
        System.out.println(moneyToChinese(1000005.3));
        System.out.println(moneyToChinese(-1200005.3));
        System.out.println(moneyToChinese(-12000025.333));
        System.out.println(moneyToChinese(567896325.00));
        System.out.println(moneyToChinese(1234567891234.01));
        System.out.println(moneyToChinese(211102002034000000000000.99));
        System.out.println(moneyToChinese(-211102002034000190000101.00));
    }
    @Test
    public void testWriteToDesk() throws Exception {

        HttpClientUtil.writeFileToDisk("http://wx.qlogo.cn/mmopen/1a4mFicfA1HDkGwjFnvVTbDrJyNOgOOaLMLkicavbibk3V9vFDYN8htP0W83r7MD2x00B4uIK8vVJ5yZ9qVp5k8YdUICRtibU30d/0", "d://1.png");

    }
    @Test
    public void getVideo(){
        GetVideoListTask videoListTask = new GetVideoListTask();
        videoListTask.run();
    }
}
