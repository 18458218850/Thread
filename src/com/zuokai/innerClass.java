package com.zuokai;

/**
 * 内部类
 * @author lijh
 *
 */
public class innerClass extends Scores{

	private Students s;
	
	public innerClass(Students s){
		this.s = s;
	}
	
	public String getName(){
		return s.getName();
	}
	
	public static void main(String[] args) {
		Students s = new S();
		innerClass ic = new innerClass(s);
		System.out.println(ic.getName());
	}
}

abstract class Students{
	public abstract String getName();
}

class Scores{
	public Integer getScore(){
		return 100;
	}
}

class S extends Students{

	@Override
	public String getName() {
		return "李四";
	}
	
}