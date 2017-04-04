package rostradamus.simplematrixcalculator.ui;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


import java.util.*;

import rostradamus.simplematrixcalculator.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class VectorInputFragment extends Fragment {
    Button confirm;
    Button clear;
    EditText editText;
    ConstraintLayout firstVectorLayout;
    ConstraintLayout secondVectorLayout;
    List<EditText> firstVectorArr = new ArrayList<>();
    List<EditText> secondVectorArr = new ArrayList<>();


    public VectorInputFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.vector_input_fragment_layout, container, false);
        confirm = (Button) v.findViewById(R.id.confirmButton);
        clear = (Button) v.findViewById(R.id.clearInput);
        editText = (EditText) v.findViewById(R.id.componentEditText);
        firstVectorLayout = (ConstraintLayout) v.findViewById(R.id.firstVectorInput);
        secondVectorLayout = (ConstraintLayout) v.findViewById(R.id.secondVectorInput);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int rowNumber = Integer.parseInt(editText.getText().toString());
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
        // Inflate the layout for this fragment
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
        for (int i = 0; i < numInput; i++) {
            firstVectorLayout.addView(makeEditText(10 + i, firstVectorArr));
            secondVectorLayout.addView(makeEditText(20 + i, secondVectorArr));
        }
    }
    private void flushInput() {
        firstVectorArr.clear();
        secondVectorArr.clear();
        firstVectorLayout.removeAllViews();
        secondVectorLayout.removeAllViews();
    }
    private EditText makeEditText(int _intID, List<EditText> vectorArr) {
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        EditText prevText;
        int prevY;
        if (_intID > 11 && _intID < 16) {
            prevText = firstVectorArr.get(_intID - 10);
            prevY = (int) prevText.getY();
            Log.i("prevY", Integer.toString(prevY));
            layoutParams.setMargins(0, prevY + 20, 0, 0);
        } else if (_intID > 21 && _intID < 26) {
            prevText = firstVectorArr.get(_intID - 20);
            prevY = (int) prevText.getY();
            Log.i("prevY", Integer.toString(prevY));
            layoutParams.setMargins(0, prevY + 20, 0, 0);
        }
        EditText editText = new EditText(getActivity());
        editText.setId(_intID);
        editText.setHint("My lines");
        editText.setLayoutParams(layoutParams);
        vectorArr.add(editText);
        return editText;
    }


}
