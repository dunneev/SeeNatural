package com.dunneev.seenatural.Utilities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class TextDrawable extends Drawable {

    private static final String LOG_TAG = TextDrawable.class.getSimpleName();

    public enum PositioningInBounds {
        DEFAULT,
        CENTER,
        LEFT,
        TOP,
        RIGHT,
        BOTTOM
    }


    private int color = Color.WHITE;
    private static final int DEFAULT_TEXTSIZE = 100;
    private Paint paint;
    private CharSequence text;
    PositioningInBounds positioningInBounds;
    private float intrinsicWidth;
    private float intrinsicHeight;
    private float aspectRatio;

    public Paint getPaint() {
        return paint;
    }

    public void setColor(int color) {
        this.color = color;
        paint.setColor(color);
    }

    public TextDrawable(CharSequence text, PositioningInBounds positioningInBounds) {
        this.text = text;
        this.positioningInBounds = positioningInBounds;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextAlign(Paint.Align.LEFT);
        intrinsicWidth = (paint.measureText(text, 0, text.length()) + 1);
        intrinsicHeight = paint.getFontMetrics(null);
        aspectRatio = intrinsicWidth/intrinsicHeight;
    }
    @Override
    public void draw(Canvas canvas) {

        Rect drawableBounds = getBounds();

//         Just testing to see the drawableBounds are correct
        Paint boundsPaint = new Paint();
        boundsPaint.setColor(Color.YELLOW);
        boundsPaint.setAlpha(30);
        canvas.drawRect(drawableBounds, boundsPaint);


        paint.setTextSize(drawableBounds.height());

        Rect textBounds = new Rect();
        float x;
        float y;

        switch (positioningInBounds) {
            case LEFT:
                break;
            case TOP:
                paint.getTextBounds((String) text, 0, text.length(), textBounds);
                x = drawableBounds.left;
                y = drawableBounds.bottom - paint.descent() / 2;
                canvas.drawText((String) text, x, y, paint);

                break;
            case RIGHT:
                break;
            case BOTTOM:
                paint.getTextBounds((String) text, 0, text.length(), textBounds);
                x = drawableBounds.left;
                y = drawableBounds.bottom;
                canvas.drawText((String) text, x, y, paint);
                break;
            case CENTER:

                paint.getTextBounds((String) text, 0, text.length(), textBounds);
//                x = drawableBounds.left + (drawableBounds.width() / 2f)  - (textBounds.width() / 2f) - textBounds.left;
                x = drawableBounds.left;
                y = drawableBounds.top + (drawableBounds.height() / 2f) + (textBounds.height() / 2f) - textBounds.bottom;
                canvas.drawText((String) text, x, y, paint);

                break;

            case DEFAULT:
                canvas.drawText(text, 0, text.length(),
                        drawableBounds.left, drawableBounds.bottom, paint);
                break;
        }
    }

    public float getAspectRatio() {
        return aspectRatio;
    }
    @Override
    public int getOpacity() {
        return paint.getAlpha();
    }
    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }
    @Override
    public void setColorFilter(ColorFilter filter) {
        paint.setColorFilter(filter);
    }
}
