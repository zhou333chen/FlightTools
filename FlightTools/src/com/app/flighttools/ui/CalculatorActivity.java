package com.app.flighttools.ui;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.flighttools.view.R;

public class CalculatorActivity extends Activity implements OnClickListener{
	
	private TextView tv_last;
	private TextView tv_now;
	private TextView tv_result;
	private ImageView image_back;
	private StringBuilder equation;
	private float height;
	private boolean ifResult = false;
	private boolean ifChangedTextSize = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);
		initResource();
	}
	
	public void initResource()
	{
		equation = new StringBuilder("0");
		tv_last = (TextView)findViewById(R.id.tv_last);
		tv_now = (TextView)findViewById(R.id.tv_now);
		tv_result = (TextView)findViewById(R.id.tv_result);
		image_back = (ImageView)findViewById(R.id.image_back);
		tv_now.setMovementMethod(ScrollingMovementMethod.getInstance());
		findViewById(R.id.button_0).setOnClickListener(this);
		findViewById(R.id.button_1).setOnClickListener(this);
		findViewById(R.id.button_2).setOnClickListener(this);
		findViewById(R.id.button_3).setOnClickListener(this);
		findViewById(R.id.button_4).setOnClickListener(this);
		findViewById(R.id.button_5).setOnClickListener(this);
		findViewById(R.id.button_6).setOnClickListener(this);
		findViewById(R.id.button_7).setOnClickListener(this);
		findViewById(R.id.button_8).setOnClickListener(this);
		findViewById(R.id.button_9).setOnClickListener(this);
		findViewById(R.id.button_dian).setOnClickListener(this);		
		findViewById(R.id.button_jia).setOnClickListener(this);
		findViewById(R.id.button_jian).setOnClickListener(this);
		findViewById(R.id.button_cheng).setOnClickListener(this);
		findViewById(R.id.button_chu).setOnClickListener(this);
		findViewById(R.id.button_delete).setOnClickListener(this);
		findViewById(R.id.button_clear).setOnClickListener(this);
		findViewById(R.id.button_equal).setOnClickListener(this);
		//返回上级页面
		image_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	public void onClick(View v){
		// TODO Auto-generated method stub
		try{
			switch(v.getId())
			{
			case R.id.button_0:
				addNumber("0");
				break;
			case R.id.button_1:
				addNumber("1");
				break;
			case R.id.button_2:
				addNumber("2");
				break;
			case R.id.button_3:
				addNumber("3");
				break;
			case R.id.button_4:
				addNumber("4");
				break;
			case R.id.button_5:
				addNumber("5");
				break;
			case R.id.button_6:
				addNumber("6");
				break;
			case R.id.button_7:
				addNumber("7");
				break;
			case R.id.button_8:
				addNumber("8");
				break;
			case R.id.button_9:
				addNumber("9");
				break;
			case R.id.button_dian:
				if(ifResult)
				{
					equation.delete(0, equation.length());
					equation.append("0");
				}
				equation.append(".");
				break;
			case R.id.button_jia:
				addOperator("+");
				break;
			case R.id.button_jian:
				addOperator("-");
				break;
			case R.id.button_cheng:
				addOperator("×");
				break;
			case R.id.button_chu:
				addOperator("÷");
				break;
			case R.id.button_delete:
				ifResult = false;
				if(equation.length() !=1)
					equation.deleteCharAt(equation.length()-1);
				else
				{
					equation.replace(0, 1, "0");
					if(ifChangedTextSize == true)
					{
						tv_now.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv_now.getTextSize()*2);
						ifChangedTextSize = false;
					}
					
				}
				break;
			case R.id.button_clear:
				ifResult = false;
				if(equation.length() !=1)
				{
					equation.delete(1, equation.length());
				}
				equation.replace(0, 1, "0");
				if(ifChangedTextSize == true)
				{
					tv_now.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv_now.getTextSize()*2);
					ifChangedTextSize = false;
				}
				break;
			case R.id.button_equal:
				ifResult = true;
				int tag1 = 0;
				ArrayList<String> number1 = new ArrayList<String>();
				ArrayList<String> operator1 = new ArrayList<String>();
				for(int i=0;i<equation.length();i++)
				{
					if(equation.charAt(i) == '+' || equation.charAt(i) == '-')
					{
						number1.add(equation.substring(tag1, i));
						operator1.add(equation.substring(i, i+1));
						tag1 = i+1;
					}
				}
				number1.add(equation.substring(tag1, equation.length()));
				for(int i=0;i<number1.size();i++)
				{
					if(number1.get(i).contains("×") || number1.get(i).contains("÷"))
					{
						int tag2 =0;
						ArrayList<String> number2 = new ArrayList<String>();
						ArrayList<String> operator2 = new ArrayList<String>();
						int h = number1.get(i).length();
						for(int j=0;j<number1.get(i).length();j++)
						{		
							if(number1.get(i).charAt(j) == '×' || number1.get(i).charAt(j) == '÷')
							{
								number2.add(number1.get(i).substring(tag2, j));
								operator2.add(number1.get(i).substring(j, j+1));
								tag2 = j+1;
							}
						}
						number2.add(number1.get(i).substring(tag2, number1.get(i).length()));
						double result = 0;
						if(operator2.get(0).equals("×"))
							result = Double.parseDouble(number2.get(0))*Double.parseDouble(number2.get(1));
						else if(operator2.get(0).equals("÷"))
						{
							if(Double.parseDouble(number2.get(1)) == 0)
							{
								throwException();
								return;
							}
							result = Double.parseDouble(number2.get(0))/Double.parseDouble(number2.get(1));
						}
						for(int k=1;k<operator2.size();k++)
						{
							if(operator2.get(k).equals("×"))
							{
								result *= Double.parseDouble(number2.get(k+1));
							}
							if(operator2.get(k).equals("÷"))
							{
								if(Double.parseDouble(number2.get(k+1)) == 0)
								{
									throwException();
									return;
								}						
								result /= Double.parseDouble(number2.get(k+1));
							}
						}
						number1.set(i, result+"");
					}
				}
				double result = Double.parseDouble(number1.get(0));
				for(int i=0;i<operator1.size();i++)
				{
					if(operator1.get(i).equals("+"))
					{
						result += Double.parseDouble(number1.get(i+1));
					}
					if(operator1.get(i).equals("-"))
					{
						result -= Double.parseDouble(number1.get(i+1));
					}
				}
				DecimalFormat df = new DecimalFormat("0.00");
				result = Double.parseDouble(df.format(result));
				if(Math.round(result)-result == 0)
				{
					tv_last.setText(equation.toString() + "=");
					equation.replace(0, equation.length(), (long)result+"");
					break;
				}
				tv_last.setText(equation.toString() + "=");
				java.text.NumberFormat nf = java.text.NumberFormat.getInstance();   
				nf.setGroupingUsed(false);
				equation.replace(0, equation.length(), nf.format(result));
				if(ifChangedTextSize == true)
				{
					tv_now.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv_now.getTextSize()*2);
					ifChangedTextSize = false;
				}
				break;
			}
			if(height == 0f)
			{
				height = tv_now.getHeight();
			}
			if(height != 0 && tv_now.getHeight() > height && ifChangedTextSize == false)
			{
				tv_now.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv_now.getTextSize()/2);
				ifChangedTextSize = true;
			}
			if(ifResult == false)
			{
				tv_now.setVisibility(View.VISIBLE);
				tv_result.setVisibility(View.GONE);  
				tv_now.setText(equation);
			}
			else
			{
				tv_now.setVisibility(View.GONE);
				tv_result.setVisibility(View.VISIBLE);
				tv_result.setText(equation);
			}	
		}
		catch(Exception e){
			throwException();
		}
	}
	
	public void addNumber(String s)
	{
		if(ifResult)
		{
			equation.delete(0, equation.length());
			ifResult = false;
		}
		if(equation.toString().equals("0"))
			equation.deleteCharAt(0);
		equation.append(s);
	}
	
	public void addOperator(String s)
	{
		ifResult = false;
		equation.append(s);
	}
	
	public void throwException()
	{
		Toast.makeText(this, "算式有误，请重新输入", Toast.LENGTH_SHORT).show();
		if(ifChangedTextSize == true)
		{
			tv_now.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv_now.getTextSize()*2);
			ifChangedTextSize = false;
		}
		if(equation.length() !=1)
		{
			equation.delete(1, equation.length());
		}
		equation.replace(0, 1, "0");
		tv_now.setVisibility(View.VISIBLE);
		tv_result.setVisibility(View.GONE);
		tv_now.setText(equation);
	}
}
