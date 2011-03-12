package org.servalproject.svd;

import java.io.IOException;

import android.content.Context;
import android.database.MergeCursor;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CamView extends SurfaceView implements SurfaceHolder.Callback {

	/**
	 * Object which drive the record
	 */
	private MediaRecorder recorder;

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
	 * Set up the source and the encorder
	 */
	private void setupSourceEncoder() {
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		recorder.setOutputFile(outputFile);
		recorder.setPreviewDisplay(holder.getSurface());
		prepare();
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

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
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
	 * Start the recording. Reset all the parameters before
	 */
	public void startRecording() {
		recorder.reset();
		setupSourceEncoder();
		prepare();
		recorder.start();
	}

	/**
	 * Stop recording. Release the camera, and reinit the preview. Need it to
	 * restart cleanly.
	 */
	public void stopRecording() {
		recorder.stop();
		recorder.release();
		recorder = new MediaRecorder();

		recorder.setOutputFile(outputFile);
		recorder.setPreviewDisplay(holder.getSurface());

	}

	/**
	 * Set the new frame rate (independent of the resolution)
	 * 
	 * @param newFrameRate
	 *            The new FR
	 */
	public void setFrameRate(int newFrameRate) {
		stopRecording();
		setupSourceEncoder();
		recorder.setVideoFrameRate(newFrameRate);
		prepare();
		recorder.start();
	}

	/**
	 * Set the new definition.
	 * 
	 * @param width
	 *            The new width
	 * @param height
	 *            The new height
	 */
	public void setDefinition(int width, int height) {
		stopRecording();
		setupSourceEncoder();
		recorder.setVideoSize(width, height);
		prepare();
		recorder.start();
	}
}