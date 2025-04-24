package entity;

public class ChiTietHoaDon {
    private String maChiTiet;
    private VeXemPhim ve;
    private double giaVe;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(String maChiTiet, VeXemPhim ve, double giaVe) {
        this.maChiTiet = maChiTiet;
        this.ve = ve;
        this.giaVe = giaVe;
    }

    public String getMaChiTiet() {
        return maChiTiet;
    }

    public void setMaChiTiet(String maChiTiet) {
        this.maChiTiet = maChiTiet;
    }

    public VeXemPhim getVe() {
        return ve;
    }

    public void setVe(VeXemPhim ve) {
        this.ve = ve;
    }

    public double getGiaVe() {
        return giaVe;
    }

    public void setGiaVe(double giaVe) {
        this.giaVe = giaVe;
    }

    @Override
    public String toString() {
        return "Chi tiết: " + ve.toString() + " - Giá: " + giaVe + " VND";
    }
}

