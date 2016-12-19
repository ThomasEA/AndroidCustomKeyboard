package me.worksit.keyboardapp.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import java.math.BigDecimal;

import me.worksit.keyboardapp.R;

public class DialogKeyboardBox {

    private Context context;
    private AlertDialog dialog;
    private View customView;
    private DecimalKeyboard keyboardView;
    private Keyboard keyboard;
    private EditText textInputBox;
    private CustomKeyboardActionListener keyboardActionListener;

    private int CODE_BACKSPACE = -1;

    public DialogKeyboardBox(Context context) {//}, OnClickListenerPlus clickCallback) {
        this.context = context;
        //this.clickCallback = clickCallback;
    }

    public void show() {
        LayoutInflater inflater = LayoutInflater.from(this.context);

        customView = inflater.inflate(R.layout.dialog, null);

        AlertDialog.Builder builder;

        builder = new AlertDialog.Builder(this.context);
        dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setView(customView, 0, 0, 0, 0);

        dialog.setCancelable(false);

        textInputBox = (EditText) customView.findViewById(R.id.keyboard_input);
        textInputBox.setVisibility(View.VISIBLE);

        keyboardActionListener = new CustomKeyboardActionListener(textInputBox);

        keyboardView = (DecimalKeyboard) customView.findViewById(R.id.keyboard_keys);
        keyboardView.setInputType(DecimalKeyboard.EnumInputType.Decimal);
        keyboardView.setOnKeyboardActionListener(keyboardActionListener);

        keyboard = new Keyboard(this.context, R.xml.decimal);

        keyboardView.setKeyboard(keyboard);
        keyboardView.setPreviewEnabled(false);

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        dialog.show();
    }

    private class CustomKeyboardActionListener implements KeyboardView.OnKeyboardActionListener {

        private BigDecimal retorno = BigDecimal.ZERO;
        private EditText editText;

        public CustomKeyboardActionListener(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {

            // Get the EditText and its Editable
            View focusCurrent = dialog.getWindow().getCurrentFocus();

            if(editText == null && (focusCurrent==null || focusCurrent.getClass()!=EditText.class) )
                return;

            if (editText == null) {
                editText = (EditText) focusCurrent;
            }

            Editable editable = editText.getText();

            int start = editText.getSelectionStart();

            // Handle key
            if( primaryCode == CODE_BACKSPACE ) {
                if( editable!=null && start>0 ) editable.delete(start - 1, start);
            }
            else{
                if (editable.toString().length() < 8) {
                    String textoAtual = editable.toString();

                    if (primaryCode == 46) { //ASCII CODE PARA VIRGULA
                        if (textoAtual.trim().length() > 0 && !textoAtual.contains(".")) {
                            editable.insert(start, Character.toString((char) primaryCode));
                        }
                    }
                    else {
                        editable.insert(start, Character.toString((char) primaryCode));
                    }
                }
            }

            if (editable == null || editable.toString().isEmpty())
                retorno = null;
            else
                retorno = new BigDecimal(Double.parseDouble(editable.toString()));
        }

        @Override public void onPress(int arg0) {
        }

        @Override public void onRelease(int primaryCode) {
        }

        @Override public void onText(CharSequence text) {
        }

        @Override public void swipeDown() {
        }

        @Override public void swipeLeft() {
        }

        @Override public void swipeRight() {
        }

        @Override public void swipeUp() {
        }
    }

}
