package rostradamus.simplematrixcalculator.model;

import java.util.List;

import rostradamus.simplematrixcalculator.exception.UnavailableMatrixException;

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

    private Vector getVectorAt(Matrix matrix, int index) {
        return matrix.getVectors().get(index);
    }

    @Override
    public Matrix createMatrix() {
        return null;
    }

    @Override
    public Matrix createMatrix(List<Vector> vectors) {
        return null;
    }

    @Override
    public Matrix addition(Matrix m1, Matrix m2) throws UnavailableMatrixException {
        if (!isSameProperty(m1, m2))
            throw new UnavailableMatrixException("Error: Matrices have different properties");

        for (int i = 0; i < m1.getNumColumns(); i++){
            Vector newVector = vectorController.add(getVectorAt(m1, i), getVectorAt(m2, i));
        }

        return null;
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
}
