package com.bawei.admin.wdcinema.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bawei.admin.wdcinema.bean.MoviesByIdBean;
import com.bawei.admin.wdcinema.bean.Result;
import com.bawei.admin.wdcinema.core.ResultInfe;
import com.bawei.admin.wdcinema.presenter.MoviesByIdPresenter;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class MoviesByIdActivity extends AppCompatActivity implements CustomAdapt {
    private SharedPreferences sp;
    private MoviesByIdPresenter moviesByIdPresenter;

    @BindView(R.id.moviesbyid_name)
    TextView name;
    @BindView(R.id.moviesbyid_sim)
    SimpleDraweeView simpleDraweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_by_id);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        //调用sp，获取userID和sessionid
        sp = getSharedPreferences("login", MODE_PRIVATE);

        String sessionId = sp.getString("sessionId", "1");
        int userId = sp.getInt("userId", 1);

        moviesByIdPresenter = new MoviesByIdPresenter(new MoviesById());
        moviesByIdPresenter.request(userId, sessionId, Integer.parseInt(id));
    }

    @OnClick(R.id.moviesbyid_finish)
    public void moviesbyid_finish() {
        finish();
    }

    @OnClick(R.id.moviesbyid_xq)
    public void moviesbyid_xq(){
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    private class MoviesById implements ResultInfe<Result> {
        @Override
        public void success(Result data) {
            MoviesByIdBean result = (MoviesByIdBean) data.getResult();
            name.setText(result.getName());
            simpleDraweeView.setImageURI(Uri.parse(result.getImageUrl()));
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }
}
