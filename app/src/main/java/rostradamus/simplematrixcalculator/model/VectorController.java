package rostradamus.simplematrixcalculator.model;

import android.util.Log;

import java.util.*;

import rostradamus.simplematrixcalculator.exception.UnavailableVectorException;

/**
 * Created by rolee on 2017-03-06.
 */

public class VectorController {

    VectorController() {
        System.out.println("Vector Controller Created...");
    }

    public Vector createVector() {
        return new Vector();
    }

    public Vector createVector(List<Double> components) {
        return new Vector(components);
    }

    public List<Double> getComponents(Vector vector) {
        return Collections.unmodifiableList(vector.getComponents());
    }

    public int getNumComponents(Vector vector) {
        return vector.getNumComponents();
    }

    public void setComponent(Vector vector, int pos, double component) throws UnavailableVectorException {
        vector.setComponent(pos, component);
    }

    public boolean isNull(Vector vector) { return vector.isNull(); }

    public void addComponent(Vector vector, double component) { vector.addComponent(component); }

    public Vector unitVector(Vector vector) throws UnavailableVectorException {
        double length = norm(vector);
        Vector retVector = new Vector(new ArrayList<Double>());
        for (Double component : vector.getComponents()) {
            retVector.addComponent(component/length);
        }
        return retVector;
    }

    public double norm(Vector vector) throws UnavailableVectorException {
        double result = dotProduct(vector, vector);

        return Math.sqrt(result);
    }

    public double dotProduct(Vector v1, Vector v2) throws UnavailableVectorException{
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


}


