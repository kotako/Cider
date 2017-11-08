package info.kotlin.kotako.cider.viewmodel

import android.util.Log
import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.contract.DMFragmentContract
import info.kotlin.kotako.cider.contract.DMViewModelContract
import info.kotlin.kotako.cider.model.RestAPIClient
import info.kotlin.kotako.cider.model.StreamApiClient
import info.kotlin.kotako.cider.model.entity.DirectMessage
import info.kotlin.kotako.cider.rx.DefaultObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DMFragmentViewModel(private val dmView: DMFragmentContract) : DMViewModelContract {

    private var disposable = CompositeDisposable()
    private var maxId = 0L

    override fun start() {
        if (dmView.isEmpty()) loadDirectMessages()
        startStream()
    }

    override fun stop() {
        maxId = 0L
        disposable.dispose()
    }

    override fun loadDirectMessages() {
        if (disposable.isDisposed) disposable = CompositeDisposable()
        dmView.showProgressBar()
        disposable.add(RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .DirectMessagesObservable()
                .directMessages(null, null, 50, null, null)
                .map { maxId = it.last().id; it }
                .map { dmList ->
                    val resultMap = mutableMapOf<Long, ArrayList<DirectMessage>>()
                    dmList.forEach { dm ->
                        resultMap[dm.sender.id]?.apply { add(dm); return@forEach }
                        resultMap.put(dm.sender.id, arrayListOf(dm))
                    }
                    resultMap
                }
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(DefaultObserver<Map<Long, ArrayList<DirectMessage>>>(
                        next = { dmView.addDMCollection(it); dmView.hideProgressBar() },
                        error = { Log.d("dev", it.localizedMessage); dmView.hideProgressBar() }
                )))
    }

    override fun startStream() {
        if (disposable.isDisposed) disposable = CompositeDisposable()
        disposable.add(
                StreamApiClient.statusObservable
                        .filter({ it is twitter4j.DirectMessage })
                        .map { it as twitter4j.DirectMessage }
                        .map { DirectMessage(it) }
                        .subscribeWith(DefaultObserver<DirectMessage>(
                                next = { dmView.addDm(it) },
                                error = { Log.d("dev_from_dm_stream", it.localizedMessage) }
                        ))
        )
    }

    //  TODO: どう使うか
    override fun loadMore() {
        if (disposable.isDisposed) disposable = CompositeDisposable()
        dmView.showProgressBar()
        disposable.add(RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .DirectMessagesObservable()
                .directMessages(null, maxId, 50, null, null)
                .map { dmList ->
                    val resultMap = mutableMapOf<Long, ArrayList<DirectMessage>>()
                    dmList.forEach { dm ->
                        resultMap[dm.sender.id]?.apply { add(dm); return@forEach }
                        resultMap.put(dm.sender.id, arrayListOf(dm))
                    }
                    resultMap
                }
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(DefaultObserver<Map<Long, ArrayList<DirectMessage>>>(
                        next = { dmView.addDMCollection(it); dmView.hideProgressBar() },
                        error = { Log.d("dev", it.localizedMessage); dmView.hideProgressBar() }
                )))
    }

    override fun refresh() {
        dmView.clearDMList()
        maxId = 0L
        loadDirectMessages()
    }
}