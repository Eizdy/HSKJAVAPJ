package entity;

public class LoaiPhim {
    private String maLoai;
    private String tenLoai;
    private String moTa;


    public LoaiPhim(String maLoai, String tenLoai, String moTa) {
		super();
		this.maLoai = maLoai;
		this.tenLoai = tenLoai;
		this.moTa = moTa;
	}

	public LoaiPhim() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

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

    @Override
    public String toString() {
        return tenLoai;
    }
}
