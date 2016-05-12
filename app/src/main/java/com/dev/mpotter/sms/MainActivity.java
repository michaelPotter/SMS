package com.dev.mpotter.sms;

import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.MetadataChangeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Lists a summary of all threads
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
		OnRequestPermissionsResultCallback, GoogleApiClient.ConnectionCallbacks , GoogleApiClient.OnConnectionFailedListener
{
	private static final String TAG = "main-activity";
	GoogleApiClient mGoogleApiClient;

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

		Permissions.requestPermission(this, Permissions.READ_CONTACTS);
//		Permissions.requestPermission(this, Permissions.READ_SMS);

		if (Permissions.checkPermission(this, Permissions.READ_CONTACTS) &&
				Permissions.checkPermission(this, Permissions.READ_SMS)) {
			populateUI();
		}

		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addApi(Drive.API)
				.addScope(Drive.SCOPE_FILE)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.build();
	}

	@Override
	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}

	public void populateUI() {
		List<Thread> threadList = resolveThreads();

		ArrayAdapter<Thread> threadAdapter =
//					new ArrayAdapter<Thread>(this, android.R.layout.simple_list_item_1, threadList);
				new ThreadAdapter(this, threadList);

		ListView listView = (ListView) findViewById(R.id.list_view);
		listView.setAdapter(threadAdapter);
		listView.setOnItemClickListener(this);
	}

	/**
	 * Check database and return a list of Thread objects
	 * @return
	 */
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

	/**
	 * When a thread is chosen, open it
	 * @param parent
	 * @param view
	 * @param position
	 * @param id
	 */
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(this, MessageActivity.class);
		ListView list = (ListView) parent;
		Thread thread = (Thread) list.getItemAtPosition(position);
		intent.putExtra("thread_id", thread.getThreadId());
		intent.putExtra("number", thread.getAddress());
		intent.putExtra("name", thread.getName());
		startActivity(intent);
		makeFile();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		populateUI();
	}

	@Override
	public void onConnected(Bundle bundle) {
		Log.i(TAG, "Connection Connected");
	}

	@Override
	public void onConnectionSuspended(int i) {
		Log.i(TAG, "Connection Suspended");
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		Log.i(TAG, "GoogleApiClient connection failed: " + connectionResult.toString());
		if (connectionResult.hasResolution()) {
			try {
				connectionResult.startResolutionForResult(this, 0);
			} catch (IntentSender.SendIntentException e) {
				// Unable to resolve, message user appropriately
			}
		} else {
			GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, 0).show();
		}
	}

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		switch (requestCode) {
			case 0:
				if (resultCode == RESULT_OK) {
					mGoogleApiClient.connect();
				}
				break;
		}
	}


	public void makeFile() {
		Drive.DriveApi.newDriveContents(mGoogleApiClient)
                .setResultCallback(new
						ResultCallback<DriveApi.DriveContentsResult>() {
							@Override
							public void onResult(DriveApi.DriveContentsResult result) {
								if (!result.getStatus().isSuccess()) {
									// Handle error
									return;
								}

								MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
										.setTitle("New file")
										.setMimeType("text/plain").build();
								// Create a file in the root folder
								Drive.DriveApi.getRootFolder(mGoogleApiClient)
										.createFile(mGoogleApiClient, changeSet, result.getDriveContents())
										.setResultCallback(null);
							}
						});
	}
}
