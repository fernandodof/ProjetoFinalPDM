package com.pdm.jsInterface;

import com.pdm.service.CreateRequests;

import ag.ifpb.pdm.service.FBConfig;
import ag.ifpb.pdm.service.FBService;
import ag.ifpb.pdm.service.impl.FBServiceImpl;
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

	@JavascriptInterface
	public String sendMessage(String msg){
		Log.e("DEBUG", msg+" inicio");
		FBService service;
		service = new FBServiceImpl(new FBConfig(){
		      @Override
		      public String getClientId() {
		          return "734863696634478";
		      }
		      @Override
		      public String getRedirectUri() {
		          return "https://ifpbtasks.herokuapp.com";
		      }
		      @Override
		      public String getScope() {
		          return "email,user_friends,publish_actions";
		      }
			  });
		service.sendMessage(msg);
		
		Log.e("DEBUG", msg);
		
		return "OK";
	}
	
	
}
