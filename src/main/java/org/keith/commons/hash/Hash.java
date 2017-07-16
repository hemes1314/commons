package org.keith.commons.hash;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

public class Hash {

	/**
	 * java获取hash值
	 * 
	 * 
	 * @param h
	 * @return
	 */
	static int hash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
	
	/**
	 * java hash算法
	 * 
	 * 
	 * @param h
	 * @param length
	 * @return
	 */
	static int indexFor(int h, int length) {
        return h & (length-1);
    }
	
	/**
	 * 普通hash算法
	 * 
	 * 
	 * @param h
	 * @param length
	 * @return
	 */
	static int mo(int h, int length) {
        return h % length;
    }
	
	public static void main(String[] args) {
		String[] keys = new String[]{
				"key1","key2","key1",
				"key3","key4","key5"
		};
		// 32位无符号整形
//		Long l = -1L ^ (-1L << 32);
//		System.out.println(l);
		
		for(String key : keys) {
//			System.out.println(key.hashCode());
			int k1Hash = hash(key.hashCode());
			// hash值
			System.out.println(k1Hash);
			// java hash算法
			System.out.println(indexFor(k1Hash, 10));
			// 普通 hash算法
			System.out.println(mo(k1Hash, 10));
		}
	}
}
