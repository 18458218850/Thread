package com.zuokai.thread0424;

import java.util.ArrayList;
import java.util.List;

public class ArrayListTest {
	

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		
		//list的初始大小为10 每次自增 10+（10>>1） 10 15 23 34
		for (int i = 0; i < 20; i++) {
			list.add(i);
		}
		
		int a = -2147483648;
		
		System.out.println(a-1);
	}
	
	

}
