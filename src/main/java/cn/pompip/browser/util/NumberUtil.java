package cn.pompip.browser.util;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * 数字工具类
 * 
 * @author xiejiong
 * 
 */
public final class NumberUtil {
	private NumberUtil() {
	}

	/**
	 * 金额数字 double amount = 75543.2195691;
	 * 
	 * @return 75,543.22
	 */
	public static String getAmountString(double d) {
		NumberFormat format = NumberFormat.getInstance(Locale.CHINA);

		format.setMaximumFractionDigits(2);
		format.setMinimumFractionDigits(2);

		return format.format(d);
	}

	/**
	 * 是否能转换成数字
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isNumber(String number) {
		try {
			Double.parseDouble(number);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 转换成大写金额(最大26位有效数字，小数点前24位，小数点后2位)
	 * 当数值过大时应注意精度
	 * 
	 * @param amount = 81211.15
	 * @return 捌万壹仟贰壹拾圆壹角伍分
	 */
	public static String moneyToChinese(double amount) {
		String[] yuan = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖",
				"拾", "佰", "仟", "万", "亿", "圆", "角", "分", "负" };

		NumberFormat format = NumberFormat.getInstance(Locale.CHINA);
		format.setMaximumFractionDigits(2);
		format.setMinimumFractionDigits(2);
		format.setGroupingUsed(false);

		String money = format.format(amount);
		
		// 记录符号
		String flag = null;
		if (money.indexOf('-') == 0) {
			flag = yuan[18];
			money = money.substring(1);
		}
		
		// 最大小数点前24位
		if(money.length() > 27){
			return "超出有效范围";
		}

		StringBuilder strb = new StringBuilder();

		char[] chars = money.toCharArray();
		int len = chars.length;
		for (int i = len - 1; i >= 0; i--) {
			String temp = null;
			switch (chars[i]) {
			case '0':
				temp = yuan[0];
				break;
			case '1':
				temp = yuan[1];
				break;
			case '2':
				temp = yuan[2];
				break;
			case '3':
				temp = yuan[3];
				break;
			case '4':
				temp = yuan[4];
				break;
			case '5':
				temp = yuan[5];
				break;
			case '6':
				temp = yuan[6];
				break;
			case '7':
				temp = yuan[7];
				break;
			case '8':
				temp = yuan[8];
				break;
			case '9':
				temp = yuan[9];
				break;
			case '.':
				temp = yuan[15];
				break;
			}

			switch (len - 1 - i) {
			case 0:
				temp += yuan[17];
				break;
			case 1:
				temp += yuan[16];
				break;
			case 4:
			case 8:
			case 12:
			case 16:
			case 20:
			case 24:
				temp += yuan[10];
				break;
			case 5:
			case 9:
			case 13:
			case 17:
			case 21:
			case 25:
				temp += yuan[11];
				break;
			case 6:
			case 10:
			case 14:
			case 18:
			case 22:
			case 26:
				temp += yuan[12];
				break;
			case 7:
			case 15:
			case 23:
				temp += yuan[13];
				break;
			case 11:
			case 19:
				temp += yuan[14];
				break;
			}

			strb.insert(0, temp);
		}

		// 插入符号位
		if (null != flag) {
			strb.insert(0, flag);
		}

		String result = strb.toString();
		result = result.replaceAll("零拾", "零");
		result = result.replaceAll("零佰", "零");
		result = result.replaceAll("零仟", "零");
		result = result.replaceAll("零零零", "零");
		result = result.replaceAll("零零", "零");
		result = result.replaceAll("零角零分", "整");
		result = result.replaceAll("零分", "整");
		result = result.replaceAll("零角", "零");
		result = result.replaceAll("零亿零万零圆", "亿圆");
		result = result.replaceAll("亿零万零圆", "亿圆");
		result = result.replaceAll("零亿零万", "亿");
		result = result.replaceAll("零万零圆", "万圆");
		result = result.replaceAll("零亿", "亿");
		result = result.replaceAll("零万", "万");
		result = result.replaceAll("零圆", "圆");
		result = result.replaceAll("零零", "零");

		return result;
	}


}