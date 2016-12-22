package com.veggy;

import static android.R.attr.shape;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import static android.graphics.Color.WHITE;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Gravity;
import static android.view.Gravity.CENTER;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpClient;
import java.text.SimpleDateFormat;

public class MainActivity extends Activity {

	//global
	static Context context;
	int ScreenW;
	int ScreenH;
	WindowManager.LayoutParams WRAP_HEIGHT;
	WindowManager.LayoutParams MATCH_PARENT;
	private ViewGroup.LayoutParams params;
	private float corners;
	private int editH;
	AsyncHttpClient HttpClient;
	RequestParams TagParams;
	public String data_source_url = "http://sukapich.com/veggy/connect.php";

	/**
	 * Called when the activity is first created.
	 *
	 * @param savedInstanceState
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		ScreenW = displaymetrics.widthPixels;
		ScreenH = displaymetrics.heightPixels;
		WRAP_HEIGHT = new WindowManager.LayoutParams(
				  WindowManager.LayoutParams.MATCH_PARENT,
				  WindowManager.LayoutParams.WRAP_CONTENT);
		MATCH_PARENT = new WindowManager.LayoutParams(
				  WindowManager.LayoutParams.MATCH_PARENT,
				  WindowManager.LayoutParams.MATCH_PARENT);

		context = getApplicationContext();
		HttpClient = new AsyncHttpClient(); //object for connect web service
		TagParams = new RequestParams(); //data for show
		this.setContentView(signinScreen(context));

	}

	private LinearLayout signinScreen(final Context context) {
		//final=announce paramitor, don't edit  Context=Library name  context=paramitor name
		LinearLayout Screen = new LinearLayout(context);
		Screen.setLayoutParams(MATCH_PARENT);
		Screen.setBackgroundResource(R.color.light_green);
		Screen.setOrientation(LinearLayout.VERTICAL);
		Screen.setGravity(Gravity.CENTER);

		LinearLayout LL = new LinearLayout(context);
		LL.setLayoutParams(MATCH_PARENT);
		LL.setBackgroundResource(R.color.light_green);
		LL.setOrientation(LinearLayout.HORIZONTAL);
		LL.setGravity(Gravity.CENTER);

		//image logo
		ImageView imgLogo2 = new ImageView(context);
		imgLogo2.setLayoutParams(
				  new LinearLayout.LayoutParams(
							 ScreenW / 2, ScreenW / 2
				  ));
		imgLogo2.setBackgroundResource(R.drawable.sms_icon1);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				  (int) (ScreenW * 0.6),
				  LinearLayout.LayoutParams.WRAP_CONTENT
		);
		int marginTop = (int) (ScreenH * 0.01);
		int marginBottom = (int) (ScreenH * 0.01);
		params.setMargins(0, marginTop, 0, marginBottom);

		int corners = (int) (ScreenH * 0.3); //ความโค้ค้  ง
		GradientDrawable shape = new GradientDrawable();
		shape.setCornerRadius(corners);
		shape.setStroke(1, Color.rgb(0xff, 0x9c, 0x40));
		shape.setColor(Color.WHITE);

		int editH = (int) (ScreenH * 0.1);
		final EditText username = new EditText(context);
		final EditText password = new EditText(context);
		username.setHeight(editH);
		password.setHeight(editH);

		username.setLayoutParams(params);
		username.setGravity(Gravity.CENTER);
		username.setBackground(shape);
		username.setHintTextColor(Color.LTGRAY);
		username.setHint("USER NAME");

		password.setLayoutParams(params);
		password.setGravity(Gravity.CENTER);
		password.setInputType(
				  InputType.TYPE_CLASS_TEXT
				  | InputType.TYPE_TEXT_VARIATION_PASSWORD
		);
		password.setBackground(shape);
		password.setHintTextColor(Color.LTGRAY);
		password.setHint("PASS WORD");

		Button signin = new Button(context);
		signin.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				TagParams.put("function", "signin");
				TagParams.put("username", username.getText());
				TagParams.put("password", password.getText());
				HttpClient.post(context, data_source_url, TagParams, AsyncHandler()); //connect
			}
		});

		//Button login2 = new Button(context);
		//LL.addView(login);
		//LL.addView(login2);
		Screen.addView(username);
		Screen.addView(password);
		Screen.addView(signin); //insert btn to screen

		return Screen;

	}

	public LinearLayout signupScreen(final Context context) {

		LinearLayout Screen = new LinearLayout(context);
		Screen.setLayoutParams(MATCH_PARENT);
		Screen.setBackgroundResource(R.color.light_green);
		Screen.setOrientation(LinearLayout.VERTICAL);
		Screen.setGravity(Gravity.CENTER);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				  (int) (ScreenW * 0.6),
				  LinearLayout.LayoutParams.WRAP_CONTENT
		);
		int marginTop = (int) (ScreenH * 0.01);
		int marginBottom = (int) (ScreenH * 0.01);
		params.setMargins(0, marginTop, 0, marginBottom);

		int corners = (int) (ScreenH * 0.3); //ความโค้ค้  ง
		GradientDrawable shape = new GradientDrawable();
		shape.setCornerRadius(corners);
		shape.setStroke(1, Color.MAGENTA);
		shape.setColor(Color.WHITE);

		int editH = (int) (ScreenH * 0.1);
		final EditText username = new EditText(context);
		final EditText password = new EditText(context);
		final EditText email = new EditText(context);
		final EditText image = new EditText(context);
		Button btnAdd;

		username.setHeight(editH);
		password.setHeight(editH);
		email.setHeight(editH);
		image.setHeight(editH);
		btnAdd = new Button(context);

		username.setLayoutParams(params);
		username.setGravity(Gravity.CENTER);
		username.setBackground(shape);
		username.setHintTextColor(Color.LTGRAY);
		username.setHint("Username");

		password.setLayoutParams(params);
		password.setGravity(Gravity.CENTER);
		password.setBackground(shape);
		password.setHintTextColor(Color.LTGRAY);
		password.setHint("Password");

		email.setLayoutParams(params);
		email.setGravity(Gravity.CENTER);
		email.setBackground(shape);
		email.setHintTextColor(Color.LTGRAY);
		email.setHint("email");

		image.setLayoutParams(params);
		image.setGravity(Gravity.CENTER);
		image.setBackground(shape);
		image.setHintTextColor(Color.LTGRAY);
		image.setHint("image");

//		btnAdd.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View view) {
//				Intent intent = new Intent(Intent.ACTION_PICK,
//						  android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//				startActivityForResult(intent, 1);
//			}
//		});
		Button signup = new Button(context);
		signup.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				//setContentView(homeScreen(context));
				TagParams.put("function", "signup");
				TagParams.put("username", username.getText());
				TagParams.put("password", password.getText());
				TagParams.put("email", email.getText());
				TagParams.put("image", image.getText());
				HttpClient.post(context, data_source_url, TagParams, AsyncHandler()); //connect
			}
		});

		Screen.addView(username);
		Screen.addView(password);
		Screen.addView(email);
		Screen.addView(image);
		Screen.addView(signup);

		return Screen;
	}

	public LinearLayout homeScreen(final Context context) {

		LinearLayout Screen = new LinearLayout(context);
		Screen.setLayoutParams(MATCH_PARENT);
		Screen.setBackgroundResource(R.color.black);
		Screen.setOrientation(LinearLayout.VERTICAL);
		Screen.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);

		LinearLayout LL = new LinearLayout(context);
		LL.setLayoutParams(MATCH_PARENT);
		LL.setBackgroundResource(R.color.black);
		LL.setOrientation(LinearLayout.HORIZONTAL);
		LL.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				  (int) (ScreenW * 0.33),
				  LinearLayout.LayoutParams.WRAP_CONTENT
		);
		int marginTop = (int) (ScreenH * 0.01);
		int marginBottom = (int) (ScreenH * 0.01);
		params.setMargins(0, marginTop, 0, marginBottom);

		int corners = (int) (ScreenH * 0.3); //ความโค้ค้  ง
		GradientDrawable shape = new GradientDrawable();
		shape.setCornerRadius(corners);
		shape.setStroke(1, Color.MAGENTA);
		shape.setColor(Color.WHITE);

		int editH = (int) (ScreenH * 0.1);

		EditText choose = new EditText(context);
		EditText day = new EditText(context);
		choose.setHeight(editH);
		day.setHeight(editH);

		choose.setLayoutParams(params);
		choose.setGravity(Gravity.CENTER);
		choose.setBackground(shape);
		choose.setHintTextColor(Color.LTGRAY);
		choose.setHint("Choose Vegetable for Planting");

		Button mnhome;
		Button mnplant;
		Button mnprofile;
//		Button plant1;
//		Button plant2;
		mnhome = new Button(context);
		mnplant = new Button(context);
		mnprofile = new Button(context);

		mnhome.setLayoutParams(params);
		mnhome.setGravity(Gravity.CENTER);
		mnhome.setBackground(shape);
		mnhome.setHintTextColor(Color.MAGENTA);
		mnhome.setHint("HOME");

		mnplant.setLayoutParams(params);
		mnplant.setGravity(Gravity.CENTER);
		mnplant.setBackground(shape);
		mnplant.setHintTextColor(Color.MAGENTA);
		mnplant.setHint("PLANT");

		mnprofile.setLayoutParams(params);
		mnprofile.setGravity(Gravity.CENTER);
		mnprofile.setBackground(shape);
		mnprofile.setHintTextColor(Color.MAGENTA);
		mnprofile.setHint("PROFILE");

		LL.addView(mnhome);
		LL.addView(mnplant);
		LL.addView(mnprofile);

		Screen.addView(LL);

		return Screen;
	}

	public LinearLayout detailScreen(final Context context) {
		return null;
	}

	public LinearLayout profileScreen(final Context context) {
		return null;
	}

	public LinearLayout plantScreen(final Context context) {
		return null;
	}

	public LinearLayout finishScreen(final Context context) {
		return null;
	}

	public LinearLayout stepScreen(final Context context) {
		return null;
	}

	//================================================ connect web service
	AsyncHttpResponseHandler AsyncHandler() {
		return new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int i, Header[] headers, byte[] bytes) {
				String txtXml = new String(bytes);
				Toast.makeText(context, txtXml, Toast.LENGTH_SHORT).show();
				SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");

				int number = Integer.parseInt(txtXml);
				switch (number) {
					case 0: //sign up success
						setContentView(signinScreen(context));
						break;
					case 1: //sign up fail
						setContentView(signupScreen(context));
						break;
					case 2: //sign in success
						setContentView(homeScreen(context));
						break;
					case 3: //sign in fail
						setContentView(signupScreen(context));
						break;

					default:
						setContentView(signupScreen(context));
						Toast.makeText(context, "error!!!", Toast.LENGTH_SHORT).show();
				}

//				if (txtXml.equals("success")) {
//					Toast.makeText(context, "sign in success", Toast.LENGTH_SHORT).show();
//					setContentView(homeScreen(context));
//				} else {
//					Toast.makeText(context, "error!!!", Toast.LENGTH_SHORT).show();
//					setContentView(signinScreen(context));
//				}
			}

			@Override
			public void onFailure(int i, Header[] headers, byte[] bytes, Throwable thrwbl) {
				Toast.makeText(context, "fail!!!!!!!", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onStart() {
				//Toast.makeText(context, "step2", Toast.LENGTH_SHORT).show();
			}

		};

	}

}
