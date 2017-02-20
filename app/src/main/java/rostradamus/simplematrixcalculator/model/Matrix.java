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
    private boolean isNull;


    // Constructor
    // MODIFIES: this
    // EFFECT: takes user's input to create a Matrix
    public Matrix(Vector... vectors) throws UnavailableMatrixException {
        this.vectors = new ArrayList<Vector>();
        for (Vector vector : vectors)
            this.vectors.add(vector);
        if (!isVaildMatrix(this.vectors))
            throw new UnavailableMatrixException("The number of rows are not valid");
        this.numColumns = this.vectors.size();
        this.numRows = this.vectors.get(0).getNumComponents();
        this.isNull = false;
    }

    // Constructor
    // MODIFIES: this
    // EFFECT: used to duplicate or create a Matrix within a method
    private Matrix(List<Vector> vectors) throws UnavailableMatrixException {
        if (!isVaildMatrix(vectors))
            throw new UnavailableMatrixException("The number of rows are not valid");
        this.vectors = new ArrayList<>(vectors);
        this.numColumns = this.vectors.size();
        this.numRows = this.vectors.get(0).getNumComponents();
        this.isNull = false;
    }

    // EFFECT: return list of vectors in the matrix
    public List<Vector> getVectors() {
        return vectors;
    }

    // EFFECT: return the number of columns in the matrix
    public int getNumColumns() {
        return numColumns;
    }

    // EFFECT: return the number of rows in the matrix
    public int getNumRows() {
        return numRows;
    }

    // Check if the matrix is valid or not
    // EFFECT: return true if the matrix is valid
    //                        matrix has vectors with same number of components
    static public boolean isVaildMatrix(List<Vector> vectors) {
        if (vectors.size() == 0)
            return false;
        for (int i = 0; i < (vectors.size() - 1); i++) {
            if (vectors.get(i).getNumComponents() != vectors.get(i+1).getNumComponents())
                return false;
        }
        return true;
    }


    public Matrix transpose() throws UnavailableMatrixException{
        List<Vector> rowVectors = new ArrayList<Vector>();
        for (int i = 0; i < numRows; i++) {
            Vector rowVector = new Vector();
            for (Vector v: this) {
                rowVector.addComponent(v.getComponents().get(i));
            }
            rowVectors.add(rowVector);
        }

        return new Matrix(rowVectors);
    }

    public Matrix gaussianElimination() {
        return null;
    }

    static public Matrix add(Matrix matrix1, Matrix matrix2) throws UnavailableMatrixException{
        if (matrix1.numRows != matrix2.numRows || matrix1.numColumns != matrix2.numColumns)
            throw new UnavailableMatrixException("The size of matrix is different");
        List<Vector> retVectors = new ArrayList<>();


        for (int i = 0; i < matrix1.numColumns; i++) {
            Vector retVector = new Vector();
            for (int j = 0; j < matrix1.getNumRows(); j++) {

            }
        }

        return null;
    }


    @Override
    public Iterator<Vector> iterator() {
        return vectors.iterator();
    }
}

