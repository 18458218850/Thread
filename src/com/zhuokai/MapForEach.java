package com.zhuokai;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MapForEach {

	public static void main(String[] args) {
		Map<String,String> map = new HashMap<String,String>(); 
		
		map.put("1", "lijh");
		map.put("2", "pech");
		map.put("3", "wuml");
		map.put("4", "wuwx");
		map.put("5", "wuxm");
		
		map.forEach((k,v)->System.out.println("key:value = " + k + ":" + v));
		
		Stream<String> stream= Stream.of("I", "love", "you", "too");
		stream.filter(str -> str.length()==3)
		    .forEach(str -> System.out.println(str));
		
		Map<String,String> m = new HashMap<String,String>();
	}
	
	public Map<Object,Object> getMap(String a,String b){
		if("".equals(a)){
			return Collections.emptyMap();
		}
		return (Map<Object,Object>)new HashMap<Object,Object>().put(a, b);
	}
}
