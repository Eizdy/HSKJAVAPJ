package entity;

public class KhachHang {
    private String maKhachHang;
    private String tenKH;
	public KhachHang(String maKhachHang, String tenKH) {
		super();
		this.maKhachHang = maKhachHang;
		this.tenKH = tenKH;
	}
	public KhachHang() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getMaKhachHang() {
		return maKhachHang;
	}
	public void setMaKhachHang(String maKhachHang) {
		this.maKhachHang = maKhachHang;
	}
	public String getTenKH() {
		return tenKH;
	}
	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}

    
}

