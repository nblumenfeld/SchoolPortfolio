/**
 * This is the separate thread that services each
 * incoming web server request.
 *
 * @author Noah Blumenfeld
 *
 */

import java.net.*;
import java.io.*;
import java.net.URL;
import java.util.Calendar;
import java.nio.charset.StandardCharsets;

public class Connection implements Runnable
{
	public static final int BUFFER_SIZE = 2048;
	public static final int PORT = 8080;


	private Socket client;
	private Configuration config;

	public Connection(Socket client, Configuration config) {
		this.client = client;
		this.config = config;
	}

    /**
     * This is the method that runs when a new thread is ran
     */
	public void run() {
		try {
			//These are the different type of file requests that we can service
			String[] contentType = {"text/html", "image/gif", "image/jpeg", "image/png", "text/plain"};

			try{
				//Initialize the BufferedReader, OutputStream and BufferedWriter
				BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
				OutputStream out = new BufferedOutputStream(client.getOutputStream());
				BufferedWriter writer;

				//Split the request by spaces and initialize variables needed for next steps
				String line = reader.readLine();
				String[] splitOnSpaces = line.split(" ");
				String oldPath = splitOnSpaces[1];
				String req;
				File requestFile = null;
				String fileType;
				String content = " ";
				String contentSizeString;
				long contentSize = 0;
				String date;
				String server;
				String log;
				String logStatus;
				String connection;
				String request;

				//If the request is a GET request
				if (line.substring(0,3).equals("GET") ){
					String defaultFile = oldPath.replaceFirst("/","");

					defaultFile = URLDecoder.decode(defaultFile, StandardCharsets.UTF_8.toString());

					//No specific file requested, hand back the default document
					if(oldPath.equals("/")) {
						defaultFile = config.getDefaultDocument();
						requestFile = new File(defaultFile);
					}
					else{
						requestFile = new File(config.getDocumentRoot() + defaultFile);
					}

					//Document requested exists, find the file type for the request
					if(requestFile.isFile()){
						req = "HTTP/1.1 200 OK \r\n";
						logStatus = "200";

						fileType = defaultFile.substring(defaultFile.lastIndexOf("."));

						//Find out the file type
						if(fileType.equals(".html")){
							content = contentType[0];
						}
						else if(fileType.equals(".gif")){
							content = contentType[1];
						}
						else if(fileType.equals(".jpeg")){
							content = contentType[2];
						}
						else if(fileType.equals(".png")){
							content = contentType[3];
						}
						else if(fileType.equals(".txt")){
							content = contentType[4];
						}

						contentSize = requestFile.length();

					}
					//The file wasn't found, return a 404 error code
					else{
						req = "HTTP/1.1 404 NOT FOUND \r\n";
						logStatus = "404";
						content = contentType[0];
						requestFile = new File(config.getFourOFourDocument());
						contentSize = requestFile.length();
					}
				}
				else{
					client.close();
					return;
				}

				/* The Header should look like this
				* HTTP/1.1 <200 OK/ 404 NOT found>
				* Date: <today's date>
				* Server Name: <Server Name (from config)>
				* Content-Type: <contentType[x]>
				* Content-Lenght: <contentType[x].length>
				* Connection: closed
				*/
				date = "Date: "+ Calendar.getInstance().getTime() + "\r\n";
				server = "Server: " + config.getServerName() + "\r\n";
				content = "Content-Type: " + content + "\r\n";
				contentSizeString = "Content-Size: " + String.valueOf(contentSize) + "\r\n";
				connection = "Connection: close\r\n\r\n";

				//Build the full request
				request = req + date + server + content + contentSizeString + connection;

				//Build the log
				log = client.getInetAddress().toString() + " [" + Calendar.getInstance().getTime() + "] " + line + " " + logStatus + " " + String.valueOf(contentSize) + "\n";

				File logFile = new File(config.getLogFile());
				writer = new BufferedWriter(new FileWriter(logFile,true));
				writer.write(log);
				writer.close();

				//Write the request to the OutputStream
				out.write(request.getBytes());
				out.flush();

				//return the requested file or 404 file
				FileInputStream fileIn = new FileInputStream(requestFile);
				byte[] buffer = new byte[BUFFER_SIZE];
				int numBytes;
				while((numBytes = fileIn.read(buffer)) != -1){
					out.write(buffer, 0, numBytes);
				}
				//close all the open streams and connections
				fileIn.close();
				out.close();
				reader.close();
				client.close();

			}
			catch (Exception e){
				System.err.println(e);
			}
			finally{
				//ensure that the client connection was closed
				client.close();
			}
		}
		catch (java.io.IOException ioe) {
			System.err.println(ioe);
		}
	}
}
