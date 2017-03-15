package rostradamus.simplematrixcalculator.ui;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;


import rostradamus.simplematrixcalculator.R;
import rostradamus.simplematrixcalculator.model.CalculationController;
import rostradamus.simplematrixcalculator.model.ICalculationController;

public class VectorCalcuatorUI extends AppCompatActivity {
    private ICalculationController calculationController;
    private boolean isFragmentOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calculationController = CalculationController.getInstance();
        setContentView(R.layout.vector_calcuator_layout);
    }


    public void openFragment(View view) {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.input_frame_layout);
        frameLayout.setVisibility(View.VISIBLE);
    }


}
