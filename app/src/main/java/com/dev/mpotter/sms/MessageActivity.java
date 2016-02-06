package com.dev.mpotter.sms;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
	private String mThreadId;

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
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mThreadId = getIntent().getStringExtra("thread_id");

		List<SMS> smsList = resolveMessages(mThreadId);
		ArrayAdapter<SMS> adapter =
//				new ArrayAdapter<SMS>(this, android.R.layout.simple_list_item_1, smsList);
				new ListAdapter(this, smsList);
		ListView listView = (ListView) findViewById(R.id.thread_list_view);
		listView.setAdapter(adapter);
		getSupportActionBar().setTitle(getIntent().getStringExtra("name"));
	}

	public List<SMS> resolveMessages(String threadId) {
		Uri uri = Uri.parse("content://sms");
		Cursor c = getContentResolver().query(uri, SMS.PROJECTION, "thread_id = " + threadId, null, null);

		List<SMS> list = new ArrayList<>();

		if (c.moveToFirst()) {
			for (int i = 0; i < c.getCount(); i++) {
				list.add(new SMS(this, c));
				c.moveToNext();
			}
		}
		return list;
	}


}
