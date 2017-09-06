package com.dicoding.safecity.networking;

import com.dicoding.safecity.model.PoiResponse;
import com.dicoding.safecity.model.TicketResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("v1/ticket/find/{radius}")
    Call<List<TicketResponse>> getTicketList(@Path("radius") int radius);

    @GET("v1/pointinterest/all/{radius}")
    Call<List<PoiResponse>> getPoiList(@Path("radius") int radius);

}
