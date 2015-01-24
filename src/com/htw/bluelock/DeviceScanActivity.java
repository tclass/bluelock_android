package com.htw.bluelock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Activity for scanning and displaying available BLE devices.
 */
public class DeviceScanActivity extends ListActivity
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Ensures Bluetooth is available on the device and it is enabled. If
		// not, displays a dialog requesting user permission to enable Bluetooth.
		Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		startActivityForResult(enableBtIntent, RESULT_OK);
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		final ArrayList<String> devices = new ArrayList<String>();
		final Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
		DeviceArrayAdapter adapter = new DeviceArrayAdapter(this, android.R.layout.simple_list_item_1, devices);

		getListView().setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> p, View v, int pos, long arg3)
			{
				for (BluetoothDevice d : pairedDevices)
				{
					if (devices.get(pos).endsWith(d.getAddress()))
					{
						BluetoothConnection.getInstance(d);
						return;
					}
				}
			}
		});

		if (pairedDevices.size() > 0)// fill adapter
		{
			for (BluetoothDevice device : pairedDevices)
			{
				adapter.add(device.getName() + "\n" + device.getAddress());
			}
		}

		setListAdapter(adapter);
	}

	private class DeviceArrayAdapter extends ArrayAdapter<String>
	{
		public DeviceArrayAdapter(Context context, int textViewResourceId, List<String> objects)
		{
			super(context, textViewResourceId, objects);
		}

		@Override
		public boolean hasStableIds()
		{
			return true;
		}
	}

}