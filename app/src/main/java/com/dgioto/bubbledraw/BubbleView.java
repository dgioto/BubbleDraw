package com.dgioto.bubbledraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Random;

public class BubbleView extends androidx.appcompat.widget.AppCompatImageView
        implements View.OnTouchListener {
    //генератор случайных чисел
    private final Random rand = new Random();
    //объявляем динамический масив
    private final ArrayList<Bubble> bubbleList;
    //размер пузырька
    private final int size = 50;
    //скорость кадрав в секунду
    private final int delay = 33;
    //кисть для рисования пузырьков
    private final Paint myPaint = new Paint();
    //объект для работы с многопоточностью для выполнения анимации
    private final Handler h = new Handler();

    public BubbleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        bubbleList = new ArrayList<Bubble>();
        //тестируем приложение
        //testBubbles();
        setOnTouchListener(this);
    }

    //создаем многопоточность
    private final Runnable r = new Runnable() {
        @Override
        public void run() {
            //обходим динамический массив для обновления координат конкретного пузырька
            // для следующего кадра анимации
            for (Bubble b : bubbleList) b.update();
            invalidate();
        }
    };
    //вызывавается каждый раз, когда экран BubbleView нуждается в обновлении
    protected void onDraw(Canvas canvas){
        //для каждого объекта b класса Bubble в динамическом массиве bubbleList нарисуйте b на
        // объекте Canvas устройства Android
        for (Bubble b : bubbleList) b.draw(canvas);
        //отправляет сообщение от обработчика в наш поток r, указывая ему снова запуститься
        //в 33 миллисекунды
        h.postDelayed(r, delay);
    }

    //рисование касанием
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //обработка множественных касаний
        for (int n = 0; n < motionEvent.getPointerCount(); n++) {
            //получаем координаты касания x, y
            int x = (int) motionEvent.getX(n);
            int y = (int) motionEvent.getY(n);
            //генерируем случайный размер
            int s = rand.nextInt(size) + size;
            bubbleList.add(new Bubble(x, y, s));
        }
        //true при полной обработке события касания, false  при прокрутке или маштабировании
        return true;
    }

    private class Bubble{
        private int x;
        private int y;
        private final int size;
        private final int color;
        private int xspeed, yspeed;

        public  Bubble(int newX, int newY, int newSize){
            x = newX;
            y = newY;
            size = newSize;
            //устанавливаем случайный цвет
            color = Color.argb(
                    rand.nextInt(256),
                    rand.nextInt(256),
                    rand.nextInt(256),
                    rand.nextInt(256));
        }

        public void draw(Canvas canvas){
            //устанавливаем цвет кисти случайным способом
            myPaint.setColor(color);
            //рисуем овал, указываем ограничесвающий прямоугольник
            canvas.drawOval(x - size/2, y - size/2,
                    x + size/2, y + size/2, myPaint);
        }

        public void update(){
            x += xspeed;
            y += yspeed;
        }
    }
}
