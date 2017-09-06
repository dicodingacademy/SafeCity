package com.dicoding.safecity.home;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.dicoding.safecity.model.PoiResponse;
import com.dicoding.safecity.model.TicketResponse;
import com.dicoding.safecity.networking.ApiBuilder;
import com.dicoding.safecity.networking.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 9/6/17.
 */

public class MainPresenter {
    private final MainView view;
    private final Context context;

    public MainPresenter(Context context, MainView view) {
        this.context  = context;
        this.view = view;
    }

    public void getTicketList(int radius) {
        ApiService service = ApiBuilder.call();
        service.getTicketList(radius).enqueue(new Callback<List<TicketResponse>>() {
            @Override
            public void onResponse(Call<List<TicketResponse>> call, Response<List<TicketResponse>> response) {
                view.setVisibilityProgressBar(View.GONE);
                view.getEventTicketSuccess(response);
            }

            @Override
            public void onFailure(Call<List<TicketResponse>> call, Throwable t) {
                view.setVisibilityProgressBar(View.GONE);
            }
        });
    }

    public void getPoiList(int radius) {
        ApiService service = ApiBuilder.call();
        service.getPoiList(radius).enqueue(new Callback<List<PoiResponse>>() {
            @Override
            public void onResponse(Call<List<PoiResponse>> call, Response<List<PoiResponse>> response) {
                view.setVisibilityProgressBar(View.GONE);
                view.getEventPoiSuccess(response);
            }

            @Override
            public void onFailure(Call<List<PoiResponse>> call, Throwable t) {
                view.setVisibilityProgressBar(View.GONE);
            }
        });
    }

}
