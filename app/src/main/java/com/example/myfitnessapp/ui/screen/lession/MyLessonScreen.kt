package com.example.myfitnessapp.ui.screen.lession

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.datasource.MocKLessonDataSource
import com.example.myfitnessapp.model.Lesson
import com.example.myfitnessapp.repository.LessonRepository
import com.example.myfitnessapp.ui.color.backgroundColor
import com.example.myfitnessapp.ui.color.textColor
import com.example.myfitnessapp.ui.component.ScreenTitleRow
import com.example.myfitnessapp.ui.screen.main.MainViewModel
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme
import com.example.myfitnessapp.util.spokenDuration

@Composable
fun MyLessonScreen(
    viewModel: MyLessonViewModel,
    onLessonClick: (id: String) -> Unit = {},
    onAddLesson: () -> Unit = {},
) {
    val lessons by viewModel.lessons.observeAsState(emptyList())
    val selectedLessons = remember { viewModel.selectedLessons }
    val screenMode by viewModel.screenMode.collectAsState(LessonScreenMode.Normal)
    val hasSelectedLesson by viewModel.hasLessonsSelected.collectAsState(false)
    val isEditMode = (screenMode == LessonScreenMode.Edit)

    Column(
        Modifier
            .background(backgroundColor)
            .fillMaxSize()
    ) {
        ScreenTitleRow(
            title = if (isEditMode) "選擇訓練" else "我的訓練",
            icons = {
                TitleIcons(
                    screenMode = screenMode,
                    onAddLesson = onAddLesson,
                    onEditEnter = { viewModel.onScreenModeChange(LessonScreenMode.Edit) },
                    onEditExit = { viewModel.onScreenModeChange(LessonScreenMode.Normal) }
                )
            }
        )
        LessonList(
            modifier = Modifier.weight(1f),
            lessons = lessons,
            screenMode = screenMode,
            onLessonClick = onLessonClick,
            isSelected = selectedLessons::contains,
            onSelectedChange = { selected, lesson ->
                viewModel.onLessonSelectedChange(selectedLessons, selected, lesson)
            }
        )
        AnimatedVisibility(isEditMode) {
            BottomEditButtons(
                enabled = hasSelectedLesson,
                onDelete = viewModel::deleteSelectedLessons
            )
        }
    }
}

@Composable
private fun BottomEditButtons(
    enabled: Boolean = true,
    onDelete: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        TextButton(
            enabled = enabled,
            modifier = Modifier.fillMaxWidth(),
            onClick = onDelete
        ) {
            Text(
                text = "刪除",
                color = if (enabled) textColor else Color.LightGray,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
private fun LessonList(
    lessons: List<Lesson>,
    screenMode: LessonScreenMode,
    onLessonClick: (id: String) -> Unit,
    isSelected: (Lesson) -> Boolean,
    onSelectedChange: (Boolean, Lesson) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(lessons) { lesson ->
            val lessonId = lesson.id ?: return@items
            val rowModifier = when (screenMode) {
                LessonScreenMode.Normal -> Modifier.clickable(onClick = { onLessonClick(lessonId) })
                LessonScreenMode.Edit -> Modifier
            }
            LessonRow(
                screenMode = screenMode,
                modifier = rowModifier,
                name = lesson.name,
                startTime = lesson.startTime.toString(),
                duration = lesson.duration.spokenDuration(),
                daysOfWeek = lesson.daysOfWeek,
                checked = isSelected(lesson),
                onCheckedChange = { selected ->
                    onSelectedChange(selected, lesson)
                }
            )
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
        mainViewModel = MainViewModel(),
        lessonRepository = LessonRepository(MocKLessonDataSource())
    )
    MyFitnessAppTheme {
        MyLessonScreen(viewModel)
    }
}
