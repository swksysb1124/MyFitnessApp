package studio.jasonsu.myfitness.ui.screen.lession

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import studio.jasonsu.myfitness.datasource.MocKLessonDataSource
import studio.jasonsu.myfitness.model.Lesson
import studio.jasonsu.myfitness.repository.LessonAlarmRepository
import studio.jasonsu.myfitness.repository.LessonRepository
import studio.jasonsu.myfitness.repository.MockProfileRepository
import studio.jasonsu.myfitness.ui.component.ScreenTitleRow
import studio.jasonsu.myfitness.ui.icon.AddIconButton
import studio.jasonsu.myfitness.ui.icon.CloseIconButton
import studio.jasonsu.myfitness.ui.icon.EditIconButton
import studio.jasonsu.myfitness.ui.screen.main.MainViewModel
import studio.jasonsu.myfitness.ui.theme.MyFitnessAppTheme
import studio.jasonsu.myfitness.util.spokenDuration

@Composable
fun MyLessonScreen(
    viewModel: MyLessonViewModel,
    initMode: LessonScreenMode = LessonScreenMode.Normal,
    onLessonClick: (id: String) -> Unit = {},
    onAddLesson: () -> Unit = {},
    onAddProfile: () -> Unit = {}
) {
    val lessons by viewModel.lessons.observeAsState(emptyList())
    val selectedLessons = remember { viewModel.selectedLessons }
    val screenMode by viewModel.screenMode.collectAsState(initMode)
    val hasSelectedLesson by viewModel.hasLessonsSelected.collectAsState(false)
    val isEditMode = (screenMode == LessonScreenMode.Edit)
    val hasLessons = lessons.isNotEmpty()
    val needAddProfile by viewModel.needAddProfile.collectAsState(false)
    LaunchedEffect(needAddProfile) {
        if (needAddProfile) {
            onAddProfile()
        }
    }

    Column(
        Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
    ) {
        ScreenTitleRow(
            title = if (isEditMode) "選擇運動計畫" else "我的運動計畫",
            icons = {
                TitleIcons(
                    screenMode = screenMode,
                    onAddLesson = onAddLesson,
                    canEditLessons = hasLessons,
                    onEditEnter = { viewModel.onScreenModeChange(LessonScreenMode.Edit) },
                    onEditExit = { viewModel.onScreenModeChange(LessonScreenMode.Normal) }
                )
            }
        )
        if (hasLessons) {
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
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val textColor = MaterialTheme.colorScheme.onPrimaryContainer
                val textFontSize = 22.sp
                Text(text = "建立你的專屬運動計畫吧!", color = textColor, fontSize = textFontSize)
                Text(text = "可以按上方的 + 新增運動計畫", color = textColor, fontSize = textFontSize)
            }
        }

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
            .height(66.dp)
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
    canEditLessons: Boolean,
    onAddLesson: () -> Unit,
    onEditEnter: () -> Unit,
    onEditExit: () -> Unit
) {
    val iconTintColor = MaterialTheme.colorScheme.onSurface
    Row {
        when (screenMode) {
            LessonScreenMode.Edit -> {
                CloseIconButton(iconTintColor, onEditExit)
            }

            else -> {
                AddIconButton(iconTintColor, onAddLesson)
                AnimatedVisibility(canEditLessons) {
                    EditIconButton(iconTintColor, onEditEnter)
                }
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
        profileRepository = MockProfileRepository(),
        lessonRepository = LessonRepository(MocKLessonDataSource()),
        lessonAlarmRepository = LessonAlarmRepository(LocalContext.current)
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
        profileRepository = MockProfileRepository(),
        lessonRepository = LessonRepository(MocKLessonDataSource()),
        lessonAlarmRepository = LessonAlarmRepository(LocalContext.current)
    )
    MyFitnessAppTheme {
        MyLessonScreen(
            viewModel,
            LessonScreenMode.Edit
        )
    }
}
