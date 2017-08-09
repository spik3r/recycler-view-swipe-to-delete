package kaitait.com.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private final List<String> input = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        
        for (int i = 0; i < 10; i++) {
            input.add("Original position: " + i);
        }
        adapter = new MyAdapter(input);
        recyclerView.setAdapter(adapter);
        AddDeleteListener();
    }
    
    private void AddDeleteListener() {
        RecyclerDeleteStrategy strategy = viewHolder -> {
            int position = viewHolder.getAdapterPosition();
            Log.i("swipe", "deleted item: " + position);
            Toast.makeText(getApplicationContext(),
                           "Item deleted: " + position,
                           Toast.LENGTH_SHORT).show();
            
            RecyclerViewUndoHelper snack_bar_helper =
                    new RecyclerViewUndoHelper(adapter, findViewById(R.id.coordinator_layout));
            
            snack_bar_helper.RemovePositionFrom(input, position);
        };
        
        RecyclerViewDeleteUIHelper helper =
                new RecyclerViewDeleteUIHelper(strategy, getApplicationContext());
        helper.swipeToDismissTouchHelper.attachToRecyclerView(recyclerView);
    }
}
