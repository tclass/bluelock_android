package com.htw.bluelock;

import java.io.IOException;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BluelockMainScreen extends Activity {

	BluetoothConnection connection;
	Button connect;
	Button shutdown;
	Button login;
	Button logout;

	protected void onResume() {
		super.onResume();
		Bundle b = (Bundle) this.getIntent().getBundleExtra("device");
		if (b != null) {
			BluetoothDevice device = b.getParcelable("device");
			if (device != null) {
				connection = new BluetoothConnection(device);
				try {
					connection.connect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		connect = (Button) findViewById(R.id.btnconnect);
		connect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BluelockMainScreen.this, DeviceScanActivity.class);
				startActivity(intent);
			};
		});

		login = (Button) findViewById(R.id.btnLogin);
		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				connection.write(BluetoothConnection.protocol.get("UNLOCK"));
			};
		});

		logout = (Button) findViewById(R.id.btnLogout);
		logout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				connection.write(BluetoothConnection.protocol.get("LOCK"));
			};
		});

		shutdown = (Button) findViewById(R.id.btnShutdown);
		shutdown.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				connection.write(BluetoothConnection.protocol.get("SHUTDOWN"));
			};
		});

	}
}