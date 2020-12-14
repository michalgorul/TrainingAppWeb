package model;

public class Bmi {

    private Double height;

    private Double weight;

    private Double bmi;

    public Bmi(Double height, Double weight, Double bmi) {
        this.height = height;
        this.weight = weight;
        this.bmi = bmi;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getBmi() {
        return bmi;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }
}
