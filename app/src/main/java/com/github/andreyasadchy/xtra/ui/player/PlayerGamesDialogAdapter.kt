package com.github.andreyasadchy.xtra.ui.player

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import com.github.andreyasadchy.xtra.R
import com.github.andreyasadchy.xtra.model.ui.Game
import com.github.andreyasadchy.xtra.ui.common.BaseListAdapter
import com.github.andreyasadchy.xtra.util.TwitchApiHelper
import com.github.andreyasadchy.xtra.util.gone
import com.github.andreyasadchy.xtra.util.loadImage
import com.github.andreyasadchy.xtra.util.visible
import kotlinx.android.synthetic.main.fragment_games_list_item.view.*

class PlayerGamesDialogAdapter(
    private val fragment: Fragment) : BaseListAdapter<Game>(
    object : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean =
            oldItem.vodPosition == newItem.vodPosition

        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean = true
    }) {

    override val layoutId: Int = R.layout.fragment_games_list_item

    override fun bind(item: Game, view: View) {
        with(view) {
            setOnClickListener {
                item.vodPosition?.let { position -> (fragment as? PlayerGamesDialog)?.listener?.seek(position.toLong()) }
                (fragment as? PlayerGamesDialog)?.dismiss()
            }
            if (item.boxArt != null)  {
                gameImage.visible()
                gameImage.loadImage(fragment, item.boxArt)
            } else {
                gameImage.gone()
            }
            if (item.gameName != null)  {
                gameName.visible()
                gameName.text = item.gameName
            } else {
                gameName.gone()
            }
            val position = item.vodPosition?.div(1000)?.toString()?.let { TwitchApiHelper.getDurationFromSeconds(context, it, true) }
            if (!position.isNullOrBlank()) {
                viewers.visible()
                viewers.text = context.getString(R.string.position, position)
            } else {
                viewers.gone()
            }
            val duration = item.vodDuration?.div(1000)?.toString()?.let { TwitchApiHelper.getDurationFromSeconds(context, it, true) }
            if (!duration.isNullOrBlank()) {
                broadcastersCount.visible()
                broadcastersCount.text = context.getString(R.string.duration, duration)
            } else {
                broadcastersCount.gone()
            }
        }
    }
}