package com.example.buy_sellnow.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import com.example.buy_sellnow.Adapters.ProductGridViewAdapter
import com.example.buy_sellnow.Connexions.FireBaseConexion
import com.example.buy_sellnow.Model.Product
import com.example.buy_sellnow.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.FirebaseAuth

class Sell : AppCompatActivity() {
    lateinit var sellToolbar: MaterialToolbar

    var productLis: ArrayList<Product> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell)

        sellToolbar = findViewById(R.id.sellToolbar)
        sellToolbar.setTitle("Ventas")
        sellToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        val sell_grid_view_home: GridView = findViewById(R.id.sell_grid_view_home)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        val conexion: FireBaseConexion =  FireBaseConexion()
        conexion.getAllProductsByUserId(userId) { products ->
            productLis.clear()
            productLis.addAll(products)
            val productGridViewAdapter = ProductGridViewAdapter(productLis, this, true)
            productGridViewAdapter.notifyDataSetChanged()
            sell_grid_view_home.adapter = productGridViewAdapter
        }
    }
}