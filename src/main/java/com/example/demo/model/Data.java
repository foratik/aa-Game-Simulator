package com.example.demo.model;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Data {
    private static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<CircleGameData> savedCircles = new ArrayList<>();
    public static ArrayList<LineData> savedLines = new ArrayList<>();
    public static OtherGameData otherGameData = null;

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        Data.users = users;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static User findUser(String username){
        for (User user : users)
            if (user.getUsername().equals(username))
                return user;
        return null;
    }

    public static void saveUsersInJSON() {

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("data.json");
            fileWriter.write(new Gson().toJson(Data.getUsers()));
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void saveGameInJSON() {

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("circles.json");
            fileWriter.write(new Gson().toJson(Data.savedCircles));
            fileWriter.close();
            fileWriter = new FileWriter("lines.json");
            fileWriter.write(new Gson().toJson(Data.savedLines));
            fileWriter.close();
            fileWriter = new FileWriter("otherData.json");
            fileWriter.write(new Gson().toJson(Data.otherGameData));
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void initializeGameData() {
        String json = null;
        try {
            json = new String(Files.readAllBytes(Paths.get("circles.json")));
            ArrayList<CircleGameData> circles = new Gson().fromJson(json, new TypeToken<ArrayList<CircleGameData>>() {
            }.getType());
            if (circles != null) Data.savedCircles = circles;

            json = new String(Files.readAllBytes(Paths.get("lines.json")));
            ArrayList<LineData> lines = new Gson().fromJson(json, new TypeToken<ArrayList<LineData>>() {
            }.getType());
            if (lines != null) Data.savedLines = lines;

            json = new String(Files.readAllBytes(Paths.get("otherData.json")));
            OtherGameData otherGameData1 = new Gson().fromJson(json, new TypeToken<OtherGameData>() {
            }.getType());
            if (otherGameData1 != null) Data.otherGameData = otherGameData1;

        } catch (IOException e) {
            System.out.println("Unable to read from database");
        }

    }
    public static void initializeUsers() {
        String json = null;
        try {
            json = new String(Files.readAllBytes(Paths.get("data.json")));
            ArrayList<User> users = new Gson().fromJson(json, new TypeToken<ArrayList<User>>() {
            }.getType());
            if (users != null) Data.setUsers(users);
        } catch (IOException e) {
            System.out.println("Unable to read from database");
        }

    }
    public static void removeUser(User user){
        users.remove(user);
    }
}
