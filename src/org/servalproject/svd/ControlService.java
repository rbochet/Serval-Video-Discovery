package org.servalproject.svd;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ControlService extends Service {

	/**
	 * Debug Tag
	 */
	public static final String TAG = "SVD";
	
	
	public ControlService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
