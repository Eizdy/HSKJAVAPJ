CREATE DATABASE RCP;
GO
USE RCP;
GO

-- Create Tables
CREATE TABLE TaiKhoan (
    tenTK NVARCHAR(50) PRIMARY KEY,
    matKhau NVARCHAR(50)
);
GO

CREATE TABLE NhanVien (
    maNV NVARCHAR(10) PRIMARY KEY,
    tenNV NVARCHAR(100),
    ngaySinh DATE,
    dienThoai NVARCHAR(20),
    email NVARCHAR(100),
    chucVu NVARCHAR(50),
    tenTK NVARCHAR(50) FOREIGN KEY REFERENCES TaiKhoan(tenTK)
);
GO

CREATE TABLE KhachHang (
    maKH NVARCHAR(10) PRIMARY KEY,
    tenKH NVARCHAR(100)
);
GO

CREATE TABLE LoaiPhim (
    maLoai NVARCHAR(10) PRIMARY KEY,
    tenLoai NVARCHAR(100),
    moTa NVARCHAR(255)
);
GO

CREATE TABLE Phim (
    maPhim NVARCHAR(10) PRIMARY KEY,
    tenPhim NVARCHAR(100),
    thoiLuong INT,
    daoDien NVARCHAR(100),
    nuocSX NVARCHAR(50),
    moTa NVARCHAR(255),
    theLoai NVARCHAR(10) FOREIGN KEY REFERENCES LoaiPhim(maLoai),
    ngayKhoiChieu DATE,
    ngonNgu NVARCHAR(50),
    doTuoiGioiHan INT
);
GO

CREATE TABLE PhongChieuPhim (
    maPhong NVARCHAR(10) PRIMARY KEY,
    tenPhong NVARCHAR(100),
    trangThaiPhongChieu BIT,
    soLuongGhe INT
);
GO

CREATE TABLE Ghe (
    maGhe NVARCHAR(10) PRIMARY KEY,
    viTri NVARCHAR(10),
    trangThai BIT,
    phong NVARCHAR(10) FOREIGN KEY REFERENCES PhongChieuPhim(maPhong)
);
GO

CREATE TABLE LichChieuPhim (
    maLichChieu NVARCHAR(10) PRIMARY KEY,
    maPhim NVARCHAR(10) FOREIGN KEY REFERENCES Phim(maPhim),
    maPhong NVARCHAR(10) FOREIGN KEY REFERENCES PhongChieuPhim(maPhong),
    thoiGianChieu NVARCHAR(20),
    trangThai NVARCHAR(20),
    ngayChieu DATE
);
GO

CREATE TABLE VeXemPhim (
    maVe NVARCHAR(10) PRIMARY KEY,
    giaVe FLOAT,
    maGhe NVARCHAR(10) FOREIGN KEY REFERENCES Ghe(maGhe),
    maLichChieu NVARCHAR(10) FOREIGN KEY REFERENCES LichChieuPhim(maLichChieu),
    maKH NVARCHAR(10) FOREIGN KEY REFERENCES KhachHang(maKH),
    maNV NVARCHAR(10) FOREIGN KEY REFERENCES NhanVien(maNV)
);
GO

CREATE TABLE HoaDon (
    maHD NVARCHAR(10) PRIMARY KEY,
    maVe NVARCHAR(10) FOREIGN KEY REFERENCES VeXemPhim(maVe),
    maNV NVARCHAR(10) FOREIGN KEY REFERENCES NhanVien(maNV),
    ngayLap DATE,
    soLuong INT
);
GO

-- Insert TaiKhoan (10 accounts)
INSERT INTO TaiKhoan (tenTK, matKhau) VALUES
('admin', 'admin123'),
('nv01', 'pass01'),
('nv02', 'pass02'),
('nv03', 'pass03'),
('nv04', 'pass04'),
('nv05', 'pass05'),
('user01', 'userpass01'),
('user02', 'userpass02'),
('user03', 'userpass03'),
('user04', 'userpass04');
GO

-- Insert NhanVien (5 employees)
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, dienThoai, email, chucVu, tenTK) VALUES
('NV01', 'Nguyen Van A', '1990-05-10', '0901234567', 'a@gmail.com', 'Quan ly', 'admin'),
('NV02', 'Tran Thi B', '1992-09-20', '0902345678', 'b@gmail.com', 'Nhan vien ban ve', 'nv01'),
('NV03', 'Le Van C', '1995-12-05', '0903456789', 'c@gmail.com', 'Nhan vien ban ve', 'nv02'),
('NV04', 'Pham Thi D', '1988-03-15', '0904567890', 'd@gmail.com', 'Nhan vien ban ve', 'nv03'),
('NV05', 'Hoang Van E', '1993-07-25', '0905678901', 'e@gmail.com', 'Nhan vien ban ve', 'nv04');
GO

-- Insert KhachHang (50 customers)
INSERT INTO KhachHang (maKH, tenKH) VALUES
('KH01', 'Pham Minh Tuan'), ('KH02', 'Doan Thi Lan'), ('KH03', 'Hoang Gia Bao'),
('KH04', 'Nguyen Thi Mai'), ('KH05', 'Tran Van Nam'), ('KH06', 'Le Thi Hoa'),
('KH07', 'Vo Van Hung'), ('KH08', 'Bui Thi Hong'), ('KH09', 'Dang Van Long'),
('KH10', 'Phan Thi Thanh'), ('KH11', 'Nguyen Van Kiet'), ('KH12', 'Tran Thi Kim'),
('KH13', 'Le Van Phuc'), ('KH14', 'Ho Thi Thu'), ('KH15', 'Pham Van Hieu'),
('KH16', 'Nguyen Thi Dung'), ('KH17', 'Tran Van Bao'), ('KH18', 'Le Thi Tuyet'),
('KH19', 'Vo Van Duc'), ('KH20', 'Bui Van An'), ('KH21', 'Dang Thi Ngoc'),
('KH22', 'Phan Van Tai'), ('KH23', 'Nguyen Thi Lan'), ('KH24', 'Tran Van Minh'),
('KH25', 'Le Thi Cam'), ('KH26', 'Ho Van Tuan'), ('KH27', 'Pham Thi Huong'),
('KH28', 'Nguyen Van Phat'), ('KH29', 'Tran Thi Thao'), ('KH30', 'Le Van Quang'),
('KH31', 'Vo Thi My'), ('KH32', 'Bui Van Khoa'), ('KH33', 'Dang Thi Le'),
('KH34', 'Phan Van Hoang'), ('KH35', 'Nguyen Thi Xuan'), ('KH36', 'Tran Van Dat'),
('KH37', 'Le Thi Yen'), ('KH38', 'Ho Van Loi'), ('KH39', 'Pham Thi Quy'),
('KH40', 'Nguyen Van Thang'), ('KH41', 'Tran Thi Be'), ('KH42', 'Le Van Vinh'),
('KH43', 'Vo Thi Linh'), ('KH44', 'Bui Van Thien'), ('KH45', 'Dang Thi Phuong'),
('KH46', 'Phan Van Son'), ('KH47', 'Nguyen Thi Oanh'), ('KH48', 'Tran Van Khanh'),
('KH49', 'Le Thi Nhi'), ('KH50', 'Ho Van Phong');
GO

-- Insert LoaiPhim (6 genres)
INSERT INTO LoaiPhim (maLoai, tenLoai, moTa) VALUES
('LP01', 'Hanh dong', 'Phim co cac canh chien dau, ruot duoi gay can'),
('LP02', 'Hai', 'Phim mang tinh giai tri, hai huoc'),
('LP03', 'Tinh cam', 'Phim ve cac moi quan he tinh yeu'),
('LP04', 'Kinh di', 'Phim mang tinh kinh di, gay so hai'),
('LP05', 'Vien tuong', 'Phim khoa hoc vien tuong, tuong tuong tuong lai'),
('LP06', 'Tai lieu', 'Phim tai lieu, ghi chep su kien thuc te');
GO

-- Insert Phim (15 movies)
INSERT INTO Phim (maPhim, tenPhim, thoiLuong, daoDien, nuocSX, moTa, theLoai, ngayKhoiChieu, ngonNgu, doTuoiGioiHan) VALUES
('P01', 'Avengers: Endgame', 181, 'Anthony Russo', 'My', 'Biet doi sieu anh hung ket thuc', 'LP01', '2019-04-26', 'Tieng Anh', 13),
('P02', 'Em chua 18', 100, 'Le Thanh Son', 'Viet Nam', 'Hai huoc tuoi hoc tro', 'LP02', '2017-04-28', 'Tieng Viet', 16),
('P03', 'Titanic', 195, 'James Cameron', 'My', 'Chuyen tinh bi trang', 'LP03', '1997-12-19', 'Tieng Anh', 13),
('P04', 'The Conjuring', 112, 'James Wan', 'My', 'Phim kinh di sieu nhien', 'LP04', '2013-07-19', 'Tieng Anh', 18),
('P05', 'Interstellar', 169, 'Christopher Nolan', 'My', 'Du hanh vu tru', 'LP05', '2014-11-07', 'Tieng Anh', 13),
('P06', 'The Social Dilemma', 94, 'Jeff Orlowski', 'My', 'Tai lieu ve mang xa hoi', 'LP06', '2020-09-09', 'Tieng Anh', 13),
('P07', 'Fast & Furious 9', 143, 'Justin Lin', 'My', 'Hanh dong dua xe', 'LP01', '2021-06-25', 'Tieng Anh', 13),
('P08', 'Mr. Bean', 90, 'John Howard Davies', 'Anh', 'Hai huoc gia dinh', 'LP02', '1997-08-01', 'Tieng Anh', 7),
('P09', 'The Notebook', 123, 'Nick Cassavetes', 'My', 'Chuyen tinh lang man', 'LP03', '2004-06-25', 'Tieng Anh', 13),
('P10', 'It', 135, 'Andy Muschietti', 'My', 'Phim kinh di ve chu he', 'LP04', '2017-09-08', 'Tieng Anh', 18),
('P11', 'Dune', 155, 'Denis Villeneuve', 'My', 'Vien tuong tren sa mac', 'LP05', '2021-10-22', 'Tieng Anh', 13),
('P12', 'Planet Earth II', 300, 'David Attenborough', 'Anh', 'Tai lieu ve trai dat', 'LP06', '2016-11-06', 'Tieng Anh', 7),
('P13', 'Mission: Impossible - Fallout', 147, 'Christopher McQuarrie', 'My', 'Nhiem vu bat kha thi', 'LP01', '2018-07-27', 'Tieng Anh', 13),
('P14', 'The Hangover', 100, 'Todd Phillips', 'My', 'Hai huoc sau buoi tiec', 'LP02', '2009-06-05', 'Tieng Anh', 16),
('P15', 'La La Land', 128, 'Damien Chazelle', 'My', 'Chuyen tinh am nhac', 'LP03', '2016-12-09', 'Tieng Anh', 13);
GO

-- Insert PhongChieuPhim (5 rooms)
INSERT INTO PhongChieuPhim (maPhong, tenPhong, trangThaiPhongChieu, soLuongGhe) VALUES
('P01', 'Phong 1', 1, 50),
('P02', 'Phong 2', 1, 50),
('P03', 'Phong 3', 1, 50),
('P04', 'Phong 4', 1, 50),
('P05', 'Phong 5', 1, 50);
GO

-- Insert Ghe (50 seats per room, total 250 seats)
INSERT INTO Ghe (maGhe, viTri, trangThai, phong) VALUES
-- Room P01 (seats P01A1 to P01E10)
('P01A1', 'A1', 0, 'P01'), ('P01A2', 'A2', 0, 'P01'), ('P01A3', 'A3', 0, 'P01'), ('P01A4', 'A4', 0, 'P01'), ('P01A5', 'A5', 0, 'P01'),
('P01A6', 'A6', 0, 'P01'), ('P01A7', 'A7', 0, 'P01'), ('P01A8', 'A8', 0, 'P01'), ('P01A9', 'A9', 0, 'P01'), ('P01A10', 'A10', 0, 'P01'),
('P01B1', 'B1', 0, 'P01'), ('P01B2', 'B2', 0, 'P01'), ('P01B3', 'B3', 0, 'P01'), ('P01B4', 'B4', 0, 'P01'), ('P01B5', 'B5', 0, 'P01'),
('P01B6', 'B6', 0, 'P01'), ('P01B7', 'B7', 0, 'P01'), ('P01B8', 'B8', 0, 'P01'), ('P01B9', 'B9', 0, 'P01'), ('P01B10', 'B10', 0, 'P01'),
('P01C1', 'C1', 0, 'P01'), ('P01C2', 'C2', 0, 'P01'), ('P01C3', 'C3', 0, 'P01'), ('P01C4', 'C4', 0, 'P01'), ('P01C5', 'C5', 0, 'P01'),
('P01C6', 'C6', 0, 'P01'), ('P01C7', 'C7', 0, 'P01'), ('P01C8', 'C8', 0, 'P01'), ('P01C9', 'C9', 0, 'P01'), ('P01C10', 'C10', 0, 'P01'),
('P01D1', 'D1', 0, 'P01'), ('P01D2', 'D2', 0, 'P01'), ('P01D3', 'D3', 0, 'P01'), ('P01D4', 'D4', 0, 'P01'), ('P01D5', 'D5', 0, 'P01'),
('P01D6', 'D6', 0, 'P01'), ('P01D7', 'D7', 0, 'P01'), ('P01D8', 'D8', 0, 'P01'), ('P01D9', 'D9', 0, 'P01'), ('P01D10', 'D10', 0, 'P01'),
('P01E1', 'E1', 0, 'P01'), ('P01E2', 'E2', 0, 'P01'), ('P01E3', 'E3', 0, 'P01'), ('P01E4', 'E4', 0, 'P01'), ('P01E5', 'E5', 0, 'P01'),
('P01E6', 'E6', 0, 'P01'), ('P01E7', 'E7', 0, 'P01'), ('P01E8', 'E8', 0, 'P01'), ('P01E9', 'E9', 0, 'P01'), ('P01E10', 'E10', 0, 'P01'),
-- Room P02 (seats P02A1 to P02E10)
('P02A1', 'A1', 0, 'P02'), ('P02A2', 'A2', 0, 'P02'), ('P02A3', 'A3', 0, 'P02'), ('P02A4', 'A4', 0, 'P02'), ('P02A5', 'A5', 0, 'P02'),
('P02A6', 'A6', 0, 'P02'), ('P02A7', 'A7', 0, 'P02'), ('P02A8', 'A8', 0, 'P02'), ('P02A9', 'A9', 0, 'P02'), ('P02A10', 'A10', 0, 'P02'),
('P02B1', 'B1', 0, 'P02'), ('P02B2', 'B2', 0, 'P02'), ('P02B3', 'B3', 0, 'P02'), ('P02B4', 'B4', 0, 'P02'), ('P02B5', 'B5', 0, 'P02'),
('P02B6', 'B6', 0, 'P02'), ('P02B7', 'B7', 0, 'P02'), ('P02B8', 'B8', 0, 'P02'), ('P02B9', 'B9', 0, 'P02'), ('P02B10', 'B10', 0, 'P02'),
('P02C1', 'C1', 0, 'P02'), ('P02C2', 'C2', 0, 'P02'), ('P02C3', 'C3', 0, 'P02'), ('P02C4', 'C4', 0, 'P02'), ('P02C5', 'C5', 0, 'P02'),
('P02C6', 'C6', 0, 'P02'), ('P02C7', 'C7', 0, 'P02'), ('P02C8', 'C8', 0, 'P02'), ('P02C9', 'C9', 0, 'P02'), ('P02C10', 'C10', 0, 'P02'),
('P02D1', 'D1', 0, 'P02'), ('P02D2', 'D2', 0, 'P02'), ('P02D3', 'D3', 0, 'P02'), ('P02D4', 'D4', 0, 'P02'), ('P02D5', 'D5', 0, 'P02'),
('P02D6', 'D6', 0, 'P02'), ('P02D7', 'D7', 0, 'P02'), ('P02D8', 'D8', 0, 'P02'), ('P02D9', 'D9', 0, 'P02'), ('P02D10', 'D10', 0, 'P02'),
('P02E1', 'E1', 0, 'P02'), ('P02E2', 'E2', 0, 'P02'), ('P02E3', 'E3', 0, 'P02'), ('P02E4', 'E4', 0, 'P02'), ('P02E5', 'E5', 0, 'P02'),
('P02E6', 'E6', 0, 'P02'), ('P02E7', 'E7', 0, 'P02'), ('P02E8', 'E8', 0, 'P02'), ('P02E9', 'E9', 0, 'P02'), ('P02E10', 'E10', 0, 'P02'),
-- Room P03 (seats P03A1 to P03E10)
('P03A1', 'A1', 0, 'P03'), ('P03A2', 'A2', 0, 'P03'), ('P03A3', 'A3', 0, 'P03'), ('P03A4', 'A4', 0, 'P03'), ('P03A5', 'A5', 0, 'P03'),
('P03A6', 'A6', 0, 'P03'), ('P03A7', 'A7', 0, 'P03'), ('P03A8', 'A8', 0, 'P03'), ('P03A9', 'A9', 0, 'P03'), ('P03A10', 'A10', 0, 'P03'),
('P03B1', 'B1', 0, 'P03'), ('P03B2', 'B2', 0, 'P03'), ('P03B3', 'B3', 0, 'P03'), ('P03B4', 'B4', 0, 'P03'), ('P03B5', 'B5', 0, 'P03'),
('P03B6', 'B6', 0, 'P03'), ('P03B7', 'B7', 0, 'P03'), ('P03B8', 'B8', 0, 'P03'), ('P03B9', 'B9', 0, 'P03'), ('P03B10', 'B10', 0, 'P03'),
('P03C1', 'C1', 0, 'P03'), ('P03C2', 'C2', 0, 'P03'), ('P03C3', 'C3', 0, 'P03'), ('P03C4', 'C4', 0, 'P03'), ('P03C5', 'C5', 0, 'P03'),
('P03C6', 'C6', 0, 'P03'), ('P03C7', 'C7', 0, 'P03'), ('P03C8', 'C8', 0, 'P03'), ('P03C9', 'C9', 0, 'P03'), ('P03C10', 'C10', 0, 'P03'),
('P03D1', 'D1', 0, 'P03'), ('P03D2', 'D2', 0, 'P03'), ('P03D3', 'D3', 0, 'P03'), ('P03D4', 'D4', 0, 'P03'), ('P03D5', 'D5', 0, 'P03'),
('P03D6', 'D6', 0, 'P03'), ('P03D7', 'D7', 0, 'P03'), ('P03D8', 'D8', 0, 'P03'), ('P03D9', 'D9', 0, 'P03'), ('P03D10', 'D10', 0, 'P03'),
('P03E1', 'E1', 0, 'P03'), ('P03E2', 'E2', 0, 'P03'), ('P03E3', 'E3', 0, 'P03'), ('P03E4', 'E4', 0, 'P03'), ('P03E5', 'E5', 0, 'P03'),
('P03E6', 'E6', 0, 'P03'), ('P03E7', 'E7', 0, 'P03'), ('P03E8', 'E8', 0, 'P03'), ('P03E9', 'E9', 0, 'P03'), ('P03E10', 'E10', 0, 'P03'),
-- Room P04 (seats P04A1 to P04E10)
('P04A1', 'A1', 0, 'P04'), ('P04A2', 'A2', 0, 'P04'), ('P04A3', 'A3', 0, 'P04'), ('P04A4', 'A4', 0, 'P04'), ('P04A5', 'A5', 0, 'P04'),
('P04A6', 'A6', 0, 'P04'), ('P04A7', 'A7', 0, 'P04'), ('P04A8', 'A8', 0, 'P04'), ('P04A9', 'A9', 0, 'P04'), ('P04A10', 'A10', 0, 'P04'),
('P04B1', 'B1', 0, 'P04'), ('P04B2', 'B2', 0, 'P04'), ('P04B3', 'B3', 0, 'P04'), ('P04B4', 'B4', 0, 'P04'), ('P04B5', 'B5', 0, 'P04'),
('P04B6', 'B6', 0, 'P04'), ('P04B7', 'B7', 0, 'P04'), ('P04B8', 'B8', 0, 'P04'), ('P04B9', 'B9', 0, 'P04'), ('P04B10', 'B10', 0, 'P04'), -- Added missing trangThai (0)
('P04C1', 'C1', 0, 'P04'), ('P04C2', 'C2', 0, 'P04'), ('P04C3', 'C3', 0, 'P04'), ('P04C4', 'C4', 0, 'P04'), ('P04C5', 'C5', 0, 'P04'),
('P04C6', 'C6', 0, 'P04'), ('P04C7', 'C7', 0, 'P04'), ('P04C8', 'C8', 0, 'P04'), ('P04C9', 'C9', 0, 'P04'), ('P04C10', 'C10', 0, 'P04'),
('P04D1', 'D1', 0, 'P04'), ('P04D2', 'D2', 0, 'P04'), ('P04D3', 'D3', 0, 'P04'), ('P04D4', 'D4', 0, 'P04'), ('P04D5', 'D5', 0, 'P04'),
('P04D6', 'D6', 0, 'P04'), ('P04D7', 'D7', 0, 'P04'), ('P04D8', 'D8', 0, 'P04'), ('P04D9', 'D9', 0, 'P04'), ('P04D10', 'D10', 0, 'P04'),
('P04E1', 'E1', 0, 'P04'), ('P04E2', 'E2', 0, 'P04'), ('P04E3', 'E3', 0, 'P04'), ('P04E4', 'E4', 0, 'P04'), ('P04E5', 'E5', 0, 'P04'),
('P04E6', 'E6', 0, 'P04'), ('P04E7', 'E7', 0, 'P04'), ('P04E8', 'E8', 0, 'P04'), ('P04E9', 'E9', 0, 'P04'), ('P04E10', 'E10', 0, 'P04'),
-- Room P05 (seats P05A1 to P05E10)
('P05A1', 'A1', 0, 'P05'), ('P05A2', 'A2', 0, 'P05'), ('P05A3', 'A3', 0, 'P05'), ('P05A4', 'A4', 0, 'P05'), ('P05A5', 'A5', 0, 'P05'),
('P05A6', 'A6', 0, 'P05'), ('P05A7', 'A7', 0, 'P05'), ('P05A8', 'A8', 0, 'P05'), ('P05A9', 'A9', 0, 'P05'), ('P05A10', 'A10', 0, 'P05'),
('P05B1', 'B1', 0, 'P05'), ('P05B2', 'B2', 0, 'P05'), ('P05B3', 'B3', 0, 'P05'), ('P05B4', 'B4', 0, 'P05'), ('P05B5', 'B5', 0, 'P05'),
('P05B6', 'B6', 0, 'P05'), ('P05B7', 'B7', 0, 'P05'), ('P05B8', 'B8', 0, 'P05'), ('P05B9', 'B9', 0, 'P05'), ('P05B10', 'B10', 0, 'P05'),
('P05C1', 'C1', 0, 'P05'), ('P05C2', 'C2', 0, 'P05'), ('P05C3', 'C3', 0, 'P05'), ('P05C4', 'C4', 0, 'P05'), ('P05C5', 'C5', 0, 'P05'),
('P05C6', 'C6', 0, 'P05'), ('P05C7', 'C7', 0, 'P05'), ('P05C8', 'C8', 0, 'P05'), ('P05C9', 'C9', 0, 'P05'), ('P05C10', 'C10', 0, 'P05'),
('P05D1', 'D1', 0, 'P05'), ('P05D2', 'D2', 0, 'P05'), ('P05D3', 'D3', 0, 'P05'), ('P05D4', 'D4', 0, 'P05'), ('P05D5', 'D5', 0, 'P05'),
('P05D6', 'D6', 0, 'P05'), ('P05D7', 'D7', 0, 'P05'), ('P05D8', 'D8', 0, 'P05'), ('P05D9', 'D9', 0, 'P05'), ('P05D10', 'D10', 0, 'P05'),
('P05E1', 'E1', 0, 'P05'), ('P05E2', 'E2', 0, 'P05'), ('P05E3', 'E3', 0, 'P05'), ('P05E4', 'E4', 0, 'P05'), ('P05E5', 'E5', 0, 'P05'),
('P05E6', 'E6', 0, 'P05'), ('P05E7', 'E7', 0, 'P05'), ('P05E8', 'E8', 0, 'P05'), ('P05E9', 'E9', 0, 'P05'), ('P05E10', 'E10', 0, 'P05');
GO
GO

-- Insert LichChieuPhim (20 showtimes)
INSERT INTO LichChieuPhim (maLichChieu, maPhim, maPhong, thoiGianChieu, trangThai, ngayChieu) VALUES
('LC01', 'P01', 'P01', '10:00', 'Dang chieu', '2025-04-27'),
('LC02', 'P02', 'P02', '10:00', 'Dang chieu', '2025-04-27'),
('LC03', 'P03', 'P03', '12:00', 'Dang chieu', '2025-04-27'),
('LC04', 'P04', 'P04', '14:00', 'Dang chieu', '2025-04-27'),
('LC05', 'P05', 'P05', '16:00', 'Dang chieu', '2025-04-27'),
('LC06', 'P06', 'P01', '18:00', 'Dang chieu', '2025-04-27'),
('LC07', 'P07', 'P02', '20:00', 'Dang chieu', '2025-04-27'),
('LC08', 'P08', 'P03', '10:00', 'Dang chieu', '2025-04-28'),
('LC09', 'P09', 'P04', '10:00', 'Dang chieu', '2025-04-28'),
('LC10', 'P10', 'P05', '12:00', 'Dang chieu', '2025-04-28'),
('LC11', 'P11', 'P01', '14:00', 'Dang chieu', '2025-04-28'),
('LC12', 'P12', 'P02', '16:00', 'Dang chieu', '2025-04-28'),
('LC13', 'P13', 'P03', '18:00', 'Dang chieu', '2025-04-28'),
('LC14', 'P14', 'P04', '20:00', 'Dang chieu', '2025-04-28'),
('LC15', 'P15', 'P05', '10:00', 'Dang chieu', '2025-04-29'),
('LC16', 'P01', 'P01', '12:00', 'Dang chieu', '2025-04-29'),
('LC17', 'P02', 'P02', '14:00', 'Dang chieu', '2025-04-29'),
('LC18', 'P03', 'P03', '16:00', 'Dang chieu', '2025-04-29'),
('LC19', 'P04', 'P04', '18:00', 'Dang chieu', '2025-04-29'),
('LC20', 'P05', 'P05', '20:00', 'Dang chieu', '2025-04-29');
GO

-- Insert VeXemPhim (200 tickets, 10 tickets per showtime)
INSERT INTO VeXemPhim (maVe, giaVe, maGhe, maLichChieu, maKH, maNV) VALUES
-- LC01 (P01, tickets V001 to V010, seats P01A1 to P01B5)
('V001', 50000, 'P01A1', 'LC01', 'KH01', 'NV01'),
('V002', 50000, 'P01A2', 'LC01', 'KH02', 'NV02'),
('V003', 50000, 'P01A3', 'LC01', 'KH03', 'NV03'),
('V004', 50000, 'P01A4', 'LC01', 'KH04', 'NV04'),
('V005', 50000, 'P01A5', 'LC01', 'KH05', 'NV05'),
('V006', 50000, 'P01B1', 'LC01', 'KH06', 'NV01'),
('V007', 50000, 'P01B2', 'LC01', 'KH07', 'NV02'),
('V008', 50000, 'P01B3', 'LC01', 'KH08', 'NV03'),
('V009', 50000, 'P01B4', 'LC01', 'KH09', 'NV04'),
('V010', 50000, 'P01B5', 'LC01', 'KH10', 'NV05'),
-- LC02 (P02, tickets V011 to V020, seats P02A1 to P02B5)
('V011', 50000, 'P02A1', 'LC02', 'KH11', 'NV01'),
('V012', 50000, 'P02A2', 'LC02', 'KH12', 'NV02'),
('V013', 50000, 'P02A3', 'LC02', 'KH13', 'NV03'),
('V014', 50000, 'P02A4', 'LC02', 'KH14', 'NV04'),
('V015', 50000, 'P02A5', 'LC02', 'KH15', 'NV05'),
('V016', 50000, 'P02B1', 'LC02', 'KH16', 'NV01'),
('V017', 50000, 'P02B2', 'LC02', 'KH17', 'NV02'),
('V018', 50000, 'P02B3', 'LC02', 'KH18', 'NV03'),
('V019', 50000, 'P02B4', 'LC02', 'KH19', 'NV04'),
('V020', 50000, 'P02B5', 'LC02', 'KH20', 'NV05'),
-- LC03 (P03, tickets V021 to V030, seats P03A1 to P03B5)
('V021', 50000, 'P03A1', 'LC03', 'KH21', 'NV01'),
('V022', 50000, 'P03A2', 'LC03', 'KH22', 'NV02'),
('V023', 50000, 'P03A3', 'LC03', 'KH23', 'NV03'),
('V024', 50000, 'P03A4', 'LC03', 'KH24', 'NV04'),
('V025', 50000, 'P03A5', 'LC03', 'KH25', 'NV05'),
('V026', 50000, 'P03B1', 'LC03', 'KH26', 'NV01'),
('V027', 50000, 'P03B2', 'LC03', 'KH27', 'NV02'),
('V028', 50000, 'P03B3', 'LC03', 'KH28', 'NV03'),
('V029', 50000, 'P03B4', 'LC03', 'KH29', 'NV04'),
('V030', 50000, 'P03B5', 'LC03', 'KH30', 'NV05'),
-- LC04 (P04, tickets V031 to V040, seats P04A1 to P04B5)
('V031', 50000, 'P04A1', 'LC04', 'KH31', 'NV01'),
('V032', 50000, 'P04A2', 'LC04', 'KH32', 'NV02'),
('V033', 50000, 'P04A3', 'LC04', 'KH33', 'NV03'),
('V034', 50000, 'P04A4', 'LC04', 'KH34', 'NV04'),
('V035', 50000, 'P04A5', 'LC04', 'KH35', 'NV05'),
('V036', 50000, 'P04B1', 'LC04', 'KH36', 'NV01'),
('V037', 50000, 'P04B2', 'LC04', 'KH37', 'NV02'),
('V038', 50000, 'P04B3', 'LC04', 'KH38', 'NV03'),
('V039', 50000, 'P04B4', 'LC04', 'KH39', 'NV04'),
('V040', 50000, 'P04B5', 'LC04', 'KH40', 'NV05'),
-- LC05 (P05, tickets V041 to V050, seats P05A1 to P05B5)
('V041', 50000, 'P05A1', 'LC05', 'KH41', 'NV01'),
('V042', 50000, 'P05A2', 'LC05', 'KH42', 'NV02'),
('V043', 50000, 'P05A3', 'LC05', 'KH43', 'NV03'),
('V044', 50000, 'P05A4', 'LC05', 'KH44', 'NV04'),
('V045', 50000, 'P05A5', 'LC05', 'KH45', 'NV05'),
('V046', 50000, 'P05B1', 'LC05', 'KH46', 'NV01'),
('V047', 50000, 'P05B2', 'LC05', 'KH47', 'NV02'),
('V048', 50000, 'P05B3', 'LC05', 'KH48', 'NV03'),
('V049', 50000, 'P05B4', 'LC05', 'KH49', 'NV04'),
('V050', 50000, 'P05B5', 'LC05', 'KH50', 'NV05'),
-- LC06 (P01, tickets V051 to V060, seats P01A1 to P01B5)
('V051', 50000, 'P01A1', 'LC06', 'KH01', 'NV01'),
('V052', 50000, 'P01A2', 'LC06', 'KH02', 'NV02'),
('V053', 50000, 'P01A3', 'LC06', 'KH03', 'NV03'),
('V054', 50000, 'P01A4', 'LC06', 'KH04', 'NV04'),
('V055', 50000, 'P01A5', 'LC06', 'KH05', 'NV05'),
('V056', 50000, 'P01B1', 'LC06', 'KH06', 'NV01'),
('V057', 50000, 'P01B2', 'LC06', 'KH07', 'NV02'),
('V058', 50000, 'P01B3', 'LC06', 'KH08', 'NV03'),
('V059', 50000, 'P01B4', 'LC06', 'KH09', 'NV04'),
('V060', 50000, 'P01B5', 'LC06', 'KH10', 'NV05'),
-- LC07 (P02, tickets V061 to V070, seats P02A1 to P02B5)
('V061', 50000, 'P02A1', 'LC07', 'KH11', 'NV01'),
('V062', 50000, 'P02A2', 'LC07', 'KH12', 'NV02'),
('V063', 50000, 'P02A3', 'LC07', 'KH13', 'NV03'),
('V064', 50000, 'P02A4', 'LC07', 'KH14', 'NV04'),
('V065', 50000, 'P02A5', 'LC07', 'KH15', 'NV05'),
('V066', 50000, 'P02B1', 'LC07', 'KH16', 'NV01'),
('V067', 50000, 'P02B2', 'LC07', 'KH17', 'NV02'),
('V068', 50000, 'P02B3', 'LC07', 'KH18', 'NV03'),
('V069', 50000, 'P02B4', 'LC07', 'KH19', 'NV04'),
('V070', 50000, 'P02B5', 'LC07', 'KH20', 'NV05'),
-- LC08 (P03, tickets V071 to V080, seats P03A1 to P03B5)
('V071', 50000, 'P03A1', 'LC08', 'KH21', 'NV01'),
('V072', 50000, 'P03A2', 'LC08', 'KH22', 'NV02'),
('V073', 50000, 'P03A3', 'LC08', 'KH23', 'NV03'),
('V074', 50000, 'P03A4', 'LC08', 'KH24', 'NV04'),
('V075', 50000, 'P03A5', 'LC08', 'KH25', 'NV05'),
('V076', 50000, 'P03B1', 'LC08', 'KH26', 'NV01'),
('V077', 50000, 'P03B2', 'LC08', 'KH27', 'NV02'),
('V078', 50000, 'P03B3', 'LC08', 'KH28', 'NV03'),
('V079', 50000, 'P03B4', 'LC08', 'KH29', 'NV04'),
('V080', 50000, 'P03B5', 'LC08', 'KH30', 'NV05'),
-- LC09 (P04, tickets V081 to V090, seats P04A1 to P04B5)
('V081', 50000, 'P04A1', 'LC09', 'KH31', 'NV01'),
('V082', 50000, 'P04A2', 'LC09', 'KH32', 'NV02'),
('V083', 50000, 'P04A3', 'LC09', 'KH33', 'NV03'),
('V084', 50000, 'P04A4', 'LC09', 'KH34', 'NV04'),
('V085', 50000, 'P04A5', 'LC09', 'KH35', 'NV05'),
('V086', 50000, 'P04B1', 'LC09', 'KH36', 'NV01'),
('V087', 50000, 'P04B2', 'LC09', 'KH37', 'NV02'),
('V088', 50000, 'P04B3', 'LC09', 'KH38', 'NV03'),
('V089', 50000, 'P04B4', 'LC09', 'KH39', 'NV04'),
('V090', 50000, 'P04B5', 'LC09', 'KH40', 'NV05'),
-- LC10 (P05, tickets V091 to V100, seats P05A1 to P05B5)
('V091', 50000, 'P05A1', 'LC10', 'KH41', 'NV01'),
('V092', 50000, 'P05A2', 'LC10', 'KH42', 'NV02'),
('V093', 50000, 'P05A3', 'LC10', 'KH43', 'NV03'),
('V094', 50000, 'P05A4', 'LC10', 'KH44', 'NV04'),
('V095', 50000, 'P05A5', 'LC10', 'KH45', 'NV05'),
('V096', 50000, 'P05B1', 'LC10', 'KH46', 'NV01'),
('V097', 50000, 'P05B2', 'LC10', 'KH47', 'NV02'),
('V098', 50000, 'P05B3', 'LC10', 'KH48', 'NV03'),
('V099', 50000, 'P05B4', 'LC10', 'KH49', 'NV04'),
('V100', 50000, 'P05B5', 'LC10', 'KH50', 'NV05'),
-- LC11 (P01, tickets V101 to V110, seats P01A1 to P01B5)
('V101', 50000, 'P01A1', 'LC11', 'KH01', 'NV01'),
('V102', 50000, 'P01A2', 'LC11', 'KH02', 'NV02'),
('V103', 50000, 'P01A3', 'LC11', 'KH03', 'NV03'),
('V104', 50000, 'P01A4', 'LC11', 'KH04', 'NV04'),
('V105', 50000, 'P01A5', 'LC11', 'KH05', 'NV05'),
('V106', 50000, 'P01B1', 'LC11', 'KH06', 'NV01'),
('V107', 50000, 'P01B2', 'LC11', 'KH07', 'NV02'),
('V108', 50000, 'P01B3', 'LC11', 'KH08', 'NV03'),
('V109', 50000, 'P01B4', 'LC11', 'KH09', 'NV04'),
('V110', 50000, 'P01B5', 'LC11', 'KH10', 'NV05'),
-- LC12 (P02, tickets V111 to V120, seats P02A1 to P02B5)
('V111', 50000, 'P02A1', 'LC12', 'KH11', 'NV01'),
('V112', 50000, 'P02A2', 'LC12', 'KH12', 'NV02'),
('V113', 50000, 'P02A3', 'LC12', 'KH13', 'NV03'),
('V114', 50000, 'P02A4', 'LC12', 'KH14', 'NV04'),
('V115', 50000, 'P02A5', 'LC12', 'KH15', 'NV05'),
('V116', 50000, 'P02B1', 'LC12', 'KH16', 'NV01'),
('V117', 50000, 'P02B2', 'LC12', 'KH17', 'NV02'),
('V118', 50000, 'P02B3', 'LC12', 'KH18', 'NV03'),
('V119', 50000, 'P02B4', 'LC12', 'KH19', 'NV04'),
('V120', 50000, 'P02B5', 'LC12', 'KH20', 'NV05'),
-- LC13 (P03, tickets V121 to V130, seats P03A1 to P03B5)
('V121', 50000, 'P03A1', 'LC13', 'KH21', 'NV01'),
('V122', 50000, 'P03A2', 'LC13', 'KH22', 'NV02'),
('V123', 50000, 'P03A3', 'LC13', 'KH23', 'NV03'),
('V124', 50000, 'P03A4', 'LC13', 'KH24', 'NV04'),
('V125', 50000, 'P03A5', 'LC13', 'KH25', 'NV05'),
('V126', 50000, 'P03B1', 'LC13', 'KH26', 'NV01'),
('V127', 50000, 'P03B2', 'LC13', 'KH27', 'NV02'),
('V128', 50000, 'P03B3', 'LC13', 'KH28', 'NV03'),
('V129', 50000, 'P03B4', 'LC13', 'KH29', 'NV04'),
('V130', 50000, 'P03B5', 'LC13', 'KH30', 'NV05'),
-- LC14 (P04, tickets V131 to V140, seats P04A1 to P04B5)
('V131', 50000, 'P04A1', 'LC14', 'KH31', 'NV01'),
('V132', 50000, 'P04A2', 'LC14', 'KH32', 'NV02'),
('V133', 50000, 'P04A3', 'LC14', 'KH33', 'NV03'),
('V134', 50000, 'P04A4', 'LC14', 'KH34', 'NV04'),
('V135', 50000, 'P04A5', 'LC14', 'KH35', 'NV05'),
('V136', 50000, 'P04B1', 'LC14', 'KH36', 'NV01'),
('V137', 50000, 'P04B2', 'LC14', 'KH37', 'NV02'),
('V138', 50000, 'P04B3', 'LC14', 'KH38', 'NV03'),
('V139', 50000, 'P04B4', 'LC14', 'KH39', 'NV04'),
('V140', 50000, 'P04B5', 'LC14', 'KH40', 'NV05'),
-- LC15 (P05, tickets V141 to V150, seats P05A1 to P05B5)
('V141', 50000, 'P05A1', 'LC15', 'KH41', 'NV01'),
('V142', 50000, 'P05A2', 'LC15', 'KH42', 'NV02'),
('V143', 50000, 'P05A3', 'LC15', 'KH43', 'NV03'),
('V144', 50000, 'P05A4', 'LC15', 'KH44', 'NV04'),
('V145', 50000, 'P05A5', 'LC15', 'KH45', 'NV05'),
('V146', 50000, 'P05B1', 'LC15', 'KH46', 'NV01'),
('V147', 50000, 'P05B2', 'LC15', 'KH47', 'NV02'),
('V148', 50000, 'P05B3', 'LC15', 'KH48', 'NV03'),
('V149', 50000, 'P05B4', 'LC15', 'KH49', 'NV04'),
('V150', 50000, 'P05B5', 'LC15', 'KH50', 'NV05'),
-- LC16 (P01, tickets V151 to V160, seats P01A1 to P01B5)
('V151', 50000, 'P01A1', 'LC16', 'KH01', 'NV01'),
('V152', 50000, 'P01A2', 'LC16', 'KH02', 'NV02'),
('V153', 50000, 'P01A3', 'LC16', 'KH03', 'NV03'),
('V154', 50000, 'P01A4', 'LC16', 'KH04', 'NV04'),
('V155', 50000, 'P01A5', 'LC16', 'KH05', 'NV05'),
('V156', 50000, 'P01B1', 'LC16', 'KH06', 'NV01'),
('V157', 50000, 'P01B2', 'LC16', 'KH07', 'NV02'),
('V158', 50000, 'P01B3', 'LC16', 'KH08', 'NV03'),
('V159', 50000, 'P01B4', 'LC16', 'KH09', 'NV04'),
('V160', 50000, 'P01B5', 'LC16', 'KH10', 'NV05'),
-- LC17 (P02, tickets V161 to V170, seats P02A1 to P02B5)
('V161', 50000, 'P02A1', 'LC17', 'KH11', 'NV01'),
('V162', 50000, 'P02A2', 'LC17', 'KH12', 'NV02'), -- Fixed typo: VHEAT to V162
('V163', 50000, 'P02A3', 'LC17', 'KH13', 'NV03'),
('V164', 50000, 'P02A4', 'LC17', 'KH14', 'NV04'),
('V165', 50000, 'P02A5', 'LC17', 'KH15', 'NV05'),
('V166', 50000, 'P02B1', 'LC17', 'KH16', 'NV01'),
('V167', 50000, 'P02B2', 'LC17', 'KH17', 'NV02'),
('V168', 50000, 'P02B3', 'LC17', 'KH18', 'NV03'),
('V169', 50000, 'P02B4', 'LC17', 'KH19', 'NV04'),
('V170', 50000, 'P02B5', 'LC17', 'KH20', 'NV05'),
-- LC18 (P03, tickets V171 to V180, seats P03A1 to P03B5)
('V171', 50000, 'P03A1', 'LC18', 'KH21', 'NV01'),
('V172', 50000, 'P03A2', 'LC18', 'KH22', 'NV02'),
('V173', 50000, 'P03A3', 'LC18', 'KH23', 'NV03'),
('V174', 50000, 'P03A4', 'LC18', 'KH24', 'NV04'),
('V175', 50000, 'P03A5', 'LC18', 'KH25', 'NV05'),
('V176', 50000, 'P03B1', 'LC18', 'KH26', 'NV01'),
('V177', 50000, 'P03B2', 'LC18', 'KH27', 'NV02'),
('V178', 50000, 'P03B3', 'LC18', 'KH28', 'NV03'),
('V179', 50000, 'P03B4', 'LC18', 'KH29', 'NV04'),
('V180', 50000, 'P03B5', 'LC18', 'KH30', 'NV05'),
-- LC19 (P04, tickets V181 to V190, seats P04A1 to P04B5)
('V181', 50000, 'P04A1', 'LC19', 'KH31', 'NV01'),
('V182', 50000, 'P04A2', 'LC19', 'KH32', 'NV02'),
('V183', 50000, 'P04A3', 'LC19', 'KH33', 'NV03'),
('V184', 50000, 'P04A4', 'LC19', 'KH34', 'NV04'),
('V185', 50000, 'P04A5', 'LC19', 'KH35', 'NV05'),
('V186', 50000, 'P04B1', 'LC19', 'KH36', 'NV01'),
('V187', 50000, 'P04B2', 'LC19', 'KH37', 'NV02'),
('V188', 50000, 'P04B3', 'LC19', 'KH38', 'NV03'),
('V189', 50000, 'P04B4', 'LC19', 'KH39', 'NV04'),
('V190', 50000, 'P04B5', 'LC19', 'KH40', 'NV05'),
-- LC20 (P05, tickets V191 to V200, seats P05A1 to P05B5)
('V191', 50000, 'P05A1', 'LC20', 'KH41', 'NV01'),
('V192', 50000, 'P05A2', 'LC20', 'KH42', 'NV02'),
('V193', 50000, 'P05A3', 'LC20', 'KH43', 'NV03'),
('V194', 50000, 'P05A4', 'LC20', 'KH44', 'NV04'),
('V195', 50000, 'P05A5', 'LC20', 'KH45', 'NV05'),
('V196', 50000, 'P05B1', 'LC20', 'KH46', 'NV01'),
('V197', 50000, 'P05B2', 'LC20', 'KH47', 'NV02'),
('V198', 50000, 'P05B3', 'LC20', 'KH48', 'NV03'),
('V199', 50000, 'P05B4', 'LC20', 'KH49', 'NV04'),
('V200', 50000, 'P05B5', 'LC20', 'KH50', 'NV05');
GO

-- Insert HoaDon (80 invoices for tickets V001 to V080, with soLuong)
INSERT INTO HoaDon (maHD, maVe, maNV, ngayLap, soLuong) VALUES
-- LC01 (V001 to V010, date 2025-04-27)
('HD01', 'V001', 'NV01', '2025-04-27', 1),
('HD02', 'V002', 'NV02', '2025-04-27', 1),
('HD03', 'V003', 'NV03', '2025-04-27', 1),
('HD04', 'V004', 'NV04', '2025-04-27', 1),
('HD05', 'V005', 'NV05', '2025-04-27', 1),
('HD06', 'V006', 'NV01', '2025-04-27', 1),
('HD07', 'V007', 'NV02', '2025-04-27', 1),
('HD08', 'V008', 'NV03', '2025-04-27', 1),
('HD09', 'V009', 'NV04', '2025-04-27', 1),
('HD10', 'V010', 'NV05', '2025-04-27', 1),
-- LC02 (V011 to V020, date 2025-04-27)
('HD11', 'V011', 'NV01', '2025-04-27', 1),
('HD12', 'V012', 'NV02', '2025-04-27', 1),
('HD13', 'V013', 'NV03', '2025-04-27', 1),
('HD14', 'V014', 'NV04', '2025-04-27', 1),
('HD15', 'V015', 'NV05', '2025-04-27', 1),
('HD16', 'V016', 'NV01', '2025-04-27', 1),
('HD17', 'V017', 'NV02', '2025-04-27', 1),
('HD18', 'V018', 'NV03', '2025-04-27', 1),
('HD19', 'V019', 'NV04', '2025-04-27', 1),
('HD20', 'V020', 'NV05', '2025-04-27', 1),
-- LC03 (V021 to V030, date 2025-04-27)
('HD21', 'V021', 'NV01', '2025-04-27', 1),
('HD22', 'V022', 'NV02', '2025-04-27', 1),
('HD23', 'V023', 'NV03', '2025-04-27', 1),
('HD24', 'V024', 'NV04', '2025-04-27', 1),
('HD25', 'V025', 'NV05', '2025-04-27', 1),
('HD26', 'V026', 'NV01', '2025-04-27', 1),
('HD27', 'V027', 'NV02', '2025-04-27', 1),
('HD28', 'V028', 'NV03', '2025-04-27', 1),
('HD29', 'V029', 'NV04', '2025-04-27', 1),
('HD30', 'V030', 'NV05', '2025-04-27', 1),
-- LC04 (V031 to V040, date 2025-04-27)
('HD31', 'V031', 'NV01', '2025-04-27', 1),
('HD32', 'V032', 'NV02', '2025-04-27', 1),
('HD33', 'V033', 'NV03', '2025-04-27', 1),
('HD34', 'V034', 'NV04', '2025-04-27', 1),
('HD35', 'V035', 'NV05', '2025-04-27', 1),
('HD36', 'V036', 'NV01', '2025-04-27', 1),
('HD37', 'V037', 'NV02', '2025-04-27', 1),
('HD38', 'V038', 'NV03', '2025-04-27', 1),
('HD39', 'V039', 'NV04', '2025-04-27', 1),
('HD40', 'V040', 'NV05', '2025-04-27', 1),
-- LC05 (V041 to V050, date 2025-04-27)
('HD41', 'V041', 'NV01', '2025-04-27', 1),
('HD42', 'V042', 'NV02', '2025-04-27', 1),
('HD43', 'V043', 'NV03', '2025-04-27', 1),
('HD44', 'V044', 'NV04', '2025-04-27', 1),
('HD45', 'V045', 'NV05', '2025-04-27', 1),
('HD46', 'V046', 'NV01', '2025-04-27', 1),
('HD47', 'V047', 'NV02', '2025-04-27', 1),
('HD48', 'V048', 'NV03', '2025-04-27', 1),
('HD49', 'V049', 'NV04', '2025-04-27', 1),
('HD50', 'V050', 'NV05', '2025-04-27', 1),
-- LC06 (V051 to V060, date 2025-04-27)
('HD51', 'V051', 'NV01', '2025-04-27', 1),
('HD52', 'V052', 'NV02', '2025-04-27', 1),
('HD53', 'V053', 'NV03', '2025-04-27', 1),
('HD54', 'V054', 'NV04', '2025-04-27', 1),
('HD55', 'V055', 'NV05', '2025-04-27', 1),
('HD56', 'V056', 'NV01', '2025-04-27', 1),
('HD57', 'V057', 'NV02', '2025-04-27', 1),
('HD58', 'V058', 'NV03', '2025-04-27', 1),
('HD59', 'V059', 'NV04', '2025-04-27', 1),
('HD60', 'V060', 'NV05', '2025-04-27', 1),
-- LC07 (V061 to V070, date 2025-04-27)
('HD61', 'V061', 'NV01', '2025-04-27', 1),
('HD62', 'V062', 'NV02', '2025-04-27', 1),
('HD63', 'V063', 'NV03', '2025-04-27', 1),
('HD64', 'V064', 'NV04', '2025-04-27', 1),
('HD65', 'V065', 'NV05', '2025-04-27', 1),
('HD66', 'V066', 'NV01', '2025-04-27', 1),
('HD67', 'V067', 'NV02', '2025-04-27', 1),
('HD68', 'V068', 'NV03', '2025-04-27', 1),
('HD69', 'V069', 'NV04', '2025-04-27', 1),
('HD70', 'V070', 'NV05', '2025-04-27', 1),
-- LC08 (V071 to V080, date 2025-04-28)
('HD71', 'V071', 'NV01', '2025-04-28', 1),
('HD72', 'V072', 'NV02', '2025-04-28', 1),
('HD73', 'V073', 'NV03', '2025-04-28', 1),
('HD74', 'V074', 'NV04', '2025-04-28', 1),
('HD75', 'V075', 'NV05', '2025-04-28', 1),
('HD76', 'V076', 'NV01', '2025-04-28', 1),
('HD77', 'V077', 'NV02', '2025-04-28', 1),
('HD78', 'V078', 'NV03', '2025-04-28', 1),
('HD79', 'V079', 'NV04', '2025-04-28', 1),
('HD80', 'V080', 'NV05', '2025-04-28', 1);
GO