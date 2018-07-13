package vn.tiki.android.androidhometest

import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import vn.tiki.android.androidhometest.adapter.DealsAdapter
import vn.tiki.android.androidhometest.data.api.ApiServices
import vn.tiki.android.androidhometest.data.api.response.Deal
import vn.tiki.android.androidhometest.di.initDependencies
import vn.tiki.android.androidhometest.di.inject
import vn.tiki.android.androidhometest.di.releaseDependencies
import vn.tiki.android.androidhometest.util.DividerDecoration
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity(), DealsAdapter.DealsCallback {
    val apiServices by inject<ApiServices>()
    var deals: List<Deal> = ArrayList()
    val adapter = DealsAdapter(callback = this)
    val handler = Handler()
    val runnable = Runnable {
        updateDeals()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDependencies(this)

        setContentView(R.layout.activity_main)
        initRecycler()

        refreshDeals()
        runTimer()
    }

    fun initRecycler() {
        swipeRefresh.setColorSchemeColors(getColor(R.color.colorPrimary))
        swipeRefresh.setOnRefreshListener {
            refreshDeals()
        }
        val layoutManager = GridLayoutManager(this, 2)
        rvDeals.layoutManager = layoutManager
        rvDeals.adapter = adapter
        rvDeals.addItemDecoration(DividerDecoration())
        rvDeals.itemAnimator = DefaultItemAnimator()
    }

    private fun runTimer() {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(runnable)
            }
        }, 0, 1000L)
    }

    private fun refreshDeals() {
        object : AsyncTask<Unit, Unit, List<Deal>>() {
            override fun onPreExecute() {
                super.onPreExecute()
                swipeRefresh.isRefreshing = true
            }

            override fun doInBackground(vararg params: Unit?): List<Deal> {
                return apiServices.getDeals()
            }

            override fun onPostExecute(result: List<Deal>?) {
                super.onPostExecute(result)
                result?.let {
                    deals = it
                    updateDeals()
                }
                swipeRefresh.isRefreshing = false
//            result.orEmpty()
//                .forEach { deal ->
//                    println(deal)
//                }
            }
        }.execute()
    }

    fun updateDeals() {
        val newDeals = deals.dropWhile { !it.isAvailable() }
        adapter.updateData(newDeals)
        if (newDeals.isEmpty()) {
            rvDeals.visibility = View.GONE
            txtEmpty.visibility = View.VISIBLE
        } else {
            rvDeals.visibility = View.VISIBLE
            txtEmpty.visibility = View.GONE
        }
    }

    override fun onBuyNow(deal: Deal) {
        val message = getString(R.string.message_buy, deal.productName)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseDependencies()
    }
}
