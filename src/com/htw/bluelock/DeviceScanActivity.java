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
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Activity for scanning and displaying available BLE devices.
 */
public class DeviceScanActivity extends ListActivity {

	private BluetoothAdapter bluetoothAdapter;
	private DeviceArrayAdapter deviceListAdapter;
	private ArrayList<String> results = new ArrayList<String>();
	private Set<BluetoothDevice> pairedDevices;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Ensures Bluetooth is available on the device and it is enabled. If
		// not,
		// displays a dialog requesting user permission to enable Bluetooth.
		if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, RESULT_OK);
			bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		}

		pairedDevices = bluetoothAdapter.getBondedDevices();
		deviceListAdapter = new DeviceArrayAdapter(this, android.R.layout.simple_list_item_1, results);

		if (pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices) {
				deviceListAdapter.add(device.getName() + "\n" + device.getAddress());
				device.fetchUuidsWithSdp();
				ParcelUuid[] uuids = device.getUuids();
				for(int i=0;i < uuids.length;i++){
					System.out.print(uuids[i].toString()+"\n");
				}
			}
		}

		setListAdapter(deviceListAdapter);
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Iterator i = pairedDevices.iterator();
				String clickedDevice = deviceListAdapter.getItem(position);
				BluetoothDevice actualDevice = null;
				while (i.hasNext()) {
					actualDevice = (BluetoothDevice) i.next();

					if (new String(actualDevice.getName() + "\n" + actualDevice.getAddress()).equals(clickedDevice)) {
						break;
					}
				}
				Intent intent = new Intent(DeviceScanActivity.this, BluelockMainScreen.class);
				if (actualDevice != null) {
					Bundle b = new Bundle();
					b.putParcelable("device", (Parcelable)actualDevice);
					intent.putExtra("device", b);
				}
				startActivity(intent);
			}

		});
	}

	private class DeviceArrayAdapter extends ArrayAdapter<String> {

		public DeviceArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
			super(context, textViewResourceId, objects);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}

}