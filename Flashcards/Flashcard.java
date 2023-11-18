import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * @author Simeng Li
 */

public class Flashcard implements Comparable<Flashcard> {

    private String dueDate;
    private String front;
    private String back;

    public Flashcard(String dueDate, String front, String back) {
        this.dueDate = dueDate;
        this.front = front;
        this.back = back;
    }

    /**
     * Gets the text for the front of this flashcard.
     */
    public String getFrontText() {
        return front;
    }

    /**
     * Gets the text for the Back of this flashcard.
     */
    public String getBackText() {
        return back;
    }

    /**
     * Gets the time when this flashcard is next due.
     */
    public LocalDateTime getDueDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return LocalDateTime.parse(dueDate, dateTimeFormatter);
    }

    /**
     * compareTo method;
     */
     public int compareTo(Flashcard other) {
         return dueDate.compareTo(other.dueDate);
     }

     @Override
     public String toString() {
         return dueDate + "\t" + front + "\t" + back;
     }
}
