package com.example.myfitnessapp.ui.screen.lession

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfitnessapp.datasource.MocKLessonDataSource
import com.example.myfitnessapp.event.RefreshLessonListEvent
import com.example.myfitnessapp.repository.LessonRepository
import com.example.myfitnessapp.ui.color.backgroundColor
import com.example.myfitnessapp.ui.color.textColor
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
    onAddLesson: () -> Unit = {},
    onModeChange: (LessonScreenMode) -> Unit = {}
) {
    val lessons by viewModel.lessons.observeAsState(emptyList())

    var screenMode by remember { mutableStateOf(LessonScreenMode.Normal) }
    LaunchedEffect(screenMode) { onModeChange(screenMode) }

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
            title = if (screenMode == LessonScreenMode.Normal) "我的訓練" else "選擇訓練",
            icons = {
                TitleIcons(
                    screenMode = screenMode,
                    onAddLesson = onAddLesson,
                    onEditEnter = { screenMode = LessonScreenMode.Edit },
                    onEditExit = { screenMode = LessonScreenMode.Normal }
                )
            }
        )
        LazyColumn(
            Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(lessons) { lesson ->
                val lessonId = lesson.id ?: return@items
                val modifier = when (screenMode) {
                    LessonScreenMode.Normal -> Modifier.clickable(onClick = { onLessonClick(lessonId) })
                    LessonScreenMode.Edit -> Modifier
                }
                LessonRow(
                    screenMode = screenMode,
                    modifier = modifier,
                    name = lesson.name,
                    startTime = lesson.startTime.toString(),
                    duration = lesson.duration.speakableDuration(),
                    daysOfWeek = lesson.daysOfWeek
                )
            }
        }
    }
}

enum class LessonScreenMode {
    Normal,
    Edit
}

@Composable
private fun TitleIcons(
    screenMode: LessonScreenMode,
    onAddLesson: () -> Unit,
    onEditEnter: () -> Unit,
    onEditExit: () -> Unit
) {
    Row {
        when (screenMode) {
            LessonScreenMode.Edit -> {
                IconButton(
                    onClick = onEditExit
                ) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null,
                        tint = textColor
                    )
                }
            }

            else -> {
                AddLessonButton(onAddLesson)
                EditLessonButton(onEditEnter)
            }
        }
    }
}

@Composable
private fun EditLessonButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(25.dp),
            imageVector = Icons.Filled.Edit,
            contentDescription = null,
            tint = textColor
        )
    }
}

@Composable
private fun AddLessonButton(onAddLesson: () -> Unit) {
    IconButton(
        onClick = onAddLesson
    ) {
        Icon(
            modifier = Modifier.size(25.dp),
            imageVector = Icons.Filled.Add,
            contentDescription = null,
            tint = textColor
        )
    }
}

@Preview(apiLevel = 33)
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
