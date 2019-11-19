package com.sogeti.inno.cherry.activities.ui.choregraphy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sogeti.inno.cherry.R
import com.sogeti.inno.cherry.activities.models.Choregraphy
import com.sogeti.inno.cherry.core.ControlServer
import com.sogeti.inno.cherry.utils.ChoregraphiesHolder
import com.sogeti.inno.cherry.utils.IdleHandler
import com.sogeti.inno.cherry.utils.ToolbarHandler
import kotlinx.android.synthetic.main.activity_choregraphy_choice.*

class ChoregraphyChoiceActivity : AppCompatActivity() {

    var adapter: ChoregraphyChoiceAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choregraphy_choice)
        ToolbarHandler.initToolbar(this)

        val newChore = Choregraphy("", ArrayList(), "")
        newChore.isSaved = false
        ChoregraphiesHolder.instance.choregraphiesList.add(newChore)
        redirectToMainActivity(ChoregraphiesHolder.instance.choregraphiesList.indexOf(newChore))

        val choregraphies = ChoregraphiesHolder.instance.choregraphiesList
        val adapter = ChoregraphyChoiceAdapter(this, choregraphies)
        choregraphy_choice_recv.adapter = adapter
        adapter.setOnItemClickListener(object: ClickListener {
            override fun onClick(pos: Int, adapterView: View) {
                redirectToMainActivity(pos)
            }
        })
        this.adapter = adapter
        choregraphy_choice_recv.layoutManager = LinearLayoutManager(this)

        val addBtn = findViewById<Button>(R.id.add_choregraphy_btn)
        addBtn.setOnClickListener{
            val newChore = Choregraphy("", ArrayList(), "")
            newChore.isSaved = false
            ChoregraphiesHolder.instance.choregraphiesList.add(newChore)
            redirectToMainActivity(ChoregraphiesHolder.instance.choregraphiesList.indexOf(newChore))
        }
    }

    private fun redirectToMainActivity(position: Int) {
        val intent = Intent(this@ChoregraphyChoiceActivity, ChoregraphyMainActivity::class.java)
        intent.putExtra("choregraphy_index", position)
        startActivity(intent)
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        IdleHandler.resetCount()
    }
}

class ChoregraphyChoiceAdapter(var context: Context, var choregraphies: ArrayList<Choregraphy>): RecyclerView.Adapter<ChoregraphyChoiceAdapter.ChoregraphyChoiceViewHolder>() {

    lateinit var mClickListener: ClickListener

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    override fun getItemCount(): Int {
        return choregraphies.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoregraphyChoiceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ChoregraphyChoiceViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ChoregraphyChoiceViewHolder, position: Int) {
        val choregraphy = choregraphies[position]
        holder.bind(choregraphy)
    }

    inner class ChoregraphyChoiceViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.choregraphy_choice_item, parent, false)), View.OnClickListener {
        private var choregraphyName: TextView? = null


        init {
            val view = itemView.findViewById<TextView>(R.id.choregraphy_choice_name)
            view.setOnClickListener(this)
            choregraphyName = view
        }

        fun bind(choregraphy: Choregraphy) {
            choregraphyName?.text = choregraphy.name
        }

        override fun onClick(v: View) {
            mClickListener.onClick(adapterPosition, v)
        }
    }
}

interface ClickListener {
    fun onClick(pos: Int, adapterView: View)
}
