package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.Vector;

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
     * Vector of exercise names
     */
    private Vector<String> exerciseNames = new Vector<>();


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
     * This method will calculate user's BMI depending on his weight and height
     * @param height user's height
     * @param weight user's weight
     * @return user's BMI
     */
    public double calculateBmi(double height, double weight) {
        double bmi;

        bmi = weight / ((height / 100) * (height / 100));
        return BigDecimal.valueOf(bmi).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

}
