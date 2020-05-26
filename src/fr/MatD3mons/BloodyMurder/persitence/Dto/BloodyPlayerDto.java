package fr.MatD3mons.BloodyMurder.persitence.Dto;

import net.bloodybattle.bloodykvs.api.BloodyDto;

public class BloodyPlayerDto extends BloodyDto{

    private int Totaltekill;
    private int argent;
    private int win;
    private int lose;
    private String grade;

    public BloodyPlayerDto() {}

    public int getTotaltekill() { return Totaltekill; }
    public void setTotaltekill(int totaltekill) { Totaltekill = totaltekill; }

    public int getArgent() { return argent; }
    public void setArgent(int argent) { this.argent = argent; }

    public int getWin() { return win; }
    public void setWin(int win) { this.win = win; }

    public int getLose() { return lose; }
    public void setLose(int lose) { this.lose = lose; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    @Override
    public int dataVersion() {
        return 0;
    }

    @Override
    public String uniqueCollectionName() {
        return "bloodyMurder_bloodyplayer";
    }
}
