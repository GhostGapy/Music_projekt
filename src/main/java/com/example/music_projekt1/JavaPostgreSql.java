package com.example.music_projekt1;

import javafx.scene.control.Alert;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaPostgreSql {
    public static void Register(String userName, String userPassword1) throws NoSuchAlgorithmException {
        String url = "jdbc:postgresql://ep-purple-breeze-177741.eu-central-1.aws.neon.tech/neondb";
        String user = "GhostGapy";
        String password = "G4XZhDPTB0WC";

        String username = userName;
        String pass1 = PasswordHasher.hashPassword(userPassword1);

        String query="INSERT INTO users(username, password1) VALUES(?, ?)";

        try(Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement pst = con.prepareStatement(query)){

            pst.setString(1, username);
            pst.setString(2, pass1);
            pst.executeUpdate();
            System.out.println("Successfully created.");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful Registered");
            alert.setHeaderText("You have successfully registered");
            alert.setContentText("You can now login");
            alert.showAndWait();
        }
        catch (SQLException ex){

            Logger lgr = Logger.getLogger(JavaPostgreSql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("There was an error");
            alert.setContentText("Please try again");
            alert.showAndWait();

        }
    }

    public static boolean freeUsername(String userName) throws NoSuchAlgorithmException {
        String url = "jdbc:postgresql://ep-purple-breeze-177741.eu-central-1.aws.neon.tech/neondb";
        String user = "GhostGapy";
        String password = "G4XZhDPTB0WC";

        String username = userName;

        String query = "SELECT username FROM users WHERE username = ?";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            return rs.next();
        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(JavaPostgreSql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("There was an error");
            alert.setContentText("Please try again");
            alert.showAndWait();
            return false;
        }
    }

    public static boolean Login(String userName, String userPassword1) throws NoSuchAlgorithmException {
        String url = "jdbc:postgresql://ep-purple-breeze-177741.eu-central-1.aws.neon.tech/neondb";
        String user = "GhostGapy";
        String password = "G4XZhDPTB0WC";

        String username = userName;

        String query="SELECT password1 FROM users WHERE username = ?";

        try(Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement pst = con.prepareStatement(query)){

            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String correctPassword = rs.getString("password1");
                String pass1 = PasswordHasher.hashPassword(userPassword1);
                if (pass1.equals(correctPassword))
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successful Login");
                    alert.setHeaderText("You have successfully logged in");
                    alert.setContentText("You can now continue");
                    alert.showAndWait();

                    return true;
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Passwords do not match");
                    alert.setContentText("Please try again");
                    alert.showAndWait();
                    return false;
                }
            } else {
                // No matching user was found
                System.out.println("No matching user was found");

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("No matching user was found");
                alert.setContentText("Please try again");
                alert.showAndWait();
                return false;
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            return false;

        }

    }
}
