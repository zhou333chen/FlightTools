package com.app.flighttools.ui;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.app.flighttools.util.FileUtils;
import com.app.flighttools.view.R;

public class MainActivity extends Activity implements OnClickListener{
	private String dirPath = "Flight Tools/";
	private String proffesional = "Professional Word.xls";
	private String code = "Airport Code.xls";
	private String abbreviation = "Abbreviation.xls";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		handler.postDelayed(run, 5000);
		initResource();
		try {
			writeXlsIfNotExist();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch(v.getId())
		{
		case R.id.layout_dictionary:
			intent = new Intent(this, DictionaryActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_abbreviation:
			intent = new Intent(this, AbbreviationActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_icao_iata:
			intent = new Intent(this, IcaoIataActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_calculator:
			intent = new Intent(this, CalculatorActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_engineering:
			intent = new Intent(this, EngineeringActivity.class);
			startActivity(intent);
			break;
		}
	}
	
	public void initResource(){
		findViewById(R.id.layout_dictionary).setOnClickListener(this);
		findViewById(R.id.layout_abbreviation).setOnClickListener(this);
		findViewById(R.id.layout_icao_iata).setOnClickListener(this);
		findViewById(R.id.layout_calculator).setOnClickListener(this);
		findViewById(R.id.layout_engineering).setOnClickListener(this);
	}
	
	public void writeXlsIfNotExist() throws IOException{
		AssetManager assetManager = getResources().getAssets();
		if(!FileUtils.isFileExist(dirPath + proffesional))
		{
			InputStream in = assetManager.open(proffesional);
			FileUtils.write2SDFromInput(dirPath, proffesional, in);
			in.close();
		}
		if(!FileUtils.isFileExist(dirPath + code))
		{
			InputStream in = assetManager.open(code);
			FileUtils.write2SDFromInput(dirPath, code, in);
			in.close();
		}
		if(!FileUtils.isFileExist(dirPath + abbreviation))
		{
			InputStream in = assetManager.open(abbreviation);
			FileUtils.write2SDFromInput(dirPath, abbreviation, in);
			in.close();
		}
	}
	
	Handler handler = new Handler();
	Runnable run = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			findViewById(R.id.image_logo).setVisibility(View.GONE);
			findViewById(R.id.layout_content).setVisibility(View.VISIBLE);
			Animation alphaAnimation = new AlphaAnimation(1.0f, 0f);  
			alphaAnimation.setDuration(1000);  
			findViewById(R.id.image_logo).startAnimation(alphaAnimation);  
		}
	};
}
