package com.dunneev.seenatural.Fragments.Reading;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.Fragments.Piano.PianoKey;
import com.dunneev.seenatural.Fragments.Piano.PianoViewModel;
import com.dunneev.seenatural.Fragments.Staff.StaffViewModel;
import com.dunneev.seenatural.R;
import com.dunneev.seenatural.databinding.FragmentPianoBinding;
import com.dunneev.seenatural.databinding.FragmentReadingBinding;
import com.dunneev.seenatural.databinding.FragmentStaffBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ReadingFragment extends Fragment {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private FragmentReadingBinding binding;
    FragmentStaffBinding staffBinding;
    FragmentPianoBinding pianoBinding;
    private ReadingViewModel viewModel;
    private StaffViewModel staffViewModel;
    private PianoViewModel pianoViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "create");
        super.onCreate(savedInstanceState);


        // Although each view should supposedly have one viewmodel, I couldn't find
        // anything addressing best practices regarding nested fragments.
        // I'm adhering to DRY and referencing the child model views.
        viewModel = new ViewModelProvider(this).get(ReadingViewModel.class);
        staffViewModel = new ViewModelProvider(this).get(StaffViewModel.class);
        pianoViewModel = new ViewModelProvider(this).get(PianoViewModel.class);

        setUpObservers();

    }


    private void setUpObservers() {

        final Observer<Boolean> singleOctaveObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSingleOctaveMode) {
                viewModel.isSingleOctaveMode = isSingleOctaveMode;
            }
        };

        final Observer<PianoNote> keyPressedObserver = new Observer<PianoNote>() {
            @Override
            public void onChanged(PianoNote note) {
                Log.i(LOG_TAG, note.toString() + " pressed");
                if (isCorrect(note)) {
                    staffViewModel.onCorrectNote();
                    pianoViewModel.onCorrectKeyPressed();
                }
                else {
                    staffViewModel.onIncorrectNote();
                    pianoViewModel.onIncorrectKeyPressed();
                }
            }
        };

        final Observer<PianoNote> keyReleasedObserver = new Observer<PianoNote>() {
            @Override
            public void onChanged(PianoNote note) {
                Log.i(LOG_TAG, note.toString() + " released");

            }
        };

        pianoViewModel.getMutableLiveDataIsSingleOctaveMode().observe(this, singleOctaveObserver);
        pianoViewModel.getMutableLiveDataKeyPressed().observe(this, keyPressedObserver);
        pianoViewModel.getMutableLiveDataKeyReleased().observe(this, keyReleasedObserver);

    }

    private boolean isCorrect(PianoNote note) {
        if (note.equals(staffViewModel.getNotesOnStaff().get(staffViewModel.getCurrentNoteIndex()), viewModel.isSingleOctaveMode)){
            return true;
        }
        return false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(LOG_TAG, "createView");

        binding = FragmentReadingBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        Log.i(LOG_TAG, "viewCreated");
        super.onViewCreated(view, savedInstanceState);
//        staffViewModel.addRandomNoteFromPracticableNotes();
    }
}
