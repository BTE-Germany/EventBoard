package de.nudelsuppe.eventboard.data;

public class LeaderboardData {
    private LeaderboardUser[] users;
    private LeaderboardBuilds[] builds;
    private int points;

    public LeaderboardData() {
    }

    public LeaderboardUser[] getUsers() {
        return users;
    }

    public void setUsers(LeaderboardUser[] users) {
        this.users = users;
    }

    public LeaderboardBuilds[] getBuilds() {
        return builds;
    }

    public void setBuilds(LeaderboardBuilds[] builds) {
        this.builds = builds;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
