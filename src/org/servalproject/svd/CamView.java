package org.servalproject.svd;

import java.io.IOException;

import android.content.Context;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CamView extends SurfaceView implements SurfaceHolder.Callback {

	/**
	 * Debug Tag
	 */
	public static final String TAG = "SVD";

	/**
	 * Preview holder
	 */
	private SurfaceHolder holder;

	/**
	 * File which will be saved
	 */
	private String outputFile;

	/**
	 * Object which drive the record
	 */
	private MediaRecorder recorder;

	public CamView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// Set up the surface
		holder = getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		// Default output file
		outputFile = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		outputFile += "/record.3gp";

		recorder = new MediaRecorder();

		setupSourceEncoder();
		Log.v(TAG, "Camera initialisation complete.");
	}

	/**
	 * Prepare the camera. Basically call {@link MediaRecorder#prepare()}
	 */
	private void prepare() {
		if (recorder != null) {
			try {
				recorder.prepare();
			} catch (IllegalStateException e) {
				Log.e("IllegalStateException", e.toString());
			} catch (IOException e) {
				Log.e("IOException", e.toString());
			}
		}
	}

	/**
	 * Set the new definition.
	 * 
	 * @param width
	 *            The new width
	 * @param height
	 *            The new height
	 */
	private void setDefinition(int width, int height) {
		stopRecording();
		setupSourceEncoder();
		recorder.setVideoSize(width, height);
		prepare();
		recorder.start();
	}

	/**
	 * Set the new frame rate (independent of the resolution)
	 * 
	 * @param newFrameRate
	 *            The new FR
	 */
	private void setFrameRate(int newFrameRate) {
		stopRecording();
		setupSourceEncoder();
		recorder.setVideoFrameRate(newFrameRate);
		prepare();
		recorder.start();
	}

	/**
	 * Set a high definition
	 */
	public synchronized void setHighDefinition() {
		int width = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH).videoFrameHeight;
		int height = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH).videoFrameWidth;
		
		setDefinition(width, height);		
	}

	/**
	 * Set a high frame rate
	 */
	public synchronized void setHighFrameRate() {
		setFrameRate(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH).videoFrameRate);
	}

	/**
	 * Set a low definition
	 */
	public synchronized void setLowDefinition() {
		int width = CamcorderProfile.get(CamcorderProfile.QUALITY_LOW).videoFrameHeight;
		int height = CamcorderProfile.get(CamcorderProfile.QUALITY_LOW).videoFrameWidth;

		setDefinition(width, height);		
	}

	/**
	 * Set a low frame rate
	 */
	public synchronized void setLowFrameRate() {
		setFrameRate(CamcorderProfile.get(CamcorderProfile.QUALITY_LOW).videoFrameRate);
	}

	/**
	 * Set the new file
	 * 
	 * @param filename
	 *            Name of the file (absolute)
	 */
	public void setOutputFile(String filename) {
		outputFile = filename;
		recorder.setOutputFile(filename);
	}

	/**
	 * Set up the source and the encorder
	 */
	private void setupSourceEncoder() {
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
	}

	/**
	 * Start the recording. Reset all the parameters before
	 */
	public synchronized void startRecording() {
		recorder.reset();
		setupSourceEncoder();
		prepare();
		recorder.start();
	}

	/**
	 * Stop recording. Release the camera, and reinit the preview. Need it to
	 * restart cleanly.
	 */
	public synchronized void stopRecording() {
		recorder.stop();
		recorder.release();
		recorder = new MediaRecorder();

		recorder.setOutputFile(outputFile);
		recorder.setPreviewDisplay(holder.getSurface());

	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		recorder.setOutputFile(outputFile);
		recorder.setPreviewDisplay(holder.getSurface());
		prepare();
	}
	public void surfaceDestroyed(SurfaceHolder holder) {
	}

}