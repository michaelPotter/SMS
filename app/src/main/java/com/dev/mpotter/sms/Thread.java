package com.dev.mpotter.sms;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.Date;

/**
 * Created by michael on 2/4/16.
 */
public class Thread {

	public static final String[] PROJECTION = {
			"address",
			"read",
			"date",
			"body",
			"thread_id"
	};

	private String address;
	private String read;
	private String date;
	private String body;
	private String threadId;
	private String name;

	public Thread(Context context, Cursor c) {
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

	public String toString() {
		String output = "";

		output += "name: " + this.name + "\n";
		output += "address: " + this.address + "\n";
		output += "body: " + this.body + "\n";
		output += "read: " + this.read + "\n";
		output += "date: " + getDateObject();

		return output;
	}
}
