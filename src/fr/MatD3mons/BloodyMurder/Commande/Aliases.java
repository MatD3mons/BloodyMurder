package fr.MatD3mons.BloodyMurder.Commande;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Aliases {

    public static ArrayList<String> join = new ArrayList<>(Arrays.asList("join","j"));
    public static ArrayList<String> lobby = new ArrayList<>(Arrays.asList("lobby","l"));
    public static ArrayList<String> help = new ArrayList<>(Arrays.asList("help","h"));
    public static ArrayList<String> setend = new ArrayList<>(Collections.singletonList("setend"));
    public static ArrayList<String> setgame = new ArrayList<>(Collections.singletonList("sendgame"));
    public static ArrayList<String> setor = new ArrayList<>(Collections.singletonList("setor"));
    public static ArrayList<String> setwait = new ArrayList<>(Collections.singletonList("setwait"));

    private static transient Aliases i = new Aliases();
}
