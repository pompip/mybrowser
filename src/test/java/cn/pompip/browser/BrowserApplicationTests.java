package cn.pompip.browser;

import cn.pompip.browser.dao.MenuDao;
import cn.pompip.browser.dao.NewDao;
import cn.pompip.browser.dao.NewsContentDao;
import cn.pompip.browser.model.MenuBean;
import cn.pompip.browser.model.NewsBean;
import cn.pompip.browser.model.NewsContentBean;
import cn.pompip.browser.task.GetNewListTask;
import cn.pompip.browser.task.GetVideoListTask;
import cn.pompip.browser.util.HttpUtil;
import cn.pompip.browser.util.PropertiesFileUtil;
import cn.pompip.browser.util.date.DateTimeUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import io.lettuce.core.GeoArgs;
import org.jsoup.helper.DataUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BrowserApplicationTests {
    @Autowired
    private MockMvc mvc;


    @Test
    public void testProp() {
        String value = PropertiesFileUtil.getValue("DEFAULT_PASSWORD");
        System.out.println(value);
    }

    @Test
    public void getMessage() throws Exception {
        String responsesStr = HttpUtil
                .get(PropertiesFileUtil.getValue("news_list_url") + "?type=" + "toutiao" + "&qid="
                        + PropertiesFileUtil.getValue("news_qid"));
        System.out.println(responsesStr);
    }
    @Test
    public void contextLoads() {


    }


    @Test
    public void testSMS() throws ClientException {
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                PropertiesFileUtil.getValue("aliyun_accessKeyId"),
                PropertiesFileUtil.getValue("aliyun_accessKeySecret"));
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
        IAcsClient acsClient = new DefaultAcsClient(profile);

        Random random = new Random();

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        //必填:待发送手机号
        sendSmsRequest.setPhoneNumbers("17091613625");
        //必填:短信签名-可在短信控制台中找到
        sendSmsRequest.setSignName("草莓头条");
        //必填:短信模板-可在短信控制台中找到
        sendSmsRequest.setTemplateCode("SMS_134215060");
        //可选:模板中的变量替换JSON串,如模板内容为"您的验证码为${code}"时,此处的值为
        sendSmsRequest.setTemplateParam("{'code':" + random.nextInt(10000) + "}");
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        sendSmsRequest.setOutId(DateTimeUtil.getCurrentDateTimeStr());
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(sendSmsRequest);
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + sendSmsResponse.getCode());
        System.out.println("Message=" + sendSmsResponse.getMessage());
        System.out.println("RequestId=" + sendSmsResponse.getRequestId());
        System.out.println("BizId=" + sendSmsResponse.getBizId());
    }
    @Autowired
    NewsContentDao newsContentDao;
    @Test
    public  void testInsertContent(){
        NewsContentBean bean = new NewsContentBean();
        bean.setNewsId(11111L);
        bean.setUrl("http://baidu.com");
        bean.setNewsTitle("test");
        bean.setCreateTime(DateTimeUtil.getCurrentDateTimeStr());
        bean.setNewsTime(DateTimeUtil.getCurrentDateTimeStr());
        bean.setNewsContent("hello wold");
        bean.setAuthor("liukechong");
        newsContentDao.save(bean);
    }
    @Autowired
    GetVideoListTask task;
    @Test
    public  void testGetVideo(){
        task.run();
    }


    @Autowired
    GetNewListTask newsTask;
    @Test public void testGetNews(){
        newsTask.generateResult("kexue");
    }

    @Autowired
    MenuDao menuDao;
    @Test public void testMenuDao(){
        List<MenuBean> exitMenuSort = menuDao.findAllByTypeOrderBySort("2");
        System.out.println(exitMenuSort.size());
        exitMenuSort.forEach(menuBean -> System.out.println("menu" + menuBean.getTitle()));
    }

}
