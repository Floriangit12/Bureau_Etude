import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
 
public class SimpleClient {
    public static void main(String[] args) throws Exception {
        Socket sock = new Socket("192.168.0.25", 5991);
        System.out.println("Connecting.........");
        File myFile = new File("C:/Users/Dell/Music/TEST_TCP");
        File[] files = myFile.listFiles();
        OutputStream os = sock.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        DataOutputStream dos = new DataOutputStream(bos);
 
        dos.writeInt(files.length);
        long totalBytesRead = 0;
        int percentCompleted = 0;
        for (File file : files) {
            long length = file.length();
            dos.writeLong(length);
 
            String name = file.getName();
            dos.writeUTF(name);
 
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
 
            int theByte = 0;
            while ((theByte = bis.read()) != -1) {
                totalBytesRead += theByte;
                
                bos.write(theByte);
            }
            // System.out.println("file read");
            bis.close();
        }
 
        dos.close();
 
        // Closing socket
        sock.close();
    }
}
 
 
