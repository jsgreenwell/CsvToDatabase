package edu.fgcu.dataengineering;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
// just to check if these are found

public class BookStoreDB {

  //connect to database
  public void connect() {
    //connection URL
    String connectionURL = "jdbc:sqlite:src//Data//Bookstore.db;";

    //try connection and create Statement object
    try (Connection conn = DriverManager.getConnection(connectionURL);
        Statement statement = conn.createStatement();) {

      ResultSet resultSet = null;

      //insert json authors into database
      Gson gson = new Gson();
      JsonReader jread = new JsonReader(new FileReader("src/Data/authors.json"));
      AuthorParser[] authors = gson.fromJson(jread, AuthorParser[].class);

      for (var element : authors) {
        String authorName = element.getName();
        String authorEmail = element.getEmail();
        String authorUrl = element.getUrl();

        //sql query
        String addAuthor = "INSERT INTO author VALUES 'Phillip K. Dick', 'phillip@aol.com', 'phillipkdick.com';";
        //String addAuthor = "INSERT INTO author VALUES '" + authorName + "', '" + authorEmail + "', '"
        //  + authorUrl +"';";
        resultSet = statement.executeQuery(addAuthor);
      }

      while (resultSet.next()) {
        System.out.println("Inserted" + resultSet.getString(1));
      }

    } catch (SQLException | FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
