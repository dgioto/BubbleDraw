package com.example.bubbledraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Random;

//ПОЧЕМУ ImageView ПЕРЕПИСАЛСЯ В ТАКОЙ ВИД??????
public class BubbleView extends androidx.appcompat.widget.AppCompatImageView implements View.OnTouchListener {

    private Random rand = new Random();
    private ArrayList<Bubble> bubbleList;
    private int size = 50;
    private  int delay = 33;
    private Paint myPaint = new Paint();
    private Handler h = new Handler();

    public BubbleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        bubbleList = new ArrayList<Bubble>();
        testBubbles();
    }

    protected void onDraw(Canvas canvas){
        for (Bubble b : bubbleList) b.draw(canvas);
    }

    public void testBubbles(){
        for (int n = 0; n < 100; n++){
            int x = rand.nextInt(600);
            int y = rand.nextInt(600);
            int s = rand.nextInt(size) + size;
            bubbleList.add(new Bubble(x, y, s));
        }
        invalidate();
    }

    //ЧТО ЭТО ЗА МЕТОД????
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    private class Bubble{
        private int x;
        private int y;
        private int size;
        private int color;
        private int xspeed, yspeed;
        private final int MAX_SPEED = 5;

        public  Bubble(int newX, int newY, int newSize){
            x = newX;
            y = newY;
            size = newSize;
            color = Color.argb(rand.nextInt(256),
                    rand.nextInt(256),
                    rand.nextInt(256),
                    rand.nextInt(256));
            xspeed = rand.nextInt(MAX_SPEED * 2 + 1) - MAX_SPEED;
            yspeed = rand.nextInt(MAX_SPEED * 2 + 1) - MAX_SPEED;
        }

        public void draw(Canvas canvas){
            myPaint.setColor(color);
            canvas.drawOval(x - size/2, y - size/2,
                    x + size/2, y + size/2, myPaint);
        }

        public void update(){
            x += xspeed;
            y += yspeed;
        }
    }
}
