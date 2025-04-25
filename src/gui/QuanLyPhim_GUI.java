package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class QuanLyPhim_GUI extends JFrame {
    private JTable tblPhim;
    private DefaultTableModel modelPhim;
    private JTextField txtMaPhim, txtTenPhim;
    private JCheckBox chkMaPhim, chkTenPhim;
    private JButton btnTimKiem, btnXoaTrang, btnThem, btnXoa, btnSua, btnHienTatCa;

    public QuanLyPhim_GUI() {
        setTitle("Phim - Quản lý Rạp Chiếu Phim");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        UIManager.put("Label.foreground", Color.WHITE);
        UIManager.put("TitledBorder.titleColor", Color.LIGHT_GRAY);

        add(createHeader(), BorderLayout.NORTH);
        add(createSidebar(), BorderLayout.WEST);
        add(createMainContent(), BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);
    }

    private JPanel createHeader() {
        JLabel lbl = new JLabel("HỆ THỐNG QUẢN LÝ RẠP CHIẾU PHIM", SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lbl.setForeground(Color.WHITE);
        lbl.setOpaque(true);
        lbl.setBackground(new Color(30, 30, 30));
        lbl.setBorder(new MatteBorder(0, 0, 2, 0, new Color(64, 64, 64)));

        JPanel header = new JPanel(new BorderLayout());
        header.add(lbl);
        return header;
    }

    private JPanel createSidebar() {
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(new Color(25, 25, 25));
        menu.setPreferredSize(new Dimension(180, 0));

        String[] items = {"Trang chủ", "Phim", "Suất chiếu", "Thức ăn", "Nhân viên", "Hoá đơn", "Bán vé", "Bán thức ăn", "Thống kê", "Đăng xuất"};

        for (String item : items) {
            JButton btn = new JButton(item);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btn.setForeground(item.equals("Đăng xuất") ? Color.RED : Color.WHITE);
            btn.setBackground(new Color(40, 40, 40));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(60, 60, 60));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(40, 40, 40));
                }
            });

            btn.addActionListener(e -> {
                dispose();
                switch (item) {
                	case "Trang chủ" -> new TrangChuRapChieuPhim_GUI().setVisible(true);
                	case "Phim" -> new QuanLyPhim_GUI().setVisible(true);
                	//case "Suất chiếu" -> new QuanLySuatChieu_GUI().setVisible(true);
                	case "Nhân viên" -> new QuanLyNhanVien_GUI().setVisible(true);
                	//case "Hoá đơn" -> new QuanLyHoaDon_GUI().setVisible(true);
                	//case "Bán vé" -> new BanVe_GUI().setVisible(true);
                	//case "Thống kê" -> new ThongKe_GUI().setVisible(true);
                	case "Đăng xuất" -> System.exit(0);
                	default -> {}
                }
            });

            menu.add(Box.createVerticalStrut(10));
            menu.add(btn);
        }

        return menu;
    }

    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(new EmptyBorder(10, 10, 10, 10));
        main.setBackground(new Color(45, 45, 45));

        // Center - Table
        String[] cols = {"Mã phim", "Tên phim", "Thể loại", "Thời lượng", "Đạo diễn", "Diễn viên", "Ngày khởi chiếu", "Mô tả", "Ngôn ngữ", "Độ tuổi", "Hình ảnh", "Giá vé"};
        modelPhim = new DefaultTableModel(cols, 0);
        tblPhim = new JTable(modelPhim);
        tblPhim.setBackground(new Color(60, 60, 60));
        tblPhim.setForeground(Color.WHITE);
        tblPhim.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblPhim.setRowHeight(22);
        JScrollPane scroll = new JScrollPane(tblPhim);
        scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Danh sách phim"));
        main.add(scroll, BorderLayout.CENTER);

        // East - Tìm kiếm
        JPanel east = new JPanel();
        east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
        east.setBackground(new Color(45, 45, 45));
        east.setPreferredSize(new Dimension(250, 0));

        JLabel lblTieuDe = new JLabel("TRA CỨU", SwingConstants.CENTER);
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTieuDe.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTieuDe.setForeground(Color.WHITE);

        chkMaPhim = new JCheckBox("Tra cứu theo mã phim");
        chkMaPhim.setBackground(new Color(45, 45, 45));
        chkMaPhim.setForeground(Color.WHITE);
        chkMaPhim.setSelected(true);

        txtMaPhim = new JTextField();
        txtMaPhim.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtMaPhim.setBorder(BorderFactory.createTitledBorder("Mã phim"));
        txtMaPhim.setBackground(new Color(60, 60, 60));
        txtMaPhim.setForeground(Color.WHITE);

        chkTenPhim = new JCheckBox("Tra cứu theo tên phim");
        chkTenPhim.setBackground(new Color(45, 45, 45));
        chkTenPhim.setForeground(Color.WHITE);

        txtTenPhim = new JTextField();
        txtTenPhim.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtTenPhim.setBorder(BorderFactory.createTitledBorder("Tên phim"));
        txtTenPhim.setBackground(new Color(60, 60, 60));
        txtTenPhim.setForeground(Color.WHITE);

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionPanel.setBackground(new Color(45, 45, 45));
        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setBackground(new Color(0, 120, 215));
        btnTimKiem.setForeground(Color.WHITE);
        btnXoaTrang = new JButton("Xoá trắng");
        btnXoaTrang.setBackground(new Color(200, 50, 50));
        btnXoaTrang.setForeground(Color.WHITE);
        actionPanel.add(btnTimKiem);
        actionPanel.add(btnXoaTrang);

        east.add(lblTieuDe);
        east.add(Box.createVerticalStrut(10));
        east.add(chkMaPhim);
        east.add(txtMaPhim);
        east.add(Box.createVerticalStrut(10));
        east.add(chkTenPhim);
        east.add(txtTenPhim);
        east.add(Box.createVerticalStrut(15));
        east.add(actionPanel);

        main.add(east, BorderLayout.EAST);

        // South - Các nút chức năng
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        south.setBackground(new Color(45, 45, 45));
        btnThem = new JButton("Thêm");
        btnThem.setBackground(new Color(0, 120, 215));
        btnThem.setForeground(Color.WHITE);
        btnThem.addActionListener(e -> showAddMoviePopup());

        btnXoa = new JButton("Xoá");
        btnXoa.setBackground(new Color(200, 50, 50));
        btnXoa.setForeground(Color.WHITE);

        btnSua = new JButton("Sửa");
        btnSua.setBackground(new Color(0, 120, 215));
        btnSua.setForeground(Color.WHITE);

        btnHienTatCa = new JButton("Hiện tất cả");
        btnHienTatCa.setBackground(new Color(0, 120, 215));
        btnHienTatCa.setForeground(Color.WHITE);

        south.add(btnThem);
        south.add(btnXoa);
        south.add(btnSua);
        south.add(btnHienTatCa);
        main.add(south, BorderLayout.SOUTH);

        return main;
    }

    private void showAddMoviePopup() {
        JDialog dialog = new JDialog(this, "Thêm Phim Mới", true);
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        // North: Title
        JLabel lblTitle = new JLabel("THÊM PHIM MỚI", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(new Color(30, 30, 30));
        lblTitle.setBorder(new EmptyBorder(10, 0, 10, 0));
        dialog.add(lblTitle, BorderLayout.NORTH);

        // Center: Text fields
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(6, 2, 10, 10));
        textPanel.setBackground(new Color(45, 45, 45));
        textPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextField txtMaPhim = createTextField("Mã phim");
        JTextField txtTenPhim = createTextField("Tên phim");
        JTextField txtTheLoai = createTextField("Thể loại");
        JTextField txtThoiLuong = createTextField("Thời lượng (phút)");
        JTextField txtDaoDien = createTextField("Đạo diễn");
        JTextField txtDienVien = createTextField("Diễn viên");
        JTextField txtNgayKhoiChieu = createTextField("Ngày khởi chiếu (yyyy-MM-dd)");
        JTextField txtMoTa = createTextField("Mô tả");
        JTextField txtNgonNgu = createTextField("Ngôn ngữ");
        JTextField txtDoTuoiGioiHan = createTextField("Độ tuổi giới hạn");
        JTextField txtHinhAnh = createTextField("Đường dẫn ảnh");
        JTextField txtGiaVe = createTextField("Giá vé (VND)");

        textPanel.add(txtMaPhim);
        textPanel.add(txtTenPhim);
        textPanel.add(txtTheLoai);
        textPanel.add(txtThoiLuong);
        textPanel.add(txtDaoDien);
        textPanel.add(txtDienVien);
        textPanel.add(txtNgayKhoiChieu);
        textPanel.add(txtMoTa);
        textPanel.add(txtNgonNgu);
        textPanel.add(txtDoTuoiGioiHan);
        textPanel.add(txtHinhAnh);
        textPanel.add(txtGiaVe);

        JScrollPane scrollPane = new JScrollPane(textPanel);
        scrollPane.setBackground(new Color(45, 45, 45));
        dialog.add(scrollPane, BorderLayout.CENTER);

        // South: Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(45, 45, 45));

        JButton btnSave = new JButton("Lưu");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setBackground(new Color(0, 120, 215));
        btnSave.setForeground(Color.WHITE);
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.addActionListener(e -> {
            try {
                String maPhim = txtMaPhim.getText().trim();
                String tenPhim = txtTenPhim.getText().trim();
                String theLoai = txtTheLoai.getText().trim();
                String thoiLuongStr = txtThoiLuong.getText().trim();
                String daoDien = txtDaoDien.getText().trim();
                String dienVien = txtDienVien.getText().trim();
                String ngayKhoiChieuStr = txtNgayKhoiChieu.getText().trim();
                String moTa = txtMoTa.getText().trim();
                String ngonNgu = txtNgonNgu.getText().trim();
                String doTuoiGioiHanStr = txtDoTuoiGioiHan.getText().trim();
                String hinhAnh = txtHinhAnh.getText().trim();
                String giaVeStr = txtGiaVe.getText().trim();

                // Validate required fields
                if (maPhim.isEmpty() || tenPhim.isEmpty() || thoiLuongStr.isEmpty() ||
                    ngayKhoiChieuStr.isEmpty() || doTuoiGioiHanStr.isEmpty() || giaVeStr.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đầy đủ các trường bắt buộc!");
                    return;
                }

                // Parse numeric and date fields
                int thoiLuong = Integer.parseInt(thoiLuongStr);
                LocalDate ngayKhoiChieu = LocalDate.parse(ngayKhoiChieuStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                int doTuoiGioiHan = Integer.parseInt(doTuoiGioiHanStr);
                double giaVe = Double.parseDouble(giaVeStr);

                // Add to table
                modelPhim.addRow(new Object[]{
                    maPhim, tenPhim, theLoai, thoiLuong, daoDien, dienVien,
                    ngayKhoiChieu.toString(), moTa, ngonNgu, doTuoiGioiHan, hinhAnh, giaVe
                });

                JOptionPane.showMessageDialog(dialog, "Đã thêm phim: " + tenPhim);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập số hợp lệ cho Thời lượng, Độ tuổi giới hạn hoặc Giá vé!");
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(dialog, "Ngày khởi chiếu phải có định dạng yyyy-MM-dd!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Đã xảy ra lỗi: " + ex.getMessage());
            }
        });

        JButton btnCancel = new JButton("Hủy");
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.setBackground(new Color(200, 50, 50));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private JTextField createTextField(String title) {
        JTextField field = new JTextField();
        field.setBorder(BorderFactory.createTitledBorder(title));
        field.setBackground(new Color(60, 60, 60));
        field.setForeground(Color.WHITE);
        return field;
    }

    private JPanel createFooter() {
        JLabel lbl = new JLabel("Nhóm Quản lý Rạp Chiếu Phim", SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setForeground(Color.LIGHT_GRAY);
        lbl.setBackground(new Color(30, 30, 30));
        lbl.setOpaque(true);
        lbl.setBorder(new EmptyBorder(10, 0, 10, 0));

        JPanel footer = new JPanel(new BorderLayout());
        footer.add(lbl);
        return footer;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new QuanLyPhim_GUI().setVisible(true));
    }
}