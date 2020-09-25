package com.example.calculatorslist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.calculatormain.CalculatorActivity;
import com.example.calculatormain.R;
import com.example.models.Calculator;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CreateCalculatorBottomSheet extends BottomSheetDialogFragment {

    Button continueButton;
    Button cancelButton;
    EditText nameSetter;
    View view;
    CalculatorsListActivity calculatorsListActivity;

    public CreateCalculatorBottomSheet(CalculatorsListActivity calculatorsListActivity) {
        this.calculatorsListActivity = calculatorsListActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_bottom_sheet, container, false);
        nameSetter = view.findViewById(R.id.edit_text_bottom_sheet);
        nameSetter.requestFocus();

        continueButton = view.findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calculator calculator = new Calculator(nameSetter.getText().toString(), "","");
                Intent intent = new Intent(view.getContext(), CalculatorActivity.class);
                intent.putExtra("new_calculator", calculator);
                view.getContext().startActivity(intent);
                calculatorsListActivity.overridePendingTransition(R.anim.animate_swipe_left_enter, R.anim.animate_swipe_left_exit);
                calculatorsListActivity.addCalculator(calculator);
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme);
        super.onCreate(savedInstanceState);
    }
}
