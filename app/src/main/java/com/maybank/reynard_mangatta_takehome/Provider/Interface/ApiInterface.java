package com.maybank.reynard_mangatta_takehome.Provider.Interface;

import com.maybank.reynard_mangatta_takehome.Model.BaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created By reynard on 21/12/20.
 */
public interface ApiInterface {
    @GET("/search/users")
    Call<BaseResponse> getListUsers(@Query(value = "q") String p, @Query(value = "page") String page);
}
