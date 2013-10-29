package com.codepath.apps.mytwitterapp;

import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public abstract class EndlessScrollListener implements OnScrollListener {
  
  // The minimum amount of items to have below your current scroll position
  // before loading more.
  private int visibleThreshold = 5;
  // The current offset index of data you have loaded
  private int currentPage = 0;
  // The total number of items in the dataset after the last load
  private int previousTotalItemCount = 0;
  // True if we are still waiting for the last set of data to load.
  private boolean loading = true;
  // Sets the starting page index
  private int startingPageIndex = 0;
  // The previous first visible item before scrolling.
  private int previousFirstVisibleItem = 0;

  public EndlessScrollListener() {
  }

  public EndlessScrollListener(int visibleThreshold) {
    this.visibleThreshold = visibleThreshold;
  }

  public EndlessScrollListener(int visibleThreshold, int startPage) {
    this.visibleThreshold = visibleThreshold;
    this.startingPageIndex = startPage;
    this.currentPage = startPage;
  }

  // This happens many times a second during a scroll, so be wary of the code you place here.
  // We are given a few useful parameters to help us work out if we need to load some more data,
  // but first we check if we are waiting for the previous load to finish.
  @Override
  public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    // Determines the direction of scroll.
    boolean isScrollDown = (firstVisibleItem > previousFirstVisibleItem) ? true : false;
    
    // If the total item count is zero and the previous isn't, assume the
    // list is invalidated and should be reset back to initial state
    // If there are no items in the list, assume that initial items are loading
    if (!loading && (totalItemCount < this.previousTotalItemCount)) {
      this.currentPage = this.startingPageIndex;
      this.previousTotalItemCount = totalItemCount;
      if (totalItemCount == 0) {
        this.loading = true;
      } 
    }
    
    int invisibleBottomItemCount = totalItemCount - visibleItemCount - firstVisibleItem;
    if (this.loading) {
      if (totalItemCount > this.previousTotalItemCount) {
        /**
         * When dataset count has increased, we conclude it has finished loading.
         * We then update the total item count and increase the page number.
         */
        this.loading = false;
        this.previousTotalItemCount = totalItemCount;
        this.currentPage++;
      } else if (totalItemCount > 0 && firstVisibleItem == 0){
        /**
         * When dataset is not empty and top item is seen, we assume user wants to reload
         * for the latest tweets.
         */
        this.loading = false;
        this.previousTotalItemCount = totalItemCount;
        this.currentPage = 0;
      } else if (totalItemCount > 0 && totalItemCount == this.previousTotalItemCount  &&
            invisibleBottomItemCount < visibleThreshold) {
        /**
         * When dataset is not empty and remaining items are less than the threshold,
         * then we fetch more data.
         */
        this.loading = false;
        this.previousTotalItemCount = totalItemCount;
        this.currentPage++;
      }
    }
    
    // If it isn’t currently loading, we check to see if we have breached
    // the visibleThreshold and need to reload more data.
    // If we do need to reload some more data, we execute onLoadMore to fetch the data.    
    if (!this.loading && (invisibleBottomItemCount <= visibleThreshold)  && isScrollDown) {
      onLoadTail(this.currentPage + 1, totalItemCount);
      this.loading = true;
    }
   
    /**
     * Scrolling when first item is visible
     */
    if (!this.loading && (firstVisibleItem == 0)  && !isScrollDown) {
      Log.i("Loading...", "PREVIOUS");
      //onLoadPrevious(totalItemCount);
      this.loading = true;
    }
    
    previousFirstVisibleItem = firstVisibleItem;
  }

  // Defines the process for actually loading more data based on page
  public abstract void onLoadTail(int page, int totalItemsCount);
  
  // Defines to load latest data.
  public abstract void onLoadHead(int totalItemsCount);

  @Override
  public void onScrollStateChanged(AbsListView view, int scrollState) {
    // Don't take any action on changed
  }

}
