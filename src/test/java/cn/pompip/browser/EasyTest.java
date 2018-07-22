package cn.pompip.browser;

import cn.pompip.browser.task.GetVideoListTask;
import cn.pompip.browser.util.InfoMapEncrypt;
import cn.pompip.browser.util.MyBeanUtils;
import cn.pompip.browser.util.HttpClientUtil;
import cn.pompip.browser.util.PropertiesFileUtil;
import cn.pompip.browser.util.security.ThreeDES;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

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
    @Test
    public void testCheckNotNull(){
        Person source = new Person();
        source.age = "11";
        String[] names =   MyBeanUtils.findNotNullPropertyName(source);

      for(String n:names){
          System.out.println(n);
      }
    }
    @Test
    public  void textNull(){
        Object oo =null;

        if (oo==null){

        }
    }
    class Person{
        public String name;
        public String age;

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
    }


    @Test
    public  void testEncode() {
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
    public  void testBase64Encode() {
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
}
