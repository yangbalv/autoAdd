package GUI;

import com.alibaba.fastjson.JSONObject;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Welcome {
    public static void main(String[] args) {
        new Welcome();
    }

    public Welcome() {
        JFrame f1 = new JFrame();
        f1.setTitle("信息填写");
        f1.setBounds(400, 200, 400, 270);
        f1.setResizable(false);
        f1.invalidate(); // 保证组件是有效的布局
        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 实例化各组件
        Container con = f1.getContentPane(); //生成一个容器
        con.setLayout(new GridLayout(7, 1));  //设置容器布局
        JLabel welcome = new JLabel("在线运算计算器");
        JLabel usname = new JLabel();
        JLabel paswd = new JLabel();
        JTextField text1 = new JTextField(12);
        JPasswordField text2 = new JPasswordField(12);
        JButton login = new JButton("登陆");
        JButton regist = new JButton("注册");
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        JPanel p4 = new JPanel();
        //调用FocusListener方法，获取鼠标焦点，实现提示功能。
        text1.addFocusListener(new Tip(text1, "请输入账号："));
//        text2.addFocusListener(new Tip(text2, "请输入密码："));
        text2.addFocusListener(new Tip(text2, ""));
        welcome.setFont(new Font("宋体", Font.BOLD, 20));
        p1.add(welcome);
        con.add(p1);
        usname.setText("账号：");
        paswd.setText("密码：");
        p2.add(usname);
        p2.add(text1);
        con.add(p2);
        p3.add(paswd);
        p3.add(text2);
        con.add(p3);
        p4.add(login);


        login.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        boolean go = true;
//                        User user = new User();
                        System.out.println(text1.getText());
                        if (null == text1.getText() || text1.getText().equals("") || text1.getText().equals("请输入账号：")) {
                            System.out.println("********" + text1.getText() + "**********");
                            go = false;
                        }
                        if (go) {
                            System.out.println(text2.getText());
//                            String message = JSONObject.toJSONString(user);
//                            System.out.println(message);
//                            String res = HttpHelper.postHelper("http://localhost:8099/CalculatorOnline_server_war_exploded/userController/login", message);
//                            new Client().start();
//                            new CalculatorOnlineBody(user);
                            f1.setVisible(false);
                        }
                    }
                }
        );
        p4.add(regist);

        regist.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        boolean go = true;
//                        User user = new User();
                        System.out.println(text1.getText());
                        if (null == text1.getText() || text1.getText().equals("")) {
                            text1.addFocusListener(new Tip(text1, "请输入账号："));
                            go = false;
                        }
                        if (go) {
                            System.out.println(text2.getText());
//                            String message = JSONObject.toJSONString(user);
//                            System.out.println(message);
//                            String res = HttpHelper.postHelper("http://localhost:8099/CalculatorOnline_server_war_exploded/userController/regist", message);
//                            System.out.println(res);

                        }
                    }
                }
        );
        con.add(p4);
        f1.setVisible(true);

    }


}

