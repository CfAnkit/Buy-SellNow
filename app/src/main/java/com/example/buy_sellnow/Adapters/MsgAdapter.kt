package com.example.buy_sellnow.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.buy_sellnow.Model.Message
import com.example.buy_sellnow.R
import com.google.firebase.auth.FirebaseAuth

class MsgAdapter(private val msgs: ArrayList<Message>, val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val userId = FirebaseAuth.getInstance().currentUser!!.uid

    override fun getItemViewType(position: Int): Int {
        return if(msgs[position].userID == userId) {
            1
        } else {
            2
        }
    }

    override fun getItemCount(): Int {
        return msgs.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = msgs[position].msg
        val time = msgs[position].sendTime
        val img = msgs[position].img
         val viewHolder = holder as Sender
            viewHolder.sendTextView.text = msg
            if(img.isNotEmpty()){
                viewHolder.sendChatImage.visibility = View.VISIBLE
                Glide.with(context).load(img).into(viewHolder.sendChatImage)
            } else {
                viewHolder.sendChatImage.visibility = View.GONE
            }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val  layoutInflater = LayoutInflater.from(parent.context);

            return Sender(
                layoutInflater.inflate(
                    R.layout.send_msg,
                    parent,
                    false
                )
            )
    }

    class Sender(view: View): RecyclerView.ViewHolder(view){
        val sendTextView: TextView = view.findViewById(R.id.sendTextView)
        val sendChatImage: ImageView = view.findViewById(R.id.sendChatImage)
    }

    class Reciver(view: View): RecyclerView.ViewHolder(view){
        val reciveMsgTextView: TextView = view.findViewById(R.id.reciveMsgTextView)
        val reciveChatImage: ImageView = view.findViewById(R.id.reciveChatImage)
    }
}
