package entity;

public class Ghe {
    private String maGhe;
    private String viTri; // Ví dụ: A1, B2,...
    private boolean daDat;

    public Ghe() {
    }

    public Ghe(String maGhe, String viTri, boolean daDat) {
        this.maGhe = maGhe;
        this.viTri = viTri;
        this.daDat = daDat;
    }

    public String getMaGhe() {
        return maGhe;
    }

    public void setMaGhe(String maGhe) {
        this.maGhe = maGhe;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public boolean isDaDat() {
        return daDat;
    }

    public void setDaDat(boolean daDat) {
        this.daDat = daDat;
    }

    @Override
    public String toString() {
        return viTri + (daDat ? " (Đã đặt)" : " (Còn trống)");
    }
}

