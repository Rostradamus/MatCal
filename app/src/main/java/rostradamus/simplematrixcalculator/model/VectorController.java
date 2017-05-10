package rostradamus.simplematrixcalculator.model;

import java.util.*;

import rostradamus.simplematrixcalculator.Log;
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
        Log.i("Vector Controller Created...");
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
    public Vector unitVector(Vector u) throws UnavailableVectorException {
        double length = norm(u);
        Vector retVector = new Vector(new ArrayList<Double>());
        for (Double component : u.getComponents()) {
            retVector.addComponent(component / length);
        }
        return retVector;
    }

    @Override
    public double norm(Vector u) throws UnavailableVectorException {
        double result = dotProduct(u, u);

        return Math.sqrt(result);
    }

    @Override
    public double dotProduct(Vector u, Vector v) throws UnavailableVectorException {
        if (!isSameSize(u,v))
            throw new UnavailableVectorException("Error: The Number of Components are Different");
        if (u.isNull() || v.isNull())
            throw new UnavailableVectorException("Error: One of the Vectors is Null");

        double result = 0;

        for (int i = 0; i < u.getNumComponents(); i++) {
            result += u.getComponents().get(i) * v.getComponents().get(i);
        }

        return result;
    }

    @Override
    public Vector crossProduct(Vector u, Vector v) throws UnavailableVectorException {

        if (u == null || v == null)
            throw new UnavailableVectorException("Error: One of the Vectors is null");

        if (u.getNumComponents() != 3 || v.getNumComponents() != 3)
            throw new UnavailableVectorException("Error: The Number of Components must be 3");

        List<Double> newComponent = new ArrayList<>();
        int size = u.getNumComponents();
        List<Double> comps1 = u.getComponents();
        List<Double> comps2 = v.getComponents();

        for (int i = 0; i < size; i++) {
            double newComp = comps1.get((i + 1) % size) * comps2.get((i + 2) % size)
                    - comps1.get((i+2) % size) * comps2.get((i + 1) % size);
            newComponent.add(newComp);
        }
        return new Vector(newComponent);
    }

    @Override
    public Vector add(Vector u, Vector v) throws UnavailableVectorException {

        if (!isSameSize(u,v))
            throw new UnavailableVectorException("Error: The Number of Components are Different");

        List<Double> newComponent = new ArrayList<>();
        for (int i = 0; i < u.getNumComponents(); i++) {
            newComponent.add(getComponentAt(u, i) + getComponentAt(v, i));
        }
        return new Vector(newComponent);
    }

    @Override
    public Vector scalarMultiplication(double c, Vector u) throws UnavailableVectorException {
        if (isNull(u))
            throw new UnavailableVectorException("Error: The Vector is Null");
        List<Double> newComps = new ArrayList<>();
        for (double comp: u) {
            newComps.add(c * comp);
        }
        return new Vector(newComps);
    }

    @Override
    public double scalarProjection(Vector u, Vector v) throws UnavailableVectorException {
        if (!isSameSize(u,v))
            throw new UnavailableVectorException("Error: The Number of Components are Different");

        return dotProduct(u,v) / norm(u);
    }

    @Override
    public Vector projection(Vector u, Vector v) throws UnavailableVectorException {
        if (!isSameSize(u,v))
            throw new UnavailableVectorException("Error: The Number of Components are Different");
        double scalar = scalarProjection(u,v);
        Vector unitVector = unitVector(u);
        return scalarMultiplication(scalar, unitVector);
    }

    @Override
    public double angle(Vector u, Vector v) throws UnavailableVectorException {
        return angle(u, v, true);
    }

    @Override
    public double angle(Vector u, Vector v, boolean isDegree) throws UnavailableVectorException {
        double result = Math.acos(scalarProjection(u,v) / norm(v));
        if (isDegree) return Math.toDegrees(result);
        else return result;
    }

    private boolean isSameSize(Vector u, Vector v) {
        return u.getNumComponents() == v.getNumComponents();
    }

}


