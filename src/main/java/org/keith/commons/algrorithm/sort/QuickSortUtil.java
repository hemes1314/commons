package org.keith.commons.algrorithm.sort;

import java.util.Arrays;

/**
 * 快速排序是由东尼·霍尔所发展的一种排序算法。
 * 在平均状况下，排序 n 个项目要Ο(n log n)次比较。
 * 在最坏状况下则需要Ο(n2)次比较，但这种状况并不常见。
 * 事实上，快速排序通常明显比其他Ο(n log n) 算法更快，
 * 因为它的内部循环（inner loop）可以在大部分的架构上很有效率地被实现出来。
 * 快速排序使用分治法（Divide and conquer）策略来把一个串行（list）分为两个子串行（sub-lists）。
 * 
 *
 * @date 2016-7-19
 * @author keith
 */
public class QuickSortUtil {

	public static void quiksort(int[] arr) {
		
		int i = 0,j = arr.length-1,c = arr[0];
		int x = arr[j],y = arr[i],t;
		
		while(i < j) {
			
			while(x >= c) {
				x = arr[--j];
			}
			
			while(y <= c) {
				y = arr[++i];
			}
			
			t = arr[i];
			arr[i] = arr[j];
			arr[j] = t;
			
			x = arr[--j];
			y = arr[++i];
			if(i > j) {
				t = arr[j];
				arr[j] = arr[0];
				arr[0] = t;
			}
		}
	}
	
	public static void main(String[] args) {
		
		int[] arr = new int[]{2,4,6,0,7,3,1,4,6,8,9};
		System.out.println(Arrays.toString(arr));
		quiksort(arr);
		System.out.println(Arrays.toString(arr));
	}
}
