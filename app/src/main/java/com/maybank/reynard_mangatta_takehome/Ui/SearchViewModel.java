package com.maybank.reynard_mangatta_takehome.Ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.maybank.reynard_mangatta_takehome.Model.BaseResponse;
import com.maybank.reynard_mangatta_takehome.Model.Items;
import com.maybank.reynard_mangatta_takehome.Provider.ApiProvider;
import com.maybank.reynard_mangatta_takehome.Provider.Interface.ApiInterface;
import com.maybank.reynard_mangatta_takehome.Utils.ConstantValue;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created By reynard on 21/12/20.
 */
public class SearchViewModel extends ViewModel {
    ApiInterface apiInterface;
    MutableLiveData<List<Items>> items = new MutableLiveData<>();
    MutableLiveData<String> status = new MutableLiveData<>();

    public void getUsers(String query, String page) {
        apiInterface = ApiProvider.getApi();

        apiInterface.getListUsers(query, page).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getItems() != null) {
                        items.setValue(response.body().getItems());
                    } else {
                        status.setValue(response.body().getMessage());
                    }
                } else {
                    if (response.code() == 403) {
                        status.setValue("You Got " + response.message() + "\nPlease try a few minute");
                    } else {
                        status.setValue(ConstantValue.ERROR_MESSAGE_DEFAULT);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                status.setValue(ConstantValue.ERROR_MESSAGE_DEFAULT);
            }
        });
    }
}
