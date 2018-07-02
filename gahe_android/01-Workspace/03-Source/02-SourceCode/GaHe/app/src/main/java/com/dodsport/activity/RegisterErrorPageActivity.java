package com.dodsport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.dodsport.R;
import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 注册错误页面
 */
public class RegisterErrorPageActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.tvPhone)
    TextView mTvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_error_page);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("错误提示");

        mHeadIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterErrorPageActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        /**
         * 拨打电话
         * */
        mTvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String num = mTvPhone.getText().toString();
//                Intent intent=new Intent("android.intent.action.CALL", Uri.parse("tel:"+num));
//                startActivity(intent);
            }
        });
    }
}
