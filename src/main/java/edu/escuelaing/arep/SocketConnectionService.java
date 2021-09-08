package edu.escuelaing.arep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SocketConnectionService {
	
	private static SocketConnectionService _instance = new SocketConnectionService();
	private static HttpServiceUserSearch jsonWeather  = new HttpServiceUserSearch();
	
	private SocketConnectionService() {
	}

	private static SocketConnectionService getInstance() {
		return _instance;
	}
	public static void main(String[] args) throws IOException {
		SocketConnectionService.getInstance().startServer(args);
	}
	
	public void startServer(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(getPort());
		} catch (IOException e) {
			System.err.println("Could not listen on port: 35000.");
			System.exit(1);
		}
		Socket clientSocket = null;
		boolean running=true;
		while (running) {
			try {
				System.out.println("Listo para recibir ...");
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
			processRequest(clientSocket);
		}
		serverSocket.close();
	}
	
	public void processRequest(Socket clientSocket) throws IOException {
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		String inputLine;
		String method="";
		String path="";
		String version="";
		List<String> headers = new ArrayList<String>();
		while ((inputLine = in.readLine()) != null) {
			if(method.isEmpty()) {
				String[] requestStrings = inputLine.split(" ");
				method = requestStrings[0];
				path = requestStrings[1];
				version = requestStrings[2];
				System.out.println("Request: " + method + "" + path + "" + version);
			}else {
				System.out.println("Header: " + inputLine);
				headers.add(inputLine);
			}
			
			if (!in.ready()) {
				break;
			}
		} 
		String responseMsg = "HTTP/1.1 200 OK\r\n" 
				+"Content-Type: text/html\r\n" 
				+ "\r\n" 
				+ "<!DOCTYPE html>" 
				+ "<html>"
				+ "<head>"
				+ "<meta charset=\"UTF-8\">"
				+ "<title>WeatherService</title>\n"
				+ "</head>"
				+ "<body>"
				+ jsonWeather.getDataService()
				+ "</body>" 
				+ "</html>";
		out.println(responseMsg);
		out.close();
		in.close();
		clientSocket.close();
	}

	
	public static int getPort() {
		 if (System.getenv("PORT") != null) {
			 return Integer.parseInt(System.getenv("PORT"));
			 }
			 return 35000;
	}
}
