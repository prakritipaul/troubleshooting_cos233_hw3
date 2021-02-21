// Programmed by Laura Esguerra and Alex Moosbrugger
// laurale and akm5
//
// This class represents a cluster of genes with similar expression data. It
// contains methods for creating an image of the cluster's expression data and
// for returning the centroid of the cluster.

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Cluster {
    // Constants for creating Jpeg images. Do not change these.
    private static final double C_D_CUTOFF = 2;
    private static final int C_IX = 8;
    private static final int C_IY = 4;

    // Data members
    private HashSet<Gene> geneSet; // List of genes in cluster

    // Constructor; creates an empty cluster.
    public Cluster() {
        geneSet = new HashSet<Gene>;
        // TODO / THINK DONE
        // make a new HashSet of objects of type Gene


    }

    // Prints the names of all genes in the cluster
    public void printGeneNames() {
        // TODO / DONE THINK THIS IS JUST CODE FROM THE ASSIGNMENT PAGE
        for (Gene gene : geneSet) {
            System.out.println(gene.getName());
        }
        // This should print the name of each gene in the cluster
        // with one name printed per line
    }

    // Adds a gene to the cluster
    public void addGene(Gene gene) {
        // Append a gene to the set
        geneSet.add(gene);
    }

    // Getter method for geneSet
    public HashSet<Gene> getGeneSet() {
        return geneSet;
    }

    // Returns the centroid of the cluster as a Gene object with name "centroid".
    // The expression data of the returned gene is the centroid of the cluster.
    public Gene centroid() {
        // TODO / DONE THINK!
        int l = 0;
        for (Gene holder : 1) {
            l = holder.getValues().length;
        }
        int count = 0;
        double[] sum = new double[l];
        for (Gene gene : geneSet) {
            for (int ind = 0; ind < gene.getValues().length; ind++) {
                sum[ind] += gene.getValues()[ind];
            }
            count++;
        }
        for (int ind = 0; ind < l; ind++) {
            sum[ind] = sum[ind] / count;
        }
        // Add code here to calculate and return the centroid
        // of the cluster of genes
        return new Gene("centroid", sum);
    }

    // Creates an image of this cluster's expression data. The image will be
    // stored in file "<fileName><id>.jpg". Do not change this method.
    public void createJPG(String fileName, int id) {
        String strOut;
        int i, j, iGenes, iConditions;
        double dValue;
        BufferedImage bimg;
        Graphics2D gr2d;
        Color colr;

        strOut = fileName + id + ".jpg";

        // Initialize some values
        Vector<Gene> geneVector = new Vector<Gene>();
        Iterator<Gene> it = geneSet.iterator();
        while (it.hasNext())
            geneVector.add(it.next());

        Comparator<Gene> GENE_ORDER = new Comparator<Gene>() {
            public int compare(Gene g1, Gene g2) {
                return g1.getName().compareTo(g2.getName());
            }
        };

        Collections.sort(geneVector, GENE_ORDER);

        iGenes = geneVector.size();
        iConditions = (geneVector.get(0)).getValues().length;

        // Create the empty image and graphics2D
        bimg = new BufferedImage(C_IX * iConditions, C_IY * iGenes,
                BufferedImage.TYPE_INT_RGB);
        gr2d = bimg.createGraphics();

        // Draw a rectangle for each gene/condition pair
        for (i = 0; i < iGenes; ++i) {
            for (j = 0; j < iConditions; ++j) {
                dValue = (geneVector.get(i)).getValues()[j];
                if (dValue < 0)
                    colr = new Color(0.0f, (dValue < -C_D_CUTOFF) ? 1.0f
                            : (float) (dValue / -C_D_CUTOFF), 0.0f);
                else
                    colr = new Color((dValue > C_D_CUTOFF) ? 1.0f
                            : (float) (dValue / C_D_CUTOFF), 0.0f, 0.0f);
                gr2d.setColor(colr);
                gr2d.fill(new Rectangle2D.Float(j * C_IX, i * C_IY, C_IX, C_IY));
            }
        }
        gr2d.dispose();

        try {
            // Output the image to file
            File outFile = new File(strOut);
            ImageIO.write(bimg, "jpg", outFile);
        } catch (IOException e) {
            System.out.println("ERROR: Unable to write image in " + strOut + ".");
        }
    }
}
