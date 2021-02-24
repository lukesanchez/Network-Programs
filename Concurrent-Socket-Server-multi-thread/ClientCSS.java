/******************************************************
 * Concurrent Socket Server Client
 * @author Luke Sanchez & Jordan Pacilio 
 * Created: Jun. 20, 2020
 * Last Modified: Jul. 26, 2020
 *****************************************************/

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Thread;

class Client extends Thread {
	String host;
	int port;
	int command;
	long start = 0;
	long end = 0;
	
	Client(String name, String hostName, int portNum, int c, long time){ //client constructor
		super(name);
		host = hostName;
		port = portNum;
		command = c;
		start = time; 
		this.start();
	}
	
	public void run() {
		try (Socket socket = new Socket(host, port)) { //creates a client socket for the given host & port

			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			OutputStream output = socket.getOutputStream(); //creates input and output streams to/from server
			
			PrintWriter writer = new PrintWriter(output, true);
			writer.println(command); //encodes command and writes it to the server
			
			String s = reader.readLine();
			String result = "";
			while (s != null) { 
				result = result + "\n" + s; 
				s = reader.readLine(); //read server results 
			}
				System.out.println(this.getName() + " result: " + result);
				end = System.nanoTime(); //marks client turn-around time
		}
		catch(UnknownHostException ex) {
			System.err.println("No Server Found: " + ex.getMessage());
		}
		catch(IOException ex) {
			System.err.println("IOException in thread " + this.getName() + ": " + ex.getMessage());
		}
	}

		public long getRunTime() { //Calc runtime from thread start to results printed. 
		if(end != 0)
			return end - start;
		else 
			return -1;
	}
}

public class ClientCSS {	
	public static void main(String[] args) {
		Scanner userInput = new Scanner(System.in);
		
		System.out.print("Enter HostName: "); //prompt user for Host
		String hostName = userInput.nextLine();
		
		System.out.print("Enter Port: ");  //prompt user for port
		int port = Integer.parseInt(userInput.nextLine());
		
		int command = -1;
		
		while (command != 0) {
			System.out.println("\nPossible Commands: (1)date, (2)uptime, (3)memory, (4)netstat, (5)users, (6)ps, (0)exit");
			
			System.out.print("Enter Command(0-6): "); //prompt user for Command
			command = Integer.parseInt(userInput.nextLine()); 

			if(command > 0 && command <= 6) { 
				System.out.print("Number of Clients: "); //prompt user for num of clients
				int clients = Integer.parseInt(userInput.nextLine());

				if (clients > 0 && clients <=100){ //verifies num of clients is in range
					ArrayList<Client> client = new ArrayList<Client>();
					long runtime = 0; 
					
					for(int i = 0; i < clients; i++) { //creates client instances using the Client constructor
						client.add(new Client("Client " + (i + 1), hostName, port, command, System.nanoTime()));
					}
					
					for(int i = 0; i < client.size(); i++) {
						while(client.get(i).isAlive()) {} 
						long duration = client.get(i).getRunTime(); //gets runtime once client thread process is completed
						System.out.println(client.get(i).getName() + " runtime: " + (duration / 1000000) + " ms");
						runtime += duration; //adds instance duration to the total runtime. 
					}
					
					System.out.println("---------------------------------------"); //displays TATs for client
					System.out.println("Total TAT for " + clients + " clients: " + (runtime / 1000000) + " ms");
					System.out.println("Average TAT: " + (runtime / 1000000) / clients + " ms");
					System.out.println("---------------------------------------");
				}
				else{
					System.out.println("Number of simulated clients must be between (1-100)!");
				}
			}
			else if (command == 0){
				break; //exits client program loop
			}

			else {
				System.out.println("Please enter a integer between 0-6.");
			}
		}
		System.out.println("Client Program exited!");
		userInput.close();
	}
}
