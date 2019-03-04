package com.park.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Test {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		// 1.建立TCP连接
		 String ip="192.168.72.1"; //服务器端ip地址
		 // String ip = "120.25.234.195";
		int port = 9090; // 端口号
		Socket sck = new Socket(ip, port);
		// 2.传输内容
		String content = "{\"parkId\":\"108\",\"cardNumber\":\"川A00011\"}\r\n";
		byte[] bstream = content.getBytes("GBK"); // 转化为字节流
		OutputStream os = sck.getOutputStream(); // 输出流
		os.write(bstream);
		os.flush();
		InputStream is = sck.getInputStream();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"GBK"));
		
		InputStreamReader bReader=new InputStreamReader(is, "GBK");
//		char[] read=new char[br.read()];
		String info = null;
//		br.read(read);
	//	bReader.read(cbuf)
		while ((info = br.readLine()) != null) {
			
			System.out.println("我是客户端，服务器说：" + info);
			break;
		}
		// 3.关闭连接
		os.close();
		br.close();
		sck.close();

	}
	public String ddd() {
	//	float a=1.2;
		return null;
	}
	public void main333(String[] args) {
		try {
			// 1.创建客户端Socket，指定服务器地址和端口
			Socket socket = new Socket("120.25.234.195", 9090);
			// Socket socket=new Socket("192.168.72.1", 8888);

			// socket.wait(5000);

			// 2.获取输出流，向服务器端发送信息
			OutputStream os = socket.getOutputStream();// 字节输出流
			PrintWriter pw = new PrintWriter(os);// 将输出流包装为打印流
			// OutputStream os=sck.getOutputStream();
			DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
			String content = "{\\\"parkId\\\":\\\"108\\\",\\\"cardNumber\\\":\\\"川A00011\\\"}\\r\\n";
			byte[] bstream = content.getBytes("GBK");
			os.write(bstream);
			outToServer.write(bstream);
			// pw.write("{\"parkId\":\"108\",\"cardNumber\":\"川A00012\"}");
			os.flush();
			outToServer.flush();

			pw.write(content);
			pw.flush();

			// socket.shutdownOutput();//关闭输出流
			// 3.获取输入流，并读取服务器端的响应信息
			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String info = null;
			while ((info = br.readLine()) != null) {
				System.out.println("我是客户端，服务器说：" + info);
				break;
			}
			// 4.关闭资源
			br.close();
			is.close();
			pw.close();
			os.close();
			outToServer.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
