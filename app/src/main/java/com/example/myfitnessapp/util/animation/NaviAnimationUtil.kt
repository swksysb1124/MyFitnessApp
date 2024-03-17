package com.example.myfitnessapp.util.animation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

fun AnimatedContentTransitionScope<NavBackStackEntry>.rightSlideOutNaviAnimation() =
    slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(400)
    )

fun AnimatedContentTransitionScope<NavBackStackEntry>.leftSlideOutNaviAnimation() =
    slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(400)
    )

fun AnimatedContentTransitionScope<NavBackStackEntry>.leftSlideInNaviAnimation() =
    slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(250)
    )

fun AnimatedContentTransitionScope<NavBackStackEntry>.rightSlideInNaviAnimation() =
    slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(250)
    )