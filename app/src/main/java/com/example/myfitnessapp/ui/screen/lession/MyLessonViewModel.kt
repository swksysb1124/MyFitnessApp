package com.example.myfitnessapp.ui.screen.lession

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfitnessapp.model.Lesson
import com.example.myfitnessapp.repository.LessonRepository
import kotlinx.coroutines.launch

class MyLessonViewModel(
    private val lessonRepository: LessonRepository
): ViewModel() {
    private val _lessons = MutableLiveData<List<Lesson>>()
    val lessons: LiveData<List<Lesson>> = _lessons

    init {
        fetchLessonList()
    }

    fun refreshLessonList() = fetchLessonList()

    fun deleteLessonAndRefresh(lessonId: String) {
        viewModelScope.launch {
            lessonRepository.deleteLessonById(lessonId)
            refreshLessonList()
        }
    }

    private fun fetchLessonList() {
        viewModelScope.launch {
            _lessons.value = lessonRepository.getLessons()
        }
    }
}