import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class TransferFile_Server implements Runnable {
    private ServerSocket ss;
    private DataInputStream dis;
    TransferFile_Server(int port) {
        try {
            ss = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Socket clientSocket=null;
        while (true) {
            try {
                clientSocket = ss.accept();
                System.out.println(ss.getInetAddress().getHostAddress());
                //clientSocket.setSendBufferSize(2<<28);
                //clientSocket.setReceiveBufferSize(2<<28);
                dis = new DataInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(clientSocket != null){
                try {
                    long length = dis.readLong();
                    String name = dis.readUTF();
                    System.out.println("file name: "+name+" size: "+length);
                    FileOutputStream fos = new FileOutputStream(name);
                    byte buffer[] = new byte[32*1024];
                    while(dis.read(buffer)!=-1){
                        fos.write(buffer);
                    }
                    fos.close();
                    dis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
   
    public void close_inst(){
        try {
            dis.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            ss.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}