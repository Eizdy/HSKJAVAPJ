package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import dao.LoaiPhim_DAO;
import dao.Phim_DAO;
import entity.LoaiPhim;
import entity.Phim;

public class QuanLyPhim_GUI extends JFrame {
    private JTable tblPhim;
    private DefaultTableModel modelPhim;
    private JTextField txtMaPhim, txtTenPhim;
    private JCheckBox chkMaPhim, chkTenPhim;
    private JButton btnTimKiem, btnXoaTrang, btnThem, btnXoa, btnSua, btnHienTatCa;
    private Phim_DAO phimDAO = new Phim_DAO();
    private LoaiPhim_DAO loaiPhimDAO = new LoaiPhim_DAO();

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
                    case "Nhân viên" -> new QuanLyNhanVien_GUI().setVisible(true);
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
        String[] cols = {"Mã phim", "Tên phim", "Thể loại", "Thời lượng", "Đạo diễn", "Ngày khởi chiếu", "Mô tả", "Ngôn ngữ", "Độ tuổi giới hạn", "Nước sản xuất"};
        modelPhim = new DefaultTableModel(cols, 0);
        tblPhim = new JTable(modelPhim);
        
        // Customize table appearance
        tblPhim.setBackground(new Color(60, 60, 60));
        tblPhim.setForeground(Color.WHITE);
        tblPhim.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblPhim.setRowHeight(22);
        tblPhim.setSelectionBackground(new Color(0, 120, 215));
        tblPhim.setSelectionForeground(Color.WHITE);
        tblPhim.setGridColor(new Color(80, 80, 80));
        
        // Custom cell renderer
        tblPhim.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

        JScrollPane scroll = new JScrollPane(tblPhim);
        scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Danh sách phim"));
        scroll.getViewport().setBackground(new Color(45, 45, 45));
        main.add(scroll, BorderLayout.CENTER);

        // East - Tìm kiếm
        JPanel east = new JPanel();
        east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
        east.setBackground(new Color(45, 45, 45));
        east.setPreferredSize(new Dimension(250, 0));

        JLabel lblTieuDe = new JLabel("TRA CỨU", SwingConstants.CENTER);
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTieuDe.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTieuDe.setForeground(Color.WHITE);

        chkMaPhim = new JCheckBox("Tra cứu theo mã phim");
        chkMaPhim.setBackground(new Color(45, 45, 45));
        chkMaPhim.setForeground(Color.WHITE);
        chkMaPhim.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chkMaPhim.setSelected(true);
        chkMaPhim.setMaximumSize(new Dimension(230, 30));
        chkMaPhim.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtMaPhim = new JTextField();
        txtMaPhim.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtMaPhim.setBorder(BorderFactory.createTitledBorder("Mã phim"));
        txtMaPhim.setBackground(new Color(60, 60, 60));
        txtMaPhim.setForeground(Color.WHITE);
        txtMaPhim.setCaretColor(Color.WHITE);
        txtMaPhim.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        chkTenPhim = new JCheckBox("Tra cứu theo tên phim");
        chkTenPhim.setBackground(new Color(45, 45, 45));
        chkTenPhim.setForeground(Color.WHITE);
        chkTenPhim.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chkTenPhim.setMaximumSize(new Dimension(230, 30));
        chkTenPhim.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtTenPhim = new JTextField();
        txtTenPhim.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtTenPhim.setBorder(BorderFactory.createTitledBorder("Tên phim"));
        txtTenPhim.setBackground(new Color(60, 60, 60));
        txtTenPhim.setForeground(Color.WHITE);
        txtTenPhim.setCaretColor(Color.WHITE);
        txtTenPhim.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        chkMaPhim.addActionListener(e -> {
            if (chkMaPhim.isSelected()) {
                chkTenPhim.setSelected(false);
                txtTenPhim.setText("");
            }
        });
        chkTenPhim.addActionListener(e -> {
            if (chkTenPhim.isSelected()) {
                chkMaPhim.setSelected(false);
                txtMaPhim.setText("");
            }
        });

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionPanel.setBackground(new Color(45, 45, 45));
        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setBackground(new Color(0, 120, 215));
        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnXoaTrang = new JButton("Xoá trắng");
        btnXoaTrang.setBackground(new Color(200, 50, 50));
        btnXoaTrang.setForeground(Color.WHITE);
        btnXoaTrang.setFont(new Font("Segoe UI", Font.BOLD, 14));
        actionPanel.add(btnTimKiem);
        actionPanel.add(btnXoaTrang);

        // Add action listener for search button
        btnTimKiem.addActionListener(e -> searchPhim());

        // Add action listener for clear button
        btnXoaTrang.addActionListener(e -> {
            txtMaPhim.setText("");
            txtTenPhim.setText("");
            chkMaPhim.setSelected(true);
            chkTenPhim.setSelected(false);
            loadDataToTable();
            tblPhim.clearSelection();
        });

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
        btnThem.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnThem.addActionListener(e -> showAddMovieDialog());

        btnXoa = new JButton("Xoá");
        btnXoa.setBackground(new Color(200, 50, 50));
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnXoa.addActionListener(e -> {
            int selectedRow = tblPhim.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phim để xoá!");
                return;
            }

            String maPhim = modelPhim.getValueAt(selectedRow, 0).toString();
            String tenPhim = modelPhim.getValueAt(selectedRow, 1).toString();
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xoá phim: " + tenPhim + " (" + maPhim + ")?",
                "Xác nhận xoá",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    if (phimDAO.xoaPhim(maPhim)) {
                        JOptionPane.showMessageDialog(this, "Đã xoá phim: " + tenPhim);
                        loadDataToTable();
                        tblPhim.clearSelection();
                    } else {
                        JOptionPane.showMessageDialog(this, "Xoá phim thất bại!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi xoá phim: " + ex.getMessage());
                }
            }
        });

        btnSua = new JButton("Sửa");
        btnSua.setBackground(new Color(0, 120, 215));
        btnSua.setForeground(Color.WHITE);
        btnSua.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSua.addActionListener(e -> showEditMovieDialog());

        btnHienTatCa = new JButton("Hiện tất cả");
        btnHienTatCa.setBackground(new Color(0, 120, 215));
        btnHienTatCa.setForeground(Color.WHITE);
        btnHienTatCa.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnHienTatCa.addActionListener(e -> {
            txtMaPhim.setText("");
            txtTenPhim.setText("");
            chkMaPhim.setSelected(true);
            chkTenPhim.setSelected(false);
            loadDataToTable();
            tblPhim.clearSelection();
        });

        south.add(btnThem);
        south.add(btnXoa);
        south.add(btnSua);
        south.add(btnHienTatCa);
        main.add(south, BorderLayout.SOUTH);

        return main;
    }

    private void searchPhim() {
        try {
            String maPhim = txtMaPhim.getText().trim();
            String tenPhim = txtTenPhim.getText().trim();
            
            // Validate input
            if (!chkMaPhim.isSelected() && !chkTenPhim.isSelected()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một tiêu chí tìm kiếm!");
                return;
            }
            
            if ((chkMaPhim.isSelected() && maPhim.isEmpty()) || 
                (chkTenPhim.isSelected() && tenPhim.isEmpty())) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin tìm kiếm!");
                return;
            }

            modelPhim.setRowCount(0); // Clear table
            
            boolean found = false;
            int rowIndex = 0;
            int selectIndex = -1;

            if (chkMaPhim.isSelected()) {
                Phim phim = phimDAO.timPhimTheoMa(maPhim);
                if (phim != null) {
                    modelPhim.addRow(new Object[]{
                        phim.getMaPhim(),
                        phim.getTenPhim(),
                        phim.getTheLoai().getTenLoai(),
                        phim.getThoiLuong(),
                        phim.getDaoDien(),
                        phim.getNgayKhoiChieu().toString(),
                        phim.getMoTa(),
                        phim.getNgonNgu(),
                        phim.getDoTuoiGioiHan(),
                        phim.getNuocSX()
                    });
                    found = true;
                    selectIndex = 0;
                }
            } else if (chkTenPhim.isSelected()) {
                for (Phim phim : phimDAO.layTatCaPhim()) {
                    if (phim.getTenPhim().toLowerCase().contains(tenPhim.toLowerCase())) {
                        modelPhim.addRow(new Object[]{
                            phim.getMaPhim(),
                            phim.getTenPhim(),
                            phim.getTheLoai().getTenLoai(),
                            phim.getThoiLuong(),
                            phim.getDaoDien(),
                            phim.getNgayKhoiChieu().toString(),
                            phim.getMoTa(),
                            phim.getNgonNgu(),
                            phim.getDoTuoiGioiHan(),
                            phim.getNuocSX()
                        });
                        if (phim.getTenPhim().equalsIgnoreCase(tenPhim)) {
                            selectIndex = rowIndex;
                        }
                        found = true;
                        rowIndex++;
                    }
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy phim phù hợp!");
                loadDataToTable(); // Reload all data if no results
            } else if (selectIndex >= 0) {
                // Select and scroll to the found row
                tblPhim.setRowSelectionInterval(selectIndex, selectIndex);
                tblPhim.scrollRectToVisible(tblPhim.getCellRect(selectIndex, 0, true));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage());
        }
    }

    private String generateNextMaPhim() {
        try {
            int currentCount = phimDAO.layTatCaPhim().size();
            return String.format("P%02d", currentCount + 1);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể tạo mã phim mới: " + e.getMessage());
            return "P01";
        }
    }

    private void showAddMovieDialog() {
        JDialog dialog = new JDialog(this, "Thêm Phim Mới", true);
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setBackground(new Color(45, 45, 45));

        JLabel lblTitle = new JLabel("THÊM PHIM MỚI", SwingConstants.CENTER);
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
        basicInfoPanel.setLayout(new GridLayout(2, 2, 15, 15));
        basicInfoPanel.setBackground(new Color(45, 45, 45));
        basicInfoPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), "Thông tin cơ bản", 
            0, 0, new Font("Segoe UI", Font.BOLD, 14), Color.LIGHT_GRAY));

        JTextField txtMaPhim = createTextField("Mã phim", 16);
        txtMaPhim.setText(generateNextMaPhim());
        txtMaPhim.setEditable(false);
        JTextField txtTenPhim = createTextField("Tên phim", 16);
        
        JLabel lblTheLoai = new JLabel("Thể loại");
        lblTheLoai.setForeground(Color.WHITE);
        lblTheLoai.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JComboBox<LoaiPhim> cboTheLoai = new JComboBox<>();
        cboTheLoai.setBackground(new Color(60, 60, 60));
        cboTheLoai.setForeground(Color.WHITE);
        cboTheLoai.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        try {
            for (LoaiPhim lp : loaiPhimDAO.layTatCaLoaiPhim()) {
                cboTheLoai.addItem(lp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(dialog, "Không thể tải danh sách thể loại: " + e.getMessage());
        }
        JPanel theLoaiPanel = new JPanel(new BorderLayout());
        theLoaiPanel.setBackground(new Color(45, 45, 45));
        theLoaiPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), "Thể loại", 
            0, 0, new Font("Segoe UI", Font.PLAIN, 12), Color.LIGHT_GRAY));
        theLoaiPanel.add(cboTheLoai, BorderLayout.CENTER);

        JTextField txtThoiLuong = createTextField("Thời lượng (phút)", 16);

        basicInfoPanel.add(txtMaPhim);
        basicInfoPanel.add(txtTenPhim);
        basicInfoPanel.add(theLoaiPanel);
        basicInfoPanel.add(txtThoiLuong);

        JPanel productionInfoPanel = new JPanel();
        productionInfoPanel.setLayout(new GridLayout(2, 2, 15, 15));
        productionInfoPanel.setBackground(new Color(45, 45, 45));
        productionInfoPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), "Thông tin sản xuất", 
            0, 0, new Font("Segoe UI", Font.BOLD, 14), Color.LIGHT_GRAY));

        JTextField txtDaoDien = createTextField("Đạo diễn", 16);
        JTextField txtNgayKhoiChieu = createTextField("Ngày khởi chiếu (yyyy-MM-dd)", 16);
        JTextField txtNgonNgu = createTextField("Ngôn ngữ", 16);
        JTextField txtNuocSanXuat = createTextField("Nước sản xuất", 16);

        productionInfoPanel.add(txtDaoDien);
        productionInfoPanel.add(txtNgayKhoiChieu);
        productionInfoPanel.add(txtNgonNgu);
        productionInfoPanel.add(txtNuocSanXuat);

        JPanel additionalInfoPanel = new JPanel();
        additionalInfoPanel.setLayout(new GridLayout(2, 1, 15, 15));
        additionalInfoPanel.setBackground(new Color(45, 45, 45));
        additionalInfoPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), "Thông tin bổ sung", 
            0, 0, new Font("Segoe UI", Font.BOLD, 14), Color.LIGHT_GRAY));

        JTextField txtMoTa = createTextField("Mô tả", 16);
        JTextField txtDoTuoiGioiHan = createTextField("Độ tuổi giới hạn", 16);

        additionalInfoPanel.add(txtMoTa);
        additionalInfoPanel.add(txtDoTuoiGioiHan);

        formPanel.add(basicInfoPanel);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(productionInfoPanel);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(additionalInfoPanel);

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
                String maPhim = txtMaPhim.getText().trim();
                String tenPhim = txtTenPhim.getText().trim();
                LoaiPhim theLoai = (LoaiPhim) cboTheLoai.getSelectedItem();
                String thoiLuongStr = txtThoiLuong.getText().trim();
                String daoDien = txtDaoDien.getText().trim();
                String ngayKhoiChieuStr = txtNgayKhoiChieu.getText().trim();
                String moTa = txtMoTa.getText().trim();
                String ngonNgu = txtNgonNgu.getText().trim();
                String doTuoiGioiHanStr = txtDoTuoiGioiHan.getText().trim();
                String nuocSanXuat = txtNuocSanXuat.getText().trim();

                if (maPhim.isEmpty() || tenPhim.isEmpty() || thoiLuongStr.isEmpty() ||
                    ngayKhoiChieuStr.isEmpty() || doTuoiGioiHanStr.isEmpty() || theLoai == null) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đầy đủ các trường bắt buộc!");
                    return;
                }

                int thoiLuong = Integer.parseInt(thoiLuongStr);
                LocalDate ngayKhoiChieu = LocalDate.parse(ngayKhoiChieuStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                int doTuoiGioiHan = Integer.parseInt(doTuoiGioiHanStr);

                Phim phim = new Phim(maPhim, tenPhim, theLoai, thoiLuong, daoDien, ngayKhoiChieu, moTa, ngonNgu, doTuoiGioiHan, nuocSanXuat);

                if (phimDAO.themPhim(phim)) {
                    JOptionPane.showMessageDialog(dialog, "Đã thêm phim: " + tenPhim);
                    loadDataToTable();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Thêm phim thất bại!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập số hợp lệ cho Thời lượng hoặc Độ tuổi giới hạn!");
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(dialog, "Ngày khởi chiếu phải có định dạng yyyy-MM-dd!");
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

    private void showEditMovieDialog() {
        int selectedRow = tblPhim.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phim để sửa!");
            return;
        }

        String[] rowData = new String[10];
        for (int i = 0; i < 10; i++) {
            rowData[i] = modelPhim.getValueAt(selectedRow, i).toString();
        }

        JDialog dialog = new JDialog(this, "Sửa Thông Tin Phim", true);
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setBackground(new Color(45, 45, 45));

        JLabel lblTitle = new JLabel("SỬA THÔNG TIN PHIM", SwingConstants.CENTER);
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
        basicInfoPanel.setLayout(new GridLayout(2, 2, 15, 15));
        basicInfoPanel.setBackground(new Color(45, 45, 45));
        basicInfoPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), "Thông tin cơ bản", 
            0, 0, new Font("Segoe UI", Font.BOLD, 14), Color.LIGHT_GRAY));

        JTextField txtMaPhim = createTextField("Mã phim", 16);
        txtMaPhim.setText(rowData[0]);
        txtMaPhim.setEditable(false);
        JTextField txtTenPhim = createTextField("Tên phim", 16);
        txtTenPhim.setText(rowData[1]);
        
        JComboBox<LoaiPhim> cboTheLoai = new JComboBox<>();
        cboTheLoai.setBackground(new Color(60, 60, 60));
        cboTheLoai.setForeground(Color.WHITE);
        cboTheLoai.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        try {
            for (LoaiPhim lp : loaiPhimDAO.layTatCaLoaiPhim()) {
                cboTheLoai.addItem(lp);
                if (lp.getTenLoai().equals(rowData[2])) {
                    cboTheLoai.setSelectedItem(lp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(dialog, "Không thể tải danh sách thể loại: " + e.getMessage());
        }
        JPanel theLoaiPanel = new JPanel(new BorderLayout());
        theLoaiPanel.setBackground(new Color(45, 45, 45));
        theLoaiPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), "Thể loại", 
            0, 0, new Font("Segoe UI", Font.PLAIN, 12), Color.LIGHT_GRAY));
        theLoaiPanel.add(cboTheLoai, BorderLayout.CENTER);

        JTextField txtThoiLuong = createTextField("Thời lượng (phút)", 16);
        txtThoiLuong.setText(rowData[3]);

        basicInfoPanel.add(txtMaPhim);
        basicInfoPanel.add(txtTenPhim);
        basicInfoPanel.add(theLoaiPanel);
        basicInfoPanel.add(txtThoiLuong);

        JPanel productionInfoPanel = new JPanel();
        productionInfoPanel.setLayout(new GridLayout(2, 2, 15, 15));
        productionInfoPanel.setBackground(new Color(45, 45, 45));
        productionInfoPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), "Thông tin sản xuất", 
            0, 0, new Font("Segoe UI", Font.BOLD, 14), Color.LIGHT_GRAY));

        JTextField txtDaoDien = createTextField("Đạo diễn", 16);
        txtDaoDien.setText(rowData[4]);
        JTextField txtNgayKhoiChieu = createTextField("Ngày khởi chiếu (yyyy-MM-dd)", 16);
        txtNgayKhoiChieu.setText(rowData[5]);
        JTextField txtNgonNgu = createTextField("Ngôn ngữ", 16);
        txtNgonNgu.setText(rowData[7]);
        JTextField txtNuocSanXuat = createTextField("Nước sản xuất", 16);
        txtNuocSanXuat.setText(rowData[9]);

        productionInfoPanel.add(txtDaoDien);
        productionInfoPanel.add(txtNgayKhoiChieu);
        productionInfoPanel.add(txtNgonNgu);
        productionInfoPanel.add(txtNuocSanXuat);

        JPanel additionalInfoPanel = new JPanel();
        additionalInfoPanel.setLayout(new GridLayout(2, 1, 15, 15));
        additionalInfoPanel.setBackground(new Color(45, 45, 45));
        additionalInfoPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), "Thông tin bổ sung", 
            0, 0, new Font("Segoe UI", Font.BOLD, 14), Color.LIGHT_GRAY));

        JTextField txtMoTa = createTextField("Mô tả", 16);
        txtMoTa.setText(rowData[6]);
        JTextField txtDoTuoiGioiHan = createTextField("Độ tuổi giới hạn", 16);
        txtDoTuoiGioiHan.setText(rowData[8]);

        additionalInfoPanel.add(txtMoTa);
        additionalInfoPanel.add(txtDoTuoiGioiHan);

        formPanel.add(basicInfoPanel);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(productionInfoPanel);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(additionalInfoPanel);

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
                String maPhim = txtMaPhim.getText().trim();
                String tenPhim = txtTenPhim.getText().trim();
                LoaiPhim theLoai = (LoaiPhim) cboTheLoai.getSelectedItem();
                String thoiLuongStr = txtThoiLuong.getText().trim();
                String daoDien = txtDaoDien.getText().trim();
                String ngayKhoiChieuStr = txtNgayKhoiChieu.getText().trim();
                String moTa = txtMoTa.getText().trim();
                String ngonNgu = txtNgonNgu.getText().trim();
                String doTuoiGioiHanStr = txtDoTuoiGioiHan.getText().trim();
                String nuocSanXuat = txtNuocSanXuat.getText().trim();

                if (maPhim.isEmpty() || tenPhim.isEmpty() || thoiLuongStr.isEmpty() ||
                    ngayKhoiChieuStr.isEmpty() || doTuoiGioiHanStr.isEmpty() || theLoai == null) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đầy đủ các trường bắt buộc!");
                    return;
                }

                int thoiLuong = Integer.parseInt(thoiLuongStr);
                LocalDate ngayKhoiChieu = LocalDate.parse(ngayKhoiChieuStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                int doTuoiGioiHan = Integer.parseInt(doTuoiGioiHanStr);

                Phim phim = new Phim(maPhim, tenPhim, theLoai, thoiLuong, daoDien, ngayKhoiChieu, moTa, ngonNgu, doTuoiGioiHan, nuocSanXuat);

                if (phimDAO.capNhatPhim(phim)) {
                    JOptionPane.showMessageDialog(dialog, "Đã cập nhật phim: " + tenPhim);
                    loadDataToTable();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Cập nhật phim thất bại!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập số hợp lệ cho Thời lượng hoặc Độ tuổi giới hạn!");
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(dialog, "Ngày khởi chiếu phải có định dạng yyyy-MM-dd!");
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
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể tải danh sách phim: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new QuanLyPhim_GUI().setVisible(true));
    }
}