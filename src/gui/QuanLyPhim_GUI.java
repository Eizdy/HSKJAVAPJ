//package gui;
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import javax.swing.border.MatteBorder;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;
//import dao.Phim_DAO;
//import entity.Phim;
//
//public class QuanLyPhim_GUI extends JFrame {
//    private JTable tblPhim;
//    private DefaultTableModel modelPhim;
//    private JTextField txtMaPhim, txtTenPhim;
//    private JCheckBox chkMaPhim, chkTenPhim;
//    private JButton btnTimKiem, btnXoaTrang, btnThem, btnXoa, btnSua, btnHienTatCa;
//    private Phim_DAO phimDAO = new Phim_DAO();
//
//    public QuanLyPhim_GUI() {
//        setTitle("Phim - Quản lý Rạp Chiếu Phim");
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setSize(1280, 800);
//        setLocationRelativeTo(null);
//        setLayout(new BorderLayout());
//
//        UIManager.put("Label.foreground", Color.WHITE);
//        UIManager.put("TitledBorder.titleColor", Color.LIGHT_GRAY);
//
//        add(createHeader(), BorderLayout.NORTH);
//        add(createSidebar(), BorderLayout.WEST);
//        add(createMainContent(), BorderLayout.CENTER);
//        add(createFooter(), BorderLayout.SOUTH);
//    }
//
//    private JPanel createHeader() {
//        JLabel lbl = new JLabel("HỆ THỐNG QUẢN LÝ RẠP CHIẾU PHIM", SwingConstants.CENTER);
//        lbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
//        lbl.setForeground(Color.WHITE);
//        lbl.setOpaque(true);
//        lbl.setBackground(new Color(30, 30, 30));
//        lbl.setBorder(new MatteBorder(0, 0, 2, 0, new Color(64, 64, 64)));
//
//        JPanel header = new JPanel(new BorderLayout());
//        header.add(lbl);
//        return header;
//    }
//
//    private JPanel createSidebar() {
//        JPanel menu = new JPanel();
//        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
//        menu.setBackground(new Color(25, 25, 25));
//        menu.setPreferredSize(new Dimension(180, 0));
//
//        String[] items = {"Trang chủ", "Phim", "Suất chiếu", "Thức ăn", "Nhân viên", "Hoá đơn", "Bán vé", "Bán thức ăn", "Thống kê", "Đăng xuất"};
//
//        for (String item : items) {
//            JButton btn = new JButton(item);
//            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
//            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//            btn.setForeground(item.equals("Đăng xuất") ? Color.RED : Color.WHITE);
//            btn.setBackground(new Color(40, 40, 40));
//            btn.setFocusPainted(false);
//            btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
//            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
//            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
//
//            btn.addMouseListener(new java.awt.event.MouseAdapter() {
//                public void mouseEntered(java.awt.event.MouseEvent evt) {
//                    btn.setBackground(new Color(60, 60, 60));
//                }
//
//                public void mouseExited(java.awt.event.MouseEvent evt) {
//                    btn.setBackground(new Color(40, 40, 40));
//                }
//            });
//
//            btn.addActionListener(e -> {
//                dispose();
//                switch (item) {
//
//                	case "Trang chủ" -> new TrangChuRapChieuPhim_GUI().setVisible(true);
//                	case "Phim" -> new QuanLyPhim_GUI().setVisible(true);
//                	//case "Suất chiếu" -> new QuanLySuatChieu_GUI().setVisible(true);
//                	case "Nhân viên" -> new QuanLyNhanVien_GUI().setVisible(true);
//                	//case "Hoá đơn" -> new QuanLyHoaDon_GUI().setVisible(true);
//                	//case "Bán vé" -> new BanVe_GUI().setVisible(true);
//                	//case "Thống kê" -> new ThongKe_GUI().setVisible(true);
//                	case "Đăng xuất" -> System.exit(0);
//                	default -> {}
//
//                    case "Trang chủ" -> new TrangChuRapChieuPhim_GUI().setVisible(true);
//                    case "Suất chiếu" -> new SuatChieu_GUI().setVisible(true);
//                    case "Đăng xuất" -> System.exit(0);
//                    default -> {}
//
//                }
//            });
//
//            menu.add(Box.createVerticalStrut(10));
//            menu.add(btn);
//        }
//
//        return menu;
//    }
//
//    private JPanel createMainContent() {
//        JPanel main = new JPanel(new BorderLayout(10, 10));
//        main.setBorder(new EmptyBorder(10, 10, 10, 10));
//        main.setBackground(new Color(45, 45, 45));
//
//        // Center - Table
//        String[] cols = {"Mã phim", "Tên phim", "Thể loại", "Thời lượng", "Đạo diễn", "Ngày khởi chiếu", "Mô tả", "Ngôn ngữ", "Độ tuổi giới hạn", "Nước sản xuất"};
//        modelPhim = new DefaultTableModel(cols, 0);
//        tblPhim = new JTable(modelPhim);
//        tblPhim.setBackground(new Color(60, 60, 60));
//        tblPhim.setForeground(Color.WHITE);
//        tblPhim.setFont(new Font("Segoe UI", Font.PLAIN, 13));
//        tblPhim.setRowHeight(22);
//        JScrollPane scroll = new JScrollPane(tblPhim);
//        scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Danh sách phim"));
//        main.add(scroll, BorderLayout.CENTER);
//
//        // East - Tìm kiếm
//        JPanel east = new JPanel();
//        east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
//        east.setBackground(new Color(45, 45, 45));
//        east.setPreferredSize(new Dimension(250, 0));
//
//        JLabel lblTieuDe = new JLabel("TRA CỨU", SwingConstants.CENTER);
//        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 18));
//        lblTieuDe.setAlignmentX(Component.CENTER_ALIGNMENT);
//        lblTieuDe.setForeground(Color.WHITE);
//
//        chkMaPhim = new JCheckBox("Tra cứu theo mã phim");
//        chkMaPhim.setBackground(new Color(45, 45, 45));
//        chkMaPhim.setForeground(Color.WHITE);
//        chkMaPhim.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//        chkMaPhim.setSelected(true);
//
//        txtMaPhim = new JTextField();
//        txtMaPhim.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
//        txtMaPhim.setBorder(BorderFactory.createTitledBorder("Mã phim"));
//        txtMaPhim.setBackground(new Color(60, 60, 60));
//        txtMaPhim.setForeground(Color.WHITE);
//        txtMaPhim.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//
//        chkTenPhim = new JCheckBox("Tra cứu theo tên phim");
//        chkTenPhim.setBackground(new Color(45, 45, 45));
//        chkTenPhim.setForeground(Color.WHITE);
//        chkTenPhim.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//
//        txtTenPhim = new JTextField();
//        txtTenPhim.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
//        txtTenPhim.setBorder(BorderFactory.createTitledBorder("Tên phim"));
//        txtTenPhim.setBackground(new Color(60, 60, 60));
//        txtTenPhim.setForeground(Color.WHITE);
//        txtTenPhim.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//
//        // Make checkboxes mutually exclusive
//        chkMaPhim.addActionListener(e -> {
//            if (chkMaPhim.isSelected()) {
//                chkTenPhim.setSelected(false);
//            }
//        });
//        chkTenPhim.addActionListener(e -> {
//            if (chkTenPhim.isSelected()) {
//                chkMaPhim.setSelected(false);
//            }
//        });
//
//        JPanel actionPanel = new JPanel();
//        actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
//        actionPanel.setBackground(new Color(45, 45, 45));
//        btnTimKiem = new JButton("Tìm kiếm");
//        btnTimKiem.setBackground(new Color(0, 120, 215));
//        btnTimKiem.setForeground(Color.WHITE);
//        btnTimKiem.setFont(new Font("Segoe UI", Font.BOLD, 14));
//        btnXoaTrang = new JButton("Xoá trắng");
//        btnXoaTrang.setBackground(new Color(200, 50, 50));
//        btnXoaTrang.setForeground(Color.WHITE);
//        btnXoaTrang.setFont(new Font("Segoe UI", Font.BOLD, 14));
//        actionPanel.add(btnTimKiem);
//        actionPanel.add(btnXoaTrang);
//
//        east.add(lblTieuDe);
//        east.add(Box.createVerticalStrut(10));
//        east.add(chkMaPhim);
//        east.add(txtMaPhim);
//        east.add(Box.createVerticalStrut(10));
//        east.add(chkTenPhim);
//        east.add(txtTenPhim);
//        east.add(Box.createVerticalStrut(15));
//        east.add(actionPanel);
//
//        main.add(east, BorderLayout.EAST);
//
//        // South - Các nút chức năng
//        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
//        south.setBackground(new Color(45, 45, 45));
//        btnThem = new JButton("Thêm");
//        btnThem.setBackground(new Color(0, 120, 215));
//        btnThem.setForeground(Color.WHITE);
//        btnThem.setFont(new Font("Segoe UI", Font.BOLD, 14));
//        btnThem.addActionListener(e -> showAddMoviePopup());
//
//        btnXoa = new JButton("Xoá");
//        btnXoa.setBackground(new Color(200, 50, 50));
//        btnXoa.setForeground(Color.WHITE);
//        btnXoa.setFont(new Font("Segoe UI", Font.BOLD, 14));
//
//        btnSua = new JButton("Sửa");
//        btnSua.setBackground(new Color(0, 120, 215));
//        btnSua.setForeground(Color.WHITE);
//        btnSua.setFont(new Font("Segoe UI", Font.BOLD, 14));
//        btnSua.addActionListener(e -> showEditMoviePopup());
//
//        btnHienTatCa = new JButton("Hiện tất cả");
//        btnHienTatCa.setBackground(new Color(0, 120, 215));
//        btnHienTatCa.setForeground(Color.WHITE);
//        btnHienTatCa.setFont(new Font("Segoe UI", Font.BOLD, 14));
//
//        south.add(btnThem);
//        south.add(btnXoa);
//        south.add(btnSua);
//        south.add(btnHienTatCa);
//        main.add(south, BorderLayout.SOUTH);
//
//        return main;
//    }
//
//    private void showAddMoviePopup() {
//        showMoviePopup("Thêm Phim Mới", "THÊM PHIM MỚI", null);
//    }
//
//    private void showEditMoviePopup() {
//        int selectedRow = tblPhim.getSelectedRow();
//        if (selectedRow == -1) {
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phim để sửa!");
//            return;
//        }
//
//        // Retrieve data from selected row
//        String[] rowData = new String[10];
//        for (int i = 0; i < 10; i++) {
//            rowData[i] = modelPhim.getValueAt(selectedRow, i).toString();
//        }
//
//        showMoviePopup("Sửa Thông Tin Phim", "SỬA THÔNG TIN PHIM", rowData);
//    }
//
//    private void showMoviePopup(String dialogTitle, String labelTitle, String[] rowData) {
//        JDialog dialog = new JDialog(this, dialogTitle, true);
//        dialog.setSize(700, 500);
//        dialog.setLocationRelativeTo(this);
//        dialog.setLayout(new BorderLayout(10, 10));
//
//        // North: Title
//        JLabel lblTitle = new JLabel(labelTitle, SwingConstants.CENTER);
//        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
//        lblTitle.setForeground(Color.WHITE);
//        lblTitle.setOpaque(true);
//        lblTitle.setBackground(new Color(30, 30, 30));
//        lblTitle.setBorder(new EmptyBorder(10, 0, 10, 0));
//        dialog.add(lblTitle, BorderLayout.NORTH);
//
//        // Center: Text fields
//        JPanel textPanel = new JPanel();
//        textPanel.setLayout(new GridLayout(5, 2, 10, 10));
//        textPanel.setBackground(new Color(45, 45, 45));
//        textPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
//
//        JTextField txtMaPhim = createTextField("Mã phim", 16);
//        JTextField txtTenPhim = createTextField("Tên phim", 16);
//        JTextField txtTheLoai = createTextField("Thể loại", 16);
//        JTextField txtThoiLuong = createTextField("Thời lượng (phút)", 16);
//        JTextField txtDaoDien = createTextField("Đạo diễn", 16);
//        JTextField txtNgayKhoiChieu = createTextField("Ngày khởi chiếu (yyyy-MM-dd)", 16);
//        JTextField txtMoTa = createTextField("Mô tả", 16);
//        JTextField txtNgonNgu = createTextField("Ngôn ngữ", 16);
//        JTextField txtDoTuoiGioiHan = createTextField("Độ tuổi giới hạn", 16);
//        JTextField txtNuocSanXuat = createTextField("Nước sản xuất", 16);
//
//        // Pre-fill fields for edit mode
//        if (rowData != null) {
//            txtMaPhim.setText(rowData[0]);
//            txtTenPhim.setText(rowData[1]);
//            txtTheLoai.setText(rowData[2]);
//            txtThoiLuong.setText(rowData[3]);
//            txtDaoDien.setText(rowData[4]);
//            txtNgayKhoiChieu.setText(rowData[5]);
//            txtMoTa.setText(rowData[6]);
//            txtNgonNgu.setText(rowData[7]);
//            txtDoTuoiGioiHan.setText(rowData[8]);
//            txtNuocSanXuat.setText(rowData[9]);
//        }
//
//        textPanel.add(txtMaPhim);
//        textPanel.add(txtTenPhim);
//        textPanel.add(txtTheLoai);
//        textPanel.add(txtThoiLuong);
//        textPanel.add(txtDaoDien);
//        textPanel.add(txtNgayKhoiChieu);
//        textPanel.add(txtMoTa);
//        textPanel.add(txtNgonNgu);
//        textPanel.add(txtDoTuoiGioiHan);
//        textPanel.add(txtNuocSanXuat);
//
//        JScrollPane scrollPane = new JScrollPane(textPanel);
//        scrollPane.setBackground(new Color(45, 45, 45));
//        dialog.add(scrollPane, BorderLayout.CENTER);
//
//        // South: Buttons
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
//        buttonPanel.setBackground(new Color(45, 45, 45));
//
//        JButton btnSave = new JButton("Lưu");
//        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 16));
//        btnSave.setBackground(new Color(0, 120, 215));
//        btnSave.setForeground(Color.WHITE);
//        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        btnSave.addActionListener(e -> {
//            try {
//                String maPhim = txtMaPhim.getText().trim();
//                String tenPhim = txtTenPhim.getText().trim();
//                String theLoai = txtTheLoai.getText().trim();
//                String thoiLuongStr = txtThoiLuong.getText().trim();
//                String daoDien = txtDaoDien.getText().trim();
//                String ngayKhoiChieuStr = txtNgayKhoiChieu.getText().trim();
//                String moTa = txtMoTa.getText().trim();
//                String ngonNgu = txtNgonNgu.getText().trim();
//                String doTuoiGioiHanStr = txtDoTuoiGioiHan.getText().trim();
//                String nuocSanXuat = txtNuocSanXuat.getText().trim();
//
//                // Validate required fields
//                if (maPhim.isEmpty() || tenPhim.isEmpty() || thoiLuongStr.isEmpty() ||
//                    ngayKhoiChieuStr.isEmpty() || doTuoiGioiHanStr.isEmpty()) {
//                    JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đầy đủ các trường bắt buộc!");
//                    return;
//                }
//
//                // Parse numeric and date fields
//                int thoiLuong = Integer.parseInt(thoiLuongStr);
//                LocalDate ngayKhoiChieu = LocalDate.parse(ngayKhoiChieuStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                int doTuoiGioiHan = Integer.parseInt(doTuoiGioiHanStr);
//
//                // Add or update table
//                if (rowData == null) {
//                    // Add mode
//                    modelPhim.addRow(new Object[]{
//                        maPhim, tenPhim, theLoai, thoiLuong, daoDien, ngayKhoiChieu.toString(),
//                        moTa, ngonNgu, doTuoiGioiHan, nuocSanXuat
//                    });
//                    JOptionPane.showMessageDialog(dialog, "Đã thêm phim: " + tenPhim);
//                } else {
//                    // Edit mode
//                    int selectedRow = tblPhim.getSelectedRow();
//                    modelPhim.setValueAt(maPhim, selectedRow, 0);
//                    modelPhim.setValueAt(tenPhim, selectedRow, 1);
//                    modelPhim.setValueAt(theLoai, selectedRow, 2);
//                    modelPhim.setValueAt(thoiLuong, selectedRow, 3);
//                    modelPhim.setValueAt(daoDien, selectedRow, 4);
//                    modelPhim.setValueAt(ngayKhoiChieu.toString(), selectedRow, 5);
//                    modelPhim.setValueAt(moTa, selectedRow, 6);
//                    modelPhim.setValueAt(ngonNgu, selectedRow, 7);
//                    modelPhim.setValueAt(doTuoiGioiHan, selectedRow, 8);
//                    modelPhim.setValueAt(nuocSanXuat, selectedRow, 9);
//                    JOptionPane.showMessageDialog(dialog, "Đã cập nhật phim: " + tenPhim);
//                }
//
//                dialog.dispose();
//            } catch (NumberFormatException ex) {
//                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập số hợp lệ cho Thời lượng hoặc Độ tuổi giới hạn!");
//            } catch (DateTimeParseException ex) {
//                JOptionPane.showMessageDialog(dialog, "Ngày khởi chiếu phải có định dạng yyyy-MM-dd!");
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(dialog, "Đã xảy ra lỗi: " + ex.getMessage());
//            }
//        });
//
//        JButton btnCancel = new JButton("Hủy");
//        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 16));
//        btnCancel.setBackground(new Color(200, 50, 50));
//        btnCancel.setForeground(Color.WHITE);
//        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        btnCancel.addActionListener(e -> dialog.dispose());
//
//        buttonPanel.add(btnSave);
//        buttonPanel.add(btnCancel);
//
//        dialog.add(buttonPanel, BorderLayout.SOUTH);
//
//        dialog.setVisible(true);
//    }
//
//    private JTextField createTextField(String title, int fontSize) {
//        JTextField field = new JTextField();
//        field.setBorder(BorderFactory.createTitledBorder(title));
//        field.setBackground(new Color(60, 60, 60));
//        field.setForeground(Color.WHITE);
//        field.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
//        return field;
//    }
//
//    private JPanel createFooter() {
//        JLabel lbl = new JLabel("Nhóm Quản lý Rạp Chiếu Phim", SwingConstants.CENTER);
//        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
//        lbl.setForeground(Color.LIGHT_GRAY);
//        lbl.setBackground(new Color(30, 30, 30));
//        lbl.setOpaque(true);
//        lbl.setBorder(new EmptyBorder(10, 0, 10, 0));
//
//        JPanel footer = new JPanel(new BorderLayout());
//        footer.add(lbl);
//        return footer;
//    }
//
//    public static void main(String[] args) {
//        EventQueue.invokeLater(() -> new QuanLyPhim_GUI().setVisible(true));
//    }
//}

package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import dao.Phim_DAO;
import entity.Phim;
import entity.LoaiPhim;

public class QuanLyPhim_GUI extends JFrame {
    private JTable tblPhim;
    private DefaultTableModel modelPhim;
    private JTextField txtMaPhim, txtTenPhim;
    private JCheckBox chkMaPhim, chkTenPhim;
    private JButton btnTimKiem, btnXoaTrang, btnThem, btnXoa, btnSua, btnHienTatCa;
    private Phim_DAO phimDAO = new Phim_DAO();

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

        loadDataToTable();
    }

    private JPanel createHeader() {
        JLabel lbl = new JLabel("HỆ THỐNG QUẢN LÝ RẠP CHIẾU PHIM", SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
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

        String[] items = {"Trang chủ", "Phim", "Suất chiẾu", "Thức ăn", "Nhân viên", "Hoá đơn", "Bán vé", "Bán thức ăn", "Thống kê", "Đăng xuất"};
        for (String item : items) {
            JButton btn = new JButton(item);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btn.setForeground(item.equals("\u0110\u0103ng xu\u1ea5t") ? Color.RED : Color.WHITE);
            btn.setBackground(new Color(40, 40, 40));
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            menu.add(Box.createVerticalStrut(10));
            menu.add(btn);
        }

        return menu;
    }

    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(new EmptyBorder(10, 10, 10, 10));
        main.setBackground(new Color(45, 45, 45));

        String[] cols = {"Mã phim", "Tên phim", "Thể loại", "Thời lượng", "Đạo diễn", "Ngày khởi chiẾu", "Mô tả", "Ngôn ngữ", "Độ tuổi giới hạn", "Nước sản xuất"};
        modelPhim = new DefaultTableModel(cols, 0);
        tblPhim = new JTable(modelPhim);
        tblPhim.setBackground(new Color(60, 60, 60));
        tblPhim.setForeground(Color.WHITE);
        tblPhim.setRowHeight(22);
        JScrollPane scroll = new JScrollPane(tblPhim);
        scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Danh sách phim"));

        main.add(scroll, BorderLayout.CENTER);
        return main;
    }

    private void loadDataToTable() {
        modelPhim.setRowCount(0);
        for (Phim p : phimDAO.layTatCaPhim()) {
            modelPhim.addRow(new Object[]{
                p.getMaPhim(),
                p.getTenPhim(),
                p.getTheLoai().getTenLoai(),
                p.getThoiLuong(),
                p.getDaoDien(),
                p.getNgayKhoiChieu().toString(),
                p.getMoTa(),
                p.getNgonNgu(),
                p.getDoTuoiGioiHan(),
                p.getNuocSX()
            });
        }
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

    private void showAddMoviePopup() {
        showMoviePopup("Thêm Phim Mới", "THÊM PHIM MỚI", null);
    }

    private void showEditMoviePopup() {
        int selectedRow = tblPhim.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phim để sửa!");
            return;
        }

        String[] rowData = new String[10];
        for (int i = 0; i < 10; i++) {
            rowData[i] = modelPhim.getValueAt(selectedRow, i).toString();
        }

        showMoviePopup("Sửa Thông Tin Phim", "SỬA THÔNG TIN PHIM", rowData);
    }

    private void showMoviePopup(String dialogTitle, String labelTitle, String[] rowData) {
        JDialog dialog = new JDialog(this, dialogTitle, true);
        dialog.setSize(700, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        JLabel lblTitle = new JLabel(labelTitle, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(new Color(30, 30, 30));
        lblTitle.setBorder(new EmptyBorder(10, 0, 10, 0));
        dialog.add(lblTitle, BorderLayout.NORTH);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(5, 2, 10, 10));
        textPanel.setBackground(new Color(45, 45, 45));
        textPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextField txtMaPhim = createTextField("Mã phim", 16);
        JTextField txtTenPhim = createTextField("Tên phim", 16);
        JTextField txtTheLoai = createTextField("Thể loại (mã)", 16);
        JTextField txtThoiLuong = createTextField("Thời lượng (phút)", 16);
        JTextField txtDaoDien = createTextField("Đạo diễn", 16);
        JTextField txtNgayKhoiChieu = createTextField("Ngày khởi chiếu (yyyy-MM-dd)", 16);
        JTextField txtMoTa = createTextField("Mô tả", 16);
        JTextField txtNgonNgu = createTextField("Ngôn ngữ", 16);
        JTextField txtDoTuoiGioiHan = createTextField("Độ tuổi giới hạn", 16);
        JTextField txtNuocSanXuat = createTextField("Nước sản xuất", 16);

        if (rowData != null) {
            txtMaPhim.setText(rowData[0]);
            txtTenPhim.setText(rowData[1]);
            txtTheLoai.setText(rowData[2]);
            txtThoiLuong.setText(rowData[3]);
            txtDaoDien.setText(rowData[4]);
            txtNgayKhoiChieu.setText(rowData[5]);
            txtMoTa.setText(rowData[6]);
            txtNgonNgu.setText(rowData[7]);
            txtDoTuoiGioiHan.setText(rowData[8]);
            txtNuocSanXuat.setText(rowData[9]);
        }

        textPanel.add(txtMaPhim);
        textPanel.add(txtTenPhim);
        textPanel.add(txtTheLoai);
        textPanel.add(txtThoiLuong);
        textPanel.add(txtDaoDien);
        textPanel.add(txtNgayKhoiChieu);
        textPanel.add(txtMoTa);
        textPanel.add(txtNgonNgu);
        textPanel.add(txtDoTuoiGioiHan);
        textPanel.add(txtNuocSanXuat);

        dialog.add(new JScrollPane(textPanel), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(45, 45, 45));

        JButton btnSave = new JButton("Lưu");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSave.setBackground(new Color(0, 120, 215));
        btnSave.setForeground(Color.WHITE);
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.addActionListener(e -> {
            try {
                String maPhim = txtMaPhim.getText().trim();
                String tenPhim = txtTenPhim.getText().trim();
                String maLoai = txtTheLoai.getText().trim();
                String thoiLuongStr = txtThoiLuong.getText().trim();
                String daoDien = txtDaoDien.getText().trim();
                String ngayKhoiChieuStr = txtNgayKhoiChieu.getText().trim();
                String moTa = txtMoTa.getText().trim();
                String ngonNgu = txtNgonNgu.getText().trim();
                String doTuoiGioiHanStr = txtDoTuoiGioiHan.getText().trim();
                String nuocSanXuat = txtNuocSanXuat.getText().trim();

                if (maPhim.isEmpty() || tenPhim.isEmpty() || thoiLuongStr.isEmpty() ||
                    ngayKhoiChieuStr.isEmpty() || doTuoiGioiHanStr.isEmpty() || maLoai.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đầy đủ các trường bắt buộc!");
                    return;
                }

                int thoiLuong = Integer.parseInt(thoiLuongStr);
                LocalDate ngayKhoiChieu = LocalDate.parse(ngayKhoiChieuStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                int doTuoiGioiHan = Integer.parseInt(doTuoiGioiHanStr);

                Phim phim = new Phim(maPhim, tenPhim, new LoaiPhim(maLoai), thoiLuong, daoDien, ngayKhoiChieu,
                        moTa, ngonNgu, doTuoiGioiHan, nuocSanXuat);

                boolean success;
                if (rowData == null) {
                    success = phimDAO.themPhim(phim);
                    if (success) JOptionPane.showMessageDialog(dialog, "Đã thêm phim thành công!");
                } else {
                    success = phimDAO.capNhatPhim(phim);
                    if (success) JOptionPane.showMessageDialog(dialog, "Đã cập nhật phim thành công!");
                }

                if (success) {
                    loadDataToTable();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Thao tác không thành công!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Lỗi: " + ex.getMessage());
            }
        });

        JButton btnCancel = new JButton("Hủy");
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCancel.setBackground(new Color(200, 50, 50));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private JTextField createTextField(String title, int fontSize) {
        JTextField field = new JTextField();
        field.setBorder(BorderFactory.createTitledBorder(title));
        field.setBackground(new Color(60, 60, 60));
        field.setForeground(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
        return field;
    }

//    private JPanel createFooter() {
//        JLabel lbl = new JLabel("Nhóm Quản lý Rạp Chiếu Phim", SwingConstants.CENTER);
//        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
//        lbl.setForeground(Color.LIGHT_GRAY);
//        lbl.setBackground(new Color(30, 30, 30));
//        lbl.setOpaque(true);
//        lbl.setBorder(new EmptyBorder(10, 0, 10, 0));
//
//        JPanel footer = new JPanel(new BorderLayout());
//        footer.add(lbl);
//        return footer;
//    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new QuanLyPhim_GUI().setVisible(true));
    }
}
