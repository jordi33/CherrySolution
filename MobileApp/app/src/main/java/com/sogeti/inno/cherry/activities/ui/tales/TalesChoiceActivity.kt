package com.sogeti.inno.cherry.activities.ui.tales

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sogeti.inno.cherry.R
import com.sogeti.inno.cherry.activities.models.Choregraphy
import com.sogeti.inno.cherry.activities.models.Tale
import com.sogeti.inno.cherry.activities.ui.choregraphy.ChoregraphyChoiceAdapter
import com.sogeti.inno.cherry.activities.ui.choregraphy.ChoregraphyMainActivity
import com.sogeti.inno.cherry.activities.ui.choregraphy.ClickListener
import com.sogeti.inno.cherry.comm.CommandBuilder
import com.sogeti.inno.cherry.comm.SpeakCommand
import com.sogeti.inno.cherry.core.ControlServer
import com.sogeti.inno.cherry.utils.IdleHandler
import com.sogeti.inno.cherry.utils.TalesHolder
import com.sogeti.inno.cherry.utils.ToolbarHandler
import kotlinx.android.synthetic.main.activity_choregraphy_choice.*
import kotlinx.android.synthetic.main.activity_tales_choice.*

class TalesChoiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tales_choice)
        ToolbarHandler.initToolbar(this)

        val tales = TalesHolder.instance.talesList
        val adapter = TalesChoiceAdapter(this, tales)
        tales_choice_recv.adapter = adapter
        adapter.setOnItemClickListener(object: ClickListener {
            override fun onClick(pos: Int, adapterView: View) {
                redirectToMainActivity(pos)
            }
        })
        tales_choice_recv.layoutManager = LinearLayoutManager(this)
        if (tales.size == 0) {
            val taleChoiceTv = findViewById<TextView>(R.id.tale_choice_tv)
            taleChoiceTv.text = resources.getString(R.string.tale_choice_text_no_items)
        }

        ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand("Ici, tu peux choisir le conte que tu souhaites lire. Une fois choisi, il te suffira d'appuyer sur le bouton pour que je te lise l'histoire")))
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        IdleHandler.resetCount()
    }

    private fun redirectToMainActivity(position: Int) {
        val intent = Intent(this@TalesChoiceActivity, TalesActivity::class.java)
        intent.putExtra("tales_index", position)
        startActivity(intent)
    }
}

class TalesChoiceAdapter(var context: Context, var tales: ArrayList<Tale>): RecyclerView.Adapter<TalesChoiceAdapter.TalesChoiceViewHolder>() {

    lateinit var mClickListener: ClickListener

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    override fun getItemCount(): Int {
        return tales.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalesChoiceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TalesChoiceViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: TalesChoiceViewHolder, position: Int) {
        val tale = tales[position]
        holder.bind(tale)
    }

    inner class TalesChoiceViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.choregraphy_choice_item, parent, false)), View.OnClickListener {
        private var taleName: TextView? = null


        init {
            val view = itemView.findViewById<TextView>(R.id.choregraphy_choice_name)
            view.setOnClickListener(this)
            taleName = view
        }

        fun bind(tale: Tale) {
            taleName?.text = tale.Name
        }

        override fun onClick(v: View) {
            mClickListener.onClick(adapterPosition, v)
        }
    }
}
