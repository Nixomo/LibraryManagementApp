package m19.core;

import java.io.Serializable;

public class User implements Serializable{
    private int _id;
    private boolean _isActive;
    private String _name;
    private String _email;
    private UserBehavior _behavior;

    private static final long serialVersionUID = 201901101342L;

    public User(String name, String email, int id) {
        _id = id;
        _isActive = true;
        _name = name;
        _email = email;
        _behavior = UserBehavior.NORMAL;
    }

    public String getName() {
        return _name;
    }

    public String getLowerName() {
        return _name.toLowerCase();
    }

    protected boolean isActive() {
        return _isActive;
    }

    public String getDescription() {
        String active = "";
        if (_isActive)
            active = "ACTIVO";
        else
            active = "SUSPENSO";

        return _id + " - " + _name + " - " + _email + " - " + _behavior.toString() + " - " + active;
    }

    protected void getNotifications() {
        
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof User) {
            User other = (User)o;
            return other.hashCode() == this.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return _id;
    }
}