package com.example.myfitnessapp.ui.screen.lession

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfitnessapp.event.RefreshLessonListEvent
import com.example.myfitnessapp.model.Lesson
import com.example.myfitnessapp.repository.LessonRepository
import com.example.myfitnessapp.repository.ProfileRepository
import com.example.myfitnessapp.ui.screen.main.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MyLessonViewModel(
    private val mainViewModel: MainViewModel,
    private val profileRepository: ProfileRepository,
    private val lessonRepository: LessonRepository
) : ViewModel() {
    private val _lessons = MutableLiveData<List<Lesson>>()
    val lessons: LiveData<List<Lesson>> = _lessons

    private val _screenMode = MutableStateFlow(LessonScreenMode.Normal)
    val screenMode: StateFlow<LessonScreenMode> = _screenMode

    val selectedLessons = mutableStateListOf<Lesson>()

    private val _hasLessonsSelected = MutableStateFlow(false)
    val hasLessonsSelected: StateFlow<Boolean> = _hasLessonsSelected

    private val _needAddProfile = MutableSharedFlow<Boolean>()
    val needAddProfile: SharedFlow<Boolean> = _needAddProfile

    init {
        checkIfNeedAddProfile()
        fetchLessonList()
        collectDataChange()
    }

    private fun checkIfNeedAddProfile() {
        viewModelScope.launch {
            val savedProfile = profileRepository.getProfile()
            _needAddProfile.emit(savedProfile == null)
        }
    }

    private fun collectDataChange() {
        viewModelScope.launch {
            collectScreenModeChange()
            collectEventChange()
        }
        collectSelectedLessonsChange()
    }

    private fun CoroutineScope.collectEventChange() {
        launch {
            mainViewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is RefreshLessonListEvent -> refreshLessonList()
                }
            }
        }
    }

    private fun CoroutineScope.collectScreenModeChange() {
        launch {
            screenMode.collectLatest { mode ->
                // clear selected lessons when switching to Normal mode
                if (mode == LessonScreenMode.Normal &&
                    selectedLessons.isNotEmpty()
                ) {
                    selectedLessons.clear()
                }
                mainViewModel.showOrHideBottomNavigationBar(
                    mode == LessonScreenMode.Normal
                )
            }
        }
    }

    private fun collectSelectedLessonsChange() {
        snapshotFlow { selectedLessons.toList() }
            .onEach {
                _hasLessonsSelected.value = selectedLessons.isNotEmpty()
            }
            .launchIn(viewModelScope)
    }

    fun deleteSelectedLessons() {
        val ids = selectedLessons.mapNotNull { it.id }
        deleteLessonsAndRefresh(ids)
        _screenMode.value = LessonScreenMode.Normal
    }

    fun onScreenModeChange(mode: LessonScreenMode) {
        _screenMode.value = mode
    }

    fun onLessonSelectedChange(
        selectedLessons: SnapshotStateList<Lesson>,
        selected: Boolean,
        lesson: Lesson
    ) {
        if (selected) {
            selectedLessons.add(lesson)
        } else {
            selectedLessons.remove(lesson)
        }
    }

    private fun refreshLessonList() = fetchLessonList()

    private fun fetchLessonList() {
        viewModelScope.launch {
            _lessons.value = lessonRepository.getLessons()
        }
    }

    private fun deleteLessonsAndRefresh(lessonIds: List<String>) {
        viewModelScope.launch {
            lessonRepository.deleteLessonsByIds(lessonIds)
            refreshLessonList()
        }
    }
}