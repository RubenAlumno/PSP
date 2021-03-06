package com.paypal.es.cliente;

import java.io.IOException;
import java.net.InetAddress;

public class CajeroClient {
	 static final int MAX_THREADS = 1;

	    public static void main(String[] args)
	            throws IOException, InterruptedException {
	        InetAddress addr
	                = InetAddress.getByName(null);
	        while (true) {
	        	if (ClientThread.getThreadsCount() < MAX_THREADS) {
	        		new ClientThread(addr);
	        		Thread.currentThread().sleep(100);
	            }
	            
	        }
	    }
}