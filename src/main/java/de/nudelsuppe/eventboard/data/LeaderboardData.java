package de.nudelsuppe.eventboard.data;

public class LeaderboardData {
    private LeaderboardUser[] users;
    private LeaderboardBuilds[] builds;
    private double points;

    public LeaderboardData() {
    }

    public LeaderboardUser[] getUsers() {
        return users;
    }

    public void setUsers(LeaderboardUser[] users) {
        this.users = users;
    }

    public LeaderboardUser getUser(String minecraft_id) {
        for(LeaderboardUser user: users) {
            if(user.getMinecraft_id().equals(minecraft_id)) return user;
        }
        return null;
    }

    public LeaderboardBuilds[] getBuilds() {
        return builds;
    }

    public void setBuilds(LeaderboardBuilds[] builds) {
        this.builds = builds;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }
}
