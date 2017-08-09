package kaitait.com.recyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.View;


import static android.support.v7.widget.RecyclerView.*;
import static android.support.v7.widget.helper.ItemTouchHelper.*;

/**
 * Created by kai-tait on 2/08/2017.
 */

/**
 *  Helper class for the RecyclerViews to centralise logic for
 *  Swipe to delete,
 *  colouring the background of deleted items
 *  and adding the delete icon
 */
public class RecyclerViewDeleteUIHelper {
    private RecyclerDeleteStrategy strategy;
    private Context context;
    private static final float ALPHA_FULL = 1.0f;
    
    public RecyclerViewDeleteUIHelper(RecyclerDeleteStrategy strategy, Context context) {
        this.strategy = strategy;
        this.context = context;
    }
    
    public ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new SimpleCallback(0, LEFT) {
        
        @Override
        public boolean onMove(RecyclerView recycler_view, ViewHolder view_holder, ViewHolder target) {
                return false;
            }
            
        @Override
        public void onSwiped(ViewHolder view_holder, int direction) {
                strategy.Delete(view_holder);
            }
            
        @Override
        public void onChildDraw(
                    Canvas canvas,
                    RecyclerView recyclerView,
                    ViewHolder viewHolder,
                    float horizontalDisplacement,
                    float verticalDisplacement,
                    int actionState,
                    boolean isCurrentlyActive) {
                
                if (actionState == ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    
                    Paint paint = new Paint();
                    Bitmap icon;
                    
                    if (horizontalDisplacement < 0) {
                        icon = BitmapFactory.decodeResource(
                                context.getResources(), R.drawable.clear_w);
        
                            /* Set color for left swipe */
                        int color = ContextCompat.getColor(context, R.color.colorAccent);
                        paint.setColor(color);
                        
                        canvas.drawRect(
                                (float) itemView.getRight() + horizontalDisplacement,
                                (float) itemView.getTop(),
                                (float) itemView.getRight(),
                                (float) itemView.getBottom(), paint);
                        
                        canvas.drawBitmap(
                                icon,
                                (float) itemView.getRight() - ConvertDpToPx(16) - icon.getWidth(),
                                (float) itemView.getTop() +
                                ((float) itemView.getBottom()
                                 - (float) itemView.getTop() - icon.getHeight()) / 2, paint);
                    }
                    
                    // Fade out the view as it is swiped out of the parent's bounds
                    final float alpha = ALPHA_FULL -
                                        Math.abs(horizontalDisplacement) / (float) viewHolder.itemView.getWidth();
                    viewHolder.itemView.setAlpha(alpha);
                    viewHolder.itemView.setTranslationX(horizontalDisplacement);
                    
                } else {
                    super.onChildDraw(
                            canvas,
                            recyclerView,
                            viewHolder,
                            horizontalDisplacement,
                            verticalDisplacement,
                            actionState,
                            isCurrentlyActive);
                }
            }
            
            private int ConvertDpToPx(int dp) {
                return Math.round(dp * (context.getResources().getDisplayMetrics().xdpi /
                                        DisplayMetrics.DENSITY_DEFAULT));
            }
            
    });
}
