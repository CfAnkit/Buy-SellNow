package com.example.buy_sellnow.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.buy_sellnow.Activity.Buy
import com.example.buy_sellnow.Login
import com.example.buy_sellnow.R
import com.example.buy_sellnow.Activity.EditUserDetail
import com.example.buy_sellnow.Activity.Sell
import com.example.buy_sellnow.Connexions.FireBaseConexion
import com.google.android.gms.auth.api.signin.GoogleSignInClient


class Profile(): Fragment() {
    companion object {
        private lateinit var redrige: Intent
        private lateinit var googleSignInClient : GoogleSignInClient
        /** User Text View Detail variables **/
        private lateinit var profile_user_name: TextView
        private lateinit var profile_user_sell_detail: TextView
        private lateinit var profile_user_geo: TextView
        private lateinit var profile_user_img: ImageView
        private lateinit var profile_sell_btn: Button
        private lateinit var profile_buy_btn: Button
        private lateinit var preferences: SharedPreferences
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val profile_config_btn: Button = view.findViewById(R.id.profile_config_btn)
        profile_sell_btn = view.findViewById(R.id.profile_sell_btn)
        profile_sell_btn.setOnClickListener {
            redrige = Intent(view.context, Sell::class.java)
            startActivity(redrige)
        }

        profile_buy_btn = view.findViewById(R.id.profile_buy_btn)
        profile_buy_btn.setOnClickListener {
            redrige = Intent(view.context, Buy::class.java)
            startActivity(redrige)
        }
        preferences = view.context.getSharedPreferences(getString(R.string.sesionPref), Context.MODE_PRIVATE)
        val userId = preferences.getString("userId", null).toString()

        profile_user_name = view.findViewById(R.id.profile_user_name)
        profile_user_sell_detail = view.findViewById(R.id.profile_user_sell_detail)
        profile_user_geo = view.findViewById(R.id.profile_user_geo)
        profile_user_img = view.findViewById(R.id.profile_user_img)
        val conexion: FireBaseConexion = FireBaseConexion()
        conexion.getUserById(userId){
            if(it != null){
                profile_user_name.setText(it.firstName+" "+it.lastName)
                profile_user_geo.setText(it.direccion)
                Glide.with(view.context).load(it.userImage)
                    .into(profile_user_img)
            }
        }

        profile_config_btn.setOnClickListener {
            redrige = Intent(view.context, EditUserDetail::class.java)
            startActivity(redrige)
        }

        val logOutBtn: ImageButton = view.findViewById(R.id.log_out)
        logOutBtn.setOnClickListener {
            signOut(view)
        }

        //prom_list_view.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        //val adapter : RecycleViewList = RecycleViewList(productLis, context)
        //prom_list_view.adapter = adapter
        return view
    }

    private fun signOut(view: View) {
        googleSignInClient.signOut().addOnCompleteListener {
            startActivity(Intent(view.context, Login()::class.java))
        }
    }

}