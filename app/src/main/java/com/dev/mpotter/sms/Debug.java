package com.dev.mpotter.sms;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 2/4/16.
 */
public class Debug extends AppCompatActivity {
	private static final String phoneNumber = "4253508358";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_debug);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		Uri uri = Uri.parse("content://mms-sms/conversations");
//		Uri uri = Uri.parse("content://sms/inbox");
		Cursor conversationCursor = getContentResolver().query(uri, null, null, null, null);

		Uri contactsUri = ContactsContract.Contacts.CONTENT_URI;
		Cursor contactsCursor = getContentResolver().query(contactsUri, null, null, null, null);

		Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
				Uri.encode(phoneNumber));
		Cursor phoneNumCursor = getContentResolver().query(lookupUri, null, null, null, null);

		printTable(phoneNumCursor);
	}

	public List<Thread> printTable(Cursor c) {

		List<Thread> list = new ArrayList<>();
		TextView t = (TextView) findViewById(R.id.text_view);
		TextView t2 = (TextView) findViewById(R.id.text_view2);

		t.append(c.getColumnCount() + "columns\n");
		// Print column definitions
		for (int j = 0; j < c.getColumnCount(); j++) {
			t.append(j + ") " + c.getColumnName(j));
			t.append("\n");
		}
		t.append("\n");
		t2.append(c.getCount() + "threads\n");
		t.append("\n");

		// Print each row
		if (c.moveToFirst()) {
			for (int i = 0; i < c.getCount(); i++) {
				t2.append("Thread #" + i + "\n");
				for (int j = 0; j < c.getColumnCount(); j++) {
					if (c.getString(j) != null)
						t2.append(j + ") " + c.getString(j) + "\n");
				}
				t2.append("\n");
				c.moveToNext();
			}
		}
		return list;
	}
}
