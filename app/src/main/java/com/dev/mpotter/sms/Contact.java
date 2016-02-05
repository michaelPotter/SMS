package com.dev.mpotter.sms;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

/**
 * Created by michael on 2/5/16.
 */
public class Contact {

	public static final String[] CONTACT_NAME_PROJECTION = {
			"display_name"
	};

	public static String getNameFromPhoneNumber(Context context, String address) {
		Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
				Uri.encode(address));
		Cursor c = context.getContentResolver().query(lookupUri, CONTACT_NAME_PROJECTION, null, null, null);
		c.moveToFirst();
		if (c.getCount() > 0)
			return c.getString(c.getColumnIndex("display_name"));
		else
			return null;
	}

}
