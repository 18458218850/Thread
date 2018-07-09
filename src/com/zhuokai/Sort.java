package com.zhuokai;

public class Sort {

	private static String string;
	private static String name;

	public static void main(String[] args) {
		int [] arr = {1,4,5,2,3,6,7,8,9};
		
		for(int i = 0; i<arr.length-1;i++){
			int kayValue=arr[i];
			for(int j = i+1; j<arr.length;j++){
				if(arr[j]<arr[i]){
					int temp = arr[j];
					arr[i] = temp;
					arr[j] = kayValue;
				}
			}
		}
		
		for(int i = 0;i<arr.length;i++){
			System.out.println(arr[i]);
		}
		
		//name = string.getClass().getName()+ "@" + Integer.toHexString(string.hashCode());
		//System.out.println(name);
	}
}
