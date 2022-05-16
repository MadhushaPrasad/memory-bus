package com.project.memorybuzz.models;

public class PlaceClass {
    private String locationName,locationdesc,locationimage;

    public PlaceClass(){

    }
    public PlaceClass(String locationName, String locationdesc, String locationimage) {
        this.locationName = locationName;
        this.locationdesc = locationdesc;
        this.locationimage = locationimage;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationdesc() {
        return locationdesc;
    }

    public void setLocationdesc(String locationdesc) {
        this.locationdesc = locationdesc;
    }

    public String getLocationimage() {
        return locationimage;
    }

    public void setLocationimage(String locationimage) {
        this.locationimage = locationimage;
    }
}
