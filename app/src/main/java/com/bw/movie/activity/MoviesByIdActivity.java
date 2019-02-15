package com.bw.movie.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.FilmReviewAdapter;
import com.bw.movie.adapter.StagePhotoAdapter;
import com.bw.movie.adapter.YGAdapter;
import com.bw.movie.bean.FilmReviewBean;
import com.bw.movie.bean.FindCommentReply;
import com.bw.movie.bean.LoginSubBean;
import com.bw.movie.bean.MoviesByIdBean;
import com.bw.movie.bean.MoviesDetailBean;
import com.bw.movie.bean.Result;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.core.utils.Constant;
import com.bw.movie.greendao.DaoMaster;
import com.bw.movie.greendao.DaoSession;
import com.bw.movie.greendao.LoginSubBeanDao;
import com.bw.movie.presenter.CommentReplyPresenter;
import com.bw.movie.presenter.FilmReviewPresenter;
import com.bw.movie.presenter.FindCommentPresenter;
import com.bw.movie.presenter.MovieAttListPresenter;
import com.bw.movie.presenter.MovieCommentGreatPresenter;
import com.bw.movie.presenter.MovieCommentPresenter;
import com.bw.movie.presenter.MoviesByIdPresenter;
import com.bw.movie.presenter.MoviesDetailPresenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import me.jessyan.autosize.internal.CustomAdapt;

public class MoviesByIdActivity extends WDActivity implements XRecyclerView.LoadingListener ,CustomAdapt {
    private MoviesByIdPresenter moviesByIdPresenter;
    @BindView(R.id.xinxin)
    ImageView xinxin;
    @BindView(R.id.moviesbyid_name)
    TextView name;
    @BindView(R.id.moviesbyid_sim)
    SimpleDraweeView simpleDraweeView;
    private View contentView;
    private View contentView2;
    private View contentView3;
    private View contentView4;
    private Dialog bottomDialog;
    private Dialog bottomDialog1;
    private MoviesDetailPresenter moviesDetailPresenter;
    private String id;
    private YGAdapter ygAdapter;
    private SimpleDraweeView simpleDraweeView1;
    private StagePhotoAdapter stagePhotoAdapter;
    private FilmReviewAdapter filmReviewAdapter;
    private int page = 1;
    private FilmReviewPresenter filmReviewPresenter;
    private XRecyclerView filmreview_recycler;
    private TextView popupwindow_detalis_daoyan;
    private TextView popupwindow_detalis_shichang;
    private TextView popupwindow_detalis_chandi;
    private TextView plot;
    private TextView popupwindow_detalis_leixing;
    private int id1;
    private String names;
    private String movieTypes;
    private String placeOrigin;
    private String duration;
    private String director;
    private String imageUrl;
    private View pl;
    private Button button;
    private EditText editText;
    private MovieCommentPresenter movieCommentPresenter;
    private String sessionId;
    private int userId;
    private ShineButton imageView;
    private List<LoginSubBean> list;
    private TextView textview;
    private View root;
    private EditText editTexts;
    private TextView send_text;
    private CommentReplyPresenter commentReplyPresenter;
    private int comment;
    private FindCommentPresenter findCommentPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movies_by_id;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        DaoSession daoSession = DaoMaster.newDevSession(MoviesByIdActivity.this, LoginSubBeanDao.TABLENAME);
        LoginSubBeanDao loginSubBeanDao = daoSession.getLoginSubBeanDao();
        list = loginSubBeanDao.queryBuilder()
                .where(LoginSubBeanDao.Properties.Statu.eq("1"))
                .build().list();
        if (list.size() > 0) {
            LoginSubBean loginSubBean = list.get(0);
            userId = loginSubBean.getId();
            sessionId = loginSubBean.getSessionId();
        }
        //回复列表
        findCommentPresenter = new FindCommentPresenter(new findComment());

        root = View.inflate(this, R.layout.comment_movie, null);
        editTexts = root.findViewById(R.id.commen_eid);
        send_text = root.findViewById(R.id.send_text);
        contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_normal, null);
        contentView2 = LayoutInflater.from(this).inflate(R.layout.item_yg, null);
        contentView3 = LayoutInflater.from(this).inflate(R.layout.item_stagephoto, null);
        contentView4 = LayoutInflater.from(this).inflate(R.layout.item_filmreview, null);
        pl = LayoutInflater.from(this).inflate(R.layout.comment_popupwindow, null);

        bottomDialog = new Dialog(this, R.style.BottomDialog);
        bottomDialog1 = new Dialog(this, R.style.BottomDialog);

        bottomDialog1.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getRepeatCount() == 0)
                    bottomDialog1.cancel();
                return false;
            }
        });

        editText = pl.findViewById(R.id.et_discuss);
        button = pl.findViewById(R.id.tv_confirm);

        //回复评论
        commentReplyPresenter = new CommentReplyPresenter(new CommentView());


        simpleDraweeView1 = contentView.findViewById(R.id.popupwindow_detalis_sdvone);
        popupwindow_detalis_daoyan = contentView.findViewById(R.id.popupwindow_detalis_daoyan);
        popupwindow_detalis_shichang = contentView.findViewById(R.id.popupwindow_detalis_shichang);
        popupwindow_detalis_chandi = contentView.findViewById(R.id.popupwindow_detalis_chandi);
        popupwindow_detalis_leixing = contentView.findViewById(R.id.popupwindow_detalis_leixing);
        plot = contentView.findViewById(R.id.plot);
        moviesDetailPresenter = new MoviesDetailPresenter(new MoviesDetail());

        moviesByIdPresenter = new MoviesByIdPresenter(new MoviesById());
        moviesByIdPresenter.request(userId, sessionId, Integer.parseInt(id));

        filmReviewPresenter = new FilmReviewPresenter(new FilmReview());
        filmReviewPresenter.request(userId, sessionId, Integer.parseInt(id), page, 10000000);

        RecyclerView recyclerView = contentView2.findViewById(R.id.yg_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ygAdapter = new YGAdapter(this);
        recyclerView.setAdapter(ygAdapter);

        RecyclerView stagePhoto_recycler = contentView3.findViewById(R.id.stagephoto_recycler);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        stagePhoto_recycler.setLayoutManager(staggeredGridLayoutManager);
        stagePhotoAdapter = new StagePhotoAdapter(this);
        stagePhoto_recycler.setAdapter(stagePhotoAdapter);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        filmreview_recycler = contentView4.findViewById(R.id.filmreview_recycler);
        filmreview_recycler.setLayoutManager(linearLayoutManager1);
        filmReviewAdapter = new FilmReviewAdapter(this);
        filmreview_recycler.setAdapter(filmReviewAdapter);
        filmreview_recycler.setLoadingListener(this);
        filmreview_recycler.setLoadingMoreEnabled(false);
        filmreview_recycler.setPullRefreshEnabled(true);
        movieCommentPresenter = new MovieCommentPresenter(new MovieComment());
        if (list.size() > 0) {
            moviesDetailPresenter.request(userId, sessionId, Integer.parseInt(id));
        } else {
            moviesDetailPresenter.request(Integer.parseInt(id));
        }


        contentView.findViewById(R.id.popupwindow_detalis_sdvtwo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });

        contentView2.findViewById(R.id.yg_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                // TODO: 2019/1/31  DISMISS
                ygAdapter.getStop();
            }
        });
        bottomDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                ygAdapter.getStop();
            }
        });
        contentView3.findViewById(R.id.stagephoto_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        contentView4.findViewById(R.id.filmreview_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        //影评
        contentView4.findViewById(R.id.comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show1(pl);
            }
        });
        bottomDialog1.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getRepeatCount() == 0)
                    bottomDialog1.cancel();
                return false;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString().trim();
                movieCommentPresenter.request(userId, sessionId, Integer.parseInt(id), str);
            }
        });


        filmReviewAdapter.setOnClick(new FilmReviewAdapter.OnClick() {
            @Override
            public void onclick(ShineButton like, int commentId, TextView text, int great) {
                MovieCommentGreatPresenter movieCommentGreatPresenter = new MovieCommentGreatPresenter(new MovieCommentGreat());
                imageView = like;
                textview = text;
                textview.setText(++great + "");
                if (list.size() > 0) {
                    movieCommentGreatPresenter.request(userId, sessionId, commentId);
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MoviesByIdActivity.this);
                    alert.setTitle("提示");
                    alert.setMessage("当前未登录是否去登陆");
                    alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(MoviesByIdActivity.this, LoginActivity.class));
                        }
                    });
                    alert.setNegativeButton("取消", null);
                    alert.show();
                }
            }
        });

        //回复评论
        filmReviewAdapter.setOnClickListener(new FilmReviewAdapter.onClickListener() {
            @Override
            public void onClickListener(int commentId) {
                show1(root);
                comment = commentId;
            }
        });

        //查看回复列表
        filmReviewAdapter.setLookCommentList(new FilmReviewAdapter.LookCommentList() {
            @Override
            public void LookComment(int commentId) {
                findCommentPresenter.request(userId, sessionId, commentId, page, 10000);
            }
        });

        send_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commContent = editTexts.getText().toString();
                if (list.size() > 0) {
                    commentReplyPresenter.request(userId, sessionId, comment, commContent);
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MoviesByIdActivity.this);
                    alert.setTitle("提示");
                    alert.setMessage("当前未登录是否去登陆");
                    alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(MoviesByIdActivity.this, LoginActivity.class));
                        }
                    });
                    alert.setNegativeButton("取消", null);
                    alert.show();
                }
            }
        });
    }

    @Override
    protected void destoryData() {
        moviesDetailPresenter.unBind();
        filmReviewPresenter.unBind();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            ygAdapter.getrRelease();
            ygAdapter.getStop();
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.goupay)
    public void goupay() {
        Intent intent = new Intent(this, CinemasListByMovieIdActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", names);
        intent.putExtra("director", director);
        intent.putExtra("duration", duration);
        intent.putExtra("placeOrigin", placeOrigin);
        intent.putExtra("movieTypes", movieTypes);
        intent.putExtra("imageUrl", imageUrl);
        startActivity(intent);
    }

    @OnClick(R.id.moviesbyid_finish)
    public void moviesbyid_finish() {
        finish();
    }

    @OnClick(R.id.moviesbyid_xq)
    public void moviesbyid_xq() {
        show(contentView);
    }

    @OnClick(R.id.moviesbyid_yg)
    public void moviesbyid_yg() {
        show(contentView2);
    }

    @OnClick(R.id.moviesbyid_photo)
    public void moviesbyid_photo() {
        show(contentView3);
    }

    @OnClick(R.id.moviesbyid_filmreview)
    public void moviesbyid_filmreview() {
        show(contentView4);
    }

    @OnClick(R.id.xinxin)
    public void followMovie() {
        MovieAttListPresenter movieAttListPresenter = new MovieAttListPresenter(new FollowMovie());
        if (list.size() > 0) {
            movieAttListPresenter.request(userId, sessionId, Integer.parseInt(id));
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(MoviesByIdActivity.this);
            alert.setTitle("提示");
            alert.setMessage("当前未登录是否去登陆");
            alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(MoviesByIdActivity.this, LoginActivity.class));
                }
            });
            alert.setNegativeButton("取消", null);
            alert.show();
        }
    }

    private void show(View contentViewss) {
        bottomDialog.setContentView(contentViewss);
        ViewGroup.LayoutParams layoutParams = contentViewss.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentViewss.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

    private void show1(View contentViewss) {
        bottomDialog1.setContentView(contentViewss);
        ViewGroup.LayoutParams layoutParams = contentViewss.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentViewss.setLayoutParams(layoutParams);
        bottomDialog1.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog1.setCanceledOnTouchOutside(true);
        bottomDialog1.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog1.show();
    }

    //上下拉
    @Override
    public void onRefresh() {
        page = 1;
        filmReviewAdapter.remove();
        filmReviewPresenter.request(userId, sessionId, Integer.parseInt(id), page, 10000000);
    }

    @Override
    public void onLoadMore() {
        page++;
        filmReviewPresenter.request(userId, sessionId, Integer.parseInt(id), page, 10000000);
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
            id1 = result.getId();
            names = result.getName();
            name.setText(names);
            simpleDraweeView.setImageURI(Uri.parse(result.getImageUrl()));
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }

    private class MoviesDetail implements ResultInfe<Result> {
        @Override
        public void success(Result data) {
            MoviesByIdBean result = (MoviesByIdBean) data.getResult();
            imageUrl = result.getImageUrl();
            simpleDraweeView1.setImageURI(Uri.parse(imageUrl));
            director = result.getDirector();
            duration = result.getDuration();
            placeOrigin = result.getPlaceOrigin();
            movieTypes = result.getMovieTypes();
            popupwindow_detalis_daoyan.setText("导演：" + director);
            popupwindow_detalis_shichang.setText("时长：" + duration);
            popupwindow_detalis_chandi.setText("产地：" + placeOrigin);
            popupwindow_detalis_leixing.setText("产地：" + movieTypes);
            plot.setText(result.getSummary());
            List posterList = result.getPosterList();
            List<MoviesDetailBean> shortFilmList = (List<MoviesDetailBean>) result.getShortFilmList();
            ygAdapter.addItem(shortFilmList);
            ygAdapter.notifyDataSetChanged();
            stagePhotoAdapter.addItem(posterList);
            stagePhotoAdapter.notifyDataSetChanged();
            if (result.getFollowMovie() == 1) {
                xinxin.setImageResource(R.drawable.com_icon_collection_selected);
            } else {
                xinxin.setImageResource(R.drawable.weiguanzhu);
            }

        }

        @Override
        public void errors(Throwable throwable) {

        }
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        ygAdapter.getStop();
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ygAdapter.getrRelease();
        ygAdapter.getStop();
    }

    /**
     * 回复评论
     */

    private class CommentView implements ResultInfe<Result> {

        @Override
        public void success(Result data) {
            Toast.makeText(MoviesByIdActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("0000")) {
                bottomDialog1.dismiss();
                editTexts.setText(null);
            }
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }

    /**
     * 点赞返回数据
     */
    private class FilmReview implements ResultInfe<Result> {
        @Override
        public void success(Result data) {
            List<FilmReviewBean> result = (List<FilmReviewBean>) data.getResult();
            filmReviewAdapter.addItem(result);
            filmReviewAdapter.notifyDataSetChanged();
            filmreview_recycler.loadMoreComplete();
            filmreview_recycler.refreshComplete();
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }


    private class MovieComment implements ResultInfe<Result> {
        @Override
        public void success(Result data) {
            Toast.makeText(MoviesByIdActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
            bottomDialog1.dismiss();
            filmReviewAdapter.remove();
            filmReviewPresenter.request(userId, sessionId, Integer.parseInt(id), 1, 10000000);
            editText.setText(null);
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }

    //点赞
    private class MovieCommentGreat implements ResultInfe<Result> {
        @Override
        public void success(Result data) {
            Toast.makeText(MoviesByIdActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }

    private class FollowMovie implements ResultInfe<Result> {
        @Override
        public void success(Result data) {
            Toast.makeText(MoviesByIdActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
            moviesDetailPresenter.request(userId, sessionId, Integer.parseInt(id));
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }

    /**
     * 回复列表
     */
    private class findComment implements ResultInfe {

        @Override
        public void success(Object data) {
            Result result = (Result) data;
            List<FindCommentReply> findCommentReplies = (List<FindCommentReply>) result.getResult();
            Toast.makeText(MoviesByIdActivity.this, "" + findCommentReplies.get(0).getReplyUserName(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }
}
