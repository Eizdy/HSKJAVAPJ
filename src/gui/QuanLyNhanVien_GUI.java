package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import dao.TaiKhoan_DAO;
import dao.NhanVien_DAO;
import entity.NhanVien;
import entity.TaiKhoan;
import java.util.List;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class QuanLyNhanVien_GUI extends JFrame {
    private JTable tblNhanVien;
    private DefaultTableModel modelNhanVien;
    private JTextField txtMaNV, txtHoTen;
    private JCheckBox chkMaNV, chkHoTen;
    private JButton btnTimKiem, btnXoaTrang, btnThem, btnXoa, btnSua, btnHienTatCa;
    private NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
    private TaiKhoan_DAO taiKhoanDAO = new TaiKhoan_DAO();

    public QuanLyNhanVien_GUI() {
        setTitle("Nhân viên - Quản lý Rạp Chiếu Phim");
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

        String[] items = {"Trang chủ", "Phim", "Suất chiếu", "Nhân viên", "Hoá đơn", "Bán vé", "Thống kê", "Đăng xuất"};
        for (String item : items) {
            JButton btn = new JButton(item);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(40, 40, 40));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btn.addActionListener(e -> {
                dispose();
                switch (item) {
                    case "Trang chủ" -> new TrangChuRapChieuPhim_GUI().setVisible(true);
                    case "Phim" -> new QuanLyPhim_GUI().setVisible(true);
                    case "Suất chiếu" -> new SuatChieu_GUI().setVisible(true);
                    case "Nhân viên" -> new QuanLyNhanVien_GUI().setVisible(true);
                    case "Hoá đơn" -> new QuanLyHoaDon_GUI().setVisible(true);
                    case "Bán vé" -> new QuanLyBanVe_GUI().setVisible(true);
                    case "Thống kê" -> new QuanLyThongKe_GUI().setVisible(true);
                    case "Đăng xuất" -> System.exit(0);
                    default -> {}
                }
            });

            if (item.equals("Đăng xuất")) btn.setForeground(Color.RED);
            menu.add(Box.createVerticalStrut(10));
            menu.add(btn);
        }
        return menu;
    }

    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(new EmptyBorder(10, 10, 10, 10));
        main.setBackground(new Color(45, 45, 45));

        String[] cols = {"Mã nhân viên", "Họ tên", "Số điện thoại", "Email", "Ngày sinh", "Chức vụ", "Tài khoản"};
        modelNhanVien = new DefaultTableModel(cols, 0);
        
        tblNhanVien = new JTable(modelNhanVien);
        tblNhanVien.setBackground(new Color(60, 60, 60));
        tblNhanVien.setForeground(Color.WHITE);
        tblNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblNhanVien.setRowHeight(22);
        tblNhanVien.setSelectionBackground(new Color(0, 120, 215));
        tblNhanVien.setSelectionForeground(Color.WHITE);
        tblNhanVien.setGridColor(new Color(80, 80, 80));
        
        tblNhanVien.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                                                          boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(isSelected ? new Color(0, 120, 215) : new Color(60, 60, 60));
                c.setForeground(Color.WHITE);
                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
                return c;
            }
        });
        
        JScrollPane scroll = new JScrollPane(tblNhanVien);
        scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Danh sách nhân viên"));
        scroll.getViewport().setBackground(new Color(45, 45, 45));
        main.add(scroll, BorderLayout.CENTER);

        JPanel east = new JPanel();
        east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
        east.setBackground(new Color(45, 45, 45));
        east.setPreferredSize(new Dimension(250, 0));

        JLabel lblTieuDe = new JLabel("TRA CỨU", SwingConstants.CENTER);
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTieuDe.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTieuDe.setForeground(Color.WHITE);

        chkMaNV = new JCheckBox("Tra cứu theo mã nhân viên");
        chkMaNV.setBackground(new Color(45, 45, 45));
        chkMaNV.setForeground(Color.WHITE);
        chkMaNV.setSelected(true);
        chkMaNV.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtMaNV = new JTextField();
        txtMaNV.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtMaNV.setBorder(BorderFactory.createTitledBorder("Mã nhân viên"));
        txtMaNV.setBackground(new Color(60, 60, 60));
        txtMaNV.setForeground(Color.WHITE);
        txtMaNV.setCaretColor(Color.WHITE);
        txtMaNV.setEnabled(true);

        chkHoTen = new JCheckBox("Tra cứu theo họ tên");
        chkHoTen.setBackground(new Color(45, 45, 45));
        chkHoTen.setForeground(Color.WHITE);
        chkHoTen.setSelected(false);
        chkHoTen.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtHoTen = new JTextField();
        txtHoTen.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtHoTen.setBorder(BorderFactory.createTitledBorder("Họ tên"));
        txtHoTen.setBackground(new Color(60, 60, 60));
        txtHoTen.setForeground(Color.WHITE);
        txtHoTen.setCaretColor(Color.WHITE);
        txtHoTen.setEnabled(false);

        chkMaNV.addActionListener(e -> {
            if (chkMaNV.isSelected()) {
                chkHoTen.setSelected(false);
                txtHoTen.setText("");
                txtMaNV.setEnabled(true);
                txtHoTen.setEnabled(false);
            } else {
                txtMaNV.setEnabled(false);
            }
        });

        chkHoTen.addActionListener(e -> {
            if (chkHoTen.isSelected()) {
                chkMaNV.setSelected(false);
                txtMaNV.setText("");
                txtHoTen.setEnabled(true);
                txtMaNV.setEnabled(false);
            } else {
                txtHoTen.setEnabled(false);
            }
        });

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actionPanel.setBackground(new Color(45, 45, 45));
        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setBackground(new Color(0, 120, 215));
        btnTimKiem.setForeground(Color.WHITE);
        btnXoaTrang = new JButton("Xoá trắng");
        btnXoaTrang.setBackground(new Color(200, 50, 50));
        btnXoaTrang.setForeground(Color.WHITE);
        actionPanel.add(btnTimKiem);
        actionPanel.add(btnXoaTrang);

        btnTimKiem.addActionListener(e -> searchNhanVien());

        btnXoaTrang.addActionListener(e -> {
            txtMaNV.setText("");
            txtHoTen.setText("");
            chkMaNV.setSelected(true);
            chkHoTen.setSelected(false);
            loadDataToTable();
            tblNhanVien.clearSelection();
        });

        east.add(lblTieuDe);
        east.add(Box.createVerticalStrut(10));
        east.add(chkMaNV);
        east.add(txtMaNV);
        east.add(Box.createVerticalStrut(10));
        east.add(chkHoTen);
        east.add(txtHoTen);
        east.add(Box.createVerticalStrut(15));
        east.add(actionPanel);

        main.add(east, BorderLayout.EAST);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        south.setBackground(new Color(45, 45, 45));
        btnThem = new JButton("Thêm");
        btnThem.setBackground(new Color(0, 120, 215));
        btnThem.setForeground(Color.WHITE);
        btnThem.addActionListener(e -> showAddNhanVienDialog());

        btnXoa = new JButton("Xoá");
        btnXoa.setBackground(new Color(200, 50, 50));
        btnXoa.setForeground(Color.WHITE);
        btnXoa.addActionListener(e -> {
            int selectedRow = tblNhanVien.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để xoá!");
                return;
            }

            String maNV = modelNhanVien.getValueAt(selectedRow, 0).toString();
            String tenNV = modelNhanVien.getValueAt(selectedRow, 1).toString();
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xoá nhân viên: " + maNV + " (" + maNV + ")?",
                "Xác nhận xoá",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    if (nhanVienDAO.xoaNhanVien(maNV)) {
                        JOptionPane.showMessageDialog(this, "Đã xoá nhân viên: " + tenNV);
                        loadDataToTable();
                        tblNhanVien.clearSelection();
                    } else {
                        JOptionPane.showMessageDialog(this, "Xoá nhân viên thất bại!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi xoá nhân viên: " + ex.getMessage());
                }
            }
        });

        btnSua = new JButton("Sửa");
        btnSua.setBackground(new Color(0, 120, 215));
        btnSua.setForeground(Color.WHITE);
        btnSua.addActionListener(e -> showEditEmployeeDialog());

        btnHienTatCa = new JButton("Hiện tất cả");
        btnHienTatCa.setBackground(new Color(0, 120, 215));
        btnHienTatCa.setForeground(Color.WHITE);
        btnHienTatCa.addActionListener(e -> {
            txtMaNV.setText("");
            txtHoTen.setText("");
            chkMaNV.setSelected(true);
            chkHoTen.setSelected(false);
            loadDataToTable();
            tblNhanVien.clearSelection();
        });

        south.add(btnThem);
        south.add(btnXoa);
        south.add(btnSua);
        south.add(btnHienTatCa);
        main.add(south, BorderLayout.SOUTH);

        btnTimKiem.addActionListener(e -> searchNhanVien());
        return main;
    }

    private void searchNhanVien() {
        try {
            String maNV = txtMaNV.getText().trim();
            String tenNV = txtHoTen.getText().trim();

            if (!chkMaNV.isSelected() && !chkHoTen.isSelected()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một tiêu chí tìm kiếm!");
                return;
            }

            if ((chkMaNV.isSelected() && maNV.isEmpty()) || 
                (chkHoTen.isSelected() && tenNV.isEmpty())) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin tìm kiếm!");
                return;
            }

            modelNhanVien.setRowCount(0);

            boolean found = false;
            int rowIndex = 0;
            int selectIndex = -1;

            if (chkMaNV.isSelected()) {
                NhanVien nhanVien = nhanVienDAO.timNhanVienTheoMa(maNV);
                if (nhanVien != null) {
                    modelNhanVien.addRow(new Object[]{
                        nhanVien.getMaNV(),
                        nhanVien.getTenNV(),
                        nhanVien.getNgaySinh().toString(),
                        nhanVien.getDienThoai(),
                        nhanVien.getEmail(),
                        nhanVien.getChucVu(),
                        nhanVien.getTenTK().getTenDangNhap()
                    });
                    found = true;
                    selectIndex = 0;
                }
            } else if (chkHoTen.isSelected()) {
                for (NhanVien nhanVien : nhanVienDAO.layTatCaNhanVien()) {
                    if (nhanVien.getTenNV().toLowerCase().contains(tenNV.toLowerCase())) {
                        modelNhanVien.addRow(new Object[]{
                            nhanVien.getMaNV(),
                            nhanVien.getTenNV(),
                            nhanVien.getNgaySinh().toString(),
                            nhanVien.getDienThoai(),
                            nhanVien.getEmail(),
                            nhanVien.getChucVu(),
                            nhanVien.getTenTK().getTenDangNhap()
                        });
                        if (nhanVien.getTenNV().equalsIgnoreCase(tenNV)) {
                            selectIndex = rowIndex;
                        }
                        found = true;
                        rowIndex++;
                    }
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên phù hợp!");
                loadDataToTable();
            } else if (selectIndex >= 0) {
                tblNhanVien.setRowSelectionInterval(selectIndex, selectIndex);
                tblNhanVien.scrollRectToVisible(tblNhanVien.getCellRect(selectIndex, 0, true));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage());
        }
    }

    private String generateNextMaNV() {
        try {
            int currentCount = nhanVienDAO.layTatCaNhanVien().size();
            return String.format("NV%02d", currentCount + 1);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể tạo mã nhân viên mới: " + e.getMessage());
            return "NV01";
        }
    }

    private void showAddNhanVienDialog() {
        JDialog dialog = new JDialog(this, "Thêm Nhân Viên", true);
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setBackground(new Color(45, 45, 45));

        JLabel lblTitle = new JLabel("THÊM NHÂN VIÊN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(new Color(30, 30, 30));
        lblTitle.setBorder(new EmptyBorder(15, 0, 15, 0));
        dialog.add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(new Color(45, 45, 45));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel basicInfoPanel = new JPanel();
        basicInfoPanel.setLayout(new GridLayout(0, 1, 10, 10));
        basicInfoPanel.setBackground(new Color(45, 45, 45));
        basicInfoPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), "Thông tin cơ bản",
            0, 0, new Font("Segoe UI", Font.BOLD, 14), Color.LIGHT_GRAY));

        JTextField txtMaNV = createTextField("Mã nhân viên", 16);
        txtMaNV.setText(generateNextMaNV());
        txtMaNV.setEditable(false);

        JTextField txtTenNV = createTextField("Tên nhân viên", 16);
        JTextField txtNgaySinh = createTextField("Ngày sinh (yyyy-MM-dd)", 16);
        JTextField txtDienThoai = createTextField("Điện thoại", 16);
        JTextField txtEmail = createTextField("Email", 16);
        JTextField txtChucVu = createTextField("Chức vụ", 16);

        JLabel lblTaiKhoan = new JLabel("Tài khoản");
        lblTaiKhoan.setForeground(Color.WHITE);
        lblTaiKhoan.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JComboBox<TaiKhoan> cboTaiKhoan = new JComboBox<>();
        cboTaiKhoan.setBackground(new Color(60, 60, 60));
        cboTaiKhoan.setForeground(Color.WHITE);
        cboTaiKhoan.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        try {
            List<TaiKhoan> danhSachTaiKhoan = taiKhoanDAO.layTatCaTaiKhoan();
            if (danhSachTaiKhoan != null && !danhSachTaiKhoan.isEmpty()) {
                for (TaiKhoan taiKhoan : danhSachTaiKhoan) {
                    cboTaiKhoan.addItem(taiKhoan);
                }
            } else {
                System.out.println("Không có tài khoản nào.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(dialog, "Không thể tải danh sách tài khoản: " + e.getMessage());
        }

        JPanel taiKhoanPanel = new JPanel(new BorderLayout());
        taiKhoanPanel.setBackground(new Color(45, 45, 45));
        taiKhoanPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), "Tài khoản",
            0, 0, new Font("Segoe UI", Font.PLAIN, 12), Color.LIGHT_GRAY));
        taiKhoanPanel.add(cboTaiKhoan, BorderLayout.CENTER);

        basicInfoPanel.add(txtMaNV);
        basicInfoPanel.add(txtTenNV);
        basicInfoPanel.add(txtNgaySinh);
        basicInfoPanel.add(txtDienThoai);
        basicInfoPanel.add(txtEmail);
        basicInfoPanel.add(txtChucVu);
        basicInfoPanel.add(taiKhoanPanel);

        formPanel.add(basicInfoPanel);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBackground(new Color(45, 45, 45));
        scrollPane.setBorder(null);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(45, 45, 45));

        JButton btnSave = new JButton("Thêm");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSave.setBackground(new Color(0, 120, 215));
        btnSave.setForeground(Color.WHITE);
        btnSave.setPreferredSize(new Dimension(100, 40));
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.addActionListener(e -> {
            try {
                String maNV = txtMaNV.getText().trim();
                String tenNV = txtTenNV.getText().trim();
                String ngaySinhStr = txtNgaySinh.getText().trim();
                String dienThoai = txtDienThoai.getText().trim();
                String email = txtEmail.getText().trim();
                String chucVu = txtChucVu.getText().trim();
                TaiKhoan taiKhoan = (TaiKhoan) cboTaiKhoan.getSelectedItem();

                if (maNV.isEmpty() || tenNV.isEmpty() || ngaySinhStr.isEmpty()
                    || dienThoai.isEmpty() || email.isEmpty() || chucVu.isEmpty() || taiKhoan == null) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đầy đủ các trường bắt buộc!");
                    return;
                }

                if (!validateNhanVienInputs(tenNV, ngaySinhStr, dienThoai, email, chucVu)) {
                    return;
                }

                LocalDate ngaySinh = LocalDate.parse(ngaySinhStr);
                NhanVien nhanVien = new NhanVien(maNV, tenNV, ngaySinh, dienThoai, email, chucVu, taiKhoan);

                if (nhanVienDAO.themNhanVien(nhanVien)) {
                    JOptionPane.showMessageDialog(dialog, "Đã thêm nhân viên: " + tenNV);
                    loadDataToTable();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Thêm nhân viên thất bại!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Đã xảy ra lỗi: " + ex.getMessage());
            }
        });

        JButton btnCancel = new JButton("Hủy");
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCancel.setBackground(new Color(200, 50, 50));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setPreferredSize(new Dimension(100, 40));
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private boolean validateNhanVienInputs(String tenNV, String ngaySinhStr, String dienThoai, String email, String chucVu) {
        String namePattern = "^[\\p{L} .'-]+$";
        String phonePattern = "^0\\d{9}$";
        String emailPattern = "^[\\w.-]+@[\\w.-]+\\.\\w{2,}$";

        if (!tenNV.matches(namePattern)) {
            JOptionPane.showMessageDialog(null, "Tên nhân viên không hợp lệ! Chỉ được chứa chữ cái và dấu cách.");
            return false;
        }

        if (!chucVu.matches(namePattern)) {
            JOptionPane.showMessageDialog(null, "Chức vụ không hợp lệ! Chỉ được chứa chữ cái và dấu cách.");
            return false;
        }

        if (!dienThoai.matches(phonePattern)) {
            JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ! Phải bắt đầu bằng 0 và có 10 chữ số.");
            return false;
        }

        if (!email.matches(emailPattern)) {
            JOptionPane.showMessageDialog(null, "Email không hợp lệ!");
            return false;
        }

        try {
            LocalDate ngaySinh = LocalDate.parse(ngaySinhStr);
            if (ngaySinh.isAfter(LocalDate.now())) {
                JOptionPane.showMessageDialog(null, "Ngày sinh không được lớn hơn ngày hiện tại!");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ngày sinh không hợp lệ! Định dạng đúng là yyyy-MM-dd.");
            return false;
        }

        return true;
    }

    private void showEditEmployeeDialog() {
        int selectedRow = tblNhanVien.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để sửa!");
            return;
        }

        String[] rowData = new String[7];
        for (int i = 0; i < 7; i++) {
            rowData[i] = modelNhanVien.getValueAt(selectedRow, i).toString();
        }

        JDialog dialog = new JDialog(this, "Sửa Nhân Viên", true);
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setBackground(new Color(45, 45, 45));

        JLabel lblTitle = new JLabel("SỬA NHÂN VIÊN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(new Color(30, 30, 30));
        lblTitle.setBorder(new EmptyBorder(15, 0, 15, 0));
        dialog.add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(new Color(45, 45, 45));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel basicInfoPanel = new JPanel();
        basicInfoPanel.setLayout(new GridLayout(0, 1, 10, 10));
        basicInfoPanel.setBackground(new Color(45, 45, 45));
        basicInfoPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), "Thông tin cơ bản",
            0, 0, new Font("Segoe UI", Font.BOLD, 14), Color.LIGHT_GRAY));

        JTextField txtMaNV = createTextField("Mã nhân viên", 16);
        txtMaNV.setText(rowData[0]);
        txtMaNV.setEditable(false);

        JTextField txtTenNV = createTextField("Tên nhân viên", 16);
        txtTenNV.setText(rowData[1]);

        JTextField txtNgaySinh = createTextField("Ngày sinh (yyyy-MM-dd)", 16);
        txtNgaySinh.setText(rowData[2]);

        JTextField txtDienThoai = createTextField("Điện thoại", 16);
        txtDienThoai.setText(rowData[3]);

        JTextField txtEmail = createTextField("Email", 16);
        txtEmail.setText(rowData[4]);

        JTextField txtChucVu = createTextField("Chức vụ", 16);
        txtChucVu.setText(rowData[5]);

        JLabel lblTaiKhoan = new JLabel("Tài khoản");
        lblTaiKhoan.setForeground(Color.WHITE);
        lblTaiKhoan.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JComboBox<TaiKhoan> cboTaiKhoan = new JComboBox<>();
        cboTaiKhoan.setBackground(new Color(60, 60, 60));
        cboTaiKhoan.setForeground(Color.WHITE);
        cboTaiKhoan.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        try {
            List<TaiKhoan> danhSachTaiKhoan = taiKhoanDAO.layTatCaTaiKhoan();
            if (danhSachTaiKhoan != null && !danhSachTaiKhoan.isEmpty()) {
                for (TaiKhoan taiKhoan : danhSachTaiKhoan) {
                    cboTaiKhoan.addItem(taiKhoan);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(dialog, "Không thể tải danh sách tài khoản: " + e.getMessage());
        }

        for (int i = 0; i < cboTaiKhoan.getItemCount(); i++) {
            if (cboTaiKhoan.getItemAt(i).getTenDangNhap().equals(rowData[6])) {
                cboTaiKhoan.setSelectedIndex(i);
                break;
            }
        }

        JPanel taiKhoanPanel = new JPanel(new BorderLayout());
        taiKhoanPanel.setBackground(new Color(45, 45, 45));
        taiKhoanPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), "Tài khoản",
            0, 0, new Font("Segoe UI", Font.PLAIN, 12), Color.LIGHT_GRAY));
        taiKhoanPanel.add(cboTaiKhoan, BorderLayout.CENTER);

        basicInfoPanel.add(txtMaNV);
        basicInfoPanel.add(txtTenNV);
        basicInfoPanel.add(txtNgaySinh);
        basicInfoPanel.add(txtDienThoai);
        basicInfoPanel.add(txtEmail);
        basicInfoPanel.add(txtChucVu);
        basicInfoPanel.add(taiKhoanPanel);

        formPanel.add(basicInfoPanel);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBackground(new Color(45, 45, 45));
        scrollPane.setBorder(null);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(45, 45, 45));

        JButton btnSave = new JButton("Cập nhật");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSave.setBackground(new Color(0, 120, 215));
        btnSave.setForeground(Color.WHITE);
        btnSave.setPreferredSize(new Dimension(100, 40));
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.addActionListener(e -> {
            try {
                String maNV = txtMaNV.getText().trim();
                String tenNV = txtTenNV.getText().trim();
                String ngaySinhStr = txtNgaySinh.getText().trim();
                String dienThoai = txtDienThoai.getText().trim();
                String email = txtEmail.getText().trim();
                String chucVu = txtChucVu.getText().trim();
                TaiKhoan taiKhoan = (TaiKhoan) cboTaiKhoan.getSelectedItem();

                if (tenNV.isEmpty() || ngaySinhStr.isEmpty()
                    || dienThoai.isEmpty() || email.isEmpty() || chucVu.isEmpty() || taiKhoan == null) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đầy đủ các trường bắt buộc!");
                    return;
                }

                if (!validateNhanVienInputs(tenNV, ngaySinhStr, dienThoai, email, chucVu)) {
                    return;
                }

                LocalDate ngaySinh = LocalDate.parse(ngaySinhStr);
                NhanVien nhanVien = new NhanVien(maNV, tenNV, ngaySinh, dienThoai, email, chucVu, taiKhoan);

                if (nhanVienDAO.capNhatNhanVien(nhanVien)) {
                    JOptionPane.showMessageDialog(dialog, "Đã cập nhật nhân viên: " + tenNV);
                    loadDataToTable();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Cập nhật nhân viên thất bại!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Đã xảy ra lỗi: " + ex.getMessage());
            }
        });

        JButton btnCancel = new JButton("Hủy");
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCancel.setBackground(new Color(200, 50, 50));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setPreferredSize(new Dimension(100, 40));
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private JTextField createTextField(String title, int fontSize) {
        JTextField field = new JTextField();
        field.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), title, 
            0, 0, new Font("Segoe UI", Font.PLAIN, 12), Color.LIGHT_GRAY));
        field.setBackground(new Color(60, 60, 60));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
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

    private void loadDataToTable() {
        try {
            modelNhanVien.setRowCount(0);
            for (NhanVien nv : nhanVienDAO.layTatCaNhanVien()) {
                modelNhanVien.addRow(new Object[]{
                    nv.getMaNV(),
                    nv.getTenNV(),
                    nv.getNgaySinh().toString(),
                    nv.getDienThoai(),
                    nv.getEmail(),
                    nv.getChucVu(),
                    nv.getTenTK().getTenDangNhap()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể tải danh sách nhân viên: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new QuanLyNhanVien_GUI().setVisible(true));
    }
}