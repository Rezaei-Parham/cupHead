package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.Vector;

public class Database {
    private static Vector<User> users = new Vector<>();
    private static final String databasePath = "./database/users.json";


    public static void addUser(User user) throws MalformedURLException {
        users.add(user);
        saveToJson();
    }

    public static boolean containsUser(User user) {
        return users.contains(user);
    }

    public static Vector<User> getUsers() {
        if (users == null) users = new Vector<>();
        return users;
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

    public static void removeUser(User user) {
        users.remove(user);
        try {
            saveToJson();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void saveToJson() throws MalformedURLException {

        try {
            Gson gson = new Gson();
            FileWriter fileWriter = new FileWriter(databasePath);
            gson.toJson(users, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadFromJson() {
        File file = new File(databasePath);
        if (!file.exists()) return;
        Gson gson = new Gson();
        try (Reader reader = new FileReader(databasePath)) {
            Type listType = new TypeToken<Vector<User>>() {
            }.getType();
            users = gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
