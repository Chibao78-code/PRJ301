package dao;

import dto.BookDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utlis.DBUtils;

public class BookDAO implements IDAO<BookDTO, String> {

    @Override
    public boolean create(BookDTO entity) {
        String sql = "INSERT INTO [tblBooks] ([BookID], [Title], [Author], [PublishYear], [Price], [Quantity]) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getBookID());
            ps.setString(2, entity.getTitle());
            ps.setString(3, entity.getAuthor());
            ps.setInt(4, entity.getPublishYear());
            ps.setDouble(5, entity.getPrice());
            ps.setInt(6, entity.getQuantity());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error in BookDAO.create: " + ex.toString());
            return false;
        }
    }

    @Override
    public List<BookDTO> readAll() {
        return null; // Implement if needed
    }

    @Override
    public BookDTO readById(String id) {
        return null; // Implement if needed
    }

    @Override
    public boolean update(BookDTO entity) {
        return false; // Implement if needed
    }

    @Override
    public boolean delete(String id) {
        return false; // Implement if needed
    }

    @Override
    public List<BookDTO> search(String searchTerm) {
        return null; // Implement if needed
    }

    public List<BookDTO> searchByTitle(String searchTerm) {
        String sql = "SELECT * FROM [tblBooks] WHERE [title] LIKE ?";
        List<BookDTO> list = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            ps.setString(1, "%" + searchTerm + "%");
            while (rs.next()) {
                BookDTO b = new BookDTO(
                    rs.getString("BookID"),
                    rs.getString("Title"),
                    rs.getString("Author"),
                    rs.getInt("PublishYear"),
                    rs.getDouble("Price"),
                    rs.getInt("Quantity")
                );
                list.add(b);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error in searchByTitle: " + ex.toString());
        }
        return list;
    }

    public List<BookDTO> searchByTitle2(String searchTerm) {
        String sql = "SELECT * FROM [tblBooks] WHERE [title] LIKE ? AND [Quantity] > 0";
        List<BookDTO> list = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            ps.setString(1, "%" + searchTerm + "%");
            while (rs.next()) {
                BookDTO b = new BookDTO(
                    rs.getString("BookID"),
                    rs.getString("Title"),
                    rs.getString("Author"),
                    rs.getInt("PublishYear"),
                    rs.getDouble("Price"),
                    rs.getInt("Quantity")
                );
                list.add(b);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error in searchByTitle2: " + ex.toString());
        }
        return list;
    }

    public boolean updateQuantityToZero(String id) {
        String sql = "UPDATE [tblBooks] SET [Quantity] = 0 WHERE [BookID] = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error in updateQuantityToZero: " + ex.toString());
            return false;
        }
    }
}