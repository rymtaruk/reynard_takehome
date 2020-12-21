package com.maybank.reynard_mangatta_takehome.Provider;

import com.maybank.reynard_mangatta_takehome.Provider.Interface.ApiInterface;
import com.maybank.reynard_mangatta_takehome.Provider.Manager.RetrofitProvider;

/**
 * Created By reynard on 21/12/20.
 */
public class ApiProvider {
    public static ApiInterface getApi(){
        return RetrofitProvider.getRetrofit().create(ApiInterface.class);
    }
}
