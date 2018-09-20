package app.Model;


import java.util.ArrayList;
import java.util.List;

public class Dish {

    private long i_dish;
    private long i_kitchen;
    private String name;
    private String unit;
    private String description;
    private int qty;
    private float price;
    private List<Product> products;

    public Dish() {
    }

    public Dish(long i_dish, long i_kitchen, String name, String unit, String description, int qty, float price) {
        this.i_dish = i_dish;
        this.i_kitchen = i_kitchen;
        this.name = name;
        this.unit = unit;
        this.description = description;
        this.qty = qty;
        this.price = price;
        this.products = new ArrayList<>();
    }

    public long getI_dish() {
        return i_dish;
    }

    public void setI_dish(long i_dish) {
        this.i_dish = i_dish;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return this.name;
    }
}