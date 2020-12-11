package com.dunneev.seenatural.Fragments.Staff;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.dunneev.seenatural.Enums.KeySignature;
import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.R;
import com.dunneev.seenatural.Utilities.TextDrawable;

public class StaffNote extends View {

    private static final String LOG_TAG = StaffNote.class.getSimpleName();

    private KeySignature keySignature;
    public PianoNote note;

    private boolean isAccidental;
    private String accidentalSymbol;
    private TextDrawable noteDrawable;

    private Rect noteBoundsRect = new Rect();

    private static int desiredWidth = 500;
    private static int desiredHeight = 500;


    public static int getDesiredWidth() {
        return desiredWidth;
    }

    public static void setDesiredWidth(int desiredWidth) {
        StaffNote.desiredWidth = desiredWidth;
    }

    public static int getDesiredHeight() {
        return desiredHeight;
    }

    public static void setDesiredHeight(int desiredHeight) {
        StaffNote.desiredHeight = desiredHeight;
    }

    // todo: draw a ledger line when necessary
    public StaffNote(Context context, KeySignature keySignature, PianoNote note) {
        super(context);
        this.note = note;
        this.keySignature = keySignature;
        init();
    }

    public StaffNote(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.note = PianoNote.B_FLAT_4;
        this.keySignature = KeySignature.C_MAJOR;
        init();
    }

    private void init() {



        if (PianoNote.isAccidental(note, keySignature)) {
//            setAccidentalSymbol();
            noteDrawable = new TextDrawable(note.symbol + getResources().getString(R.string.char_quarter_note), TextDrawable.PositioningInBounds.DEFAULT);
        }

        else {
            noteDrawable = new TextDrawable(getResources().getString(R.string.char_quarter_note), TextDrawable.PositioningInBounds.DEFAULT);
        }
    }

    private void setAccidentalSymbol() {
//        if (note.isFlat) {
//            accidentalSymbol = getResources().getString(R.string.char_flat_symbol);
//        }
//        if (note.isNatural) {
//            accidentalSymbol = getResources().getString(R.string.char_natural_symbol);
//        }
//        if (note.isSharp) {
//            accidentalSymbol = getResources().getString(R.string.char_sharp_symbol);
//        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width;
        int height;

        // Staff notes should always be passed visibleStaffHeight.EXACTLY for height.
        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        // Width is be dependent on the length of text, type of note, and height of text.
        desiredWidth = (int) (height * noteDrawable.getAspectRatio());

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        noteBoundsRect.set(0,0, getMeasuredWidth(), getMeasuredHeight());
//        Paint blackPaint = new Paint();
//        blackPaint.setColor(Color.BLACK);
//        canvas.drawRect(0,0, getMeasuredWidth(), getMeasuredHeight(), blackPaint);

        noteDrawable.setBounds(noteBoundsRect);
        noteDrawable.draw(canvas);

    }

    public void setColor(int color) {
        noteDrawable.setColor(color);
    }
}
