package com.android.housingonitoringagent.homeworryagent.pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.android.housingonitoringagent.homeworryagent.Const;
import com.android.housingonitoringagent.homeworryagent.R;
import com.android.housingonitoringagent.homeworryagent.User;
import com.android.housingonitoringagent.homeworryagent.beans.ShowHouseBean;
import com.android.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.android.housingonitoringagent.homeworryagent.utils.net.VolleyResponseListener;
import com.android.housingonitoringagent.homeworryagent.utils.net.VolleyStringRequest;
import com.android.housingonitoringagent.homeworryagent.utils.uikit.BGARefreshLayoutBuilder;
import com.android.housingonitoringagent.homeworryagent.utils.uikit.QBLToast;
import com.android.housingonitoringagent.homeworryagent.views.XAdapter;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Administrator on 2014/9/30.
 */

public class RecordFragment extends Fragment {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rvMain)
    RecyclerView rvMain;
    @Bind(R.id.refreshView)
    BGARefreshLayout refreshView;

    private XAdapter<ShowHouseBean.ContentBean> neighborAdapter;
    //    private XAdapter<String> neighborAdapter;
//    private XAdapter neighborAdapter;
    private boolean lastPage;
    private int pageIndex;
    private int selectedVillageIndex = -1;
    private List<String> villages;

    private static final int REQUEST_CODE_NEW_COMMENT = 0;

    public RecordFragment() {

    }

    private BaseActivity getThis() {
        return ((BaseActivity) getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View currentView = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, currentView);
        return currentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initDate();
    }

    private void initViews() {
        toolbar.setTitle(R.string.title_me);
        getThis().setSupportActionBar(toolbar);
        rvMain.setLayoutManager(new LinearLayoutManager(getActivity()));
//        neighborAdapter = new HomeNeighbourAdapter(this, new ArrayList<NeighbourListBean.NeighbourMessagesBean.ContentBean>());
        BGARefreshLayoutBuilder.init(getActivity(), refreshView, true);
       /* List<> list = new ArrayList<>();
        final ViewGroup.LayoutParams tvParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        neighborAdapter = new XAdapter<NeighbourListBean.NeighbourMessagesBean.ContentBean>(this, list, R.layout.item_home_neighbor) {
            @Override
            protected int getItemType(NeighbourListBean.NeighbourMessagesBean.ContentBean item) {
                List<NeighbourListBean.NeighbourMessagesBean.ContentBean.NeighbourCommentsBean> comments = item.getNeighbourComments();
                int count = 0;
                for (int i = 0; i < comments.size(); i++) {
                    count++;
                    List<NeighbourListBean.NeighbourMessagesBean.ContentBean.NeighbourCommentsBean.NeighbourReplysBean> replies = comments.get(i).getNeighbourReplys();
                    for (int j = 0; j < replies.size(); j++) {
                        if (replies.get(j).getId() != null && !replies.get(j).getId().isEmpty()) {
                            count++;
                        }
                    }
                }
                return count;
            }

            @Override
            public void creatingHolder(final CustomHolder holder, final List<NeighbourListBean.NeighbourMessagesBean.ContentBean> dataList, final int adapterPos, int viewType) {
                View.OnClickListener clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.comment_btn:
                                inputContentWindow = new InputContentWindow<String>(getThis(), dataList.get(holder.getAdapterPosition()).getId(), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        switch (v.getId()) {
                                            case R.id.send:
                                                String content = inputContentWindow.getEtContent().getText().toString();
                                                String id = ((String) inputContentWindow.getData());
                                                sendComment(id, CONTENT_TYPE_COMMENT, content);
//                                                inputContentWindow.dismiss();
                                                Toast.makeText(HomeNeighborActivity.this, "content:" + content + "\nid:" + id, Toast.LENGTH_SHORT).show();
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                });
                                inputContentWindow.showAtLocation(getThis().findViewById(R.id.llMain), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                                inputContentWindow.getEtContent().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        setInputMethodVisible(inputContentWindow.getEtContent(), true);
                                    }
                                }, 10);
                                break;
                            case R.id.delete:
                                String id = dataList.get(holder.getAdapterPosition()).getId();
                                Toast.makeText(HomeNeighborActivity.this, "id:" + id, Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.more_comment:
                                dataList.get(holder.getAdapterPosition()).setShowMore(!dataList.get(holder.getAdapterPosition()).isShowMore());
                                notifyItemChanged(holder.getAdapterPosition());
                                break;
                            default:
                                break;
                        }
                    }
                };
                holder.getView(R.id.neighbor_head);
                holder.getView(R.id.neighbor_name);
                holder.getView(R.id.content);
                holder.getView(R.id.date);
                NineGridlayout glNine = holder.getView(R.id.photos);

                holder.getView(R.id.delete).setOnClickListener(clickListener);
                holder.getView(R.id.comment_btn).setOnClickListener(clickListener);
                holder.getView(R.id.more_comment).setOnClickListener(clickListener);

                LinearLayout ll = holder.getView(R.id.llComments);

                View.OnClickListener clickListenerTextView = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommentForTextView item = (CommentForTextView) v.getTag();
                        if (item.getUserId().equals(User.getUserId())) {
                            return;
                        }
                        inputContentWindow = new InputContentWindow<>(getThis(), item, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String content = inputContentWindow.getEtContent().getText().toString();
                                String id = ((CommentForTextView) inputContentWindow.getData()).getNcId();

                                sendComment(id, CONTENT_TYPE_REPLY, content);
                                Toast.makeText(HomeNeighborActivity.this, "content:" + content + "\nid:" + id, Toast.LENGTH_SHORT).show();
//                                inputContentWindow.dismiss();
                            }
                        });
                        inputContentWindow.showAtLocation(getThis().findViewById(R.id.llMain), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                        inputContentWindow.getEtContent().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setInputMethodVisible(inputContentWindow.getEtContent(), true);
                            }
                        }, 10);
                    }
                };

                List<TextView> tvList = new ArrayList<>();
                for (int i = 0; i < viewType; i++) {
                    TextView tv = new TextView(getThis());
                    tv.setLayoutParams(tvParams);
                    tv.setVisibility(View.GONE);
                    ll.addView(tv);
                    tvList.add(tv);
                    tv.setOnClickListener(clickListenerTextView);
                }
                ll.setTag(tvList);
            }

            @Override
            public void bindingHolder(CustomHolder holder, List<NeighbourListBean.NeighbourMessagesBean.ContentBean> dataList, int pos) {

                SimpleDraweeView ivHead = holder.getView(R.id.neighbor_head);
                TextView tvName = holder.getView(R.id.neighbor_name);
                TextView tvContent = holder.getView(R.id.content);
                NineGridlayout nglPhotos = holder.getView(R.id.photos);
                TextView tvDate = holder.getView(R.id.date);
                TextView tvDelete = holder.getView(R.id.delete);
//                ImageView ivComment = holder.getView(R.id.comment_btn);
                ImageView ivMoreComment = holder.getView(R.id.more_comment);
                LinearLayout llComments = holder.getView(R.id.llComments);

                NeighbourListBean.NeighbourMessagesBean.ContentBean item = dataList.get(pos);
//                String item = dataList.get(pos);

                ivHead.setImageURI(Uri.parse(Const.SERVER + item.getUser().getAvatar()));
                tvContent.setText(item.getNmContent());
                tvName.setText(item.getUser().getName());
                tvDate.setText(DateUtil.DATE_FORMAT_SIMPLE.format(new Date(item.getNmDatetime())));

                if (User.getUserId() != null && User.getUserId().equals(item.getUser().getId())) {
                    tvDelete.setVisibility(View.VISIBLE);
                } else {
                    tvDelete.setVisibility(View.GONE);
                }
                final List<String> photoUrlList = new ArrayList<>();
//                final String[] photoUrls = new String[item.getNeighbourPictures().size()];
//                for (int i = 0; i < photoUrls.length; i++) {
//                    photoUrls[i] = item.getNeighbourPictures().get(i).getUrl();
//                }
                for (NeighbourListBean.NeighbourMessagesBean.ContentBean.NeighbourPicturesBean pic : item.getNeighbourPictures()) {
                    photoUrlList.add(pic.getUrl());
                }
//        new String[item.getNeighbourPictures().size()];
                String[] photoUrls = photoUrlList.toArray(new String[0]);
                if (photoUrls.length != 0) {
                    nglPhotos.setImagesData(photoUrls, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int itemPos = (int) v.getTag();
                            Intent intent = new Intent(getThis(), ImageBrowserActivity.class);
                            intent.putExtra("position", itemPos);
                            intent.putStringArrayListExtra("images", (ArrayList<String>) photoUrlList);
                            startActivity(intent);
                        }
                    });
                    nglPhotos.setVisibility(View.VISIBLE);
                } else {
                    nglPhotos.setVisibility(View.GONE);
                }

                List<CommentForTextView> commentList = new ArrayList<>();
                List<NeighbourListBean.NeighbourMessagesBean.ContentBean.NeighbourCommentsBean> comments = item.getNeighbourComments();
                for (int i = 0; i < comments.size(); i++) {
                    NeighbourListBean.NeighbourMessagesBean.ContentBean.NeighbourCommentsBean aComment = comments.get(i);
                    commentList.add(new CommentForTextView(aComment.getUser().getName(), aComment.getUser().getId(), "", aComment.getId(), aComment.getNcContent()));
                    List<NeighbourListBean.NeighbourMessagesBean.ContentBean.NeighbourCommentsBean.NeighbourReplysBean> replies = aComment.getNeighbourReplys();
                    for (int j = 0; j < replies.size(); j++) {
                        NeighbourListBean.NeighbourMessagesBean.ContentBean.NeighbourCommentsBean.NeighbourReplysBean aReply = replies.get(j);
                        if (aReply.getId() != null && !aReply.getId().isEmpty()) {
                            commentList.add(new CommentForTextView(aReply.getUser().getName(), aReply.getUserId(), aReply.getReplyName(), aComment.getId(), aReply.getNrContent()));
                        }
                    }
                }

                if (llComments.getTag() != null && llComments.getTag() instanceof List) {
                    List tvList = (List) llComments.getTag();
                    for (int i = 0; i < tvList.size(); i++) {
                        if (tvList.get(i) instanceof TextView) {
                            TextView tv = (TextView) tvList.get(i);
                            if (i >= commentList.size() || (!item.isShowMore() && i >= maxContentSize)) {
                                tv.setVisibility(View.GONE);
                            } else {
                                tv.setText(commentList.get(i).getHtmlString());
                                tv.setBackgroundColor(0x00000000);
                                int pad = UIUtils.dip2px(getThis(), 8);
                                tv.setPadding(pad, pad / 2, pad, pad / 2);
                                tv.setTag(commentList.get(i));
                                tv.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    ivMoreComment.setVisibility((!item.isShowMore() && commentList.size() > maxContentSize) ? View.VISIBLE : View.GONE);
                }
            }
        };*/
        rvMain.setAdapter(neighborAdapter);
//        ivPost.setOnClickListener(this);
//        ivBack.setOnClickListener(this);
    }

    private void initDate() {
        try {
            NeighbourListBean bean = JSON.parseObject(User.getNeighbours().toString(), NeighbourListBean.class);
            lastPage = bean.getNeighbourMessages().isLastPage();
            villages = bean.getVillageNames();
            neighborAdapter.setDataList(bean.getNeighbourMessages().getContent());
        } catch (Exception e) {
            e.printStackTrace();
            getDataByRefresh(++pageIndex, 10, getSelectedVillage(selectedVillageIndex), Const.RefreshType.REFRESH);
        }
    }


    /**
     * 获取小区

     * @param page        页码
     * @param villageName 小区id
     * @param refreshType 状态
     */
    private void getDataByRefresh(final int page, final int pageSize, final String villageName, final int refreshType) {
        VolleyStringRequest request = new VolleyStringRequest(Request.Method.POST, Const.serviceMethod.NEIGHBOR_LIST, new VolleyResponseListener() {
            @Override
            public void handleJson(com.alibaba.fastjson.JSONObject json) {
                super.handleJson(json);
                int resultCode = json.getIntValue("resultCode");
                String message = json.getString("message");
                if (resultCode == 1) {
                    NeighbourListBean bean = JSON.parseObject(json.toString(), NeighbourListBean.class);
                    lastPage = bean.getNeighbourMessages().isLastPage();
                    villages = bean.getVillageNames();
                    User.setNeighbours(json.toString());
                    switch (refreshType) {
                        case Const.RefreshType.REFRESH:
//                            refreshView.endRefreshing();
//                            neighborAdapter.clearData();
                            if (bean.getNeighbourMessages().getContent().size() > 0) {
                                neighborAdapter.setDataList(bean.getNeighbourMessages().getContent());
                            }
                            break;
                        case Const.RefreshType.LOAD:
//                            refreshView.endLoadingMore();
                            pageIndex++;
                            if (bean.getNeighbourMessages().getContent().size() > 0) {
//                                neighborAdapter.addAdapterData(bean.getNeighbourMessages().getContent());
                            }
                            break;
                    }
                } else {
                    QBLToast.show(message);
                    switch (refreshType) {
                        case Const.RefreshType.REFRESH:
//                            refreshView.endRefreshing();
                            break;
                        case Const.RefreshType.LOAD:
//                            refreshView.endLoadingMore();
                            break;
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                QBLToast.show(R.string.network_exception);
                switch (refreshType) {
                    case Const.RefreshType.REFRESH:
//                        refreshView.endRefreshing();
                        break;
                    case Const.RefreshType.LOAD:
//                        refreshView.endLoadingMore();
                        break;
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = super.getParams();
                params.put("page", String.valueOf(page));
                if (!villageName.isEmpty()) {
                    params.put("villageName", villageName);
                }
                params.put("pageSize", String.valueOf(pageSize));
                return params;
            }
        };
        getThis().getVolleyRequestQueue().add(request);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
//        getDataByRefresh(++pageIndex, 10, villages.get(selectedVillageIndex), Const.RefreshType.REFRESH);
//        if (neighborAdapter.getItemCount() > 0) {
//            getDataByRefresh(pageIndex, neighborAdapter.get.get(mBuildSelectIndex).getId(), villageName, Const.RefreshType.REFRESH);
//        } else {
//            refreshView.endRefreshing();
//            QBLToast.show(R.string.text_no_data);
//        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (!lastPage) {
            getDataByRefresh(++pageIndex, 10, villages.get(selectedVillageIndex), Const.RefreshType.LOAD);
        }
      /*  if (mVillages.size() > 0) {
            if (!lastPage) {
                getDataByRefresh(pageIndex, neighborAdapter.getData().get(mBuildSelectIndex).getId(), villageName, Const.RefreshType.LOAD);
            }
        } else {
            QBLToast.show(R.string.text_no_data);
        }
    else

    {
        refreshView.endRefreshing();
        QBLToast.show(R.string.text_no_village_tip);
    }*/

        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_NEW_COMMENT:
                if (resultCode == RESULT_OK) {
                    if (data != null && data.getStringExtra("data") != null) {
                        String json = data.getStringExtra("data");
                        NeighbourListBean.NeighbourMessagesBean.ContentBean bean = JSON.parseObject(json, NeighbourListBean.NeighbourMessagesBean.ContentBean.class);
                        neighborAdapter.addItem(0, bean);
                    }
                }
                break;
            default:
                break;
        }
    }

}
