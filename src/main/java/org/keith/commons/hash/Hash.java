package org.keith.commons.hash;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

public class Hash {

	static int hash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
	
	static int indexFor(int h, int length) {
        return h & (length-1);
    }
	
	static int mo(int h, int length) {
        return h % length;
    }
	
	public static void main(String[] args) {
		String[] keys = new String[]{
				"key1","key2","key1",
				"key3","key4","key5"
		};
		for(String key : keys) {
			System.out.println(key.hashCode());
			int k1Hash = hash(key.hashCode());
			System.out.println(k1Hash);
			System.out.println(indexFor(k1Hash, 10));
			System.out.println(mo(k1Hash, 10));
		}
	}
	
	
}
