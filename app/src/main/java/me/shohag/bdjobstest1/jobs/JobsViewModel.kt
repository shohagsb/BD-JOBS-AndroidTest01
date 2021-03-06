package me.shohag.bdjobstest1.jobs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.shohag.bdjobstest1.model.JobResponse
import me.shohag.bdjobstest1.repository.JobsRepository

class JobsViewModel : ViewModel() {
    private val repository = JobsRepository
    private val _jobs = MutableLiveData<JobResponse>()
    val jobs: LiveData<JobResponse>
        get() = _jobs

    private val _progressbarStatus = MutableLiveData<Boolean>()
    val progressbarStatus: LiveData<Boolean>
        get() = _progressbarStatus

    init {
        getJobs()
        _progressbarStatus.value = false
    }

    private fun getJobs() {
        viewModelScope.launch {
            repository.getJobs
                .catch { }
                .collect { jobs ->
                    _jobs.value = jobs
                    _progressbarStatus.value = true
                }
        }
    }

    private val _navigateToDetailFragment = MutableLiveData<JobResponse.Data?>()
    val navigateToDetailFragment: MutableLiveData<JobResponse.Data?>
        get() = _navigateToDetailFragment

    fun onJobClicked(job: JobResponse.Data) {
        _navigateToDetailFragment.value = job
    }

    fun onJobNavigated() {
        _navigateToDetailFragment.value = null
    }
}
