package ag.ifpb.pdm;

import com.pdm.jsInterface.WebAppInterface;
import com.pdm.service.CreateRequests;

import ag.ifpb.pdm.service.FBConfig;
import ag.ifpb.pdm.service.FBService;
import ag.ifpb.pdm.service.impl.FBServiceImpl;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends Activity {
	private LinearLayout container;
	private static FBService service;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  //
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
	  //
	  container = new LinearLayout(this);
	  container.setLayoutParams(
	  		new LayoutParams(
	  				LayoutParams.MATCH_PARENT,
	  				LayoutParams.MATCH_PARENT
	  		)
	  );
	  container.setOrientation(LinearLayout.VERTICAL);
	  //
	  final WebView webView = new WebView(this);
	  webView.getSettings().setJavaScriptEnabled(true);
	  
	  webView.addJavascriptInterface(new WebAppInterface(this), "WV");
	  
	  webView.setWebViewClient(new WebViewClient(){
	  		@Override
	  		public boolean shouldOverrideUrlLoading(WebView view, String url) {
	  		  view.loadUrl(url);
	  		  return true;
	  		}
	  		
	  		public void onPageFinished(WebView view, String url) {
	  	        if(url.startsWith("https://ifpbtasks.herokuapp.com")){
					String token = service.extractAccessToken(url);
	  	        	view.loadUrl("file:///android_asset/index.html?token="+token);
	  	        	
	  	        	//CreateRequests.getUserFriendList(token);
	  	        }
	  	    }
	  		
	  });
	  webView.loadData("<form action='http://google.com.br'><button type='submit'>enviar</button></form>", "text/html", "UTF-8");
	  LayoutParams lp1 = new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT
		);
	  lp1.weight = 8;
	  webView.setLayoutParams(lp1);
	  container.addView(webView);
	  //
	  Button btnlogin = new Button(this);
	  btnlogin.setText("Login");
	  btnlogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String url = service.createLoginUrl();
				webView.loadUrl(url);
			}
		});
	  //
	  Button btnrecover = new Button(this);
	  btnrecover.setText("Recuperar Token");
	  btnrecover.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String url = webView.getUrl();
				String token = service.extractAccessToken(url);
				Log.e("AGDebug", token);
				
				
				
			}
		});
	  //
	  LinearLayout btnpanel = new LinearLayout(this);
	  btnpanel.setOrientation(LinearLayout.HORIZONTAL);
	  btnpanel.setVerticalGravity(Gravity.CENTER);
	  btnpanel.setHorizontalGravity(Gravity.CENTER);
	  LayoutParams lp2 = new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT
		);
	  lp2.weight = 2;
	  btnpanel.setLayoutParams(lp2);
	  btnpanel.addView(btnlogin);
	  btnpanel.addView(btnrecover);
	  container.addView(btnpanel);
	  //
	  setContentView(container);
	}
	
	public static String sendMsg1(String msg){
		return service.sendMessage(msg);
	}
	

}
