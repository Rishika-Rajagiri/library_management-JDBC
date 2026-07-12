package library.dao;
import library.db.DBConnection;
import library.model.Member;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    //CREATE - add a new member
    public void addMember(Member member) throws SQLException{
        String sql= "INSERT INTO Members (name, email, phone) VALUES (?, ?, ?)";
        try(Connection con=DBConnection.getConnection();
        PreparedStatement ps=con.prepareStatement(sql)){
            ps.setString(1,member.getName());
            ps.setString(2, member.getEmail());
            ps.setString(3, member.getPhone());
            ps.executeUpdate();
        }
    }

    //READ - get a member by the id
    public Member getMemberById(int memberId) throws SQLException {
        String sql = "SELECT * FROM Members WHERE member_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, memberId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Member(
                            rs.getInt("member_id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getDate("join_date").toLocalDate()
                    );
                }
            }
        }
        return null;
    }

    //READ - get all members
    public List<Member> getAllMembers() throws SQLException{
        List<Member> members=new ArrayList<>();
        String sql="SELECT* FROM Members";
        try(Connection con=DBConnection.getConnection();
        PreparedStatement ps=con.prepareStatement(sql);
        ResultSet rs=ps.executeQuery()){
            while(rs.next()) {
                members.add(new Member(
                        rs.getInt("member_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getDate("join_date").toLocalDate()
                ));
            }
        }
        return members;
    }

    //UPDATE - UPDATE AN EXISTING MEMBER
    public void updateMember(Member member) throws SQLException {
        String sql = "UPDATE Members SET name=?, email=?, phone=? WHERE member_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, member.getName());
            ps.setString(2, member.getEmail());
            ps.setString(3, member.getPhone());
            ps.setInt(4, member.getMemberId());
            ps.executeUpdate();
        }
    }
        //DELETE - delete a member
       public void deleteMember(int memberId) throws SQLException {
            String sql = "DELETE FROM Members WHERE member_id = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, memberId);
                stmt.executeUpdate();
            }
        }

    }

