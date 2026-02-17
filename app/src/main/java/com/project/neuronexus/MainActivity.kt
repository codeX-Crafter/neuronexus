package com.project.neuronexus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.neuronexus.ui.VoiceTaskScreen
import com.project.neuronexus.ui.VoiceTaskViewModel
import com.project.neuronexus.ui.dashboard.NeuroNexusDashboard
import com.project.neuronexus.ui.tasks.TasksScreen
import com.project.neuronexus.ui.tasks.MemoryMatchScreen
import com.project.neuronexus.ui.community.CommunityPage
import com.project.neuronexus.ui.tasks.MemoryMcqScreen
import com.project.neuronexus.ui.tasks.MemoryPreviewScreen
import com.project.neuronexus.ui.tasks.MemoryRecallScreen
import com.project.neuronexus.ui.theme.NeuroNexusTheme
import com.project.neuronexus.ui.tasks.NarrativeRecallScreen
import com.project.neuronexus.ui.tasks.StoryScreen
import com.project.neuronexus.ui.tasks.RecallPhaseScreen
import com.project.neuronexus.ui.tasks.RecallQuestionScreen
import com.project.neuronexus.ui.tasks.RecallResultScreen
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.project.neuronexus.ui.tasks.MemoryScoreScreen
import com.project.neuronexus.ui.tasks.StroopIntroScreen
import com.project.neuronexus.ui.tasks.StroopGameScreen
import com.project.neuronexus.ui.tasks.StroopResultScreen
import com.project.neuronexus.ui.auth.LoginScreen
import com.project.neuronexus.ui.auth.LoadingScreen
import com.project.neuronexus.ui.auth.AvatarSelectionScreen
import com.project.neuronexus.ui.auth.SignupProfileScreen
import com.project.neuronexus.ui.auth.SignupInsuranceScreen
import com.project.neuronexus.ui.auth.SignupHealthScreen
import com.project.neuronexus.ui.auth.SignupContactScreen
import com.project.neuronexus.ui.auth.SignupPersonalScreen
import com.project.neuronexus.ui.auth.RoleSelectionScreen
import com.project.neuronexus.ui.auth.WelcomeScreen
import com.project.neuronexus.ui.profile.ProfileScreen
import androidx.navigation.NavController



class MainActivity : ComponentActivity() {

    private val viewModel: VoiceTaskViewModel by viewModels {
        VoiceTaskViewModel.Factory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            NeuroNexusTheme {

                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "welcome"
                ) {

                    // -------- DASHBOARD --------
                    composable("dashboard") {

                        NeuroNexusDashboard(
                            navController = navController,
                            onHomeClick = { navController.navigate("dashboard") },
                            onTasksClick = { navController.navigate("tasks") },
                            onSettingsClick = { /* TODO: navigate to settings */ },
                            onShareClick = { navController.navigate("community") }
                        )
                    }

                    // -------- TASK LIST --------
                    composable("tasks") {
                        TasksScreen(navController = navController)
                    }

                    // -------- MEMORY MATCH --------
                    composable("memory_match") {
                        MemoryMatchScreen(navController = navController)
                    }

                    // -------- NARRATIVE RECALL --------
                    composable("narrative_recall") {
                        NarrativeRecallScreen(navController)
                    }

                    composable("memory_preview") {
                        MemoryPreviewScreen(navController)
                    }
                    composable("memory_recall") {
                        MemoryRecallScreen(navController)
                    }


                    // -------- VOICE TASK --------
                    composable("voice_task") {
                        VoiceTaskScreen(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                    composable("story") {
                        StoryScreen(navController)
                    }
                    composable("recall_phase") {
                        RecallPhaseScreen(navController)
                    }
                    composable("recall_question") {
                        RecallQuestionScreen(navController)
                    }
                    composable("recall_result/{score}/{time}") { backStackEntry ->

                        val score = backStackEntry.arguments
                            ?.getString("score")
                            ?.toIntOrNull() ?: 0

                        val time = backStackEntry.arguments
                            ?.getString("time")
                            ?.toIntOrNull() ?: 0

                        RecallResultScreen(navController, score, time)
                    }


                    composable("memory_mcq") {
                        MemoryMcqScreen(navController)
                    }

                    composable(
                        route = "memory_score/{score}",
                        arguments = listOf(navArgument("score") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val score = backStackEntry.arguments?.getInt("score") ?: 0
                        MemoryScoreScreen(navController, score)
                    }


                    composable("stroop_intro") {
                        StroopIntroScreen(navController)
                    }

                    composable("stroop_game") {
                        StroopGameScreen(navController)
                    }

                    composable("stroop_result/{score}/{time}") { backStackEntry ->
                        val score = backStackEntry.arguments?.getString("score")?.toInt() ?: 0
                        val time = backStackEntry.arguments?.getString("time")?.toInt() ?: 0
                        StroopResultScreen(navController, score, time)
                    }

                    composable("welcome") { WelcomeScreen(navController) }
                    composable("role_select") { RoleSelectionScreen(navController) }
                    composable("signup_personal") { SignupPersonalScreen(navController) }
                    composable("signup_contact") { SignupContactScreen(navController) }
                    composable("signup_health") { SignupHealthScreen(navController) }
                    composable("signup_insurance") { SignupInsuranceScreen(navController) }
                    composable("signup_profile") { SignupProfileScreen(navController) }
                    composable("avatar_select") { AvatarSelectionScreen(navController) }
                    composable("loading") { LoadingScreen(navController) }
                    composable("login") { LoginScreen(navController) }
                    composable("profile") {
                        ProfileScreen(navController)
                    }



                    // -------- COMMUNITY PAGE --------
                    composable("community") {
                        CommunityPage(navController = navController)
                    }
                }

            }
        }
    }
}
