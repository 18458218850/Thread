package com.zuokai.IO0502;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * BIO客户端
 * @author Administrator
 *
 */
public class TimeClient{

	public static void main(String[] args) {
		int port = 8080;
		if(args !=null && args.length != 0){
			port = Integer.parseInt(args[0]);
		}
		Socket s = null;
		PrintWriter out = null;
		BufferedReader in = null;
		
		try {
			s = new Socket("127.0.0.1",port);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(s.getOutputStream(),true);
			//out.println("zzz");//发送请求信息
			out.println("QUERY TIME ORDER");
			//System.out.println("Send order 2 server success");
			String resp;
			do{
				resp = in.readLine();//接受响应信息
			}while(resp ==null);
			System.out.println("resp is "+resp);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				in = null; 
			}
			
			if(out !=null){
				out.close();
				out = null;
			}
			
			if(s !=null){
				try {
					s.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				s = null;
			}
		}
	}
}