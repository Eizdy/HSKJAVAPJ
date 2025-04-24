package entity;

public class VeXemPhim {
    private String maVe;
    private LichChieuPhim lichChieu;
    private Ghe ghe;
    private String tenKhachHang;

    public VeXemPhim() {
    }

    public VeXemPhim(String maVe, LichChieuPhim lichChieu, Ghe ghe, String tenKhachHang) {
        this.maVe = maVe;
        this.lichChieu = lichChieu;
        this.ghe = ghe;
        this.tenKhachHang = tenKhachHang;
    }

    public String getMaVe() {
        return maVe;
    }

    public void setMaVe(String maVe) {
        this.maVe = maVe;
    }

    public LichChieuPhim getLichChieu() {
        return lichChieu;
    }

    public void setLichChieu(LichChieuPhim lichChieu) {
        this.lichChieu = lichChieu;
    }

    public Ghe getGhe() {
        return ghe;
    }

    public void setGhe(Ghe ghe) {
        this.ghe = ghe;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    @Override
    public String toString() {
        return "Vé: " + maVe + ", Khách: " + tenKhachHang +
                ", Ghế: " + ghe.getViTri() + ", " + lichChieu.toString();
    }
}

