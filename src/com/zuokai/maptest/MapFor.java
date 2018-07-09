package com.zuokai.maptest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MapFor {

	public static void main(String[] args) {
		
		/*Map<String,String> map = new HashMap<>();
		map.put("张三", "1");
		map.put("李四", "2");
		map.put("王五", "3");
		map.put("赵六", "4");
		map.put("钱七", "5");
		
		for (String string : map.keySet()) {
			System.out.println(string);
		}
		
		for (String string : map.values()) {
			System.out.println(string);
		}
		
		map.keySet().forEach(key->System.out.printf("map.get("+key+") = "+map.get(key)));*/
		
		
		List<String> list = new ArrayList<>();
		
		list.add("张三");
		list.add("李四");
		list.add("王五");
		list.add("赵六");
		list.add("钱七");
		Stream<String> stream = list.stream();
		System.out.println(stream);
		list.forEach(action->System.out.println(action));
	}
}
