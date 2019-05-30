import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

public class ChatClient {
    public static void main(String[] args){
        System.out.println("客户端运行");

        Thread t = new Thread(()->{
            try(
                    Socket socket = new Socket("127.0.0.1",8080);
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    BufferedReader keyin = new BufferedReader(new InputStreamReader(System.in))
            ){
                while(true){
                    String str = keyin.readLine();
                    if(str.equals("bye")){
                        break;
                    }
                    out.writeUTF(str);
                    out.flush();

                }
            }catch (ConnectException e)
            {
                e.printStackTrace();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            System.out.println("停止运行");
        });
        Thread t2 = new Thread(()->{
            try(
                    Socket socket = new Socket("127.0.0.1",8081);
                    DataInputStream in= new DataInputStream(socket.getInputStream())

            ){
                while(true){

                    String str2 = in.readUTF();
                    System.out.printf("服务器数据：%s\n",str2);

                }
            }catch (ConnectException e)
            {
                e.printStackTrace();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            System.out.println("停止运行");
        });
        t.start();
        t2.start();
    }
}
