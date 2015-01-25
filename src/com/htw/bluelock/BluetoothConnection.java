package com.htw.bluelock;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class BluetoothConnection {

	private static final String BLUELOCK_UUID = "00001124-0000-1000-8000-00805f9b34fb";
	public static HashMap<String, String> protocol = new HashMap<String, String>();

	static {
		protocol = new HashMap<String, String>();
		protocol.put("CONNECTION_TEST", "0");
		protocol.put("LOCK", "1");
		protocol.put("UNLOCK", "2");
		protocol.put("SHUTDOWN", "3");
	}

	private BluetoothSocket socket;
	private static BluetoothDevice mDevice;
	private static BluetoothConnection mInstance;

	public static BluetoothConnection getInstance(BluetoothDevice device) {
		if (mInstance == null && device != null) {
			mInstance = new BluetoothConnection();
			mDevice = device;
		}
		return mInstance;
	}
	public static void killInstance(){
		mInstance = null;
	}

	private BluetoothConnection() {
	}

	private void connect() throws IOException {
		if (mDevice != null) {
			socket = mDevice.createInsecureRfcommSocketToServiceRecord(UUID.fromString(BluetoothConnection.BLUELOCK_UUID));
			socket.connect();
		}
	}

	public boolean isConnected() {
		return socket.isConnected();
	}

	public String write(String code) throws IOException {
		try {
			if (socket == null)
				connect();
				
			socket.getOutputStream().write(code.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			if (socket != null) {
				socket.getOutputStream().write(code.getBytes());
				connect();
				return "after recoonect, send command to device";
			}
		}
		return "Send command to device";
	}
}
