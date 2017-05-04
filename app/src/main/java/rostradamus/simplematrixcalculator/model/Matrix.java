package rostradamus.simplematrixcalculator.model;

import java.util.*;

import rostradamus.simplematrixcalculator.exception.UnavailableMatrixException;

/**
 * Created by rolee on 2016-12-28.
 */


public class Matrix implements Iterable<Vector> {

    private List<Vector> vectors;
    private int numColumns;
    private int numRows;



    // Constructor
    // MODIFIES: this
    // EFFECT: used to duplicate or create a Matrix within a method
    Matrix(List<Vector> vectors) throws UnavailableMatrixException {
        if (!isValidMatrix(vectors))
            throw new UnavailableMatrixException("The number of rows are not valid");
        this.vectors = vectors;
        this.numColumns = this.vectors.size();
        this.numRows = this.vectors.get(0).getNumComponents();

    }

    // EFFECT: return list of vectors in the matrix
    List<Vector> getVectors() {
        return vectors;
    }

    // EFFECT: return the number of columns in the matrix
    int getNumColumns() {
        return numColumns;
    }

    // EFFECT: return the number of rows in the matrix
    int getNumRows() {
        return numRows;
    }

    boolean isSquare() {
        return numRows == numColumns;
    }

    // Check if the matrix is valid or not
    // EFFECT: return true if the matrix is valid
    //                        matrix has vectors with same number of components
    private boolean isValidMatrix(List<Vector> vectors) throws UnavailableMatrixException{
        if (vectors.size() == 0)
            throw new UnavailableMatrixException("Matrix with no vector is NOT valid");
        int numComponents = vectors.get(0).getNumComponents();
        for (Vector v: vectors) {
            if (numComponents != v.getNumComponents()) return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Matrix vectors1 = (Matrix) o;

        if (numColumns != vectors1.numColumns) return false;
        if (numRows != vectors1.numRows) return false;
        return vectors.equals(vectors1.vectors);

    }

    @Override
    public int hashCode() {
        int result = vectors.hashCode();
        result = 31 * result + numColumns;
        result = 31 * result + numRows;
        return result;
    }

    @Override
    public Iterator<Vector> iterator() {
        return vectors.iterator();
    }
}

