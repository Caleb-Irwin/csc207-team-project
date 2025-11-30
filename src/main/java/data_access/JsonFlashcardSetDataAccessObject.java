//package data_access;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import entity.FlashCard;
//import entity.FlashCardSet;
//import use_case.FlashCardSetsDataAccessInterface;
//import use_case.FlashCardSetsDataAccessInterface;
//
//import java.io.File;
//import java.io.IOException;
//
//public class JsonFlashcardSetDataAccessObject implements FlashCardSetsDataAccessInterface {
//
//    private final String folder = "data/flashcards/";
//    private final ObjectMapper mapper = new ObjectMapper();
//
//    public JsonFlashcardSetDataAccessObject() {
//        File dir = new File(folder);
//        if (!dir.exists()) {
//            dir.mkdirs(); // auto-create folder if missing
//        }
//    }
//
//    private String filePath(String setName) {
//        return folder + setName + ".json";
//    }
//
//    @Override
//    public FlashCardSet load(String setName) {
//        try {
//            return mapper.readValue(new File(filePath(setName)), FlashCardSet.class);
//        } catch (IOException e) {
//            throw new RuntimeException("Could not load set: " + setName, e);
//        }
//    }
//
//    @Override
//    public void saveSet(FlashCardSet set) {
//        try {
//            mapper.writerWithDefaultPrettyPrinter()
//                    .writeValue(new File(filePath(set.getSetName())), set);
//        } catch (IOException e) {
//            throw new RuntimeException("Could not save set.", e);
//        }
//    }
//
//    @Override
//    public boolean existsByName(String setName) {
//        return new File(filePath(setName)).exists();
//    }
//
//    @Override
//    public void deleteSet(String setName) {
//        File f = new File(filePath(setName));
//        if (f.exists()) {
//            f.delete();
//        }
//    }
//}