/**
 * A simple program demonstrating server sockets.
 *
 * @author Noah Blumenfeld.
 */

import java.net.*;
import java.io.*;
import java.util.concurrent.*;


public class WebServer
{
    public static final int PORT = 8080;
    private static final Executor threadPool = Executors.newCachedThreadPool();
	
	public static void main(String[] args) throws java.io.IOException {
        ServerSocket server = null;
        Configuration config = null;


		try{
            config = new Configuration(args[0]);
        }
        catch(ConfigurationException e){
            System.out.println("No config file: \n" + e);
        }

        try{
            server = new ServerSocket(PORT);
            
            while(true){
                Runnable connection = new Connection(server.accept(), config);
                threadPool.execute(connection);
            }
        }
        catch(IOException e) {
            System.err.println(e);
        }
        finally{
            if(server != null){
                server.close();
            }
        }
    }
}