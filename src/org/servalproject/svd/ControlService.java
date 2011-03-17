package org.servalproject.svd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

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
	 * Start the command server. Open a socket on the port PORT.
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
							BufferedReader input = new BufferedReader(
									new InputStreamReader(s.getInputStream()));
							String rcvd = input.readLine();
							Log.v(TAG, "From client: " + rcvd);
							s.close();
						}
						ss.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

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
