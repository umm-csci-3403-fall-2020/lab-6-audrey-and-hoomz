package echoserver;

import java.net.*;
import java.io.*;

public class EchoClient {
  public static final int portNumber = 6013;
  private Socket socket;
  private String server;

public EchoClient (String ser){
	server = ser;
}

  public static void main(String[] args) throws InterruptedException {
	String ser;
	
	// Use "127.0.0.1", i.e., localhost, if no server is specified.
	if (args.length == 0) {
	  ser = "127.0.0.1";
	} else {
	  ser = args[0];
	} 
	EchoClient client = new EchoClient(ser);
	client.go(

	);
  }

  public void go() throws InterruptedException{
	ClientThreads client = new ClientThreads();
	client.run();
  }

  public class ClientThreads implements Runnable {

	@Override
	public void run() {
	
		try {
		  // Connect to the server
			socket = new Socket(server, portNumber);
	
		  // Get the input stream so we can read from that socket
		  InputStream input = socket.getInputStream();
		  OutputStream output = socket.getOutputStream();
	
		  // Print all the input we receive from the server
	
		  int byteIn;
	
		  while((byteIn = System.in.read()) != -1){
			output.write(byteIn);
			System.out.write(input.read());
			output.flush();
			System.out.flush();
		  }
	
		  // Close the socket when we're done reading from it
		  socket.close();
	
		// Provide some minimal error handling.
		} catch (ConnectException ce) {
		  System.out.println("We were unable to connect to " + server);
		  System.out.println("You should make sure the server is running.");
		} catch (IOException ioe) {
		  System.out.println("We caught an unexpected exception");
		  System.err.println(ioe);
		}
	}
  }
}