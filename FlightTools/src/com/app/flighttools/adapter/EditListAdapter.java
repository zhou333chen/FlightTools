package com.app.flighttools.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.flighttools.util.TimeUtils;
import com.app.flighttools.view.R;

public class EditListAdapter extends BaseAdapter{

	public String[] data;
	private Context context;
	private LayoutInflater layoutInflater;
	private ViewHolder holder;
	public int choose;
	
	public EditListAdapter(Context context)
    {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
		data = new String[10];
		for(int i=0;i<10;i++)
		{
			data[i] = "";
		}
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.length;
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return data[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null)
		{    
			convertView = layoutInflater.inflate(R.layout.item_sum, null);
		    holder = new ViewHolder();
            holder.edit = (TextView) convertView.findViewById(R.id.tv_sum);
            holder.view = (RelativeLayout) convertView.findViewById(R.id.view);
            convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		if(position == choose)
			holder.view.setBackgroundColor(Color.LTGRAY);
		else
			holder.view.setBackgroundColor(Color.WHITE);
		holder.edit.setText(data[position]);
		return convertView;
	}
	
	private static class ViewHolder
    {
        RelativeLayout view;
		TextView edit;
    }
	
	public String sum()
	{
		String result = "00:00";
		for(int i=0;i<10;i++)
		{
			if(data[i].length()>4)
			{
				result = TimeUtils.sum(result, data[i]);
			}
		}
		return result;
	}
	
	public void addNumber(String s)
	{
		data[choose] = TimeUtils.addNumber(data[choose], s);
		notifyDataSetChanged();
	}
	
	public void deleteNumber()
	{
		data[choose] = TimeUtils.deleteNumber(data[choose]);
		notifyDataSetChanged();
	}
	
	public void clear()
	{
		for(int i=0;i<10;i++)
			data[i] = "";
		notifyDataSetChanged();
	}
	
	public boolean confirm()
	{
		if(choose != 11)
			return TimeUtils.confirm(data[choose]);
		else
			return true;
	}
}
