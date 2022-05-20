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

public class TransferFile_Client {
    private Socket socket;
    private String ip;
    private int port;
    private OutputStream os;
    private BufferedOutputStream bos;
    private DataOutputStream dos;



    private File file;
    private LinkedList<File> file_liste;



    TransferFile_Client(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            os = socket.getOutputStream();
            bos = new BufferedOutputStream(os);
            dos = new DataOutputStream(bos);
            this.ip=ip;
            this.port = port;
            socket.setSendBufferSize(2<<24);
            socket.setReceiveBufferSize(2<<24);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void send_file(File file){
        file_liste.add(file);
    }
    
    public void run(){
        while(true){
            if(!file_liste.isEmpty()){
                long length = file_liste.getFirst().length();
                String name = file_liste.getFirst().getName();
                try {
                    dos.writeLong(length);
                    dos.writeUTF(name);
                    FileInputStream fis = new FileInputStream(file_liste.getFirst());
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    while ( bis.read() != -1) {
                        bos.write(bis.read());
                    }
                    file_liste.removeFirst();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
        }
    }
    public void close_inst(){
        try {
            dos.close();
            bos.close();
            os.close();
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
