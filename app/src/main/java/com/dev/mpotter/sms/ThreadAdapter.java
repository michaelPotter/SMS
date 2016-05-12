package com.dev.mpotter.sms;

import java.util.List;

import android.content.Context;
import android.graphics.Outline;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.mpotter.sms.R;
import com.dev.mpotter.sms.SMS;


/**
 * List adapter for storing SMS data
 *
 * @author itcuties
 *
 */
public class ThreadAdapter extends ArrayAdapter<Thread> {

	// List context
	private final Context context;
	// List values
	private final List<Thread> threadList;

	public ThreadAdapter(Context context, List<Thread> threadList) {
		super(context, R.layout.activity_main, threadList);
		this.context = context;
		this.threadList = threadList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.thread_list_item, parent, false);

		TextView sender = (TextView) view.findViewById(R.id.name);
		TextView body = (TextView) view.findViewById(R.id.body);
		ImageView pic = (ImageView) view.findViewById(R.id.avatar_icon);

		sender.setText(getItem(position).getName());
		if (getItem(position).getName() == null) {
			// use the number instead
			sender.setText(getItem(position).getAddress());
		}
		body.setText(getItem(position).getBody());

//		TextView senderNumber = (TextView) rowView.findViewById(R.id.smsNumberText);
//		senderNumber.setText(smsList.get(position).getNumber());

		return view;
	}

	/**
	 * A {@link ViewOutlineProvider} which clips the view with a rounded rectangle which is inset
	 * by 10%
	 */
	private class ClipOutlineProvider extends ViewOutlineProvider {

		@Override
		public void getOutline(View view, Outline outline) {
			final int margin = Math.min(view.getWidth(), view.getHeight()) / 10;
			outline.setRoundRect(margin, margin, view.getWidth() - margin,
					view.getHeight() - margin, margin / 2);
		}

	}

}
