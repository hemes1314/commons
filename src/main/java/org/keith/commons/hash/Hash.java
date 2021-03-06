package org.keith.commons.hash;

public class Hash {

	/**
	 * java获取hash值
	 * 这个方法的主要作用是防止质量较差的哈希函数带来过多的冲突（碰撞）问题。Java中int值占4个字节，即32位。根据这32位值进行移位、异或运算得到一个值。
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
	 * indexFor返回hash值和table数组长度减1的与运算结果。为什么使用的是length-1？应为这样可以保证结果的最大值是length-1，不会产生数组越界问题。
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
	 * 由于使用了除法所以性能比较低
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
