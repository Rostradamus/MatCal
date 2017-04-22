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


//    public Matrix transpose() throws UnavailableMatrixException{
//        List<Vector> rowVectors = new ArrayList<Vector>();
//        for (int i = 0; i < numRows; i++) {
//            Vector rowVector = new Vector();
//            for (Vector v: this) {
//                rowVector.addComponent(v.getComponents().get(i));
//            }
//            rowVectors.add(rowVector);
//        }
//
//        return new Matrix(rowVectors);
//    }

//    public Matrix gaussianElimination() {
//        return null;
//    }
//
//    public static Matrix add(Matrix matrix1, Matrix matrix2) throws UnavailableMatrixException{
//        if (matrix1.numRows != matrix2.numRows || matrix1.numColumns != matrix2.numColumns)
//            throw new UnavailableMatrixException("The size of matrix is different");
//        List<Vector> retVectors = new ArrayList<>();
//
//
//        for (int i = 0; i < matrix1.numColumns; i++) {
//            Vector retVector = new Vector();
//            for (int j = 0; j < matrix1.getNumRows(); j++) {
//
//            }
//        }
//
//        return null;
//    }

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

