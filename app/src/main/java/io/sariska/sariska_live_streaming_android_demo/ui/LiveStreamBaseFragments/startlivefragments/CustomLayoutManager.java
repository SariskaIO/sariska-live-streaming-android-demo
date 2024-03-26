package io.sariska.sariska_live_streaming_android_demo.ui.LiveStreamBaseFragments.startlivefragments;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

public class CustomLayoutManager extends RecyclerView.LayoutManager {

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);

        int itemCount = getItemCount();
        if (itemCount == 0) {
            return;
        }

        int width = getWidth();
        int height = getHeight();

        if (itemCount == 1) {
            layoutSingleView(recycler, 0, width, height);
        } else if (itemCount == 2) {
            layoutTwoViews(recycler, width, height);
        } else if (itemCount == 3) {
            layoutThreeViews(recycler, width, height);
        } else if (itemCount >= 4) {
            layoutFourOrMoreViews(recycler, width, height);
        }
    }

    private void layoutSingleView(RecyclerView.Recycler recycler, int position, int width, int height) {
        View view = recycler.getViewForPosition(position);
        addView(view);
        measureChildWithMargins(view, 0, 0);
        int viewWidth = getDecoratedMeasuredWidth(view);
        int viewHeight = getDecoratedMeasuredHeight(view);
        layoutDecorated(view, 0, 0, viewWidth, viewHeight);
    }

    private void layoutTwoViews(RecyclerView.Recycler recycler, int width, int height) {
        for (int i = 0; i < 2; i++) {
            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int viewWidth = getDecoratedMeasuredWidth(view);
            int viewHeight = getDecoratedMeasuredHeight(view);
            layoutDecorated(view, 0, i * height / 2, viewWidth, (i + 1) * height / 2);
        }
    }

    private void layoutThreeViews(RecyclerView.Recycler recycler, int width, int height) {
        // First participant covers the top half
        layoutSingleView(recycler, 0, width, height / 2);
        // Remaining two participants cover the bottom half
        for (int i = 1; i < 3; i++) {
            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int viewWidth = getDecoratedMeasuredWidth(view);
            int viewHeight = getDecoratedMeasuredHeight(view);
            layoutDecorated(view, (i - 1) * width / 2, height / 2, i * width / 2, height);
        }
    }

    private void layoutFourOrMoreViews(RecyclerView.Recycler recycler, int width, int height) {
        // Divide the screen into top and bottom halves
        int topHalfHeight = height / 2;
        int bottomHalfHeight = height - topHalfHeight;

        // Layout top half with 2 participants
        for (int i = 0; i < 2; i++) {
            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int viewWidth = getDecoratedMeasuredWidth(view);
            int viewHeight = getDecoratedMeasuredHeight(view);
            layoutDecorated(view, i * width / 2, 0, (i + 1) * width / 2, topHalfHeight);
        }

        // Layout bottom half with remaining participants
        for (int i = 2; i < getItemCount(); i++) {
            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int viewWidth = getDecoratedMeasuredWidth(view);
            int viewHeight = getDecoratedMeasuredHeight(view);
            layoutDecorated(view, (i - 2) * width / 2, topHalfHeight, (i - 1) * width / 2, height);
        }
    }
}
