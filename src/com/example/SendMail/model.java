package com.example.SendMail;


import org.apache.http.auth.UsernamePasswordCredentials;


public class model {
	
	String username;
	String password;
	public static final String PREFS_NAME = "MyAccountsFile";
	
	public model(String username, String password) {
		
		this.username = username;
		this.password = password;
	}
	public String store(){
		
		UsernamePasswordCredentials accounts = new UsernamePasswordCredentials(this.username, this.password);
		//accounts.getUserName()
		
		
	    //save data preferences
		
		
		return accounts.toString();
	}
}
