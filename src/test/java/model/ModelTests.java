package model;

import exceptions.MyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class responsible of doing tests on my model class
 * @author Michal Goral
 * @version 1.0
 */
public class ModelTests {

    /**
     * A model object from MVC model
     */
    private Model model;

    /**
     * This method will be processed before each test and it creates an object of TrainingModel class
     */
    @BeforeEach
    public void setUp() {

        model = new Model();
    }

    /**
     * This test will check if method calculateBmi calculates BMI correctly. If there will be a division by 0 this method
     * should throw ArithmeticException exception. It takes various heights and one weight
     *
     * @param height heights to check
     */
    @ParameterizedTest
    @ValueSource(doubles = {100.0, 1.0, 3.0, 100.0, 300.0, -764834.0}) // add ""
    public void calculateBmiWithHeightTest(Double height) {

        double bmi;

        try {
            bmi = 100.0 / ((height / 100) * (height / 100));
            assertEquals(bmi, model.calculateBmi(height, 100.0), 0.01, "Variable values differ by more than 0.01!");
        } catch (ArithmeticException ignored) {

        }
    }

    /**
     * This test will check if method calculateBmi calculates BMI correctly. If there will be a division by 0 this method
     * should throw ArithmeticException exception. It takes various weights and one height
     *
     * @param weight weights to check
     */
    @ParameterizedTest
    @ValueSource(doubles = {100.0, 1.0, 3.0, 100.0, 300.0, 100.0, 0.0, 1.0, 74.0}) // add ""
    public void calculateBmiWithWeightTest(Double weight) {

        double bmi;

        try {
            bmi = weight / 4.0;
            assertEquals(bmi, model.calculateBmi(200.0, weight), 0.01, "Variable values differ by more than 0.01!");
        } catch (ArithmeticException ignored) {

        }
    }

    /**
     * This test will check if method calculateBmi calculates BMI correctly. If there will be a division by 0 this method
     * should throw ArithmeticException exception. It takes various weights and heights
     *
     * @param height heights to check
     * @param weight weights to check
     */
    @ParameterizedTest
    @CsvSource({"1.0,2.0", "2.0,1.0", "200.0,120.0", "2.0,52.0", "400.0,0.0", "200.0,120.0", "200.0,120.0", "200.0,120.0"})
    public void calculateBmiWithHeightAndWeightTest(Double height, Double weight) {

        double bmi;

        try {
            bmi = weight / ((height / 100) * (height / 100));
            assertEquals(bmi, model.calculateBmi(height, weight), 0.01, "Variable values differ by more than 0.01!");
        } catch (ArithmeticException ignored) {

        }
    }

    /**
     * This test will check if method checkHeightAndWeight works correctly. Only numbers from 1.0 to 320.0 should be processed.
     * Otherwise this method should throw a MyException exception
     *
     * @param height heights to check
     */
    @ParameterizedTest
    @ValueSource(doubles = {-100.0, 1.0, 3.0, 100.0, 320.0, 1000.0, 0.0}) // add ""
    public void checkHeightTest(Double height) {

        model.setHeightAndWeight(height.toString(), "80.0");

        try {
            model.checkHeightAndWeight();
        } catch (MyException ignored) {

        }
    }

    /**
     * This test will check if method checkHeightAndWeight works correctly. Only numbers from 1.0 to 320.0 should be processed.
     * Otherwise this method should throw a MyException exception
     *
     * @param weight weights to check
     */
    @ParameterizedTest
    @ValueSource(doubles = {-100.0, 1.0, 3.0, 100.0, 320.0, 1000.0, 0.0}) // add ""
    public void checkWeightTest(Double weight) {

        model.setHeightAndWeight("80.0", weight.toString());

        try {
            model.checkHeightAndWeight();
        } catch (MyException ignored) {

        }
    }

    /**
     * This test will check if method checkHeightAndWeight works correctly. Only numbers from 1.0 to 320.0 should be processed.
     * Otherwise this method should throw a MyException exception
     *
     * @param weight weights to check
     * @param height heights to check
     */
    @ParameterizedTest
    @CsvSource({"1.0,2.0", "2.0,1.0", "200.0,120.0", "0.0,520.0", "400.0,0.0", "0.0,0.0", "-200.0,120.0", "200.0,-120.0", "-200.0,-120.0"})
    public void checkHeightAndWeightTest(Double height, Double weight) {

        model.setHeightAndWeight(height.toString(), weight.toString());

        try {
            model.checkHeightAndWeight();
        } catch (MyException ignored) {

        }
    }

    /**
     * This test will check if method checkDoublesIfNegative checks if number is negative correctly.
     * If a number is negative it should throw a MyException exception
     *
     * @param arg numbers to check
     */
    @ParameterizedTest
    @ValueSource(doubles = {-100.0, -1.0, 0.0, -2134.0, -123780.0, 1.1, 0.0}) // add ""
    public void checkDoublesIfNegative(Double arg) {

        try {
            model.checkDoublesIfNegative(arg);
        } catch (MyException ignored) {

        }
    }

    /**
     * This test will check if method readCategoriesFromFile is reading correctly
     * In the existing file there is only one string and its value is "test"
     *
     * @param str string to compare with the text in the file
     */
    @ParameterizedTest
    @ValueSource(strings = {"test"})
    public void readCategoriesFromExistingFileCorrectString(String str) {

        Vector<String> test = model.readCategoriesFromFile("C:\\Users\\micha\\IdeaProjects\\TrainingAppFX\\abc.txt");
        assertEquals(str, test.get(0), "the strings differ");

    }

    /**
     * This test will check if method readCategoriesFromFile is reading correctly
     * In the existing file there is only one string and its value is "test"
     *
     * @param str string to compare with the text in the file
     */
    @ParameterizedTest
    @ValueSource(strings = {"test1", "test%%%"}) // add ""
    public void readCategoriesFromExistingFileIncorrectString(String str) {

        Vector<String> test = model.readCategoriesFromFile("C:\\Users\\micha\\IdeaProjects\\TrainingAppFX\\abc.txt");
        assertNotEquals(str, test.get(0), "the strings differ");

    }

    /**
     * This test will check if method readCategoriesFromFile handles reading from not existing file
     * It should throw an FileNotFoundException every time
     *
     * @param str names of files to check
     */
    @ParameterizedTest
    @ValueSource(strings = {"test", "test1", "test%%%"})
    public void readCategoriesFromNotExistingFile(String str) {

        Vector<String> test = model.readCategoriesFromFile(str);
    }

    /**
     * This test will check, if method addExercise checks if double values are positive, correctly.
     * If a number is negative it should not throw any  exception
     *
     * @param val values to check
     */
    @ParameterizedTest
    @ValueSource(doubles = {100.0, 2134.0, 123780.0, 1.1, 23.234234234}) // add ""
    public void addExerciseTestPositiveDoubles(Double val) {

        try {
            model.addExercise("test", "test", "test", val, val);
        } catch (Exception ignored) {

        }
    }

    /**
     * This test will check, if method addExercise checks if double values are negative, correctly.
     * If a number is negative it should throw a MyException exception
     *
     * @param val values to check
     */
    @ParameterizedTest
    @ValueSource(doubles = {-100.0, -1.0, 0.0, -2134.0, -123780.0, 1.1, 0.0}) // add ""
    public void addExerciseTestNegativeDoubles(Double val) {

        try {
            model.addExercise("test", "test", "test", val, val);
        } catch (Exception ignored) {

        }
    }

    /**
     * This test will check, if method getSumDistanceForEach sums up kilometers correctly
     *
     * @param val values to check
     */
    @ParameterizedTest
    @ValueSource(doubles = {100.0, 2134.0, 123780.0, 1.1, -12.0, -234124.2}) // add ""
    public void getSumDistanceForEachTest(Double val) {

        model.addExercise("test", "test", "test", val, val);
        model.addExercise("test", "test", "test", val, val);
        model.addExercise("test", "test", "test", val, val);

        if (val > 0.0) {
            assertEquals(val * 3, model.getSumDistanceForEach("test"), "summing went wrong");
        } else {
            assertEquals(0.0, model.getSumDistanceForEach("test"), "summing went wrong");
        }
    }

    /**
     * This test will check, if method getSumDurationForEach sums up time correctly
     *
     * @param val values to check
     */
    @ParameterizedTest
    @ValueSource(doubles = {100.0, 2134.0, 123780.0, 1.1, -12.0, -234124.2}) // add ""
    public void getSumDurationForEachTest(Double val) {

        model.addExercise("test", "test", "test", val, val);
        model.addExercise("test", "test", "test", val, val);
        model.addExercise("test", "test", "test", val, val);

        if (val > 0.0) {
            assertEquals(val * 3, model.getSumDurationForEach("test"), "summing went wrong");
        } else {
            assertEquals(0.0, model.getSumDurationForEach("test"), "summing went wrong");
        }
    }

    /**
     * This test will check, if method getSumDurationStream sums up kilometers correctly
     *
     * @param val values to check
     */
    @ParameterizedTest
    @ValueSource(doubles = {100.0, 2134.0, 123780.0, 1.1, -12.0, -234124.2}) // add ""
    public void getSumDistanceStreamTest(Double val) {

        model.addExercise("test", "test", "test", val, val);
        model.addExercise("test", "test", "test", val, val);
        model.addExercise("test", "test", "test", val, val);

        if (val > 0.0) {
            assertEquals(val * 3, model.getSumDistanceStream("test"), "summing went wrong");
        } else {
            assertEquals(0.0, model.getSumDistanceStream("test"), "summing went wrong");
        }
    }

    /**
     * This test will check, if method getSumDurationStream sums up time correctly
     *
     * @param val values to check
     */
    @ParameterizedTest
    @ValueSource(doubles = {100.0, 2134.0, 123780.0, 1.1, -12.0, -234124.2}) // add ""
    public void getSumDurationStreamTest(Double val) {

        model.addExercise("test", "test", "test", val, val);
        model.addExercise("test", "test", "test", val, val);
        model.addExercise("test", "test", "test", val, val);

        if (val > 0.0) {
            assertEquals(val * 3, model.getSumDurationStream("test"), "summing went wrong");
        } else {
            assertEquals(0.0, model.getSumDurationStream("test"), "summing went wrong");
        }
    }

    /**
     * This test will check if readCategoriesFromFile responds to a null value correctly
     */
    @Test
    public void readCategoriesFromFileTestWithNull() {

        try {
            model.readCategoriesFromFile(null);

        } catch (NullPointerException ignored) {

        }
    }
}