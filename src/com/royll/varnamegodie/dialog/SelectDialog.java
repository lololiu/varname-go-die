package com.royll.varnamegodie.dialog;

import com.royll.varnamegodie.utils.GenerateCode;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class SelectDialog extends JDialog {
    private JPanel contentPane;
    private JList list1;

    private String mSelectedText;
    private String mChooseText;
    private SelectTextCallBack mCallBack;

    public SelectDialog(String selecttext, SelectTextCallBack callBack) {
        mSelectedText = selecttext;
        mCallBack = callBack;
        setPreferredSize(new Dimension(600, 400));
        setContentPane(contentPane);
        setModal(true);
        setLocationRelativeTo(null);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int Swing1x = 500;
        int Swing1y = 300;
        setBounds(screensize.width / 2 - Swing1x / 2, screensize.height / 2 - Swing1y / 2, Swing1x, Swing1y);
        Vector vector = GenerateCode.getGenerateCode(selecttext);
        list1.setListData(vector);
        list1.setSelectedIndex(0);
        mChooseText = (String) vector.get(0);

        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                mChooseText = "" + list1.getSelectedValue();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        /**
         * 增加键盘处理事件
         */
        list1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    onCancel();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    onOK();
                }
            }
        });

        /**
         * 增加鼠标点击处理事件
         */
        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    onOK();
                }
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        if (mCallBack != null) {
            mCallBack.onSelectText(mChooseText);
        }
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
