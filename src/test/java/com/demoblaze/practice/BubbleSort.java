package com.demoblaze.practice;

import java.util.Arrays;

public class BubbleSort {

	public static void main(String[] args) {
		
		int[] arr = {5, 3, 8, 4, 2};
		int j = 1;
		
		while(j < arr.length) {
			for (int i = 0; i < arr.length - j; i++) {
				if(arr[i] > arr[i + 1]) {
					int temp = arr[i];
					arr[i] = arr[i + 1];
					arr[i + 1] = temp;
				}
			}
			j++;
		}		
		
		System.out.println(Arrays.toString(arr));

	}

}
