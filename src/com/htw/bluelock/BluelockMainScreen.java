package com.htw.bluelock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class BluelockMainScreen extends Activity {
	private BluetoothConnection mConnection;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		disable();
	}

	public void clickConnect(View v) {
		startActivityForResult(new Intent(BluelockMainScreen.this, DeviceScanActivity.class), 42);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mConnection = BluetoothConnection.getInstance(null);
		try {
			mConnection.write(BluetoothConnection.protocol.get("CONNECTION_TEST"));
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "Couldn't connect, is the PC App running?", Toast.LENGTH_SHORT).show();
			BluetoothConnection.killInstance();
			disable();
		}
		if (BluetoothConnection.getInstance(null) != null) {
			enable();
		}
	}

	public void clickLogin(View v) {
		try {
			mConnection.write(BluetoothConnection.protocol.get("UNLOCK"));
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "Couldn't send command, please reconnect again!", Toast.LENGTH_SHORT).show();
			BluetoothConnection.killInstance();
			disable();
		}
	}

	public void clickLogout(View v) {
		try {
			mConnection.write(BluetoothConnection.protocol.get("LOCK"));
		} catch (Exception e) {
			Toast.makeText(this, "Couldn't send command, please reconnect again!", Toast.LENGTH_SHORT).show();
			BluetoothConnection.killInstance();
			disable();
		}
	}

	public void clickShutdown(View v) {
		try {
			mConnection.write(BluetoothConnection.protocol.get("SHUTDOWN"));
		} catch (Exception e) {
			Toast.makeText(this, "Couldn't send command, please reconnect again!", Toast.LENGTH_SHORT).show();
			BluetoothConnection.killInstance();
			disable();
		}
	}

	private void enable() {
		Button shutdown = (Button) findViewById(R.id.btnShutdown);
		shutdown.setEnabled(true);
		Button login = (Button) findViewById(R.id.btnLogin);
		login.setEnabled(true);
		Button logout = (Button) findViewById(R.id.btnLogout);
		logout.setEnabled(true);
	}

	private void disable() {
		Button shutdown = (Button) findViewById(R.id.btnShutdown);
		shutdown.setEnabled(false);
		Button login = (Button) findViewById(R.id.btnLogin);
		login.setEnabled(false);
		Button logout = (Button) findViewById(R.id.btnLogout);
		logout.setEnabled(false);
	}
}