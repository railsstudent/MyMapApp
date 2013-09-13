package com.blueskyconnie.mymapapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AboutUsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		
		TextView tvBlog = (TextView) this.findViewById(R.id.tvBlogValue);
		tvBlog.setMovementMethod(LinkMovementMethod.getInstance());

		final String strPhoneNum = this.getString(R.string.phoneValue1).trim();
		final String strEmail = this.getString(R.string.emailValue).trim();
		OnClickListener listener = new OnClickListener() {
			public void onClick(View view) {
				switch (view.getId()) {
					case R.id.btnPhoneCall:
						Intent itDial = new Intent();
						itDial.setAction(Intent.ACTION_CALL);
						itDial.setData(Uri.parse("tel:" + strPhoneNum));
						startActivity(itDial);
						break;
					case R.id.btnSendEmail:
						Intent itEmail = new Intent();
						itEmail.setAction(Intent.ACTION_VIEW);
						itEmail.setData(Uri.parse("mailto:" + strEmail));
						itEmail.putExtra(Intent.EXTRA_SUBJECT, "Hello");
						itEmail.putExtra(Intent.EXTRA_TEXT, "Good Work!!!");
						try {
							startActivity(Intent.createChooser(itEmail, "Choose Email Provider"));
						} catch (ActivityNotFoundException ex) {
							Toast.makeText(AboutUsActivity.this, "There are no email clients installed.", 
									Toast.LENGTH_SHORT).show();
						}
						break;
					case R.id.btnSendSms:
						Intent itSms = new Intent();
						itSms.setAction(Intent.ACTION_VIEW);
						itSms.setData(Uri.parse("sms:" + strPhoneNum.trim()));
						itSms.putExtra("sms_body", "Good Work!!!");
						try {
							startActivity(itSms);
						} catch (ActivityNotFoundException ex) {
							Toast.makeText(AboutUsActivity.this, "SMS service is not provided.", 
									Toast.LENGTH_SHORT).show();
						}
						break;
				}
			}
		};
		
		Button btnDial = (Button)findViewById(R.id.btnPhoneCall);
		btnDial.setOnClickListener(listener);

		Button btnSendEmail = (Button)findViewById(R.id.btnSendEmail);
		btnSendEmail.setOnClickListener(listener);

		Button btnSendSms = (Button)findViewById(R.id.btnSendSms);
		btnSendSms.setOnClickListener(listener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

}
