package com.app.flighttools.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.flighttools.fragment.CrossWindFragment;
import com.app.flighttools.fragment.FlightLogFragment;
import com.app.flighttools.fragment.TimeCalculatorFragment;
import com.app.flighttools.view.R;

public class EngineeringActivity extends FragmentActivity {

	private TextView tv_tab1chs;
	private TextView tv_tab1eng;
	private TextView tv_tab2chs;
	private TextView tv_tab2eng;
	private TextView tv_tab3chs;
	private TextView tv_tab3eng;
	private RelativeLayout layout_tab1;
	private RelativeLayout layout_tab2;
	private RelativeLayout layout_tab3;
	private String[] tabchs;
	private String[] tabeng;
	private FragmentManager fm;
	private int current = 1;
	private final static int TAB_CROSS_WIND = 0;
	private final static int TAB_FLIGHT_LOG = 1;
	private final static int TAB_TIME_CALCULATOR = 2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_engineering);
		initResource();
	}
	
	public void initResource()
	{
		String[] tabchsTemp = {getString(R.string.tab1chs), 
				  			   getString(R.string.tab2chs), 
				  			   getString(R.string.tab3chs)};
		String[] tabengTemp = {getString(R.string.tab1eng), 
				  			   getString(R.string.tab2eng), 
				  			   getString(R.string.tab3eng)};
		tabchs = tabchsTemp;
		tabeng = tabengTemp;
		tv_tab1chs = (TextView)findViewById(R.id.tv_tab1chs);
		tv_tab1eng = (TextView)findViewById(R.id.tv_tab1eng);
		tv_tab2chs = (TextView)findViewById(R.id.tv_tab2chs);
		tv_tab2eng = (TextView)findViewById(R.id.tv_tab2eng);
		tv_tab3chs = (TextView)findViewById(R.id.tv_tab3chs);
		tv_tab3eng = (TextView)findViewById(R.id.tv_tab3eng);
		layout_tab1 = (RelativeLayout)findViewById(R.id.layout_tab1);
		layout_tab2 = (RelativeLayout)findViewById(R.id.layout_tab2);
		layout_tab3 = (RelativeLayout)findViewById(R.id.layout_tab3);
		fm = getSupportFragmentManager();
		layout_tab1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				chooseFragment(-1);
				setCenter();
			}
		});
		layout_tab3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				chooseFragment(1);
				setCenter();
			}
		});
		setCenter();
		showFragment(1);
	}
	
	public void setCenter()
	{
		if(current-1 < 0)
		{
			tv_tab1chs.setText("");
			tv_tab1eng.setText("");	
		}
		else
		{
			tv_tab1chs.setText(tabchs[current-1]);
			tv_tab1eng.setText(tabeng[current-1]);
		}
		tv_tab2chs.setText(tabchs[current]);
		tv_tab2eng.setText(tabeng[current]);
		if(current+1 == tabchs.length)
		{
			tv_tab3chs.setText("");
			tv_tab3eng.setText("");	
		}
		else
		{
			tv_tab3chs.setText(tabchs[current+1]);
			tv_tab3eng.setText(tabeng[current+1]);
		}
	}
	
	public void chooseFragment(int i)
	{
		FragmentTransaction tr = fm.beginTransaction();
		Fragment currentFragment = fm.findFragmentByTag(current+"");
		if (currentFragment != null)
			tr.hide(currentFragment);
		current += i;
		Fragment toaddFragment = null;
		switch(current) {
		case TAB_CROSS_WIND:
			toaddFragment = fm.findFragmentByTag(current+"");
			if (toaddFragment == null)
				toaddFragment = new CrossWindFragment();
			break;
		case TAB_FLIGHT_LOG:
			toaddFragment = fm.findFragmentByTag(current+"");
			if (toaddFragment == null)
			   toaddFragment = new FlightLogFragment();
			break;
		case TAB_TIME_CALCULATOR:
			toaddFragment = fm.findFragmentByTag(current+"");
			if (toaddFragment == null)
				toaddFragment = new TimeCalculatorFragment();
			break;
		default:
			toaddFragment = currentFragment;
			break;
		}

		if (toaddFragment.isAdded()) {
			tr.show(toaddFragment);
		} else {
			tr.add(R.id.maincontent, toaddFragment, current+"");
			tr.show(toaddFragment);
		}
		tr.commitAllowingStateLoss();
	}
	
	public void showFragment(int fragmentTag) {
		FragmentTransaction tr = fm.beginTransaction();
		Fragment currentFragment = fm.findFragmentByTag(current+"");
		if (currentFragment != null)
			tr.hide(currentFragment);
		current = fragmentTag;
		String temp = fragmentTag+"";
		Fragment toaddFragment = null;
		toaddFragment = fm.findFragmentByTag(temp);
		if (toaddFragment == null) {
			if (temp.equals(TAB_CROSS_WIND+"")) {
				toaddFragment = new CrossWindFragment();
			} else if (temp.equals(TAB_FLIGHT_LOG+"")) {
				toaddFragment = new FlightLogFragment();
			} else if (temp.equals(TAB_TIME_CALCULATOR+"")) {
				toaddFragment = new TimeCalculatorFragment();
			}
		}
		if (!toaddFragment.isAdded())
			tr.add(R.id.maincontent, toaddFragment, current+"");
		tr.show(toaddFragment);
		tr.commit();
	}
}
