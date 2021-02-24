/******************************************************
 * Concurrent Socket Server 
 * @author Luke Sanchez & Jordan Pacilio 
 * Created: Jun. 20, 2020
 * Last Modified: Jul. 26, 2020
 *****************************************************/

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServerCSS {
	public static void main(String[] args) {
		Scanner userInput = new Scanner(System.in);
		
		System.out.print("Listening Port: ");
		int port = Integer.parseInt(userInput.nextLine());
		userInput.close();
		
		try(ServerSocket serverSocket = new ServerSocket(port)){  //creates a server socket on the selected port
			System.out.println("Server is active on port " + port + "...");
			
			while(true) {
				Socket socket = serverSocket.accept(); //listens and accepts connection
				new ServerThread(socket).start();
			}
		}
				
		catch (IOException ex) {
			System.err.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}

