package fr.MatD3mons.BloodyMurder.bdd;

import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class BloodyPlayerDao implements Dao<UUID, BloodyPlayer> {

    private final String TABLE_NAME = "player";

    @Override
    public void update(UUID key, BloodyPlayer b) {
        int kills = b.getTotaltekill();
        int argent = b.getArgent();
        int deaths = b.getDeaths();
        int win = b.getWin();
        int lose = b.getLose();

        try(Connection con = DataSourceFactory.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE "+TABLE_NAME+" SET kills = ?,argent = ?,deaths = ?,win = ?,lose = ? WHERE uuid = ?"))
        {
            ps.setInt(1, kills);
            ps.setInt(2, argent);
            ps.setInt(3, deaths);
            ps.setInt(4, win);
            ps.setInt(5, lose);
            ps.setString(6, b.getPlayerInstance().getUniqueId().toString());
            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public BloodyPlayer create(UUID key) {
        if (!hasAccount(key)) {
            try (Connection con = DataSourceFactory.getDataSource().getConnection();
                 PreparedStatement ps = con.prepareStatement("INSERT INTO " + TABLE_NAME + "(uuid,kills,argent,deaths,win,lose,grade) VALUES (?,?,?,?,?,?,?)")) {
                ps.setString(1, key.toString());// uuid
                ps.setInt(2, 0);//kills;
                ps.setInt(3, 0);//argent;
                ps.setInt(4, 0);//deaths
                ps.setInt(5, 0);//win
                ps.setInt(6, 0);//lose
                ps.setString(7, "joueur");
                ps.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Player player = Bukkit.getPlayer(key);
        BloodyPlayer bloodyPlayer = get(key);
        if(!Repository.BloodyPlayerContains(player) && bloodyPlayer != null)
            Repository.players.put(player, bloodyPlayer);
        return bloodyPlayer;
    }

    @Override
    public void delete(UUID key) {

    }

    @Override
    public BloodyPlayer get(UUID key) {
        BloodyPlayer bloodyPlayer = null;
        try (Connection con = DataSourceFactory.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM "+TABLE_NAME+" WHERE uuid = ?"))
        {
            ps.setString(1, Bukkit.getPlayer(key).getUniqueId().toString());
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    bloodyPlayer = new BloodyPlayer(Bukkit.getPlayer(key));
                    bloodyPlayer.setStatut(
                            rs.getInt(2),
                            rs.getInt(3),
                            rs.getInt(4),
                            rs.getInt(5),
                            rs.getInt(6),
                            rs.getString(7));
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bloodyPlayer;
    }

    public boolean hasAccount(UUID key) {
        try (Connection con = DataSourceFactory.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT uuid FROM "+TABLE_NAME+" WHERE uuid = ?", Statement.RETURN_GENERATED_KEYS))
        {
            ps.setString(1, Bukkit.getPlayer(key).getUniqueId().toString());
            try(ResultSet rs = ps.executeQuery()) {
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
