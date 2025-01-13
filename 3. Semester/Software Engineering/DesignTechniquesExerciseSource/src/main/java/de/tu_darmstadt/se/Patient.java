package de.tu_darmstadt.se;

import java.util.List;
import java.lang.IllegalArgumentException;

public class Patient {
    private String name;
    private double weight;
    private double height;
    private List<String> allergies;

    public Patient(String name, double weight, double height, List<String> allergies) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be greater zero!");
        }
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be greater zero!");
        }

        this.name = name;
        this.weight = weight;
        this.height = height;
        this.allergies = allergies;
    }
    /**
     * Returns whether the patient has the allergy given in the parameter.
     * Will throw an error if parameter is null.
     * @param allergy the allergy that needs to be checked
     * @return boolean (true if patient has the allergy, false otherwise)
     **/
    public boolean hasAllergy(String allergy) {
        if (allergy == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < allergies.size(); i++) {
            if (allergies.get(i).equalsIgnoreCase(allergy)) {
                return true;
            }
        }

        return false;
    }

    public double getBmi() {
        return weight / (height * height);
    }

    @Override
    public String toString() {
        return name + " [" + weight + "kg; " + (height * 100) + "cm; " + allergies.size() + " known allergies]";
    }
}
