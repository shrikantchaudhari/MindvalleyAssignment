package com.mindvalley.app.ui.pinlistscreen;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mindvalley.app.R;
import com.mindvalley.app.data.model.api.Pinboard;
import com.mindvalley.app.ui.pinlistscreen.adapter.PinboardAdapter;
import com.mindvalley.app.utils.GridSpacingItemDecoration;
import com.mindvalley.asyncloader.AsyncLoader;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mindvalley.app.utils.GridSpacingItemDecoration.dpToPx;

public class PinListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_pinboard)
    RecyclerView rvPinboard;

    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;

    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    @BindView(R.id.tv_empty_view)
    TextView emptyPin;

    @BindView(R.id.fab_clear_cache)
    FloatingActionButton fabClearCache;

    private PinboardAdapter pinboardAdapter;

    private List<Pinboard> pinboardList;

    private PinboardViewModel pinboardViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initComponents();

        setListeners();

        observePinsViewModel();

        pinboardViewModel.fetchPins();
    }


    /**
     * Initializing UI components
     */
    private void initComponents() {

        pinboardViewModel = ViewModelProviders.of(this).get(PinboardViewModel.class);

        //Initializing recycler view
        pinboardList = new ArrayList<>();
        pinboardAdapter = new PinboardAdapter(PinListActivity.this, pinboardList);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        rvPinboard.setLayoutManager(mLayoutManager);
        rvPinboard.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(this, 10), true));
        rvPinboard.setItemAnimator(new DefaultItemAnimator());
        rvPinboard.setAdapter(pinboardAdapter);
    }

    /**
     * Setting listeners to UI component
     */
    private void setListeners() {
        srlRefresh.setOnRefreshListener(this);
        srlRefresh.setEnabled(false);

        //Adding scroll listener to pindboard list
        rvPinboard.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Hide and show Filter button
                if (dy > 0 && fabClearCache.getVisibility() == View.VISIBLE) {
                    fabClearCache.hide();
                } else if (dy < 0 && fabClearCache.getVisibility() != View.VISIBLE) {
                    fabClearCache.show();
                }
            }
        });

    }

    private void observePinsViewModel() {

        pinboardViewModel.getAllPins().observe(this, pinboards -> {

            if (srlRefresh.isRefreshing())
                srlRefresh.setRefreshing(false);
            srlRefresh.setEnabled(true);
            hideProgress();

            pinboardList.clear();

            pinboardList.addAll(pinboards);
            pinboardAdapter.notifyDataSetChanged();
        });
    }

    @OnClick(R.id.fab_clear_cache)
    public void onCacheClear(View view) {

        Snackbar.make(view, R.string.cache_refreshed, Snackbar.LENGTH_LONG).show();

        AsyncLoader.get().clearCache();

    }

    public void showProgress() {

        pbLoading.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {

        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {

        pinboardViewModel.fetchPins();
    }
}
