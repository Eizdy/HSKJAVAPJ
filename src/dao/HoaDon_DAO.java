package dao;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.NhanVien;
import entity.VeXemPhim;
import entity.LichChieuPhim;
import entity.KhachHang;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class HoaDon_DAO {

    public boolean themHoaDon(HoaDon hoaDon) {
        String sql = "INSERT INTO HoaDon (maHD, ngayLap, soLuong, maVe, maNV) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hoaDon.getMaHoaDon());
            stmt.setDate(2, Date.valueOf(hoaDon.getNgayLap()));
            stmt.setInt(3, hoaDon.getSoLuong());
            stmt.setString(4, hoaDon.getVe().getMaVe());
            stmt.setString(5, hoaDon.getMaNV().getMaNV());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Hóa đơn " + hoaDon.getMaHoaDon() + " đã được lưu vào cơ sở dữ liệu.");
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thêm hóa đơn: " + e.getMessage(), e);
        }
    }

    public HoaDon timHoaDonTheoMa(String maHD) {
        if (maHD == null || maHD.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống.");
        }

        String sql = "SELECT hd.*, v.giaVe, v.maLichChieu, v.maKH " +
                     "FROM HoaDon hd " +
                     "LEFT JOIN VeXemPhim v ON hd.maVe = v.maVe " +
                     "WHERE hd.maHD = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maHD);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                VeXemPhim ve = new VeXemPhim();
                ve.setMaVe(rs.getString("maVe"));
                ve.setGiaVe(rs.getDouble("giaVe"));

                LichChieuPhim lichChieu = new LichChieuPhim();
                lichChieu.setMaLichChieu(rs.getString("maLichChieu"));
                ve.setLichChieu(lichChieu);

                String maKH = rs.getString("maKH");
                if (maKH != null) {
                    KhachHang khachHang = new KhachHang();
                    khachHang.setMaKhachHang(maKH);
                    ve.setKhachHang(khachHang);
                }

                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("maNV"));

                return new HoaDon(
                        rs.getString("maHD"),
                        rs.getDate("ngayLap").toLocalDate(),
                        rs.getInt("soLuong"),
                        ve,
                        nv
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tìm hóa đơn: " + e.getMessage(), e);
        }
        return null;
    }

    public List<HoaDon> layTatCaHoaDon() {
        List<HoaDon> ds = new ArrayList<>();
        String sql = "SELECT hd.*, v.giaVe, v.maLichChieu, v.maKH " +
                     "FROM HoaDon hd " +
                     "LEFT JOIN VeXemPhim v ON hd.maVe = v.maVe";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                VeXemPhim ve = new VeXemPhim();
                ve.setMaVe(rs.getString("maVe"));
                ve.setGiaVe(rs.getDouble("giaVe"));

                LichChieuPhim lichChieu = new LichChieuPhim();
                lichChieu.setMaLichChieu(rs.getString("maLichChieu"));
                ve.setLichChieu(lichChieu);

                String maKH = rs.getString("maKH");
                if (maKH != null) {
                    KhachHang khachHang = new KhachHang();
                    khachHang.setMaKhachHang(maKH);
                    ve.setKhachHang(khachHang);
                }

                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("maNV"));

                HoaDon hd = new HoaDon(
                        rs.getString("maHD"),
                        rs.getDate("ngayLap").toLocalDate(),
                        rs.getInt("soLuong"),
                        ve,
                        nv
                );
                ds.add(hd);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách hóa đơn: " + e.getMessage(), e);
        }
        return ds;
    }

    public List<HoaDon> getHoaDonTheoNgay(String ngayLap) {
        if (ngayLap == null || ngayLap.trim().isEmpty()) {
            throw new IllegalArgumentException("Ngày lập không được để trống.");
        }

        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(ngayLap);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ngày lập không đúng định dạng (YYYY-MM-DD): " + ngayLap);
        }

        List<HoaDon> ds = new ArrayList<>();
        String sql = "SELECT hd.*, v.giaVe, v.maLichChieu, v.maKH " +
                     "FROM HoaDon hd " +
                     "LEFT JOIN VeXemPhim v ON hd.maVe = v.maVe " +
                     "WHERE hd.ngayLap = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(parsedDate));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                VeXemPhim ve = new VeXemPhim();
                ve.setMaVe(rs.getString("maVe"));
                ve.setGiaVe(rs.getDouble("giaVe"));

                LichChieuPhim lichChieu = new LichChieuPhim();
                lichChieu.setMaLichChieu(rs.getString("maLichChieu"));
                ve.setLichChieu(lichChieu);

                String maKH = rs.getString("maKH");
                if (maKH != null) {
                    KhachHang khachHang = new KhachHang();
                    khachHang.setMaKhachHang(maKH);
                    ve.setKhachHang(khachHang);
                }

                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("maNV"));

                HoaDon hd = new HoaDon(
                        rs.getString("maHD"),
                        rs.getDate("ngayLap").toLocalDate(),
                        rs.getInt("soLuong"),
                        ve,
                        nv
                );
                ds.add(hd);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy hóa đơn theo ngày: " + e.getMessage(), e);
        }
        return ds;
    }
    public List<HoaDon> getHoaDonTheoThang(int thang, int nam) {
        List<HoaDon> ds = new ArrayList<>();
        String sql = "SELECT hd.*, v.giaVe FROM HoaDon hd LEFT JOIN VeXemPhim v ON hd.maVe = v.maVe WHERE MONTH(ngayLap) = ? AND YEAR(ngayLap) = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, thang);
            stmt.setInt(2, nam);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                VeXemPhim ve = new VeXemPhim();
                ve.setMaVe(rs.getString("maVe"));
                ve.setGiaVe(rs.getDouble("giaVe"));

                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("maNV"));

                HoaDon hd = new HoaDon(
                        rs.getString("maHD"),
                        rs.getDate("ngayLap").toLocalDate(),
                        rs.getInt("soLuong"),
                        ve,
                        nv
                );
                ds.add(hd);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy hóa đơn theo tháng: " + e.getMessage(), e);
        }
        return ds;
    }
    
    public List<HoaDon> getHoaDonTheoKhoangNgay(LocalDate tuNgay, LocalDate denNgay) {
        List<HoaDon> ds = new ArrayList<>();
        String sql = "SELECT hd.*, v.giaVe FROM HoaDon hd LEFT JOIN VeXemPhim v ON hd.maVe = v.maVe WHERE ngayLap BETWEEN ? AND ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(tuNgay));
            stmt.setDate(2, Date.valueOf(denNgay));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                VeXemPhim ve = new VeXemPhim();
                ve.setMaVe(rs.getString("maVe"));
                ve.setGiaVe(rs.getDouble("giaVe"));

                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("maNV"));

                HoaDon hd = new HoaDon(
                        rs.getString("maHD"),
                        rs.getDate("ngayLap").toLocalDate(),
                        rs.getInt("soLuong"),
                        ve,
                        nv
                );
                ds.add(hd);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy hóa đơn theo khoảng ngày: " + e.getMessage(), e);
        }
        return ds;
    }
}