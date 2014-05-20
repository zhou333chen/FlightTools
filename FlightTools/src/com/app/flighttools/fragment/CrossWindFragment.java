package com.app.flighttools.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.flighttools.view.CompassView;
import com.app.flighttools.view.R;
import com.app.flighttools.wheel.ArrayWheelAdapter;
import com.app.flighttools.wheel.OnWheelScrollListener;
import com.app.flighttools.wheel.WheelView;

public class CrossWindFragment extends Fragment implements OnWheelScrollListener{
		
	private CompassView compass;
	private WheelView wheel_wind_speed1;
	private WheelView wheel_wind_speed2;
	private WheelView wheel_wind_speed3;
	private WheelView wheel_output_unit;
	private TextView tv_wind_angle;
	private TextView tv_hdg;
	private int speed;	
	String[] number = {"0","1","2","3","4","5","6","7","8","9"};
    String[] unit = {"kt","m/s","km/h"};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contextView = inflater.inflate(R.layout.fragment_cross_wind, container,
				false);
		return contextView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initResource();
	}
	
	public void initResource()
	{
        compass = (CompassView)getActivity().findViewById(R.id.compassView1);
        compass.setFragment(this);
        ArrayWheelAdapter<String> adapter1 =
                new ArrayWheelAdapter<String>(getActivity(), number);
        adapter1.setTextSize(30);
        ArrayWheelAdapter<String> adapter2 =
                new ArrayWheelAdapter<String>(getActivity(), unit);
        adapter1.setTextSize(30);
		wheel_wind_speed1 = (WheelView)getActivity().findViewById(R.id.windspeed1);
        wheel_wind_speed1.setVisibleItems(3);  
        wheel_wind_speed1.setCyclic(true);
        wheel_wind_speed1.setViewAdapter(adapter1);
        wheel_wind_speed1.addScrollingListener(this);
		wheel_wind_speed2 = (WheelView)getActivity().findViewById(R.id.windspeed2);
        wheel_wind_speed2.setVisibleItems(3);   
        wheel_wind_speed2.setCyclic(true);
        wheel_wind_speed2.setViewAdapter(adapter1);
        wheel_wind_speed2.addScrollingListener(this);
		wheel_wind_speed3 = (WheelView)getActivity().findViewById(R.id.windspeed3);
        wheel_wind_speed3.setVisibleItems(3);   
        wheel_wind_speed3.setViewAdapter(adapter2);
        wheel_wind_speed3.addScrollingListener(this);
        wheel_output_unit = (WheelView)getActivity().findViewById(R.id.output_unit);
        wheel_output_unit.setVisibleItems(3);   
        wheel_output_unit.setViewAdapter(adapter2);
        wheel_output_unit.addScrollingListener(this);
        tv_wind_angle = (TextView)getActivity().findViewById(R.id.tv_wind_angle);
	}
	
	public void setAngel(int b)
	{
		tv_wind_angle.setText(b+"°");
	}

	@Override
	public void onScrollingStarted(WheelView wheel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrollingFinished(WheelView wheel) {
		// TODO Auto-generated method stub
		speed = 10*wheel_wind_speed1.getCurrentItem() + wheel_wind_speed2.getCurrentItem();
		String unit_input = unit[wheel_wind_speed3.getCurrentItem()];
		String unit_output = unit[wheel_output_unit.getCurrentItem()];
		if(!unit_input.equals(unit_output.toString()))
		{
			if(unit_input.equals("m/s") && unit_output.equals("km/s"))
				speed *= 3.6;
			if(unit_input.equals("m/s") && unit_output.equals("kt"))
				speed *= 1.94384449;
			if(unit_input.equals("km/h") && unit_output.equals("m/s"))
				speed *= 0.27777778;
			if(unit_input.equals("km/h") && unit_output.equals("kt"))
				speed *= 0.5399568;
			if(unit_input.equals("kt") && unit_output.equals("m/s"))
				speed *= 0.51444444;
			if(unit_input.equals("kt") && unit_output.equals("km/h"))
				speed *= 1.852;
		}
		compass.setSpeed(speed);
	}
}
