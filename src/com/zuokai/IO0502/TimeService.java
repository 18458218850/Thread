package com.zuokai.IO0502;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO服务端
 * @author Administrator
 *
 */
public class TimeService {

	
	public static void main(String[] args) throws Exception {
		int port = 8080;
		if(args != null && args.length !=0){
			port = Integer.parseInt(args[0]);
		}
		ServerSocket ss = null;
		try{
			
			ss = new ServerSocket(port);
			System.out.println("the time server is start in port:" +port);
			Socket s = null;
			while(true){
				s = ss.accept(); //侦听并接受到此套接字的连接,当有连接时，才执行下一行代码
				new Thread(new TimeServerHandler(s)).start();
			}
		}finally {
			if(ss != null){
				ss.close();
				System.out.println("the time server is close");
				ss=null;
			}
		}
	}

}

class TimeServerHandler implements Runnable{
	
	private Socket s;
	
	public TimeServerHandler(Socket s){
		this.s = s;
	}
	
	@Override
	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;
		System.out.println("zzz");
		try {
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(s.getOutputStream(),true);
			String currentTime = null;
			String body = null;
			while(true){
				body = in.readLine();//读一行
				if(body ==null){//为空就退出循环
					break;
				}
				System.out.println("the time server receive order: " +body);
				currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)?new java.util.Date(
						System.currentTimeMillis()).toString():"BAD ORDER";
				out.println(currentTime);
				out.println(body);
			}
			
		} catch (IOException e) {
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
				this.s = null;
			}
		}
		
	}
	
}
