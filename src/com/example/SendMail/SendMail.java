package com.example.SendMail;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendMail extends Activity implements OnClickListener {
	
	//login
	EditText address;
	EditText password;
	CheckBox rememberme;
	Button login;
	
	//tabs
	TextView inbox;
	TextView compose;
	
	//compose
	EditText to;
	EditText subject;
	EditText body;
	Button send;
	
	public static final String PREFS_NAME = "MyAccountsFile";
	
	public void onCreate(Bundle icicle) {
		
		super.onCreate(icicle);
		setContentView(R.layout.login);		//login.xml
		
		login = (Button) this.findViewById(R.id.buttonlogin);
		address = (EditText) this.findViewById(R.id.fieldaddress);
		password = (EditText) this.findViewById(R.id.fieldpassword);
		rememberme = (CheckBox) this.findViewById(R.id.chckboxremember);
		    
		// Restore preferences
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		String prev_username = settings.getString("username", null);
		String prev_password = settings.getString("password", null);
		
		address.setText(prev_username);
		password.setText(prev_password);
		//Toast.makeText(getApplicationContext(), prev_username+", "+prev_password, Toast.LENGTH_LONG).show();
		
		login.setOnClickListener(this);   
	}
	
	public void onClick(View v) {
		if (v.equals(login) || v.equals(compose)){
			
			//Store the data
			if (rememberme.isChecked()){
				
				final String user = address.getText().toString();
			    final String pass = password.getText().toString();
			    
				// Save username and password as preferences 
				SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("username", user);
				editor.putString("password", pass);
				editor.commit();
			    
			 	//toast notification
			    //Toast.makeText(getApplicationContext(), user+" "+pass+"", Toast.LENGTH_LONG).show();
			}
			
			setContentView(R.layout.main);
			send = (Button) this.findViewById(R.id.buttonsend);
			to = (EditText) this.findViewById(R.id.fieldto);
			subject = (EditText) this.findViewById(R.id.fieldsubject);
			body = (EditText) this.findViewById(R.id.fieldbody);
			inbox = (TextView) this.findViewById(R.id.tabinbox);
			inbox.setOnClickListener(this);
			send.setOnClickListener(this);
		}
		else if (v.equals(send)) {
			
			final String user = address.getText().toString();
		    final String pass = password.getText().toString();
		    GMailSender sender = new GMailSender(user, pass);
		    
			try {
				sender.sendMail(
					subject.getText().toString(), 
					body.getText().toString(), 
					user,
					to.getText().toString()
				);
				Toast.makeText(getApplicationContext(), "Email Sent Successfully", Toast.LENGTH_LONG).show();
			}
			catch (Exception e) {
				Log.e("SendMail", e.getMessage(), e);
				Toast.makeText(getApplicationContext(), "Problem Occured", Toast.LENGTH_LONG).show();
			}
		}
		else if (v.equals(inbox)) {
			setContentView(R.layout.inbox);
			compose = (TextView) this.findViewById(R.id.tabcompose);
			compose.setOnClickListener(this);
		}
	}
}