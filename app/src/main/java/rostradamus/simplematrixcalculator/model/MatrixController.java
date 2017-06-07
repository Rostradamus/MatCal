package rostradamus.simplematrixcalculator.model;


import java.util.ArrayList;
import java.util.List;

import rostradamus.simplematrixcalculator.exception.UnavailableMatrixException;
import rostradamus.simplematrixcalculator.exception.UnavailableVectorException;
import rostradamus.simplematrixcalculator.Log;

/**
 * Created by rolee on 2017-03-06.
 */

public class MatrixController implements IMatrixController {
    private static MatrixController instance;
    MatrixController() {
        Log.i("Matrix Controller Created...");
    }

    public static MatrixController getInstance() {
        if (instance == null) instance = new MatrixController();
        return instance;
    }

    @Override
    public Matrix createMatrix(List<Vector> vectors) throws UnavailableMatrixException {
        return new Matrix(vectors);
    }

    @Override
    public Matrix add(Matrix m1, Matrix m2) throws UnavailableMatrixException {
        IVectorController ivc = VectorController.getInstance();

        if (!isSameProperty(m1, m2))
            throw new UnavailableMatrixException("Error: Matrices have different properties");

        List<Vector> newVectors = new ArrayList<>();
        for (int i = 0; i < m1.getNumColumns(); i++){

            try {
                newVectors.add(ivc.add(getVectorAt(m1, i), getVectorAt(m2, i)));
            } catch (UnavailableVectorException e){
                throw new UnavailableMatrixException(e.getMessage());
            }

        }

        return new Matrix(newVectors);
    }

    @Override
    public Matrix transpose(Matrix matrix) throws UnavailableMatrixException {
        List<Vector> rowVectors = new ArrayList<>();
        int row = matrix.getNumRows();
        for (int i = 0; i < row; i++) {
            List<Double> compsInRow = new ArrayList<>();
            for (Vector v: matrix) {
                compsInRow.add(v.getComponents().get(i));
            }
            rowVectors.add(new Vector(compsInRow));
        }

        return new Matrix(rowVectors);
    }

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

    // TODO
    @Override
    public Matrix gaussianElimination(Matrix matrix) throws UnavailableMatrixException {
        return null;
    }

    @Override
    public double determinant(Matrix matrix) throws UnavailableMatrixException {
        if (!matrix.isSquare())
            throw new UnavailableMatrixException("Error: The Number of Rows & Columns must be the same.");
        return determinantHelper(matrix);
    }

    // TODO: need to implement Gaussian to compute inverse matrix
    @Override
    public Matrix inverse(Matrix matrix) throws UnavailableMatrixException {
        if (!matrix.isSquare())
            throw new UnavailableMatrixException("Error: Input matrix must be square");
        double determinant = determinant(matrix);
        if (determinant == 0 )
            throw new UnavailableMatrixException("Error: Input matrix is Singular(No Inverse Matrix)");


        return null;
    }

    @Override
    public Matrix multiply(Matrix m1, Matrix m2) throws UnavailableMatrixException {
        if (m1.getNumColumns() != m2.getNumRows())
            throw new UnavailableMatrixException("The number of columns of the 1st matrix must equal the number of rows of the 2nd matrix.");
//        Matrix retMatrix = initMatrix(m1.getNumRows(), m2.getNumColumns());
        int row = m1.getNumRows();
        int col = m2.getNumColumns();
        double[][] retMatrix = new double[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                double result = 0;
                for (int k = 0; k < m1.getNumColumns(); k++) {
                    result += m1.getVectors().get(k).getComponents().get(i)
                            * m2.getVectors().get(j).getComponents().get(k);
                }
                retMatrix[i][j] = result;
            }
        }
        return matrixHelper(retMatrix);
    }

    @Override
    public Matrix scalarMultiplication(double scalar, Matrix matrix) throws UnavailableMatrixException {
        for (int i = 0; i < matrix.getNumColumns(); i++) {
            for (int j = 0; j < matrix.getNumRows(); j++) {
                double target = matrix.getVectors().get(i).getComponents().get(i);
                matrix.setComponentAt(j, i, scalar * target);
            }
        }
        return matrix;
    }

    // TODO
    @Override
    public double eigenvalue(Matrix matrix) throws UnavailableMatrixException {
        return 0;
    }

    // TODO
    @Override
    public Vector eigenvector(Matrix matrix) throws UnavailableMatrixException {
        return null;
    }

    // EFFECT: return the number of columns in the matrix
    public int getNumColumns(Matrix matrix) {
        return matrix.getNumColumns();
    }

    // EFFECT: return the number of rows in the matrix
    public int getNumRows(Matrix matrix) {
        return matrix.getNumRows();
    }

    public double getComponentAt(Matrix matrix, int row, int column) throws UnavailableMatrixException {
        return getVectorAt(matrix, column).getComponents().get(row);
    }

    private double determinantHelper(Matrix matrix) throws UnavailableMatrixException {

        double sum = 0;
        int column = matrix.getNumColumns();

        if (column == 1) {
            return getComponentAt(matrix, 0 ,0);
        }

        if (column == 2) {
            double first = getComponentAt(matrix, 0, 0) * getComponentAt(matrix, 1, 1);
            double second = getComponentAt(matrix, 0, 1) * getComponentAt(matrix, 1, 0);
            return first - second;
        }
        else {
            for (int i = 0; i < column; i++) {
                List<Vector> subVectors = new ArrayList<>();
                for (int j = 0; j < column; j++) {
                    if (i == j) continue;
                    List<Double> newComps = new ArrayList<>();
                    newComps.addAll(matrix.getVectors().get(j).getComponents());
                    newComps.remove(0);
                    Vector newVector = new Vector(newComps);
                    subVectors.add(newVector);
                }
                Matrix subMatrix = new Matrix(subVectors);
                if (i % 2 == 0)
                    sum += getComponentAt(matrix, 0, i) * determinantHelper(subMatrix);
                else
                    sum -= getComponentAt(matrix, 0, i) * determinantHelper(subMatrix);
            }
            return sum;
        }
    }


    private boolean isSameProperty(Matrix m1, Matrix m2){
        return m1.getNumColumns() == m2.getNumColumns() && m1.getNumRows() == m2.getNumRows();
    }

    private Vector getVectorAt(Matrix matrix, int index) throws UnavailableMatrixException {
        return matrix.getVectors().get(index);
    }

    private Matrix matrixHelper(double[][] matrix) throws UnavailableMatrixException{

        int row = matrix.length;
        int col = matrix[0].length;
        List<Vector> vectors = new ArrayList<>();
        for(int i = 0; i < col; i++) {
            List<Double> v = new ArrayList<>();
            for(int j = 0; j < row; j++) {
                v.add(matrix[j][i]);
            }
            vectors.add(new Vector(v));
        }

        return new Matrix(vectors);
    }
    /*
    private Matrix initMatrix(int row, int column) throws UnavailableMatrixException {
        List<Double> d = new ArrayList<>();
        for (int i = 0; i < row; i++)
            d.add((double) 0);
        List<Vector> vectors = new ArrayList<>();
        for (int j = 0; j < column; j++)
            vectors.add(new Vector(d));
        return new Matrix(vectors);
    }
    */
}
