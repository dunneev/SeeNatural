package com.dunneev.seenatural.Fragments.Reading;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dunneev.seenatural.Enums.KeySignature;
import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.Fragments.Piano.PianoKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReadingViewModel extends ViewModel {

    public boolean isSingleOctaveMode;

    private MutableLiveData<PianoNote> correctKeyPressed = new MutableLiveData<>();
    private MutableLiveData<PianoNote> incorrectKeyPressed = new MutableLiveData<>();


    public MutableLiveData<PianoNote> getMutableLiveDataCorrectKeyPressed(){
        return correctKeyPressed;
    }

    public MutableLiveData<PianoNote> getMutableLiveDataIncorrectKeyPressed(){
        return incorrectKeyPressed;
    }

    public boolean isCorrectPress(PianoNote notePressed, List<List<PianoNote>> itemsOnStaff, int currentItemIndex) {

        if (itemsOnStaff.size() == 0) {
            return false;
        }

        if (currentItemIndex >= itemsOnStaff.size()) {
            return false;
        }

        // todo: adapt to chords
        if (notePressed.equals(itemsOnStaff.get(currentItemIndex).get(0), isSingleOctaveMode)) {
            return true;
        }

        return false;
//        if (itemsOnStaff.get(currentItemIndex).contains(notePressed))

    }

    public void onCorrectKeyPressed(PianoNote note) {
        correctKeyPressed.setValue(note);
    }

    public void onIncorrectKeyPressed(PianoNote note) {
        incorrectKeyPressed.setValue(note);
    }


}
