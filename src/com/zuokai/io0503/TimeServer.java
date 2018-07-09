package com.zuokai.io0503;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO服务器
 * 步骤1：打开ServerSocketChannel 通道
 * 步骤2：绑定服务端端口，设置连接为非阻塞连接
 * 步骤3：创建线程，创建多路复用器，启动线程
 * 步骤4：将ServerSocketChannel注册到线程多路复用器上，监听accept事件
 * 步骤5：多路复用器线程的run方法循环获取就绪key
 * 步骤6：多路复用器监听新客户端接入，处理接入请求
 * 步骤7：设置客户端为非阻塞模式
 * 步骤8：将新客户端请求注册到线程的多路复用器上，监听读操作
 * 步骤9：异步读取客户端请求信息到buffer缓冲区
 * 步骤10：处理请求信息
 * 步骤11：返回相应信息
 * @author Administrator
 *
 */
public class TimeServer {

	
	public static void main(String[] args) {
		
		int port = 8080;
		
		MultipexerTimeServer timeServer = new MultipexerTimeServer(port);
		//步骤3：创建线程，创建多路复用器，启动线程
		new Thread(timeServer,"NIO-MultipexerTimeServer-001").start();
	}
}

class MultipexerTimeServer implements Runnable{
	
	private Selector selector;
	
	private ServerSocketChannel serverSocketChannel;
	
	private Boolean stop = false;

	public MultipexerTimeServer(int port){
		try {
			selector = Selector.open();//打开多路复用器
			//步骤1：打开ServerSocketChannel 通道
			serverSocketChannel = ServerSocketChannel.open();
			//步骤2：绑定服务端端口，设置连接为非阻塞连接
			serverSocketChannel.configureBlocking(false);//非阻塞
			serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);//服务器通道绑定端口
			//步骤4：将ServerSocketChannel注册到线程多路复用器上，监听accept事件
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//通道绑定选择器
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	public void stop(){
		this.stop = true;//调用此方法，停止while循环
	}
	
	@Override
	public void run() {
		while(!stop){
			try {
				//步骤5：多路复用器线程的run方法循环获取就绪key
				selector.select(1000);//每隔一秒获取一次就绪状态的channel
				//System.out.println("循环获取通道中...");
				Set<SelectionKey> selectedKeys = selector.selectedKeys();//获取channel的key
				Iterator<SelectionKey> iterator = selectedKeys.iterator();//获取key的迭代器
				SelectionKey key = null;
				while(iterator.hasNext()){
					key = iterator.next();
					iterator.remove();
					try{
						handleInput(key);
					}catch(Exception e){
						if(key != null){
							key.cancel();
							if(key.channel() != null){
								key.channel().close();//关闭通道
							}
						}
					}
					
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//多路复用器关闭后，channel（通道）和pipe（管道）会自动注销
		if(selector != null){
			try {
				selector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void handleInput(SelectionKey key) throws IOException{
		if(key.isValid()){//告知此键是否有效。
			if(key.isAcceptable()){//测试此键的通道是否已准备好接受新的套接字连接。
				//创建新的连接
				ServerSocketChannel ssc	= (ServerSocketChannel)key.channel();//返回创建此键的通道
				//步骤6：多路复用器监听新客户端接入，处理接入请求
				SocketChannel channel = ssc.accept();//接受到此通道套接字的连接
				//步骤7：设置客户端为非阻塞模式
				channel.configureBlocking(false);//设置为非阻塞通道
				//步骤8：将新客户端请求注册到线程的多路复用器上，监听读操作
				channel.register(selector, key.OP_READ);
				System.out.println("zzz");
				if(key.isReadable()){//测试此键的通道是否已准备好进行读取。
					SocketChannel sc =(SocketChannel)key.channel();
					ByteBuffer bbf = ByteBuffer.allocate(1024);//分配一个新的字节缓冲区。
					//步骤9：异步读取客户端请求信息到buffer缓冲区
					int i = sc.read(bbf);
					System.out.println("读取到通道中的信息长度： "+i);
					if(i>0){//步骤10：处理请求信息
						bbf.flip();
						byte[] b = new byte[bbf.remaining()];
						bbf.get(b);
						String body = new String(b,"UTF-8");
						System.out.println(body);
						String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)?new java.util.Date(
								System.currentTimeMillis()).toString():"BAD ORDER";
						//步骤11：返回相应信息		
						doWrite(sc,currentTime);//相应给客户端
					}else if(i<0){
						key.cancel();
						sc.close();
					}
				}
			}
		}
	}

	private void doWrite(SocketChannel sc, String currentTime) throws IOException {
		if(currentTime != null && currentTime.trim().length()>0){
			byte[] b = currentTime.getBytes();
			ByteBuffer buffer = ByteBuffer.allocate(b.length);
			buffer.put(b);
			buffer.flip();
			sc.write(buffer);
		}
	}
	
}