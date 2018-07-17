package cn.pompip.browser.util;
/**
 * Bytes工具类
 * @author xiejiong
 *
 */
public final class ByteUtil {
	private ByteUtil() {
	}
	
	/**
	 * byte[]转成int
	 * @param bytes
	 * @return
	 */
	public static int toInt(byte[] bytes) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
		}
		return result;
	}

	/**
	 * byte[]转成short
	 * @param bytes
	 * @return
	 */
	public static short toShort(byte[] bytes) {
		return (short) (((-(short) Byte.MIN_VALUE + (short) bytes[0]) << 8)
				- (short) Byte.MIN_VALUE + (short) bytes[1]);
	}

	/**
	 * int转成byte[]
	 * @param value
	 * @return
	 */
	public static byte[] toBytes(int value) {
		byte[] result = new byte[4];
		for (int i = 3; i >= 0; i--) {
			result[i] = (byte) ((0xFFl & value) + Byte.MIN_VALUE);
			value >>>= 8;
		}
		return result;
	}

	/**
	 * short转成byte[]
	 * @param value
	 * @return
	 */
	public static byte[] toBytes(short value) {
		byte[] result = new byte[2];
		for (int i = 1; i >= 0; i--) {
			result[i] = (byte) ((0xFFl & value) + Byte.MIN_VALUE);
			value >>>= 8;
		}
		return result;
	}
    
    /**
     * 把byte[]数组转换成十六进制字符串表示形式
     * @param tmp    要转换的byte[]
     * @return 十六进制字符串表示形式
     */
    public static String byteToHexString(byte[] tmp) {
        String s;
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'a', 'b', 'c', 'd', 'e', 'f' }; 
        
        char str[] = new char[16 * 2]; 
        int k = 0; 
        for (int i = 0; i < 16; i++) { 
            byte byte0 = tmp[i]; 
            str[k++] = hexDigits[byte0 >>> 4 & 0xf]; 
            str[k++] = hexDigits[byte0 & 0xf]; 
        }
        s = new String(str); 
        return s;
    }
}