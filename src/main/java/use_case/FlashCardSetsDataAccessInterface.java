package use_case;

import java.util.ArrayList;

import entity.FlashCardSet;

public interface FlashCardSetsDataAccessInterface {
    ArrayList<FlashCardSet> getFlashCardSets();

    FlashCardSet getFlashCardSetById(Integer setId);

    void createFlashCardSet(FlashCardSet flashCardSet);

    void updateFlashCardSet(Integer id, FlashCardSet flashCardSet);

    void deleteFlashCardSet(Integer id);
}
