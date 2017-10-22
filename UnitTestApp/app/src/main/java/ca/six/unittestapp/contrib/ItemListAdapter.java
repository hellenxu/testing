package ca.six.unittestapp.contrib;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.six.unittestapp.R;

/**
 * @copyright six.ca
 * Created by Xiaolin on 2017-10-21.
 */

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {
    private List<String> data = new ArrayList<>();
    private LayoutInflater inflater;

    public ItemListAdapter(Context ctx, List<String> data){
        this.data = data;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.rv_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.tvItem.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    final class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvItem = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }
}
