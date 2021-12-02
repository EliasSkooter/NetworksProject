import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private InetAddress ip;
    private Socket socket;
    private DataOutputStream outputToServer;
    private DataInputStream inputFromServer;
//    private PrintWriter pr;
//    private BufferedInputStream bis;


    public Client() throws IOException {
        this.ip = InetAddress.getByName("localhost");
        this.socket = new Socket(ip, 6969);
        this.inputFromServer = new DataInputStream(socket.getInputStream());
        this.outputToServer = new DataOutputStream(socket.getOutputStream());

    }

    public static void main(String[] args) throws IOException {
//        try {
//            Scanner scan = new Scanner(System.in);
//
//            // Getting local IP Address (127.0.0.1)
//            InetAddress ip = InetAddress.getByName("localhost");
//
//            // Establish the connection with Server on port 5056
//            Socket socket = new Socket(ip, 6969);
//            // This will trigger the accept() function of the Server
//
//            // Receiving input and sending output to Server
//            DataInputStream inputFromServer = new DataInputStream(socket.getInputStream());
//            DataOutputStream outputToServer = new DataOutputStream(socket.getOutputStream());
//
//            while (true) {
//                System.out.println(inputFromServer.readUTF());
//                String toSend = scan.nextLine();
//                outputToServer.writeUTF(toSend);
//
//                // Sending Exit closes the connection and breaks the loop
//                if(toSend.equals("Exit"))
//                {
//                    System.out.println("-----------------------------------------------------------------------------------");
//                    System.out.println("Closing this connection : " + socket);
//                    socket.close();
//                    System.out.println("Connection closed");
//                    break;
//                }
//                System.out.println("-----------------------------------------------------------------------------------");
//                // Printing message received from Server
//                String received = inputFromServer.readUTF();
//                System.out.println(received);
//            }
//
//            // Closing resources
//            scan.close();
//            inputFromServer.close();
//            outputToServer.close();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataOutputStream getOutputToServer() {
        return outputToServer;
    }

    public void setOutputToServer(DataOutputStream outputToServer) {
        this.outputToServer = outputToServer;
    }

    public DataInputStream getInputFromServer() {
        return inputFromServer;
    }

    public void setInputFromServer(DataInputStream inputFromServer) {
        this.inputFromServer = inputFromServer;
    }

    public void sendMessageToServer(String s) throws IOException{
       try {
           this.outputToServer.writeUTF(s);
//           socket.close();
            }catch(Exception e){
           e.printStackTrace();
       }
    }
    public void sendFileToServer(File f) throws UnknownHostException, IOException{
        try {
//            byte[] buffer = new byte[(int)f.length()];
//            FileInputStream fileInputStream = new FileInputStream(f);
//            int bytes = fileInputStream.read(buffer,0,buffer.length);
//            outputToServer.write(buffer,0,bytes);

            String fileName = f.getName();
            int fileSize = (int) f.length();
            PrintWriter pw = new PrintWriter(outputToServer, true);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
            pw.println(fileName);
            pw.println(fileSize);
            byte[] fileByte = new byte[fileSize];
            bis.read(fileByte,0,fileByte.length);
            outputToServer.write(fileByte,0,fileByte.length);
            outputToServer.flush();


            //this.outputToServer.writeUTF(f);
//           socket.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
} 