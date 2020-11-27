package com.dunneev.seenatural.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dunneev.seenatural.Enums.KeySignature;
import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.Fragments.Piano.PianoKey;
import com.dunneev.seenatural.Fragments.Piano.PianoView;
import com.dunneev.seenatural.R;
import com.dunneev.seenatural.Utilities.SoundPlayer;
import com.dunneev.seenatural.Fragments.Staff.StaffView;

import java.util.ArrayList;
import java.util.Random;

public class SightReadingActivity extends AppCompatActivity implements PianoKey.PianoKeyListener {

    private static final String LOG_TAG = SightReadingActivity.class.getSimpleName();

    static {
        System.loadLibrary("native-lib");
    }

    private KeySignature selectedKeySignature;
    private String selectedClef;
    private PianoNote lowestPracticeNote;
    private PianoNote highestPracticeNote;

    private boolean singleOctavePracticeMode = true;

    private String selectedDifficulty;

    private ArrayList<PianoNote> practicableNotes = new ArrayList();


    private ArrayList<PianoKey> pianoKeys = new ArrayList();

    private ArrayList<PianoNote> practiceNotesOnStaff = new ArrayList();

    private SoundPlayer soundPlayer;

    Random random = new Random();

    StaffView staffView;
    PianoView pianoView;

    public boolean isSingleOctavePractice() {
        return singleOctavePracticeMode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreate()");

        Intent intent = getIntent();
//        selectedClef = intent.getExtras().getString(ClefActivity.EXTRA_SELECTED_CLEF);
//        selectedDifficulty = intent.getExtras().getString(DifficultyActivity.EXTRA_SELECTED_DIFFICULTY);

        setTheme(R.style.Theme_AppCompat_DayNight_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        staffView = findViewById(R.id.staffView);
//        setStaffKeySignature();
        setStaffPracticeNotes();
        setUpPianoView();

        soundPlayer = new SoundPlayer(pianoView.getLowestPracticeNote(), PianoKey.count);
        soundPlayer.loadWavAssets(this.getAssets());
    }

    private void setStaffKeySignature() {
        staffView.setKeySignature(selectedKeySignature);
    }


    @Override
    protected void onStart() {
        Log.i(LOG_TAG, "onStart()");

        super.onStart();

        soundPlayer.setUpAudioStream();
    }

    @Override
    protected void onResume() {
        Log.i(LOG_TAG, "onResume()");

        super.onResume();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            // todo: generate random notes based on piano keys as well staffview practice range
            // todo: randomize notes BASED ON SELECTED DIFFICULTY
            for (PianoNote note : practicableNotes) {
                addSightReadingNote(note);
            }
        }
    }

    @Override
    protected void onStop() {
        Log.i(LOG_TAG, "onStop()");

        soundPlayer.teardownAudioStream();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(LOG_TAG, "onDestroy()");

        soundPlayer.unloadWavAssets();
        super.onDestroy();
    }

    private void setStaffPracticeNotes() {
        setPracticableNoteRange();
        staffView.setHighestPracticeNote(highestPracticeNote);
        staffView.setLowestPracticeNote(lowestPracticeNote);
    }

    private void setPracticableNoteRange() {
        if (selectedClef.equals(getResources().getString(R.string.treble))) {
            lowestPracticeNote = PianoNote.C4;
            highestPracticeNote = PianoNote.C6;
        }
        else if (selectedClef.equals(getResources().getString(R.string.bass))) {
            lowestPracticeNote = PianoNote.G2;
            highestPracticeNote = PianoNote.G4;
        }
        else if (selectedClef.equals(getResources().getString(R.string.both))) {
            lowestPracticeNote = PianoNote.G2;
            highestPracticeNote = PianoNote.C6;
        }


        // TODO: 11/23/2020 Change the conditional to allow for max-range single-octave practice
        for (int i=0;i<PianoKey.count;i++) {
            practicableNotes.add(PianoNote.valueOfStoredOrdinal(lowestPracticeNote.storedOrdinal + i));
        }
    }

    private void setUpPianoView() {

        pianoView = findViewById(R.id.pianoView);
        if (singleOctavePracticeMode) {
            pianoView.setLowestPracticeNote(PianoNote.C4);
            pianoView.setHighestPracticeNote(PianoNote.B4);
        }

        else {
            pianoView.setLowestPracticeNote(lowestPracticeNote);
            pianoView.setHighestPracticeNote(highestPracticeNote);
        }

        pianoKeys = pianoView.getPianoKeys();
        setPianoKeyListeners();
    }

    private void setPianoKeyListeners() {
        for (PianoKey key : pianoKeys) {
            key.setPianoKeyListener(this);
        }
    }

    private PianoNote generatePracticablePianoNote() {
        int randomInt = random.nextInt(PianoKey.count);
        return pianoKeys.get(randomInt).getNote();
    }


    private void addSightReadingNote(PianoNote note) {
        Log.i(LOG_TAG, "Adding note: " + note.toString());
        staffView = findViewById(R.id.staffView);
        practiceNotesOnStaff.add(note);
        staffView.addNote(note);
    }


    @Override
    public void keyDown(PianoKey key) {
        Log.i(LOG_TAG, "keyDown(" + key.toString() + ")");

        PianoNote note = key.getNote();

        int relativePianoKeyIndex = note.absoluteKeyIndex - lowestPracticeNote.absoluteKeyIndex;

        if (isCorrectNote(note)) {
            correctKeyPressed(key);
        }
        else {
            Log.i(LOG_TAG, "note is " + note);
            incorrectKeyPressed(key);
        }

        // TODO: 11/23/2020 Play note depending on StaffNote, not PianoKey (especially if in single octave mode)
        soundPlayer.triggerDown(relativePianoKeyIndex);

    }

    // TODO: 11/18/2020 Consider displaying a translucent wrong note for a short time on incorrect key
    private void incorrectKeyPressed(PianoKey key) {
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER,0,0);

        toast = Toast.makeText(this, "wrongo", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void correctKeyPressed(PianoKey key) {

        practiceNotesOnStaff.remove(0);
        staffView.markNoteCorrect();
        staffView.scrollToNextNote();
        staffView.highlightCurrentNote();

        addSightReadingNote(generatePracticablePianoNote());
    }

    private boolean isCorrectNote(PianoNote note) {
        if (note.equals(practiceNotesOnStaff.get(0), isSingleOctavePractice()))
            return true;
        else
            return false;
    }


    @Override
    public void keyUp(PianoKey key) {
//        Log.i(LOG_TAG, "keyUp(" + key.toString() + ")");
//        int relativePianoKeyIndex = key.getNote().absoluteKeyIndex - absoluteStartingPianoKeyIndex;
//
//        soundPlayer.triggerUp(relativePianoKeyIndex);
    }
}
