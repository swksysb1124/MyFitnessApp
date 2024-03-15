package com.example.myfitnessapp.ui.screen.lession

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfitnessapp.datasource.MocKLessonDataSource
import com.example.myfitnessapp.event.RefreshLessonListEvent
import com.example.myfitnessapp.repository.LessonRepository
import com.example.myfitnessapp.ui.color.backgroundColor
import com.example.myfitnessapp.ui.component.ScreenTitleRow
import com.example.myfitnessapp.ui.screen.main.MainViewModel
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme
import com.example.myfitnessapp.util.speakableDuration
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MyLessonScreen(
    mainViewModel: MainViewModel,
    viewModel: MyLessonViewModel,
    onLessonClick: (id: String) -> Unit = {},
    onAddLesson: () -> Unit = {}
) {
    val lessons by viewModel.lessons.observeAsState(emptyList())
    LaunchedEffect(Unit) {
        mainViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is RefreshLessonListEvent -> viewModel.refreshLessonList()
            }
        }
    }

    Column(
        Modifier
            .background(backgroundColor)
            .fillMaxSize()
    ) {
        ScreenTitleRow(
            title = "我的訓練",
            icon = {
                IconButton(
                    onClick = onAddLesson
                ) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        )
        LazyColumn(
            Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(lessons) { lesson ->
                val lessonId = lesson.id ?: return@items
                LessonRow(
                    modifier = Modifier.clickable(
                        onClick = {
                            onLessonClick(lessonId)
                        }
                    ),
                    name = lesson.name,
                    startTime = lesson.startTime.toString(),
                    duration = lesson.duration.speakableDuration(),
                    daysOfWeek = lesson.daysOfWeek,
                    onDelete = {
                        viewModel.deleteLessonAndRefresh(lessonId)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun MyPlanScreenPreview() {
    val viewModel = MyLessonViewModel(
        lessonRepository = LessonRepository(MocKLessonDataSource())
    )
    MyFitnessAppTheme {
        MyLessonScreen(
            MainViewModel(),
            viewModel
        )
    }
}
