package com.erickrim.contacts.presentation.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.erickrim.contacts.R;
import com.squareup.picasso.Picasso;

public class ContactListView extends LinearLayout {

    private ImageView mIconView;
    private TextView mTitleView;

    public ContactListView(@NonNull Context context) {
        this(context, null);
    }

    public ContactListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_contact_list, this, true);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mIconView = findViewById(R.id.device_icon);
        mTitleView = findViewById(R.id.device_title);

        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.ContactListView,
                    0, 0);

            int drawableId;
            String title;
            String subtitle;

            try {
                drawableId = a.getResourceId(R.styleable.ContactListView_idv_icon, 0);
                title = a.getString(R.styleable.ContactListView_idv_title);
            } finally {
                a.recycle();
            }

            if (drawableId != 0) {
                Drawable drawable = VectorDrawableCompat.create(getResources(), drawableId, null);
                setIcon(drawable);
            }
            setTitle(title);
        }

        setOrientation(VERTICAL);

        ViewCompat.setBackground(this,
                AppCompatResources.getDrawable(context, R.drawable.first_device_selector));

        setFocusable(true);
        setClickable(true);
    }

    public void setTitle(String title) {
        mTitleView.setText(title);
    }

    public void setIcon(Drawable drawable) {
        mIconView.setImageDrawable(drawable);
    }

    public void setIcon(String url) {
        Picasso.get().load(url).into(mIconView);
    }
}
