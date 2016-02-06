package com.dev.mpotter.sms;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.inflateMenu(R.menu.menu_main);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});

		List<Thread> threadList = resolveThreads();

		ArrayAdapter<Thread> threadAdapter =
				new ArrayAdapter<Thread>(this, android.R.layout.simple_list_item_1, threadList);

		ListView listView = (ListView) findViewById(R.id.list_view);
		listView.setAdapter(threadAdapter);
		listView.setOnItemClickListener(this);
	}

	public List<Thread> resolveThreads() {
		Uri uri = Uri.parse("content://mms-sms/conversations");
		Cursor c = getContentResolver().query(uri, Thread.PROJECTION, null, null, "date desc");

		List <Thread> list = new ArrayList<>();

		if (c.moveToFirst()) {
			for (int i = 0; i < c.getCount(); i++) {
				list.add(new Thread(this, c));
				c.moveToNext();
			}
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.debug_menu_item:
				startActivity(new Intent(this, Debug.class));
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(this, MessageActivity.class);
		ListView list = (ListView) parent;
		Thread thread = (Thread) list.getItemAtPosition(position);
		intent.putExtra("thread_id", thread.getThreadId());
		intent.putExtra("number", thread.getAddress());
		intent.putExtra("name", thread.getName());
		startActivity(intent);
	}

}
