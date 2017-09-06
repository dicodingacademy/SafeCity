
package com.dicoding.safecity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketResponse {

    @SerializedName("ticketNumber")
    @Expose
    private int ticketNumber;
    @SerializedName("ticketRegisterDate")
    @Expose
    private float ticketRegisterDate;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("priorityName")
    @Expose
    private String priorityName;
    @SerializedName("latitude")
    @Expose
    private float latitude;
    @SerializedName("longitude")
    @Expose
    private float longitude;

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public float getTicketRegisterDate() {
        return ticketRegisterDate;
    }

    public void setTicketRegisterDate(int ticketRegisterDate) {
        this.ticketRegisterDate = ticketRegisterDate;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

}
