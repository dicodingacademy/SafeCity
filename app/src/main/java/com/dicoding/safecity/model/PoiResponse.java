
package com.dicoding.safecity.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PoiResponse {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("latitude")
    @Expose
    private float latitude;
    @SerializedName("longitude")
    @Expose
    private float longitude;
    @SerializedName("pointInterestTypeId")
    @Expose
    private int pointInterestTypeId;
    @SerializedName("pointInterestTypeName")
    @Expose
    private String pointInterestTypeName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getPointInterestTypeId() {
        return pointInterestTypeId;
    }

    public void setPointInterestTypeId(int pointInterestTypeId) {
        this.pointInterestTypeId = pointInterestTypeId;
    }

    public String getPointInterestTypeName() {
        return pointInterestTypeName;
    }

    public void setPointInterestTypeName(String pointInterestTypeName) {
        this.pointInterestTypeName = pointInterestTypeName;
    }
}
