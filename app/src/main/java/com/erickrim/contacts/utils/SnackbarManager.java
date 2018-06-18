package com.erickrim.contacts.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.erickrim.contacts.R;

public class SnackbarManager {

    private static final String ERROR = "FAILURE";
    private static final String INFO = "INFO";
    private static final String PROGRESS = "PROGRESS";
    private static final String INFO_NEW_STYLE = "INFO_NEW_STYLE";

    private final static int DURATION = 6000;

    private static SnackbarHolder snackbar;

    private static class SnackbarHolder {
        final Snackbar snackbar;
        final View blockedView;
        final String type;
        final String message;
        boolean isDismissing;

        SnackbarHolder(Snackbar snackbar, View blockedView, String type, String message) {
            this.snackbar = snackbar;
            this.blockedView = blockedView;
            this.type = type;
            this.message = message;
        }

        void setDismissing(boolean dismissing) {
            isDismissing = dismissing;
        }
    }

    private SnackbarManager() {
    }

    public static void showErrorMessage(String title, String message, int iconResID, View view) {
        show(title, ERROR, message, iconResID, view.getContext(), view);
    }

    public static void showErrorMessage(String title, String message, View view) {
        show(title, ERROR, message, -1, view.getContext(), view);
    }

    public static void showLoadingMessage(String title, String message, View view) {
        show(title, PROGRESS, message, -1, view);
    }

    public static void showInformationMessage(String title, String message, View view) {
        show(title, INFO, message, R.string.font_exclamation, view);
    }

    public static void showNewInformationMessage(String message, View view) {
        show(null, INFO_NEW_STYLE, message, -1, view);
    }

    private static void show(String title, String type, String message, int iconResID, View view) {
        show(title, type, message, iconResID, view.getContext(), view);
    }

    private static void show(String title, String type, String message, int iconResID, Context context, View blockingView) {
        if (isShowing(message)) {
            return;
        }

        if (ERROR.equals(type)) {}

        final Snackbar snackbar = Snackbar.make(blockingView, message, Snackbar.LENGTH_LONG);
        final View snackbarView = snackbar.getView();
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);

        final TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);

        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20.0f);
        textView.setTextColor(0xffffffff);
        textView.setMaxLines(32);

        if (iconResID > 0) {
            final TextView actionView = snackbarView.findViewById(R.id.snackbar_action);

            DisplayMetrics displayMetrics = textView.getContext().getResources().getDisplayMetrics();
            int actionWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, displayMetrics);

            LinearLayout.LayoutParams actionLayoutParams = new LinearLayout.LayoutParams(actionWidth, actionWidth);
            actionView.setLayoutParams(actionLayoutParams);
            actionView.requestLayout();

            int textWidth = snackbarView.getWidth() - actionWidth;

            LinearLayout.LayoutParams textLayoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
            textLayoutParams.width = textWidth;
            textLayoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            textView.requestLayout();

            actionView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40.0f);
            actionView.setTextColor(0xffffffff);
            actionView.setVisibility(View.VISIBLE);

            actionView.setText(iconResID);
        }

        switch (type) {
            case ERROR:
                snackbarView.setBackgroundColor(
                        ContextCompat.getColor(context, R.color.app_error_color));
                break;
            case INFO:
                snackbarView.setBackgroundColor(
                        ContextCompat.getColor(context, R.color.snackbar_info_color));
                break;
            case PROGRESS:
                snackbarView.setBackgroundColor(
                        ContextCompat.getColor(context, R.color.snackbar_progress_color));
                break;
            case INFO_NEW_STYLE:
                snackbarView.setBackgroundColor(
                        ContextCompat.getColor(context, R.color.snackbar_info_new_color));
                break;
        }

        Spanned sp = Html.fromHtml(message);
        textView.setText(sp);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        show(new SnackbarHolder(snackbar, blockingView, type, message));
    }

    private static void show(final SnackbarHolder holder) {
        snackbar = holder;

        holder.snackbar.show();
        holder.snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                holder.blockedView.setVisibility(View.GONE);
                holder.snackbar.removeCallback(this);
            }
        });

        holder.snackbar.getView().post(() -> {
            boolean isBlocking = PROGRESS.equals(holder.type);
            holder.blockedView.setVisibility(View.VISIBLE);
            if (holder.blockedView.getBackground() != null) {
                holder.blockedView.getBackground().setAlpha(isBlocking ? 255 : 0);
            }
            holder.blockedView.setClickable(isBlocking);
            holder.blockedView.setFocusable(isBlocking);

            ViewGroup.LayoutParams lp = holder.snackbar.getView().getLayoutParams();
            if (lp instanceof CoordinatorLayout.LayoutParams) {
                ((CoordinatorLayout.LayoutParams) lp).setBehavior(new DisableSwipeBehavior());
                holder.snackbar.getView().setLayoutParams(lp);
            }
        });

        if (!PROGRESS.equals(holder.type)) {
            holder.snackbar.getView().postDelayed(() -> dismiss(holder), DURATION);
        }

        //announceForAccessibility(holder.message);
    }

    private static void dismiss(final SnackbarHolder holder) {
        if (holder.isDismissing) {
            return;
        }
        holder.setDismissing(true);
        holder.snackbar.dismiss();
        snackbar = null;
    }

    public static void dismissLoadingMessage() {
        final SnackbarHolder currentSnackbar = snackbar;
        if (currentSnackbar != null
                && currentSnackbar.snackbar.isShown()
                && PROGRESS.equals(currentSnackbar.type)) {
            dismiss(currentSnackbar);
        }
    }

    private static boolean isShowing(String message) {
        SnackbarHolder holder = snackbar;
        if (holder != null) {
            TextView textView = holder.snackbar.getView()
                    .findViewById(android.support.design.R.id.snackbar_text);
            return TextUtils.equals(message, textView.getText());
        }

        return false;
    }

    private static void announceForAccessibility(String message) {
    }

    public static void dismissMessage() {
        final SnackbarHolder currentSnackbar = snackbar;
        if (currentSnackbar != null
                && currentSnackbar.snackbar.isShown()) {
            dismiss(currentSnackbar);
        }
    }

    private static class DisableSwipeBehavior extends SwipeDismissBehavior<Snackbar.SnackbarLayout> {
        @Override
        public boolean canSwipeDismissView(@NonNull View view) {
            return false;
        }
    }
}
