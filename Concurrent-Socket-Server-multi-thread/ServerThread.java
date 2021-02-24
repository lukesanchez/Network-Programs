/******************************************************
 * Concurrent Socket ServerThread
 * @author Luke Sanchez & Jordan Pacilio 
 * Created: Jun. 20, 2020
 * Last Modified: Jul. 26, 2020
 *****************************************************/

import java.io.*;
import java.net.*;
 
public class ServerThread extends Thread {
    private Socket socket;
    int command; //from client
	String result = ""; //from server terminal output
 
    public ServerThread(Socket socket) {
        this.socket = socket;
    }
 
    public void run() {
        try {
        	InputStream input = socket.getInputStream(); //creates input and output streams to/from server
        	OutputStream output = socket.getOutputStream();
        	BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        	PrintWriter writer = new PrintWriter(output, true); 

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
        	
        	System.out.println("--- " + cmd + " ---"); //prints command and result in server console
        	System.out.println(result);

        	writer.println(result); //send result to client

        	socket.close();

        } catch (IOException ex) {
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
