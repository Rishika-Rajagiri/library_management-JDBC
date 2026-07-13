package library.dao;
import library.db.DBConnection;
import library.model.Transaction;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class TransactionDAO {
    //CREATE - add a new transaction
    public void addTransaction(Transaction t) throws SQLException{
        String sql="INSERT INTO transactions(book_id,member_id,borrow_date,due_date,return_date,fine_amount) VALUES(?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, t.getBookId());
            ps.setInt(2, t.getMemberId());
            ps.setDate(3, Date.valueOf(t.getBorrowDate()));
            ps.setDate(4, Date.valueOf(t.getDueDate()));
            ps.setDate(5, t.getReturnDate() != null ? Date.valueOf(t.getReturnDate()) : null);
            ps.setBigDecimal(6, t.getFineAmount());
            ps.executeUpdate();
        }
    }

    // UPDATE - Mark a transaction as returned
    public void updateReturn(int transactionId, LocalDate returnDate, BigDecimal fine) throws SQLException {
        String sql = "UPDATE transactions SET return_date=?, fine_amount=? WHERE transaction_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(returnDate));
            ps.setBigDecimal(2, fine);
            ps.setInt(3, transactionId);
            ps.executeUpdate();
        }
    }

    // READ - Get one transaction by ID
    public Transaction getTransactionById(int transactionId) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE transaction_id = ?";
        try (Connection con= DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, transactionId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Date returnDate = rs.getDate("return_date");
                    return new Transaction(
                            rs.getInt("transaction_id"),
                            rs.getInt("book_id"),
                            rs.getInt("member_id"),
                            rs.getDate("borrow_date").toLocalDate(),
                            rs.getDate("due_date").toLocalDate(),
                            returnDate != null ? returnDate.toLocalDate() : null,
                            rs.getBigDecimal("fine_amount")
                    );
                }
            }
        }
        return null;
    }

    // READ - Get all transactions
    public List<Transaction> getAllTransactions() throws SQLException {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Date returnDate = rs.getDate("return_date");
                list.add(new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("book_id"),
                        rs.getInt("member_id"),
                        rs.getDate("borrow_date").toLocalDate(),
                        rs.getDate("due_date").toLocalDate(),
                        returnDate != null ? returnDate.toLocalDate() : null,
                        rs.getBigDecimal("fine_amount")
                ));
            }
        }
        return list;
    }

    // READ - Get all transactions for a specific member
    public List<Transaction> getTransactionsByMember(int memberId) throws SQLException {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE member_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps= con.prepareStatement(sql)) {
            ps.setInt(1, memberId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Date returnDate = rs.getDate("return_date");
                    list.add(new Transaction(
                            rs.getInt("transaction_id"),
                            rs.getInt("book_id"),
                            rs.getInt("member_id"),
                            rs.getDate("borrow_date").toLocalDate(),
                            rs.getDate("due_date").toLocalDate(),
                            returnDate != null ? returnDate.toLocalDate() : null,
                            rs.getBigDecimal("fine_amount")
                    ));
                }
            }
        }
        return list;
    }

    // DELETE - Remove a transaction record
    public void deleteTransaction(int transactionId) throws SQLException {
        String sql = "DELETE FROM transactions WHERE transaction_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, transactionId);
            ps.executeUpdate();
        }
    }
}

