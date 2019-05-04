package br.com.alessanderleite.recyclerviewwithsearchview.api;

import br.com.alessanderleite.recyclerviewwithsearchview.model.ItemResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {

    @GET("search/users?q=language:java+location:brazil")
    Call<ItemResponse> getItems();
}
