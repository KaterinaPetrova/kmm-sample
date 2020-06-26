package com.jetbrains.handson.mpp.mobile
import kotlinx.coroutines.launch

fun GifService.getGifs(types: List<GifType>, completion: (List<Gif>) -> Unit) {
    launch {
        completion(getGifs(types))
    }
}


