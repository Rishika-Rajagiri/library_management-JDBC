package library;

import library.db.DBConnection;
import java.sql.Connection;


public class Main {
    public static void main(String[] args){
        try(Connection con=DBConnection.getConnection()){
            System.out.println("Connected to database successfully");
        }catch(Exception e){
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }
}
