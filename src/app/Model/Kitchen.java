package app.Model;

import java.util.ArrayList;
import java.util.List;

public class Kitchen {

    private long i_kitchen;
    private long i_restaurant;
    private String name;
    private String chef;
    private List<Dish> dishes;

    public Kitchen() {
    }

    public Kitchen(long i_kitchen, long i_restaurant, String name, String chef) {
        this.i_restaurant = i_restaurant;
        this.i_kitchen = i_kitchen;
        this.name = name;
        this.chef = chef;
        this.dishes = new ArrayList<>();
    }

    public long getI_restaurant() {
        return i_restaurant;
    }

    public void setI_restaurant(long i_restaurant) {
        this.i_restaurant = i_restaurant;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public long getI_kitchen() {
        return i_kitchen;
    }

    public void setI_kitchen(long i_kitchen) {
        this.i_kitchen = i_kitchen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChef() {
        return chef;
    }

    public void setChef(String chef) {
        this.chef = chef;
    }

    @Override
    public String toString() {
        return this.name;
    }
}