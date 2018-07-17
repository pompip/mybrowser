package cn.pompip.browser.util.sms;

import cn.pompip.browser.util.PropertiesFileUtil;
import cn.pompip.browser.util.date.DateTimeUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Random;

/***
 * 
 * @author xiejiong
 *
 */
public class SmsUtil {
	
	private static Log log = LogFactory.getLog(SmsUtil.class);
	
	//产品名称:云通信短信API产品,开发者无需替换
    private static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    private static final String domain = "dysmsapi.aliyuncs.com";
    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    
    public static SendSmsResponse sendAliSms(String phoneNum,int code) throws ClientException
    {
    	IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", PropertiesFileUtil.getValue("aliyun_accessKeyId"), PropertiesFileUtil.getValue("aliyun_accessKeySecret"));
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		//组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest sendSmsRequest = new SendSmsRequest();
		//必填:待发送手机号
		sendSmsRequest.setPhoneNumbers(phoneNum);
		//必填:短信签名-可在短信控制台中找到
		sendSmsRequest.setSignName("多采科技浏览器");
		//必填:短信模板-可在短信控制台中找到
		sendSmsRequest.setTemplateCode("SMS_134215060");
		//可选:模板中的变量替换JSON串,如模板内容为"您的验证码为${code}"时,此处的值为
		sendSmsRequest.setTemplateParam("{'code':"+code+"}");
		//选填-上行短信扩展码(无特殊需求用户请忽略此字段)
		//request.setSmsUpExtendCode("90997");
		//可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		sendSmsRequest.setOutId(DateTimeUtil.getCurrentDateTimeStr());
		//hint 此处可能会抛出异常，注意catch
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(sendSmsRequest);
		log.info("短信接口返回的数据----------------");
		log.info("Code=" + sendSmsResponse.getCode());
		log.info("Message=" + sendSmsResponse.getMessage());
		log.info("RequestId=" + sendSmsResponse.getRequestId());
		log.info("BizId=" + sendSmsResponse.getBizId());
		return sendSmsResponse;
    }
    
	
	public static void main(String[] args) throws ClientException {
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", PropertiesFileUtil.getValue("aliyun_accessKeyId"), PropertiesFileUtil.getValue("aliyun_accessKeySecret"));
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		
		Random random = new Random();

		//组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest sendSmsRequest = new SendSmsRequest();
		//必填:待发送手机号
		sendSmsRequest.setPhoneNumbers("15072954846");
		//必填:短信签名-可在短信控制台中找到
		sendSmsRequest.setSignName("草莓头条");
		//必填:短信模板-可在短信控制台中找到
		sendSmsRequest.setTemplateCode("SMS_134215060");
		//可选:模板中的变量替换JSON串,如模板内容为"您的验证码为${code}"时,此处的值为
		sendSmsRequest.setTemplateParam("{'code':"+random.nextInt(10000)+"}");
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
}
