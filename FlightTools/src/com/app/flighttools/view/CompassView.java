package com.app.flighttools.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.app.flighttools.view.R;

public class CompassView extends View{
	private int compassAngle;	//表示罗盘旋转的角度，数值为从正北方向顺时针旋转的角度，该角度的位置始终在↑方向
	private int windAngle;	//表示风的方向，数值为从正北方向顺时针旋转的角度
	private float centerX;	//罗盘圆心横坐标
	private float centerY;	//罗盘圆心纵坐标
	private float radius = 150;	//罗盘的半径
	private Paint planePaint;	//飞机的画笔
	private Paint textPaint;	//文字的画笔
	private Paint compassPaint;	//罗盘的画笔
	private Paint windPaint;	//风的箭头的画笔
	private Paint resultPaint;	//风速在分量箭头的画笔
	private Bitmap plane;
	private String[] text = {"北","东","南","西"};
	private float startX;	//滑动罗盘的起点x坐标
	private float startY;	//滑动罗盘的起点y坐标
	private float endX;	//滑动罗盘的终点x坐标
	private float endY;	//滑动罗盘的终点y坐标
	
	public CompassView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initResource();
	}
	
	public CompassView(Context context, AttributeSet attrs){
		super(context, attrs);
		initResource();
	}
	
    public CompassView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initResource();
    }
	
	public void onDraw(Canvas canvas){
		if(centerX ==0 && centerY ==0)
		{
			centerX = getWidth()/2;
			centerY = getHeight()/2;
		}
		//绘制罗盘和飞机
		canvas.drawCircle(centerX, centerY, radius, compassPaint);
		canvas.drawBitmap(plane, centerX - plane.getWidth()/2, centerY - plane.getHeight()/2, planePaint);
		for(int i=0;i<4;i++)
		{
			canvas.drawText(text[i], 
							centerX + (radius+20)*(float)Math.sin((90*i-compassAngle)*Math.PI/180), 
							centerY - (radius+20)*(float)Math.cos((90*i-compassAngle)*Math.PI/180) + 8, 
							textPaint);
		}
		//绘制风向箭头
		int theta = compassAngle - windAngle;
		float a = 0.3f;	//箭头的相对长度
		float b = 0.28f;	//防止箭头处不圆滑
		float c = 0.1f;	//箭头前端的相对长度
		canvas.drawLine(centerX-radius*sin(theta), centerY-radius*cos(theta), 
						centerX-a*radius*sin(theta), centerY-a*radius*cos(theta), 
						windPaint);
		canvas.drawLine(centerX-b*radius*sin(theta), centerY-b*radius*cos(theta), 
						centerX-b*radius*sin(theta)-c*radius*sin(theta-45), centerY-b*radius*cos(theta)-c*radius*cos(theta-45), 
						windPaint);
		canvas.drawLine(centerX-b*radius*sin(theta), centerY-b*radius*cos(theta), 
						centerX-b*radius*sin(theta)-c*radius*sin(theta+45), centerY-b*radius*cos(theta)-c*radius*cos(theta+45), 
						windPaint);
		//绘制风速水平分量
		canvas.drawLine(centerX-radius*sin(theta), centerY-radius*cos(theta), 
						centerX-a*radius*sin(theta), centerY-radius*cos(theta), 
						resultPaint);
		//绘制风速垂直分量
		canvas.drawLine(centerX-radius*sin(theta), centerY-radius*cos(theta), 
						centerX-radius*sin(theta), centerY-a*radius*cos(theta), 
						resultPaint);
	}
		
	public void initResource(){
		planePaint = new Paint();
		textPaint = new Paint();
		textPaint.setTextSize(20);
		textPaint.setColor(getResources().getColor(R.color.paint_text));
		textPaint.setTextAlign(Paint.Align.CENTER);
		compassPaint = new Paint();
		compassPaint.setColor(getResources().getColor(R.color.paint_compass));
		compassPaint.setStyle(Paint.Style.STROKE);
		compassPaint.setStrokeWidth(4);
		windPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		windPaint.setColor(getResources().getColor(R.color.paint_wind));
		windPaint.setStrokeWidth(4);
		resultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		resultPaint.setColor(getResources().getColor(R.color.paint_result));
		resultPaint.setStrokeWidth(3);
		plane = BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.tag_plane);
		this.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch(event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_MOVE:
					endX = event.getX();
					endY = event.getY();
					//三角形余弦定理公式计算旋转的角度Δθ
					double a = distance(startX, startY, centerX, centerY);
					double b = distance(endX, endY, centerX, centerY);
					double c = distance(startX, startY, endX, endY);
					float deltaTheta = (float)(Math.acos((a*a+b*b-c*c)/(2*a*b))/Math.PI)*180;
					deltaTheta *= 1;
					//判断是顺时针还是逆时针，以及是旋转风向还是罗盘
					float deltaX = endX - startX;
					float deltaY = endY - startY;
					if(Float.isNaN(deltaTheta))
						return true;					
					if(startX <= centerX && startY <= centerY)
					{
						if(deltaX < 0 && deltaY > 0)
						{
							if(a > radius)
								compassAngle += deltaTheta;
							else
								windAngle -= deltaTheta;
						}
						else if(deltaX > 0 && deltaY < 0)
						{
							if(a > radius)
								compassAngle -= deltaTheta;
							else
								windAngle += deltaTheta;
						}
					}
					else if(startX <= centerX && startY >= centerY)
					{
						if(deltaX > 0 && deltaY > 0)
						{
							if(a > radius)
								compassAngle += deltaTheta;
							else
								windAngle -= deltaTheta;
						}
						else if(deltaX < 0 && deltaY < 0)
						{
							if(a > radius)
								compassAngle -= deltaTheta;
							else
								windAngle += deltaTheta;
						}
					}
					else if(startX >= centerX && startY >= centerY)
					{
						if(deltaX > 0 && deltaY < 0)
						{
							if(a > radius)
								compassAngle += deltaTheta;
							else
								windAngle -= deltaTheta;
						}
						else if(deltaX < 0 && deltaY > 0)
						{
							if(a > radius)
								compassAngle -= deltaTheta;
							else
								windAngle += deltaTheta;
						}
					}
					else if(startX >= centerX && startY <= centerY)
					{
						if(deltaX < 0 && deltaY < 0)
						{
							if(a > radius)
								compassAngle += deltaTheta;
							else
								windAngle -= deltaTheta;
						}
						else if(deltaX > 0 && deltaY > 0)
						{
							if(a > radius)
								compassAngle -= deltaTheta;
							else
								windAngle += deltaTheta;
						}
					}
					if(compassAngle < 0)
						compassAngle += 360;
					if(windAngle < 0)
						windAngle += 360;
					startX = endX;
					startY = endY;
					invalidate();
					break;
				default:
					break;
				}
				return true;
			}
		});
	}
	
	public double distance(float x1, float y1, float x2, float y2)
	{
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}
	
	public float sin(int theta)
	{
		return (float) Math.sin(theta*Math.PI/180);
	}
	
	public float cos(int theta)
	{
		return (float) Math.cos(theta*Math.PI/180);
	}
}
