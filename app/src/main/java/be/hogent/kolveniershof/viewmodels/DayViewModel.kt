package be.hogent.kolveniershof.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.hogent.kolveniershof.domain.Workday
import be.hogent.kolveniershof.repository.KolvRepository
import be.hogent.kolveniershof.repository.WorkdayRepository
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DayViewModel(val repo: WorkdayRepository) : ViewModel() {

    val workdays = MutableLiveData<List<Workday>>()
    var workday = LiveData<Workday>
    val loadingVisibility = MutableLiveData<Int>()
    val objectVisibility = MutableLiveData<Int>()

    private var disposables = CompositeDisposable()

    init {
        objectVisibility.value = View.VISIBLE
        loadingVisibility.value = View.GONE
    }

    fun getWorkdayById(authToken: String, id: String) {
            workday = repo.getWorkdayById(id)
    }

    fun getWorkdayByDateByUser(authToken: String, date: String, userId: String) {
        disposables.add(
            kolvApi.getWorkdayByDateByUser(authToken, date, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveStart() }
                .doOnTerminate { onRetrieveFinish() }
                .subscribe(
                    { result -> onRetrieveSingleSuccess(result) },
                    { error -> onRetrieveError(error) }
                )
        )
    }

    fun getWorkdayByDateByUserSync(authToken: String, date: String, userId: String): Workday? {
        var workday: Workday? = null
        try {
            onRetrieveStart()
            workday = kolvApi.getWorkdayByDateByUser(authToken, date, userId)
                .doOnError { error -> onRetrieveError(error) }
                .blockingSingle()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            onRetrieveFinish()
        }
        return workday
    }

    private fun onRetrieveSingleSuccess(result: Workday) {
        workday.value = result
        Logger.i(result.toString())
    }

    private fun onRetrieveListSuccess(results: List<Workday>) {
        workdays.value = results
        Logger.i(results.toString())
    }

    private fun onRetrieveError(error: Throwable) {
        Logger.e(error.message!!)
    }

    private fun onRetrieveStart() {
        loadingVisibility.value = View.VISIBLE
        objectVisibility.value = View.GONE
    }

    private fun onRetrieveFinish() {
        loadingVisibility.value = View.GONE
        objectVisibility.value = View.VISIBLE
    }

    /**
     * Disposes the subscription when the [BaseViewModel] is no longer used.
     */
    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}