package entity;

public class PhongChieuPhim {
    private String maPhong;
    private String tenPhong;
    private int soLuongGhe;

    public PhongChieuPhim() {
    }

    public PhongChieuPhim(String maPhong, String tenPhong, int soLuongGhe) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.soLuongGhe = soLuongGhe;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public int getSoLuongGhe() {
        return soLuongGhe;
    }

    public void setSoLuongGhe(int soLuongGhe) {
        this.soLuongGhe = soLuongGhe;
    }

    @Override
    public String toString() {
        return tenPhong;
    }
}

