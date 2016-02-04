package com.dev.mpotter.sms;

import android.database.Cursor;

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

	public Thread(Cursor c) {
		this.address = c.getString(c.getColumnIndex("address"));
		this.read = c.getString(c.getColumnIndex("read"));
		this.date = c.getString(c.getColumnIndex("date"));
		this.body = c.getString(c.getColumnIndex("body"));
		this.threadId = c.getString(c.getColumnIndex("thread_id"));
	}

	public Date getDateObject() {return new Date(Long.parseLong(date));}

	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("address: " + this.address + "\n");
		builder.append("read: " + this.read + "\n");
		builder.append("date: " + getDateObject() + "\n");
		builder.append("body: " + this.body + "\n");
		builder.append("threadId: " + this.threadId + "\n");

		return builder.toString();
	}
}
