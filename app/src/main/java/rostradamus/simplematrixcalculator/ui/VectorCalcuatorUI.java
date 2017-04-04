package rostradamus.simplematrixcalculator.ui;



import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;


import rostradamus.simplematrixcalculator.R;
import rostradamus.simplematrixcalculator.model.CalculationController;
import rostradamus.simplematrixcalculator.model.ICalculationController;

public class VectorCalcuatorUI extends AppCompatActivity {
    private ICalculationController calculationController;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment openedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calculationController = CalculationController.getInstance();
        setContentView(R.layout.vector_calcuator_layout);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
    }


    public void openFragment(View view) {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.input_frame_layout);
        if (openedFragment != null) {
            frameLayout.setVisibility(View.GONE);
        }
        else {
            openedFragment = new VectorInputFragment();
            frameLayout.setVisibility(View.VISIBLE);
            fragmentTransaction.add(R.id.input_frame_layout, openedFragment);
            fragmentTransaction.commit();
        }
    }


}
