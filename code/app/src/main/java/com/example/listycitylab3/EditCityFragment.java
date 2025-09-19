package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.Serializable;

public class EditCityFragment extends DialogFragment {

    interface EditCityDialogListener {
        void updateCityList(City city,String name,String province);
    }

    private EditCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    static EditCityFragment newInstance(City city){
        Bundle args =  new Bundle();
        args.putSerializable("city", city);

        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    //what actually pops up on Dialog
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        Bundle args = getArguments();
        City selectedCity = (City) args.getSerializable("city");
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        editCityName.setText(selectedCity.getName());
        editProvinceName.setText(selectedCity.getProvince());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Edit", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    listener.updateCityList(selectedCity,cityName,provinceName);
                })
                .create();

    }

}
