package de.tu_darmstadt.se;

import java.util.Arrays;
import java.util.List;

import de.tu_darmstadt.se.BloodBag.BloodType;

public class Example {    
    public static void main(String[] args) {
        
        // Exercise 1

        Patient alice = new Patient(
            "Alice", 
            75, 
            1.80, 
            Arrays.asList("Peanuts", "Penicillin")
        );

        System.out.println(alice);
        System.out.printf("The BMI is %.2f\n", alice.getBmi());
        System.out.println((alice.hasAllergy("BANANA") ? "Hates " : "Likes ") + " Bananas");
        System.out.println((alice.hasAllergy("PEANUTS") ? "Hates " : "Likes ") + " Peanuts");
        

        // Exercise 2
        System.out.print("\n\n\n");

        List<BloodBag> bloodBags = Arrays.asList(
            new BloodBag(BloodType.APositive, 8000),
            new BloodBag(BloodType.BNegative, 1200),
            new BloodBag(BloodType.BPositive, 1500),
            new BloodBag(BloodType.ONegative, 200),
            new BloodBag(BloodType.ABPositive, 400)
        );

        int available = BloodBag.computeCompatibleBlood(BloodType.BPositive, bloodBags);
        System.out.printf("%d ml blood available for B+ patients. \n", available);

    }
}
