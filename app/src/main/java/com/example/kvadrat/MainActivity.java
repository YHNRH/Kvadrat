package com.example.kvadrat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final int IDM_RED = 101;
    public static final int IDM_GREEN = 201;
    public static final int IDM_BLACK = 202;
    public static final int IDM_REST = 203;
    int message = Color.BLACK;
    MyView mv;
    float x = 100;
    float y = 100;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        SubMenu subMenuColor = menu.addSubMenu("Цвет");
        subMenuColor.add(Menu.NONE, IDM_RED, Menu.NONE, "Черный");
        subMenuColor.add(Menu.NONE, IDM_GREEN, Menu.NONE, "Красный");
        subMenuColor.add(Menu.NONE, IDM_BLACK, Menu.NONE, "Зеленый");
        menu.add(Menu.NONE, IDM_REST, Menu.NONE, "Заново");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case IDM_RED:
                message = Color.BLACK;
                break;
            case IDM_GREEN:
                message = Color.RED;
                break;
            case IDM_BLACK:
                message = Color.GREEN;
                break;
            case IDM_REST:
                x = 100;
                y = 100;
                break;
            default:
                return false;
        }
        Toast t = Toast.makeText(getApplicationContext(), "МАРИНА СЕРГЕЕВНА ОЧЕНЬ КРАСИВАЯ СЕГОДНЯ", Toast.LENGTH_LONG );
        t.show();
        mv.invalidate();

        return true;
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mv= new MyView(this);
        setContentView(mv);
    }

    class MyView extends View {

        Paint p;
        // координаты для рисования квадрата

        int side = 100;

        // переменные для перетаскивания
        boolean drag = false;
        float dragX = 0;
        float dragY = 0;

        public MyView(Context context) {
            super(context);
            p = new Paint();
        }

        protected void onDraw(Canvas canvas) {
            // рисуем квадрат
            p.setColor(message);
            canvas.drawRect(x, y, x + side, y + side, p);

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            // координаты Touch-события
            float evX = event.getX();
            float evY = event.getY();

            switch (event.getAction()) {
                // касание началось
                case MotionEvent.ACTION_DOWN:
                    // если касание было начато в пределах квадрата
                    if (evX >= x && evX <= x + side && evY >= y && evY <= y + side) {
                        // включаем режим перетаскивания
                        drag = true;
                        // разница между левым верхним углом квадрата и точкой касания
                        dragX = evX - x;
                        dragY = evY - y;
                    }
                    break;
                // тащим
                case MotionEvent.ACTION_MOVE:
                    // если режим перетаскивания включен
                    if (drag) {
                        // определеяем новые координаты для рисования
                        x = evX - dragX;
                        y = evY - dragY;
                        // перерисовываем экран
                        invalidate();
                    }
                    break;
                // касание завершено
                case MotionEvent.ACTION_UP:
                    // выключаем режим перетаскивания
                    drag = false;
                    break;
            }
            return true;
        }
    }

}