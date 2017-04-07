package rostradamus.simplematrixcalculator.ui;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.*;
import android.widget.*;


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
    private TextView resultView;
    private int layoutHeight;
    private static final int HEIGHT_MARGIN = 200;



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
        resultView = (TextView) v.findViewById(R.id.resultView);

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
                calculateResult();
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
        ViewGroup.LayoutParams params = firstVectorLayout.getLayoutParams();
        params.height = layoutHeight + HEIGHT_MARGIN;
        firstVectorLayout.setLayoutParams(params);
        params = secondVectorLayout.getLayoutParams();
        params.height = layoutHeight + HEIGHT_MARGIN;
        secondVectorLayout.setLayoutParams(params);

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
        final int INITIAL_POSITION = 10;
        final int INCREASE_RATIO_Y = 100;
        layoutHeight = INITIAL_POSITION + INCREASE_RATIO_Y * ((_intID >= 10 && _intID < 15) ? _intID - 10 : _intID - 20);

        EditText editText = new EditText(getActivity());
        editText.setId(_intID);
        editText.setHint("My lines" + _intID);

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // editText.setLeft(vectorText.getLeft()); TODO
        editText.setLayoutParams(layoutParams);

        editText.setY(layoutHeight);


        editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        vectorArr.add(editText);
        if (debug) {
            Log.i("Y-POS => " + Integer.toString(_intID), Integer.toString(layoutHeight));
            Log.i("INITIAL_POSITION", Integer.toString(INITIAL_POSITION));
            Log.i("TEXT_Y_POS => " + Integer.toString(editText.getId()), Integer.toString((int)editText.getY()));
        }
        return editText;
    }

    private void calculateResult() {
        List<Double> l1 = new ArrayList<>();
        List<Double> l2 = new ArrayList<>();
        try {
            for (EditText et: firstVectorArr) {
                l1.add(Double.parseDouble(et.getText().toString()));
            }
            for (EditText et: secondVectorArr) {
                l2.add(Double.parseDouble(et.getText().toString()));
            }
            Vector v1 = calculationController.createVector(l1);
            Vector v2 = calculationController.createVector(l2);

            double result = calculationController.dotProduct(v1, v2);
            Log.i("Result from DotProduct", Double.toString(result));
            resultView = (TextView) getActivity().findViewById(R.id.resultView);
            resultView.setText("" + result);

        } catch (UnavailableVectorException e) {
            alertHelper(e.getMessage());
        } catch (NumberFormatException e) {
            alertHelper("Input must be number");
        }
    }


}
