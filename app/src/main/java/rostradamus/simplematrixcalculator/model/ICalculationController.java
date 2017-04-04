package rostradamus.simplematrixcalculator.model;

import java.util.List;

import rostradamus.simplematrixcalculator.exception.UnavailableVectorException;

/**
 * Created by rolee on 2017-03-14.
 */

public interface ICalculationController {


    /*
     *  VECTOR CALCULATION
     */

    /**
     *  Get a norm of given Vector
     *
     */

    Vector createVector(List<Double> components);

    double norm(Vector vector) throws UnavailableVectorException;

    /**
     *  Get a unit vector of given Vector
     */
    Vector unitVector(Vector vector) throws UnavailableVectorException;

    /**
     *  Get dot product of two given Vectors
     */
    double dotProduct(Vector v1, Vector v2) throws UnavailableVectorException;



    /*
     *  MATRIX CALCULATION
     */

    /**
     *  TODO:
     */


}
