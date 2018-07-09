package com.zuokai.thread0424;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/**
 * map遍历
 * @author Administrator
 *
 */
public class MapTest {

	public static void main(String[] args) {
		Map<String,String> map = new HashMap<>();
		map.put("1", "张三");
		map.put("2", "李四");
		map.put("3", "王五");
		map.put("4", "赵六");
		map.put("5", "秦七");
		
		Set<String> keySet = map.keySet();
		for (String string : keySet) {
			System.out.println("key = "+string+" value = "+map.get(string));
		}
	}
}
