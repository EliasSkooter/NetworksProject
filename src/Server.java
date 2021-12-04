import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
import java.sql.*;

public class Server {
    public static void main(String[] args) throws IOException {
        // Server is listening on port 5056
        ServerSocket serverSocket = new ServerSocket(6969);

        // Server keeps on receiving new Clients
        while (true) {

            Socket clientSocket = null;
            try {
                // ServerSocket waits for a Client to connect
                clientSocket = serverSocket.accept();

                System.out.println("A new client is connected : " + clientSocket);

                // Receiving input and sending output to Client
                DataInputStream inputFromClient = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(clientSocket.getOutputStream());

                System.out.println("Assigning new thread for this client");

                System.out.println("-----------------------------------------------------------------------------------");

                // Create a new Thread object for the Client
                Thread thread = new ClientHandler(clientSocket, inputFromClient, outputToClient);
                thread.start();

            } catch (Exception e) {
                clientSocket.close();
                e.printStackTrace();
            }
        }
    }
}

// ClientHandler class
class ClientHandler extends Thread {
    final Socket clientSocket;
    final DataInputStream inputFromClient;
    final DataOutputStream outputToClient;

    // Constructor
    public ClientHandler(Socket clientSocket, DataInputStream inputFromClient, DataOutputStream outputToClient) {
        this.clientSocket = clientSocket;
        this.inputFromClient = inputFromClient;
        this.outputToClient = outputToClient;
    }

    @Override
    public void run() {
        // Variables
        String received;
        String recieve2;
        String toreturn;
        while (true) {
            try {
                // Initiate communication with Client
//                outputToClient.writeUTF("What do you want?[Date | Time]..\n" + "Type Exit to terminate connection.");

                // Receive the answer from Client
                received = inputFromClient.readUTF();
                recieve2 = inputFromClient.readUTF();

                String myDriver = "com.mysql.cj.jdbc.Driver";
                String myUrl = "jdbc:mysql://localhost/networksdb";
                Class.forName(myDriver);
                Connection conn = DriverManager.getConnection(myUrl, "root", "");
                String query = "SELECT * FROM users";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next())
                {
                    String username = rs.getString("username");
                    String password = rs.getString("password");


                    if (username.equals(received) && password.equals(recieve2)) {

                        int id = rs.getInt("id");
                        String full_name = rs.getString("full_name");
                        String photo = rs.getString("photo");
                        String email = rs.getString("email");
                        String vaccination = rs.getString("vaccination_status");
                        System.out.println("Welcome: " + username);
                        System.out.println("your vaccination status: " + vaccination);
                        //System.out.print(id + ", " + full_name + ", " + photo + ", " + email + ", " + username + ", " + vaccination);
                    }
                    else{
                        System.out.print("authentication failed");
                    }
                }
                st.close();

//                System.out.println(received);

                if(inputFromClient.readUTF().equals("reg")) {


                    System.out.println(inputFromClient.readUTF());
//                    recieveImage();
                    System.out.println(inputFromClient.readUTF());
                    System.out.println(inputFromClient.readUTF());
                    System.out.println(inputFromClient.readUTF());
                    boolean vc = inputFromClient.readBoolean();
                    System.out.println(vc);
                    if(vc == true)
                    receiveFile();
                }
                else if (inputFromClient.readUTF().equals("log")){
                    System.out.println(inputFromClient.readUTF());
                    System.out.println(inputFromClient.readUTF());
                }
                // GET REQUEST ON localhost:6969/login

                // Receiving Exit closes the connection and breaks the loop
//                if (received.equals("Exit")) {
//                    System.out.println("-----------------------------------------------------------------------------------");
//                    System.out.println("Client " + this.clientSocket + " sends exit...");
//                    System.out.println("Closing this connection.");
//                    this.clientSocket.close();
//                    System.out.println("Connection closed");
//                    break;
//                }

                // Creating and formatting Date object
               /* Date date = new Date();
                DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
                DateFormat fortime = new SimpleDateFormat("hh:mm:ss");
//                Date date = new Date();
//                DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
//                DateFormat fortime = new SimpleDateFormat("hh:mm:ss");

                // Send to Client what is requested
//                switch (received) {
//
//                    case "Date":
//                        toreturn = fordate.format(date);
//                        outputToClient.writeUTF(toreturn);
//                        System.out.println("Sent date to client.");
//                        break;
//
//                    case "Time":
//                        toreturn = fortime.format(date);
//                        outputToClient.writeUTF(toreturn);
//                        System.out.println("Sent Time to client.");
//                        break;
//
//                    default:
//                        outputToClient.writeUTF("Invalid input");
//                        break;
//                }
            } catch (IOException e) {
                switch (received) {

                    case "Date":
                        toreturn = fordate.format(date);
                        outputToClient.writeUTF(toreturn);
                        System.out.println("Sent date to client.");
                        break;

                    case "Time":
                        toreturn = fortime.format(date);
                        outputToClient.writeUTF(toreturn);
                        System.out.println("Sent Time to client.");
                        break;

                    default:
                        outputToClient.writeUTF("Invalid input");
                        break;
                }*/
            } catch (IOException | SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
                try {
                    this.clientSocket.close();
                    System.out.println("Connection closed.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
        try {
            // Closing resources
            this.inputFromClient.close();
            this.outputToClient.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void receiveFile() {
        try {
            int bytesRead;

            DataInputStream clientData = new DataInputStream(clientSocket.getInputStream());

            String fileName = clientData.readUTF();
            OutputStream output = new FileOutputStream(fileName);
            long size = clientData.readLong();
            byte[] buffer = new byte[1024];
            while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }
//            File f = new File(fileName);
//            System.out.println(f.getAbsolutePath());
            output.close();
//            clientData.close();

            System.out.println("File "+fileName+" received from client.");
        } catch (IOException ex) {
            System.err.println("Client error. Connection closed.");
        }
    }
    public void recieveImage() throws IOException {
        InputStream clientData = new DataInputStream(clientSocket.getInputStream());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(clientData);
        BufferedImage bufferedImage = ImageIO.read(bufferedInputStream);
//        bufferedInputStream.close();
        File outputFile = new File("image.jpg");
        ImageIO.write(bufferedImage, "jpg", outputFile);
    }
}