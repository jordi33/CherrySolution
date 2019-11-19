package com.sogeti.inno.cherry.activities.ui.memory

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.sogeti.inno.cherry.R
import com.sogeti.inno.cherry.comm.CommandBuilder
import com.sogeti.inno.cherry.comm.MoveCommand
import com.sogeti.inno.cherry.comm.SpeakCommand
import com.sogeti.inno.cherry.core.ControlServer
import kotlinx.android.synthetic.main.memory_gv_item.view.*
import kotlin.random.Random

class MemoryAdapter(val mContext: Context, var cardItems: ArrayList<CardItem>): BaseAdapter() {
    private var firstSelectedItem: CardItem? = null
    private var isBusy: Boolean = false
    private var boardChanged: Boolean = false

    fun changeGameBoard(newCardItems: ArrayList<CardItem>) {
        cardItems = newCardItems
        boardChanged = true
        this.notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return cardItems.size
    }

    override fun getItem(position: Int): Any {
        return cardItems[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val memoryCard = cardItems[position]
        if ((view == null) || (boardChanged)) {
            val inflator = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflator.inflate(R.layout.memory_gv_item, null)
            view.tag = ""
            view.memory_game_gv_item.setOnClickListener {
                if (! isBusy)
                    onClick(memoryCard, position)
            }
        }
        if (!memoryCard.found) {
            if (memoryCard.selected) {
                view!!.memory_game_gv_item.setImageResource(memoryCard.image)
                view.memory_game_gv_item.tag = memoryCard.image
            }
            else {
                view!!.memory_game_gv_item.setImageResource(R.drawable.memory_back)
            }
        }
        else
            view!!.memory_game_gv_item.setImageResource(memoryCard.image)

        return view
    }

    private fun onClick(selectedCard: CardItem, itemPosition: Int) {
        selectedCard.selected = !selectedCard.selected
        when {
            firstSelectedItem == null -> firstSelectedItem = selectedCard

            firstSelectedItem?.image == selectedCard.image && firstSelectedItem !== selectedCard -> {
                firstSelectedItem?.found = true
                selectedCard.found = true
                firstSelectedItem = null
                if (cardItems.all(CardItem::found)) {
                    val gameContext = mContext as MemoryActivityMain
                    gameContext.showFinishDialog()
                    ControlServer.sendCommand(CommandBuilder.toJson((MoveCommand("twist"))))
                }else {
                    ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand("<emphasis level='reduced'>Bravo!</emphasis>")))
                    //ControlServer.sendCommand(CommandBuilder.toJson((MoveCommand("congratulations_1"))))
                }
            }

            else -> {
                isBusy = true
                val handler = Handler(mContext.mainLooper)
                val gameContext = mContext as MemoryActivityMain
                gameContext.updateTrialsCounter()
                val chance = Random.nextInt(0,4)
                when(chance){
                    0 -> ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand("<emphasis level='reduced'>Essaie encore !</emphasis>")))
                1 -> ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand("<emphasis level='reduced'>Tu vas y arriver !</emphasis>")))
                2 -> ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand("<emphasis level='reduced'>C'était pas loin !</emphasis>")))
                    3 -> ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand("<emphasis level='reduced'>Presque ça !</emphasis>")))
                }
                handler.postDelayed({ // This delay allows to display the image even though it is not the correct one
                    firstSelectedItem?.selected = false
                    firstSelectedItem = null
                    selectedCard.selected = false
                    cardItems[itemPosition] = selectedCard
                    notifyDataSetChanged()
                    isBusy = false
                }, 1750)

            }
        }
        cardItems[itemPosition] = selectedCard
        this.notifyDataSetChanged()
    }
}

data class CardItem(val image: Int) {
    var selected: Boolean = false
    var found: Boolean = false
}