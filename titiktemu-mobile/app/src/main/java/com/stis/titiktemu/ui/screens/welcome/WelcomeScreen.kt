package com.stis.titiktemu.ui.screens.welcome

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Canvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stis.titiktemu.R
import com.stis.titiktemu.ui.theme.Primary
import com.stis.titiktemu.ui.theme.TitikTemuTheme

@Composable
fun WelcomeScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    val iconScale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "iconScale"
    )

    val contentAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 800, delayMillis = 200),
        label = "contentAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Decorative Background Elements
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            
            // Top wave pattern
            val topPath = Path().apply {
                moveTo(0f, height * 0.15f)
                cubicTo(
                    width * 0.3f, height * 0.10f,
                    width * 0.7f, height * 0.20f,
                    width, height * 0.15f
                )
                lineTo(width, 0f)
                lineTo(0f, 0f)
                close()
            }
            
            drawPath(
                path = topPath,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Primary.copy(alpha = 0.15f),
                        Primary.copy(alpha = 0.05f)
                    )
                )
            )
            
            // Floating circles - top right
            drawCircle(
                color = Primary.copy(alpha = 0.08f),
                radius = 100f,
                center = Offset(width * 0.85f, height * 0.12f)
            )
            
            drawCircle(
                color = Primary.copy(alpha = 0.12f),
                radius = 60f,
                center = Offset(width * 0.92f, height * 0.08f)
            )
            
            // Floating circles - middle left
            drawCircle(
                color = Primary.copy(alpha = 0.06f),
                radius = 80f,
                center = Offset(width * 0.10f, height * 0.35f)
            )
            
            drawCircle(
                color = Primary.copy(alpha = 0.10f),
                radius = 40f,
                center = Offset(width * 0.15f, height * 0.42f)
            )
            
            // Bottom decorative circles
            drawCircle(
                color = Primary.copy(alpha = 0.08f),
                radius = 120f,
                center = Offset(width * 0.20f, height * 0.92f)
            )
            
            drawCircle(
                color = Primary.copy(alpha = 0.10f),
                radius = 70f,
                center = Offset(width * 0.88f, height * 0.88f)
            )
            
            // Small accent circles
            drawCircle(
                color = Primary.copy(alpha = 0.15f),
                radius = 20f,
                center = Offset(width * 0.75f, height * 0.55f)
            )
            
            drawCircle(
                color = Primary.copy(alpha = 0.12f),
                radius = 15f,
                center = Offset(width * 0.30f, height * 0.65f)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon/Logo Area
            Box(
                modifier = Modifier
                    .size(220.dp)
                    .scale(iconScale),
                contentAlignment = Alignment.Center
            ) {
                // Outer ring
                Box(
                    modifier = Modifier
                        .size(220.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Primary.copy(alpha = 0.15f),
                                    Primary.copy(alpha = 0.05f),
                                    Color.Transparent
                                )
                            ),
                            shape = CircleShape
                        )
                )
                
                // Middle ring
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Primary.copy(alpha = 0.20f),
                                    Primary.copy(alpha = 0.08f),
                                    Color.Transparent
                                )
                            ),
                            shape = CircleShape
                        )
                )
                
                // Logo container
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .background(
                            color = Color.White,
                            shape = CircleShape
                        )
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.titiktemu_logo),
                        contentDescription = "Titik Temu Logo",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Title
            Column(
                modifier = Modifier.alpha(contentAlpha),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Selamat Datang di",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Titik Temu",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Primary,
                    fontSize = 42.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Description
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Gray)) {
                        append("Kehilangan atau menemukan barang? ")
                    }
                    withStyle(style = SpanStyle(color = Primary, fontWeight = FontWeight.SemiBold)) {
                        append("Laporkan di sini")
                    }
                },
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .alpha(contentAlpha)
                    .padding(horizontal = 16.dp),
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(64.dp))

            // Buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(contentAlpha),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onNavigateToRegister,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "MULAI",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = onNavigateToLogin,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Primary
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "SUDAH PUNYA AKUN",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    TitikTemuTheme {
        WelcomeScreen(
            onNavigateToLogin = {},
            onNavigateToRegister = {}
        )
    }
}
