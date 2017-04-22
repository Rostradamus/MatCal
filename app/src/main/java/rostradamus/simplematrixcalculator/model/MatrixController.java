package rostradamus.simplematrixcalculator.model;

import java.util.ArrayList;
import java.util.List;

import rostradamus.simplematrixcalculator.exception.UnavailableMatrixException;
import rostradamus.simplematrixcalculator.exception.UnavailableVectorException;

/**
 * Created by rolee on 2017-03-06.
 */

public class MatrixController implements IMatrixController {
    private static MatrixController instance;
    private static IVectorController vectorController;
    MatrixController() {
        vectorController = VectorController.getInstance();
        System.out.println("Matrix Controller Created...");
    }

    public static MatrixController getInstance() {
        if (instance == null) instance = new MatrixController();
        return instance;
    }

    public IVectorController getVectorController() {
        return vectorController;
    }

    // EFFECT: return the number of columns in the matrix
    public int getNumColumns(Matrix matrix) {
        return matrix.getNumColumns();
    }

    // EFFECT: return the number of rows in the matrix
    public int getNumRows(Matrix matrix) {
        return matrix.getNumRows();
    }

    public boolean isNull(Matrix matrix) {return matrix.isNull(); }

    @Override
    public Matrix createMatrix(List<Vector> vectors) throws UnavailableMatrixException {
        return new Matrix(vectors);
    }

    @Override
    public Matrix addition(Matrix m1, Matrix m2) throws UnavailableMatrixException {
        if (m1.isNull() || m2.isNull())
            throw new UnavailableMatrixException("Error: One of the matrices is null");

        if (!isSameProperty(m1, m2))
            throw new UnavailableMatrixException("Error: Matrices have different properties");

        List<Vector> newVectors = new ArrayList<>();
        for (int i = 0; i < m1.getNumColumns(); i++){

            try {
                newVectors.add(vectorController.add(getVectorAt(m1, i), getVectorAt(m2, i)));
            } catch (UnavailableVectorException e){
                throw new UnavailableMatrixException(e.getMessage());
            }

        }

        return new Matrix(newVectors);
    }

    @Override
    public Matrix transpose(Matrix matrix) throws UnavailableMatrixException {
        return null;
    }

    @Override
    public Matrix gaussianElimination(Matrix matrix) throws UnavailableMatrixException {
        return null;
    }

    @Override
    public double determinant(Matrix matrix) throws UnavailableMatrixException {
        return 0;
    }

    @Override
    public Matrix inverse(Matrix matrix) throws UnavailableMatrixException {
        return null;
    }

    @Override
    public Matrix multiply(Matrix m1, Matrix m2) throws UnavailableMatrixException {
        return null;
    }

    @Override
    public double eigenvalue(Matrix matrix) throws UnavailableMatrixException {
        return 0;
    }

    @Override
    public Vector eigenvector(Matrix matrix) throws UnavailableMatrixException {
        return null;
    }


    private boolean isSameProperty(Matrix m1, Matrix m2){
        return m1.getNumColumns() == m2.getNumColumns() && m1.getNumRows() == m2.getNumRows();
    }

    private Vector getVectorAt(Matrix matrix, int index) throws UnavailableMatrixException {
        if (matrix.isNull())
            throw new UnavailableMatrixException("Error: Matrix is null. Can not reach the index");

        return matrix.getVectors().get(index);
    }

}
