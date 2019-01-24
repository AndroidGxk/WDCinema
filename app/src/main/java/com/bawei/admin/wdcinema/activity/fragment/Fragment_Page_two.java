package com.bawei.admin.wdcinema.activity.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bawei.admin.wdcinema.bean.RecommBean;
import com.bawei.admin.wdcinema.bean.Result;
import com.bawei.admin.wdcinema.core.ResultInfe;
import com.bawei.admin.wdcinema.presenter.RecomMoviePresenter;
import com.bw.movie.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;


public class Fragment_Page_two extends Fragment implements ResultInfe {
    @BindView(R.id.recommend)
    Button recommend;
    @BindView(R.id.nearby)
    Button nearby;
    @BindView(R.id.cinemarecycleview)
    XRecyclerView cinemarecycleview;
    private boolean recommcheck = true;
    private boolean nearbycheck = false;
    private RecomMoviePresenter recomMoviePresenter;
    private SharedPreferences sp;
    private int page = 1;
    private final static int count = 10;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_page_two, null);
        ButterKnife.bind(this, view);
        recommend.setBackgroundResource(R.drawable.btn_gradient);
        recomMoviePresenter = new RecomMoviePresenter(this);
        sp = getActivity().getSharedPreferences("login", MODE_PRIVATE);
        String sessionId = sp.getString("sessionId", "1");
        int userId = sp.getInt("userId", 1);
        recomMoviePresenter.request(userId, sessionId, page, count);
        return view;
    }

    @OnClick(R.id.recommend)
    public void recommend() {
        boolean recommcheck = true;
        if (recommcheck) {
            recommend.setBackgroundResource(R.drawable.btn_gradient);
            nearbycheck = false;
            nearby.setBackgroundResource(R.drawable.btn_false);
        }
    }

    @OnClick(R.id.nearby)
    public void nearby() {
        nearbycheck = true;
        if (nearbycheck) {
            nearby.setBackgroundResource(R.drawable.btn_gradient);
            recommcheck = false;
            recommend.setBackgroundResource(R.drawable.btn_false);
        }
    }

    /**
     * 推荐影院
     *
     * @param data
     */
    @Override
    public void success(Object data) {
        Result result = (Result) data;
        List<RecommBean> recommList = (List<RecommBean>) result.getResult();
    }

    @Override
    public void errors(Throwable throwable) {

    }
}
