package kaitait.com.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

/**
 * Created by kai-tait on 9/08/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<String> values;
    
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView subtitle;
        public View layout;
        
        public ViewHolder(View v) {
            super(v);
            layout = v;
            title = (TextView) v.findViewById(R.id.firstLine);
            subtitle = (TextView) v.findViewById(R.id.secondLine);
        }
    }
    
    
    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }
    

    public MyAdapter(List<String> values) {
        this.values = values;
    }
    
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_layout, parent, false);
        
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String name = values.get(position);
        holder.title.setText(name);
        holder.subtitle.setText("Index : " + position);
        holder.title.setOnClickListener(v -> remove(position));
    }
    
    @Override
    public int getItemCount() {
        return values.size();
    }
}
