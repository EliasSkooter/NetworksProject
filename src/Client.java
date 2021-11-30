import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        try {
            Scanner scan = new Scanner(System.in);

            // Getting local IP Address (127.0.0.1)
            InetAddress ip = InetAddress.getByName("localhost");

            // Establish the connection with Server on port 5056
            Socket socket = new Socket(ip, 6969);
            // This will trigger the accept() function of the Server

            // Receiving input and sending output to Server
            DataInputStream inputFromServer = new DataInputStream(socket.getInputStream());
            DataOutputStream outputToServer = new DataOutputStream(socket.getOutputStream());

            while (true) {
                System.out.println(inputFromServer.readUTF());
                String tosend = scan.nextLine();
                outputToServer.writeUTF(tosend);

                // Sending Exit closes the connection and breaks the loop
                if(tosend.equals("Exit"))
                {
                    System.out.println("-----------------------------------------------------------------------------------");
                    System.out.println("Closing this connection : " + socket);
                    socket.close();
                    System.out.println("Connection closed");
                    break;
                }
                System.out.println("-----------------------------------------------------------------------------------");
                // Printing message received from Server 
                String received = inputFromServer.readUTF();
                System.out.println(received);
            }

            // Closing resources 
            scan.close();
            inputFromServer.close();
            outputToServer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
} 