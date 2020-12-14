package model;

import exceptions.MyException;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.Vector;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

/**
 * The Model class contains data to operate on and all necessary calculations on them
 * @author Michal Goral
 * @version 1.0
 */
public class Model {

    /**
     * Vector of exercises
     */
    private final Vector<Exercise> exercises = new Vector<>();

    /**
     * Holds user's height
     */
    private Double height = 0.0;

    /**
     * Holds user's weight
     */
    private Double weight = 0.0;

    /**
     * Vector of exercise names
     */
    private Vector<String> exerciseNames = new Vector<>();

    /**
     * This method will give us vector of category names saved in model
     * @return vector of category names
     */
    public Vector<String> getExerciseNames() {
        return exerciseNames;
    }

    public Vector<Exercise> getExercises(){
        return this.exercises;
    }

    /**
     * This method will check if user's weight and height are in reasonable bounds
     * @throws MyException thrown if height or weight arent proper
     */
    public void checkHeightAndWeight() throws MyException {
        if(this.height < 0.0 || this.height > 320.0 || this.weight < 0.0 || this.weight > 320.0){

            throw new MyException(this.height, this.weight);
        }
    }

    /**
     * This method will save user's weight and height in this class
     * @param height user's height
     * @param weight user's weight
     */
    public void setHeightAndWeight(String height, String weight){

        try {

            height = height.replace(",",".");
            weight = weight.replace(",",".");
            this.height = Double.parseDouble(height);
            this.weight = Double.parseDouble(weight);

        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter right value");
            alert.showAndWait();
            this.height = 0.0;
            this.weight = 0.0;
        }
    }

    /**
     * This method will read categories from file
     * @param name name of file we want to read from
     * @return vector of category names
     */
    public Vector<String> readCategoriesFromFile(String name) {

        if(name!=null){
            Vector<String> categories = new Vector<>();
            try {
                File myObj = new File(name);
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {

                    String data = myReader.nextLine();
                    categories.add(data);
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            exerciseNames = categories;
            return categories;
        }
        return null;
    }


    /**
     * This method will add new exercise to exercises vector
     * @param name name of category
     * @param comment comment user wanted to add
     * @param date date of exercise
     * @param distance distance that user made that exercise
     * @param duration how long user was exercising
     */
    public void addExercise(String name, String comment, String date, Double distance, Double duration){

        try{
            checkDoublesIfNegative(distance, duration);
            exercises.add(new Exercise(name, comment, date, distance, duration));
        }
        catch(MyException ignored){

        }
    }

    /**
     * This method will check if integers are negative and throw an exception if so
     * @param args values to check
     * @throws MyException thrown if value is negative
     */
    public void checkDoublesIfNegative(Double... args) throws MyException {

        for (Double arg : args) {

            if (arg < 0) {
                throw new MyException(arg);
            }
        }
    }

    /**
     * This method will calculate user's BMI depending on his weight and height
     * @param height user's height
     * @param weight user's weight
     * @return user's BMI
     */
    public double calculateBmi(double height, double weight) {

        double bmi = 0.0;

        try{
            this.height = height;
            this.weight = weight;
            checkHeightAndWeight();
            bmi = weight / ((height / 100) * (height / 100));
            bmi = BigDecimal.valueOf(bmi).setScale(2, RoundingMode.HALF_UP).doubleValue();
        }
        catch(MyException ignored){

        }

        return bmi;
    }

    /**
     * This method will give us sum of kilometers using for each loop of specific category
     * @param arrayName name of a specific category
     * @return sum of kilometers in specific category
     */
    public Double getSumDistanceForEach(String arrayName){

        Double sum = 0.0;

        for(Exercise ex : exercises){

            if(ex.getExerciseName().equals(arrayName)){

                sum += ex.getDistance();
            }
        }
        return sum;
    }

    /**
     * This method will give us sum of minutes using for each loop of specific category
     * @param arrayName name of a specific category
     * @return sum of minutes in specific category
     */
    public Double getSumDurationForEach(String arrayName){

        Double sum = 0.0;

        for(Exercise ex : exercises){

            if(ex.getExerciseName().equals(arrayName)) {
                sum += ex.getDuration();
            }
        }
        return sum;
    }

    /**
     * This method will give us sum of kilometers using for each loop of specific category
     * @param arrayName name of a specific category
     * @param vec vector of exercises
     * @return sum of kilometers in specific category
     */
    public Double getSumDistanceForEach(String arrayName, Vector<Exercise> vec){

        Double sum = 0.0;

        for(Exercise ex : vec){

            if(ex.getExerciseName().equals(arrayName)){

                sum += ex.getDistance();
            }
        }
        return sum;
    }

    /**
     * This method will give us sum of minutes using for each loop of specific category
     * @param arrayName name of a specific category
     * @param vec vector of exercises
     * @return sum of minutes in specific category
     */
    public Double getSumDurationForEach(String arrayName, Vector<Exercise> vec){

        Double sum = 0.0;

        for(Exercise ex : vec){

            if(ex.getExerciseName().equals(arrayName)) {
                sum += ex.getDuration();
            }
        }
        return sum;
    }

    /**
     * This method will give us sum of kilometers from specific category using streams
     * I'm using streams here
     * @param arrayName category from which we want to get sum of reps
     * @return sum of kilometers in specific category
     */
    public Double getSumDistanceStream(String arrayName){

        Stream<Double> filteredStream = exercises.stream()
                .filter(e -> e.getExerciseName().equals(arrayName))
                .map(Exercise::getDistance);

        DoubleStream doubleStream = filteredStream.mapToDouble(x -> x);

        return doubleStream.sum();
    }

    /**
     * This method will give us sum of minutes from specific category using streams
     * I'm using streams here
     * @param arrayName category from which we want to get sum of reps
     * @return sum of minutes in specific category
     */
    public Double getSumDurationStream(String arrayName){

        Stream<Double> filteredStream = exercises.stream()
                .filter(e -> e.getExerciseName().equals(arrayName))
                .map(Exercise::getDuration);

        DoubleStream doubleStream = filteredStream.mapToDouble(x -> x);

        return doubleStream.sum();
    }

}
