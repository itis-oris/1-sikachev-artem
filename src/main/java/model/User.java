package model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter@Setter
public class User {

    private Integer id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone_number;
    private String place_of_living;
    private String role;

    public User(){}

    public User(String userName, String role, String firstName, String lastName, String email, String password, String phone_number, String place_of_living) {
        this.userName = userName;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
        this.place_of_living = place_of_living;
    }

    public User(Integer id, String userName, String role, String firstName, String lastName, String email, String password, String phone_number, String place_of_living) {
        this.id = id;
        this.userName = userName;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
        this.place_of_living = place_of_living;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getFirstName(), user.getFirstName())
                && Objects.equals(getLastName(), user.getLastName()) && Objects.equals(getEmail(), user.getEmail())
                && Objects.equals(getUserName(), user.getUserName()) && Objects.equals(getPassword(), user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserName(), getFirstName(), getLastName(), getEmail(), getPassword());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("The user with username:").append(userName);
        sb.append("Real name: ").append(firstName);
        if (lastName != null) {
            sb.append(", lastname: ").append(lastName);
        }
        return sb.toString();
    }
}
