package com.stis.titiktemu.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.stis.titiktemu.data.model.Laporan
import com.stis.titiktemu.ui.theme.Border
import com.stis.titiktemu.ui.theme.DangerLight
import com.stis.titiktemu.ui.theme.DangerRed
import com.stis.titiktemu.ui.theme.Primary
import com.stis.titiktemu.ui.theme.Surface
import com.stis.titiktemu.ui.theme.SuccessGreen
import com.stis.titiktemu.ui.theme.SuccessLight
import com.stis.titiktemu.ui.theme.TextPrimary
import com.stis.titiktemu.ui.theme.TextSecondary
import com.stis.titiktemu.ui.theme.TextTertiary
import com.stis.titiktemu.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaporanCard(
    laporan: Laporan,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = laporan.judul,
                        style = Typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = laporan.kategori,
                        style = Typography.labelSmall,
                        color = TextTertiary
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                // Tipe Badge
                val (bgColor, fgColor) = if (laporan.tipe == "HILANG") {
                    Pair(DangerLight, DangerRed)
                } else {
                    Pair(SuccessLight, SuccessGreen)
                }
                AssistChip(
                    onClick = {},
                    label = {
                        Text(
                            text = laporan.tipe,
                            style = Typography.labelSmall,
                            color = fgColor
                        )
                    },
                    modifier = Modifier,
                    shape = RoundedCornerShape(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = laporan.deskripsi,
                style = Typography.bodySmall,
                color = TextSecondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📍 ${laporan.lokasi}",
                    style = Typography.labelSmall,
                    color = TextTertiary,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = laporan.tanggalKejadian,
                    style = Typography.labelSmall,
                    color = TextTertiary
                )
            }
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val bgColor = if (status == "AKTIF") Primary else TextTertiary
    Box(
        modifier = Modifier
            .background(color = bgColor, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = status,
            style = Typography.labelSmall,
            color = Surface
        )
    }
}

@Composable
fun KategoriChip(kategori: String) {
    AssistChip(
        onClick = {},
        label = {
            Text(
                text = kategori,
                style = Typography.labelSmall
            )
        },
        shape = RoundedCornerShape(12.dp)
    )
}
