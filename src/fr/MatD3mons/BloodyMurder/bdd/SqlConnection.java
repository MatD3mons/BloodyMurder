package fr.MatD3mons.BloodyMurder.bdd;

import org.bukkit.entity.Player;

import java.sql.*;

public class SqlConnection {

    private Connection connection;
    private String urlbase, host, database, user, pass;

    public SqlConnection(String urlbase, String host, String database, String user, String pass) {
        this.urlbase = urlbase;
        this.host = host;
        this.database = database;
        this.user = user;
        this.pass = pass;
    }

    public void connection() {
        if (!isConnected()) {
            try {
                connection = DriverManager.getConnection(urlbase + host + "/" + database, user, pass);
                System.out.println("connected ok");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
                System.out.println("connected off");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected() {
        return connection != null;
    }


    public void createAccount(Player player) {
        if (!hasAccount(player)) {
            //INSERT

            try {
                PreparedStatement q = connection.prepareStatement("INSERT INTO joueurs(uuid,coins,grade) VALUES (?,?,?)");
                q.setString(1, player.getUniqueId().toString());
                q.setInt(2, 0);
                q.setString(3, "joueur");
                q.execute();
                q.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public boolean hasAccount(Player player) {
        //SELECT

        try {
            PreparedStatement q = connection.prepareStatement("SELECT uuid FROM joueurs WHERE uuid = ?");
            q.setString(1, player.getUniqueId().toString());
            ResultSet resultat = q.executeQuery();
            boolean hasAccount = resultat.next();
            q.close();
            return hasAccount;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public int getBalance(Player player) {
        //SELECT

        try {
            PreparedStatement q = connection.prepareStatement("SELECT coins FROM joueurs WHERE uuid = ?");
            q.setString(1, player.getUniqueId().toString());

            int balance = 0;
            ResultSet rs = q.executeQuery();

            while (rs.next()) {
                balance = rs.getInt("coins");
            }

            q.close();

            return balance;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
    public void addMoney(Player player, int amount) {
        //UPDATE

        int balance = getBalance(player);
        int newbalance = balance + amount;

        try {
            PreparedStatement rs = connection.prepareStatement("UPDATE joueurs SET coins = ? WHERE uuid = ?");
            rs.setInt(1, newbalance);
            rs.setString(2, player.getUniqueId().toString());
            rs.executeUpdate();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void removeMoney(Player player, int amount) {
        //UPDATE

        int balance = getBalance(player);
        int newbalance = balance - amount;

        if (newbalance <= 0) {
            return;
        }

        try {
            PreparedStatement rs = connection.prepareStatement("UPDATE joueurs SET coins = ? WHERE uuid = ?");
            rs.setInt(1, newbalance);
            rs.setString(2, player.getUniqueId().toString());
            rs.executeUpdate();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}