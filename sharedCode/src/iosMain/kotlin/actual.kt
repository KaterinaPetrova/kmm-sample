package com.epetrova.kmm_sample

import kotlinx.coroutines.launch

fun GifService.getGifs(types: List<GifType>, completion: (List<Gif>) -> Unit) {
    launch {
        completion(getGifs(types))
    }
}


