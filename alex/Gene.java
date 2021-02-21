// Programmed by Laura Esguerra and Alex Moosbrugger
// laurale and akm5
//
// This class represents a gene with associated expression data

public class Gene {
    // Data members
    private String geneName; //gene name
    private double[] exprValues; // Actual values of expression data
    private int[] exprRanks;     // Ranks of expression data

    // Constructor; sets up instance fields
    public Gene(String name, double[] expressionVals) {
        geneName = name;
        exprValues = new double[expressionVals.length];
        for (int i = 0; i < exprValues.length; i++)
            exprValues[i] = expressionVals[i]; // copies input array into variable array
        exprRanks = new int[exprValues.length];
        for (int i = 0; i < exprValues.length; i++)
            for (int j = 0; j < exprValues.length; j++)
                if (exprValues[j] < exprValues[i])
                    exprRanks[i]++; // ranks the exprValues from smallest to largest
    }

    // Required for HashSet
    public boolean equals(Object o) {
        Gene g = (Gene) o; // This casts Object o to Gene
        // TODO (checks that two genes are equal, based on geneName) / DONE THINK
        return this.geneName.equals(g.geneName);
        // * Had if statement, auto-replaced with above

    }

    // Required for HashSet
    public int hashCode() {
        return this.geneName.hashCode();
        // TODO (hashCode for Gene is the same as hashCode for geneName) / DONE THINK

    }

    // Gets expression values
    public double[] getValues() {
        return exprValues;
    }

    // Gets gene name
    public String getName() {
        return geneName;
    }

    // Computes Euclidean distance to another gene. Distance = 0 indicates
    // identical expression data, with higher values representing increasingly
    // dissimilar expression data.
    public double euclideanDistance(Gene other) {
        // TODO / DONE THINK
        double distSum = 0;
        for (int ind = 0; ind < other.exprValues.length; ind++) {
            double dist = this.exprValues[ind] - other.exprValues[ind];
            distSum += (dist * dist);
        }
        return Math.sqrt(distSum);
        // This should calculate and return the Euclidean distance between
        // the calling Gene and the argument Gene.
    }

    // Computes Spearman distance between the two genes. The range of this measure
    // is between 0 and 2, with 0 representing perfectly correlated expression
    // data, 1 representing uncorrelated data, and 2 representing perfectly
    // anticorrelated data.
    public double spearmanDistance(Gene other) {
        int l = other.exprRanks.length;
        double[] d_2 = new double[l];
        double d_2Sum = 0;
        for (int ind = 0; ind < l; ind++) {
            d_2[ind] = (this.exprRanks[ind] - other.exprRanks[ind]) *
                    (this.exprRanks[ind] - other.exprRanks[ind]);
            d_2Sum += d_2[ind];

        }
        double numerator = 6.0 * d_2Sum;
        double denominator = Math.pow(l, 3) - l;
        double fraction = numerator / denominator;
        return 1 - fraction;

        // FINISH

        // TODO / DONE THINK
        // This should calculate and return the distance between the calling
        // Gene and the argument Gene determined from the Spearman Correlation

    }
}
// watched https://www.youtube.com/watch?v=WPcKwA5WF7s, only until he went into
// a code example, then we stopped. SO we only watched the theoretical explanation.
