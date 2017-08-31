package top.omooo.admin.btscoure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Omooo on 2017/8/29.
 */

public class MyAdapter extends BaseAdapter {
    private List<ItemBean> mItemBeen;
    private LayoutInflater mLayoutInflater;

    public MyAdapter(Context context, List<ItemBean> itemBeen) {
        mItemBeen = itemBeen;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mItemBeen.size();
    }

    @Override
    public Object getItem(int i) {
        return mItemBeen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = mLayoutInflater.inflate(R.layout.itembean, null);
            viewHolder.text_Title = view.findViewById(R.id.item_Title);
            viewHolder.text_Date = view.findViewById(R.id.item_Date);
            viewHolder.text_Size = view.findViewById(R.id.item_Size);
            viewHolder.text_Color = view.findViewById(R.id.item_Color);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        ItemBean bean = mItemBeen.get(i);

        viewHolder.text_Title.setText(bean.item_Title);
        viewHolder.text_Date.setText(bean.item_Date);
        viewHolder.text_Size.setText(bean.item_Size);
        viewHolder.text_Color.setBackgroundColor(bean.item_Color);
        return view;
    }
    class ViewHolder{
        public TextView text_Title;
        public TextView text_Date;
        public TextView text_Size;
        public TextView text_Color;
    }
}

