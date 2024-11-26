import java.util.ArrayList;
public class WordleBot implements WordlePlayer {
    ArrayList<String> knownWords;
    int guessNumber;
    String incorrectlyPlaced;
    String correctlyPlaced;
    String notInPuzzle;
    String blankSpaces;


    @Override
    public void beginGame(Wordle game) {
        knownWords = game.getKnownWords();
        guessNumber = 0;
        blankSpaces = "";
        for(int i = 0; i < knownWords.get(0).length(); i++) {
            blankSpaces = blankSpaces + "-";
        }

    }
    @Override
    public boolean hasNextGuess() {
        return (0 < knownWords.size());   // rewrite as 0 < knownWords.size()
    }

    @Override
    public String nextGuess() {
        // first guess will be random!
            // if guessNumber = 0 >> random guess
        if (guessNumber == 0){
            guessNumber++;
            return randomGuess();
        }
        // first, remove words that do not contain the max number of correctly Placed
        if (!correctlyPlaced.equals(blankSpaces)){ // can be different lengths
            mostCorrectlyPlaced();
        }
        // remove words that do not contain the max number of incorrectly placed
        if (!incorrectlyPlaced.equals(blankSpaces)) {
            mostIncorrectlyPlaced();
        }
        //remove words that contain any letter in notInPuzzle
        if (!notInPuzzle.equals("")) {
            removeNotInPuzzle();
        }
        guessNumber++;      // increase guess number
        return randomGuess();       // random guess
    }
//
    @Override
    public void tell(Hint h) {
        // hints from wordle
        incorrectlyPlaced = h.getIncorrectlyPlaced();
        correctlyPlaced = h.getCorrectlyPlaced();
        notInPuzzle = h.getNotInPuzzle();
    }
    public String randomGuess() {
        //random guess from list
        int index = (int)(Math.random() * knownWords.size());
        return knownWords.get(index); //return word
    }
    public ArrayList<String> removeNotInPuzzle(){
        // remove all words that contain "Not In Puzzle"
        // to make faster, after removing all words with letter in Not In Puzzle, remove letter from string
            // so we don't have to keep on checking letters we already removed
        for (int i = 0; i < knownWords.size(); i++) {   // each word in array
            for (int n = 0; n < notInPuzzle.length(); n++) {       // puts each letter
                char letter = notInPuzzle.charAt(n);
                for (int x = 0; x < knownWords.get(0).length(); x++) {     // compares the letter to each placement, if found, remove and break
                    if(knownWords.get(i).charAt(x) == letter) {
                        knownWords.remove(i);       // remove
                        n = -1;
                        if (i == knownWords.size()){        // reach end of array
                            return knownWords;
                        }
                        break;
                    }
                }
            }
        }
        return knownWords;
    }
    public ArrayList<String> mostCorrectlyPlaced(){
        // keeps all words with most correctly placed
        // have a counter to count how many letters are in correctly placed
        // int countCorrectlyPlaced = 0
        // for (int i = 0; i < knownWords.size(); i++)
            // for (int x = 0; x < guess.length(); x++)
                // if correctlyPlaced.charAt(x) == knownWords[i].charAt(x).
                    //increase counter
            // if countCorrectlyPlaced > counterArrayWord
                // knownWords.remove(i)      ; removes word in array, continues
        int counterCorrectlyPlaced = 0;
        for (int n = 0; n < correctlyPlaced.length(); n++){
            if (correctlyPlaced.charAt(n) != '-'){      // getting number of correctly placed
                counterCorrectlyPlaced++;
            }
        }
        for (int i = 0; i < knownWords.size(); i++){
            int counterArray = 0;
            for (int x = 0; x < knownWords.get(0).length(); x++) {
                if (correctlyPlaced.charAt(x) == knownWords.get(i).charAt(x)){
                    counterArray++;     // counts correctly placed in array word
                }
            }
            if (counterCorrectlyPlaced > counterArray){     // compares
                knownWords.remove(i);   // removes word if it doesn't have the max number of correctly placed
                i--;
            }
        }
        return knownWords;
    }
    public ArrayList<String> mostIncorrectlyPlaced() {
        // keeps all words with most incorrectly placed
        // have a counter to count how many letters are in incorrectly placed
        // int countIncorrectlyPlaced = 0
        // for (int i = 0; i < knownWords.size(); i++)
            // for (int x = 0; x < guess.length(); x++)
                // if correctlyPlaced.charAt(x) == knownWords[i].charAt(x).
                    //increase counter
            // if countIncorrectlyPlaced > counterArrayWord
                // knownWords.remove(i)      ; removes word in array, continues
        int counterIncorrectlyPlaced = 0;
        for (int n = 0; n < incorrectlyPlaced.length(); n++){
            if (incorrectlyPlaced.charAt(n) != '-'){        // incorrectly placed counter
                counterIncorrectlyPlaced++;
            }
        }
        for (int i = 0; i < knownWords.size(); i++){
            int counterArray = 0;
            for (int x = 0; x < knownWords.get(0).length(); x++) {
                if (incorrectlyPlaced.charAt(x) == knownWords.get(i).charAt(x)){
                    counterArray++;
                }
            }
            if (counterIncorrectlyPlaced > counterArray){       // compares values
                knownWords.remove(i);       // removes words that doesn't contain max number of incorrectly placed
                i--;
            }
        }
        return knownWords;
    }
}
