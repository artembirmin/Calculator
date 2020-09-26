package com.example.calculatorslist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.calculatormain.R;
import com.example.models.Calculator;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CreateCalculatorBottomSheet extends BottomSheetDialogFragment {

    Button continueButton;
    Button cancelButton;
    EditText nameField;
    View view;
    OnBottomSheetContinueClick onBottomSheetContinueClick;

    public CreateCalculatorBottomSheet(OnBottomSheetContinueClick onBottomSheetContinueClick) {
       this.onBottomSheetContinueClick = onBottomSheetContinueClick;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_bottom_sheet, container, false);
        nameField = view.findViewById(R.id.edit_text_bottom_sheet);
        nameField.requestFocus();
        continueButton = view.findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBottomSheetContinueClick.onBottomSheetContinueClick(new Calculator(nameField.getText().toString()));
                nameField.setText("");
            }
        });
        return view;
    }

    interface OnBottomSheetContinueClick{
        void onBottomSheetContinueClick(Calculator calculator);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme);
        super.onCreate(savedInstanceState);
    }
}
