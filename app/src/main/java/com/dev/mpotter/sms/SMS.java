package com.dev.mpotter.sms;

import android.database.Cursor;

import java.util.Date;

/**
 * Created by michael on 2/4/16.
 */
public class SMS {
	private String id;
	private String threadId;
	private String address;
	private String person;
	private String date;
	private String dateSent;
	private String protocol;
	private String read;
	private String status;
	private String type;
	private String reply_path_present;
	private String subject;
	private String body;
	private String service_center;
	private String locked;
	private String sub_id;
	private String phone_id;
	private String error_code;
	private String creator;
	private String seen;
	private String priority;

	public static final String[] PROJECTION = {
			"body",
			"_id",
			"thread_id",
			"address",
			"date",
			"person",
			"phone_id",
			"read"
	};

	public SMS() {

	}

	public SMS(Cursor c) {
		this.body = c.getString(c.getColumnIndexOrThrow("body"));
		this.id = c.getString(c.getColumnIndexOrThrow("_id"));
		this.threadId = c.getString(c.getColumnIndexOrThrow("thread_id"));
		this.address = c.getString(c.getColumnIndexOrThrow("address"));
		this.person = c.getString(c.getColumnIndexOrThrow("person"));
		this.date = c.getString(c.getColumnIndexOrThrow("date"));
		this.phone_id = c.getString(c.getColumnIndexOrThrow("phone_id"));
	}

	public void setId(String id) {this.id = id;}

	public String getId(String id) {return this.id;}

	public void setBody(String body) {this.body = body;}

	public String getBody() {return this.body;}

	public void setNumber(String address) {this.address = address;}

	public String getNumber() {return this.address;}

	public void setDate(String date) {this.date = date;}

	public String getDate() {return this.date;}

	public Date getDateObject() {return new Date(Long.parseLong(this.date));}

	public String toString() {
		String output = "";
		output += "Body: " + body + "\n";
		output += "id: " + id + "\n";
		output += "Threadid: " + threadId + "\n";
		output += "address: " + address + "\n";
		output += "person: " + person + "\n";
		output += "date: " + getDateObject() + "\n";
		output += "phone_id: " + phone_id + "\n";
		output += "\n";
		return output;
	}
}
