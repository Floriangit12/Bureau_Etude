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

public class TransferFile_Server {
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
                clientSocket.setSendBufferSize(2<<24);
                clientSocket.setReceiveBufferSize(2<<24);
                dis = new DataInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(clientSocket != null){
                try {
                    long length = dis.readLong();
                    String name = dis.readUTF();
                    FileOutputStream fos = new FileOutputStream(name);
                    fos.write((int) length);
                    // ecriture du fichier
                    fos.close();
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