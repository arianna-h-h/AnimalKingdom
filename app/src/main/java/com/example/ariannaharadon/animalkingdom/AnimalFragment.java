package com.example.ariannaharadon.animalkingdom;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.Locale;


public class AnimalFragment extends Fragment {
    public static final String ARG_DOG_NUMBER = "dog_number";

    public AnimalFragment() {
        // Empty constructor required for fragment subclasses
    }


    //Creates and inflates animal fragment to be displayed
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_animal, container, false);
        int i = getArguments().getInt(ARG_DOG_NUMBER);
        String dog = getResources().getStringArray(R.array.dogs_array)[i];

        int imageId = getResources().getIdentifier(dog.toLowerCase(Locale.getDefault()),
                "drawable", getActivity().getPackageName());
        ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
        getActivity().setTitle(dog);
        return rootView;
    }
}