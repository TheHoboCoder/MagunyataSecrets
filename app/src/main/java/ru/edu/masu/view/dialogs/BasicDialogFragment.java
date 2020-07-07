package ru.edu.masu.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import ru.edu.masu.R;

/** Базовый класс для всплывающих диалогов.
 * Содержит кнопку для закрытия диалога и кнопку для произвольного действия, которое он должен выполнять.
 * Чтобы создать свой диалог, достаточно создать разметку для контента диалога
 *
 * */
public abstract class BasicDialogFragment extends DialogFragment implements View.OnClickListener {

    private static final String ACTION_TEXT = "ACTION_TEXT";
    private static final String CONTENT_LAYOUT_ID = "CONTENT_LAYOUT_ID";

    private int contentLayoutId;
    private int actionTextId;

    protected static Bundle packArgs(int contentLayout, int actionText){
        Bundle args = new Bundle();
        args.putInt(ACTION_TEXT, actionText);
        args.putInt(CONTENT_LAYOUT_ID, contentLayout);
        return args;
    }

    protected void unpackArgs(Bundle args){
        contentLayoutId = args.getInt(CONTENT_LAYOUT_ID);
        actionTextId = args.getInt(ACTION_TEXT);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            unpackArgs(getArguments());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_basic,container,false);
        FrameLayout contentContainer = view.findViewById(R.id.contentContainer);
        View contentView = inflater.inflate(contentLayoutId,contentContainer,true);
        Button closeBtn = view.findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(this);
        Button actionBtn = view.findViewById(R.id.actionBtn);
        actionBtn.setOnClickListener(this);
        actionBtn.setText(getString(actionTextId));
        onContentViewCreated(contentView);
        Dialog d = getDialog();
        if(d != null){
            Window w = d.getWindow();
            if(w != null){
                w.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }
        return view;
    }

    protected abstract void onContentViewCreated(View view);

    protected void onCloseBtnClick(View v){
        this.dismiss();
    }

    protected void onActionBtnClick(View v){
        this.dismiss();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.actionBtn:
                onActionBtnClick(v);
                break;
            case R.id.closeBtn:
                onCloseBtnClick(v);
                break;
        }
    }
}
