package org.servalproject.svd;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ControlService extends Service {

	/**
	 * Debug Tag
	 */
	public static final String TAG = "SVD";

	/**
	 * The service notification ID (used for the notification manager)
	 */
	private static final int SERVICE_NOTIFICATION_ID = 82;

	public ControlService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "Control Service started.");
		
		// Create the notification
		Notification notification = new Notification(R.drawable.icon,
				getText(R.string.ticker_text), System.currentTimeMillis());
		startForeground(SERVICE_NOTIFICATION_ID, notification);
		Log.v(TAG, "Control service foregrounded.");
		
		handleCommand(intent);
		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return START_STICKY;
	}

	/**
	 * Handle the service start, ie. create a listening socket and wait for
	 * instruction.
	 * 
	 * @param intent
	 *            The passed intent
	 */
	private void handleCommand(Intent intent) {
		Log.v(TAG, "Handle method starteds");
	}

}
