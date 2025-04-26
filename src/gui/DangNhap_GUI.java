//package gui;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//
//public class DangNhap_GUI extends JFrame {
//
//    // Khai báo các thành phần giao diện
//    private JLabel lblTitle, lblUsername, lblPassword, lblImage;
//    private JTextField txtUsername;
//    private JPasswordField txtPassword;
//    private JLabel errUsername, errPassword;
//    private JButton btnLogin, btnRegister, btnExit;
//    private JPanel pnlLeft, pnlRight;
//
//    public DangNhap_GUI() {
//        // Thiết lập cửa sổ
//        setTitle("Đăng Nhập Rạp Chiếu Phim");
//        setSize(650, 450);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        setResizable(false);
//        setLayout(new BorderLayout());
//        setVisible(true);
//
//        // Panel bên trái (hiển thị ảnh)
//        pnlLeft = new JPanel();
//        pnlLeft.setPreferredSize(new Dimension(250, 450));
//        pnlLeft.setBackground(Color.LIGHT_GRAY);
//
//        try {
//            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/img/login2.jpg"));
//            Image scaledImage = imageIcon.getImage().getScaledInstance(250, 450, Image.SCALE_SMOOTH);
//            lblImage = new JLabel(new ImageIcon(scaledImage));
//        } catch (Exception e) {
//            lblImage = new JLabel("Không tải được ảnh", SwingConstants.CENTER);
//            lblImage.setFont(new Font("Arial", Font.PLAIN, 16));
//        }
//
//        lblImage.setPreferredSize(new Dimension(250, 450));
//        pnlLeft.add(lblImage);
//        add(pnlLeft, BorderLayout.WEST);
//
//        // Panel bên phải (form đăng nhập)
//        pnlRight = new JPanel();
//        pnlRight.setBackground(Color.PINK);
//        pnlRight.setLayout(null);
//        add(pnlRight, BorderLayout.CENTER);
//
//        // Tiêu đề
//        lblTitle = new JLabel("ĐĂNG NHẬP", SwingConstants.CENTER);
//        lblTitle.setFont(new Font("Arial", Font.BOLD, 26));
//        lblTitle.setBounds(0, 30, 400, 40);
//        pnlRight.add(lblTitle);
//
//        // Nhãn và ô nhập tên tài khoản
//        lblUsername = new JLabel("Tên tài khoản:");
//        lblUsername.setFont(new Font("Arial", Font.PLAIN, 16));
//        lblUsername.setBounds(30, 100, 120, 30);
//        pnlRight.add(lblUsername);
//
//        txtUsername = new JTextField();
//        txtUsername.setBounds(150, 100, 200, 30);
//        pnlRight.add(txtUsername);
//
//        // Error label cho tên tài khoản
//        errUsername = new JLabel();
//        errUsername.setForeground(Color.RED);
//        errUsername.setFont(new Font("Arial", Font.ITALIC, 11));
//        errUsername.setBounds(150, 140, 200, 20); // Adjusted y to 140 (10px gap)
//        pnlRight.add(errUsername);
//
//        // Nhãn và ô nhập mật khẩu
//        lblPassword = new JLabel("Mật khẩu:");
//        lblPassword.setFont(new Font("Arial", Font.PLAIN, 16));
//        lblPassword.setBounds(30, 170, 120, 30); // Adjusted y to 170 (10px gap from errUsername)
//        pnlRight.add(lblPassword);
//
//        txtPassword = new JPasswordField();
//        txtPassword.setBounds(150, 170, 200, 30); // Adjusted y to 170
//        pnlRight.add(txtPassword);
//
//        // Error label cho mật khẩu
//        errPassword = new JLabel();
//        errPassword.setForeground(Color.RED);
//        errPassword.setFont(new Font("Arial", Font.ITALIC, 11));
//        errPassword.setBounds(150, 210, 200, 20); // Adjusted y to 210 (10px gap from txtPassword)
//        pnlRight.add(errPassword);
//
//        // Nút Đăng nhập
//        btnLogin = new JButton("Đăng nhập");
//        btnLogin.setFont(new Font("Arial", Font.PLAIN, 14));
//        btnLogin.setBounds(30, 250, 110, 35); // Adjusted y to 250 (20px gap from errPassword)
//        btnLogin.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (validData()) {
//                    String username = txtUsername.getText().trim();
//                    String password = new String(txtPassword.getPassword());
//
//                    TaiKhoan_DAO dao = new TaiKhoan_DAO();
//                    TaiKhoan tk = dao.timTaiKhoanTheoTen(username);
//
//                    if (tk != null && tk.getMatKhau().equals(password)) {
//                        JOptionPane.showMessageDialog(null, "Đăng nhập thành công!");
//                        dispose(); // Đóng cửa sổ đăng nhập
//                        new TrangChuRapChieuPhim_GUI().setVisible(true); // Mở giao diện chính
//                    } else {
//                        JOptionPane.showMessageDialog(null, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                    }
//                }
//            }
//        });
//
//        pnlRight.add(btnLogin);
//
//        // Nút Đăng ký
//        btnRegister = new JButton("Đăng ký");
//        btnRegister.setFont(new Font("Arial", Font.PLAIN, 14));
//        btnRegister.setBounds(145, 250, 100, 35); // Adjusted y to 250
//        btnRegister.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Đóng cửa sổ đăng nhập
//                dispose();
//                // Chuyển sang cửa sổ đăng ký
//                SwingUtilities.invokeLater(() -> {
//                    DangKy_GUI registerFrame = new DangKy_GUI();
//                    registerFrame.setVisible(true);
//                });
//            }
//        });
//        pnlRight.add(btnRegister);
//
//        // Nút Thoát
//        btnExit = new JButton("Thoát");
//        btnExit.setFont(new Font("Arial", Font.PLAIN, 14));
//        btnExit.setBounds(255, 250, 100, 35); // Adjusted y to 250
//        btnExit.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.exit(0);
//            }
//        });
//        pnlRight.add(btnExit);
//    }
//
//    // Hàm kiểm tra dữ liệu hợp lệ
//    private boolean validData() {
//        boolean isValid = true;
//
//        // Xóa các thông báo lỗi trước đó
//        errUsername.setText("");
//        errPassword.setText("");
//
//        // Kiểm tra tên tài khoản
//        String username = txtUsername.getText().trim();
//        if (username.isEmpty() || username.contains(" ")) {
//            errUsername.setText("Tên tài khoản không được trống hoặc chứa khoảng trắng.");
//            txtUsername.requestFocus();
//            isValid = false;
//        }
//
//        // Kiểm tra mật khẩu
//        String password = new String(txtPassword.getPassword());
//        if (password.length() < 8) {
//            errPassword.setText("Mật khẩu phải từ 8 ký tự trở lên.");
//            if (isValid) txtPassword.requestFocus();
//            isValid = false;
//        }
//
//        return isValid;
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new DangNhap_GUI();
//            }
//        });
//    }
//}

// Phiên bản hoàn chỉnh đã chỉnh sửa cho DangNhap_GUI
package gui;

import dao.TaiKhoan_DAO;
import entity.TaiKhoan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DangNhap_GUI extends JFrame {

    private JLabel lblTitle, lblUsername, lblPassword, lblImage;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JLabel errUsername, errPassword;
    private JButton btnLogin, btnRegister, btnExit;
    private JPanel pnlLeft, pnlRight;

    public DangNhap_GUI() {
        setTitle("Đăng Nhập Rạp Chiếu Phim");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Panel bên trái
        pnlLeft = new JPanel();
        pnlLeft.setPreferredSize(new Dimension(250, 450));
        pnlLeft.setBackground(Color.LIGHT_GRAY);
        try {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/img/login2.jpg"));
            Image scaledImage = imageIcon.getImage().getScaledInstance(250, 450, Image.SCALE_SMOOTH);
            lblImage = new JLabel(new ImageIcon(scaledImage));
        } catch (Exception e) {
            lblImage = new JLabel("Không tải được ảnh", SwingConstants.CENTER);
        }
        lblImage.setPreferredSize(new Dimension(250, 450));
        pnlLeft.add(lblImage);
        add(pnlLeft, BorderLayout.WEST);

        // Panel bên phải
        pnlRight = new JPanel(null);
        pnlRight.setBackground(Color.PINK);
        add(pnlRight, BorderLayout.CENTER);

        lblTitle = new JLabel("ĐĂNG NHẬP", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitle.setBounds(0, 30, 400, 40);
        pnlRight.add(lblTitle);

        lblUsername = new JLabel("Tên tài khoản:");
        lblUsername.setBounds(30, 100, 120, 30);
        pnlRight.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(150, 100, 200, 30);
        pnlRight.add(txtUsername);

        errUsername = new JLabel();
        errUsername.setForeground(Color.RED);
        errUsername.setBounds(150, 135, 200, 20);
        pnlRight.add(errUsername);

        lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setBounds(30, 170, 120, 30);
        pnlRight.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 170, 200, 30);
        pnlRight.add(txtPassword);

        errPassword = new JLabel();
        errPassword.setForeground(Color.RED);
        errPassword.setBounds(150, 205, 200, 20);
        pnlRight.add(errPassword);

        btnLogin = new JButton("Đăng nhập");
        btnLogin.setBounds(30, 250, 110, 35);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validData()) {
                    String tenTK = txtUsername.getText().trim();
                    String mk = new String(txtPassword.getPassword());

                    TaiKhoan_DAO dao = new TaiKhoan_DAO();
                    TaiKhoan tk = dao.dangNhap(tenTK, mk);

                    if (tk != null) {
                        JOptionPane.showMessageDialog(null, "Đăng nhập thành công!");
                        dispose();
                        new TrangChuRapChieuPhim_GUI().setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Tên tài khoản hoặc mật khẩu không đúng!");
                    }
                }
            }
        });
        pnlRight.add(btnLogin);

        btnRegister = new JButton("Đăng ký");
        btnRegister.setBounds(145, 250, 100, 35);
        btnRegister.addActionListener(e -> {
            dispose();
            new DangKy_GUI().setVisible(true);
        });
        pnlRight.add(btnRegister);

        btnExit = new JButton("Thoát");
        btnExit.setBounds(255, 250, 100, 35);
        btnExit.addActionListener(e -> System.exit(0));
        pnlRight.add(btnExit);
    }

    private boolean validData() {
        boolean isValid = true;
        errUsername.setText("");
        errPassword.setText("");

        String username = txtUsername.getText().trim();
        if (username.isEmpty() || username.contains(" ")) {
            errUsername.setText("Tên tài khoản không hợp lệ.");
            isValid = false;
        }

        String password = new String(txtPassword.getPassword());
        if (password.length() < 8) {
            errPassword.setText("Mật khẩu phải từ 8 ký tự.");
            isValid = false;
        }

        return isValid;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DangNhap_GUI().setVisible(true));
    }
}
