package group06.com.jot_a_thought.dao;

import java.util.ArrayList;
import java.util.List;

import group06.com.jot_a_thought.model.Journal;

public class JournalDAO {

    private final static List<Journal> journals = new ArrayList<>();
    public void save(Journal journal) {
        journals.add(journal);
    }

    public List<Journal> allJounals() {
        return new ArrayList<>(journals);
    }
}
