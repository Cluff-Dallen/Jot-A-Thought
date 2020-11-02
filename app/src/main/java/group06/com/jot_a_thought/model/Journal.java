package group06.com.jot_a_thought.model;

import androidx.annotation.NonNull;

public class Journal {
    private final String title;
    private final String journal;

    public Journal(String journal, String title) {
        this.journal = journal;
        this.title = title;
    }

    @NonNull
    @Override
    public String toString() {
        return title;
    }
}
