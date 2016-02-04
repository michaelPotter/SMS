package com.dev.mpotter.sms;

import android.app.Application;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 2/4/16.
 */
public class Debug extends AppCompatActivity {
	public List<Thread> testThreads() {
		Uri uri = Uri.parse("content://mms-sms/conversations");
		Cursor c = getContentResolver().query(uri, null, null, null, null);

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
