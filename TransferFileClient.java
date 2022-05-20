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

public class TransferFile_Client implements Runnable{
    private Socket socket;
    private String ip;
    private int port;
    private OutputStream os;
    private BufferedOutputStream bos;
    private DataOutputStream dos;



    private File file;
    private LinkedList<File> file_liste;



    TransferFile_Client(String ip, int port) {
        this.ip=ip;
        this.port = port;
        file_liste = new LinkedList<>();
        try {
            socket = new Socket(ip, port);
            os = socket.getOutputStream();
            bos = new BufferedOutputStream(os);
            dos = new DataOutputStream(bos);
            //socket.setSendBufferSize(2<<28);
            //socket.setReceiveBufferSize(2<<28);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("init end");
    }
    public void send_file(String file_name){
        File file = new File(file_name);
        file_liste.add(file);
    }
    
    public void run(){
        while(true){
            if(file_liste.size()>0){
                long length = file_liste.getFirst().length();
                String name = file_liste.getFirst().getName();
                try {
                    dos.writeLong(length);
                    dos.writeUTF(name);
                    System.out.println("send file name: "+name+" taille: "+length);
                    FileInputStream fis = new FileInputStream(file_liste.getFirst());
                    long offset=0;
                    //int size=2<<16;
                    byte[] buffer = new byte[128*1024];
                    int bytes=0;
                    while ((bytes=fis.read(buffer))!=-1){
                        dos.write(buffer);
                        dos.flush();
                    }
                    fis.close();
                    file_liste.removeFirst();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    private void sendFile(String filePath){
        try {
            byte[] buffer = new byte[Integer.MAX_VALUE];
            FileInputStream fis = new FileInputStream(file_liste.getFirst());
            int bytes = fis.read(buffer,0,buffer.length);
            dos.write(buffer,0,bytes);
            fis.close();
            dos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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