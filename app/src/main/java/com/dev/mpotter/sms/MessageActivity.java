package com.dev.mpotter.sms;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.lang.*;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity showing all messages in a conversation with a single person
 */
public class MessageActivity extends AppCompatActivity {
	private String mThreadId;
	private String number;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				EditText messageBox = (EditText) findViewById(R.id.message_box);
				String message = messageBox.getText().toString();
				sendMessage(message);
				messageBox.setText("");
			}
		});
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mThreadId = getIntent().getStringExtra("thread_id");

		MessageResolver messageResolver = new MessageResolver(this, mThreadId);
		List<SMS> smsList = messageResolver.resolveMessages(20);
		this.number = smsList.get(0).getNumber();
		ArrayAdapter<SMS> adapter =
//				new ArrayAdapter<SMS>(this, android.R.layout.simple_list_item_1, smsList);
				new ListAdapter(this, smsList);
		ListView listView = (ListView) findViewById(R.id.thread_list_view);
		listView.setAdapter(adapter);
		if (getIntent().getStringExtra("name") != null) {
			getSupportActionBar().setTitle(getIntent().getStringExtra("name"));
		} else {
			getSupportActionBar().setTitle(getIntent().getStringExtra("number"));
		}
	}

	private void sendMessage(String message) {
		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(number, null, message, null, null);
	}
}
