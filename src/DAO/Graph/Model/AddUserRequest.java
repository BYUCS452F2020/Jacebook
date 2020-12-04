package DAO.Graph.Model;

public class AddUserRequest extends AddTokenRequest {
    public String alias;
    public String name;
    public String salt;
    public String photo;
    public String hash;
}
