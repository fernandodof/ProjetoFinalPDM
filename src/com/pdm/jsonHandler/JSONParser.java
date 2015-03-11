package com.pdm.jsonHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {
	
	private static InputStream inputStream = null;
	
	public static JSONArray getJsonArrayFromHttpResponse(HttpResponse response){
		HttpEntity httpEntity = response.getEntity();

	    try {
	    	Log.e("Parser", "TESTE PARSER");
	    	
			inputStream = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder sb = new StringBuilder();
		    String line = null;
			while ((line = reader.readLine()) != null) {
			    sb.append(line + "n");
			}
			inputStream.close();
			 
			String json = sb.toString();
			JSONArray jsonArray = new JSONArray(json);
			return jsonArray;
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static JSONObject getJsonObjectFromHttpResponse(HttpResponse response){
		HttpEntity httpEntity = response.getEntity();

	    try {
	    	Log.e("Parser", "TESTE PARSER");
	    	
			inputStream = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder sb = new StringBuilder();
		    String line = null;
			while ((line = reader.readLine()) != null) {
			    sb.append(line + "n");
			}
			inputStream.close();
			 
			String json = sb.toString();
			JSONObject jsonObject = new JSONObject(json);
			return jsonObject;
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
