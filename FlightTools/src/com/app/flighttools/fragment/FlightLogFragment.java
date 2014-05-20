package com.app.flighttools.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.app.flighttools.adapter.EditListAdapter;
import com.app.flighttools.database.DictionaryDbHelper;
import com.app.flighttools.database.FlightLogDbHelper;
import com.app.flighttools.util.TimeUtils;
import com.app.flighttools.view.HorizontalListView;
import com.app.flighttools.view.R;

public class FlightLogFragment extends Fragment implements OnClickListener{
	
	private ListView list_sum;
	private HorizontalListView list_history;
	private TextView tv_history;
	private TextView tv_result;
	private String history = "";
	private String sum = "";
	private EditListAdapter sumAdapter;
	private boolean isHistory = false;	//焦点是否在上页累积输入框上
	private FlightLogDbHelper flightLogHelper;
	private SQLiteDatabase dbRead;
	private SQLiteDatabase dbWrite;
	private ArrayList<HashMap<String, String>> listHistory;
	private SimpleAdapter spHitory;
	private final static String TABLE_NAME = "FlightLog";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contextView = inflater.inflate(R.layout.fragment_flight_log, container,
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
		list_sum = (ListView)getActivity().findViewById(R.id.list_sum);
		list_history = (HorizontalListView)getActivity().findViewById(R.id.list_history);
		tv_history = (TextView)getActivity().findViewById(R.id.tv_history);
		tv_result = (TextView)getActivity().findViewById(R.id.tv_result);
		tv_history.setOnClickListener(this);
		getActivity().findViewById(R.id.button_0).setOnClickListener(this);
		getActivity().findViewById(R.id.button_1).setOnClickListener(this);
		getActivity().findViewById(R.id.button_2).setOnClickListener(this);
		getActivity().findViewById(R.id.button_3).setOnClickListener(this);
		getActivity().findViewById(R.id.button_4).setOnClickListener(this);
		getActivity().findViewById(R.id.button_5).setOnClickListener(this);
		getActivity().findViewById(R.id.button_6).setOnClickListener(this);
		getActivity().findViewById(R.id.button_7).setOnClickListener(this);
		getActivity().findViewById(R.id.button_8).setOnClickListener(this);
		getActivity().findViewById(R.id.button_9).setOnClickListener(this);
		getActivity().findViewById(R.id.button_equal).setOnClickListener(this);		
		getActivity().findViewById(R.id.button_delete).setOnClickListener(this);
		getActivity().findViewById(R.id.button_clear).setOnClickListener(this);
		flightLogHelper = new FlightLogDbHelper(getActivity(), TABLE_NAME, null, 1);
		dbRead = flightLogHelper.getReadableDatabase();
		dbWrite = flightLogHelper.getWritableDatabase();
		sumAdapter = new EditListAdapter(getActivity());
		list_sum.setAdapter(sumAdapter);
		list_sum.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(isHistory)
				{
					if(!TimeUtils.confirm(history))
					{
						Toast.makeText(getActivity(), "请重新输入！", Toast.LENGTH_SHORT).show();
						return;
					}
				}
				if(sumAdapter.confirm())
				{
					isHistory = false;
					tv_history.setBackgroundColor(Color.WHITE);
					sumAdapter.choose = position;
					sumAdapter.notifyDataSetChanged();
				}
				else
				{
					Toast.makeText(getActivity(), "请重新输入！", Toast.LENGTH_SHORT).show();
				}
			}
		});
		listHistory = new ArrayList<HashMap<String,String>>();
		spHitory = new SimpleAdapter(getActivity(), listHistory, R.layout.item_calculator_history, new String[]{"History", "Total", "Sum"}, new int[]{R.id.tv_history, R.id.tv_total, R.id.tv_sum});
		list_history.setAdapter(spHitory);
		Cursor cursor = dbRead.query(TABLE_NAME, new String[]{"TimeStamp","History","Total", "Sum"}, null, null, null, null, "TimeStamp DESC");  
		int i = 0;
		while(cursor.moveToNext())
		{
			HashMap<String, String> list = new HashMap<String, String>();
			String history = cursor.getString(cursor.getColumnIndex("History"));
			String total = cursor.getString(cursor.getColumnIndex("Total"));
			String sum = cursor.getString(cursor.getColumnIndex("Sum"));
			list.put("History", history);
			list.put("Total", total);
			list.put("Sum", sum);
			listHistory.add(list);
			i++;
			if(i > 10)
				break;
		}
		list_history.setAdapter(spHitory);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.tv_history:
			if(isHistory)
			{
				if(!sumAdapter.confirm())
				{
					Toast.makeText(getActivity(), "请重新输入！", Toast.LENGTH_SHORT).show();
					return;
				}
			}
			isHistory = true;
			tv_history.setBackgroundColor(Color.LTGRAY);
			sumAdapter.choose = 11;
			sumAdapter.notifyDataSetChanged();
			break;
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
		case R.id.button_delete:
			if(isHistory)
			{
				history = TimeUtils.deleteNumber(history);
				tv_history.setText(history);
			}
			else
				sumAdapter.deleteNumber();
			break;
		case R.id.button_clear:
			if(isHistory)
			{
				history = "";
				tv_history.setText(history);
			}
			else
				sumAdapter.clear();
			break;
		case R.id.button_equal:
			if(sumAdapter.confirm() && TimeUtils.confirm(history))
			{
				String record = "";
				for(int i=0;i<10;i++)
				{
					if(!sumAdapter.getItem(i).equals(""))
						record += sumAdapter.getItem(i)+"\n";
				}
				if(record.equals(""))
					break;
				record = record.substring(0, record.length()-1);
				sum = sumAdapter.sum();
				tv_result.setText(sum);
				ContentValues values = new ContentValues(); 
	            values.put("TimeStamp", System.currentTimeMillis());
	            values.put("History", record);
	            values.put("Total", sum);
	            values.put("Sum", TimeUtils.add(sum, history));
				dbWrite.insert(TABLE_NAME, null, values);
				HashMap<String, String> list = new HashMap<String, String>();
				list.put("History", record);
	            list.put("Total", sum);
				list.put("Sum", TimeUtils.add(sum, history));
				listHistory.add(0, list);
				spHitory.notifyDataSetChanged();
			}
			else
			{
				Toast.makeText(getActivity(), "请重新输入！", Toast.LENGTH_SHORT).show();
			}
			history = "";
			tv_history.setText(history);
			sumAdapter.clearAll();
			break;
		default:
			break;
		}
	}	
	
	public void addNumber(String s)
	{
		if(isHistory)
		{
			history = TimeUtils.addNumber(history, s);
			tv_history.setText(history);
		}
		else
			sumAdapter.addNumber(s);
	}
	
}
