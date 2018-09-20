package app.Model;


import java.util.ArrayList;
import java.util.List;

public class Restaurant {

    private long i_restaurant;
    private String name;
    private String director;
    private String address;
    private String tel;
    private List<Kitchen> kitchens;

    public Restaurant() {
    }

    public Restaurant(long i_restaurant, String name, String director, String address, String tel) {
        this.i_restaurant = i_restaurant;
        this.name = name;
        this.director = director;
        this.address = address;
        this.tel = tel;
        this.kitchens = new ArrayList<>();
    }

    public long getI_restaurant() {
        return i_restaurant;
    }

    public void setI_restaurant(long i_restaurant) {
        this.i_restaurant = i_restaurant;
    }

    public List<Kitchen> getKitchens() {
        return kitchens;
    }

    public void setKitchens(List<Kitchen> kitchens) {
        this.kitchens = kitchens;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return this.name;
    }
}