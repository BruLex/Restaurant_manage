package app.DataTransferObject;

import app.Model.Dish;
import app.Model.Kitchen;
import app.Model.Product;
import app.Model.Restaurant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/main_database?autoReconnect=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "кщще110516";

    private static final String RESTAURANT_TABLE = "restaurant";
    private static final String KITCHEN_TABLE = "kitchen";
    private static final String DISH_TABLE = "dish";
    private static final String PRODUCT_TABLE = "product";

    private static final String KEY_I_RESTAURANT = "i_restaurant";
    private static final String KEY_I_KITCHEN = "i_kitchen";
    private static final String KEY_I_DISH = "i_dish";
    private static final String KEY_I_PRODUCT = "i_product";

    private static final String KEY_NAME = "name";
    private static final String KEY_DIRECTOR = "director";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_TEL = "tel";
    private static final String KEY_CHEF = "chef";
    private static final String KEY_UNIT = "unit";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_QTY = "qty";
    private static final String KEY_PRICE = "price";

    private static DatabaseHandler instance = null;

    private static Connection conn;
    private static Statement stmt;
    private static ResultSet res;

    private DatabaseHandler() throws SQLException {
        initTables();
    }

    public static synchronized DatabaseHandler getInstance() {
        if (instance == null)
            try {
                instance = new DatabaseHandler();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        return instance;
    }

    private static void initTables() {
        final String sql_restaurant_table = "CREATE TABLE IF NOT EXISTS " + RESTAURANT_TABLE + "("
                + KEY_I_RESTAURANT + " INTEGER PRIMARY KEY AUTO_INCREMENT,"
                + KEY_NAME + " VARCHAR(64) NOT NULL,"
                + KEY_DIRECTOR + " VARCHAR(64) NOT NULL,"
                + KEY_ADDRESS + " VARCHAR(64) NOT NULL,"
                + KEY_TEL + " VARCHAR(64) NOT NULL"
                + ");";
        final String sql_kitchen_table = "CREATE TABLE IF NOT EXISTS " + KITCHEN_TABLE + "("
                + KEY_I_KITCHEN + " INTEGER PRIMARY KEY AUTO_INCREMENT,"
                + KEY_I_RESTAURANT + " INTEGER NOT NULL,"
                + KEY_NAME + " VARCHAR(64) NOT NULL,"
                + KEY_CHEF + " VARCHAR(64) NOT NULL"
                + ");";
        final String sql_dish_table = "CREATE TABLE IF NOT EXISTS " + DISH_TABLE + "("
                + KEY_I_DISH + " INTEGER PRIMARY KEY AUTO_INCREMENT,"
                + KEY_I_KITCHEN + " INTEGER NOT NULL,"
                + KEY_NAME + " VARCHAR(64) NOT NULL,"
                + KEY_UNIT + " VARCHAR(64) NOT NULL,"
                + KEY_DESCRIPTION + " VARCHAR(512) NOT NULL,"
                + KEY_QTY + " INTEGER NOT NULL,"
                + KEY_PRICE + " REAL NOT NULL"
                + ");";
        final String sql_product_table = "CREATE TABLE IF NOT EXISTS " + PRODUCT_TABLE + "("
                + KEY_I_PRODUCT + " INTEGER PRIMARY KEY AUTO_INCREMENT,"
                + KEY_I_DISH + " INTEGER NOT NULL,"
                + KEY_NAME + " VARCHAR(64) NOT NULL,"
                + KEY_UNIT + " VARCHAR(64) NOT NULL,"
                + KEY_QTY + " INTEGER NOT NULL"
                + ");";
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            stmt = conn.createStatement();
            stmt.executeUpdate(sql_restaurant_table);
            stmt.executeUpdate(sql_kitchen_table);
            stmt.executeUpdate(sql_dish_table);
            stmt.executeUpdate(sql_product_table);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            //close connection ,stmt and resultset here
            try {
                conn.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
            try {
                stmt.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
    }

    public Restaurant getRestaurantById(long i_restaurant) {
        final String SELECT_SQL = "SELECT * FROM " + RESTAURANT_TABLE + " WHERE " + KEY_I_RESTAURANT + "=" + i_restaurant;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
            res = stmt.executeQuery(SELECT_SQL);

            if (res.next()) {
                return new Restaurant(res.getLong(KEY_I_RESTAURANT),
                        res.getString(KEY_NAME),
                        res.getString(KEY_DIRECTOR),
                        res.getString(KEY_ADDRESS),
                        res.getString(KEY_TEL));
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
            try {
                stmt.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
            try {
                res.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
    }

    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurantList = new ArrayList<>();
        final String SELECT_SQL = "SELECT * FROM " + RESTAURANT_TABLE;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
            res = stmt.executeQuery(SELECT_SQL);

            while (res.next()) {
                restaurantList.add(new Restaurant(res.getLong(KEY_I_RESTAURANT),
                        res.getString(KEY_NAME),
                        res.getString(KEY_DIRECTOR),
                        res.getString(KEY_ADDRESS),
                        res.getString(KEY_TEL)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
            try {
                stmt.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
            try {
                res.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
        return restaurantList;
    }

    public List<Kitchen> getAllKitchensById(long i_restaurant) {
        List<Kitchen> kitchenList = new ArrayList<>();
        final String SELECT_SQL = "SELECT * FROM " + KITCHEN_TABLE + " WHERE " + KEY_I_RESTAURANT + "=" + i_restaurant;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
            res = stmt.executeQuery(SELECT_SQL);
            while (res.next()) {
                kitchenList.add(new Kitchen(res.getLong(KEY_I_KITCHEN),
                        res.getLong(KEY_I_RESTAURANT),
                        res.getString(KEY_NAME),
                        res.getString(KEY_CHEF)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
            try {
                stmt.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
            try {
                res.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
        return kitchenList;
    }

    public List<Dish> getAllDishesById(long i_kitchen) {
        List<Dish> dishList = new ArrayList<>();
        final String SELECT_SQL = "SELECT * FROM " + DISH_TABLE + " WHERE " + KEY_I_KITCHEN + "=" + i_kitchen;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
            res = stmt.executeQuery(SELECT_SQL);
            while (res.next()) {
                dishList.add(new Dish(res.getLong(KEY_I_DISH),
                        res.getLong(KEY_I_KITCHEN),
                        res.getString(KEY_NAME),
                        res.getString(KEY_UNIT),
                        res.getString(KEY_DESCRIPTION),
                        res.getInt(KEY_QTY),
                        res.getFloat(KEY_PRICE)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
            try {
                stmt.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
            try {
                res.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
        return dishList;
    }

    public List<Product> getAllProductsById(long i_dish) {
        List<Product> kitchenList = new ArrayList<>();
        final String SELECT_SQL = "SELECT * FROM " + PRODUCT_TABLE + " WHERE " + KEY_I_DISH + "=" + i_dish;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
            res = stmt.executeQuery(SELECT_SQL);
            while (res.next()) {
                kitchenList.add(new Product(res.getLong(KEY_I_PRODUCT),
                        res.getLong(KEY_I_DISH),
                        res.getString(KEY_NAME),
                        res.getString(KEY_UNIT),
                        res.getInt(KEY_QTY)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
            try {
                stmt.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
            try {
                res.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
        return kitchenList;
    }

    public long createRestaurant(Restaurant restaurant) {
        final String INSERT_SQL = "INSERT INTO " + RESTAURANT_TABLE + "("
                + KEY_NAME + ","
                + KEY_DIRECTOR + ","
                + KEY_ADDRESS + ","
                + KEY_TEL
                + ") VALUES(?, ?, ?, ?)";
        long id = -1;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, restaurant.getName());
            pstmt.setString(2, restaurant.getDirector());
            pstmt.setString(3, restaurant.getAddress());
            pstmt.setString(4, restaurant.getTel());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getLong(1);
            }
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        } finally {
            try {
                conn.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }

        }
        return id;
    }

    public long createKitchen(Kitchen kitchen) {
        final String INSERT_SQL = "INSERT INTO " + KITCHEN_TABLE + "("
                + KEY_I_RESTAURANT + ","
                + KEY_NAME + ","
                + KEY_CHEF
                + ") VALUES(?, ?, ?)";
        long id = -1;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, kitchen.getI_restaurant());
            pstmt.setString(2, kitchen.getChef());
            pstmt.setString(3, kitchen.getChef());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getLong(1);
            }

            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        } finally {
            try {
                conn.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
        return id;
    }

    public long createDish(Dish dish) {
        final String INSERT_SQL = "INSERT INTO " + DISH_TABLE + "("
                + KEY_I_KITCHEN + ","
                + KEY_NAME + ","
                + KEY_UNIT + ","
                + KEY_DESCRIPTION + ","
                + KEY_QTY + ","
                + KEY_PRICE
                + ") VALUES(?, ?, ?, ?, ?, ?)";
        long id;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, dish.getI_kitchen());
            pstmt.setString(2, dish.getName());
            pstmt.setString(3, dish.getUnit());
            pstmt.setString(4, dish.getDescription());
            pstmt.setInt(5, dish.getQty());
            pstmt.setFloat(6, dish.getPrice());
            id = pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        } finally {
            try {
                conn.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
        return id;
    }

    public long createProduct(Product product) {
        final String INSERT_SQL = "INSERT INTO " + PRODUCT_TABLE + "("
                + KEY_I_DISH + ","
                + KEY_NAME + " ,"
                + KEY_UNIT + ","
                + KEY_QTY
                + ") VALUES(?, ?, ?, ?)";
        long id;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, product.getI_dish());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getUnit());
            pstmt.setInt(4, product.getQty());
            id = pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        } finally {
            try {
                conn.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
        return id;
    }

    public boolean updateRestaurant(Restaurant restaurant) {
        String UPDATE_SQL = "UPDATE " + RESTAURANT_TABLE + " SET "
                + KEY_NAME + "= ? , "
                + KEY_DIRECTOR + "= ? , "
                + KEY_ADDRESS + "= ? , "
                + KEY_TEL + "= ? "
                + "WHERE " + KEY_I_RESTAURANT + "= ?";

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(UPDATE_SQL);
            pstmt.setString(1, restaurant.getName());
            pstmt.setString(2, restaurant.getDirector());
            pstmt.setString(3, restaurant.getAddress());
            pstmt.setString(4, restaurant.getTel());
            pstmt.setLong(5, restaurant.getI_restaurant());
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
        return true;
    }

    public boolean updateKithcen(Kitchen kitchen) {
        final String UPDATE_SQL = "UPDATE " + KITCHEN_TABLE + " SET "
                + KEY_NAME + "= ? ,"
                + KEY_CHEF + "= ? "
                + "WHERE " + KEY_I_KITCHEN + "= ?";
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(UPDATE_SQL);
            pstmt.setString(1, kitchen.getChef());
            pstmt.setString(2, kitchen.getChef());
            pstmt.setLong(3, kitchen.getI_kitchen());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
        return true;
    }

    public boolean updateDish(Dish dish) {
        final String UPDATE_SQL = "UPDATE " + DISH_TABLE + " SET "
                + KEY_NAME + "= ? ,"
                + KEY_UNIT + "= ? ,"
                + KEY_DESCRIPTION + "= ? ,"
                + KEY_QTY + "= ? ,"
                + KEY_PRICE + "= ? "
                + "WHERE " + KEY_I_DISH + "= ?";
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(UPDATE_SQL);
            pstmt.setString(1, dish.getName());
            pstmt.setString(2, dish.getUnit());
            pstmt.setString(3, dish.getDescription());
            pstmt.setInt(4, dish.getQty());
            pstmt.setFloat(5, dish.getPrice());
            pstmt.setLong(6, dish.getI_dish());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
        return true;
    }

    public boolean updateProduct(Product product) {
        final String UPDATE_SQL = "UPDATE " + PRODUCT_TABLE + " SET "
                + KEY_NAME + "= ? ,"
                + KEY_UNIT + "= ? ,"
                + KEY_QTY + "= ? "
                + "WHERE " + KEY_I_PRODUCT + "= ?";
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(UPDATE_SQL);
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getUnit());
            pstmt.setInt(3, product.getQty());
            pstmt.setLong(4, product.getI_dish());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
        return true;
    }

    private boolean deleteByID(long i_key, String table, String key) {
        final String DELETE_SQL = "DELETE FROM " + table + " WHERE " + key + " = ?";
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(DELETE_SQL);
            pstmt.setLong(1, i_key);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
        return true;
    }

    public boolean deleteRestaurantById(long i_restaurant) {
        return deleteByID(i_restaurant, RESTAURANT_TABLE, KEY_I_RESTAURANT);
    }

    public boolean deleteKitchenById(long i_kitchen) {
        return deleteByID(i_kitchen, KITCHEN_TABLE, KEY_I_KITCHEN);
    }

    public boolean deleteDishById(long i_dish) {
        return deleteByID(i_dish, DISH_TABLE, KEY_I_DISH);
    }

    public boolean deleteProductById(long i_dish) {
        return deleteByID(i_dish, PRODUCT_TABLE, KEY_I_PRODUCT);
    }
}