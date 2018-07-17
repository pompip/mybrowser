package cn.pompip.browser.util.security;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 生成32位唯一的ID
 * 
 * @author xiejiong
 * 
 */
public class UUID implements Runnable {
	public static void main(String[] args) {
		ExecutorService pool = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 8; i++) {
			pool.execute(new UUID());
		}
		pool.shutdown();
	}

	/**
	 * 生成36位唯一的UUID
	 * @return DA88C2ED-7500-4841-815B-D2693D7EF321
	 */
	public static String getReqID() {
		return getReqID(false);
	}

	/**
	 * 生成带连接符的36位UUID，或不带连接符的32位UUID
	 * @param b	true不带连接符，false带连接符
	 * @return	DA88C2ED-7500-4841-815B-D2693D7EF321或2CAACBD30DD54283B57405EEC563BE0C
	 */
	public static String getReqID(boolean b) {
		if (b) {
			return java.util.UUID.randomUUID().toString().toUpperCase()
					.replaceAll("-", "");
		} else {
			return java.util.UUID.randomUUID().toString().toUpperCase();
		}
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println(getReqID(true));
		}
	}
}
