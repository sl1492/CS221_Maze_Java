import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.io.PrintWriter;
/*
 * @author: Simeng Li
 */
public class FlashcardDisplayer {
    private FlashcardPriorityQueue cards;

    /**
     * Creates a flashcard displayer with the flashcards in file.
     * File has one flashcard per line. On each line, the date the flashcard
     * should next be shown is first (format: YYYY-MM-DDTHH-MM), followed by a tab,
     * followed by the text for the front of the flashcard, followed by another tab.
     * followed by the text for the back of the flashcard. You can assume that the
     * front/back text does not itself contain tabs. (I.e., a properly formatted file
     * has exactly 2 tabs per line.)
     * The time may be more precise (e.g., seconds may be included). The parse method
     * in LocalDateTime can deal with this situation without any changes to your code.
     */
    public FlashcardDisplayer(String filePath) {
        cards = new FlashcardPriorityQueue();
        if (filePath != null) {
            Scanner fileData = null;
            try {
                fileData = new Scanner(new File(filePath));
            } catch (FileNotFoundException e) {
                System.out.println("Scanner error opening the file " + filePath);
                System.out.println(e.getMessage()); // optional
                System.exit(1); // optional
            }
            while (fileData.hasNextLine()) {
                String line = fileData.nextLine();
                String[] cardLine = line.split("\t");
                Flashcard card = new Flashcard(cardLine[0],cardLine[1],cardLine[2]);
                cards.add(card);

            }
            fileData.close();
        }
    }

    /**
     * Writes out all flashcards to a file so that they can be loaded
     * by the FlashcardDisplayer(String filePath) constructor. Returns true
     * if the file could be written. The FlashcardDisplayer should still
     * have all of the same flashcards after this method is called as it
     * did before the method was called. However, flashcards with the same
     * exact same next display date may later be displayed in a different order.
     */
    public boolean saveFlashcards(String outFilePath) {
        String content;
        FlashcardPriorityQueue cardsCopy = new FlashcardPriorityQueue();
        boolean written = true;
        boolean opened = true;
        PrintWriter toFile = null;
        try {
            toFile = new PrintWriter(outFilePath);
        } catch (FileNotFoundException e) {
            System.err.println("Error writing the file " + outFilePath);
            written = false;
            opened = false;
        }

        if (opened == true) {
            while (!cards.isEmpty()) {
                Flashcard card = cards.poll();
                toFile.println(card.toString());
                cardsCopy.add(card);
            }
        }
        cards = cardsCopy;
        toFile.close();
        return written;
    }

    /**
     * Displays any flashcards that are currently due to the user, and
     * asks them to report whether they got each card correct. If the
     * card was correct (if the user entered "1"), it is added back to the
     * deck of cards with a new due date that is one day later than the current
     * date and time; if the card was incorrect (if the user entered "2"), it is
     * added back to the card with a new due date that is one minute later than
     * that the current date and time.
     */
    public void displayFlashcards() {
        Scanner keyboard = new Scanner(System.in);
        while (cards.peek().getDueDate().compareTo(LocalDateTime.now()) <= 0) {
            System.out.println(cards.peek().getFrontText());
            System.out.print("[Press return for back of card]");
            keyboard.nextLine();
            System.out.println(cards.peek().getBackText());
            System.out.println("Press 1 if you got the card correct and 2 if you got the card incorrect.");
            String answer = keyboard.nextLine();
            if (answer.equals("1")) {
                Flashcard card = cards.poll();
                Flashcard newCard = new Flashcard(LocalDateTime.now().plusDays​(1).toString(),card.getFrontText(),card.getBackText());
                cards.add(newCard);
            } else if (answer.equals("2")) {
                Flashcard card = cards.poll();
                Flashcard newCard = new Flashcard(LocalDateTime.now().plusMinutes​(1).toString(),card.getFrontText(),card.getBackText());
                cards.add(newCard);
            } else {
                System.out.println("Please enter 1 or 2.");
            }
        }
            System.out.println("No cards are waiting to be studied! ");
    }

    public static void main(String[] args) {
        boolean run = true;
        Scanner keyboard = new Scanner(System.in);
        if (args.length == 1) {
            FlashcardDisplayer displayer = new FlashcardDisplayer(args[0]);
            System.out.print("Time to practice flashcards!" +
                               "The computer will display your flashcards, " +
                               "you generate the response in your head, and then see if you got it right." +
                               "The computer will show you cards that you miss more often than those you know!");

            while (run) {
                System.out.println("Please enter commands choosing from the following: 'quiz', 'save', and 'exit': ");
                String commands = keyboard.nextLine();
                if (commands.toLowerCase().compareTo("quiz") == 0) {
                    displayer.displayFlashcards();

                } else if (commands.toLowerCase().compareTo("save") == 0) {
                    System.out.println("Type a filename where you'd like to save the flashcards: ");
                    String outFilePath = keyboard.nextLine();
                    displayer.saveFlashcards(outFilePath);
                } else if (commands.toLowerCase().compareTo("exit") == 0) {
                    System.out.println("Goodbye!");
                    run = false;
                } else {
                    System.out.println("Please enter a valid statement.");
                }
            }
        } else {
            System.out.println("Please give the right file.");
        }
    }
}
