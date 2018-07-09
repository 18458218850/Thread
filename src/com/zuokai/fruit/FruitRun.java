package com.zuokai.fruit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 输出结果
 */
public class FruitRun {
    public static void main(String[] args) throws NoSuchFieldException, SecurityException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        FruitInfoUtil.getFruitInfo(Apple.class);
        System.out.println(((FruitName)Apple.class.getDeclaredFields()[0].getAnnotation(FruitName.class)).value());
        
        FruitColor color = Apple.class.getDeclaredFields()[1].getAnnotation(FruitColor.class);
    	System.out.println(color.fruitColor());
    	
    	FruitColor color2 = Apple.class.getDeclaredField("appleColor").getAnnotation(FruitColor.class);
    	System.out.println(color2);
    	
    	Field field = Apple.class.getDeclaredField("appleColor");
    	field.setAccessible(true);
    	System.out.println(field);
    	   	
    	Method declaredMethod = Apple.class.getDeclaredMethod("setAppleColor",String.class);
    	declaredMethod.setAccessible(true);
    	Apple apple = new Apple();
    	String [] s = {"BLACK"};
    	declaredMethod.invoke(apple, s);
    	System.out.println(apple.getAppleColor());
    	
    }
}