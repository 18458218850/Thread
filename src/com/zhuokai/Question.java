package com.zhuokai;

public class Question {

	public static void main(String[] args) throws Exception {
        Base base = new Base();
        System.out.println(base.getClass());
        System.out.println(Base.class.getConstructor(null));
    }
}

class Base {
    static {
        System.out.println(6666666);
    }
    
    private String a;
    private int age;
    
    public void func() {
        System.out.println("func.........");
    }
    
    public Base(){
    	
    }
    
    public Base(int age){
    	this.age=age;
    }
    
    public Base(String a){
    	this.a=a;
    }
    
    public Base(String a,int age){
    	this.a=a;
    	this.age=age;
    }
}

