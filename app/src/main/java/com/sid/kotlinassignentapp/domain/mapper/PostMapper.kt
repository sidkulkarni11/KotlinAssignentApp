package com.sid.kotlinassignentapp.domain.mapper

import com.sid.kotlinassignentapp.data.remote.dto.PostDto
import com.sid.kotlinassignentapp.domain.model.Post

fun PostDto.toDomain(): Post {
    return Post(
        id = id,
        title = title,
        body = body
    )
}
