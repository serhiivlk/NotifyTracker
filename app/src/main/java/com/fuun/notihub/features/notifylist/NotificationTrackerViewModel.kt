package com.fuun.notihub.features.notifylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fuun.notihub.data.preferences.PreferencesStore
import com.fuun.notihub.domain.entities.Notify
import com.fuun.notihub.domain.repository.NotifyRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import java.time.LocalDateTime
import java.time.temporal.TemporalAmount

@FlowPreview
@ExperimentalCoroutinesApi
class NotificationTrackerViewModel constructor(
    private val notificationRepository: NotifyRepository,
    private val preferencesStore: PreferencesStore
) : ViewModel() {

    private val mutableState =
        MutableStateFlow(UiState(isTrackerEnable = preferencesStore.notificationTrackerEnable))
    val state: Flow<UiState>
        get() = mutableState

    private val filterState = MutableStateFlow(PeriodFilter.ALL)

    init {
        viewModelScope.launch {
            notificationRepository.observeNotification()
                .flatMapLatest { list ->
                    filterState.map {
                        list.applyFilter(it) to it
                    }
                }
                .collect { (list, filter) ->
                    updateState {
                        copy(
                            notifications = list,
                            isEmpty = list.isEmpty(),
                            currentFilter = filter
                        )
                    }
                }
        }
    }

    fun setFilter(filter: PeriodFilter) {
        filterState.value = filter
    }

    fun trackerStateToggle() {
        preferencesStore.run {
            notificationTrackerEnable = !notificationTrackerEnable
            updateState { copy(isTrackerEnable = notificationTrackerEnable) }
        }
    }

    private fun updateState(block: UiState.() -> UiState) {
        mutableState.value = block(mutableState.value)
    }

    enum class PeriodFilter {
        ALL,
        PER_HOUR,
        PER_DAY,
        PER_MONTH
    }

    data class UiState(
        val notifications: List<Notify> = emptyList(),
        val isEmpty: Boolean = true,
        val currentFilter: PeriodFilter = PeriodFilter.ALL,
        val isTrackerEnable: Boolean = false
    )

    private fun List<Notify>.applyFilter(filter: PeriodFilter): List<Notify> {
        val now = LocalDateTime.now()
        val startPeriod = when (filter) {
            PeriodFilter.ALL -> return this
            PeriodFilter.PER_HOUR -> now.minusHours(1)
            PeriodFilter.PER_DAY -> now.minusDays(1)
            PeriodFilter.PER_MONTH -> now.minusMonths(1)
        }
        return filter { it.date.isAfter(startPeriod) }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val viewModel: NotificationTrackerViewModel) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return viewModel as T
        }
    }
}
