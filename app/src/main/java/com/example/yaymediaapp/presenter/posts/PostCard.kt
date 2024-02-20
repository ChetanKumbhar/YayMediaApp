package com.example.yaymediaapp.presenter.posts

import androidx.annotation.DrawableRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.ImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.yaymediaapp.R
import com.example.yaymediaapp.data.model.Comment
import com.example.yaymediaapp.data.model.CommentWithUser
import com.example.yaymediaapp.data.model.Post
import com.example.yaymediaapp.data.model.PostWithData
import com.example.yaymediaapp.data.model.User
import com.example.yaymediaapp.ui.theme.YayMediaAppTheme
import org.ocpsoft.prettytime.PrettyTime
import java.util.Date


@Composable
fun PostCard(
    postWithData: PostWithData,
    currentUserId: String?,
    onLikeOrDislike: (String) -> Unit,
    preview: Boolean = false
) {
    val prettyTime = remember { PrettyTime() }
    val isLiked = postWithData.likes.find { it.user.id == currentUserId } != null

    Column(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painter(
                    url = postWithData.user.imageUrl,
                    tool = R.drawable.profile_pitcure,
                    preview = preview
                ),
                contentDescription = "Image de profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = postWithData.user.username,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = prettyTime.format(postWithData.post.date),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }

        postWithData.post.description?.let { description ->
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        } ?: Spacer(modifier = Modifier.height(16.dp))

        PostCardImage(
            postWithData = postWithData,
            isLiked = isLiked,
            preview = preview
        ) {
            onLikeOrDislike(postWithData.post.id)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Like
            LikeButton(isLiked = isLiked) {
                onLikeOrDislike(postWithData.post.id)
            }

            Spacer(modifier = Modifier.weight(1f))

        }
    }
}



@Composable
fun PostCardImage(
    postWithData: PostWithData,
    isLiked: Boolean,
    preview: Boolean = true,
    onDoubleTap: (Boolean) -> Unit
) {
    val currentState = if (isLiked) LikeState.Liked else LikeState.Unliked
    val transition = updateTransition(targetState = currentState, "Like transition")
    val elevation by transition.animateDp(label = "Card elevation") { state ->
        when (state) {
            LikeState.Unliked -> 0.dp
            LikeState.Liked -> 8.dp
        }
    }
    val scale by transition.animateFloat(
        label = "Card scale",
        transitionSpec = {
            when {
                LikeState.Unliked isTransitioningTo LikeState.Liked -> keyframes {
                    durationMillis = 350
                    0.99f at 100
                    1.03f at 220
                }
                else -> tween()
            }
        }
    ) { state ->
        when (state) {
            LikeState.Unliked -> 1f
            LikeState.Liked -> 1f
        }
    }

    Card(
        shape = MaterialTheme.shapes.medium,
        //elevation = CardElevation()//elevation,
        modifier = Modifier
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        onDoubleTap(isLiked)
                    }
                )
            }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painter(
                    postWithData.post.imageUrl,
                    R.drawable.post_image_example,
                    preview = preview
                ),
                contentDescription = "Post's image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 20.dp, max = 500.dp)
            )
        }
    }
}

@Composable
fun LikeButton(isLiked: Boolean, onClick: () -> Unit) {
    Crossfade(targetState = isLiked, label = "") { liked ->
        if (liked) {
            IconButton(onClick = onClick) {
                Icon(
                    Icons.Rounded.Favorite,
                    contentDescription = "Unlike icon",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            IconButton(onClick = onClick) {
                Icon(Icons.Rounded.FavoriteBorder, contentDescription = "Like icon")
            }
        }
    }
}

enum class LikeState {
    Liked,
    Unliked
}

@Composable
fun painter(url: String, @DrawableRes tool: Int, preview: Boolean): ImagePainter =
    rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = url).apply(block = fun ImageRequest.Builder.() {
            crossfade(true)
            if (preview) placeholder(tool)
        }).build()
    )

@Preview
@Composable
fun PostCardImagePreview() {
    YayMediaAppTheme {
        PostCardImage(postWithData = fakePost, isLiked = false, preview = true) {}
    }
}

@Preview(showBackground = true)
@Composable
fun PostCardPreview() {
    YayMediaAppTheme {
        PostCard(
            postWithData = fakePost,
            preview = true,
            currentUserId = "uid",
            onLikeOrDislike = {}
        )
    }
}

private val fakePost = PostWithData(
    post = Post(
        id = "test",
        imageUrl = "",
        date = Date(),
       // date = Date(1708225192000), // Sunday, February 18, 2024 2:59:52 AM
        userId = "test",
        description = "Today, we walk all day long in the mountain and it was awesome !"
    ),
    user = User(id = "test", username = "vince.app", name = "Vincent", ""),
    comments = listOf(
        CommentWithUser(
            comment = Comment("", Date(), "Yo mec comment ça va ?", "", ""),
            user = User("", "sophy.algs", "Vince", "")
        ),
        CommentWithUser(
            comment = Comment("", Date(), "Trop beau !", "", ""),
            user = User("", "romain.glb", "Vince", "")
        )
    ),
    likes = listOf()
)