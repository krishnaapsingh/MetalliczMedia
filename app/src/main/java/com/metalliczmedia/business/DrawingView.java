package com.metalliczmedia.business;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Copyright (c) 2016 Dany Madden
 * This is released under the MIT license.
 * Please see LICENSE file for licensing detail.
 *
 * Based on Sue Smith's tutorial: 
 * http://code.tutsplus.com/tutorials/android-sdk-create-a-drawing-app-interface-creation--mobile-19021
 **/



public class DrawingView extends ImageView {

	private Path drawPath;
	private Paint drawPaint;
	private Paint canvasPaint;
	Context mContext;
	// initial color
	private int paintColor = 0xFFF5F5DC;
	private Canvas drawCanvas;
	private Bitmap canvasBitmap;
	private float brushSize, lastBrushSize;
	private boolean erase = false;
	private int mWidth;
	private int mHeight;
	private float mAngle;
	// strokes drawn so far (the last MAX strokes)
	private ArrayList<Stroke> undoStrokes = new ArrayList<Stroke>();

	// strokes that were undone.
	private ArrayList<Stroke> undoneStrokes = new ArrayList<Stroke>();
	static final int MAX = 50;
	private Stroke currentStroke;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
		mContext=context;
        setupDrawing();
    }
    private void setupDrawing() {
		brushSize = getResources().getInteger(R.integer.medium_size);
		lastBrushSize = brushSize;

		drawPath = new Path();
		drawPaint = new Paint();

		drawPaint.setColor(paintColor);
		drawPaint.setAntiAlias(true);
		drawPaint.setStrokeWidth(brushSize);
		drawPaint.setStyle(Paint.Style.STROKE);
		drawPaint.setStrokeJoin(Paint.Join.ROUND);
		drawPaint.setStrokeCap(Paint.Cap.ROUND);

		canvasPaint = new Paint(Paint.DITHER_FLAG);
		currentStroke = new Stroke();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    	super.onSizeChanged (w, h, oldw, oldh);
		canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		drawCanvas = new Canvas(canvasBitmap);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {

		int cx = (mWidth - canvasBitmap.getWidth()) >> 1; // same as (...) / 2
		int cy = (mHeight - canvasBitmap.getHeight()) >> 1;

		if (mAngle > 0) {
			canvas.rotate(mAngle, mWidth >> 1, mHeight >> 1);
		}

		canvas.drawBitmap(canvasBitmap, cx, cy, null);
		//canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
		canvas.drawPath(drawPath, drawPaint);
		invalidate();
    }

	// when undo is click we call this
	protected boolean onClickUndo() {
		boolean ret = false;

		// remove from undoStrokes list, add it to undoneStrokes list
		if (undoStrokes.size() > 0) {
			Stroke theStroke = undoStrokes.remove(undoStrokes.size()-1);
            Paint mypaint = new Paint();
			undoneStrokes.add(theStroke);

			// clear the canvas
			drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);

			// draw the strokes that we wanted.
			for (int i = 0; i < undoStrokes.size() ; i++) {
                undoStrokes.get(i).paint.setXfermode(null);
                undoStrokes.get(i).paint.setColor(undoStrokes.get(i).color);
				undoStrokes.get(i).paint.setStrokeWidth(undoStrokes.get(i).brushSize);
				drawCanvas.drawPath(undoStrokes.get(i).path, undoStrokes.get(i).paint);
				invalidate();
			}

			ret = true;
		}
		return ret;
	}

	protected boolean onClickRedo() {
		Boolean ret = false;
		if (undoneStrokes.size() > 0) {
			Stroke theStroke = undoneStrokes.remove(undoneStrokes.size()-1);
			undoStrokes.add(theStroke);

            theStroke.paint.setColor(theStroke.color);
			theStroke.paint.setStrokeWidth(theStroke.brushSize);
			// draw it!
			drawCanvas.drawPath(theStroke.path, theStroke.paint);
			invalidate();
			ret = true;
		}
		return ret;
	}

    @Override
    public boolean onTouchEvent(MotionEvent event) {
		float touchX = event.getX();
      	float touchY = event.getY();

    	switch (event.getAction()) {
		// user touches a location, move there
		case MotionEvent.ACTION_DOWN:
			currentStroke = new Stroke();
    		drawPath.moveTo(touchX, touchY);
    		break;
		// draw line when move
		case MotionEvent.ACTION_MOVE:
    		drawPath.lineTo(touchX, touchY);
    		break;
		// user releases the touch
		case MotionEvent.ACTION_UP:
    		drawCanvas.drawPath(drawPath, drawPaint);
			invalidate();

			// keep track of the last MAX strokes if we're not erasing.
			if (erase == false) {
				currentStroke.setPaint(drawPaint);
				currentStroke.setColor(paintColor);
				currentStroke.setPath(drawPath);
				currentStroke.setBrushSize(brushSize);

				if (undoStrokes.size() < MAX) {
					undoStrokes.add(currentStroke);
				} else {
					// shift the first stroke off the undolist. We only keep track of MAX strokes.
					ArrayList<Stroke> tmps = new ArrayList<Stroke>(undoStrokes.subList(1, MAX));
					undoStrokes = tmps;
					undoStrokes.add(currentStroke);
				}
			}
			drawPath = new Path();
			drawPath.reset();

    		break;
		default:
    		return false;
		}
		invalidate();
		return true;
	
    }
	public void setBrushSize(float newSize) {
		brushSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				newSize, getResources().getDisplayMetrics());
		drawPaint.setStrokeWidth(brushSize);
	}

	public void setLastBrushSize(float lastSize) {
		lastBrushSize = lastSize;
	}

	public float getLastBrushSize() {
		return lastBrushSize;
	}

	public void setColor(String newColor) {
		invalidate();
		paintColor = Color.parseColor(newColor);
		drawPaint.setColor(paintColor);
	}

	public int getColor () {
		return paintColor;
	}

	public void setErase(boolean isErase) {
		erase = isErase;

		if(erase)
			drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		else
			drawPaint.setXfermode(null);
	}

	public void startNew(Bitmap path){

		undoneStrokes = new ArrayList<Stroke>();
		undoneStrokes.clear();

		undoStrokes = new ArrayList<Stroke>();
		undoStrokes.clear();

		if (path == null) {
			drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);

		} else {



			DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
			int width = metrics.widthPixels;
			int height=metrics.heightPixels;

			//Bitmap workingBitmap = 	BitmapFactory.decodeFile(path);
			Bitmap mutableBitmap = path.copy(Bitmap.Config.ARGB_8888,true);
			//drawCanvas.drawBitmap(mutableBitmap,0 , 0, canvasPaint);
			drawCanvas.drawBitmap(mutableBitmap, null, new RectF(0, 0, width, height), null);
			setImageBitmap(mutableBitmap);


			//Bitmap resizeBitmap=resizeImageForImageView(path);
			//Bitmap workingBitmap = 	BitmapFactory.decodeFile(path);
			//Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);

//			drawCanvas.drawBitmap(resizeBitmap, 0, 0, canvasPaint);
//			setImageBitmap(path);

		}
		invalidate();
		setErase(false);
	}

	@Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		mWidth = View.MeasureSpec.getSize(widthMeasureSpec);
		mHeight = View.MeasureSpec.getSize(heightMeasureSpec);

		setMeasuredDimension(mWidth, mHeight);
	}
	int scaleSize =1024;

	public Bitmap resizeImageForImageView(Bitmap bitmap) {
		Bitmap resizedBitmap = null;
		int originalWidth = bitmap.getWidth();
		int originalHeight = bitmap.getHeight();
		int newWidth = -1;
		int newHeight = -1;
		float multFactor = -1.0F;
		if(originalHeight > originalWidth) {
			newHeight = scaleSize ;
			multFactor = (float) originalWidth/(float) originalHeight;
			newWidth = (int) (newHeight*multFactor);
		} else if(originalWidth > originalHeight) {
			newWidth = scaleSize ;
			multFactor = (float) originalHeight/ (float)originalWidth;
			newHeight = (int) (newWidth*multFactor);
		} else if(originalHeight == originalWidth) {
			newHeight = scaleSize ;
			newWidth = scaleSize ;
		}
		resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
		return resizedBitmap;
	}
}
