package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;
import java.io.*;


public class EchoServer {
	public static final int portNumber = 6013;
	private ServerSocket server;
	private String client;
  
  public EchoServer (String c){
	  client = c;
  }
  
	public static void main(String[] args) throws InterruptedException {
		String c;
	
		// Use "127.0.0.1", i.e., localhost, if no server is specified.
		if (args.length == 0) {
		  c = "127.0.0.1";
		} else {
		  c = args[0];
		} 
		EchoServer server = new EchoServer(c);
		server.go();
	}

	public void go() throws InterruptedException{
		ServerThreads server = new ServerThreads();
		server.run();
	  }
	
	  public class ServerThreads implements Runnable {
	
		@Override
		public void run() {
		
			try {
			  // Connect to the server
			  server = new ServerSocket(portNumber);
			  Socket sock = server.accept();

			  // Get the input stream so we can read from that socket
			  InputStream input = sock.getInputStream();
			  OutputStream output = sock.getOutputStream();
		
			  // Print all the input we receive from the server
		
			  while (true) {
				int byteIn;
		
				while((byteIn = input.read()) != -1){
				  output.write(byteIn);
				  output.flush();
				  System.out.flush();
				}
				
				output.flush();
				System.out.flush();
		
				// Close the server socket since we're done.
				server.close();
			  }
		
			// Provide some minimal error handling.
			} catch (ConnectException ce) {
			  System.out.println("We were unable to connect to " + client);
			  System.out.println("You should make sure the server is running.");
			} catch (IOException ioe) {
			  System.out.println("We caught an unexpected exception");
			  System.err.println(ioe);
			}
		}
	  }
  }