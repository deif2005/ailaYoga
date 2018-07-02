package com.dodsport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dodsport.R;
import com.dodsport.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Administrator
 *         <p>
 *         修改用户名称
 */
public class ReviseNameActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.evName)
    EditText mEvName;
    @Bind(R.id.llConfirm)
    LinearLayout mLlConfirm;

    private static final int REQUEST_PREVIEW = 30;
    private String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise_name);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        if (!TextUtils.isEmpty(name)){
            mEvName.setText(name);
            mEvName.setSelection(mEvName.getText().length());
        }

    }

    @OnClick({R.id.head_ivBack, R.id.llConfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.llConfirm:
                if (mEvName.getText().toString().trim().equals("null")){
                    ToastUtils.showToCenters(this,"请输入正确的名称!",1000);
                    return;
                }

                Intent intent = new Intent();
                if (!name.equals(mEvName.getText().toString().trim().toString())){
                    intent.putExtra("name",mEvName.getText().toString().trim());
                }else {
                    intent.putExtra("name","null");
                }
                setResult(REQUEST_PREVIEW,intent);
                finish();
                break;
            default:
                break;
        }
    }
}
