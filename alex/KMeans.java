// Programmed by Laura Esguerra and Alex Moosbrugger
// laurale and akm5
//
// Class for computing and representing k-means clustering of expression data.

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class KMeans {
    // Data members
    private Gene[] genes; // Array of all genes in dataset
    private Cluster[] clusters; //Array of all clusters; null until
    // performClustering is called.

    // Constructor; loads expression data from file <fileName>. The genes array is
    // filled with the genes from the dataset. The clusters array is not filled;
    // to fill it, call performClustering.
    public KMeans(String fileName) {
        clusters = null;
        BufferedReader reader;
        int numGenes;
        String[] splitLine;
        double[] exprValues;

        // Creates a new KMeans object by reading in all of the genes
        // and expression levels located in filename
        try {
            reader = new BufferedReader(new FileReader(fileName));

            // Count the number of lines to determine how many genes are present
            for (numGenes = 0; reader.readLine() != null; numGenes++) ;

            // Close and then re-open the file now that we know its length
            reader.close();
            reader = new BufferedReader(new FileReader(fileName));

            // Allocate space for the genes array
            genes = new Gene[numGenes];
            exprValues = null;

            // Now, read in each line and create the corresponding Gene object
            for (int i = 0; i < genes.length; i++) {
                String line = reader.readLine();
                // The files are tab-delimited, so split on tabs (\t)
                splitLine = line.split("\t");

                // The first entry in the parts array is the gene name, the rest
                // are expression levels for that gene
                if ((exprValues == null) || (exprValues.length != (splitLine.length - 1)))
                    exprValues = new double[splitLine.length - 1];
                for (int j = 0; j < exprValues.length; j++)
                    exprValues[j] = Double.parseDouble(splitLine[j + 1]);

                // Finally, create the Gene in the array
                genes[i] = new Gene(splitLine[0], exprValues);
            }

            // Lastly, close the file
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("ERROR:  Unable to locate " + fileName + ".");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("ERROR:  Unable to read from " + fileName + ".");
            System.exit(0);
        }
    }

    // gets the array of all genes in the dataset.
    public Gene[] getGenes() {
        return genes;
    }

    // Gets the array of all clusters after performing k-means clustering. Note
    // that this method will return null if performClustering has not yet been
    // called.
    public Cluster[] getClusters() {
        return clusters;
    }

    // Perform k-means clustering with the specified number of clusters and
    // distance metric. The "metric" parameter can take two values: "euclid" for
    // Euclidean distance, or "spearman" for Spearman distance.
    public void performClustering(int numClusters, String metric) {
        if (!metric.equals("euclid") && !metric.equals("spearman"))
            throw new IllegalArgumentException("Parameter <metric> is "
                    + metric + ", should be \"euclid\" or \"spearman\".");
        else if (metric.equals("euclid")) {
            Gene[] originalCentroids = new Gene[numClusters];
            clusters = new Cluster[numClusters];
            for (int i = 0; i < numClusters; i++) {
                originalCentroids[i] = getGenes()[i];
                clusters[i].addGene(originalCentroids[i]);
            }
            for (Gene gene : genes) {
                double[] dists = new double[numClusters];
                double shortestDist = 0;
                int shortestDistIndex = 0;
                for (int j = 0; j < numClusters; j++) {
                    dists[j] = gene.euclideanDistance(originalCentroids[j]);
                    if (j == 0) {
                        shortestDist = dists[0];
                    } else {
                        if (dists[j] < shortestDist) {
                            shortestDist = dists[j];
                            shortestDistIndex = j;
                        }
                    }
                }
                clusters[shortestDistIndex].addGene(gene);
            }

            Cluster[] s1 = new Cluster[numClusters];
            Cluster[] s2 = new Cluster[numClusters];
            boolean same = false;
            for (int m = 0; m < clusters.length; m++) {
                s1[m] = clusters[m];
            }
            while (!same) {
                same = s1.containsAll(s2) && s2.containsAll(s1); // why check at beginning and end?
                for (int n = 0; n < clusters.length; n++) {
                    s2[n] = s1[n];
                }
                Gene[] newCentroids = new Gene[numClusters];
                for (int i = 0; i < numClusters; i++) {
                    newCentroids[i] = s1[i].centroid();
                }
                for (Gene gene : genes) {
                    double[] dists = new double[numClusters];
                    double shortestDist = 0;
                    int shortestDistIndex = 0;
                    for (int j = 0; j < numClusters; j++) {
                        dists[j] = gene.euclideanDistance(newCentroids[j]);
                        if (j == 0) {
                            shortestDist = dists[0];
                        } else {
                            if (dists[j] < shortestDist) {
                                shortestDist = dists[j];
                                shortestDistIndex = j;
                            }
                        }
                    }
                    s1[shortestDistIndex].addGene(gene);
                }
                same = s1.containsAll(s2) && s2.containsAll(s1);
            }


            // now we have to add the gene to the appropriate cluster

            // 1 assign gene to cluster based on which cluster's centroid it is
            // closest to
            // 2 update the centroid of each cluster based on the 'center of
            // mass' of the genes currently in that cluster, output new
            // centroid as gene object
            // 3 reassign genes to new cluster based on newly outputted centroids
            // 4 compare new clusters and old clusters to see if anything has
            // changed
            // 5 if same, stop and exit; if not same, repeat 2-5

            // *TODO this
        } else // because (metric.equals("spearman")) is always remaining true
        { // *TODO this
            // just copy and paste from euclid and change the word
        }
    }

    // Add code here to actually perform the clustering algorithm
}

    // Main method. Run this program using the following command.
    // java KMeans <input_data_filename> <K> <metric> <output_filename>
    //
    // This program will print out the genes in each cluster, and will also create
    // a jpeg file showing the expression data of each cluster. The jpeg files
    // will be named "<output_filename><clusterNumber>.jpg".
    public static void main(String[] astrArgs) {
        // TODO
        String input_data_filename = astrArgs[0];
        int K = Integer.parseInt(astrArgs[1]);
        String metric = astrArgs[2];
        String output_filename = astrArgs[3];

        KMeans KM = new KMeans(input_data_filename);
        KM.performClustering(K, metric);
        // Add code here to make a new KMeans object (which will load the data).
        // Then perform the clustering and output the results to the screen
        // and create the image files

    /*
     * The code commented out here is just an example of how to use
     * the provided functions and constructors.
     *
     KMeans KM = new KMeans( "yeast_stress.pcl" );
     Cluster C = new Cluster( );
     for( int i = 0; i < KM.getGenes( ).length; ++i )
     C.addGene( KM.getGenes( )[i] );
     C.createJPG( "test", 1 );
     */
    }
}
