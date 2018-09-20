package app.Controller;

import app.Dialogs;
import app.Model.*;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.List;

public class Controller extends AnchorPane {

    private static BaseDataController dataController;
    @FXML
    GridPane info;
    private Scene scene;
    @FXML
    private Menu file, rest_selector, operations;
    @FXML
    private MenuItem new_rest;
    @FXML
    private Button add_button, edit_button, delete_button;
    @FXML
    private TreeView<TItem> tree;
    @FXML
    private Text logo;

    public void setApp(Scene scene) {
        this.scene = scene;
    }

    @FXML
    void initialize() {

        updateSelector();

        new_rest.setOnAction(newRestaurant());
        disableButtons();
        tree.getSelectionModel().selectedItemProperty().addListener(this::changed);
    }

    private void disableButtons() {
        add_button.setVisible(false);
        edit_button.setVisible(false);
        delete_button.setVisible(false);
    }

    private void updateSelector() {
        rest_selector.getItems().clear();
        for (Restaurant r : BaseDataController.getRestaurants()) {
            MenuItem mItem = new MenuItem(r.getName());
            mItem.setUserData(r.getI_restaurant());
            mItem.setOnAction(selectRestaurant());
            rest_selector.getItems().add(mItem);
        }
    }

    private void updateView() {
        Restaurant r = dataController.getRestaurant();
        TreeItem<TItem> rootItem = new TreeItem<TItem>(new TItem(r.getName()));
        rootItem.setExpanded(true);
        if (r.getKitchens() != null)
            for (int i = 0; i < r.getKitchens().size(); i++) {
                Kitchen k = r.getKitchens().get(i);
                TreeItem<TItem> kItem = new TreeItem<TItem>(new TItem(k.getName(), i));
                if (k.getDishes() != null)
                    for (int j = 0; j < k.getDishes().size(); j++) {
                        Dish d = k.getDishes().get(j);
                        TreeItem<TItem> dItem = new TreeItem<TItem>(new TItem(d.getName(), i, j));
                        if (d.getProducts() != null)
                            for (int h = 0; h < d.getProducts().size(); h++) {
                                Product p = d.getProducts().get(i);
                                TreeItem<TItem> pItem = new TreeItem<TItem>(new TItem(p.getName(), i, j, h));
                                dItem.getChildren().add(pItem);
                            }
                        kItem.getChildren().add(dItem);
                    }
                rootItem.getChildren().add(kItem);
            }
        tree.setRoot(rootItem);
        logo.setText(r.getName());
    }

    @FXML
    public void onAddButtonClick(ActionEvent e) {
        Restaurant restaurant = dataController.getRestaurant();
        MultipleSelectionModel<TreeItem<TItem>> el = tree.getSelectionModel();
        TItem element = el.getSelectedItem().getValue();
        List<Integer> index = element.getIndexes();

        switch (index.size()) {
            case 0: // Selected restaurant
                System.out.println("add restaurant" + restaurant.getName());
                Dialogs.kitchenDialog(true).ifPresent(result -> {
                    result.setI_restaurant(restaurant.getI_restaurant());
                    restaurant.getKitchens().add(result);
                    dataController.createKitchen(restaurant.getKitchens().size() - 1);
                    updateView();
                });
                break;
            case 1: // Selected kitchen
                Dialogs.dishDialog(true).ifPresent(result -> {
                    Kitchen kitchen = getKitchenByIndex(index.get(0));
                    result.setI_kitchen(kitchen.getI_kitchen());
                    kitchen.getDishes().add(result);
                    dataController.createDish(index.get(0), kitchen.getDishes().size() - 1);
                    updateView();
                });
                break;
            case 2: // Selected dish
                Dish dish = getDishByIndex(index.get(0), index.get(1));
                Dialogs.productDialog(true).ifPresent(result -> {
                    result.setI_dish(dish.getI_dish());
                    dish.getProducts().add(result);
                    dataController.createProduct(index.get(0), index.get(1), dish.getProducts().size() - 1);
                    updateView();
                });
                break;
        }
    }

    @FXML
    public void onEditButtonClick(ActionEvent e) {
        Restaurant restaurant = dataController.getRestaurant();
        MultipleSelectionModel<TreeItem<TItem>> el = tree.getSelectionModel();
        TItem element = el.getSelectedItem().getValue();
        List<Integer> index = element.getIndexes();

        switch (index.size()) {
            case 0: // Selected restaurant

                System.out.println("edit restaurant" + restaurant.getName());
                Dialogs.restaurantDialog(false).ifPresent(restaurant1 -> {
                    // TODO: save edit in db
                });
                break;
            case 1: // Selected kitchen

                Kitchen kitchen = getKitchenByIndex(index.get(0));
                Dialogs.kitchenDialog(false).ifPresent(result -> {
                    // TODO: save edit in db
                });
                System.out.println("edit kitchen" + kitchen);
                break;
            case 2: // Selected dish
                Dish dish = getDishByIndex(index.get(0), index.get(1));
                Dialogs.dishDialog(false).ifPresent(result -> {
                    // TODO: save edit in db
                });
                System.out.println("edit dish" + dish);
                break;
            case 3: // Selected product
                Product product = getProductByIndex(index.get(0), index.get(1), index.get(2));
                Dialogs.productDialog(false).ifPresent(product1 -> {
                    // TODO: save edit in db
                });
                System.out.println("edit product" + product);
                break;

        }
    }

    @FXML
    public void onDeleteButtonClick(ActionEvent e) {
        Restaurant restaurant = dataController.getRestaurant();
        MultipleSelectionModel<TreeItem<TItem>> el = tree.getSelectionModel();
        TItem element = el.getSelectedItem().getValue();
        List<Integer> index = element.getIndexes();
        Dialogs.confirmDialog().ifPresent(product1 -> {
            switch (index.size()) {
                case 0: // Selected restaurant
                    dataController.deleteRestaurant();
                    System.out.println("delete restaurant" + restaurant.getName());
                    break;
                case 1: // Selected kitchen
                    // TODO: save edit in db
                    Kitchen kitchen = getKitchenByIndex(index.get(0));
                    dataController.deleteKitchen(index.get(0));
                    break;
                case 2: // Selected dish
                    // TODO: save edit in db
                    Dish dish = getDishByIndex(index.get(0), index.get(1));
                    dataController.deleteDish(index.get(0), index.get(1));
                    System.out.println("delete dish" + dish);
                    break;
                case 3: // Selected product
                    // TODO: save edit in db
                    Product product = getProductByIndex(index.get(0), index.get(1), index.get(2));
                    dataController.deleteProduct(index.get(0), index.get(1), index.get(2));
                    System.out.println("delete product" + product);
                    break;
            }
            updateView();
            disableButtons();
            updateSelector();
        });

    }

    private EventHandler<ActionEvent> selectRestaurant() {
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                MenuItem mItem = (MenuItem) event.getSource();
                dataController = BaseDataController.getInstance(Long.valueOf(mItem.getUserData().toString()));
                updateView();
            }
        };
    }

    private EventHandler<ActionEvent> newRestaurant() {
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Dialogs.restaurantDialog(true).ifPresent(result -> {
                    dataController = BaseDataController.getInstance(-1);
                    dataController.setRestaurant(result);
                    dataController.createRestaurant();
                    updateView();
                    disableButtons();
                    updateSelector();
                });
            }
        };
    }

    private Kitchen getKitchenByIndex(int indexK) {
        return dataController.getRestaurant().getKitchens().get(indexK);
    }

    private Dish getDishByIndex(int indexK, int indexD) {
        return dataController.getRestaurant().getKitchens().get(indexK).getDishes().get(indexD);
    }

    private Product getProductByIndex(int indexK, int indexD, int indexP) {
        return dataController.getRestaurant().getKitchens().get(indexK).getDishes().get(indexD).getProducts().get(indexP);
    }

    private void changed(ObservableValue observable, Object oldValue, Object newValue) {
        Restaurant restaurant = dataController.getRestaurant();
        info.getChildren().clear();
        TreeItem<TItem> selectedItem = (TreeItem<TItem>) newValue;
        if (newValue != null) {
            List<Integer> index = selectedItem.getValue().getIndexes();
            edit_button.setVisible(true);
            delete_button.setVisible(true);
            info.setHgap(2);
            info.setVgap(4);
            info.setPadding(new Insets(8, 8, 8, 8));
            switch (index.size()) {
                case 0: // Selected restaurant
                    add_button.setVisible(true);
                    info.add(new Label("Restaurant name:"), 0, 0);
                    info.add(new Label(restaurant.getName()), 1, 0);
                    info.add(new Label("director:"), 0, 1);
                    info.add(new Label(restaurant.getDirector()), 1, 1);
                    info.add(new Label("Address:"), 0, 2);
                    info.add(new Label(restaurant.getAddress()), 1, 2);
                    info.add(new Label("Tel:"), 0, 3);
                    info.add(new Label(restaurant.getTel()), 1, 3);

                    break;
                case 1: // Selected kitchen
                    Kitchen kitchen = getKitchenByIndex(index.get(0));

                    add_button.setVisible(true);
                    info.add(new Label("Kitchen name:"), 0, 0);
                    info.add(new Label(kitchen.getName()), 1, 0);
                    info.add(new Label("Chef"), 0, 1);
                    info.add(new Label(kitchen.getChef()), 1, 1);
                    break;
                case 2: // Selected dish
                    Dish dish = getDishByIndex(index.get(0), index.get(1));

                    add_button.setVisible(true);
                    info.add(new Label("Dish name:"), 0, 0);
                    info.add(new Label(restaurant.getName()), 1, 0);
                    info.add(new Label("Description:"), 0, 1);
                    info.add(new Label(dish.getDescription()), 0, 2);
                    info.add(new Label("Price:"), 0, 3);
                    info.add(new Label(dish.getPrice() + "$"), 1, 3);
                    info.add(new Label("Qty:"), 0, 4);
                    info.add(new Label(dish.getQty() + dish.getUnit()), 1, 4);
                    break;
                case 3: // Selected product
                    Product product = getProductByIndex(index.get(0), index.get(1), index.get(2));

                    add_button.setVisible(false);
                    info.add(new Label("Product name:"), 0, 0);
                    info.add(new Label(product.getName()), 1, 0);
                    info.add(new Label("Qty:"), 0, 1);
                    info.add(new Label(product.getQty() + product.getUnit()), 1, 1);
                    break;
            }
        }
    }
}
