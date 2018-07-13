package vn.tiki.android.androidhometest.adapter

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

/**
 * Created by ken on 7/13/18
 */
class DealsAdapter(var deals: List<Deal> = ArrayList()) :
    RecyclerView.Adapter<DealsAdapter.DealViewHolder>() {
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
        holder.txtPrice.text = deal.productPrice.toString()
        holder.txtCountDown.text = deal.remainingTime()
        ImageLoader.loadImage(holder.itemView.imgThumbnail, deal.productThumbnail)
    }

    class DealViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgThumbnail: ImageView = view.findViewById(R.id.imgThumbnail)
        val btnBuyNow: Button = view.findViewById(R.id.btnBuyNow)
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtPrice: TextView = view.findViewById(R.id.txtPrice)
        val txtCountDown: TextView = view.findViewById(R.id.txtCountDown)
    }
}