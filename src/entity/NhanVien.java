package entity;

import java.time.LocalDate;

public class NhanVien {
    private String maNV;
    private String tenNV;
    private LocalDate ngaySinh;
    private String dienThoai;
    private String email;
    private String chucVu;
    private TaiKhoan tenTK;
	public NhanVien(String maNV, String tenNV, LocalDate ngaySinh, String dienThoai, String email, String chucVu,
			TaiKhoan tenTK) {
		super();
		this.maNV = maNV;
		this.tenNV = tenNV;
		this.ngaySinh = ngaySinh;
		this.dienThoai = dienThoai;
		this.email = email;
		this.chucVu = chucVu;
		this.tenTK = tenTK;
	}
	public NhanVien() {
		super();
	}

	public NhanVien(String maNV, String tenNV, LocalDate ngaySinh, String dienThoai, String email, String chucVu) {
		super();
		this.maNV = maNV;
		this.tenNV = tenNV;
		this.ngaySinh = ngaySinh;
		this.dienThoai = dienThoai;
		this.email = email;
		this.chucVu = chucVu;
	}
	public String getMaNV() {
		return maNV;
	}
	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}
	public String getTenNV() {
		return tenNV;
	}
	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}
	public LocalDate getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(LocalDate ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public String getDienThoai() {
		return dienThoai;
	}
	public void setDienThoai(String dienThoai) {
		this.dienThoai = dienThoai;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getChucVu() {
		return chucVu;
	}
	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}
	public TaiKhoan getTenTK() {
		return tenTK;
	}
	public void setTenTK(TaiKhoan tenTK) {
		this.tenTK = tenTK;
	}
	

	
}

