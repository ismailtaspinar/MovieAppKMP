package com.ismailtaspinar.movieAppKmp.ui.components.imageLoad

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ismailtaspinar.movieAppKmp.ui.theme.AppColors
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url

@Composable
fun KamelAsyncImage(
    url: String,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    shape: Shape = RoundedCornerShape(0.dp),
    backgroundColor: Color = AppColors.surfaceVariant,
    placeholder: @Composable BoxScope.(progress: Float) -> Unit = {
        CircularProgressIndicator(
            progress = { it },
            modifier = Modifier.size(40.dp),
            color = AppColors.primary
        )
    },
    error: @Composable BoxScope.(Throwable) -> Unit = {
        Text(
            text = "🎬",
            fontSize = 32.sp,
            color = AppColors.onSurfaceVariant
        )
    }
) {
    KamelImage(
        resource = { asyncPainterResource(Url(url)) },
        contentDescription = contentDescription,
        modifier = modifier
            .clip(shape)
            .background(backgroundColor),
        contentScale = contentScale,
        onLoading = placeholder,
        onFailure = error
    )
}