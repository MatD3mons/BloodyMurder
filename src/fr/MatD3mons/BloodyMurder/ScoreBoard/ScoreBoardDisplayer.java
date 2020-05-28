package fr.MatD3mons.BloodyMurder.ScoreBoard;

import fr.MatD3mons.BloodyMurder.BloodyMurder;
import fr.MatD3mons.BloodyMurder.Game.Game;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import fr.MatD3mons.BloodyMurder.utile.util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoreBoardDisplayer {

	public static void initialize() {
		new BukkitRunnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					if(Repository.BloodyPlayerContains(p))
						updateScoreBoard(p);
				}
			}
		}.runTaskTimer(BloodyMurder.instance, 0, 5);
	}

	public static void updateScoreBoard(Player p) {
		if(Repository.findBloodyPlayer(p).getGame() == null)
			updateScoreBoardlobby(Repository.findBloodyPlayer(p));
		else{
			switch (Repository.findBloodyPlayer(p).getGame().getMode()) {
				case WAITING:
					updateScoreBoardWaiting(Repository.findBloodyPlayer(p));
					break;
				case GAME:
					updateScoreBoardInGame(Repository.findBloodyPlayer(p));
					break;
				case END:
					updateScoreBoardEnd(Repository.findBloodyPlayer(p));
					break;
				case DISABLE:
					updateScoreBoardDisable(Repository.findBloodyPlayer(p));
					break;
			}
		}
	}

	public static void updateScoreBoardlobby(BloodyPlayer b) {
		int a = b.getArgent();
		int k = b.getTotaltekill();
		int m = b.getDeaths();
		float W = b.getWin();
		int L = b.getLose();
		int n = b.getWin() + b.getLose();
		float r = W;
		if ( n != 0)
			r =  W/(float) n;
		String[] elements = new String[] {
				"  §a§lBloodyMurder ",
				"",
				"§6Argent: §e"+a,
				" ",
				"§6Kills: §e"+k,
				"§6Deaths: §e"+m,
				"§6Ratio: §e"+r,
				"§6Parti: §e"+n,
				"   ",
				"§a§lBloodyBattle.net",
		};
		ScoreboardUtil.unrankedSidebarDisplay(b.playerInstance, elements);
	}

	public static void updateScoreBoardWaiting(BloodyPlayer b) {
		int time = b.getGame().getTime()+1;
		int limite = b.getGame().getLimite();
		int online = b.getGame().getSixe();
		String[] elements = new String[] {
				"  §c§lBloodyMurder ",
				"",
				" §cPlayers: §7"+online+" / "+limite,
				"   ",
				" §cStarting: §7" + time + "s",
				"     "
		};
		ScoreboardUtil.unrankedSidebarDisplay(b.playerInstance, elements);
	}

	@Deprecated
	public static void updateScoreBoardInGame(BloodyPlayer b) {
		int time = b.getGame().getTime()+1;
		String role = "";
		if(b.getRole() != null) {
			role = b.getRole().toString();
		}
		else{
			role = "Spectateur";
		}
		int g = b.getGold();
		int i = b.getGame().innocentleftsixe();
		String m = b.getGame().getName();
		int k = b.getKills();
		int s = b.getKills()*100+b.getGold()*25;
		String[] elements = new String[] {
				"  §4§lBloodyMurder ",
				"",
				"§cTime: §7" + time + "s",
				"§cInnocent left: §7"+ i,
				"§cRôle: §7"+role,
				"§cMap: §7"+m,
				"  ",
				"§ckills: §7"+k,
				"§cScore: §7"+s,
				"§cGold: §7"+g,
				"    "
		};
		ScoreboardUtil.unrankedSidebarDisplay(b.playerInstance, elements);
		b.getGame().setTeam(b.getPlayerInstance());
	}

	public static void updateScoreBoardEnd(BloodyPlayer b) {
		String m = b.getGame().getName();
		int k = b.getKills();
		int s = b.getKills()*100+b.getGold()*25;
		String[] elements = new String[] {
				"  §4§lBloodyMurder ",
				"",
				"§cMap: §7"+m,
				"  ",
				"§ckills: §7"+k,
				"§cScore: §7"+s,
				"    "
		};
		ScoreboardUtil.unrankedSidebarDisplay(b.playerInstance, elements);
	}

	public static void updateScoreBoardDisable(BloodyPlayer b) {

		String[] elements = new String[] {
				"§c§lBloodyMurder ",
				"§cErreur"
		};
		ScoreboardUtil.unrankedSidebarDisplay(b.playerInstance, elements);
	}
}
