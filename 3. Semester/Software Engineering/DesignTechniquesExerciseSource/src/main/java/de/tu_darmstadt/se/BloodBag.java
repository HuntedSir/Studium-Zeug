package de.tu_darmstadt.se;

import java.util.List;

public class BloodBag {

    public static class BloodType {
        boolean A;
        boolean B;
        boolean rhesusPositive;

        BloodType(boolean A, boolean B, boolean rhesusPositive) {
            this.A = A;
            this.B = B;
            this.rhesusPositive = rhesusPositive;
        }

        public static BloodType OPositive = new BloodType(false, false, true);
        public static BloodType ONegative = new BloodType(false, false, false);
        public static BloodType APositive = new BloodType(true, false, true);
        public static BloodType ANegative = new BloodType(true, false, false);
        public static BloodType BPositive = new BloodType(false, true, true);
        public static BloodType BNegative = new BloodType(false, true, false);
        public static BloodType ABPositive = new BloodType(true, true, true);
        public static BloodType ABNegative = new BloodType(true, true, false);
    }

    BloodType type;
    int amount; // in milliliters

    public BloodBag(BloodType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public static int computeCompatibleBlood(BloodType recipient, List<BloodBag> bags) {
        int amount = 0;
        for (BloodBag bag : bags) {
            var compatible = checkBloodCompatibility(recipient, bag);
            if (compatible) {
                amount += bag.amount;
            }
        }
        return amount;
    }


    private static boolean checkBloodCompatibility(BloodType recipient, BloodBag bag) {
        var compatible = true;
        if (bag.type.A && !recipient.A) {
            compatible = false;
        }
        if (bag.type.B && !recipient.B) {
            compatible = false;
        }
        if (bag.type.rhesusPositive && !recipient.rhesusPositive) {
            compatible = false;
        }


        return compatible;
    }
}