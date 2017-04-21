package rostradamus.simplematrixcalculator.model;

import java.util.*;

import rostradamus.simplematrixcalculator.exception.UnavailableVectorException;

/**
 * Created by rolee on 2017-03-06.
 */

public class VectorController implements IVectorController {
    private static VectorController instance;

    public static VectorController getInstance() {
        if (instance == null) instance = new VectorController();
        return instance;
    }

    VectorController() {
        System.out.println("Vector Controller Created...");
    }

    @Override
    public Vector createVector() {
        return createVector(new ArrayList<Double>());
    }

    @Override
    public Vector createVector(List<Double> components) {
        return new Vector(components);
    }

    @Override
    public int getNumComponents(Vector vector) {
        return vector.getNumComponents();
    }

    @Override
    public double getComponentAt(Vector vector, int index) { return vector.getComponents().get(index); }

    @Override
    public boolean isNull(Vector vector) {
        return vector.isNull();
    }

    @Override
    public void addComponent(Vector vector, double component) {
        vector.addComponent(component);
    }

    @Override
    public Vector unitVector(Vector vector) throws UnavailableVectorException {
        double length = norm(vector);
        Vector retVector = new Vector(new ArrayList<Double>());
        for (Double component : vector.getComponents()) {
            retVector.addComponent(component / length);
        }
        return retVector;
    }

    @Override
    public double norm(Vector vector) throws UnavailableVectorException {
        double result = dotProduct(vector, vector);

        return Math.sqrt(result);
    }

    @Override
    public double dotProduct(Vector v1, Vector v2) throws UnavailableVectorException {
        if (v1.getNumComponents() != v2.getNumComponents())
            throw new UnavailableVectorException("Error: The Number of Components are Different");
        if (v1.isNull() || v2.isNull())
            throw new UnavailableVectorException("Error: One of the Vectors is Null");

        double result = 0;

        for (int i = 0; i < v1.getNumComponents(); i++) {
            result += v1.getComponents().get(i) * v2.getComponents().get(i);
        }

        return result;
    }

    @Override
    public Vector crossProduct(Vector v1, Vector v2) throws UnavailableVectorException {

        if (v1 == null || v2 == null)
            throw new UnavailableVectorException("Error: One of the Vectors is null");

        if (v1.getNumComponents() != 3 || v2.getNumComponents() != 3)
            throw new UnavailableVectorException("Error: The Number of Components must be 3");

        Vector retVector = createVector();
        int size = v1.getNumComponents();
        List<Double> comps1 = v1.getComponents();
        List<Double> comps2 = v2.getComponents();

        for (int i = 0; i < size; i++) {
            double newComp = comps1.get((i + 1) % size) * comps2.get((i + 2) % size)
                    - comps1.get((i+2) % size) * comps2.get((i + 1) % size);
            retVector.addComponent(newComp);
        }
        return retVector;
    }

    @Override
    public Vector add(Vector v1, Vector v2) throws UnavailableVectorException {
        Vector retVector = new Vector();
        if (v1.getNumComponents() != v2.getNumComponents())
            throw new UnavailableVectorException("Error: The Number of Components are Different");
        for (int i = 0; i < v1.getNumComponents(); i++) {
            retVector.addComponent(getComponentAt(v1, i) + getComponentAt(v2, i));
        }
        return retVector;
    }

}


