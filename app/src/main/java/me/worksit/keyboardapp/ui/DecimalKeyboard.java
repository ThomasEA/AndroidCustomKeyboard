package me.worksit.keyboardapp.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.List;

import me.worksit.keyboardapp.R;

public class DecimalKeyboard extends KeyboardView {

    private Keyboard keyboard;
    private EnumInputType type;

    public DecimalKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DecimalKeyboard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setInputType(EnumInputType type) {
        this.type = type;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        List<Keyboard.Key> keys = getKeyboard().getKeys();

        for (Keyboard.Key key : keys) {
            if (key.codes[0] == -2) {
                Log.e("KEY", "Drawing key with code " + key.codes[0]);
                drawKeyBackground(R.drawable.button_confirm_no_border, canvas, key);
                drawText(canvas, key);
            }
        }
    }

    private void drawKeyBackground(int drawableId, Canvas canvas, Keyboard.Key key) {
        Drawable npd = super.getContext().getResources().getDrawable(
                drawableId);
        int[] drawableState = key.getCurrentDrawableState();
        if (key.codes[0] != 0) {
            npd.setState(drawableState);
        }
        npd.setBounds(key.x, key.y, key.x + key.width, key.y
                + key.height);
        npd.draw(canvas);
    }

    private void drawText(Canvas canvas, Keyboard.Key key) {
        Rect bounds = new Rect();
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);


        paint.setAntiAlias(true);

        paint.setColor(Color.WHITE);
        if (key.label != null) {
            String label = key.label.toString();

            Field field;

            if (label.length() > 1 && key.codes.length < 2) {
                float labelTextSize = 0;
                try {
                    field = KeyboardView.class.getDeclaredField("mLabelTextSize");
                    field.setAccessible(true);
                    labelTextSize = super.getContext().getResources().getDimension(R.dimen.size_keyboard_keypad);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                paint.setTextSize(labelTextSize);
                paint.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                float keyTextSize = 0;
                try {
                    field = KeyboardView.class.getDeclaredField("mLabelTextSize");
                    field.setAccessible(true);
                    keyTextSize = super.getContext().getResources().getDimension(R.dimen.size_keyboard_keypad);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                paint.setTextSize(keyTextSize);
                paint.setTypeface(Typeface.DEFAULT);
            }

            paint.getTextBounds(key.label.toString(), 0, key.label.toString()
                    .length(), bounds);
            canvas.drawText(key.label.toString(), key.x + (key.width / 2),
                    (key.y + key.height / 2) + bounds.height() / 2, paint);
        } else if (key.icon != null) {
            key.icon.setBounds(key.x + (key.width - key.icon.getIntrinsicWidth()) / 2, key.y + (key.height - key.icon.getIntrinsicHeight()) / 2,
                    key.x + (key.width - key.icon.getIntrinsicWidth()) / 2 + key.icon.getIntrinsicWidth(), key.y + (key.height - key.icon.getIntrinsicHeight()) / 2 + key.icon.getIntrinsicHeight());
            key.icon.draw(canvas);
        }

    }

    public enum EnumInputType {
        Numeric, Decimal
    }
}