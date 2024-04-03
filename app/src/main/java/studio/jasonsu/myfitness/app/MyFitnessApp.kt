package studio.jasonsu.myfitness.app

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import studio.jasonsu.myfitness.event.RefreshLessonListEvent
import studio.jasonsu.myfitness.navigation.NaviScreen
import studio.jasonsu.myfitness.navigation.WizardNaviPage
import studio.jasonsu.myfitness.repository.LessonAlarmRepository
import studio.jasonsu.myfitness.repository.LessonExerciseRepository
import studio.jasonsu.myfitness.repository.LessonRepository
import studio.jasonsu.myfitness.repository.ProfileRepository
import studio.jasonsu.myfitness.ui.screen.lession.LessonContentScreen
import studio.jasonsu.myfitness.ui.screen.lession.LessonContentViewModel
import studio.jasonsu.myfitness.ui.screen.lession.LessonExercisePage
import studio.jasonsu.myfitness.ui.screen.lession.LessonExerciseViewModel
import studio.jasonsu.myfitness.ui.screen.main.MainScreen
import studio.jasonsu.myfitness.ui.screen.main.MainViewModel
import studio.jasonsu.myfitness.ui.screen.wizard.add.lesson.AddLessonScreen
import studio.jasonsu.myfitness.ui.screen.wizard.add.lesson.AddLessonViewModel
import studio.jasonsu.myfitness.ui.screen.wizard.add.profile.AddProfileScreen
import studio.jasonsu.myfitness.ui.screen.wizard.add.profile.AddProfileViewModel
import studio.jasonsu.myfitness.ui.theme.MyFitnessAppTheme
import studio.jasonsu.myfitness.util.animation.leftSlideInNaviAnimation
import studio.jasonsu.myfitness.util.animation.leftSlideOutNaviAnimation
import studio.jasonsu.myfitness.util.animation.rightSlideInNaviAnimation
import studio.jasonsu.myfitness.util.animation.rightSlideOutNaviAnimation
import studio.jasonsu.myfitness.util.tts.TextToSpeechEngine

@Composable
fun MyFitnessApp(
    profileRepository: ProfileRepository,
    lessonRepository: LessonRepository,
    lessonExerciseRepository: LessonExerciseRepository,
    lessonAlarmRepository: LessonAlarmRepository,
    textToSpeech: TextToSpeechEngine,
    mainViewModel: MainViewModel,
    onFinished: () -> Unit,
) {
    MyFitnessAppTheme {
        val mainNavController = rememberNavController()
        NavHost(
            navController = mainNavController,
            startDestination = NaviScreen.Main.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            composable(NaviScreen.AddProfile.route) {
                AddProfileScreen(
                    viewModel = viewModel {
                        AddProfileViewModel(profileRepository)
                    },
                    onConfirm = mainNavController::popBackStack,
                    onFinished = onFinished
                )
            }
            composable(
                route = NaviScreen.Main.route,
                enterTransition = {
                    when (initialState.destination.route) {
                        NaviScreen.Lesson.route -> rightSlideInNaviAnimation()
                        else -> null
                    }
                },
                exitTransition = {
                    when (targetState.destination.route) {
                        NaviScreen.Lesson.route -> leftSlideOutNaviAnimation()
                        else -> null
                    }
                }
            ) {
                MainScreen(
                    mainViewModel = mainViewModel,
                    lessonRepository = lessonRepository,
                    lessonAlarmRepository = lessonAlarmRepository,
                    profileRepository = profileRepository,
                    onLessonClick = { id ->
                        mainNavController.navigate(
                            NaviScreen.Lesson.createNaviRoute(id)
                        )
                    },
                    onAddLesson = {
                        mainNavController.navigate(WizardNaviPage.AddLessonMain.route)
                    },
                    onAddProfile = {
                        mainNavController.navigate(NaviScreen.AddProfile.route)
                    },
                    onFinished = onFinished
                )
            }
            composable(
                route = NaviScreen.Lesson.route,
                arguments = listOf(
                    navArgument(NaviScreen.LESSON_ID) { type = NavType.StringType }
                ),
                enterTransition = {
                    when (initialState.destination.route) {
                        NaviScreen.Main.route -> leftSlideInNaviAnimation()
                        NaviScreen.LessonExercise.route -> rightSlideInNaviAnimation()
                        else -> null
                    }
                },
                exitTransition = {
                    when (targetState.destination.route) {
                        NaviScreen.Main.route -> rightSlideOutNaviAnimation()
                        NaviScreen.LessonExercise.route -> leftSlideOutNaviAnimation()
                        else -> null
                    }
                }
            ) {
                val lessonId = it.arguments?.getString(NaviScreen.LESSON_ID)
                LessonContentScreen(
                    viewModel = viewModel(
                        key = lessonId
                    ) {
                        LessonContentViewModel(
                            id = lessonId,
                            lessonRepository = lessonRepository,
                            lessonExerciseRepository = lessonExerciseRepository,
                            profileRepository = profileRepository
                        )
                    },
                    onStartButtonClick = {
                        mainNavController.navigate(
                            NaviScreen.LessonExercise.createNaviRoute(
                                lessonId ?: ""
                            )
                        )
                    },
                    onBackPressed = mainNavController::popBackStack
                )
            }
            composable(
                route = NaviScreen.LessonExercise.route,
                arguments = listOf(
                    navArgument(NaviScreen.LESSON_ID) { type = NavType.StringType }
                ),
                enterTransition = {
                    when (initialState.destination.route) {
                        NaviScreen.Lesson.route -> leftSlideInNaviAnimation()
                        else -> null
                    }
                },
                exitTransition = {
                    when (targetState.destination.route) {
                        NaviScreen.Lesson.route -> rightSlideOutNaviAnimation()
                        else -> null
                    }
                }
            ) {
                val lessonId = it.arguments?.getString(NaviScreen.LESSON_ID)
                LessonExercisePage(
                    viewModel = viewModel(
                        key = lessonId
                    ) {
                        LessonExerciseViewModel(
                            lessonId = lessonId,
                            textToSpeech = textToSpeech,
                            lessonExerciseRepository = lessonExerciseRepository
                        )
                    },
                    onBackPressed = mainNavController::popBackStack
                )
            }

            composable(WizardNaviPage.AddLessonMain.route) {
                AddLessonScreen(
                    viewModel = viewModel {
                        AddLessonViewModel(
                            lessonRepository = lessonRepository,
                            lessonExerciseRepository = lessonExerciseRepository,
                            lessonAlarmRepository = lessonAlarmRepository
                        )
                    },
                    onDismiss = mainNavController::popBackStack,
                    onWizardSettingComplete = {
                        mainNavController.popBackStack()
                        mainViewModel.sentEvent(RefreshLessonListEvent)
                    }
                )
            }
        }
    }
}