package com.example.activity;

import java.net.URI;
import java.net.URL;

import com.example.test.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	private EditText num_edit;
	private Button fragment_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		num_edit = (EditText) findViewById(R.id.num_edit);
		fragment_btn = (Button) findViewById(R.id.fragment_btn);
		fragment_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						ViewPagerFragment.class);
				startActivity(intent);
			}
		});
	}

	public void onClick(View view) {
		Intent intent = new Intent();
		String tel = num_edit.getText().toString();
		intent.setAction(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:" + tel));
		startActivity(intent);
	}

}
