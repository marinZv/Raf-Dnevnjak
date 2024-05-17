package rs.raf.projekat_milan_marinkovic_7921rn.models;

public class User {

    private String username;
    private String password;
    private String email;
    private boolean logged;

    public User(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
        this.logged = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }
}
