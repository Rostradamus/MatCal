package rostradamus.simplematrixcalculator.ui;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;


import java.util.*;

import rostradamus.simplematrixcalculator.R;
import rostradamus.simplematrixcalculator.exception.UnavailableVectorException;
import rostradamus.simplematrixcalculator.model.CalculationController;
import rostradamus.simplematrixcalculator.model.ICalculationController;
import rostradamus.simplematrixcalculator.model.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class VectorInputFragment extends Fragment {
    private boolean debug = true;
    private Button confirm;
    private Button clear;
    private Button submit;
    private EditText editText;
    private ConstraintLayout firstVectorLayout;
    private ConstraintLayout secondVectorLayout;
    private List<EditText> firstVectorArr = new ArrayList<>();
    private List<EditText> secondVectorArr = new ArrayList<>();
    private ICalculationController calculationController;




    public VectorInputFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.vector_input_fragment_layout, container, false);
        calculationController = CalculationController.getInstance();

        confirm = (Button) v.findViewById(R.id.confirmButton);
        clear = (Button) v.findViewById(R.id.clearInput);
        editText = (EditText) v.findViewById(R.id.componentEditText);
        submit = (Button) v.findViewById(R.id.dotProductSubmit);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if (text.matches("")) {
                    alertHelper("Should enter the number");
                    return;
                }
                int numInput = Integer.parseInt(text);
                if (numInput < 1 || numInput > 5) {
                    alertHelper("Out of bound. (Should be between 1 ~ 5)");
                }
                else {
                    makeInputLayout(numInput);
                    Log.i("rowNumber", editText.getText().toString());
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flushInput();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Double> l1 = new ArrayList<>();
                List<Double> l2 = new ArrayList<>();

                for (EditText et: firstVectorArr) {
                    l1.add(Double.parseDouble(et.getText().toString()));
                }
                for (EditText et: secondVectorArr) {
                    l2.add(Double.parseDouble(et.getText().toString()));
                }
                Vector v1 = calculationController.createVector(l1);
                Vector v2 = calculationController.createVector(l2);
                double result = 0;
                try {
                    result = calculationController.dotProduct(v1, v2);
                } catch (UnavailableVectorException e) {
                    e.printStackTrace();
                }
                Log.i("Result from DotProduct", Double.toString(result));
            }
        });
        return v;
    }

    private void alertHelper(String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void makeInputLayout(int numInput) {
        flushInput();
        firstVectorLayout = (ConstraintLayout) getActivity().findViewById(R.id.firstVectorInput);
        secondVectorLayout = (ConstraintLayout) getActivity().findViewById(R.id.secondVectorInput);
        for (int i = 0; i < numInput; i++) {
            firstVectorLayout.addView(makeEditText(10 + i, firstVectorArr));
            secondVectorLayout.addView(makeEditText(20 + i, secondVectorArr));
        }
    }
    private void flushInput() {
        editText.getText().clear();
        for (int i = 0; i < firstVectorArr.size(); i++) {
            firstVectorLayout.removeView(firstVectorLayout.findViewById(10 + i));
            secondVectorLayout.removeView(secondVectorLayout.findViewById(20 + i));
        }
        firstVectorArr.clear();
        secondVectorArr.clear();

    }
    private EditText makeEditText(int _intID, List<EditText> vectorArr) {
        final View vectorText = getActivity().findViewById(R.id.firstVectorText);
        final int INITIAL_POSITION = vectorText.getHeight() + (int) vectorText.getY();
        final int INCREASE_RATIO_Y = 100;
        int y = INITIAL_POSITION + INCREASE_RATIO_Y * ((_intID >= 10 && _intID < 15) ? _intID - 10 : _intID - 20);

        EditText editText = new EditText(getActivity());
        editText.setId(_intID);
        editText.setHint("My lines" + _intID);

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        // editText.setLeft(vectorText.getLeft()); TODO
        editText.setLayoutParams(layoutParams);

        editText.setY(y);


        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        vectorArr.add(editText);
        if (debug) {
            Log.i("Y-POS => " + Integer.toString(_intID), Integer.toString(y));
            Log.i("INITIAL_POSITION", Integer.toString(INITIAL_POSITION));
            Log.i("TEXT_Y_POS => " + Integer.toString(editText.getId()), Integer.toString((int)editText.getY()));
        }
        return editText;
    }


}
