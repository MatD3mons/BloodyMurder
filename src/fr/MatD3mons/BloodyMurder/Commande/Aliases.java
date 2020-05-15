package fr.MatD3mons.BloodyMurder.Commande;

import java.util.ArrayList;
import java.util.Collections;

public class Aliases {

    public static ArrayList<String> join = new ArrayList<>(Collections.singletonList("join"));
    public static ArrayList<String> Leave = new ArrayList<>(Collections.singletonList("leave"));
    public static ArrayList<String> help = new ArrayList<>(Collections.singletonList("help"));
    public static ArrayList<String> setend = new ArrayList<>(Collections.singletonList("setend"));
    public static ArrayList<String> setgame = new ArrayList<>(Collections.singletonList("sendgame"));
    public static ArrayList<String> Addor = new ArrayList<>(Collections.singletonList("addor"));
    public static ArrayList<String> setwait = new ArrayList<>(Collections.singletonList("setwait"));
    public static ArrayList<String> Create = new ArrayList<>(Collections.singletonList("create"));
    public static ArrayList<String> AddSpawn = new ArrayList<>(Collections.singletonList("addspawn"));
    public static ArrayList<String> SetLobby = new ArrayList<>(Collections.singletonList("setlobby"));
    public static ArrayList<String> Admin = new ArrayList<>(Collections.singletonList("admin"));

    private static transient Aliases i = new Aliases();
}
