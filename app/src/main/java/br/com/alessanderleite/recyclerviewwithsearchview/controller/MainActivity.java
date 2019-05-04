package br.com.alessanderleite.recyclerviewwithsearchview.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import java.util.List;

import br.com.alessanderleite.recyclerviewwithsearchview.R;
import br.com.alessanderleite.recyclerviewwithsearchview.adapter.ItemAdapter;
import br.com.alessanderleite.recyclerviewwithsearchview.api.Client;
import br.com.alessanderleite.recyclerviewwithsearchview.api.Service;
import br.com.alessanderleite.recyclerviewwithsearchview.model.Item;
import br.com.alessanderleite.recyclerviewwithsearchview.model.ItemResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        Service apiService = Client.getRetrofitInstance().create(Service.class);
        Call<ItemResponse> call = apiService.getItems();
        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                itemList = response.body().getItemList();
                getRecyclerView();
            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                Log.e("Error", t.getMessage());
                Toast.makeText(MainActivity.this, "Something went wrong... Please, try again later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ItemAdapter(itemList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }
}
