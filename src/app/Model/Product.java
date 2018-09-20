package app.Model;


public class Product {

    private long i_product;
    private long i_dish;
    private String name;
    private String unit;
    private int qty;

    public Product() {
    }

    public Product(long i_product, long i_dish, String name, String unit, int qty) {
        this.i_dish = i_dish;
        this.i_product = i_product;
        this.name = name;
        this.unit = unit;
        this.qty = qty;
    }

    public long getI_dish() {
        return i_dish;
    }

    public void setI_dish(long i_dish) {
        this.i_dish = i_dish;
    }

    public long getI_product() {
        return i_product;
    }

    public void setI_product(long i_product) {
        this.i_product = i_product;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return this.name;
    }
}