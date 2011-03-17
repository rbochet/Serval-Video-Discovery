package org.servalproject.svd;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ControlService extends Service {

	/**
	 * Debug Tag
	 */
	public static final String TAG = "SVD";

	/**
	 * Listening port
	 */
	public static final int PORT = 6666;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onCreate");

		startServer();
	}

	/**
	 * Start the command server. Open a socket on the port 6666, and launch a
	 * TCP server which wait for the clients.
	 */
	private void startServer() {
		Log.d(TAG, "start Server");

		try {
			new Thread(new Runnable() {
				ServerSocket ss = new ServerSocket(ControlService.PORT);

				// Run method
				public void run() {
					try {
						Boolean end = false;
						// Run forever
						while (!end) {
							Log.v(TAG, "waiting for a connection.");
							Socket s = ss.accept();

							// Read the socket
							InputStream in = s.getInputStream();
							DataInputStream dis = new DataInputStream(in);
							byte request = dis.readByte();
							String address = s.getInetAddress()
									.getHostAddress();

							// Close the socket
							s.close();

							// Process the request
							processRequest(address, request);
						}
						ss.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}

				/**
				 * Process a request received from the network.
				 * 
				 * @param address
				 *            The sender's IP address
				 * @param request
				 *            The request
				 */
				private void processRequest(String address, byte request) {
					Log.v(TAG, "request received :" + request + " from "
							+ address);
					switch (request) {
					case MessageTypes.HD:
						Log.v(TAG, "Switch to HD requested");
						break;
					case MessageTypes.LD:

						break;
					case MessageTypes.HF:

						break;
					case MessageTypes.LF:

						break;
					case MessageTypes.START_STREAM:

						break;
					case MessageTypes.STOP_STREAM:

						break;
					case MessageTypes.KEEP_PACKETS:

						break;
					case MessageTypes.RELEASE_PACKETS:

						break;
					default:
						Log.e(TAG, "Received unknown message type");
						break;
					}
					Log.v(TAG, "Request processed");

				}
			}).start();
		} catch (IOException e) {
			Log.e(TAG, "Thread error");
			e.printStackTrace();
		}

	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onDestroy");
	}

	@Override
	public void onStart(Intent intent, int startid) {
		Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onStart");
	}

}
