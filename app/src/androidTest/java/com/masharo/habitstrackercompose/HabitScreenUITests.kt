package com.masharo.habitstrackercompose

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.masharo.habitstrackercompose.ui.screen.habit.HabitScreen
import com.masharo.habitstrackercompose.ui.theme.HabitsTrackerComposeTheme
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.compose.koinViewModel

class HabitScreenUITests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun add_new_habit_check_items() {
        composeTestRule.setContent {
            HabitsTrackerComposeTheme {
                HabitScreen(
                    vm = koinViewModel(),
                    navigateBack = {},
                    snackbarHostState = SnackbarHostState()
                )
            }
        }

        composeTestRule.onNodeWithText("Сохранить").assertIsDisplayed()
        composeTestRule.onNodeWithText("Название привычки").assertIsDisplayed()
        composeTestRule.onNodeWithText("Описание привычки").assertIsDisplayed()
        composeTestRule.onNodeWithText("Приоритет: средний").assertIsDisplayed()
        composeTestRule.onNodeWithText("Позитивная").assertIsDisplayed()
        composeTestRule.onNodeWithText("Негативная").assertIsDisplayed()
        composeTestRule.onNodeWithText("Количество выполнений").assertIsDisplayed()
        composeTestRule.onNodeWithText("Период: Неделя").assertIsDisplayed()
        composeTestRule.onNodeWithText("Выполнено").assertIsDisplayed()
        composeTestRule.onNodeWithText("Цвет").assertIsDisplayed()
    }

    @Test
    fun add_new_habit_check_empty_items_click_save() {
        composeTestRule.setContent {
            HabitsTrackerComposeTheme {
                HabitScreen(
                    vm = koinViewModel(),
                    navigateBack = {},
                    snackbarHostState = SnackbarHostState()
                )
            }
        }

        composeTestRule.onNodeWithText("Сохранить").performClick()

        composeTestRule.onNodeWithText("Сохранить").assertIsDisplayed()
        composeTestRule.onNodeWithText("Название привычки").assertTextContains("")
        composeTestRule.onNodeWithText("Описание привычки").assertTextContains("")
        composeTestRule.onNodeWithText("Приоритет: средний").assertIsDisplayed()
        composeTestRule.onNodeWithText("Позитивная").assertIsDisplayed()
        composeTestRule.onNodeWithText("Негативная").assertIsDisplayed()
        composeTestRule.onNodeWithText("Количество выполнений").assertTextContains("")
        composeTestRule.onNodeWithText("Период: Неделя").assertIsDisplayed()
        composeTestRule.onNodeWithText("Выполнено").assertIsDisplayed()
        composeTestRule.onNodeWithText("Цвет").assertIsDisplayed()
    }

    @Test
    fun add_new_habit_check_title_no_empty_click_save() {
        composeTestRule.setContent {
            HabitsTrackerComposeTheme {
                HabitScreen(
                    vm = koinViewModel(),
                    navigateBack = {},
                    snackbarHostState = SnackbarHostState()
                )
            }
        }

        composeTestRule.onNodeWithText("Название привычки").performTextInput("test")
        composeTestRule.onNodeWithText("Сохранить").performClick()

        composeTestRule.onNodeWithText("Сохранить").assertIsDisplayed()
        composeTestRule.onNodeWithText("Название привычки").assertTextContains("test")
        composeTestRule.onNodeWithText("Описание привычки").assertTextContains("")
        composeTestRule.onNodeWithText("Приоритет: средний").assertIsDisplayed()
        composeTestRule.onNodeWithText("Позитивная").assertIsDisplayed()
        composeTestRule.onNodeWithText("Негативная").assertIsDisplayed()
        composeTestRule.onNodeWithText("Количество выполнений").assertTextContains("")
        composeTestRule.onNodeWithText("Период: Неделя").assertIsDisplayed()
        composeTestRule.onNodeWithText("Выполнено").assertIsDisplayed()
        composeTestRule.onNodeWithText("Цвет").assertIsDisplayed()
    }

    @Test
    fun add_new_habit_check_title_and_description_no_empty_click_save() {
        composeTestRule.setContent {
            HabitsTrackerComposeTheme {
                HabitScreen(
                    vm = koinViewModel(),
                    navigateBack = {},
                    snackbarHostState = SnackbarHostState()
                )
            }
        }

        composeTestRule.onNodeWithText("Название привычки").performTextInput("test")
        composeTestRule.onNodeWithText("Описание привычки").performTextInput("test")
        composeTestRule.onNodeWithText("Сохранить").performClick()

        composeTestRule.onNodeWithText("Сохранить").assertIsDisplayed()
        composeTestRule.onNodeWithText("Название привычки").assertTextContains("test")
        composeTestRule.onNodeWithText("Описание привычки").assertTextContains("test")
        composeTestRule.onNodeWithText("Приоритет: средний").assertIsDisplayed()
        composeTestRule.onNodeWithText("Позитивная").assertIsDisplayed()
        composeTestRule.onNodeWithText("Негативная").assertIsDisplayed()
        composeTestRule.onNodeWithText("Количество выполнений").assertTextContains("")
        composeTestRule.onNodeWithText("Период: Неделя").assertIsDisplayed()
        composeTestRule.onNodeWithText("Выполнено").assertIsDisplayed()
        composeTestRule.onNodeWithText("Цвет").assertIsDisplayed()
    }

    @Test
    fun add_new_habit_check_title_and_description_and_count_no_empty_click_save() {
        composeTestRule.setContent {
            HabitsTrackerComposeTheme {
                HabitScreen(
                    vm = koinViewModel(),
                    navigateBack = {},
                    snackbarHostState = SnackbarHostState()
                )
            }
        }

        composeTestRule.onNodeWithText("Название привычки").performTextInput("test")
        composeTestRule.onNodeWithText("Описание привычки").performTextInput("test")
        composeTestRule.onNodeWithText("Количество выполнений").performTextInput("99")
        composeTestRule.onNodeWithText("Сохранить").performClick()
    }

    @Test
    fun add_new_habit_input_negative_count() {
        composeTestRule.setContent {
            HabitsTrackerComposeTheme {
                HabitScreen(
                    vm = koinViewModel(),
                    navigateBack = {},
                    snackbarHostState = SnackbarHostState()
                )
            }
        }

        composeTestRule.onNodeWithText("Количество выполнений").performTextInput("-3")
        composeTestRule.onNodeWithText("Количество выполнений").assertTextContains("")
    }

}