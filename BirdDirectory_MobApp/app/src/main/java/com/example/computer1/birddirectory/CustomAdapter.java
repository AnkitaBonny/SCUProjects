package com.example.computer1.birddirectory;


import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Animal> {

    private final List<Animal> animal;
    Context context;
    FragmentTransaction ft;

    public CustomAdapter(Context context, int resource, List<Animal> animal) {
        super(context, resource, animal);
        this.animal = animal;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        context = parent.getContext();
        Animal zoo = animal.get(position);
        final String itemName = zoo.getName();

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.custom_row, null);

        row.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                if (itemName.equals("Vulture")) {
                    ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                    Fragment prev = ((FragmentActivity)context).getSupportFragmentManager().findFragmentByTag("NoticeDialogFragment");
                    if (prev != null) {
                        ft.remove(prev);

                    }

                    ft.addToBackStack(null);

                    MyDialog fragment = new MyDialog();
                    fragment.show(ft, "NoticeDialogFragment");
                }
                else {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("nameKey", itemName);
                    context.startActivity(intent);
                }

            }
        });

        // Set the text
        TextView textView = (TextView) row.findViewById(R.id.rowText);
        textView.setText(zoo.getName());

        // Set the image
        try {
            ImageView imageView = (ImageView) row.findViewById(R.id.rowImage);
            InputStream inputStream = getContext().getAssets().open(zoo.getFilename());
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            imageView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return row;

    }

}
