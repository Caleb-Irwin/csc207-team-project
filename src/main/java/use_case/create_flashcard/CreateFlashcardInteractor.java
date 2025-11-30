//package use_case.create_flashcard;
//
//import entity.FlashCard;
//import entity.FlashCardSet;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Interactor for Create Flashcard use case.
// * Follows Clean Architecture: depends only on DataAccessInterface + OutputBoundary.
// */
//public class CreateFlashcardInteractor implements CreateFlashcardInputBoundary {
//
//    private final CreateFlashcardDataAccessInterface dataAccess;
//    private final CreateFlashcardOutputBoundary presenter;
//
//    public CreateFlashcardInteractor(CreateFlashcardDataAccessInterface dataAccess,
//                                     CreateFlashcardOutputBoundary presenter) {
//        assert dataAccess != null;
//        assert presenter != null;
//        this.dataAccess = dataAccess;
//        this.presenter = presenter;
//    }
//
//    /**
//     * Handle a single flashcard creation (NOT used by your current UI, but kept for completeness)
//     */
//    @Override
//    public void execute(CreateFlashcardInputData inputData) {
//
//        String setName = inputData.getSetName();
//        List<String> questions = inputData.getQuestions();
//        List<String> answers = inputData.getAnswers();
//
//        FlashCardSet set;
//
//        if (dataAccess.existsByName(setName)) {
//            set = dataAccess.load(setName);
//        } else {
//            set = new FlashCardSet(setName, new ArrayList<>());
//        }
//
//        for (int i = 0; i < questions.size(); i++) {
//            String q = questions.get(i);
//            String a = (i < answers.size()) ? answers.get(i) : "";
//
//            // 如果这一行完全是空的，就跳过
//            if ((q == null || q.isBlank()) && (a == null || a.isBlank())) {
//                continue;
//            }
//
//            FlashCard card = new FlashCard(q, a);
//            set.addFlashcard(card);
//        }
//
//        dataAccess.saveSet(set);
//
//        // 🔥 Call Presenter so ViewModel can update
//        CreateFlashcardOutputData output =
//                new CreateFlashcardOutputData(setName, true, "Flashcard added.");
//        presenter.present(output);
//    }
//
//    /**
//     * Save an ENTIRE set of flashcards (UI Save button)
//     */
//    @Override
//    public void saveFlashcards(String setName,
//                               List<String> questions,
//                               List<String> answers) {
//
//        FlashCardSet set = new FlashCardSet(setName, new ArrayList<>());
//
//        for (int i = 0; i < questions.size(); i++) {
//            FlashCard card = new FlashCard(questions.get(i), answers.get(i));
//            set.addFlashcard(card);
//        }
//
//        dataAccess.saveSet(set);
//
//        // 🔥 Presenter updates ViewModel state (message: "Saved!")
//        CreateFlashcardOutputData output =
//                new CreateFlashcardOutputData(setName, true, "Set saved!");
//        presenter.present(output);
//    }
//
//    /**
//     * Delete a flashcard set
//     */
//    @Override
//    public void deleteSet(String setName) {
//
//        boolean existed = dataAccess.existsByName(setName);
//
//        dataAccess.deleteSet(setName);
//
//        // 🔥 Give meaningful message to presenter
//        String msg = existed ? "Set deleted!" : "Set does not exist.";
//
//        CreateFlashcardOutputData output =
//                new CreateFlashcardOutputData(setName, true, msg);
//
//        presenter.present(output);
//    }
//}
package use_case.create_flashcard;

import entity.FlashCard;
import entity.FlashCardSet;

import java.util.ArrayList;
import java.util.List;

public class CreateFlashcardInteractor implements CreateFlashcardInputBoundary {

    private final CreateFlashcardDataAccessInterface dataAccess;
    private final CreateFlashcardOutputBoundary presenter;

    public CreateFlashcardInteractor(CreateFlashcardDataAccessInterface dataAccess,
                                     CreateFlashcardOutputBoundary presenter) {
        assert dataAccess != null;
        assert presenter != null;

        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    /**
     * Save an ENTIRE flashcard set (UI Save button).
     */
    @Override
    public void save(CreateFlashcardInputData inputData) {

        String setName = inputData.getSetName();
        List<String> questions = inputData.getQuestions();
        List<String> answers = inputData.getAnswers();

        FlashCardSet set = new FlashCardSet(setName, new ArrayList<>());

        for (int i = 0; i < questions.size(); i++) {
            String q = questions.get(i);
            String a = (i < answers.size()) ? answers.get(i) : "";

            if ((q == null || q.isBlank()) && (a == null || a.isBlank())) {
                continue;
            }

            set.addFlashcard(new FlashCard(q, a));
        }

        dataAccess.saveSet(set);

        CreateFlashcardOutputData output =
                new CreateFlashcardOutputData(setName, true, "Set saved!");
        presenter.present(output);
    }

    /**
     * Delete an entire flashcard set.
     */
    @Override
    public void delete(String setName) {

        boolean existed = dataAccess.existsByName(setName);
        dataAccess.deleteSet(setName);

        String msg = existed ? "Set deleted!" : "Set does not exist.";

        CreateFlashcardOutputData output =
                new CreateFlashcardOutputData(setName, true, msg);
        presenter.present(output);
    }
}