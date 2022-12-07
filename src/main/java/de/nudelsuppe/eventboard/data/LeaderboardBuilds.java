package de.nudelsuppe.eventboard.data;

public class LeaderboardBuilds {
    /*"id": 50,
"message": "1048534843312574484",
"judge_msg": "1048534844352778250",
"location": "Mischa stinks",
"A": 7,
"B": 6,
"base_points": true,
"images": [
"https://cdn.discordapp.com/attachments/1046797691180560434/1048534832591949824/q7tgoq6bxe391.webp"
],
"builder_id": "260449532750331905",
"judges": [
"260449532750331905",
"804386907244462141"
]*/
    private int id;
    private String message;
    private String judge_msg;
    private String location;
    private int A;
    private int B;
    private boolean base_points;
    private String[] images;
    private String builder_id;
    private String[] judges;

    public LeaderboardBuilds() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJudge_msg() {
        return judge_msg;
    }

    public void setJudge_msg(String judge_msg) {
        this.judge_msg = judge_msg;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getA() {
        return A;
    }

    public void setA(int a) {
        A = a;
    }

    public int getB() {
        return B;
    }

    public void setB(int b) {
        B = b;
    }

    public boolean isBase_points() {
        return base_points;
    }

    public void setBase_points(boolean base_points) {
        this.base_points = base_points;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getBuilder_id() {
        return builder_id;
    }

    public void setBuilder_id(String builder_id) {
        this.builder_id = builder_id;
    }

    public String[] getJudges() {
        return judges;
    }

    public void setJudges(String[] judges) {
        this.judges = judges;
    }
}
