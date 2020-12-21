package com.maybank.reynard_mangatta_takehome.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.maybank.reynard_mangatta_takehome.Model.Items;
import com.maybank.reynard_mangatta_takehome.R;
import com.maybank.reynard_mangatta_takehome.Utils.ConstantValue;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    EditText etSearch;
    RecyclerView rvItems;
    CountDownTimer cTimer;
    SearchViewModel viewModel;
    SearchAdapter adapter;
    ShimmerFrameLayout shimmerLoading;
    ProgressDialog progressDialog;
    int page;
    List<Items> itemUsers = new ArrayList<>();
    boolean loading;
    int pastVisiblesItems;
    int visibleItemCount;
    int totalItemCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initRecyclerView();
        viewModel = new SearchViewModel();
        onTextChange();
        observerItem();
    }

    private void initView() {
        etSearch = findViewById(R.id.etSearch);
        rvItems = findViewById(R.id.rvItems);
        shimmerLoading = findViewById(R.id.shimmerLoading);
    }

    private void initRecyclerView() {
        getAdapter().setContext(getApplicationContext());
        if (rvItems != null) {
            LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
            rvItems.setLayoutManager(manager);
            rvItems.setItemAnimator(new DefaultItemAnimator());
            rvItems.setAdapter(getAdapter());

            rvItems.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    if (dy > 0) {
                        visibleItemCount = manager.getChildCount();
                        totalItemCount = manager.getItemCount();
                        pastVisiblesItems = manager.findFirstVisibleItemPosition();

                        if (!isLoading() && totalItemCount <= (pastVisiblesItems + visibleItemCount)) {
                            progressDialog = ProgressDialog.show(SearchActivity.this, "", "Please Wait", true);
                            page += 1;
                            if (etSearch.getText().toString().isEmpty()) {
                                viewModel.getUsers(ConstantValue.DEFAULT_QUERY, String.valueOf(page));
                            } else {
                                viewModel.getUsers(etSearch.getText().toString(), String.valueOf(page));
                            }
                            setLoading(true);
                        }
                    }
                }
            });
        }
    }

    @SuppressLint("ShowToast")
    private void observerItem() {
        viewModel.items.observe(this, items -> {
            shimmerLoading.setVisibility(View.GONE);
            rvItems.setVisibility(View.VISIBLE);
            if (items != null) {
                itemUsers.addAll(items);
                getAdapter().setItems(itemUsers);
                getAdapter().notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(), ConstantValue.ERROR_MESSAGE_DEFAULT, Toast.LENGTH_SHORT);
            }
            dismissDialog();
        });

        viewModel.status.observe(this, message -> {
            dismissDialog();
            progressDialog = new ProgressDialog(SearchActivity.this);
            progressDialog.setTitle("Warning");
            progressDialog.setMessage(message);
            progressDialog.setButton(ProgressDialog.BUTTON_NEGATIVE,"Close",(dialog, which) -> progressDialog.dismiss());
            progressDialog.show();
        });
    }

    private void onTextChange() {
        if (etSearch.getText().toString().isEmpty()) {
            startTimer(ConstantValue.DEFAULT_QUERY, page);
        }

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                page = 1;
                if (!s.toString().isEmpty()) {
                    startTimer(String.valueOf(s), page);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    private void startTimer(String query, int page) {
        cancelTimer();
        cTimer = new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                hideKeyboard(SearchActivity.this);
                itemUsers.clear();
                rvItems.setVisibility(View.GONE);
                shimmerLoading.setVisibility(View.VISIBLE);
                viewModel.getUsers(query, String.valueOf(page));
            }
        };
        cTimer.start();
    }

    void cancelTimer() {
        if (cTimer != null)
            cTimer.cancel();
    }

    public SearchAdapter getAdapter() {
        if (adapter == null) {
            adapter = new SearchAdapter();
        }
        return adapter;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public boolean isLoading() {
        return loading;
    }

    public void dismissDialog() {
        if (isLoading()) {
            progressDialog.dismiss();
        }
        setLoading(false);
    }
}