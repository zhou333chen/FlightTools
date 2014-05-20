package com.app.flighttools.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.flighttools.util.TimeUtils;
import com.app.flighttools.view.R;

public class TimeCalculatorFragment extends Fragment implements OnClickListener{
	
	private TextView tv_last;
	private TextView tv_now;
	private TextView tv_in;
	private Button button_fast;
	private Button button_normal;
	private Button button_fast_delete;
	private Button button_fast_jian;
	private Button button_normal_maohao;
	private Button button_normal_jia;
	private Button button_normal_jian;
	private Button button_normal_delete;	
	private String in = "";	//输入栏的内容
	private String inTemp = "";	//备份输入内容，防止计算等式异常后原公式丢失
	private String item = "";	//输入栏中最近一个时间，例如10:30+11:21，则item表示11:21
	private boolean isFast = true;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contextView = inflater.inflate(R.layout.fragment_time_calculator, container,
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
		tv_last = (TextView)getActivity().findViewById(R.id.tv_last);
		tv_now = (TextView)getActivity().findViewById(R.id.tv_now);
		tv_in = (TextView)getActivity().findViewById(R.id.tv_in);
		button_fast = (Button)getActivity().findViewById(R.id.button_fast);
		button_normal = (Button)getActivity().findViewById(R.id.button_normal);
		button_fast_delete = (Button)getActivity().findViewById(R.id.button_fast_delete);
		button_fast_jian = (Button)getActivity().findViewById(R.id.button_fast_jian);
		button_normal_maohao = (Button)getActivity().findViewById(R.id.button_normal_maohao);
		button_normal_jia = (Button)getActivity().findViewById(R.id.button_normal_jia);
		button_normal_jian = (Button)getActivity().findViewById(R.id.button_normal_jian);
		button_normal_delete = (Button)getActivity().findViewById(R.id.button_normal_delete);
		getActivity().findViewById(R.id.button_00).setOnClickListener(this);
		getActivity().findViewById(R.id.button_11).setOnClickListener(this);
		getActivity().findViewById(R.id.button_22).setOnClickListener(this);
		getActivity().findViewById(R.id.button_33).setOnClickListener(this);
		getActivity().findViewById(R.id.button_44).setOnClickListener(this);
		getActivity().findViewById(R.id.button_55).setOnClickListener(this);
		getActivity().findViewById(R.id.button_66).setOnClickListener(this);
		getActivity().findViewById(R.id.button_77).setOnClickListener(this);
		getActivity().findViewById(R.id.button_88).setOnClickListener(this);
		getActivity().findViewById(R.id.button_99).setOnClickListener(this);		
		getActivity().findViewById(R.id.button_equall).setOnClickListener(this);
		button_fast.setOnClickListener(this);
		button_normal.setOnClickListener(this);
		button_fast_delete.setOnClickListener(this);
		button_fast_jian.setOnClickListener(this);
		button_normal_maohao.setOnClickListener(this);
		button_normal_jia.setOnClickListener(this);
		button_normal_jian.setOnClickListener(this);
		button_normal_delete.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		a:switch(v.getId())
		{
		case R.id.button_fast:
			isFast = true;
			in = "";
			button_fast.setBackgroundResource(R.drawable.bg_choose);
			button_normal.setBackgroundResource(R.drawable.bg_not_choose);
			button_fast_delete.setVisibility(View.VISIBLE);
			button_fast_jian.setVisibility(View.VISIBLE);
			button_normal_maohao.setVisibility(View.GONE);
			button_normal_jia.setVisibility(View.GONE);
			button_normal_jian.setVisibility(View.GONE);
			button_normal_delete.setVisibility(View.GONE);
			break;
		case R.id.button_normal:
			isFast = false;
			in = "";
			button_fast.setBackgroundResource(R.drawable.bg_not_choose);
			button_normal.setBackgroundResource(R.drawable.bg_choose);
			button_fast_delete.setVisibility(View.GONE);
			button_fast_jian.setVisibility(View.GONE);
			button_normal_maohao.setVisibility(View.VISIBLE);
			button_normal_jia.setVisibility(View.VISIBLE);
			button_normal_jian.setVisibility(View.VISIBLE);
			button_normal_delete.setVisibility(View.VISIBLE);
			break;
		case R.id.button_00:
			addNumber("0");
			break;
		case R.id.button_11:
			addNumber("1");
			break;
		case R.id.button_22:
			addNumber("2");
			break;
		case R.id.button_33:
			addNumber("3");
			break;
		case R.id.button_44:
			addNumber("4");
			break;
		case R.id.button_55:
			addNumber("5");
			break;
		case R.id.button_66:
			addNumber("6");
			break;
		case R.id.button_77:
			addNumber("7");
			break;
		case R.id.button_88:
			addNumber("8");
			break;
		case R.id.button_99:
			addNumber("9");
			break;
		case R.id.button_fast_delete:
			if(in.length() != 0)
			{
				if(item.length() == 0)
				{
					item = in.substring(in.length()-6, in.length()-1);
					item = TimeUtils.deleteNumber(item);
					in = in.substring(0, in.length()-4) + in.substring(in.length()-3, in.length()-2 );
				}
				else
				{
					item = TimeUtils.deleteNumber(item);
					in = in.substring(0, in.length()-1);
				}
			}
			break;
		case R.id.button_fast_jian:
			if(in.endsWith("+"))
			{
				in = in.substring(0, in.length()-1) + "-";
			}
			else
			{
				Toast.makeText(getActivity(), "无效", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.button_normal_maohao:
			in += ":";
			break;
		case R.id.button_normal_jia:
			in += "+";
			break;
		case R.id.button_normal_jian:
			in += "-";
			break;
		case R.id.button_normal_delete:
			if(!in.equals(""))
				in = in.substring(0, in.length()-1);
			break;
		case R.id.button_equall:
			try{
				int tag1 = 0;
				ArrayList<String> time = new ArrayList<String>();
				ArrayList<String> operator = new ArrayList<String>();
				inTemp = in.toString();
				if(isFast)
					in = in.substring(0, in.length()-1);
				for(int i=0;i<in.length();i++)
				{
					if(in.charAt(i) == '+' || in.charAt(i) == '-')
					{
						time.add(in.substring(tag1, i));
						operator.add(in.substring(i, i+1));
						tag1 = i+1;
					}
				}
				time.add(in.substring(tag1, in.length()));
				for(int i=0;i<time.size();i++)
				{
					if(!TimeUtils.confirm(time.get(i)))
					{
						throwException("请检查公式的格式是否有误");
						break a;
					}
				}
				String result = time.get(0);
				for(int i=0;i<operator.size();i++)
				{
					if(operator.get(i).equals("+"))
					{
						result = TimeUtils.add(result, time.get(i+1));
					}
				}
				for(int i=0;i<operator.size();i++)
				{
					if(operator.get(i).equals("-"))
					{
						result = TimeUtils.sub(result, time.get(i+1));
						if(result == null)
						{
							throwException("计算结果为负，请检查时间是否有误");
							break a;
						}
					}
				}
				in += "="+result;
				tv_last.setText(tv_now.getText());
				tv_now.setText(in);
				in = "";
			}
			catch(Exception e){
				throwException("请检查公式的格式是否有误");
			}
			break;
		default:
			break;
		}
		tv_in.setText(in);
	}
	
	public void addNumber(String s)
	{
		if(isFast)
		{
			item = TimeUtils.addNumber(item, s);
			if(item.length() < 5)
			{
				in += s;
			}
			else if(item.length() == 5)
			{
				item = "";
				in = in.substring(0, in.length()-1) + ":" +in.substring(in.length()-1) + s + "+";
			}
		}
		else
		{
			in += s;
		}
	}
	
	public void throwException(String s)
	{
		Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
		in = inTemp;
	}
}
