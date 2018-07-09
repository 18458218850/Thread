package com.zuokai.io0503;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 步骤1：打开SocketChannel,绑定客户端地址
 * 步骤2：设置SocketChannel为非阻塞模式
 * 步骤3：异步连接到服务器
 * 步骤4：判断是否连接成功
 * 		 true 注册读状态为到多路复用器 跳到步骤10
 * 		 false 继续异步连接
 * 步骤5：向线程的多路复用器注册 OP_CONNECT 操作位，监听服务端相应
 * 步骤6：创建线程和多路复用器，启动线程
 * 步骤7：多路复用器线程的run方法循环获取就绪key
 * 步骤8：接收connect事件并处理
 * 步骤9：判断是否连接成功，成功就注册读状态到多路复用器
 * 步骤10：注册读状态到多路复用器
 * 步骤11：异步读客户端请求到缓冲区
 * 步骤12：
 * @author Administrator
 *
 */
public class TimeClient {

	public static void main(String[] args) {
		int port = 8080;
		
		new Thread(new TimeClientHandle("127.0.0.1",port)).start();;
	}
}

class TimeClientHandle implements Runnable{
	
	private int port;
	private String ip;
	private Selector selector;
	private volatile boolean stop = false;
	private SocketChannel socketChannel;
	
	public TimeClientHandle(String ip,int port){
		this.port = port;
		this.ip = ip ==null?"127.0.0.1":ip;
		try {
			selector = Selector.open();
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	@Override
	public void run() {
		
		doConnect();//线程尝试连接通道
		
		while(!stop){
			try {
				selector.select(1000);////每隔一秒获取一次就绪状态的channel
				Set<SelectionKey> keys = selector.selectedKeys();//获取channel的key
				Iterator<SelectionKey> iterator = keys.iterator();//获取key的迭代器，方便循环
				SelectionKey key = null;
				while(iterator.hasNext()){
					key = iterator.next();
					iterator.remove();
					try {
						handleInput(key);
					} catch (Exception e) {
						if(key != null){
							key.cancel();
						}
						
						if(key.channel() != null){
							key.channel().close();
						}
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(selector != null){
			try {
				selector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void handleInput(SelectionKey key) throws Exception{
		if(key.isValid()){//判断此键是否有效。
			SocketChannel socketChannel	=(SocketChannel)key.channel();//通过key获取套接字通道
			if(key.isConnectable()){//判断是否连接成功
				if(socketChannel.finishConnect()){//完成套接字通道的连接过程
					doWrite(socketChannel);
				}
			}else{
				System.exit(1);//连接失败，退出进程
			}
			
			if(key.isReadable()){
				ByteBuffer bbf = ByteBuffer.allocate(1024);
				int i = socketChannel.read(bbf);
				if(i>0){
					bbf.flip();
					byte[] b = new byte[bbf.remaining()]; 
					bbf.get(b);
					String body = new String(b,"UTF-8");
					System.out.println(body);
					this.stop = true;//停止循环
				}else if(i<0){
					
				}
			}
		}
	}

	/**
	 * 发送请求，解码应答信息
	 * @param socketChannel2
	 * @throws IOException
	 */
	private void doWrite(SocketChannel socketChannel2) throws IOException {
		byte[] request = "QUERY TIME ORDER".getBytes();//请求信息
		ByteBuffer bbf = ByteBuffer.allocate(request.length);//建立缓冲区
		bbf.put(request);//放入缓冲区
		bbf.flip();//反转缓冲区，将缓存字节数组的指针设置为数组的开始序列即数组下标0。这样就可以从buffer开头，对该buffer进行遍历（读取）了
		socketChannel2.write(bbf);//将字节序列从给定的缓冲区中写入此通道
		if(!bbf.hasRemaining()){
			System.out.println("send order 2 server success!");
		}
	}

	private void doConnect(){
		try {
			if(socketChannel.connect(new InetSocketAddress(ip, port))){//连接成功
				socketChannel.register(selector, SelectionKey.OP_READ);
				doWrite(socketChannel);
			}else{//连接失败
				socketChannel.register(selector, SelectionKey.OP_CONNECT);
			}
		} catch (ClosedChannelException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stop(){
		this.stop = true;
	}
	
}
