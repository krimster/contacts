package com.erickrim.contacts.utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.erickrim.contacts.R;

/**
 * @author <a href="mailto:erickrim@yahoo.co.uk">Eric Krim</a>
 * @since 0.1.0
 */

public class MarginDecoration extends RecyclerView.ItemDecoration {
    private int margin;

    public static MarginDecoration newInstanceSmall(Context context) {
        return new MarginDecoration(context, R.dimen.item_margin_half);
    }

    public static MarginDecoration newInstanceMedium(Context context) {
        return new MarginDecoration(context, R.dimen.item_margin);
    }

    public static MarginDecoration newInstanceLarge(Context context) {
        return new MarginDecoration(context, R.dimen.item_margin_large);
    }

    public MarginDecoration(Context context) {
        margin = context.getResources().getDimensionPixelSize(R.dimen.item_margin);
    }

    private MarginDecoration(Context context, int marginResource) {
        margin = context.getResources().getDimensionPixelSize(marginResource);
    }

    @Override
    public void getItemOffsets(
            Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(margin, margin, margin, margin);
    }
}