package com.erickrim.contacts.presentation.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.erickrim.contacts.ContactsApplication;
import com.erickrim.contacts.R;
import com.erickrim.contacts.di.ApplicationComponent;

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        injectDependencies();
        setupToolbar();
    }

    public void setupToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null && getSupportActionBar() != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.drawable.ic_contacts);
        }
    }

    public abstract void setUpComponent(ApplicationComponent appComponent);

    public abstract int getLayout();

    protected void showFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.content_holder, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    private void injectDependencies() {
        setUpComponent(ContactsApplication.getApp(this).getComponent());
    }
}
