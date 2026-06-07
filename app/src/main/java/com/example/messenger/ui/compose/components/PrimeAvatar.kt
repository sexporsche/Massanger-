package com.example.messenger.ui.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.messenger.ui.compose.theme.PrimeColors

enum class AvatarSize(val dp: Dp) {
    Small(40.dp),
    Medium(48.dp),
    Large(72.dp),
    XLarge(96.dp)
}

@Composable
fun PrimeAvatar(
    name: String,
    photoUri: String? = null,
    size: AvatarSize = AvatarSize.Medium,
    isOnline: Boolean? = null,
    modifier: Modifier = Modifier
) {
    val initials = name.split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercaseChar().toString() }
        .ifBlank { name.take(1).uppercase() }

    Box(modifier = modifier.size(size.dp)) {
        Box(
            modifier = Modifier
                .size(size.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            if (!photoUri.isNullOrBlank()) {
                AsyncImage(
                    model = photoUri,
                    contentDescription = name,
                    modifier = Modifier
                        .size(size.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    text = initials,
                    style = when (size) {
                        AvatarSize.Small -> MaterialTheme.typography.labelLarge
                        AvatarSize.Medium -> MaterialTheme.typography.titleMedium
                        AvatarSize.Large -> MaterialTheme.typography.headlineSmall
                        AvatarSize.XLarge -> MaterialTheme.typography.headlineMedium
                    },
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        if (isOnline != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 2.dp, y = 2.dp)
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(if (isOnline) PrimeColors.Online else PrimeColors.Offline)
                    .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape)
            )
        }
    }
}

@Composable
fun PrimeUnreadBadge(
    count: Int,
    modifier: Modifier = Modifier
) {
    if (count <= 0) return
    Box(
        modifier = modifier
            .size(if (count > 9) 24.dp else 22.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (count > 99) "99+" else count.toString(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold
        )
    }
}
