package com.royll.varnamegodie;

import com.google.gson.Gson;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.royll.varnamegodie.dialog.SelectDialog;
import com.royll.varnamegodie.dialog.SelectTextCallBack;
import com.royll.varnamegodie.net.HttpRequest;
import com.royll.varnamegodie.net.TranslateBean;
import com.royll.varnamegodie.utils.StringTypeUtil;
import org.apache.http.util.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Roy on 2016/7/11.
 */
public class VarNameTranslate extends AnAction {

    private Editor mEditor;
    private Project mProject;
    private String mUrl = "http://fanyi.youdao.com/openapi.do";

    @Override
    public void actionPerformed(AnActionEvent e) {
        mEditor = e.getData(PlatformDataKeys.EDITOR);
        mProject = e.getData(PlatformDataKeys.PROJECT);
        String selectText = mEditor.getSelectionModel().getSelectedText();
        if (!TextUtils.isEmpty(selectText)) {
            int type = StringTypeUtil.getStringType(selectText);
            switch (type) {
                case StringTypeUtil.ONLY_HAS_CHINESE:
                    requestTranslate(selectText);
                    break;
                case StringTypeUtil.ONLY_HAS_ENGLISH:
                    showChooseDialog(selectText);
                    break;
                case StringTypeUtil.BOTH_CH_ENG:
                    Messages.showMessageDialog("不支持中英混合", "Information", Messages.getInformationIcon());
                    break;
                default:
                    break;
            }
        }


    }

    /**
     * 中文请求翻译
     *
     * @param text
     */
    private void requestTranslate(String text) {
        String params = null;
        try {
            params = "keyfrom=testtranlate1rrrr&key=1257547986&type=data&doctype=json&version=1.1&only=translate&q=" + URLEncoder.encode(text, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String res = HttpRequest.sendGet(mUrl, params);
        Gson gson = new Gson();
        TranslateBean mRes = gson.fromJson(res, TranslateBean.class);
        if (mRes.getErrorCode() == 0) {
            showChooseDialog(mRes.getTranslation().get(0));
        } else {
            showErrorMsg(mRes.getErrorCode());
        }
    }

    private void changeSelectText(String text) {
        Document document = mEditor.getDocument();
        SelectionModel selectionModel = mEditor.getSelectionModel();

        final int start = selectionModel.getSelectionStart();
        final int end = selectionModel.getSelectionEnd();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                document.replaceString(start, end, text);
            }
        };
        WriteCommandAction.runWriteCommandAction(mProject, runnable);
        selectionModel.removeSelection();
    }

    private void showChooseDialog(String selectText) {
        SelectDialog dialog = new SelectDialog(selectText, new SelectTextCallBack() {
            @Override
            public void onSelectText(String text) {
                changeSelectText(text);
            }
        });
        dialog.setVisible(true);

    }

    private void showErrorMsg(int errorcode) {
        String msg;
        switch (errorcode) {
            case 20:
                msg = "要翻译的文本过长";
                break;
            case 30:
                msg = "无法进行有效的翻译";
                break;
            case 40:
                msg = "不支持的语言类型";
                break;
            case 50:
                msg = "无效的key";
                break;
            case 60:
                msg = "无词典结果";
                break;
            default:
                msg = "未知错误";
                break;
        }
        Messages.showMessageDialog(msg, "Information", Messages.getInformationIcon());
    }
}
