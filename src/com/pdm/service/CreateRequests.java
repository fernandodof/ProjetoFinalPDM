package com.pdm.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.util.Log;

import com.pdm.jsonHandler.JSONParser;

public class CreateRequests {
	private final static String ROOT_URL = "http://ifpbtasks.herokuapp.com/";
	private final static String USER_AGENT = "Mozilla/5.0";
	
	public static JSONObject createUser(String id, String name, String accessToken){
		String url = ROOT_URL+"/users";
		try {
			URL obj = new URL(url);
			
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			
			post.setHeader("User-Agent", USER_AGENT);
			
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("facebook_id", id));
			urlParameters.add(new BasicNameValuePair("name", name));
			urlParameters.add(new BasicNameValuePair("accessToken", id));
			
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
			
			HttpResponse response = httpClient.execute(post);
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + post.getEntity());
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			
			return JSONParser.getJsonObjectFromHttpResponse(response);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//			JSONObject errorObject = new JSONObject("{success : false}");
		
		return null;

		
	}
	

	public static String getUserFriendList(String token){
		HttpClient httpclient = new DefaultHttpClient();

		Log.e("DEBUG", "getUserFriendList()");
		
		String url = ROOT_URL+"users/"+token+"/userFriendList";
		
		HttpGet httpGet = new HttpGet(url); 
	    httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
	    
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpGet);
			
			JSONParser jsonParser = new JSONParser();
			
			JSONArray jsonArray = jsonParser.getJsonArrayFromHttpResponse(response);
			
			return jsonArray.toString();
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "[]";
		
	}
	
}
