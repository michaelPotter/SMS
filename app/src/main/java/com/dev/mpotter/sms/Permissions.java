package com.dev.mpotter.sms;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by michael on 2/5/16.
 * Utility class for handling permissions
 */
public class Permissions {

	public static final String READ_SMS = Manifest.permission.READ_SMS;
	public static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
	public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;

	/**
	 * Checks if a permission has been granted and returns a boolean
	 * @param context calling context
	 * @param permission permission to check
	 * @return boolean true if requested permission has been granted
	 */
	public static boolean checkPermission(Activity context, String permission) {
		int permissionCheck = ContextCompat.checkSelfPermission(context, permission);
		return permissionCheck == PackageManager.PERMISSION_GRANTED;
	}

	/**
	 * Requests a permission
	 * @param context
	 * @param permission
	 */
	public static void requestPermission(Activity context, String permission) {
		if (!checkPermission(context, permission)) {
			ActivityCompat.requestPermissions(context,
					new String[]{READ_CONTACTS, READ_SMS},
					MY_PERMISSIONS_REQUEST_READ_CONTACTS);
		}
	}



}
