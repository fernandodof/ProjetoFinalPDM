package com.pdm.jsInterface;

import com.pdm.service.CreateRequests;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class WebAppInterface {
	Context ctx;
	
	public WebAppInterface(Context c) {
		ctx = c;
	}
	
	@JavascriptInterface
	public void showToast(String toast){
		Toast.makeText(ctx, toast, Toast.LENGTH_SHORT).show();
	}
	
	@JavascriptInterface
	public String createUser(String id, String name, String accessToken){
		return CreateRequests.createUser(id, name, accessToken).toString();
	}
	
	@JavascriptInterface
	public String getUserFriendList(String token){
		return CreateRequests.getUserFriendList(token);
	}

}
