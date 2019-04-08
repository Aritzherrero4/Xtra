package com.github.exact7.xtra.ui.player.clip

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.exact7.xtra.R
import com.github.exact7.xtra.model.kraken.Channel
import com.github.exact7.xtra.model.kraken.clip.Clip
import com.github.exact7.xtra.ui.common.RadioButtonDialogFragment
import com.github.exact7.xtra.ui.download.ClipDownloadDialog
import com.github.exact7.xtra.ui.download.HasDownloadDialog
import com.github.exact7.xtra.ui.player.BasePlayerFragment
import com.github.exact7.xtra.util.C
import com.github.exact7.xtra.util.DownloadUtils
import com.github.exact7.xtra.util.FragmentUtils
import kotlinx.android.synthetic.main.player_video.*

class ClipPlayerFragment : BasePlayerFragment(), RadioButtonDialogFragment.OnSortOptionChanged, HasDownloadDialog {
//    override fun play(obj: Parcelable) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }

    override lateinit var viewModel: ClipPlayerViewModel
    private lateinit var clip: Clip
    override val channel: Channel
        get() = clip.broadcaster

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clip = requireArguments().getParcelable(C.CLIP)!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_player_video, container, false)
    }

    override fun initialize() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ClipPlayerViewModel::class.java)
        super.initialize()
        viewModel.loaded.observe(this, Observer {
            settings.isEnabled = true
            download.isEnabled = true
            settings.setColorFilter(Color.WHITE)
            download.setColorFilter(Color.WHITE)
        })
        viewModel.setClip(clip)
        settings.setOnClickListener { FragmentUtils.showRadioButtonDialogFragment(childFragmentManager, viewModel.qualities.keys, viewModel.selectedQualityIndex) }
        download.setOnClickListener { showDownloadDialog() }
    }

    override fun onChange(index: Int, text: CharSequence, tag: Int?) {
        viewModel.changeQuality(index)
    }

    override fun showDownloadDialog() {
        if (DownloadUtils.hasInternalStoragePermission(requireActivity())) {
            ClipDownloadDialog.newInstance(viewModel.clip.value!!, viewModel.qualities).show(childFragmentManager, null)
        }
    }

    override fun onMovedToForeground() {
        if (this::viewModel.isInitialized) {
            viewModel.onResume()
        }
    }

    override fun onMovedToBackground() {
        if (this::viewModel.isInitialized) {
            viewModel.onPause()
        }
    }

    override fun onNetworkRestored() {
        if (this::viewModel.isInitialized) {
            viewModel.onResume()
        }
    }
}
