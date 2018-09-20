package app;

import app.Model.Dish;
import app.Model.Kitchen;
import app.Model.Product;
import app.Model.Restaurant;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class Dialogs {

    public static Optional<Restaurant> restaurantDialog(boolean add) {
        Dialog<Restaurant> dialog = new Dialog<>();
        if (add) {
            dialog.setTitle("Add Restaurant");
            dialog.setHeaderText("Please fill all field!");
        } else {
            dialog.setTitle("Edit Restaurant");
            dialog.setHeaderText("Edit field what you want!");
        }
        ButtonType loginButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Restaurant name");
        TextField director = new TextField();
        director.setPromptText("director name");
        TextField address = new TextField();
        address.setPromptText("Restaurant address");
        TextField tel = new TextField();
        tel.setPromptText("telephone number");

        grid.add(new Label("Restaurant:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Director:"), 0, 1);
        grid.add(director, 1, 1);
        grid.add(new Label("Address:"), 0, 2);
        grid.add(address, 1, 2);
        grid.add(new Label("Tel:"), 0, 3);
        grid.add(tel, 1, 3);


        Node SaveButton = dialog.getDialogPane().lookupButton(loginButtonType);
        SaveButton.setDisable(true);

        name.textProperty().addListener((observable, oldValue, newValue) -> {
            visible(SaveButton, name, director, address, tel);
        });
        director.textProperty().addListener((observable, oldValue, newValue) -> {
            visible(SaveButton, name, director, address, tel);
        });
        address.textProperty().addListener((observable, oldValue, newValue) -> {
            visible(SaveButton, name, director, address, tel);
        });
        tel.textProperty().addListener((observable, oldValue, newValue) -> {
            visible(SaveButton, name, director, address, tel);
        });

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Restaurant(-1,
                        name.getText(),
                        director.getText(),
                        address.getText(),
                        tel.getText());
            }
            return null;
        });
        return dialog.showAndWait();
    }

    public static Optional<Kitchen> kitchenDialog(boolean add) {
        Dialog<Kitchen> dialog = new Dialog<>();
        if (add) {
            dialog.setTitle("Add Kitchen");
            dialog.setHeaderText("Please fill all field!");
        } else {
            dialog.setTitle("Edit Kitchen");
            dialog.setHeaderText("Edit field what you want!");
        }
        ButtonType loginButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Kitchen name");
        TextField chef = new TextField();
        chef.setPromptText("Chef name");


        grid.add(new Label("Kitchen:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Chef:"), 0, 1);
        grid.add(chef, 1, 1);
        Node SaveButton = dialog.getDialogPane().lookupButton(loginButtonType);
        SaveButton.setDisable(true);

        name.textProperty().addListener((observable, oldValue, newValue) -> {
            SaveButton.setDisable(name.getText().trim().isEmpty() || chef.getText().trim().isEmpty());
        });
        chef.textProperty().addListener((observable, oldValue, newValue) -> {
            SaveButton.setDisable(name.getText().trim().isEmpty() || chef.getText().trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Kitchen(-1, -1, name.getText(), chef.getText());
            }
            return null;
        });

        return dialog.showAndWait();
    }

    public static Optional<Dish> dishDialog(boolean add) {
        Dialog<Dish> dialog = new Dialog<>();
        if (add) {
            dialog.setTitle("Add Dish");
            dialog.setHeaderText("Please fill all field!");
        } else {
            dialog.setTitle("Edit Dish");
            dialog.setHeaderText("Edit field what you want!");
        }
        ButtonType loginButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Dish name");
        TextField unit = new TextField();
        unit.setPromptText("kg/gr/ml/l");
        TextField description = new TextField();
        description.setPromptText("Push here your description");
        TextField qty = new TextField();
        qty.setPromptText("qty of this dish");
        TextField price = new TextField();
        price.setPromptText("in dollars");

        grid.add(new Label("Dish:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Unit:"), 0, 1);
        grid.add(unit, 1, 1);
        grid.add(new Label("Description:"), 0, 2);
        grid.add(description, 1, 2);
        grid.add(new Label("Qty:"), 0, 3);
        grid.add(qty, 1, 3);
        grid.add(new Label("Price:"), 0, 4);
        grid.add(price, 1, 4);

        Node SaveButton = dialog.getDialogPane().lookupButton(loginButtonType);
        SaveButton.setDisable(true);

        name.textProperty().addListener((observable, oldValue, newValue) -> {
            visible(SaveButton, name, unit, description, qty, price);
        });
        unit.textProperty().addListener((observable, oldValue, newValue) -> {
            visible(SaveButton, name, unit, description, qty, price);
        });
        description.textProperty().addListener((observable, oldValue, newValue) -> {
            visible(SaveButton, name, unit, description, qty, price);
        });
        qty.textProperty().addListener((observable, oldValue, newValue) -> {
            visible(SaveButton, name, unit, description, qty, price);
        });
        price.textProperty().addListener((observable, oldValue, newValue) -> {
            visible(SaveButton, name, unit, description, qty, price);
        });
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Dish(-1, -1,
                        name.getText(),
                        unit.getText(),
                        description.getText(),
                        Integer.valueOf(qty.getText()),
                        Float.valueOf(price.getText()));
            }
            return null;
        });
        return dialog.showAndWait();
    }

    public static Optional<Product> productDialog(boolean add) {
        Dialog<Product> dialog = new Dialog<>();
        if (add) {
            dialog.setTitle("Add Product");
            dialog.setHeaderText("Please fill all field!");
        } else {
            dialog.setTitle("Edit Product");
            dialog.setHeaderText("Edit field what you want!");
        }
        ButtonType loginButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Product name");
        TextField unit = new TextField();
        unit.setPromptText("kg/gr/ml/l");
        TextField qty = new TextField();
        qty.setPromptText("qty of this product");

        grid.add(new Label("Product:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Unit:"), 0, 1);
        grid.add(unit, 1, 1);
        grid.add(new Label("Qty:"), 0, 2);
        grid.add(qty, 1, 2);

        Node SaveButton = dialog.getDialogPane().lookupButton(loginButtonType);
        SaveButton.setDisable(true);

        name.textProperty().addListener((observable, oldValue, newValue) -> {
            visible(SaveButton, name, unit, qty);
        });
        unit.textProperty().addListener((observable, oldValue, newValue) -> {
            visible(SaveButton, name, unit, qty);
        });

        qty.textProperty().addListener((observable, oldValue, newValue) -> {
            visible(SaveButton, name, unit, qty);
        });

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Product(-1, -1,
                        name.getText(),
                        unit.getText(),
                        Integer.valueOf(qty.getText()));
            }
            return null;
        });
        return dialog.showAndWait();
    }

    public static Optional<Boolean> confirmDialog() {
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Warning");
        dialog.setHeaderText("Are you sure that you want to do this?");
        ButtonType loginButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return true;
            }
            return null;
        });

        return dialog.showAndWait();
    }

    private static void visible(Node Button, TextField... fields) {
        boolean flag = false;
        for (TextField f : fields) {
            flag = flag || f.getText().trim().isEmpty();
        }
        Button.setDisable(flag);
    }
}
