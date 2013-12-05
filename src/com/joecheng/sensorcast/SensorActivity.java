package com.joecheng.sensorcast;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class SensorActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mRotationSensor;

    public SensorActivity() {
    }
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sensor);
		// Show the Up button in the action bar.
		setupActionBar();
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mRotationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
	}

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mRotationSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
    	TextView textView = (TextView)findViewById(R.id.sensor_output);
    	textView.setText(event.values[0] + "\n" + event.values[1] + "\n" + event.values[2] + "\n" + event.values[3] + "\n" + event.values[4] + "\n");

    	ByteArrayOutputStream baos = new ByteArrayOutputStream(event.values.length * 4);
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			for (float v : event.values)
				dos.writeFloat(v);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e1);
		}
		final byte[] data = baos.toByteArray();

		new AsyncTask<Integer, Integer, Integer>() {

			@Override
			protected Integer doInBackground(Integer... params) {
		    	try {
		    		DatagramSocket socket = new DatagramSocket();
		    		try {
		    			socket.setBroadcast(true);

		    			DatagramPacket packet = new DatagramPacket(
		    					data, data.length,
		    					InetAddress.getByName("224.0.0.0"),
		    					5678);
		    			socket.send(packet);
		    		} catch (SocketException e) {
		    			// TODO Auto-generated catch block
		    			e.printStackTrace();
		    			Log.e("SensorActivity", "Exception", e);
		    		} catch (IOException e) {
		    			// TODO Auto-generated catch block
		    			e.printStackTrace();
		    			Log.e("SensorActivity", "Exception", e);
		    		} finally {
		    			socket.close();
		    		}
		    	} catch (SocketException se) {
	    			Log.e("SensorActivity", "Exception", se);
		    		throw new RuntimeException(se);
		    	}
		    	return 0;
			}
		}.execute();
    }


	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sensor, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
