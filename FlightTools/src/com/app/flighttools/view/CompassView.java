package com.app.flighttools.view;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.app.flighttools.fragment.CrossWindFragment;

public class CompassView extends View{
	private Fragment fragment;
	private int compassAngle = 0;	//表示罗盘旋转的角度，数值为从正北方向顺时针旋转的角度，该角度的位置始终在↑方向
	private int windAngle = 45;	//表示风的方向，数值为从正北方向顺时针旋转的角度
	private float centerX;	//罗盘圆心横坐标
	private float centerY;	//罗盘圆心纵坐标
	private float radius = 150;	//罗盘的半径
	private int speed;
	private int speedX;
	private int speedY;
	private Paint planePaint;	//飞机的画笔
	private Paint compassPaint;	//罗盘的画笔
	private Paint hdgPaint;
	private Paint windPaint;	//风的画笔
	private Paint windYPaint;//风的垂直分量
	private Paint windXPaint;//风的水平分量
	private Paint textPaint;	//文字的画笔
	private Paint resultPaint;	//风速在分量箭头的画笔
	private Bitmap plane;
	private Bitmap hdg;
	private Bitmap compass;
	private Bitmap wind;
	private Bitmap windY;
	private Bitmap windX;
	private float startX;	//滑动罗盘的起点x坐标
	private float startY;	//滑动罗盘的起点y坐标
	private float endX;	//滑动罗盘的终点x坐标
	private float endY;	//滑动罗盘的终点y坐标
	private PaintFlagsDrawFilter pfd;
	private float ratio;
	
	public CompassView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public CompassView(Context context, AttributeSet attrs){
		super(context, attrs);
	}
	
    public CompassView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
	
	public void onDraw(Canvas canvas){
		canvas.setDrawFilter(pfd);
		if(centerX ==0 && centerY ==0)	
		{
			centerX = getWidth()/2;
			centerY = getHeight()/2;
			initResource();
		}
		//绘制罗盘
		canvas.drawBitmap(hdg, centerX-hdg.getWidth()/2, centerY-compass.getHeight()*0.8f/2-hdg.getHeight(), hdgPaint);
		Matrix matrixCompass = new Matrix();
	    matrixCompass.postTranslate(centerX-compass.getWidth()/2, centerY-compass.getHeight()/2);
	    matrixCompass.postRotate(-compassAngle, centerX ,centerY);
		canvas.drawBitmap(compass, matrixCompass, compassPaint);
		canvas.drawText(compassAngle+"", centerX-hdg.getWidth()/2, centerY-compass.getHeight()*0.8f/2-hdg.getHeight()*142/197, textPaint);
		//绘制飞机
		canvas.drawBitmap(plane, centerX - plane.getWidth()/2+2, centerY - plane.getHeight()/2, planePaint);
		//绘制风向
		Matrix matrixWind = new Matrix();
		int theta = (windAngle - compassAngle);
		if(theta < 0)
			theta += 360;
	    matrixWind.postTranslate(centerX-wind.getWidth()/2, centerY-wind.getHeight());
	    matrixWind.postRotate(theta, centerX ,centerY);
		canvas.drawBitmap(wind, matrixWind, windPaint);
		//绘制侧风向和大小	
		speedX = Math.abs((int) (speed*sin(theta)));
		speedY = Math.abs((int) (speed*cos(theta)));
		if(theta>=0 && theta<90)
		{
			canvas.drawBitmap(windY, centerX-windY.getWidth()/2, centerY-windY.getHeight(), windYPaint);
			canvas.drawBitmap(windX, centerX, centerY-windX.getHeight()/2, windXPaint);
			canvas.drawText(speedX+"", centerX+windX.getWidth()*140/230, centerY+windX.getHeight()*0.85f/2, textPaint);
			canvas.drawText(speedY+"", centerX-windY.getWidth()*0.8f/2, centerY-windY.getHeight()*0.7f, textPaint);
		}
		else if(theta>=90 && theta<180)
		{
			Matrix matrixWind1 = new Matrix();
			matrixWind1.postTranslate(centerX-windY.getWidth()/2, centerY-windY.getHeight());
		    matrixWind1.postRotate(180, centerX ,centerY);
			canvas.drawBitmap(windY, matrixWind1, windYPaint);
			canvas.drawBitmap(windX, centerX, centerY-windX.getHeight()/2, windXPaint);
			canvas.drawText(speedX+"", centerX+windX.getWidth()*140/230, centerY+windX.getHeight()*0.85f/2, textPaint);
			canvas.drawText(speedY+"", centerX-windY.getWidth()*0.8f/2, centerY+windY.getHeight()*0.95f, textPaint);
		}
		else if(theta>=180 && theta<270)
		{
			Matrix matrixWind1 = new Matrix();
			matrixWind1.postTranslate(centerX-windY.getWidth()/2, centerY-windY.getHeight());
		    matrixWind1.postRotate(180, centerX ,centerY);
			canvas.drawBitmap(windY, matrixWind1, windYPaint);
			Matrix matrixWind2 = new Matrix();
			matrixWind2.postTranslate(centerX, centerY-windX.getHeight()/2);
		    matrixWind2.postRotate(180, centerX ,centerY);
			canvas.drawBitmap(windX, matrixWind2, windXPaint);
			canvas.drawText(speedX+"", centerX-windX.getWidth()*220/230, centerY+windX.getHeight()*0.85f/2, textPaint);
			canvas.drawText(speedY+"", centerX-windY.getWidth()*0.8f/2, centerY+windY.getHeight()*0.95f, textPaint);
		}
		else if(theta>=270 && theta<360)
		{
			Matrix matrixWind1 = new Matrix();
			matrixWind1.postTranslate(centerX, centerY-windX.getHeight()/2);
		    matrixWind1.postRotate(180, centerX ,centerY);
			canvas.drawBitmap(windX, matrixWind1, windYPaint);
			canvas.drawBitmap(windY, centerX-windY.getWidth()/2, centerY-windY.getHeight(), windYPaint);
			canvas.drawText(speedX+"", centerX-windX.getWidth()*220/230, centerY+windX.getHeight()*0.85f/2, textPaint);
			canvas.drawText(speedY+"", centerX-windY.getWidth()*0.8f/2, centerY-windY.getHeight()*0.7f, textPaint);
		}
		((CrossWindFragment) fragment).setAngel(windAngle);
	}
	
	public void initResource(){
		pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG); 
		planePaint = new Paint();
		hdgPaint = new Paint();
		compassPaint = new Paint();
		windPaint = new Paint();
		windYPaint = new Paint();
		windXPaint = new Paint();
		textPaint = new Paint();
		resultPaint = new Paint();
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		Bitmap compassTemp = BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.tag_compass);
		ratio = 0.6f*Math.min(centerX*2/compassTemp.getWidth(), centerY*2/compassTemp.getHeight());
		textPaint.setTextSize(70*ratio);
		Matrix matrix = new Matrix(); 
		matrix.postScale(ratio, ratio);
		compass = Bitmap.createBitmap(compassTemp,0,0,compassTemp.getWidth(),compassTemp.getHeight(),matrix,true);
		compassTemp.recycle();
		Bitmap planeTemp = BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.tag_plane);
		plane = Bitmap.createBitmap(planeTemp,0,0,planeTemp.getWidth(),planeTemp.getHeight(),matrix,true);
		planeTemp.recycle();
		Bitmap hdgTemp = BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.tag_hdg);
		hdg = Bitmap.createBitmap(hdgTemp,0,0,hdgTemp.getWidth(),hdgTemp.getHeight(),matrix,true);
		hdgTemp.recycle();
		Bitmap windTemp = BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.tag_wind);
		wind = Bitmap.createBitmap(windTemp,0,0,windTemp.getWidth(),windTemp.getHeight(),matrix,true);
		windTemp.recycle();
		Bitmap windYTemp = BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.tag_wind_1);
		windY = Bitmap.createBitmap(windYTemp,0,0,windYTemp.getWidth(),windYTemp.getHeight(),matrix,true);
		windYTemp.recycle();
		Bitmap windXTemp = BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.tag_wind_2);
		windX = Bitmap.createBitmap(windXTemp,0,0,windXTemp.getWidth(),windXTemp.getHeight(),matrix,true);
		windXTemp.recycle();
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
					if(compassAngle > 360)
						compassAngle = 0;
					if(windAngle > 360)
						windAngle = 0;
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
	public void setFragment(Fragment fragment)
	{
		this.fragment = fragment;
	}
	
	public void setSpeed(int a)
	{
		speed = a;
		invalidate();
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
