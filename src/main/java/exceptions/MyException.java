package exceptions;

/**
 * The class indicates conditions that our application might want to catch.
 * It is subclass of Exception
 * @author Michal Goral
 * @version 1.0
 */
public class MyException extends Exception{

    /**
     * User's height
     */
    private Double height;

    /**
     * User's weight
     */
    private Double weight;

    /**
     * Values for exercise stats that user typed in
     */
    Integer valI;

    /**
     * Values for exercise stats that user typed in
     */
    Double valD;

    /**
     * Constructor of new exception that checks user's height and weight
     * @param height user's height
     * @param weight user's weight
     */
    public MyException(Double height, Double weight){
        this.height = height;
        this.weight = weight;
    }

    /**
     * Constructor of new exception that checks value that user typed in for exercise
     * @param val value to check
     */
    public MyException(Integer val){
        this.valI = val;
    }

    /**
     * Constructor of new exception that checks value that user typed in for exercise
     * @param val value to check
     */
    public MyException(Double val){
        this.valD = val;
    }

    /**
     * This method will give us proper message when user's height or weight are incorrect
     * @return message to display
     */
    public String toStringBmi(){
        return "I do not think that your height is " + height + " cm and your weight is " + weight + " kg";
    }

    /**
     * This method will give us proper message when user typed incorrect values for exercise stats
     * @return message to display
     */
    public String toStringValues(){
        return "One or more values are less than 0 and it cannot happen";
    }

}
