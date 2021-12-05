import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
import java.sql.*;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        // Server is listening on port 5056
        ServerSocket serverSocket = new ServerSocket(6969);
        String myDriver = "com.mysql.cj.jdbc.Driver";
        String myUrl = "jdbc:mysql://localhost/networksdb";
        Class.forName(myDriver);
        Connection conn = DriverManager.getConnection(myUrl, "root", "");
        System.out.println("Database connection successful");

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
                Thread thread = new ClientHandler(clientSocket, inputFromClient, outputToClient, conn);
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
    final Connection conn;

    // Constructor
    public ClientHandler(Socket clientSocket, DataInputStream inputFromClient, DataOutputStream outputToClient, Connection conn) {
        this.clientSocket = clientSocket;
        this.inputFromClient = inputFromClient;
        this.outputToClient = outputToClient;
        this.conn = conn;
    }

    @Override
    public void run() {
        // Variables
        String toreturn;
        while (true) {
            try {
                // Initiate communication with Client
//                outputToClient.writeUTF("What do you want?[Date | Time]..\n" + "Type Exit to terminate connection.");

                // Receive the answer from Client


//                System.out.println(received);
                String clientInputType = inputFromClient.readUTF();

                if(clientInputType.equals("reg")) {


                    //System.out.println(inputFromClient.readUTF());
                    String receive_name = inputFromClient.readUTF();
                    //you can sout the recieveFile method alone this gives the full path though
                    //System.out.println(receiveFile().getAbsolutePath());
                    String receive_file = receiveFile().getAbsolutePath();
                    //System.out.println(inputFromClient.readUTF());
                    String receive_email = inputFromClient.readUTF();
                    //System.out.println(inputFromClient.readUTF());
                    String receive_username = inputFromClient.readUTF();
                    //System.out.println(inputFromClient.readUTF());
                    String receive_password = inputFromClient.readUTF();
                    boolean vc = inputFromClient.readBoolean();
                    //System.out.println(vc);
                    //if(vc == true)
                        //System.out.println(receiveFile().getAbsolutePath());

                    String query = "INSERT INTO users (full_name, photo, email, username, password, vaccination_status) "
                            +" VALUES (?, ?, ?, ?, ?, ?)";
                    File file = new File(receive_file);
                    FileInputStream fileInputStream = new FileInputStream(file);

                    PreparedStatement preparedStmt = conn.prepareStatement(query);
                    preparedStmt.setString(1,receive_name);
                    preparedStmt.setBinaryStream(2,fileInputStream);
                    preparedStmt.setString(3,receive_email);
                    preparedStmt.setString(4,receive_username);
                    preparedStmt.setString(5,receive_password);
                    preparedStmt.setBoolean(6,vc);
                    preparedStmt.execute();
                }
                else if (clientInputType.equals("log")){
                    String received = inputFromClient.readUTF();
                    String recieve2 = inputFromClient.readUTF();

                    String query = "SELECT * FROM users";
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery(query);
                    while (rs.next())
                    {
                        String username = rs.getString("username");
                        String password = rs.getString("password");


                        if (username.equals(received) && password.equals(recieve2)) {
                            outputToClient.writeBoolean(true);
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
                            outputToClient.writeBoolean(false);
//                            System.out.println();
                        }
                    }
                    st.close();
                    /*System.out.println(inputFromClient.readUTF());
                    System.out.println(inputFromClient.readUTF());*/
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
            } catch (IOException | SQLException e) {
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
    public File receiveFile() {
        File f = null;
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
            f = new File(fileName);

            output.close();
//            clientData.close();

        } catch (IOException ex) {
            System.err.println("Client error. Connection closed.");
        }
        return f;
    }
}