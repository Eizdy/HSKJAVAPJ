package entity;

public class Ghe {
    private String maGhe;
    private String viTri; 
    private boolean trangThai;
    private PhongChieuPhim phong;
	public Ghe(String maGhe, String viTri, boolean trangThai, PhongChieuPhim phong) {
		super();
		this.maGhe = maGhe;
		this.viTri = viTri;
		this.trangThai = trangThai;
		this.phong = phong;
	}
	public Ghe() {
		super();
		// TODO Auto-generated constructor stub
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
	public boolean isTrangThai() {
		return trangThai;
	}
	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}
	public PhongChieuPhim getPhong() {
		return phong;
	}
	public void setPhong(PhongChieuPhim phong) {
		this.phong = phong;
	}

   
}

