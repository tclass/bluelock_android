package com.htw.bluelock;

import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

public class ServiceReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
        BluetoothDevice deviceExtra = intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
		Parcelable[] uuids = intent.getParcelableArrayExtra("android.bluetooth.device.extra.UUID");
		for(int i=0;i < uuids.length;i++){
			System.out.print(UUID.fromString(uuids[i].toString())+"\n");
		}
		System.out.println("\n\n");
	}

}
