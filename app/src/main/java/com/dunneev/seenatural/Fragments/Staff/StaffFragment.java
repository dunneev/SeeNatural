package com.dunneev.seenatural.Fragments.Staff;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.dunneev.seenatural.Enums.KeySignature;
import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.Fragments.Piano.PianoView;
import com.dunneev.seenatural.Fragments.Piano.PianoViewModel;
import com.dunneev.seenatural.Fragments.Reading.ReadingViewModel;
import com.dunneev.seenatural.R;
import com.dunneev.seenatural.databinding.FragmentStaffBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StaffFragment extends Fragment /*implements StaffView.onStaffLaidOutListener*/{

    private final String LOG_TAG = this.getClass().getSimpleName();

    private FragmentStaffBinding binding;
    private ReadingViewModel readingViewModel;
    private StaffViewModel viewModel;
    private PianoViewModel pianoViewModel;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        Log.i(LOG_TAG, "create");

        super.onCreate(savedInstanceState);

        readingViewModel = new ViewModelProvider(requireParentFragment()).get(ReadingViewModel.class);
        viewModel = new ViewModelProvider(this).get(StaffViewModel.class);
        pianoViewModel = new ViewModelProvider(requireParentFragment()).get(PianoViewModel.class);

        setViewModelFieldsFromPreferences();
        viewModel.generatePracticableNoteList();
        setUpObservables();

    }

    private void setViewModelFieldsFromPreferences() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        sharedPreferencesEditor = sharedPreferences.edit();

        KeySignature keySignature = KeySignature.valueOfString(sharedPreferences.getString(getResources().getString(R.string.staff_key_signature_key), KeySignature.C_MAJOR.toString()));
        boolean hideKeySignature = sharedPreferences.getBoolean(getResources().getString(R.string.staff_hide_key_signature_key), false);

        boolean hideTrebleClef = sharedPreferences.getBoolean(getResources().getString(R.string.hide_treble_clef_key), false);
        boolean hideTrebleClefLines = sharedPreferences.getBoolean(getResources().getString(R.string.hide_treble_clef_lines_key), false);
        boolean hideBassClef = sharedPreferences.getBoolean(getResources().getString(R.string.hide_bass_clef_key), false);
        boolean hideBassClefLines = sharedPreferences.getBoolean(getResources().getString(R.string.hide_bass_clef_lines_key), false);

        // Staff preferences are set up so that the flats, naturals, and sharps preferences are grayed out when generateAccidentals is false.
        // However, this does not change their value in sharedPrefs. That's the reason for the conditionals here. I could change them to false in
        // StaffSettingsFragment, but I think the persistence of what was selected is more user friendly.
        boolean generateAccidentals = sharedPreferences.getBoolean(getResources().getString(R.string.generate_accidentals_key), true);
        boolean generateFlats;
        boolean generateNaturals;
        boolean generateSharps;
        if (generateAccidentals) {
            generateFlats = sharedPreferences.getBoolean(getResources().getString(R.string.generate_flats_key), true);
            generateNaturals = sharedPreferences.getBoolean(getResources().getString(R.string.generate_naturals_key), true);
            generateSharps = sharedPreferences.getBoolean(getResources().getString(R.string.generate_sharps_key), true);
        }
        else {
            generateFlats = generateNaturals = generateSharps = false;
        }


        PianoNote lowNote =  PianoNote.valueOfLabel(sharedPreferences.getString(getResources().getString(R.string.staff_low_practice_note_key), ""));
        PianoNote highNote = PianoNote.valueOfLabel(sharedPreferences.getString(getResources().getString(R.string.staff_high_practice_note_key), ""));
        
        viewModel.setSelectedKeySignature(keySignature);
        viewModel.setHideKeySignature(hideKeySignature);
        
        viewModel.setHideTrebleClef(hideTrebleClef);
        viewModel.setHideTrebleClefLines(hideTrebleClefLines);
        viewModel.setHideBassClef(hideBassClef);
        viewModel.setHideBassClefLines(hideBassClefLines);
        
        viewModel.setGenerateAccidentals(generateAccidentals);
        viewModel.setGenerateFlats(generateFlats);
        viewModel.setGenerateNaturals(generateNaturals);
        viewModel.setGenerateSharps(generateSharps);
        
        viewModel.setLowestStaffPracticeNote(lowNote);
        viewModel.setHighestStaffPracticeNote(highNote);

    }

    // TODO: 12/8/2020 Make StaffViewModel itself observable and update the binding with viewmodel properties
    private void setUpObservables() {
//        final Observer<KeySignature> keySignatureObserver = new Observer<KeySignature>() {
//            @Override
//            public void onChanged(KeySignature keySignature) {
//                viewModel.generatePracticableNoteArray();
//                sharedPreferencesEditor.putString(getResources().getString(R.string.staff_key_signature_key), keySignature.toString());
//                sharedPreferencesEditor.apply();
//            }
//        };
//
//        final Observer<Boolean> hideKeySignatureObserver = new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean hideKeySignature) {
//                sharedPreferencesEditor.putBoolean(getResources().getString(R.string.staff_hide_key_signature_key), hideKeySignature);
//                sharedPreferencesEditor.apply();
//            }
//        };
//
//        final Observer<Boolean> generateAccidentalsObserver = new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean generateAccidentals) {
//                viewModel.generatePracticableNoteArray();
//                sharedPreferencesEditor.putBoolean(getResources().getString(R.string.generate_accidentals_key), generateAccidentals);
//                sharedPreferencesEditor.apply();
//            }
//        };
//
//        final Observer<Boolean> generateFlatsObserver = new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean generateFlats) {
//                viewModel.generatePracticableNoteArray();
//                sharedPreferencesEditor.putBoolean(getResources().getString(R.string.generate_flats_key), generateFlats);
//                sharedPreferencesEditor.apply();
//            }
//        };
//
//        final Observer<Boolean> generateNaturalsObserver = new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean generateNaturals) {
//                viewModel.generatePracticableNoteArray();
//                sharedPreferencesEditor.putBoolean(getResources().getString(R.string.generate_naturals_key), generateNaturals);
//                sharedPreferencesEditor.apply();
//            }
//        };
//
//        final Observer<Boolean> generateSharpsObserver = new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean generateSharps) {
//                viewModel.generatePracticableNoteArray();
//                sharedPreferencesEditor.putBoolean(getResources().getString(R.string.generate_sharps_key), generateSharps);
//                sharedPreferencesEditor.apply();
//            }
//        };
//
//        final Observer<Boolean> hideTrebleClefObserver = new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean hideTrebleClef) {
//                sharedPreferencesEditor.putBoolean(getResources().getString(R.string.hide_treble_clef_key), hideTrebleClef);
//                sharedPreferencesEditor.apply();
//            }
//        };
//
//        final Observer<Boolean> hideTrebleClefLinesObserver = new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean hideTrebleClefLines) {
//                sharedPreferencesEditor.putBoolean(getResources().getString(R.string.hide_treble_clef_lines_key), hideTrebleClefLines);
//                sharedPreferencesEditor.apply();
//            }
//        };
//
//        final Observer<Boolean> hideBassClefObserver = new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean hideBassClef) {
//                sharedPreferencesEditor.putBoolean(getResources().getString(R.string.hide_bass_clef_lines_key), hideBassClef);
//                sharedPreferencesEditor.apply();
//            }
//        };
//
//
//        final Observer<Boolean> hideBassClefLinesObserver = new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean hideBassClefLines) {
//                sharedPreferencesEditor.putBoolean(getResources().getString(R.string.hide_bass_clef_lines_key), hideBassClefLines);
//                sharedPreferencesEditor.apply();
//            }
//        };
//
//
//        final Observer<PianoNote> lowNoteObserver = new Observer<PianoNote>() {
//            @Override
//            public void onChanged(PianoNote lowPianoNote) {
//                viewModel.generatePracticableNoteArray();
//                sharedPreferencesEditor.putString(getResources().getString(R.string.staff_low_practice_note_key), lowPianoNote.label);
//                sharedPreferencesEditor.apply();
//            }
//        };
//
//        final Observer<PianoNote> highNoteObserver = new Observer<PianoNote>() {
//            @Override
//            public void onChanged(PianoNote highPianoNote) {
//
//                viewModel.generatePracticableNoteArray();
//
//                sharedPreferencesEditor.putString(getResources().getString(R.string.staff_high_practice_note_key), highPianoNote.label);
//                sharedPreferencesEditor.apply();
//            }
//        };


//        viewModel.getMutableLiveDataKeySignature().observe(this, keySignatureObserver);
//        viewModel.getMutableLiveDataHideKeySignature().observe(this, hideKeySignatureObserver);
//
//        viewModel.getMutableLiveDataHideTrebleClef().observe(this, hideTrebleClefObserver);
//        viewModel.getMutableLiveDataHideTrebleClefLines().observe(this, hideTrebleClefLinesObserver);
//        viewModel.getMutableLiveDataHideBassClef().observe(this, hideBassClefObserver);
//        viewModel.getMutableLiveDataHideBassClefLines().observe(this, hideBassClefLinesObserver);
//
//        viewModel.getMutableLiveDataGenerateAccidentals().observe(this, generateAccidentalsObserver);
//        viewModel.getMutableLiveDataGenerateFlats().observe(this, generateFlatsObserver);
//        viewModel.getMutableLiveDataGenerateNaturals().observe(this, generateNaturalsObserver);
//        viewModel.getMutableLiveDataGenerateSharps().observe(this, generateSharpsObserver);

//        viewModel.getMutableLiveDataLowestStaffPracticeNote().observe(this, lowNoteObserver);
//        viewModel.getMutableLiveDataHighestStaffPracticeNote().observe(this, highNoteObserver);

        // Functionality

//        final Observer<Boolean> correctNotePressedObserver = new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean correctNotePressed) {
//                if (correctNotePressed) {
//                    binding.staffView.markNoteCorrect(viewModel.getCurrentNoteIndex());
//                    binding.staffView.scrollToNote(viewModel.getCurrentNoteIndex() + 1);
//                }
//                else {
//                    PianoNote incorrectNotePressed = viewModel.getIncorrectNoteEntries().get(viewModel.getIncorrectNoteEntries().size()-1);
//                    binding.staffView.markNoteIncorrect(viewModel.getCurrentNoteIndex(), incorrectNotePressed);
//
//                }
//            }
//
//        };
//
//
//
        final Observer<List<List<PianoNote>>> practiceItemsOnStaffObserver = new Observer<List<List<PianoNote>>>() {
            @Override
            public void onChanged(ArrayList<PianoNote> notesOnStaff) {
                binding.staffView.setNotesOnStaff(notesOnStaff);
                binding.staffView.addNotesOnStaffToView();
            }
        };


        final Observer<PianoNote> keyPressedObserver = new Observer<PianoNote>() {
            @Override
            public void onChanged(PianoNote note) {
                Log.i(LOG_TAG, note.toString() + " pressed");

                }
            };


        final Observer<PianoNote> keyReleasedObserver = new Observer<PianoNote>() {
            @Override
            public void onChanged(PianoNote note) {
                Log.i(LOG_TAG, note.toString() + " released");
                if (viewModel.incorrectKeyDown) {
                    binding.staffView.removeIncorrectGhostNote(viewModel.getCurrentNoteIndex());
                }
                viewModel.correctKeyDown = false;
                viewModel.incorrectKeyDown = false;
            }
        };

        final Observer<PianoNote> correctKeyPressedObserver = new Observer<PianoNote>() {
            @Override
            public void onChanged(PianoNote note) {
                Log.i(LOG_TAG, "correct key pressed");
                viewModel.correctKeyDown = true;
                binding.staffView.markNoteCorrect(viewModel.getCurrentNoteIndex());
                viewModel.onCorrectNote();
                binding.staffView.scrollToNote(viewModel.getCurrentNoteIndex());

            }
        };

        final Observer<PianoNote> incorrectKeyPressedObserver = new Observer<PianoNote>() {
            @Override
            public void onChanged(PianoNote note) {
                Log.i(LOG_TAG, "wrong key pressed");
                viewModel.incorrectKeyDown = true;
                binding.staffView.markNoteIncorrect(viewModel.getCurrentNoteIndex(), note);

            }
        };

        viewModel.getMutableLiveDataPracticeItemsOnStaff().observe(requireParentFragment(), practiceItemsOnStaffObserver);
        pianoViewModel.getMutableLiveDataKeyPressed().observe(requireParentFragment(), keyPressedObserver);
        pianoViewModel.getMutableLiveDataKeyReleased().observe(requireParentFragment(), keyReleasedObserver);
        readingViewModel.getMutableLiveDataCorrectKeyPressed().observe(requireParentFragment(), correctKeyPressedObserver);
        readingViewModel.getMutableLiveDataIncorrectKeyPressed().observe(requireParentFragment(), incorrectKeyPressedObserver);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(LOG_TAG, "createView");

        binding = FragmentStaffBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Log.i(LOG_TAG, "viewCreated");

        super.onViewCreated(view, savedInstanceState);

        binding.addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.addNoteToStaff(PianoNote.G4);
            }
        });

        binding.addChordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.addChordToStaff(new ArrayList(Arrays.asList(PianoNote.G4, PianoNote.B5, PianoNote.D5)));
            }
        });
////
//        binding.toggleHighNoteButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (viewModel.getHighestStaffPracticeNote() == PianoNote.C6) {
//                    viewModel.setHighestStaffPracticeNote(PianoNote.C8);
//                }
//                else
//                    viewModel.setHighestStaffPracticeNote(PianoNote.C6);
//
//            }
//        });
//        binding.toggleHighNoteButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (viewModel.getHighestStaffPracticeNote() == PianoNote.C6) {
//                    viewModel.setHighestStaffPracticeNote(PianoNote.C8);
//                }
//                else
//                    viewModel.setHighestStaffPracticeNote(PianoNote.C6);
//
//            }
//        });
//
//        binding.correctNoteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                binding.staffView.markNoteCorrect(viewModel.getCurrentNoteIndex());
//                viewModel.onCorrectNote();
//                binding.staffView.scrollToNote(viewModel.getCurrentNoteIndex());
//
//            }
//        });
//
//        binding.incorrectNoteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewModel.onIncorrectNote();
//            }
//        });
        regenerateStaff();


    }


    private void setUpStaff() {
        Log.i(LOG_TAG, "setUpStaff()");

        binding.staffView.setKeySignature(viewModel.getSelectedKeySignature());
        binding.staffView.setHideKeySignature(viewModel.getHideKeySignature());

        binding.staffView.setHideTrebleClef(viewModel.getHideTrebleClef());
        binding.staffView.setHideTrebleClefLines(viewModel.getHideTrebleClefLines());
        binding.staffView.setHideBassClef(viewModel.getHideBassClef());
        binding.staffView.setHideBassClefLines(viewModel.getHideBassClefLines());

        binding.staffView.setLowestPracticeNote(viewModel.getLowestStaffPracticeNote());
        binding.staffView.setHighestPracticeNote(viewModel.getHighestStaffPracticeNote());

        binding.staffView.setPracticeItemsOnStaff(viewModel.getPracticeItemsOnStaff());
        binding.staffView.addPracticeItemsOnStaffToView();
        binding.staffView.setCurrentNoteIndex(viewModel.getCurrentNoteIndex());
//        if (!viewModel.getNotesOnStaff().isEmpty()) {
//            binding.staffView.scrollToNote(viewModel.getCurrentNoteIndex());
//        }

    }


    private void initializeStaff() {
        Log.i(LOG_TAG, "initializeStaff()");
        binding.staffView.init();
//        viewModel.addAllPracticableNotesToStaff();
//        binding.staffView.addNote(PianoNote.G4);
    }


    private void regenerateStaff(){
        Log.i(LOG_TAG, "regenerateStaff()");

        setUpStaff();
        initializeStaff();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}