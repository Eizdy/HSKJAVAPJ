package entity;

public class LoaiPhim {
    private String maLoai;
    private String tenLoai;
    private String moTa;

    // Constructor đầy đủ 3 tham số
    public LoaiPhim(String maLoai, String tenLoai, String moTa) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.moTa = moTa;
    }

    // Constructor chỉ có mã loại
    public LoaiPhim(String maLoai) {
        this.maLoai = maLoai;
    }

    // Constructor có mã loại và tên loại
    public LoaiPhim(String maLoai, String tenLoai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }

    // Constructor mặc định
    public LoaiPhim() {}

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    @Override
    public String toString() {
        return tenLoai;
    }
}
