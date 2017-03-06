package com.alltosun.ceshi.imitationjanbook.Activity.Fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alltosun.ceshi.imitationjanbook.Activity.MainActivity;
import com.alltosun.ceshi.imitationjanbook.Activity.adapter.MyAdapter;
import com.alltosun.ceshi.imitationjanbook.R;
import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.github.library.callback.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

import Myview.ItemRemoveRecyclerView;
import Myview.MyScrollView;

import Myview.OnItemClickListener;

/**
 * 发现页面
 */
public class FindFragment extends Fragment implements MyScrollView.OnScrollListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private EditText search_edit;
    private MyScrollView myScrollView;
    private int searchLayoutTop;
    RelativeLayout search;
    RelativeLayout rlayout;
    ViewTreeObserver observer;
    private int searchwidth;
    private int rlayoutwidth;
    private BaseRecyclerAdapter<String> baseRecyclerAdapter;
    private List<String> list;
    private ItemRemoveRecyclerView recyclerview;
    private OnFragmentInteractionListener mListener;


    public FindFragment() {
    }

    public static FindFragment newInstance(String param1, String param2) {
        FindFragment fragment = new FindFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        intview(view);
        initData();
        return view;
    }

    private void initData() {

    }

    private void intview(View view) {
        search_edit = (EditText) view.findViewById(R.id.search_edit);
        myScrollView = (MyScrollView) view.findViewById(R.id.myScrollView);
        search = (RelativeLayout) view.findViewById(R.id.search);
        rlayout = (RelativeLayout) view.findViewById(R.id.rlayout);
        recyclerview = (ItemRemoveRecyclerView) view.findViewById(R.id.recyclerview);
        myScrollView.setOnScrollListener(this);
        list = new ArrayList<>();
        search.getBackground().setAlpha(0);
        for (int i = 0; i < 100; i++) {
            list.add("第" + i + "条数据");
        }
        observer = rlayout.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                searchLayoutTop = rlayout.getBottom();//获取rlayout的布局的底部相对于整个布局的高度
                searchwidth = search_edit.getMeasuredWidth();
                rlayoutwidth = rlayout.getMeasuredWidth();
                return true;
            }
        });
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
         final MyAdapter adapter = new MyAdapter(getActivity(), (ArrayList<String>) list);
        recyclerview.setAdapter(adapter);
    /*    recyclerview.setAdapter(new BaseRecyclerAdapter<String>
                (getActivity(), list, R.layout.item_listview) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                //helper.setText(R.id.item_data, item.toString());
            }
        });*/
        recyclerview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(MainActivity.this, "** " +i + " **", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(int position) {
                adapter.removeItem(position);

            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void donghua() {

    }

    @Override
    public void onScrollChanged(int x, int y, int oldX, int oldY) {
        //变化率
        float headHeight = rlayout.getMeasuredHeight()
                - search.getMeasuredHeight();
        int alpha = (int) (((float) y / headHeight) * 255);//透明度变化速率
        ViewGroup.LayoutParams lp = search_edit.getLayoutParams();
        if (alpha >= 255){
            alpha = 255;
            for (int i = 0; i < rlayoutwidth; i++) {
                lp.width += i/20;
            }
        }
        if (alpha <= 10){
            alpha = 0;
            for (int i = rlayoutwidth; i > 0; i--) {
                lp.width -= i /20;
            }
        }
        search.getBackground().setAlpha(alpha);
        if (lp.width >= rlayoutwidth)
            lp.width = rlayoutwidth;
        if (lp.width <= 200)
            lp.width = 200;
        search_edit.setLayoutParams(lp);
    }

    @Override
    public void onScroll(int scrollY) {
      /*  float headerBarOffsetY = getResources().getDimension(R.dimen.actionBarSize) - searchLayoutTop;//Toolbar与header高度的差值
        float offset = 1 - Math.max((headerBarOffsetY - scrollY) / headerBarOffsetY, 0f);
        //Toolbar背景色透明度
        search01.setBackgroundColor(Color.argb((int) (offset * 255), 18, 176, 242));
        if (scrollY==searchLayoutTop){
            startPropertyAnim();
        }else if (scrollY==0){
            startPropertyAnims();
        }
        if (scrollY >= searchLayoutTop) {
           // search01.setVisibility(View.VISIBLE);

        } else {

           // search01.setVisibility(View.GONE);
        }*/
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
