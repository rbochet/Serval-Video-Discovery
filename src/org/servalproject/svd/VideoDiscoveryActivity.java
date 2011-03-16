package org.servalproject.svd;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.CamcorderProfile;
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

	/**
	 * Change the resolution to the lowest one
	 */
	private OnClickListener low_resCallback = new OnClickListener() {
		public void onClick(View v) {
			int width = CamcorderProfile.get(CamcorderProfile.QUALITY_LOW).videoFrameHeight;
			int height = CamcorderProfile.get(CamcorderProfile.QUALITY_LOW).videoFrameWidth;

			Log.v(TAG, "Def. set (low): " + width + "x" + height);

			camcorderView.setDefinition(width, height);
		}
	};

	/**
	 * Change the resolution to the highest one
	 */
	private OnClickListener high_resCallback = new OnClickListener() {
		public void onClick(View v) {
			int width = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH).videoFrameHeight;
			int height = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH).videoFrameWidth;

			Log.v(TAG, "Def. set (high): " + width + "x" + height);

			camcorderView.setDefinition(width, height);
		}
	};

	/**
	 * Change the frame rate to the lowest one
	 */
	private OnClickListener low_frCallback = new OnClickListener() {
		public void onClick(View v) {
			Log.v(TAG,
					"Frame rate set (low): "
							+ CamcorderProfile
									.get(CamcorderProfile.QUALITY_LOW).videoFrameRate);

			camcorderView.setFrameRate(CamcorderProfile
					.get(CamcorderProfile.QUALITY_LOW).videoFrameRate);
		}
	};

	/**
	 * Change the frame rate to the highest one
	 */
	private OnClickListener high_frCallback = new OnClickListener() {
		public void onClick(View v) {
			Log.v(TAG,
					"Frame rate set (high): "
							+ CamcorderProfile
									.get(CamcorderProfile.QUALITY_HIGH).videoFrameRate);

			camcorderView.setFrameRate(CamcorderProfile
					.get(CamcorderProfile.QUALITY_HIGH).videoFrameRate);
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// Start the background service
		Intent intent = new Intent(this, ControlService.class);
		startService(intent);

		// Set the screen horizontally
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		// Set up the preview and the camera
		camcorderView = (CamView) findViewById(R.id.camera_preview);

		// File where the file will be saved
		String file = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		file += "/record.3gp";
		camcorderView.setOutputFile(file);

		// Set the hook for the toggle button
		((Button) findViewById(R.id.toggleVideo))
				.setOnClickListener(toggleVideoCallback);

		// Set hook for the low_res
		((Button) findViewById(R.id.low_res))
				.setOnClickListener(low_resCallback);

		// Set hook for the high_res
		((Button) findViewById(R.id.high_res))
				.setOnClickListener(high_resCallback);

		// Set hook for the low_fr
		((Button) findViewById(R.id.low_fr)).setOnClickListener(low_frCallback);

		// Set hook for the high_fr
		((Button) findViewById(R.id.high_fr))
				.setOnClickListener(high_frCallback);

		// Disable buttons as they shouldn't be used when video doesn't run.
		((Button) findViewById(R.id.low_fr)).setEnabled(false);
		((Button) findViewById(R.id.high_fr)).setEnabled(false);
		((Button) findViewById(R.id.low_res)).setEnabled(false);
		((Button) findViewById(R.id.high_res)).setEnabled(false);

	}

	/**
	 * A call-back for when the user enable the video
	 */
	OnClickListener toggleVideoCallback = new OnClickListener() {

		public void onClick(View v) {
			if (!videoFlag) {
				Log.v(TAG, "Toggle video on");
				try {
					camcorderView.startRecording();
					((Button) findViewById(R.id.low_fr)).setEnabled(true);
					((Button) findViewById(R.id.high_fr)).setEnabled(true);
					((Button) findViewById(R.id.low_res)).setEnabled(true);
					((Button) findViewById(R.id.high_res)).setEnabled(true);
				} catch (Exception e) {
					Log.e(TAG, "Error : can't start");
					e.printStackTrace();
					finish();
				}
			} else { // Disabled
				Log.v(TAG, "Toggle video off");
				camcorderView.stopRecording();
				((Button) findViewById(R.id.low_fr)).setEnabled(false);
				((Button) findViewById(R.id.high_fr)).setEnabled(false);
				((Button) findViewById(R.id.low_res)).setEnabled(false);
				((Button) findViewById(R.id.high_res)).setEnabled(false);
			}
			videoFlag = !videoFlag;
		}
	};
}