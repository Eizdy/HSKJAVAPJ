package entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HoaDon {
    private String maHoaDon;
    private KhachHang khachHang;
    private LocalDateTime ngayLap;
    private List<ChiTietHoaDon> danhSachChiTiet;
    private double tongTien;

    public HoaDon() {
        this.danhSachChiTiet = new ArrayList<>();
        this.ngayLap = LocalDateTime.now();
    }

    public HoaDon(String maHoaDon, KhachHang khachHang) {
        this.maHoaDon = maHoaDon;
        this.khachHang = khachHang;
        this.ngayLap = LocalDateTime.now();
        this.danhSachChiTiet = new ArrayList<>();
        this.tongTien = 0;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public LocalDateTime getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDateTime ngayLap) {
        this.ngayLap = ngayLap;
    }

    public List<ChiTietHoaDon> getDanhSachChiTiet() {
        return danhSachChiTiet;
    }

    public void setDanhSachChiTiet(List<ChiTietHoaDon> danhSachChiTiet) {
        this.danhSachChiTiet = danhSachChiTiet;
        tinhTongTien();
    }

    public double getTongTien() {
        return tongTien;
    }

    private void tinhTongTien() {
        tongTien = 0;
        for (ChiTietHoaDon ct : danhSachChiTiet) {
            tongTien += ct.getGiaVe();
        }
    }

    public void themChiTiet(ChiTietHoaDon chiTiet) {
        danhSachChiTiet.add(chiTiet);
        tongTien += chiTiet.getGiaVe();
    }

    @Override
    public String toString() {
        return "Hóa đơn: " + maHoaDon + ", Khách hàng: " + khachHang.getHoTen() +
                ", Ngày: " + ngayLap + ", Tổng tiền: " + tongTien + " VND";
    }
}

