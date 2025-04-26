package dao;

import connectDB.ConnectDB;
import entity.LoaiPhim;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoaiPhim_DAO {
	
	public List<LoaiPhim> layTatCaLoaiPhim() {
        List<LoaiPhim> dsLoaiPhim = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT maLoai, tenLoai, moTa FROM LoaiPhim";

        try {
            conn = ConnectDB.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String maLoai = rs.getString("maLoai");
                String tenLoai = rs.getString("tenLoai");
                String moTa = rs.getString("moTa");
                dsLoaiPhim.add(new LoaiPhim(maLoai, tenLoai, moTa));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dsLoaiPhim;
    }

    public LoaiPhim timLoaiPhimTheoMa(String maLoai) {
        String sql = "SELECT * FROM LoaiPhim WHERE maLoai = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maLoai);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new LoaiPhim(rs.getString("maLoai"), rs.getString("tenLoai"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean themLoaiPhim(LoaiPhim loai) {
        String sql = "INSERT INTO LoaiPhim (maLoai, tenLoai) VALUES (?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, loai.getMaLoai());
            stmt.setString(2, loai.getTenLoai());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatLoaiPhim(LoaiPhim loai) {
        String sql = "UPDATE LoaiPhim SET tenLoai = ? WHERE maLoai = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, loai.getTenLoai());
            stmt.setString(2, loai.getMaLoai());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaLoaiPhim(String maLoai) {
        String sql = "DELETE FROM LoaiPhim WHERE maLoai = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maLoai);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
