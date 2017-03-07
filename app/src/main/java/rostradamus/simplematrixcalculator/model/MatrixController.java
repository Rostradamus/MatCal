package rostradamus.simplematrixcalculator.model;

/**
 * Created by rolee on 2017-03-06.
 */

public class MatrixController {
    private static MatrixController instance;
    private MatrixController() {
        System.out.println("Matrix Controller Created...");
    }

    public static MatrixController getInstance(){
        if (MatrixController.instance == null) return new MatrixController();
        return instance;
    }
}
