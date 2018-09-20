package app.Controller;

import app.Model.Dish;
import app.Model.Kitchen;
import app.Model.Product;
import app.Model.Restaurant;

import java.util.List;

public class Operations {

    private Restaurant restaurant;
    private List<Kitchen> kitchens;
    private List<Dish> dishes;
    private List<Product> products;

    public Operations(Restaurant restaurant, List<Kitchen> kitchens, List<Dish> dishes, List<Product> products) {
        this.restaurant = restaurant;
        this.kitchens = kitchens;
        this.dishes = dishes;
        this.products = products;
    }

    void mainInfoAboutKitchens() {
        for (Kitchen k : kitchens) {
            StringBuilder listDishes = new StringBuilder();
            for (Dish d : dishes) {
                if (d.getI_kitchen() == k.getI_kitchen())
                    listDishes.append(d.getName()).append(", ");
            }
            int length = listDishes.length();
            listDishes.replace(length - 2, length - 1, ".");
            // TODO: adding info into table
            System.out.println(k.getName() + " " + k.getChef() + " " + listDishes);
        }
    }

    void getTableOfAllDishes() {
        for (Dish d : dishes) {
            StringBuilder listProducts = new StringBuilder();
            for (Product p : products) {
                if (p.getI_dish() == d.getI_dish())
                    listProducts.append(d.getName()).append(", ");
            }
            int length = listProducts.length();
            listProducts.replace(length - 2, length - 1, ".");
            // TODO: adding info into table
            System.out.println(d.getName() + " " + d.getQty() + d.getUnit() + " " + d.getPrice() + " " + listProducts + " " + d.getDescription());
        }
    }

    void getTableOfAllProducts() {

    }
}
