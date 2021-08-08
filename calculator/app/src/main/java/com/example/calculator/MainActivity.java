package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView resultShowTextView;
    CheckBox floatValueCheckbox;
    CheckBox signedValueCheckbox;
    EditText firstNumberInputFieldEditText;
    EditText secondNumberInputFieldEditText;
    RadioButton  additionRadioButton;
    RadioButton  subtractionRadioButton;
    RadioButton  divisionRadioButton;
    RadioButton  multiplicationRadioButton;
    List<RadioButton> radioButtonGroup;
    String operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultShowTextView = (TextView) findViewById(R.id.resultOutputField);
        additionRadioButton = (RadioButton) findViewById(R.id.additionRadioButton);
        subtractionRadioButton = (RadioButton) findViewById(R.id.subtractionRadioButton);
        divisionRadioButton = (RadioButton) findViewById(R.id.divisionRadioButton);
        multiplicationRadioButton = (RadioButton) findViewById(R.id.multiplicationRadioButton);
        radioButtonGroup = Arrays.asList(additionRadioButton, subtractionRadioButton, divisionRadioButton, multiplicationRadioButton);
        floatValueCheckbox = (CheckBox)findViewById(R.id.floatValueCheckbox);
        signedValueCheckbox = (CheckBox)findViewById(R.id.signedValueCheckbox);
        firstNumberInputFieldEditText = (EditText) findViewById(R.id.firstNumberInputField);
        firstNumberInputFieldEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newInputText = s.toString();
                newInputText = newInputText.replaceAll( "^00", "0")
                        .replaceAll( "^-00", "-0")
                        .replaceAll( "^\\.", "0.")
                        .replaceAll( "^-\\.", "-0.")
                        .replaceAll("^01", "1")
                        .replaceAll("^02", "2")
                        .replaceAll("^03", "3")
                        .replaceAll("^04", "4")
                        .replaceAll("^05", "5")
                        .replaceAll("^06", "6")
                        .replaceAll("^07", "7")
                        .replaceAll("^08", "8")
                        .replaceAll("^09", "9")
                        .replaceAll("^-01", "-1")
                        .replaceAll("^-02", "-2")
                        .replaceAll("^-03", "-3")
                        .replaceAll("^-04", "-4")
                        .replaceAll("^-05", "-5")
                        .replaceAll("^-06", "-6")
                        .replaceAll("^-07", "-7")
                        .replaceAll("^-08", "-8")
                        .replaceAll("^-09", "-9");
                if(!s.toString().equals(newInputText)) {
                    firstNumberInputFieldEditText.setText(newInputText);
                    firstNumberInputFieldEditText.setSelection(firstNumberInputFieldEditText.getText().length());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        secondNumberInputFieldEditText = (EditText) findViewById(R.id.secondNumberInputField);
        secondNumberInputFieldEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newInputText = s.toString();
                newInputText = newInputText.replaceAll( "^00", "0")
                        .replaceAll( "^-00", "-0")
                        .replaceAll( "^\\.", "0.")
                        .replaceAll( "^-\\.", "-0.")
                        .replaceAll("^01", "1")
                        .replaceAll("^02", "2")
                        .replaceAll("^03", "3")
                        .replaceAll("^04", "4")
                        .replaceAll("^05", "5")
                        .replaceAll("^06", "6")
                        .replaceAll("^07", "7")
                        .replaceAll("^08", "8")
                        .replaceAll("^09", "9")
                        .replaceAll("^-01", "-1")
                        .replaceAll("^-02", "-2")
                        .replaceAll("^-03", "-3")
                        .replaceAll("^-04", "-4")
                        .replaceAll("^-05", "-5")
                        .replaceAll("^-06", "-6")
                        .replaceAll("^-07", "-7")
                        .replaceAll("^-08", "-8")
                        .replaceAll("^-09", "-9");
                if(!s.toString().equals(newInputText)) {
                    secondNumberInputFieldEditText.setText(newInputText);
                    secondNumberInputFieldEditText.setSelection(secondNumberInputFieldEditText.getText().length());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("FLOAT_VALUE_FLAG", floatValueCheckbox.isChecked());
        outState.putBoolean("SIGNED_VALUE_FLAG", signedValueCheckbox.isChecked());
        if(operation != null)
            outState.putString("OPERATION", operation);
        outState.putInt("FIRST_NUMBER_INPUT_TYPE", firstNumberInputFieldEditText.getInputType());
        outState.putInt("SECOND_NUMBER_INPUT_TYPE", secondNumberInputFieldEditText.getInputType());
        if(firstNumberInputFieldEditText.getText().toString() != null || !firstNumberInputFieldEditText.getText().toString().equals(""))
            outState.putString("FIRST_NUMBER", firstNumberInputFieldEditText.getText().toString());
        if(secondNumberInputFieldEditText.getText().toString() != null || !secondNumberInputFieldEditText.getText().toString().equals(""))
            outState.putString("SECOND_NUMBER", secondNumberInputFieldEditText.getText().toString());
        if(resultShowTextView.getText().toString() != null || !resultShowTextView.getText().toString().equals(""))
            outState.putString("RESULT", resultShowTextView.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        floatValueCheckbox.setChecked(savedInstanceState.getBoolean("FLOAT_VALUE_FLAG"));
        signedValueCheckbox.setChecked(savedInstanceState.getBoolean("SIGNED_VALUE_FLAG"));
        operation = savedInstanceState.getString("OPERATION");
        firstNumberInputFieldEditText.setText(savedInstanceState.getString("FIRST_NUMBER"));
        secondNumberInputFieldEditText.setText(savedInstanceState.getString("SECOND_NUMBER"));
        firstNumberInputFieldEditText.setInputType(savedInstanceState.getInt("FIRST_NUMBER_INPUT_TYPE"));
        secondNumberInputFieldEditText.setInputType(savedInstanceState.getInt("SECOND_NUMBER_INPUT_TYPE"));
        resultShowTextView.setText(savedInstanceState.getString("RESULT"));
    }

    public void onCheckboxClicked(View view) {
        CheckBox checkBox = (CheckBox) view;
        boolean checked = checkBox.isChecked();
        switch(view.getId()) {
            case R.id.floatValueCheckbox:
                if (checked) {
                    addInputTypeForEditTextByCheckBox(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    break;
                }
                converNumberToInteger();
                removeInputTypeForEditTextByCheckBox(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
            case R.id.signedValueCheckbox:
                if (checked) {
                    addInputTypeForEditTextByCheckBox(InputType.TYPE_NUMBER_FLAG_SIGNED);
                    break;
                }
                converNumberToPositive();
                removeInputTypeForEditTextByCheckBox(InputType.TYPE_NUMBER_FLAG_SIGNED);
                break;
        }
    }

    public void addInputTypeForEditTextByCheckBox(int inputType) {
        firstNumberInputFieldEditText.setInputType(firstNumberInputFieldEditText.getInputType() + inputType);
        secondNumberInputFieldEditText.setInputType(secondNumberInputFieldEditText.getInputType() + inputType);
    }

    public void removeInputTypeForEditTextByCheckBox(int inputType) {
        firstNumberInputFieldEditText.setInputType(firstNumberInputFieldEditText.getInputType() - inputType);
        secondNumberInputFieldEditText.setInputType(secondNumberInputFieldEditText.getInputType() - inputType);
    }

    public void converNumberToInteger() {
        if(!firstNumberInputFieldEditText.getText().toString().equals(""))
            firstNumberInputFieldEditText.setText(String.valueOf((int)Float.parseFloat(firstNumberInputFieldEditText.getText().toString())));
        if(!secondNumberInputFieldEditText.getText().toString().equals(""))
            secondNumberInputFieldEditText.setText(String.valueOf((int)Float.parseFloat(secondNumberInputFieldEditText.getText().toString())));
    }

    public void converNumberToPositive() {
        firstNumberInputFieldEditText.setText(firstNumberInputFieldEditText.getText().toString().replace("-", ""));
        secondNumberInputFieldEditText.setText(secondNumberInputFieldEditText.getText().toString().replace("-", ""));
    }

    public void onRadioButtonClick(View view) {
        RadioButton radioButton = (RadioButton)view;
        radioButton.setChecked(true);
        radioButtonGroup.stream().filter(button -> button != radioButton).forEach(button -> button.setChecked(false));
        switch(view.getId()) {
            case R.id.additionRadioButton:
                operation = "+";
                break;
            case R.id.subtractionRadioButton:
                operation = "-";
                break;
            case R.id.divisionRadioButton:
                operation = "/";
                break;
            case R.id.multiplicationRadioButton:
                operation = "*";
                break;
        }
    }

    public void onButtonClick(View view) {
        Button button = (Button)view;
        if (operation == null) {
            Toast.makeText(this, R.string.operation_not_found, Toast.LENGTH_LONG).show();
            return;
        }
        String firstNumber = firstNumberInputFieldEditText.getText().toString();
        String secondNumber = secondNumberInputFieldEditText.getText().toString();
        if(firstNumber == null || firstNumber.trim().equals("")){
            if(secondNumber == null || secondNumber.trim().equals("")){
                Toast.makeText(this, R.string.field_1_and_2_not_found, Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if(firstNumber == null || firstNumber.trim().equals("")){
            Toast.makeText(this, R.string.field_1_not_found, Toast.LENGTH_SHORT).show();
            return;
        }
        if(secondNumber == null || secondNumber.trim().equals("")){
            Toast.makeText(this, R.string.field_2_not_found, Toast.LENGTH_SHORT).show();
            return;
        }
        float intFirstNumber = Float.parseFloat(firstNumber);
        float intSecondNumber = Float.parseFloat(secondNumber);
        calculate(intFirstNumber, intSecondNumber);
    }

    public void calculate(float firstNumber, float secondNumber) {
        float result = 0;
        try {
            switch (operation){
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "*":
                    result = firstNumber * secondNumber;
                    break;
                case "/":
                    result = firstNumber / secondNumber;
                    break;
            }
        } catch (Exception e) {
            resultShowTextView.setText(R.string.ooops_something_go_wrong);
        }
        if (Float.isInfinite(result)) {
            resultShowTextView.setText(R.string.cannot_divide_by_zero);
            return;
        }
        resultShowTextView.setText(new DecimalFormat("###.#").format(result));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.resetting :
                showDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(R.string.reset_settings)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                radioButtonGroup.stream().forEach(button -> button.setChecked(false));
                                firstNumberInputFieldEditText.getText().clear();
                                secondNumberInputFieldEditText.getText().clear();
                                resultShowTextView.setText(null);
                                floatValueCheckbox.setChecked(true);
                                signedValueCheckbox.setChecked(true);
                                operation = null;
                            }
                        }
                ).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(Color.parseColor("#FF00FF"));
        Button buttonNegative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        buttonNegative.setBackgroundColor(Color.TRANSPARENT);
        buttonNegative.setTextColor(Color.parseColor("#FF00FF"));
    }
}