package vn.tiki.android.androidhometest

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import vn.tiki.android.androidhometest.adapter.DealsAdapter
import vn.tiki.android.androidhometest.data.api.ApiServices
import vn.tiki.android.androidhometest.data.api.response.Deal
import vn.tiki.android.androidhometest.di.initDependencies
import vn.tiki.android.androidhometest.di.inject
import vn.tiki.android.androidhometest.di.releaseDependencies

class MainActivity : AppCompatActivity() {
    val apiServices by inject<ApiServices>()
    lateinit var deals: List<Deal>
    val adapter = DealsAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDependencies(this)

        setContentView(R.layout.activity_main)
        initRecycler()
        object : AsyncTask<Unit, Unit, List<Deal>>() {
            override fun doInBackground(vararg params: Unit?): List<Deal> {
                return apiServices.getDeals()
            }

            override fun onPostExecute(result: List<Deal>?) {
                super.onPostExecute(result)
                result?.let {
                    adapter.updateData(it)
                }
                result.orEmpty()
                    .forEach { deal ->
                        println(deal)
                    }
            }
        }.execute()
    }

    fun initRecycler() {
        val layoutManager = GridLayoutManager(this, 2)
        rvDeals.layoutManager = layoutManager
        rvDeals.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseDependencies()
    }
}
