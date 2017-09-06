package com.dicoding.safecity.home;

import com.dicoding.safecity.model.PoiResponse;
import com.dicoding.safecity.model.TicketResponse;

import java.util.List;

import retrofit2.Response;

/**
 * Created by root on 9/6/17.
 */

public interface MainView {
    void setVisibilityProgressBar(int visibility);

    void getEventTicketSuccess(Response<List<TicketResponse>> response);

    void getEventPoiSuccess(Response<List<PoiResponse>> response);
}
