package com.zuokai.zhujie;

import java.lang.annotation.Annotation;

@CustomAnnotation
public class TestAnnotation {
	@CustomAnnotation
	public void run(){
		
	}
	
	public static void main(String[] args) {
		Annotation[] a = TestAnnotation.class.getAnnotations();
		for (Annotation annotation : a) {
			CustomAnnotation c = (CustomAnnotation)annotation;
			System.out.println(c.userName);
		}
	}
}
