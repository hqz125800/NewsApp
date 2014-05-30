package com.example.gridlayouttest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SocketService extends Service{
	Thread mServiceThread;
	
	Socket client;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		mServiceThread = new Thread(new SocketServerThread());
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		
		mServiceThread.start();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public class SocketServerThread extends Thread {
		private static final int PORT = 54321;
		
		private SocketServerThread(){
		}
		
		@Override
		public void run() {
			try {
				ServerSocket server = new ServerSocket(PORT);
				
				while(true){
					System.out.println("begin client connected");
					client = server.accept();
					System.out.println("client connected");
					
					BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
					System.out.println("read from client:");
					
					String textLine = reader.readLine();
					
					MainActivity.database.execSQL(textLine);
					if(textLine.equalsIgnoreCase("EXIT")){
						System.out.println("EXIT invoked, closing client");
						break;
					}

					System.out.println(textLine);
					
					PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())));
					
					writer.println("ECHO from server: " + textLine);
					writer.flush();
					
					writer.close();
					reader.close();
				}							
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println(e);
			}	
		}
	    
	}
}