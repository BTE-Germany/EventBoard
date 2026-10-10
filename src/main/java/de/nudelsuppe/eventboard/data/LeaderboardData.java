package de.nudelsuppe.eventboard.data;

public class LeaderboardData {
    private LeaderboardUser[] users;
    private LeaderboardBuilds[] builds;
    private double points;

    public LeaderboardData() {
    }

    public static LeaderboardData empty() {
        LeaderboardData data = new LeaderboardData();
        data.setUsers(new LeaderboardUser[0]);
        data.setBuilds(new LeaderboardBuilds[0]);
        return data;
    }

    public LeaderboardUser[] getUsers() {
        return users;
    }

    public void setUsers(LeaderboardUser[] users) {
        this.users = users;
    }

    public LeaderboardUser getUser(String minecraft_id) {
        if (users == null) return null;
        for(LeaderboardUser user: users) {
            if(user != null && minecraft_id.equalsIgnoreCase(user.getMinecraft_id())) return user;
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
