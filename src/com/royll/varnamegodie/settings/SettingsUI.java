package com.royll.varnamegodie.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.Messages;
import com.royll.varnamegodie.utils.PropertiesUtil;
import org.apache.http.util.TextUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by Roy on 2016/7/7.
 */
public class SettingsUI implements Configurable {

    private JCheckBox mCheckBox;
    private JPanel mPanel;
    private JButton mAddButton;
    private JButton mDelButton;
    private JTable table1;
    private JScrollPane mScrollPane;
    private String[] mSettingArray;
    private String mOldValue = "";
    private DefaultTableModel mDefaultTableMoadel;
    private boolean mCheckBoxState;

    @Nls
    @Override
    public String getDisplayName() {
        return "VarNameGoDie";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        reset();
        mAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) table1.getModel();
                model.addRow(new Object[]{"hello", ""});

            }
        });
        mDelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) table1.getModel();
                int totalRow = 0;
                for (int row : table1.getSelectedRows()) {
                    model.removeRow(row - totalRow);
                    totalRow++;
                }
            }
        });
        return mPanel;
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < table1.getModel().getRowCount(); i++) {
            String str = (String) table1.getModel().getValueAt(i, 1);
            if (!TextUtils.isEmpty(str)) {
                list.add(str);
            }
        }
        PropertiesUtil.saveProperties(list.toArray(new String[list.size()]));
        PropertiesUtil.saveCheckBoxSelectState(mCheckBox.isSelected());
    }

    @Override
    public void reset() {
        mCheckBoxState = PropertiesUtil.getCheckBoxSelectState();
        mCheckBox.setSelected(mCheckBoxState);
        setDataInTable(mDefaultTableMoadel);
    }

    @Override
    public void disposeUIResources() {

    }


    private void createUIComponents() {
        mSettingArray = PropertiesUtil.getProperties();
        mCheckBoxState = PropertiesUtil.getCheckBoxSelectState();
        mCheckBox = new JCheckBox("列表展示全大写，驼峰式，如（HELLO_WORLD_TEXT）");
        mCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        mCheckBox.setSelected(mCheckBoxState);
        mDefaultTableMoadel = new DefaultTableModel();
        setDataInTable(mDefaultTableMoadel);
        table1 = new JTable(mDefaultTableMoadel) {
            public void tableChanged(TableModelEvent e) {
                super.tableChanged(e);
                repaint();
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 ? true : false;
            }


        };
        table1.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    if (e.getLastRow() == -1) {
                        return;
                    }
                    String value = table1.getValueAt(e.getLastRow(), e.getColumn()).toString();
                    if (!value.contains("hello") && !value.contains("Hello") && !value.equals("")) {
                        Messages.showMessageDialog("转换字符必须包含hello或Hello", "Information", Messages.getInformationIcon());
                        table1.setValueAt(mOldValue, e.getLastRow(), e.getColumn());
                    }
                    for (int i = 0; i < table1.getModel().getRowCount(); i++) {
                        if (table1.getValueAt(i, 1).toString().equals(value) && e.getLastRow() != i) {
                            Messages.showMessageDialog("列表已包含该字符", "Information", Messages.getInformationIcon());
                            table1.setValueAt("", e.getLastRow(), e.getColumn());
                        }
                    }
                }
            }
        });
        table1.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                //记录进入编辑状态前单元格得数据
                mOldValue = table1.getValueAt(table1.getSelectedRow(), table1.getSelectedColumn()).toString();
            }

        });
        table1.getTableHeader().setPreferredSize(new Dimension(table1.getTableHeader().getWidth(), 35));
        table1.getColumnModel().getColumn(0).setPreferredWidth(35);
        table1.setRowHeight(25);

    }

    private void setDataInTable(DefaultTableModel dm) {
        if (mSettingArray == null) {
            return;
        }
        Object[][] object = new Object[mSettingArray.length][2];
        for (int i = 0; i < mSettingArray.length; i++) {
            for (int j = 0; j < 2; j++) {
                switch (j) {
                    case 0:
                        object[i][j] = "hello";
                        break;
                    case 1:
                        object[i][j] = mSettingArray[i];
                        break;
                    default:
                        break;
                }
            }
        }
        dm.setDataVector(object, new Object[]{"Before", "After"});
    }

}
