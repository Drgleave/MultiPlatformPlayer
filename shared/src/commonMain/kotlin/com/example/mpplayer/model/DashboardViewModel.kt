package com.example.mpplayer.model


class DashboardViewModel {

    fun getAudios(): List<Track>{
        return listOf(
            Track(
                uri = "https://cdn.pixabay.com/download/audio/2022/12/13/audio_a7eaf8e68b.mp3",
                images = "https://images.unsplash.com/photo-1576525865260-9f0e7cfb02b3?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1364&q=80",
                artists = "Billie Eilish",
                name = "Bad Guy",
                duration = "0:35",
                id = "013koZTWTtNAcpl4Li2LyL2"
            ),
            Track(
                uri = "https://cdn.pixabay.com/download/audio/2022/04/29/audio_b41f9553b2.mp3",
                images = "https://images.unsplash.com/photo-1576525865260-9f0e7cfb02b3?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1364&q=80",
                artists = "Leonell Cassio",
                name = "Stuck In A Dream",
                duration = "4:58",
                id = "20koZTWTtNAcpl4Li2LyL31"
            ),
            Track(
                uri = "https://cdn.pixabay.com/download/audio/2022/01/26/audio_2694da1938.mp3",
                images = "https://images.unsplash.com/photo-1576525865260-9f0e7cfb02b3?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1364&q=80",
                artists = "Gvidon",
                name = "Success Starts With A Dream",
                duration = "2:01",
                id = "230koZTWTtNAcpl4Li2LyL1"
            )
        )
    }


    fun getPlaylists(): List<Playlist>{
        return listOf(
            Playlist(
                images = "https://images.unsplash.com/photo-1576525865260-9f0e7cfb02b3?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1364&q=80",
                name = "Hip Hop",
                id = "013koZTWTtNAcpl4Li2LyL2"
            )
        )
    }

}