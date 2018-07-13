package vn.tiki.android.androidhometest.adapter

import android.os.Handler
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.item_deal.view.*
import vn.tiki.android.androidhometest.R
import vn.tiki.android.androidhometest.data.api.response.Deal
import vn.tiki.android.androidhometest.util.ImageLoader
import java.util.Timer
import java.util.TimerTask

/**
 * Created by ken on 7/13/18
 */
class DealsAdapter(var deals: List<Deal> = ArrayList()) :
    RecyclerView.Adapter<DealsAdapter.DealViewHolder>() {
    val handler = Handler()
    val runnable = Runnable {
        notifyDataSetChanged()
        updateDeals()
    }

    init {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(runnable)
            }
        }, 0, 1000L)
    }

    fun updateData(deals: List<Deal>) {
        this.deals = deals
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_deal, parent, false)
        return DealViewHolder(view)
    }

    override fun getItemCount(): Int = deals.size
    override fun onBindViewHolder(holder: DealViewHolder, position: Int) {
        val deal = deals[position]
        holder.txtName.text = deal.productName
        holder.txtPrice.text = deal.priceCurrency()
        holder.txtCountDown.text = deal.remainingTime()
        ImageLoader.loadImage(holder.itemView.imgThumbnail, deal.productThumbnail)
    }

    private fun updateDeals() {
        val newDeals = deals.dropWhile { !it.isAvailable() }
        val diffCallback = DealDiffCallback(deals, newDeals)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        deals = newDeals
        diffResult.dispatchUpdatesTo(this)
    }

    class DealViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgThumbnail: ImageView = view.findViewById(R.id.imgThumbnail)
        val btnBuyNow: Button = view.findViewById(R.id.btnBuyNow)
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtPrice: TextView = view.findViewById(R.id.txtPrice)
        val txtCountDown: TextView = view.findViewById(R.id.txtCountDown)
    }

    class DealDiffCallback(val old: List<Deal>, val new: List<Deal>) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return old[oldItemPosition].productName == new[newItemPosition].productName
        }

        override fun getOldListSize(): Int = old.size
        override fun getNewListSize(): Int = new.size
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = old[oldItemPosition]
            val newItem = new[newItemPosition]
            return oldItem == newItem
        }
    }
}