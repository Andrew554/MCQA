package cn.edu.cqut.utils;

import cn.edu.cqut.mcqa.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;

/***
 * @author luojianhua
 *	自定义的数据加载对话框工具类
 */
public class MyAlertLoadingDialog {
	
	private Dialog mDialog = null;
	
	public MyAlertLoadingDialog(Context mContext, String message) {
		 mDialog = new AlertDialog.Builder(mContext).create();
	}
	
	// 显示对话框
	public void showLoadingDialog() {
		OnKeyListener keyListener = new OnKeyListener()
        {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_SEARCH)
                {
                    return true;
                }
                return false;
            }
        };
        
        mDialog.setOnKeyListener(keyListener);
        mDialog.show();
        // 给对话框设置布局
        mDialog.setContentView(R.layout.loading_process_dialog_anim);
	}
	
	// 取消对话框
	public void closeLoadingDialog() {
		if(mDialog != null) {
			mDialog.dismiss();
		}
	}
}
