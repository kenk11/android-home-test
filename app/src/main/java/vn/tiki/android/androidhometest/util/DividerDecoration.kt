package vn.tiki.android.androidhometest.util

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by ken on 7/13/18
 */
class DividerDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect?,
        view: View?,
        parent: RecyclerView?,
        state: RecyclerView.State?
    ) {
        val spacing = 4
        outRect?.top = spacing
        outRect?.left = spacing
        outRect?.bottom = spacing
        outRect?.right = spacing
    }
}