# Iterative Socket Server - Single-thread 

*Note: This is a collaborative project between Luke Sanchez and JT Pacilio.*

## Introduction

The purpose of our Iterative Socket Server is to create both a client and server that connect and communicate to each other. Creating these will help us understand the effects an iterative server has on the efficiency of processing client requests. The goal of the project is to create a fully functional iterative server that will accept all the requests from its clients. Also, to create a multi-threaded client that has the capability of generating multiple client sessions which will connect to the server. The requests that must be fulfilled are as follows: date and time, uptime, memory use, netstat, current users and running processes. The final goal is to collect the data of the elapsed time for each client request and determine the effects of the efficiency of the client requests. In the remaining sections we will discuss the client-server setup and configuration which describes how we designed both the client and server programs including the basic operation of the programs. Following is how the client and server was tested as well as the data collection process. Subsequently we will discuss the process of the data analysis as well as the conclusion that we drew from the analysis. Finally, we will consider the lessons that were learned throughout this project.
  
## Client-Server Setup and Configuration

The Iterative-Socket Server program code is designed to create a server socket object which then waits for the client to fully connect to the server. The server then reads the input from the client and based on what the user-entered input is the corresponding results are then sent back to the client. The client requests are going to be executed as follows: date and time, uptime, memory use, netstat, current users and running processes. Finally, the client disconnects and awaits another client request. The client program is designed in order to generate multiple Socket objects which the amount will be determined by the user. First the user must enter an IP address as well as a specific port in order for the socket to properly connect. Then each of the commands the user may choose corresponds to a number (0-6) that the server may execute. Once chosen the system logs the time it takes from the socket creation to the time when it finishes accepting the information. Both the average and total turn-around times are then displayed.

One of the main design decisions that were made was the corresponding numbers for each of the commands. This allows for less erroneous inputs by the user and an overall ease of use of the program. We made this decision with the user in mind since it did require a fair amount of extra coding in order to get the code to run properly with this incorporated. Another major design decision was the output of the requests. We decided to display both the total turn-around time as well as the average turn-around time in milliseconds that way the user can visually see the output of each of the client requests. 

The basic operation of the client and server programs is reasonably simple. First, you must be connected to the university’s VPN while also using a SSH that is connected to a specific IP address. This server file is executed via a command line which will require no additional input. On the other end, the client is also connected using the SSH and executed through the command line. The specific IP address as well as the port is entered into the client. Next, the client program will prompt the user for which operation to perform as well as asking the number of simulated clients to generate. The client will then execute that specific operation at the amount set by the user and logs the average and total turn-around times for execution. The client may be run again as well as the server will continuously run until the quit operation is requested from the client.


## Testing and Data Collection

  The Iterative Socket Server was tested three times for each operation at each number of requests. Each operation (date and time, uptime, memory use, netstat, current users and running processes) was tested at the client generation increments 1, 5, 10, 15, 20 and 25. The two data sets that were collected were the total turn-around time and the average turn-around time (TAT) per client. Each of the operation’s three runs were averaged in order to reduce outlying data points. Each operation was tested on different 18 runs at the various client increments for a total of 216 data points on all server operations. This testing was slightly exhaustive, but necessary in order to get the proper data collection for analyzing the server’s performance.
  
#### Turn-around times of six different commands. Times are in Milliseconds. ####
  
 ![Iterative Test Results](/assets/ISS_Data.png)
  
 ![Iterative Test Results](/assets/ISS_Graphs.png) 
 
 
 ## Data Analysis
 
  The impact of increasing the number of clients has on the Turn-around Time for individual clients is that it increases the time it takes for both the total and average Turn-around Time relative to the number of clients generated. The effect of increasing the number of clients has on the Average Turn-around Time is that the Average Turn-around Time increases correlationally. As the client number increases, the average time it takes for each request increases as well. The primary cause for the extended individual client Turn-around Time and Average Turn-around Time could be mainly due to the single-threaded design of the server. The server is limited in the amount of request it can process at a given time. With the server resources allocated in a multithreaded design, the average Turn-around Time may be reduced by not having to wait for one client process their command before another client command can execute.  
This hypothesis will be tested in a later version of the server. 

## Conclusion and Lessons Learned

 The conclusion that can be drawn from our data analysis is that based on the individual operation the client is handling, both average and total Turn-around Time increase as the number of client requests increase. We can confirm this is true because the data is fairly consistent even when the operations change. As suspected, the total Turn-around Time increases as the number of clients increases, however, the average Turn-around Time per client gets increasingly prolonged relative to the number of simulated clients. These extended per client Turn-around Times are likely due to the iterative design of the server; with performance significantly degrading as more clients are added. 

 A lesson we learned during this Iterative Server assignment came from collecting the data. We learned from collecting the data that the Turn-around Time is affected strongly by the number of client requests. Some lessons learned from writing the client and server programs were how to implement threads in a program, how to use sockets to communicate, and that our programming skills have gotten rusty. One of the problems that we had to overcome was the university's Virtual Machine Environment not working properly. We overcame this issue by creating our own local Virtual Machine to test the programs, until we could use the university’s VMs to collect the data. 




 
  
