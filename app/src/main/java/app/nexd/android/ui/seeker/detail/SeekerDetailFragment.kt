package app.nexd.android.ui.seeker.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import app.nexd.android.R
import app.nexd.android.api.model.HelpRequestArticle
import app.nexd.android.ui.seeker.overview.HelpRequestArticleBinder
import kotlinx.android.synthetic.main.fragment_seeker_detail.*
import mva2.adapter.ListSection
import mva2.adapter.MultiViewAdapter

class SeekerDetailFragment : Fragment() {

    private val viewModel: SeekerDetailViewModel by viewModels()
    private val args: SeekerDetailFragmentArgs by navArgs()

    private val articlesAdapter = MultiViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_seeker_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView_articles.adapter = articlesAdapter
        recyclerView_articles.layoutManager = LinearLayoutManager(context)

        articlesAdapter.registerItemBinders(
            HelpRequestArticleBinder()
        )

        viewModel.getRequest(args.requestId)
            .observe(viewLifecycleOwner, Observer { request ->
                val articlesList = ListSection<HelpRequestArticle>()
                articlesList.addAll(request.articles!!)
                articlesAdapter.addSection(articlesList)

                button_delete.setOnClickListener {
                    viewModel.cancelRequest(request.id!!)
                }
            })
    }

}