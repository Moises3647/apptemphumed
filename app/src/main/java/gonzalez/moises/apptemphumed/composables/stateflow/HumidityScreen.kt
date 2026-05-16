package gonzalez.moises.apptemphumed.composables.stateflow

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─── Colors ──────────────────────────────────────────────────────────────────
private val PrimaryBlue   = Color(0xFF1A6EDB)
private val PageBg        = Color(0xFFF0F5FF)
private val CardBg        = Color(0xFFFFFFFF)
private val TextPrimary   = Color(0xFF0D1B3E)
private val TextSecondary = Color(0xFF8A9BB8)
private val PositiveGreen = Color(0xFF2EC47A)
private val NegativeRed   = Color(0xFFE53935)
private val ChipBlue      = Color(0xFFE8F0FE)

// ─── Data models ─────────────────────────────────────────────────────────────
data class HumidityReading(
    val value: Int,
    val date: String,
    val time: String,
    val delta: String,
    val label: String,
    val isPositive: Boolean,
    val iconType: HumidityIcon
)

enum class HumidityIcon { DROP, WIND, CLOUD, FOG }

// ─── HumidityScreen ──────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HumidityScreen(
    onBack: () -> Unit = {},
    onTemperatureClick: () -> Unit = {},
    onDashboardClick: () -> Unit = {},
) {
    val readings = listOf(
        HumidityReading(64, "Oct 24,", "14:45 PM", "+1.2%", "Stable",      true,  HumidityIcon.DROP),
        HumidityReading(62, "Oct 24,", "13:00 PM", "-0.5%", "Dry Spell",   false, HumidityIcon.WIND),
        HumidityReading(68, "Oct 24,", "11:30 AM", "+4.0%", "High Peak",   true,  HumidityIcon.CLOUD),
        HumidityReading(71, "Oct 24,", "09:00 AM", "+21%",  "Morning Fog", true,  HumidityIcon.FOG),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Humidity",
                        color = PrimaryBlue,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = PrimaryBlue)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PageBg)
            )
        },
        containerColor = PageBg,
        bottomBar = { HumidityBottomBar(onDashboardClick,onTemperatureClick) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(4.dp))

            // ── Header ────────────────────────────────────────────────────────
            Column {
                Text("HISTORICAL DATA", fontSize = 11.sp, color = TextSecondary, letterSpacing = 1.5.sp)
                Text("Humidity Analysis", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            }

            // ── 24h Chart ────────────────────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "24h Relative\nHumidity",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = TextPrimary
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryBlue)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text("Percentage\n(%)", fontSize = 11.sp, color = TextSecondary)
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    HumidityLineChart(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf("00:00", "04:00", "08:00", "12:00", "16:00", "20:00").forEach {
                            Text(it, fontSize = 10.sp, color = TextSecondary)
                        }
                    }
                }
            }

            // ── Recent Readings ───────────────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Recent\nReadings",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = TextPrimary
                        )
                        Text(
                            "⬇ Download CSV",
                            color = PrimaryBlue,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.clickable { }
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                    readings.forEachIndexed { index, reading ->
                        HumidityReadingRow(reading = reading)
                        if (index < readings.lastIndex) {
                            Divider(
                                modifier = Modifier.padding(vertical = 8.dp),
                                color = Color(0xFFF0F4FA)
                            )
                        }
                    }
                }
            }

            // ── Current Reading ───────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(PrimaryBlue)
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        "CURRENT READING",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.7f),
                        letterSpacing = 1.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text("64%", fontSize = 56.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("✔", color = Color.White, fontSize = 16.sp)
                        Spacer(Modifier.width(6.dp))
                        Text(
                            "Optimal Range",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp
                        )
                    }
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "The current humidity levels are within the recommended comfort standards for your active profile.",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                }
            }

            // ── Daily Trend ───────────────────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(ChipBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.TrendingUp, contentDescription = null, tint = PrimaryBlue)
                    }
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text("Daily Trend", fontSize = 13.sp, color = TextSecondary)
                        Text("+4% Increase", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    }
                }
            }

            // ── Quick Analysis ────────────────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Quick Analysis", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
                    Spacer(Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF0D1B3E)),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            drawHumidityWaves()
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Black.copy(alpha = 0.45f))
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Text(
                                "Vapor pressure remains consistent. Moisture levels are stabilizing after the early morning condensation peak.",
                                color = Color.White,
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}

// ─── Reading Row ─────────────────────────────────────────────────────────────
@Composable
private fun HumidityReadingRow(reading: HumidityReading) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(ChipBlue),
            contentAlignment = Alignment.Center
        ) {
            val emoji = when (reading.iconType) {
                HumidityIcon.DROP  -> "💧"
                HumidityIcon.WIND  -> "💨"
                HumidityIcon.CLOUD -> "☁"
                HumidityIcon.FOG   -> "🌫"
            }
            Text(emoji, fontSize = 20.sp)
        }
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text("${reading.value}%", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextPrimary)
            Text("${reading.date} ${reading.time}", fontSize = 12.sp, color = TextSecondary)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                reading.delta,
                color = if (reading.isPositive) PositiveGreen else NegativeRed,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
            )
            Text(reading.label, fontSize = 11.sp, color = TextSecondary)
        }
    }
}

// ─── Line Chart ──────────────────────────────────────────────────────────────
@Composable
private fun HumidityLineChart(modifier: Modifier = Modifier) {
    val dataPoints = listOf(42f, 45f, 55f, 50f, 48f, 70f, 65f, 55f, 52f)
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val minVal = 20f
        val maxVal = 80f
        val range = maxVal - minVal

        fun xFor(i: Int) = i * (w / (dataPoints.size - 1))
        fun yFor(v: Float) = h - ((v - minVal) / range) * (h * 0.85f) - h * 0.05f

        val path = Path().apply {
            dataPoints.forEachIndexed { i, v ->
                if (i == 0) moveTo(xFor(i), yFor(v)) else lineTo(xFor(i), yFor(v))
            }
        }

        val fillPath = Path().apply {
            addPath(path)
            lineTo(xFor(dataPoints.lastIndex), h)
            lineTo(0f, h)
            close()
        }
        drawPath(
            fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF1A6EDB).copy(alpha = 0.20f), Color(0xFF1A6EDB).copy(alpha = 0f))
            )
        )
        drawPath(path, color = Color(0xFF1A6EDB), style = Stroke(width = 3f, cap = StrokeCap.Round, join = StrokeJoin.Round))
    }
}

private fun DrawScope.drawHumidityWaves() {
    for (i in 0..5) {
        val offset = i * 18f
        val path = Path()
        path.moveTo(0f, size.height * 0.55f + offset)
        for (x in 0..size.width.toInt() step 50) {
            path.quadraticBezierTo(
                x + 25f, size.height * 0.45f + offset + (if (x % 100 == 0) -12f else 12f),
                (x + 50).toFloat(), size.height * 0.55f + offset
            )
        }
        drawPath(
            path,
            color = Color(0xFF00D4FF).copy(alpha = 0.12f + i * 0.04f),
            style = Stroke(width = 2.5f)
        )
    }
}

// ─── Bottom Bar ──────────────────────────────────────────────────────────────
@Composable
private fun HumidityBottomBar(
    onDashboardClick: () -> Unit,
    onTemperatureClick: () -> Unit
) {
    NavigationBar(
        containerColor = CardBg,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = false,
            onClick = onDashboardClick,
            icon = { Text("⊞", fontSize = 22.sp) },
            label = {
                Text(
                    "DASHBOARD",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = onTemperatureClick,
            icon = { Text("🌡", fontSize = 22.sp) },
            label = { Text("TEMPERATURE", fontSize = 9.sp, color = TextSecondary) }
        )
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Text("💧", fontSize = 22.sp) },
            label = { Text("HUMIDITY", fontSize = 9.sp, color = TextSecondary) }
        )
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 780)
@Composable
fun hum_prew()
{
    HumidityScreen()
}

