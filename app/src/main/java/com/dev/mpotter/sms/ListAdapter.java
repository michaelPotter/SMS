package com.dev.mpotter.sms;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dev.mpotter.sms.R;
import com.dev.mpotter.sms.SMS;


/**
 * List adapter for storing SMS data
 *
 * @author itcuties
 *
 */
public class ListAdapter extends ArrayAdapter<SMS> {

	// List context
	private final Context context;
	// List values
	private final List<SMS> smsList;

	public ListAdapter(Context context, List<SMS> smsList) {
		super(context, R.layout.activity_main, smsList);
		this.context = context;
		this.smsList = smsList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.message_list_item, parent, false);
		TextView senderText = (TextView) view.findViewById(R.id.sender);
		TextView messageText = (TextView) view.findViewById(R.id.message);

		senderText.setText(getItem(position).getSender());
		messageText.setText(getItem(position).getBody());

		if(getItem(position).isOutgoing()) {
			senderText.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
			messageText.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
		}

//		TextView senderNumber = (TextView) rowView.findViewById(R.id.smsNumberText);
//		senderNumber.setText(smsList.get(position).getNumber());

		return view;
	}

}
