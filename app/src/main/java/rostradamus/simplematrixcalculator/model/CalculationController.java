package rostradamus.simplematrixcalculator.model;

import java.util.List;

import rostradamus.simplematrixcalculator.exception.UnavailableVectorException;

/**
 * Created by rolee on 2017-03-14.
 */

public class CalculationController implements ICalculationController {
    private static CalculationController instance;
    private VectorController vectorController;
    private MatrixController matrixController;

    private CalculationController() {
        vectorController = new VectorController();
        matrixController = new MatrixController();
    }

    public static CalculationController getInstance() {
        if (instance == null)
            return new CalculationController();
        return instance;
    }

    public VectorController getVectorController() {
        return vectorController;
    }

    public MatrixController getMatrixController() {
        return matrixController;
    }

    public static void flush() {
        instance.vectorController = null;
        instance.matrixController = null;
        instance = null;
    }

    @Override
    public Vector createVector(List<Double> components) {
        return vectorController.createVector(components);
    }

    @Override
    public double norm(Vector vector) throws UnavailableVectorException{
        return vectorController.norm(vector);
    }

    @Override
    public Vector unitVector(Vector vector) throws UnavailableVectorException{
        return vectorController.unitVector(vector);
    }

    @Override
    public double dotProduct(Vector v1, Vector v2) throws UnavailableVectorException {
        return vectorController.dotProduct(v1, v2);
    }
}
