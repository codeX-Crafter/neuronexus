package com.project.neuronexus.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.project.neuronexus.ui.components.ActivityChart
import com.project.neuronexus.ui.components.CustomBottomBar
import com.project.neuronexus.ui.components.NeuroTopBar
import androidx.compose.foundation.shape.RoundedCornerShape


@Composable
fun NeuroNexusDashboard() {
    Scaffold(
        topBar = { NeuroTopBar() },
        bottomBar = { CustomBottomBar() }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF3E5F5))
                .verticalScroll(rememberScrollState())
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(Color.White)
                    .padding(20.dp)
            ) {

                Text("Dashboard", style = MaterialTheme.typography.headlineSmall)

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatusCard("Points", "550 coins", Modifier.weight(1f))
                    StatusCard("Condition", "All is well", Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(20.dp))

                ActivityChart()
            }
        }
    }
}

@Composable
fun StatusCard(title: String, value: String, modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(title, color = Color.Gray)
            Text(value, style = MaterialTheme.typography.titleMedium)
        }
    }
}
