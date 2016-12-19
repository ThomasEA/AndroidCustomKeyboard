package me.worksit.keyboardapp.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import java.math.BigDecimal;

import me.worksit.keyboardapp.R;

/**
 * Created by Everton on 19/12/2016.
 */
public class Dialog {

    public void showKeyboardBox(Context context) {
        DialogKeyboardBox kbDialog = new DialogKeyboardBox(context);
        kbDialog.show();
    }

}
