package com.dunneev.seenatural.Fragments.Staff;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.dunneev.seenatural.Enums.KeySignature;
import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class StaffView extends ViewGroup {

    public static final String LOG_TAG = StaffView.class.getSimpleName();
//    public static final int TYPE_TREBLE_CLEF = 0;
//    public static final int TYPE_BASS_CLEF = 1;
//    public static final int TYPE_BOTH_CLEF = 2;

    HorizontalScrollView scrollView;
    public LinearLayout noteLinearLayout;

    private KeySignature keySignature;
    private PianoNote lowestPracticeNote;
    private PianoNote highestPracticeNote;
    private ArrayList<PianoNote> notesOnStaff = new ArrayList<>();
    private ArrayList<PianoNote> staffLines = new ArrayList<>();
    private int currentNoteIndex;

    private boolean hideKeySignature;
    private boolean hideTrebleClef;
    private boolean hideTrebleClefLines;
    private boolean hideBassClef;
    private boolean hideBassClefLines;
    int clefWidth;

    // The distance between natural notes e.g. A4 to B4
    int staffLineSpacing;
    int staffNoteHorizontalMargins = 100;
    int visibleStaffHeight;
    int totalStaffHeight;
    int noteWidth;
    private static final Map<PianoNote, Integer> noteStaffCoordinateMap = new HashMap<>();



    public KeySignature getKeySignature() {
        return keySignature;
    }

    public void setKeySignature(KeySignature keySignature) {
        this.keySignature = keySignature;
//        init();
    }

    // TODO: 11/17/2020 Create specialized method to redraw staff after setting practice notes instead of init()
    // Ensure that invalidate and requestLayout are called
    public PianoNote getLowestPracticeNote() {
        return lowestPracticeNote;
    }

    public void setLowestPracticeNote(PianoNote lowestPracticeNote) {
        this.lowestPracticeNote = lowestPracticeNote;
    }

    public PianoNote getHighestPracticeNote() {
        return highestPracticeNote;
    }

    public void setHighestPracticeNote(PianoNote highestPracticeNote) {
        this.highestPracticeNote = highestPracticeNote;
    }

    public void setNotesOnStaff(ArrayList<PianoNote> notesOnStaff) {
        this.notesOnStaff = notesOnStaff;
    }

    public void setStaffLines(ArrayList<PianoNote> staffLines) {
        this.staffLines = staffLines;
    }

    public void setCurrentNoteIndex(int currentNoteIndex) {
        this.currentNoteIndex = currentNoteIndex;
    }



    public void setHideKeySignature(boolean hideKeySignature) {
        this.hideKeySignature = hideKeySignature;
    }

    public void setHideTrebleClef(boolean hideTrebleClef) {
        this.hideTrebleClef = hideTrebleClef;
    }

    public void setHideTrebleClefLines(boolean hideTrebleClefLines) {
        this.hideTrebleClefLines = hideTrebleClefLines;
    }

    public void setHideBassClef(boolean hideBassClef) {
        this.hideBassClef = hideBassClef;
    }

    public void setHideBassClefLines(boolean hideBassClefLines) {
        this.hideBassClefLines = hideBassClefLines;
    }






    public StaffView(Context context, PianoNote lowestPracticeNote, PianoNote highestPracticeNote) {
        super(context);

        this.lowestPracticeNote = lowestPracticeNote;
        this.highestPracticeNote = highestPracticeNote;

        init();
    }

    public StaffView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);

        // This section is mostly just so that the XML preview shows the staff
        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.StaffView);


        // Choosing the clef would override the selected note range,
        // so I removed the attribute. Individual notes are more flexible anyway.
//        if (styledAttributes.getInt(R.styleable.StaffView_selectedClef, 0) == TYPE_TREBLE_CLEF) {
//            this.lowestPracticeNote = PianoNote.C4;
//            this.highestPracticeNote = PianoNote.C6;
//        }
//        else if (styledAttributes.getInt(R.styleable.StaffView_selectedClef, 0) == TYPE_BASS_CLEF) {
//            this.lowestPracticeNote = PianoNote.G2;
//            this.highestPracticeNote = PianoNote.G4;
//        }
//        else if (styledAttributes.getInt(R.styleable.StaffView_selectedClef, 0) == TYPE_BOTH_CLEF) {
//            this.lowestPracticeNote = PianoNote.G2;
//            this.highestPracticeNote = PianoNote.C6;
//        }


        keySignature = KeySignature.valueOfStoredOrdinal(styledAttributes.getInt(R.styleable.StaffView_keySignature, 7));
        hideKeySignature = styledAttributes.getBoolean(R.styleable.StaffView_hideKeySignature, false);

        hideTrebleClef = styledAttributes.getBoolean(R.styleable.StaffView_hideTrebleClef, false);
        hideTrebleClefLines = styledAttributes.getBoolean(R.styleable.StaffView_hideTrebleClefLines, false);
        hideBassClef = styledAttributes.getBoolean(R.styleable.StaffView_hideBassClef, false);
        hideBassClefLines = styledAttributes.getBoolean(R.styleable.StaffView_hideBassClefLines, false);

//        displayFlats = styledAttributes.getBoolean(R.styleable.StaffView_displayFlats, true);
//        displayNaturals = styledAttributes.getBoolean(R.styleable.StaffView_displayNaturals, true);
//        displaySharps = styledAttributes.getBoolean(R.styleable.StaffView_displaySharps, true);


        lowestPracticeNote = PianoNote.valueOfAbsoluteKeyIndex(styledAttributes.getInt(R.styleable.StaffView_staffLowPracticeNote, 0));
        highestPracticeNote = PianoNote.valueOfAbsoluteKeyIndex(styledAttributes.getInt(R.styleable.StaffView_staffHighPracticeNote, 87));



        styledAttributes.recycle();

        init();
    }

    public void init() {
        removeAllViews();

        populateStaffLines();
        addStaffLinesToView();
        addClefsToView();
        addNoteScrollerToView();
        addNotesOnStaffToView();

        // The treble clef is taller than the staff, but the clipped parts should still be visible.
        // The bounding box (which sets the font size) is only as tall as the staff.
        setClipChildren(false);
    }

    private void populateStaffLines() {
        staffLines.clear();
        for (int i=highestPracticeNote.absoluteKeyIndex; i>=lowestPracticeNote.absoluteKeyIndex;i--){
            PianoNote note = PianoNote.valueOfAbsoluteKeyIndex(i);
            if (note.isWhiteKey)
                staffLines.add(note);
        }
    }

    private void addStaffLinesToView() {
        StaffLine line;
        StaffLine.hideTrebleClefLines = hideTrebleClefLines;
        StaffLine.hideBassClefLines = hideBassClefLines;


        for (PianoNote note: staffLines) {

            // Staff lines are only ever "natural" (white).
            // Whether they are sharp or flat is signified by
            // either the key signature or a ♯/♮/♭ symbol if the note in question is an accidental.

            line = new StaffLine(getContext(), note);
            LayoutParams staffLineParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            addView(line, staffLineParams);

        }
    }

    private void addClefsToView() {
        Log.i(LOG_TAG, "keySignature: " + keySignature);

        StaffClef trebleClef = new StaffClef(getContext(), getResources().getString(R.string.treble), keySignature);

        StaffClef bassClef = new StaffClef(getContext(), getResources().getString(R.string.bass), keySignature);

        trebleClef.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        bassClef.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        addView(trebleClef);
        addView(bassClef);
    }

    private void addNoteScrollerToView() {

        // Create scroll view
        scrollView = new HorizontalScrollView(getContext());
        LayoutParams scrollViewParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(scrollViewParams);

        // Create linear layout for scrollable elements
        noteLinearLayout = new LinearLayout(getContext());

        FrameLayout.LayoutParams noteLinearLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
        noteLinearLayout.setLayoutParams(noteLinearLayoutParams);
        noteLinearLayout.setOrientation(LinearLayout.HORIZONTAL);


        // Prevent accidental symbols from getting clipped
        noteLinearLayout.setClipChildren(false);

        // Setting StaffView.clipChildren to false to display the whole clef resulted in notes scrolling under the clef.
        // Setting scrollView.clipChildren didn't seem to do anything.
        // This is a workaround for clipping notes as they approach the staff.
        scrollView.setPadding(-1,0,0,0);
        scrollView.setClipToPadding(true);

        scrollView.addView(noteLinearLayout);

        addView(scrollView);
    }

    public void addNotesOnStaffToView() {
        noteLinearLayout.removeAllViews();
        for (PianoNote note:notesOnStaff) {
            addNote(note);
        }
    }








    // TODO: 11/24/2020
    public void highlightCurrentNote() {
//        StaffNote note = (StaffNote) noteLinearLayout.getChildAt(noteScrollCounter);

    }

    public void addNote(PianoNote note) {
        StaffNote staffNote = new StaffNote(getContext(), keySignature, note);


        // noteStaffCoordinateMap only contains coordinates for non-accidental notes,
        // which is why we use the natural note field to determine the position.
        LinearLayout.LayoutParams staffNoteParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT/*visibleStaffHeight*/);
        staffNote.setLayoutParams(staffNoteParams);

        staffNoteParams.setMargins(0, 0, staffNoteHorizontalMargins, 0);

//        staffNote.setTranslationY(noteStaffCoordinateMap.get(PianoNote.valueOfLabel(note.naturalNoteLabel)) - visibleStaffHeight + staffLineSpacing);


        noteLinearLayout.addView(staffNote);
    }

    protected void removeNote(PianoNote note) {

    }

    private void markPreviousNotesCorrect() {
        for (int i=0;i<currentNoteIndex;i++){
            markNoteCorrect(i);
        }
    }

    public void markNoteCorrect(int index) {
        StaffNote note = (StaffNote) noteLinearLayout.getChildAt(index);
        note.setColor(Color.GREEN);
//        note.setAlpha(.5f);
        note.invalidate();
    }

    public void markNoteIncorrect(int index, PianoNote incorrectNote) {
        StaffNote note = (StaffNote) noteLinearLayout.getChildAt(index);
        note.setColor(Color.RED);
//        note.setAlpha(.5f);
        note.invalidate();

//        StaffNote ghostNote = new StaffNote(getContext(), keySignature, incorrectNote);
//        LinearLayout.LayoutParams staffNoteParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT/*visibleStaffHeight*/);
//        ghostNote.setLayoutParams(staffNoteParams);
//
//        ghostNote.setColor(Color.RED);
//        ghostNote.setAlpha(.4f);
//
//        staffNoteParams.setMargins(0, 0, staffNoteHorizontalMargins, 0);
//
//        noteLinearLayout.addView(ghostNote, index + 1);
//        ghostNote.setX(-(noteLinearLayout.getChildAt(index).getRight() + staffNoteHorizontalMargins));
//        ghostNote.setTranslationY(noteStaffCoordinateMap.get(PianoNote.valueOfLabel(incorrectNote.naturalNoteLabel)) - visibleStaffHeight + staffLineSpacing);


    }

    private void addGhostNote(PianoNote incorrectNote, int index) {

        //        staffNote.setTranslationY(noteStaffCoordinateMap.get(PianoNote.valueOfLabel(note.naturalNoteLabel)) - visibleStaffHeight + staffLineSpacing);

    }

    // TODO: 11/18/2020 Set up customizable scroll/keep previous note in view on scroll 
//    public void scrollToNextNote() {
//        scrollToNote(++noteScrollCounter);
//    }

    public void scrollToNote(int index) {

        // Keep the previous note in sight
//        View child = noteLinearLayout.getChildAt(index - 1);

        View child = noteLinearLayout.getChildAt(index);
        if (child != null) {
            scrollView.smoothScrollTo(child.getLeft(), 0);
        }
    }




    /**
     * Calculate size of StaffView and all children
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);

        staffLineSpacing = height / staffLines.size();
        staffNoteHorizontalMargins = staffLineSpacing * 4;

        visibleStaffHeight = staffLineSpacing * 8;
        totalStaffHeight = staffLineSpacing * PianoNote.numberOfKeysInRangeInclusive(lowestPracticeNote, highestPracticeNote);
        noteWidth = staffLineSpacing * 3;

        StaffLine.setDesiredHeight(staffLineSpacing);
        StaffClef.setDesiredHeight(visibleStaffHeight);
        StaffNote.setDesiredHeight(visibleStaffHeight);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * Position all children within this layout.
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        int childLeft = 100;
        int childTop = 100;
        int childRight = 300;
        int childBottom = 300;

        // Only white notes take up space
        int clippedHighStaffNoteCount= PianoNote.numberOfWhiteKeysInRangeInclusive(highestPracticeNote, PianoNote.HIGHEST_NOTE);
        int staffLineYCoordinate = -(clippedHighStaffNoteCount * staffLineSpacing);


        // I've decided to map every note instead of available practice notes.
        // It's more flexible - a clef can be drawn partially off the screen, for example.
        for (int i=87;i>=0;i--) {
            PianoNote note = PianoNote.valueOfAbsoluteKeyIndex(i);

            noteStaffCoordinateMap.put(note, staffLineYCoordinate);

            if (note.isWhiteKey) {
                staffLineYCoordinate += (staffLineSpacing);
            }
        }


        final int childCount = getChildCount();

        if (childCount == 0) {
            return;
        }

        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);

            if (child.getClass() == StaffLine.class) {

                childLeft = 0;
//                noteStaffMap stores the actual position of the staff line, not its bounds
                childTop = noteStaffCoordinateMap.get(((StaffLine) child).note) - (staffLineSpacing / 2);
                childRight = child.getMeasuredWidth();
                childBottom = childTop + child.getMeasuredHeight();
            }

            else if (child.getClass() == StaffClef.class) {

                if (((StaffClef) child).getClef().equals(getResources().getString(R.string.treble))) {
                    childLeft = 0;
                    childTop = noteStaffCoordinateMap.get(PianoNote.F5);
                    childRight = child.getMeasuredWidth();
                    childBottom = childTop + child.getMeasuredHeight();
                }

                else if (((StaffClef) child).getClef().equals(getResources().getString(R.string.bass))) {
                    childLeft = 0;
                    childTop = noteStaffCoordinateMap.get(PianoNote.A3);
                    childRight = child.getMeasuredWidth();
                    childBottom = childTop + child.getMeasuredHeight();
                }

                // TODO: 11/24/2020 Create separate noteScroller for each clef
                clefWidth = childRight;
            }



            // NoteScroller
            else if (child.getClass() == HorizontalScrollView.class) {

                // Right now, there's some space between the treble clef and the scroller,
                // because the bass clef is a bit wider. The scroller's left coordinate gets set to
                // the bass clef.
                childLeft = clefWidth;
                childTop = 0;
                childRight = getMeasuredWidth();
                childBottom = getMeasuredHeight();

            }

            else {
                Log.i(LOG_TAG, child.getClass().toString() + " was not laid out");
            }

            child.layout(childLeft, childTop, childRight, childBottom);
        }

        // Adjust everything that was originally set in addNote because when using fragments,
        // there's no (intuitive) way to find the dimensions of a view within that fragment
        for (int i=0;i<noteLinearLayout.getChildCount();i++) {
            StaffNote staffNote = (StaffNote) noteLinearLayout.getChildAt(i);

            staffNote.setTranslationY(noteStaffCoordinateMap.get(PianoNote.valueOfLabel(staffNote.note.naturalNoteLabel)) - visibleStaffHeight + staffLineSpacing);
        }

        // Again, not sure if this is the right place for it, but on rotation,
        // no dimensions for notes are set, so the ScrollView always returns to the beginning

        // Apparently if you set an id for noteLinearLayout and set the data before layout,
        // it will retain the scroll position. Didn't have immediate success, so I'm not going to
        // waste time on this right now.
        if (noteLinearLayout.getChildCount()!=0) {
            scrollToNote(currentNoteIndex);
            markPreviousNotesCorrect();
        }

    }


}

