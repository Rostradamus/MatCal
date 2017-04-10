package rostradamus.simplematrixcalculator.model;

import java.util.*;

import rostradamus.simplematrixcalculator.exception.UnavailableVectorException;

/**
 * Created by rolee on 2017-03-06.
 */

public class VectorController implements IVectorController {
    private static VectorController instance;
    private static final String DEFAULT_DELTA_VALUE = "%.7f";

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
    public List<Double> getComponents(Vector vector) {
        return Collections.unmodifiableList(vector.getComponents());
    }

    @Override
    public int getNumComponents(Vector vector) {
        return vector.getNumComponents();
    }

    @Override
    public void setComponent(Vector vector, int pos, double component) throws UnavailableVectorException {
        vector.setComponent(pos, component);
    }

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
            retVector.addComponent(cutDouble(component / length));
        }
        return retVector;
    }

    @Override
    public double norm(Vector vector) throws UnavailableVectorException {
        double result = dotProduct(vector, vector);

        return cutDouble(Math.sqrt(result));
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

        return cutDouble(result);
    }


    // TODO
    @Override
    public Vector crossProduct(List<Vector> vectors) throws UnavailableVectorException {

        int size = vectors.get(0).getNumComponents();
        Vector retVector = null;

        for (int i = 0; i < size; i++) {
            if (retVector == null)
                retVector = crossProduct(vectors.get(i), vectors.get(++i));
            else
                retVector = crossProduct(retVector, vectors.get(i));
        }

        return retVector;
    }

    private Vector crossProduct(Vector v1, Vector v2) throws UnavailableVectorException{

        if (v1.getNumComponents() != v2.getNumComponents())
            throw new UnavailableVectorException("Error: The Number of Components are Different");

        Vector retVector = createVector();
        int size = v1.getNumComponents();
        List<Double> comps1 = v1.getComponents();
        List<Double> comps2 = v2.getComponents();

        for (int i = 0; i < size; i++) {
            double newComp = comps1.get((i + 1) % size) * comps2.get((i + 2) % size)
                    - comps1.get((i+2) % size) * comps2.get((i + 1) % size);
            retVector.addComponent(cutDouble(newComp));
        }
        return retVector;
    }


    @Override
    public Vector addition(List<Vector> vectors) throws UnavailableVectorException {
        Vector retVector = null;
        int numComponents = vectors.get(0).getNumComponents();
        for (Vector v : vectors) {
            if (numComponents != v.getNumComponents())
                throw new UnavailableVectorException("Error: The Number of Components are Different");
            if (retVector == null) retVector = createVector(v.getComponents());
            else {
                for (int i = 0; i < numComponents; i++) {
                    retVector.setComponent(i, cutDouble(retVector.getComponents().get(i) + v.getComponents().get(i)));
                }
            }
        }
        return retVector;
    }

    // TODO
    @Override
    public Vector substraction(List<Vector> vectors) throws UnavailableVectorException {
        return null;
    }

    public double cutDouble(double value) {
        return Double.parseDouble(String.format(DEFAULT_DELTA_VALUE, value));
    }

    public List<Double> cutDouble(List<Double> doubles) {
        List<Double> newComps = new ArrayList<>();
        for (Double comp: doubles) newComps.add(cutDouble(comp));
        return newComps;
    }

}


