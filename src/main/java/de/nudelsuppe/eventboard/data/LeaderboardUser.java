package de.nudelsuppe.eventboard.data;

public class LeaderboardUser {
    private String id;
    private String minecraft_id;
    private boolean banned;
    private double points;

    public LeaderboardUser() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMinecraft_id() {
        return minecraft_id;
    }

    public void setMinecraft_id(String minecraft_id) {
        this.minecraft_id = minecraft_id;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }
}
