import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static void main(String[] args){
        System.out.println("服务器运行");
        Thread t = new Thread(()->{
            try(ServerSocket server = new ServerSocket(8081);
                Socket socket = server.accept();
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                BufferedReader keyboardIn = new BufferedReader(new InputStreamReader(System.in))){
                while (true)
                {
                    String s1 = keyboardIn.readLine();
                    if(s1.equals("bye")){
                        break;
                    }
                    out.writeUTF(s1);
                    out.flush();
                }
            }catch (Exception e){
                e.printStackTrace();

            }
            System.out.println("停止运行");
        });
        Thread t2 = new Thread(()->{
            try(ServerSocket server = new ServerSocket(8080);
                Socket socket = server.accept();
                DataInputStream in = new DataInputStream(socket.getInputStream())){
                while (true)
                {
                    String str = in.readUTF();
                    System.out.printf("客户端数据：%s\n",str);

                }
            }catch (Exception e){
                e.printStackTrace();

            }
            System.out.println("停止运行");
        });
        t.start();
        t2.start();



    }
}
