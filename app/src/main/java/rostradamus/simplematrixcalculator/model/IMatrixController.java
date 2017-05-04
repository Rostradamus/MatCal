package rostradamus.simplematrixcalculator.model;

import java.util.*;

import rostradamus.simplematrixcalculator.exception.UnavailableMatrixException;

/**
 * Created by rolee on 2017-04-08.
 */

public interface IMatrixController {

    Matrix createMatrix(List<Vector> vectors) throws UnavailableMatrixException;

    VectorController getVectorController();

    Matrix add(Matrix m1, Matrix m2) throws UnavailableMatrixException;

    Matrix transpose(Matrix matrix) throws UnavailableMatrixException;

    Matrix gaussianElimination(Matrix matrix) throws UnavailableMatrixException;

    double determinant(Matrix matrix) throws UnavailableMatrixException;

    Matrix inverse(Matrix matrix) throws UnavailableMatrixException;

    Matrix multiply(Matrix m1, Matrix m2) throws UnavailableMatrixException;

    double eigenvalue(Matrix matrix) throws UnavailableMatrixException;

    Vector eigenvector(Matrix matrix) throws UnavailableMatrixException;



}
