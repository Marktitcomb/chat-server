import java.io.*;
import java.net.*;
import java.util.*;

/**
 * 
 * This is the chat server program.
 * 
 * Press Ctrl + C to terminate the program.
 *
 * 
 * 
 * @author www.codejava.net
 * 
 */

public class ChatServer {

	private int port;
	private Set<String> userNames = new HashSet<>();
	private Set<UserThread> userThreads = new HashSet<>();
	public ChatServer(int port) {
		this.port = port;
	}

	public void execute() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Chat Server is listening on port " + port);

			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("New user connected");
				UserThread newUser = new UserThread(socket, this);
				userThreads.add(newUser);
				newUser.start();
			}

		} catch (IOException ex) {
			System.out.println("Error in the server: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	/**
     * Delivers a message from one user to others (broadcasting)
     */

    void broadcast(String message, UserThread excludeUser) throws Exception{
        for (UserThread aUser : userThreads) {
            //aUser.sendMessage(message);
            if (aUser != excludeUser) {
                aUser.sendMessage(message);
            }
        }
    }
   
    public static void main(String[] args) {
        int port = 4555;
        ChatServer server = new ChatServer(port);
        server.execute();
    }
    
}