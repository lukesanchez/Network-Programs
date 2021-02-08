/******************************************************
 * Iterative Socket Server Server
 * @author Luke Sanchez & Jordan Pacilio 
 * Created: Jun. 20, 2020
 * Last Modified: Jul. 3, 2020
 *****************************************************/

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServerISS {
	public static void main(String[] args) {
		Scanner userInput = new Scanner(System.in);
		
		System.out.print("Listening Port: ");
		int port = Integer.parseInt(userInput.nextLine());
		
		try(ServerSocket serverSocket = new ServerSocket(port)){  //creates a server socket on the selected port
			System.out.println("Server is active on port " + port + "...");
			
			while(true) {
				int command; //from client

				String result = ""; //from server terminal output
				
				Socket socket = serverSocket.accept(); //listens and accepts connections

				InputStream input = socket.getInputStream(); //creates input and output streams to/from server
				OutputStream output = socket.getOutputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));
				PrintWriter writer = new PrintWriter(output, true); 
				
				System.out.println("New Client Connected!");
				
				command = Integer.parseInt(reader.readLine()); //gets client command from InputStream

				String cmd = getCmd(command); //gets command from method

				Process p = Runtime.getRuntime().exec(cmd); //executes the command
				
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String s = stdInput.readLine();

				while(s != null) {
					if (!result.equals("")) { 
						result += "\n";
					}
					result += s;
					s = stdInput.readLine();
				}
				System.out.println("---" + cmd + "---"); //prints command and result in server console
				System.out.println(result);

				writer.println(result); //send result to client
				
				socket.close();
			}
		}
		catch (IOException ex) {
			System.err.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static String getCmd(int Command){ //simple method that returns the user-selected command. 
		String cmd = "";

		switch (Command) {
			case 1:
				cmd = "date";
				break;
			case 2:
				cmd = "uptime";
				break;
			case 3:
				cmd = "free -h -t";
				break;
			case 4:
				cmd = "netstat -tp";
				break;
			case 5:
				cmd = "users";
				break;
			case 6:
				cmd = "ps -A";
				break;	
			default:
				cmd = "Invalid Command!";
				break;
		}
		return cmd;
	}
}
