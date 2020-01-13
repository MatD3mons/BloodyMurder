package fr.MatD3mons.BloodyMurder.bdd;

import fr.MatD3mons.BloodyMurder.BloodyMurder;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.utile.Repository;
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
            try {
                PreparedStatement q = connection.prepareStatement("INSERT INTO joueurs(uuid,kills,argent,deaths,win,lose,grade) VALUES (?,?,?,?,?,?,?)");
                q.setString(1, player.getUniqueId().toString());// uuid
                q.setInt(2, 0);//kills;
                q.setInt(3, 0);//argent;
                q.setInt(4, 0);//deaths
                q.setInt(5, 0);//win
                q.setInt(6, 0);//lose
                q.setString(7, "joueur");
                q.execute();
                q.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                BloodyMurder.sql.disconnect();
            }

        } else {
            if (Repository.BloodyPlayerContains(player)) {

                BloodyPlayer b = Repository.findBloodyPlayer(player);
                b.setStatut(get("kills", player), get("argent", player), get("deaths", player), get("win", player), get("lose", player), "joueur");
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

    public int get(String s, Player player) {
        //SELECT

        try {
            PreparedStatement q = connection.prepareStatement("SELECT " + s + " FROM joueurs WHERE uuid = ?");
            q.setString(1, player.getUniqueId().toString());

            int count = 0;
            ResultSet rs = q.executeQuery();

            while (rs.next()) {
                count = rs.getInt(s);
            }

            q.close();

            return count;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            BloodyMurder.sql.disconnect();
        }

        return 0;
    }

    public void udpateStatut(BloodyPlayer b) {

        int kills = b.getTotaltekill();
        int argent = b.getArgent();
        int deaths = b.getDeaths();
        int win = b.getWin();
        int lose = b.getLose();

        try {
            PreparedStatement rs = connection.prepareStatement("UPDATE joueurs SET kills = ?,argent = ?,deaths = ?,win = ?,lose = ? WHERE uuid = ?");
            rs.setInt(1, kills);
            rs.setInt(2, argent);
            rs.setInt(3, deaths);
            rs.setInt(4, win);
            rs.setInt(5, lose);
            rs.setString(6, b.getPlayerInstance().getUniqueId().toString());
            rs.executeUpdate();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            BloodyMurder.sql.disconnect();
        }
    }
}