package kaitait.com.recyclerview;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import java.util.List;

public class RecyclerViewUndoHelper {
    private final RecyclerView.Adapter recycler_adapter;
    private final View layout;
    
    public RecyclerViewUndoHelper(
            RecyclerView.Adapter recycler_adapter,
            View layout) {
        this.recycler_adapter = recycler_adapter;
        this.layout = layout;
    }
    
    public void RemovePositionFrom(
            List<String> list,
            int position) {
        DeleteBackgroundUITimeout(300);
        String item = GetItemRemovedFromRecyclerView(list, position);
        ShowUndoSnackBar(list, position, item);
    }
    
    private String GetItemRemovedFromRecyclerView(
            List<String> list,
            int position) {
        String item = list.get(position);
        list.remove(position);
        this.recycler_adapter.notifyItemRemoved(position);
        this.recycler_adapter.notifyItemRangeChanged(position, list.size());
        return item;
    }
    
    private void DeleteBackgroundUITimeout(int timeout_ms) {
        try {
            Thread.sleep(timeout_ms);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private void ShowUndoSnackBar(
            List<String> list,
            int position,
            String item) {
        Snackbar snackbar = Snackbar
                .make(layout, "Item removed", Snackbar.LENGTH_LONG)
                .setAction("Undo", view -> ReAddItemToRecyclerView(list, position, item));
        snackbar.show();
    }
    
    private void ReAddItemToRecyclerView(
            List<String> list,
            int position,
            String item) {
        Log.i("ReAddItemToRecyclerView", "re-add: ");
        //Todo also re-add if server response with not deleted
        list.add(position, item);
        recycler_adapter.notifyItemInserted(position);
        recycler_adapter.notifyItemRangeChanged(position, list.size());
    }
}
