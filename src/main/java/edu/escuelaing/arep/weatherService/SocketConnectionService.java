package edu.escuelaing.arep.weatherService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketConnectionService {
	
	private static SocketConnectionService _instance = new SocketConnectionService();
	private static HttpServiceUserSearch jsonWeather  = new HttpServiceUserSearch();
	
	private SocketConnectionService() {
	}

	private static SocketConnectionService getInstance() {
		return _instance;
	}
	public static void main(String[] args) throws IOException {
		   ServerSocket serverSocket = null;
		   try { 
		      serverSocket = new ServerSocket(getPort());
		   } catch (IOException e) {
		      System.err.println("Could not listen on port: 35000.");
		      System.exit(1);
		   }

		   Socket clientSocket = null;
		   try {
		       System.out.println("Listo para recibir ...");
		       clientSocket = serverSocket.accept();
		   } catch (IOException e) {
		       System.err.println("Accept failed.");
		       System.exit(1);
		   }
		   PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		   BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		   String inputLine , outputLine;
		   while ((inputLine = in.readLine()) != null) {
		      System.out.println("Recibí: " + inputLine);
		      if (!in.ready()) {break; }
		   }
		;
		   System.out.println(jsonWeather.getDataService());
		   outputLine = 
		          "<!DOCTYPE html>" + 
		          "<html>" + 
		          "<head>" + 
		          "<meta charset=\"UTF-8\">" + 
		          "<title>WeatherService</title>\n" + 
		          "</head>" + 
		          "<body>" + 
		          "<h1>Weather</h1>" + 
		          "</body>" + 
		          "</html>" + inputLine; 
		    out.println(outputLine);
		    out.close(); 
		    in.close(); 
		    clientSocket.close(); 
		    serverSocket.close(); 
		  }
	
	
	public static int getPort() {
		 if (System.getenv("PORT") != null) {
			 return Integer.parseInt(System.getenv("PORT"));
			 }
			 return 35000;
	}
}
