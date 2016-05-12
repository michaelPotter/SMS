package com.dev.mpotter.sms;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by michael on 4/28/16.
 */
public class MessageResolver {
	Context mContext;
	Cursor inboxCursor;
	Cursor sentCursor;

	public MessageResolver(Context context, String threadId) {
		mContext = context;
		Uri uri = Uri.parse("content://sms/inbox");
		inboxCursor = mContext.getContentResolver().query(uri, SMS.PROJECTION, "thread_id = " + threadId, null, "date desc");
		inboxCursor.moveToFirst();

		Uri sentUri = Uri.parse("content://sms/sent");
		sentCursor = mContext.getContentResolver().query(sentUri, SMS.PROJECTION, "thread_id = " + threadId, null, "date desc");
		sentCursor.moveToFirst();
	}
	public List<SMS> resolveMessages(int number) { // make this asynchronous
		List<SMS> list = new LinkedList<>();
		int i = 0; // easier to start at 1 since adding to list backwards for optimization

		while (!inboxCursor.isAfterLast() && !sentCursor.isAfterLast() && i < number) {
			SMS sent = new SMS(mContext, sentCursor, SMS.SENT);
			SMS received = new SMS(mContext, inboxCursor, SMS.RECEIVED);
			if (Long.parseLong(sent.getDate()) > Long.parseLong(received.getDate())) {
				list.add(0, sent);
				sentCursor.moveToNext();
			} else {
				list.add(0, received);
				inboxCursor.moveToNext();
			}
			i++;
		}
		while (!inboxCursor.isAfterLast() && i < number) {
			list.add(0, new SMS(mContext, inboxCursor, SMS.RECEIVED));
			inboxCursor.moveToNext();
			i++;
		}
		while (!sentCursor.isAfterLast() && i < number) {
			list.add(0, new SMS(mContext, sentCursor, SMS.SENT));
			sentCursor.moveToNext();
			i++;
		}

		return list;
	}
}
