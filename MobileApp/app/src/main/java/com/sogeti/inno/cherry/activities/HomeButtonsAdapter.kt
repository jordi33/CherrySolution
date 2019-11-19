package com.sogeti.inno.cherry.activities

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.sogeti.inno.cherry.R
import com.sogeti.inno.cherry.activities.ui.calculator.CalculatorActivityMain
import com.sogeti.inno.cherry.activities.ui.choregraphy.ChoregraphyChoiceActivity

import com.sogeti.inno.cherry.activities.ui.joke.JokeListActivityMain
import com.sogeti.inno.cherry.activities.ui.memory.MemoryActivityMain
import com.sogeti.inno.cherry.activities.ui.moves.MovesActivity
import com.sogeti.inno.cherry.activities.ui.tales.TalesChoiceActivity
import com.sogeti.inno.cherry.activities.ui.wordImages.WordImagesActivityMain


class HomeButtonsAdapter(context: Context) : BaseAdapter() {
    val mItems = ArrayList<Item>()
    val mInflater: LayoutInflater
    val context: Context

    init {
        mInflater = LayoutInflater.from(context)
        this.context = context
        mItems.add(Item("Cases mémoires", R.drawable.ic_memory, MemoryActivityMain::class.java))
        mItems.add(Item("4 Images 1 Mot", R.drawable.ic_4images, WordImagesActivityMain::class.java))
        mItems.add(Item("Chorégraphie", R.drawable.ic_choegraphe, ChoregraphyChoiceActivity::class.java))
        mItems.add(Item("Mouvements", R.drawable.ic_movements, MovesActivity::class.java))
        mItems.add(Item("Calculatrice", R.drawable.ic_calculatrice, CalculatorActivityMain::class.java))
        mItems.add(Item("Blagues", R.drawable.ic_blagues, JokeListActivityMain::class.java))
        mItems.add(Item("Histoires", R.drawable.book_tale, TalesChoiceActivity::class.java))
    }

    override fun getCount(): Int {
        return mItems.size
    }

    override fun getItem(i: Int): Item {
        return mItems[i]
    }

    override fun getItemId(i: Int): Long {
        return mItems[i].drawableId.toLong()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var v: View? = view
        val picture: ImageView
        val name: TextView

        if (v == null) {
            v = mInflater.inflate(R.layout.grid_item_fragment, viewGroup, false)
            v!!.setTag(R.id.pictureItem, v.findViewById(R.id.pictureItem))
            v.setTag(R.id.text, v.findViewById(R.id.text))
        }

        picture = v.getTag(R.id.pictureItem) as ImageView
        name = v.findViewById(R.id.textItem)

        val item = getItem(i)

        picture.setImageResource(item.drawableId)
        name.text = item.name


        v.setOnClickListener {
            val intent = Intent(context, item.activity)
            context.startActivity(intent)
        }
        return v
    }

    public class Item internal constructor(
        val name: String,
        val drawableId: Int,
        val activity: Class<*>
    )
}

