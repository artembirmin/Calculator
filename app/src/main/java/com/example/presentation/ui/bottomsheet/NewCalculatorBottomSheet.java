package com.example.presentation.ui.bottomsheet;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.calculatormain.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class NewCalculatorBottomSheet extends BottomSheetDialogFragment {

    private static final String TAG = "CalculatorSheet";
    Button continueButton;
    Button cancelButton;
    EditText nameEditText;
    View view;
    BottomSheetBehavior<View> bottomSheetBehavior;
    OnBottomSheetContinueClick onBottomSheetContinueClick;

    public NewCalculatorBottomSheet(OnBottomSheetContinueClick onBottomSheetContinueClick) {
        this.onBottomSheetContinueClick = onBottomSheetContinueClick;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_bottom_sheet, container, false);
        Log.d(TAG, "onCreateView: ");
        nameEditText = view.findViewById(R.id.edit_text_bottom_sheet);
        nameEditText.requestFocus();
        nameEditText.setText("");
      //  bottomSheetBehavior = BottomSheetBehavior.from(view);
        continueButton = view.findViewById(R.id.continue_button);
        cancelButton = view.findViewById(R.id.cancel1_button);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBottomSheetContinueClick.onBottomSheetContinueClick(nameEditText.getText().toString());
                nameEditText.setText("");
                dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameEditText.setText("");
                dismiss();
            }
        });
    }

    public interface OnBottomSheetContinueClick{
        void onBottomSheetContinueClick(String name);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme);
        super.onCreate(savedInstanceState);
    }
}
