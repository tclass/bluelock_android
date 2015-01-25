package com.htw.bluelock;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

public class BluelockMainScreen extends Activity
{
	private BluetoothConnection mConnection;

	public void clickConnect(View v)
	{
		startActivityForResult(new Intent(BluelockMainScreen.this, DeviceScanActivity.class), 42);
	}

	public void clickLogin(View v)
	{
		try
		{
			mConnection.write(BluetoothConnection.protocol.get("UNLOCK"));
		} catch (IOException e)
		{
			// TODO toast
		}
	}

	public void clickLogout(View v)
	{
		try
		{
			mConnection.write(BluetoothConnection.protocol.get("LOCK"));
		} catch (IOException e)
		{
			// TODO toast
		}
	}

	public void clickShutdown(View v)
	{
		try
		{
			mConnection.write(BluetoothConnection.protocol.get("SHUTDOWN"));
		} catch (IOException e)
		{
			// TODO toast
		}
	}
}