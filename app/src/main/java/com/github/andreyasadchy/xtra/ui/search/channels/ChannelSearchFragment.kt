package com.github.andreyasadchy.xtra.ui.search.channels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.github.andreyasadchy.xtra.R
import com.github.andreyasadchy.xtra.model.Account
import com.github.andreyasadchy.xtra.model.ui.User
import com.github.andreyasadchy.xtra.ui.common.BasePagedListAdapter
import com.github.andreyasadchy.xtra.ui.common.PagedListFragment
import com.github.andreyasadchy.xtra.ui.main.MainActivity
import com.github.andreyasadchy.xtra.ui.search.Searchable
import com.github.andreyasadchy.xtra.util.C
import com.github.andreyasadchy.xtra.util.TwitchApiHelper
import com.github.andreyasadchy.xtra.util.gone
import com.github.andreyasadchy.xtra.util.prefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.common_recycler_view_layout.*

@AndroidEntryPoint
class ChannelSearchFragment : PagedListFragment<User, ChannelSearchViewModel, BasePagedListAdapter<User>>(), Searchable {

    override val viewModel: ChannelSearchViewModel by viewModels()
    override val adapter: BasePagedListAdapter<User> by lazy { ChannelSearchAdapter(this, requireActivity() as MainActivity) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.common_recycler_view_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefresh.isEnabled = false
    }

    override fun search(query: String) {
        if (query.isNotEmpty()) { //TODO same query doesn't fire
            viewModel.setQuery(
                query = query,
                helixClientId = requireContext().prefs().getString(C.HELIX_CLIENT_ID, "ilfexgv3nnljz3isbm257gzwrzr7bi"),
                helixToken = Account.get(requireContext()).helixToken,
                gqlClientId = requireContext().prefs().getString(C.GQL_CLIENT_ID, "kimne78kx3ncx6brgo4mv6wki5h1ko"),
                apiPref = TwitchApiHelper.listFromPrefs(requireContext().prefs().getString(C.API_PREF_SEARCH_CHANNEL, ""), TwitchApiHelper.searchChannelsApiDefaults)
            )
        } else {
            adapter.submitList(null)
            nothingHere?.gone()
        }
    }
}