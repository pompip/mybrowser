package cn.pompip.browser;

import cn.pompip.browser.task.GetVideoListTask;
import cn.pompip.browser.util.HttpUtil;
import cn.pompip.browser.util.InfoMapEncrypt;
import cn.pompip.browser.util.MyBeanUtils;
import cn.pompip.browser.util.PropertiesFileUtil;
import cn.pompip.browser.util.security.ThreeDES;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static cn.pompip.browser.util.NumberUtil.moneyToChinese;


public class EasyTest {


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

        HttpUtil.writeFileToDisk("http://wx.qlogo.cn/mmopen/1a4mFicfA1HDkGwjFnvVTbDrJyNOgOOaLMLkicavbibk3V9vFDYN8htP0W83r7MD2x00B4uIK8vVJ5yZ9qVp5k8YdUICRtibU30d/0", "d://1.png");

    }

    @Test
    public void getVideo() {
        GetVideoListTask videoListTask = new GetVideoListTask();
        videoListTask.run();
    }

    @Test
    public void testCheckNotNull() {
        Person source = new Person();
        source.age = "11";

        Person target = new Person();
//        target.age ="12";
        target.name = "liu";
        try {
            MyBeanUtils.merge(source, target);
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println(source);
        System.out.println(target);
    }


    public class Person {
        private String name;
        private String age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age='" + age + '\'' +
                    '}';
        }
    }


    @Test
    public void testEncode() {
        ThreeDES des = new ThreeDES(); // 实例化一个对像
        des.getKey("my1"); // 生成密匙

        String strEnc = null;// 加密字符串,返回String的密文
        try {
            strEnc = des.getEncString("userName=xiejiong&password=123456");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(strEnc);
//
        ThreeDES des1 = new ThreeDES(); // 实例化一个对像
        des1.getKey("my1"); // 生成密匙
        String strDes = null;// 把String 类型的密文解密
        try {
            strDes = des1.getDesString(strEnc);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(strDes);
    }

    @Test
    public void testBase64Encode() {
        String temp = "ABC2Eefgx34";
        System.out.println("加密前:" + temp);
        String enTemp = InfoMapEncrypt.encrypt(temp);
        System.out.println("加密后:" + enTemp);
        try {
            String deTemp = InfoMapEncrypt.decrypt("+Zs+zn6CZhCxQkYGqsCOg4eu8suDLmyQnDDLioZjq5s=");
            System.out.println("对加密结果解密:" + deTemp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        enTemp = enTemp.replace("4", "3");
        try {
            InfoMapEncrypt.decrypt(enTemp);
        } catch (Exception e) {
            System.out.println("如果密文存在问题,则有异常:" + e.getMessage());
        }
    }

    @Test
    public void testGetVideo() throws Exception {
        Map<String, Object> postData = new HashMap<String, Object>();
        postData.put("category", "subv_funny");
        postData.put("refer", "1");
        postData.put("count", "20");
        postData.put("min_behot_time", System.currentTimeMillis());
        postData.put("last_refresh_sub_entrance_interval", System.currentTimeMillis());
        postData.put("loc_mode", "7");
        postData.put("loc_time", System.currentTimeMillis());
        postData.put("latitude", 11);
        postData.put("longitude", 100);
        postData.put("city", "北京");
        postData.put("tt_from", "pull");
        postData.put("cp", "50ac6a6a0a581q1");
        postData.put("strict", "1");
        postData.put("iid", "24142389236");
        postData.put("device_id", "869271023736728");
        postData.put("ac", "unknown");
        postData.put("channel", "update");
        postData.put("aid", "1183");
        postData.put("app_name", "toutiaoribao_news");
        postData.put("version_code", "600");
        postData.put("version_name", "6.0.0");
        postData.put("device_platform", "android");
        postData.put("ab_client", "a1%2Cc4%2Ce1%2Cf2%2Cg2%2Cf7");
        postData.put("ab_group", "z1");
        postData.put("ab_feature", "z1");
        postData.put("abflag", "3");
        postData.put("ssmix", "a");
        postData.put("device_type", "Redmi Note 3");
        postData.put("device_brand", "Xiaomi");
        postData.put("language", "zh");
        postData.put("os_api", "25");
        postData.put("os_version", "7.1.1");
        postData.put("uuid", "869271023736728");
        postData.put("openudid", "5eceab483511e317");
        postData.put("manifest_version_code", "600");
        postData.put("resolution", "1080*1920");
        postData.put("dpi", "480");
        postData.put("update_version_code", "6002");
        postData.put("_rticket", System.nanoTime());
        String url = PropertiesFileUtil.getValue("news_video_url");


        String data = HttpUtil.get(url, postData);
        System.out.println(data);
    }

}
