package model;

/**
 * This is the Exercise class
 * It contains information about a exercise
 * and provides us to set those values
 * @author Michał Góral
 * @version 1.0
 */
public class Exercise {

    /**
     * Category name
     */
    private String exerciseName;

    /**
     * Comment that user made
     */
    private String comment;

    /**
     * Date of an exercise
     */
    private final String exerciseDate;

    /**
     * Distance made during exercise in kilometers
     */
    private final Double distance;

    /**
     * Time that user spent on an exercise in minutes
     */
    private final Double duration;

    /**
     * Constructor of exercise class
     * @param name Category name
     * @param comment Comment that user made
     * @param exerciseDate Date of an exercise
     * @param distance Distance made during exercise in kilometers
     * @param duration Time that user spent on an exercise in minutes
     */
    public Exercise(String name, String comment, String exerciseDate, Double distance, Double duration){

        this.exerciseName = name;
        this.comment = comment;
        this.exerciseDate = exerciseDate;
        this.distance = distance;
        this.duration = duration;
    }

    /**
     * This method will give us category name of an exercise
     * @return name of an exercise
     */
    public String getExerciseName() {
        return exerciseName;
    }

    /**
     * This method will give us category name of an exercise in number
     * @return name of an exercise in int
     */
    public int getExerciseNameInt() {
        int ret = 0;

        if(exerciseName.equals("Running")){
            ret = 1;
        }
        else if(exerciseName.equals("Cycling")){
            ret = 2;
        }
        else if(exerciseName.equals("Swimming")){
            ret = 3;
        }
        else if(exerciseName.equals("Walking")){
            ret = 4;
        }
        else if(exerciseName.equals("Nordic walking")){
            ret = 5;
        }
        else if(exerciseName.equals("Winter sports")){
            ret = 6;
        }
        else if(exerciseName.equals("Other")){
            ret = 7;
        }

        return ret;
    }

    /**
     * This method will set category name of an exercise after editing it in GUI
     * @param newName new category name
     */
    public void setExerciseName(String newName){
        this.exerciseName = newName;
    }

    /**
     * This method will give us comment that user made
     * @return comment that user made
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method will set new comment about an exercise after editing it in GUI
     * @param newComment new comment
     */
    public void setComment(String newComment){
        this.comment = newComment;
    }

    /**
     * This method will give us date of an exercise
     * @return date of an exercise
     */
    public String getExerciseDate() {
        return exerciseDate;
    }

    /**
     * This method will give us distance made during training
     * @return distance made during training
     */
    public Double getDistance() {
        return distance;
    }

    /**
     * This method will give us time that user spent on a training
     * @return time that user spent on a training
     */
    public Double getDuration() {
        return duration;
    }
}

