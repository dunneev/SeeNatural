package com.dunneev.seenatural.Activities.SightReading;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public enum PianoNote {
    A0("A0", 21, 0, null,"[A0]"),
    A_SHARP_0("A♯0", 22, 1,2,"[Bb0]"),
    B_FLAT_0("B♭0", 22, 2,1,"[Bb0]"),
    B0("B0", 23,3,null, "[B0]"),

    C1("C1", 24, 4,null,"[C1]"),
    C_SHARP_1("C♯1", 25, 5,6,"[Db1]"),
    D_FLAT_1("D♭1", 25, 6,5,"[Db1]"),
    D1("D1", 26, 7,null,"[D1]"),
    D_SHARP_1("D♯1", 27, 8,9,"[Eb1]"),
    E_FLAT_1("E♭1", 27, 9,8,"[Eb1]"),
    E1("E1", 28, 10,null,"[E1]"),
    F1("F1", 29, 11,null,"[F1]"),
    F_SHARP_1("F♯1", 30, 12,13,"[F♯1]"),
    G_FLAT_1("G♭1", 30, 13,12,"[F♯1]"),
    G1("G1", 31, 14,null,"[G1]"),
    G_SHARP_1("G♯1", 32, 15,16,"[G♯1]"),
    A_FLAT_1("A♭1", 32, 16,15,"[G♯1]"),
    A1("A1", 33, 17,null,"[A1]"),
    A_SHARP_1("A♯1", 34, 18,19,"[Bb1]"),
    B_FLAT_1("B♭1", 34, 19,18,"[Bb1]"),
    B1("B1", 35, 20,null,"[B1]"),

    C2("C2", 36, 21,null,"[C2]"),
    C_SHARP_2("C♯2", 37, 22,23,"[Db2]"),
    D_FLAT_2("D♭2", 37, 23,22,"[Db2]"),
    D2("D2", 38, 24,null,"[D2]"),
    D_SHARP_2("D♯2", 39, 25,26,"[Eb2]"),
    E_FLAT_2("E♭2", 39, 26,25,"[Eb2]"),
    E2("E2", 40, 27,null,"[E2]"),
    F2("F2", 41, 28,null,"[F2]"),
    F_SHARP_2("F♯2", 42, 29,30,"[F♯2]"),
    G_FLAT_2("G♭2", 42, 30,29,"[F♯2]"),
    G2("G2", 43, 31,null,"[G2]"),
    G_SHARP_2("G♯2", 44, 32,33,"[G♯2]"),
    A_FLAT_2("A♭2", 44, 33,32,"[G♯2]"),
    A2("A2", 45, 34,null,"[A2]"),
    A_SHARP_2("A♯2", 46, 35,36,"[Bb2]"),
    B_FLAT_2("B♭2", 46, 36,35,"[Bb2]"),
    B2("B2", 47, 37,null,"[B2]"),

    C3("C3", 48, 38,null,"[C3]"),
    C_SHARP_3("C♯3", 49, 39,40,"[Db3]"),
    D_FLAT_3("D♭3", 49, 40,39,"[Db3]"),
    D3("D3", 50, 41,null,"[D3]"),
    D_SHARP_3("D♯3", 51, 42,43,"[Eb3]"),
    E_FLAT_3("E♭3", 51, 43,42,"[Eb3]"),
    E3("E3", 52, 44,null,"[E3]"),
    F3("F3", 53, 45,null,"[F3]"),
    F_SHARP_3("F♯3", 54, 46,47,"[F♯3]"),
    G_FLAT_3("G♭3", 54, 47,46,"[F♯3]"),
    G3("G3", 55, 48,null,"[G3]"),
    G_SHARP_3("G♯3", 56, 49,50,"[G♯3]"),
    A_FLAT_3("A♭3", 56, 50,49,"[G♯3]"),
    A3("A3", 57, 51,null,"[A3]"),
    A_SHARP_3("A♯3", 58, 52,53,"[Bb3]"),
    B_FLAT_3("B♭3", 58, 53,52,"[Bb3]"),
    B3("B3", 59, 54,null,"[B3]"),

    C4("C4", 60, 55,null,"[C4]"),
    C_SHARP_4("C♯4", 61, 56,57,"[Db4]"),
    D_FLAT_4("D♭4", 61, 57,56,"[Db4]"),
    D4("D4", 62, 58,null,"[D4]"),
    D_SHARP_4("D♯4", 63, 59,60,"[Eb4]"),
    E_FLAT_4("E♭4", 63, 60,59,"[Eb4]"),
    E4("E4", 64, 61,null,"[E4]"),
    F4("F4", 65, 62,null,"[F4]"),
    F_SHARP_4("F♯4", 66, 63,64,"[F♯4]"),
    G_FLAT_4("G♭4", 66, 64,63,"[F♯4]"),
    G4("G4", 67, 65,null,"[G4]"),
    G_SHARP_4("G♯4", 68, 66,67,"[G♯4]"),
    A_FLAT_4("A♭4", 68, 67,66,"[G♯4]"),
    A4("A4", 69, 68,null,"[A4]"),
    A_SHARP_4("A♯4", 70, 69,70,"[Bb4]"),
    B_FLAT_4("B♭4", 70, 70,69,",[Bb4]"),
    B4("B4", 71, 71,null,"[B4]"),

    C5("C5", 72, 72,null,"[C5]"),
    C_SHARP_5("C♯5", 73, 73,74,"[Db5]"),
    D_FLAT_5("D♭5", 73, 74,73,"[Db5]"),
    D5("D5", 74, 75,null,"[D5]"),
    D_SHARP_5("D♯5", 75, 76,77,"[Eb5]"),
    E_FLAT_5("E♭5", 75, 77,76,"[Eb5]"),
    E5("E5", 76, 78,null,"[E5]"),
    F5("F5", 77, 79,null,"[F5]"),
    F_SHARP_5("F♯5", 78, 80,81,"[F♯5]"),
    G_FLAT_5("G♭5", 78, 81,80,"[F♯5]"),
    G5("G5", 79, 82,null,"[G5]"),
    G_SHARP_5("G♯5", 80, 83,84,"[G♯5]"),
    A_FLAT_5("A♭5", 80, 84,83,"[G♯5]"),
    A5("A5", 81, 85,null,"[A5]"),
    A_SHARP_5("A♯5", 82, 86,87,"[Bb5]"),
    B_FLAT_5("B♭5", 82, 87,86,"[Bb5]"),
    B5("B5", 83, 88,null,"[B5]"),

    C6("C6", 84, 89,null,"[C6]"),
    C_SHARP_6("C♯6", 85, 90,91,"[Db6]"),
    D_FLAT_6("D♭6", 85, 91,90,"[Db6]"),
    D6("D6", 86, 92,null,"[D6]"),
    D_SHARP_6("D♯6", 87, 93,94,"[Eb6]"),
    E_FLAT_6("E♭6", 87, 94,93,"[Eb6]"),
    E6("E6", 88, 95,null,"[E6]"),
    F6("F6", 89, 96,null,"[F6]"),
    F_SHARP_6("F♯6", 90, 97,98,"[F♯6]"),
    G_FLAT_6("G♭6", 90, 98,97,"[F♯6]"),
    G6("G6", 91, 99,null,"[G6]"),
    G_SHARP_6("G♯6", 92, 100,101,"[G♯6]"),
    A_FLAT_6("A♭6", 92, 101,100,"[G♯6]"),
    A6("A6", 93, 102,null,"[A6]"),
    A_SHARP_6("A♯6", 94, 103,104,"[Bb6]"),
    B_FLAT_6("B♭6", 94, 104,103,"[Bb6]"),
    B6("B6", 95, 105,null,"[B6]"),

    C7("C7", 96, 106,null,"[C7]"),
    C_SHARP_7("C♯7", 97, 107,108,"[Db7]"),
    D_FLAT_7("D♭7", 97, 108,107,"[Db7]"),
    D7("D7", 98, 109,null,"[D7]"),
    D_SHARP_7("D♯7", 99, 110,111,"[Eb7]"),
    E_FLAT_7("E♭7", 99, 111,110,"[Eb7]"),
    E7("E7", 100, 112,null,"[E7]"),
    F7("F7", 101, 113,null,"[F7]"),
    F_SHARP_7("F♯7", 102, 114,115,"[F♯7]"),
    G_FLAT_7("G♭7", 102, 115,114,"[F♯7]"),
    G7("G7", 103, 116,null,"[G7]"),
    G_SHARP_7("G♯7", 104, 117,118,"[G♯7]"),
    A_FLAT_7("A♭7", 104, 118,117,"[G♯7]"),
    A7("A7", 105, 119,null,"[A7]"),
    A_SHARP_7("A♯7", 106, 120,121,"[Bb7]"),
    B_FLAT_7("B♭7", 106, 121,120,"[Bb7]"),
    B7("B7", 107, 123,null,"[B7]"),

    C8("C8", 108, 124,null,"[C8]");

    public final String label;
    public final String pitch;
    public final int octave;
    public final String naturalNoteLabel;
    public final int midiValue;

    // A value from 0 to 87, spanning any piano note
    public final int absoluteKeyIndex;

    public final int keyColor;
    public final int keyDownColor;
    public final String filename;

    public final int storedOrdinal;
    public final int enharmonicEquivalentOrdinal;

    PianoNote(String label, int midi, int storedOrdinal, Integer enharmonicEquivalentOrdinal, String filename) {
        this.label = label;
        this.pitch = setPitch();
        this.octave = Integer.parseInt(String.valueOf(this.label.charAt(this.label.length()-1)));
        this.naturalNoteLabel = String.valueOf(this.label.charAt(0)) + this.octave;
        this.midiValue = midi;
        this.absoluteKeyIndex = midi - 21;
        this.storedOrdinal = storedOrdinal;
        this.enharmonicEquivalentOrdinal = enharmonicEquivalentOrdinal;
        this.filename = filename;
        this.keyColor = setKeyColor();
        this.keyDownColor = setKeyDownColor();

    }

    private String setPitch() {
        if (label.length() == 2)
            return label.substring(0,1);
        else
            return label.substring(0,2);
    }

    private int setKeyColor() {
        if (label.length() == 2)
            return Color.WHITE;
        return Color.BLACK;
    }

    private int setKeyDownColor() {
        if (this.keyColor == Color.WHITE)
            return Color.LTGRAY;
        return  Color.DKGRAY;
    }

    // Cache lookup values using Map that's populated when the class loads
    private static final Map<String, PianoNote> BY_LABEL = new HashMap<>();
    private static final Map<Integer, PianoNote> BY_MIDI_VALUE = new HashMap<>();
    private static final Map<Integer, PianoNote> BY_ABSOLUTE_KEY_INDEX = new HashMap<>();
    private static final Map<Integer, PianoNote> BY_STORED_ORDINAL = new HashMap<>();
    private static final Map<String, PianoNote> BY_FILENAME = new HashMap<>();


    static {
        for (PianoNote note: values()) {
            BY_LABEL.put(note.label, note);
            BY_MIDI_VALUE.put(note.midiValue, note);
            BY_ABSOLUTE_KEY_INDEX.put(note.absoluteKeyIndex, note);
            BY_STORED_ORDINAL.put(note.storedOrdinal, note);
            BY_FILENAME.put(note.filename, note);
        }
    }

    public static PianoNote valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

    public static PianoNote valueOfMidi(int midiKey) {
        return BY_MIDI_VALUE.get(midiKey);
    }

    public static PianoNote valueOfAbsoluteKeyIndex(int absoluteKeyIndex) {
        return BY_ABSOLUTE_KEY_INDEX.get(absoluteKeyIndex);
    }

    public static PianoNote valueOfStoredOrdinal(int storedOrdinal) {
        return BY_STORED_ORDINAL.get(storedOrdinal);
    }

    public static PianoNote valueOfFilename(String filename) {
        return BY_FILENAME.get(filename);
    }

    public static int numberOfWhiteKeysInRangeInclusive(PianoNote lowNote, PianoNote highNote) {

        int whiteKeyCount = 0;

        for (int i=lowNote.absoluteKeyIndex; i< highNote.absoluteKeyIndex; i++) {
            if (PianoNote.valueOfAbsoluteKeyIndex(i).keyColor == Color.WHITE) {
                whiteKeyCount++;
            }
        }
        return whiteKeyCount;
    }

    @Override
    public String toString() {
        return label;
    }
}
