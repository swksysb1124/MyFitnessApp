package com.example.myfitnessapp.ui.screen.lession

import android.content.res.Configuration
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.datasource.MocKLessonDataSource
import com.example.myfitnessapp.model.Lesson
import com.example.myfitnessapp.repository.LessonRepository
import com.example.myfitnessapp.ui.component.ScreenTitleRow
import com.example.myfitnessapp.ui.icon.AddIconButton
import com.example.myfitnessapp.ui.icon.CloseIconButton
import com.example.myfitnessapp.ui.icon.EditIconButton
import com.example.myfitnessapp.ui.screen.main.MainViewModel
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme
import com.example.myfitnessapp.util.spokenDuration

@Composable
fun MyLessonScreen(
    viewModel: MyLessonViewModel,
    initMode: LessonScreenMode = LessonScreenMode.Normal,
    onLessonClick: (id: String) -> Unit = {},
    onAddLesson: () -> Unit = {},
) {
    val lessons by viewModel.lessons.observeAsState(emptyList())
    val selectedLessons = remember { viewModel.selectedLessons }
    val screenMode by viewModel.screenMode.collectAsState(initMode)
    val hasSelectedLesson by viewModel.hasLessonsSelected.collectAsState(false)
    val isEditMode = (screenMode == LessonScreenMode.Edit)

    Column(
        Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
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
                fontSize = 22.sp
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
    val iconTintColor = MaterialTheme.colorScheme.onPrimaryContainer
    Row {
        when (screenMode) {
            LessonScreenMode.Edit -> {
                CloseIconButton(iconTintColor, onEditExit)
            }

            else -> {
                AddIconButton(iconTintColor, onAddLesson)
                EditIconButton(iconTintColor, onEditEnter)
            }
        }
    }
}

@Preview(
    apiLevel = 33,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    apiLevel = 33,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
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

@Preview(
    apiLevel = 33,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark-Edit"
)
@Preview(
    apiLevel = 33,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight-Edit"
)
@Composable
fun MyPlanScreenEditPreview() {
    val viewModel = MyLessonViewModel(
        mainViewModel = MainViewModel(),
        lessonRepository = LessonRepository(MocKLessonDataSource())
    )
    MyFitnessAppTheme {
        MyLessonScreen(
            viewModel,
            LessonScreenMode.Edit
        )
    }
}
