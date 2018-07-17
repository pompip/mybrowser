package cn.pompip.browser.common;

public class Cipher {
	
	/**
	 * 解密算法
	 * @param str
	 * @param pos1
	 * @param pos2
	 * @return
	 */
	static public String Decrypt(String str, int pos1, int pos2) {
		int nLength;
		String strReturn = "";
    	if(str==null)
    	{
    		return "";
    	}
    	
		char nTemp;
		char cTemp;
		nLength = str.length();
		for (int i = 0; i <= nLength - 2; i = i + 2) {
			cTemp = str.charAt(i + 1);
			if ((cTemp >= 48) && (cTemp <= 57)) {
				nTemp = (char) (cTemp - 48);
				nTemp = (char) ((nTemp + 10 - pos1) % 10);
				cTemp = (char) (nTemp + 48);
			} else if ((cTemp >= 65) && (cTemp <= 90)) {
				nTemp = (char) (cTemp - 65);
				nTemp = (char) ((nTemp + 26 - pos2) % 26);
				cTemp = (char) (nTemp + 97);
			} else if ((cTemp >= 97) && (cTemp <= 122)) {
				nTemp = (char) (cTemp - 97);
				nTemp = (char) ((nTemp + 26 - pos2) % 26);
				cTemp = (char) (nTemp + 65);
			}
			strReturn = strReturn + cTemp;
		}
		return strReturn;
	}

	/**
	 * 加密算法
	 * @param str
	 * @param pos1
	 * @param pos2
	 * @return
	 */
	static public String Encrypt(String str, int pos1, int pos2) {
		int nLength;
		String strReturn = "";
    	if(str==null)
    	{
    		return "";
    	}
		char nTemp;
		char cTemp;
		nLength = str.length();
		for (int i = 0; i <= nLength - 1; i++) {
			cTemp = str.charAt(i);
			if ((cTemp >= 48) && (cTemp <= 57)) {
				nTemp = (char) (cTemp - 48);
				nTemp = (char) ((nTemp + pos1) % 10);
				cTemp = (char) (nTemp + 48);
			} else if ((cTemp >= 65) && (cTemp <= 90)) {
				nTemp = (char) (cTemp - 65);
				nTemp = (char) ((nTemp + pos2) % 26);
				cTemp = (char) (nTemp + 97);
			} else if ((cTemp >= 97) && (cTemp <= 122)) {
				nTemp = (char) (cTemp - 97);
				nTemp = (char) ((nTemp + pos2) % 26);
				cTemp = (char) (nTemp + 65);
			}
			strReturn = strReturn + (char) ((int) (26 * Math.random()) + 65) + cTemp;
		}
		return strReturn;
	}	
	
	/**
	 * for test
	 * @param args
	 */
//	public static void main(String[] args) 
//	{
//		
//	}
	
}
