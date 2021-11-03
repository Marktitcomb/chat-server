
import java.io.*;
import java.net.*;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**

*

* @author mtitcomb

*/

public class UserThread extends Thread {
    private Socket socket;
    private ChatServer server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String userName;

    public UserThread(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
       try(
          ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
          ObjectInputStream in = new ObjectInputStream(socket.getInputStream()))
      {
        this.out = out;
        this.in = in;
        this.socket.setTcpNoDelay(true);

        Serializable userName = (Serializable) in.readObject();
        this.userName = userName.toString();

        while(true){
          Serializable data = (Serializable) in.readObject();
          System.out.println(data.toString());
          server.broadcast(userName + ": " + data.toString(), this);
        }

      }catch(Exception e){
      }
    }

    //sends the message to a client over the socket
     void sendMessage(Serializable message)throws Exception {
       out.writeObject(message.toString());
    }


}

 