package org.servalproject.svd;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class VideoDiscoveryActivity extends Activity {
	/**
	 * The camera handling class (manage also the surface)
	 */
	private CamView camcorderView;

	/**
	 * Debug Tag
	 */
	public static final String TAG = "SVD";

	/**
	 * Video flag
	 */
	private boolean videoFlag = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Set the screen horizontally
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		// Set up the preview and the camera
		camcorderView = (CamView) findViewById(R.id.camera_preview);

		// File where the file will be saved
		String file = Environment.getExternalStorageDirectory().getAbsolutePath();
		file += "/record.3gp";
		
		camcorderView.setOutputFile(file);

		// Set the hook for the toggle button
		((Button) findViewById(R.id.toggleVideo))
				.setOnClickListener(toggleVideoCallback);
	}

	/**
	 * A call-back for when the user send me.
	 */
	OnClickListener toggleVideoCallback = new OnClickListener() {

		public void onClick(View v) {
			if (!videoFlag) {
				Log.v(TAG, "Toggle video on");
				try {
					camcorderView.startRecording();
				} catch (Exception e) {
					Log.e(TAG, "Error : can't start");
					e.printStackTrace();
					finish();
				}
			} else { // Disabled
				Log.v(TAG, "Toggle video off");
				camcorderView.stopRecording();
			}
			videoFlag = !videoFlag;
		}
	};
}