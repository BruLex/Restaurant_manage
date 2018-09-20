package app.Controller;

import app.DataTransferObject.DatabaseHandler;
import app.Model.Dish;
import app.Model.Kitchen;
import app.Model.Product;
import app.Model.Restaurant;

import java.util.List;

public class BaseDataController {
    private static BaseDataController instance = null;
    private static Restaurant restaurant;
    private DatabaseHandler databaseHandler;

    private BaseDataController(long i_restaurant) {
        databaseHandler = DatabaseHandler.getInstance();
        restaurant = recoverRestaurant(i_restaurant);
    }

    private BaseDataController() {
        restaurant = new Restaurant();
        restaurant.setI_restaurant(-2);
    }

    public static synchronized BaseDataController getInstance(long i_restaurant) {
        if (instance == null || instance.restaurant.getI_restaurant() != i_restaurant)
            instance = i_restaurant == -1 ? new BaseDataController() : new BaseDataController(i_restaurant);
        return instance;
    }

    public static List<Restaurant> getRestaurants() {

        return DatabaseHandler.getInstance().getAllRestaurants();
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    private Restaurant recoverRestaurant(long i_restaurant) {
        Restaurant restaurant = databaseHandler.getRestaurantById(i_restaurant);
        restaurant.setKitchens(recoverKitchens(i_restaurant));
        return restaurant;
    }

    private List<Kitchen> recoverKitchens(long i_restaurant) {
        List<Kitchen> kitchens = databaseHandler.getAllKitchensById(i_restaurant);
        for (Kitchen kitchen : kitchens) {
            kitchen.setDishes(recoverDishes(kitchen.getI_kitchen()));
        }
        return kitchens;
    }

    private List<Dish> recoverDishes(long i_kitchen) {
        List<Dish> dishes = databaseHandler.getAllDishesById(i_kitchen);
        for (Dish dish : dishes) {
            dish.setProducts(databaseHandler.getAllProductsById(dish.getI_dish()));
        }
        return dishes;
    }

    private Kitchen getKitchenByIndex(int indexK) {
        return restaurant.getKitchens().get(indexK);
    }

    private Dish getDishByIndex(int indexK, int indexD) {
        return restaurant.getKitchens().get(indexK).getDishes().get(indexD);
    }

    private Product getProductByIndex(int indexK, int indexD, int indexP) {
        return restaurant.getKitchens().get(indexK).getDishes().get(indexD).getProducts().get(indexP);
    }

    public long createRestaurant() {
        restaurant.setI_restaurant(DatabaseHandler.getInstance().createRestaurant(restaurant));
        return restaurant.getI_restaurant();
    }

    public long createKitchen(int index) {
        getKitchenByIndex(index)
                .setI_kitchen(DatabaseHandler.getInstance().createKitchen(getKitchenByIndex(index)));
        return getKitchenByIndex(index).getI_kitchen();
    }

    public long createDish(int indexK, int indexD) {
        getDishByIndex(indexK, indexD)
                .setI_dish(DatabaseHandler.getInstance().createDish(getDishByIndex(indexK, indexD)));
        return getDishByIndex(indexK, indexD).getI_dish();
    }

    public long createProduct(int indexK, int indexD, int indexP) {
        getProductByIndex(indexK, indexD, indexP)
                .setI_product(DatabaseHandler.getInstance().createProduct(getProductByIndex(indexK, indexD, indexP)));
        return getProductByIndex(indexK, indexD, indexP).getI_product();
    }

    public boolean updateRestaurant() {
        return databaseHandler.updateRestaurant(restaurant);
    }

    public boolean updateKitchen(int index) {
        return databaseHandler.updateKithcen(getKitchenByIndex(index));
    }

    public boolean updateDish(int indexK, int indexD) {
        return databaseHandler.updateDish(getDishByIndex(indexK, indexD));
    }

    public boolean updateProduct(int indexK, int indexD, int indexP) {
        return databaseHandler.updateProduct(getProductByIndex(indexK, indexD, indexP));
    }

    public boolean deleteRestaurant() {
        boolean flag = true;
        if (databaseHandler.deleteRestaurantById(restaurant.getI_restaurant())) {
            while (!restaurant.getKitchens().isEmpty()) {
                flag = flag & deleteKitchen(0);
            }
            restaurant.getKitchens().removeAll(restaurant.getKitchens());
            restaurant = new Restaurant();
            restaurant.setI_restaurant(-2);
            return true;
        } else return false;
    }

    public boolean deleteKitchen(int indexK) {
        boolean flag = true;
        if (databaseHandler.deleteKitchenById(getKitchenByIndex(indexK).getI_kitchen())) {
            while (!getKitchenByIndex(indexK).getDishes().isEmpty()) {
                flag = flag & deleteDish(indexK, 0);
            }
            getKitchenByIndex(indexK).getDishes().removeAll(getKitchenByIndex(indexK).getDishes());
            restaurant.getKitchens().remove(indexK);
            return true;
        } else return false;
    }

    public boolean deleteDish(int indexK, int indexD) {
        boolean flag = true;
        if (databaseHandler.deleteDishById(getDishByIndex(indexK, indexD).getI_kitchen())) {
            while (!getDishByIndex(indexK, indexD).getProducts().isEmpty()) {
                flag = flag & deleteProduct(indexK, indexD, 0);
            }
            getDishByIndex(indexK, indexD).getProducts().removeAll(getDishByIndex(indexK, indexD).getProducts());
            getKitchenByIndex(indexK).getDishes().remove(indexD);
            return true;
        } else return false;
    }

    public boolean deleteProduct(int indexK, int indexD, int indexP) {
        if (databaseHandler.deleteProductById(getProductByIndex(indexK, indexD, indexP).getI_dish())) {
            getDishByIndex(indexK, indexD).getProducts().remove(indexP);
            return true;
        } else return false;
    }
}
