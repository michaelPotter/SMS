package com.dev.mpotter.sms;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.Date;

/**
 * Created by michael on 2/4/16.
 * Contains summary data about a messaging thread between 2 people
 */
public class Thread {

	public static final String[] PROJECTION = {
			"address",
			"read",
			"date",
			"body",
			"thread_id"
	};

	private String address; // phone number
	private String read;
	private String date;
	private String body;
	private String threadId;
	private String name;
	private Context context;

	public Thread(Context context, Cursor c) {
		this.context = context;
		this.address = c.getString(c.getColumnIndex("address"));
		this.read = c.getString(c.getColumnIndex("read"));
		this.date = c.getString(c.getColumnIndex("date"));
		this.body = c.getString(c.getColumnIndex("body"));
		this.threadId = c.getString(c.getColumnIndex("thread_id"));
		name = Contact.getNameFromPhoneNumber(context, this.address);
	}

	public Date getDateObject() {return new Date(Long.parseLong(date));}
	public String getThreadId() {return this.threadId;}
	public String getName() {return name;}
	public String getAddress() {return address;}
	public String getBody() {return body;}

	public String toString() {
		String output = "";

		output += "name: " + this.name + "\n";
		output += "address: " + this.address + "\n";
		output += "body: " + this.body + "\n";
		output += "read: " + this.read + "\n";
		output += "date: " + getDateObject();

		return output;
	}

//	private void retrievePicture() {
//		Uri u = getPhotoUri();
//		if (u != null) {
////			mPhotoView.setImageURI(u);
//		} else {
////			mPhotoView.setImageResource(R.drawable.ic_contact_picture_2);
//		}
//	}
//	/**
//	 * @return the photo URI
//	 */
//	public Uri getPhotoUri() {
//		try {
//			Cursor cur = this.context.getContentResolver().query(
//					ContactsContract.Data.CONTENT_URI,
//					null,
//					ContactsContract.Data.CONTACT_ID + "=" + this.getId() + " AND "
//							+ ContactsContract.Data.MIMETYPE + "='"
//							+ ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'", null,
//					null);
//			if (cur == null) {
//				return null; // error in cursor process
//			} else if (!cur.moveToFirst()) {
//				return null; // no photo
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//		Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long
//				.parseLong(getId()));
//		return Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
//	}
}
