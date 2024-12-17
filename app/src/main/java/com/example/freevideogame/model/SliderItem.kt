package com.example.freevideogame.model

data class SliderItem (
    val image: String = ""
)

class ListSlider {
    companion object {
        val slider: MutableList<SliderItem> = mutableListOf(
            SliderItem("https://shared.cloudflare.steamstatic.com/store_item_assets/steam/apps/1675900/capsule_616x353.jpg?t=1731059456"),
            SliderItem("https://readwrite.com/wp-content/uploads/2024/08/GETREADY-COD-NEXT-24-IMAGE-005.jpg"),
            SliderItem("https://p4.wallpaperbetter.com/wallpaper/448/477/194/scorpion-character-video-games-video-game-warriors-video-game-art-digital-art-hd-wallpaper-preview.jpg"),
            SliderItem("https://i.ytimg.com/vi/UchfadQhX7w/hq720.jpg?sqp=-oaymwEhCK4FEIIDSFryq4qpAxMIARUAAAAAGAElAADIQj0AgKJD&rs=AOn4CLA5W0UglLWcA1kEvJwXp1xyBzjPaQ"),
            SliderItem("https://i0.wp.com/highschool.latimes.com/wp-content/uploads/2021/03/Valorant.png?fit=1200%2C675&ssl=1"),
            SliderItem("https://shared.cloudflare.steamstatic.com/store_item_assets/steam/apps/2170050/capsule_616x353.jpg?t=1672737979"),
            SliderItem("https://cdn.wccftech.com/wp-content/uploads/2021/02/Dead-By-Daylight-Mobile-Freddy-1200x675-1.jpg"),
            SliderItem("https://jcmagazine.com/wp-content/uploads/2024/05/fortnite.jpg"),
            SliderItem("https://www.minecraft.net/content/dam/minecraftnet/games/minecraft/key-art/Vanilla-PMP_Collection-Carousel-0_Update-Aquatic_1280x768.jpg"),
            SliderItem("https://i.ytimg.com/vi/rZknDaPZnaw/maxresdefault.jpg"),
            SliderItem("https://static.toiimg.com/photo/89179962.cms"),
            SliderItem("https://fondosmil.co/fondo/13104.jpg"),
            SliderItem("https://www.motortrend.com/uploads/sites/25/2021/06/001-forza-horizon5-lead.jpg?w=768&width=768&q=75&format=webp"),
            SliderItem("https://wallpapercave.com/wp/84S1fUU.jpg"),
            SliderItem("https://wallpapers.com/images/hd/call-of-duty-warzone-4k-poster-jij79ziyns3oegfu.jpg"),
            SliderItem("https://m.media-amazon.com/images/I/81HS4reQ7jL.png"),
            SliderItem("https://shared.cloudflare.steamstatic.com/store_item_assets/steam/apps/302830/capsule_616x353.jpg?t=1569057315")
        )
    }
}


data class SliderTopGames(
    val image: String,
    val title: String
)

class ListSliderPopularGames {
    companion object {
        val slider: MutableList<SliderTopGames> = mutableListOf(
            SliderTopGames("https://static0.gamerantimages.com/wordpress/wp-content/uploads/2024/07/fortnite-key-visual-characters-skins.jpg", "Fornite"),
            SliderTopGames("https://www.minecraft.net/content/dam/minecraftnet/games/minecraft/key-art/Minecraft_JavaBedrock_Net_1170x500.jpg", "Minecraft"),
            SliderTopGames("https://media.contentapi.ea.com/content/dam/apex-legends/common/apex-price-change-key-art.jpg.adapt.crop16x9.431p.jpg", "Apex Legends"),
            SliderTopGames("https://cdn.wccftech.com/wp-content/uploads/2020/08/Genshin-Impact_Key-Art-EN-1.jpg", "Genshin Impact"),
            SliderTopGames("https://vgleaks.com/wp-content/uploads/2024/06/valorant-thumb-vdd-viendidong.jpg", "Valorant"),
            SliderTopGames("https://cdn1.epicgames.com/offer/9773aa1aa54f4f7b80e44bef04986cea/EGS_RocketLeague_PsyonixLLC_S3_2560x1440-18eac9b5df1028fdcd5bad480ab6b085", "Rocket League"),
            SliderTopGames("https://www.thestatesman.com/wp-content/uploads/2021/05/pubg-.jpg", "PUBG Mobile"),
            SliderTopGames("https://images.aftonbladet-cdn.se/v2/images/a5909b62-e119-4f2d-a71d-eebc6c5c0826?fit=crop&format=auto&h=1069&q=50&w=1900&s=6f5190258722a58c05264b11cfacb53d9abd49b1", "Hearthstone"),
            SliderTopGames("https://xxboxnews.blob.core.windows.net/prod/sites/2/2021/02/Fall-Guys-Key-Art_Thumb_JPG.jpg", "Fall Guys"),
            SliderTopGames("https://static.wikia.nocookie.net/among-us-wiki/images/f/f5/Among_Us_space_key_art_redesign.png/revision/latest?cb=20240408020750", "Among Us"),
            SliderTopGames("https://assets.nintendo.com/image/upload/c_fill,w_1200/q_auto:best/f_auto/dpr_2.0/ncom/software/switch/70010000043292/91e382e3e87726746dc714d3cab616a57ead6d797cff8c2f757b448534ce7cf4", "Pokémon Unite"),
            SliderTopGames("https://image.api.playstation.com/vulcan/ap/rnd/202205/1301/jvMS3mQUIyiOeGnZLkjG1SDN.png", "Dauntless"),
            SliderTopGames("https://image.api.playstation.com/vulcan/ap/rnd/202311/3020/4c8d2599033aaa3e03954719a343f0edf8636c1941627baf.png", "Brawlhalla"),
        )
    }
}


data class SliderWelcome(
    val image: String,
)

class ListSliderWelcome {
    companion object {
        val slider: MutableList<SliderWelcome> = mutableListOf(
            SliderWelcome("https://e0.pxfuel.com/wallpapers/151/421/desktop-wallpaper-video-games-high-resolution-gaming.jpg"),
            SliderWelcome("https://www.esportstalk.com/wp-content/uploads/2021/08/what-is-crossfire.jpg"),
            SliderWelcome("https://assets.nintendo.com/image/upload/ar_16:9,c_lpad,w_1240/b_white/f_auto/q_auto/ncom/pt_BR/dlc/switch-dlc/mortal-kombat-11-dlc/rom-bundle/mortal-kombat-11-ultimate/image"),
            SliderWelcome("https://cdn.antaranews.com/cache/1200x800/2022/01/17/chimeraland.jpg"),
            SliderWelcome("https://gpstatic.com/acache/40/28/1/uk/t620x300-d3a0fbbcdf18510e3cd116b4e1e50955.jpg"),
            SliderWelcome("https://images.ctfassets.net/rm5ymc69vmwz/2u6WDYzBuk8ZwMxkkjwazf/10c6f633652589e5e49eecb66ef53876/Game-Key-Art-Open-Beta_16x9.png"),
            SliderWelcome("https://www.undawn.game/images/card.png"),
            SliderWelcome("https://www.strafe.com/_next/image/?url=https%3A%2F%2Fwww.strafe.com%2Fbr%2Fapostas-esports%2Fstrafe-news%2Fwp-content%2Fuploads%2Fsites%2F30%2FSaint-League-of-Legends.jpg&w=3840&q=75"),
            SliderWelcome("https://www.allkeyshop.com/blog/wp-content/uploads/Path-of-exile-2-early-access-1.webp"),
            SliderWelcome("https://cdn1.epicgames.com/offer/badb0ee71b474ed591ec43212547cfc8/Paladins_EGS_BaseGame_2560x1440_2560x1440-99d0ed45c628c499ad600a52217b4123"),
            SliderWelcome("https://assets.nintendo.com/image/upload/ar_16:9,c_lpad,w_1240/b_white/f_auto/q_auto/ncom/software/switch/70010000013091/4043acf90fb7c854cb1ff51268330d20606d4a38e60b42a5ce0dae3ce018604b"),
            SliderWelcome("https://shared.cloudflare.steamstatic.com/store_item_assets/steam/apps/583950/capsule_616x353.jpg?t=1677266975"),

        )
    }
}


data class SliderGameRecommended(
    val image: String,
)

class ListSliderRecommended {
    companion object {
        val slider: MutableList<SliderGameRecommended> = mutableListOf(
            SliderGameRecommended("https://cdn2.unrealengine.com/fortnite-og-social-1920x1080-a5adda66fab9.jpg"),
            SliderGameRecommended("https://cms.disway.id/uploads/8c860935b41ef940268ef13b11de7541.jpeg"),
            SliderGameRecommended("https://www-static.warframe.com/images/longlanding/warframe-metacard.png"),
            SliderGameRecommended("https://updatecrazy.com/wp-content/uploads/2024/01/Phasmophobia-Update-0.9.4.0-Patch-Notes-v9.4.jpg"),
            SliderGameRecommended("https://cdn.mmohuts.com/wp-content/uploads/2015/03/StarWarsTheOldRepublic_604x423.jpg"),
            SliderGameRecommended("https://s1.pearlcdn.com/pearlabyss/news/61303a16b0720200608015441661.400x225.jpg"),
            SliderGameRecommended("https://m.media-amazon.com/images/M/MV5BNjBmODg3YjUtMmY0ZS00NTI3LTlmNDYtYjg2Yjc3Nzc2MTNjXkEyXkFqcGc@._V1_.jpg"),
            SliderGameRecommended("https://i.ytimg.com/vi/rH-eZd01NEQ/maxresdefault.jpg"),
            )
    }
}


data class SliderCategory(
    val image: String,
    val title: String
)

class ListSliderCategory {
    companion object {
        val slider: MutableList<SliderCategory> = mutableListOf(
            SliderCategory("https://i.blogs.es/423591/mmo-2023/1366_2000.jpeg", "MMORPG"),
            SliderCategory("https://winblog.s3.amazonaws.com/blog/wp-content/uploads/2023/05/juegos-shooter.jpg", "Shooter"),
            SliderCategory("https://cdn.oneesports.gg/cdn-data/2023/09/MLBB_patch1.8.20_preview.jpg", "MOBA"),
            SliderCategory("https://i0.wp.com/lavidaesunvideojuego.com/wp-content/uploads/2023/07/EspecialGenerosdelAnime17-LaVidaesunVideojuego.jpg?resize=736%2C414&ssl=1", "Anime"),
            SliderCategory("https://img.redbull.com/images/q_auto,f_auto/redbullcom/2023/2/19/h8zxvjmyhiuzq2khyoyr/fortnite-battle-royale", "Battle Royale"),
            SliderCategory("https://wallpapers.com/images/featured/strategy-game-5xjnikoco72n3i31.jpg", "Strategy"),
            SliderCategory("https://img.freepik.com/premium-vector/gaming-vector-background-landscape-video-game-fantasy-wallpaper-mountain-posters-controller_597121-846.jpg", "Fantasy"),
            SliderCategory("https://articles-images.sftcdn.net/wp-content/uploads/sites/3/2019/08/halo3thumb.jpg", "Sci-Fi"),
            SliderCategory("https://www.gameuidatabase.com/uploads/logo/Gwent:TheWitcherCardGame_Logo_06272021-121307.jpg", "Card Games"),
            SliderCategory("https://store-images.s-microsoft.com/image/apps.15477.13929305715509942.2f23e55b-b3c0-4a36-8941-40937bdee1ed.0c059add-bf87-4901-a187-6292f8be1694?q=90&w=480&h=270", "Racing"),
            SliderCategory("https://assets-prd.ignimgs.com/2022/08/10/top10fighting-1660101473258.jpg", "Fighting"),
            SliderCategory("https://cdn.mos.cms.futurecdn.net/7ExvFjuWZZnRohvNi5KQ9K-1200-80.jpg", "Social"),
            SliderCategory("https://cdn.mos.cms.futurecdn.net/HYA4i99zBVoF7iMaiyxu65.jpg", "Sports"),
            SliderCategory("https://assetsio.gnwcdn.com/best-mmo-mmorpgs-2022a.jpg?width=1600&height=900&fit=crop&quality=100&format=png&enable=upscale&auto=webp", "MMO"),
            SliderCategory("https://static0.gamerantimages.com/wordpress/wp-content/uploads/2022/08/best-sci-fi-mmorpgs-elite-dangerous-zenith-the-last-city-warframe.jpg", "MMOFPS"),
            SliderCategory("https://i.kinja-img.com/image/upload/c_fill,h_675,pg_1,q_80,w_1200/0868fb4f9f301cb9c28dff43960461dd.jpg", "Action RPG"),
            SliderCategory("https://www.play3r.site/wp-content/uploads/2024/03/THE-SANDBOX.jpg.webp", "Sandbox"),
            SliderCategory("https://static0.gamerantimages.com/wordpress/wp-content/uploads/2023/06/the-best-open-world-games-with-female-protagonists-b.jpg", "Open World"),
            SliderCategory("https://assets.nintendo.com/image/upload/ar_16:9,c_lpad,w_1240/b_white/f_auto/q_auto/ncom/software/switch/70010000010940/9a7907ab2658ed8fe077a2f093b773bd87e76b039fcce2a29afa36a7c72cfad4", "Survival"),
            SliderCategory("https://cdn.mos.cms.futurecdn.net/EjGu8DzKUmBgRp2hHj278Y.jpg", "MMOTPS"),
            SliderCategory("https://i.blogs.es/330362/dota-undelords/832_435.jpg", "PvP"),
            SliderCategory("https://cdn.prod.website-files.com/646df590700064e1c084f708/659fd5766f275ab76d78fb0b_659fb0d0bb99f264c0be7686_Main_RPG_VS_RTS.jpeg", "PvE"),
            SliderCategory("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRx_glIDCkc1_roQBKFgiwf7Dj8N4g6RxLFFQ&s", "Pixel"),
            SliderCategory("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQZQI3DKVRGf9RsfLzhS2rnC5wID5xMl94qwEgh1gqscLSIHkJxes8EDnCCSvfyv-zeY-8&usqp=CAU", "MMORTS"),
            SliderCategory("https://winblog.s3.amazonaws.com/blog/wp-content/uploads/2023/06/tipos-de-videojuegos-de-disparos-call-of-duty-533x300.jpg", "Action"),
            SliderCategory("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSaQLOm57W_BzlKV4687A4Bz-1HOPT3mmOG-Q&s", "Voxel"),
            SliderCategory("https://media.steelseriescdn.com/blog/posts/best-zombies-loadout-in-modern-warfare-3/zombies-7_86fe8400b84143cfbc387f7aaf6d97e4.jpg", "Zombie"),
            SliderCategory("https://www.digitaltrends.com/wp-content/uploads/2021/12/darkest-dungeon-2-review-3.jpg?fit=720%2C720&p=1", "Turn-Based"),
            SliderCategory("https://static1.makeuseofimages.com/wordpress/wp-content/uploads/2020/11/Borderlands-First-Person-View.jpg", "First Person"),
            SliderCategory("https://i.ytimg.com/vi/WlYG3dXQH_s/hq720.jpg?sqp=-oaymwEhCK4FEIIDSFryq4qpAxMIARUAAAAAGAElAADIQj0AgKJD&rs=AOn4CLALLtccB0UhwUTbyt_xiIh9hug4Lg", "Third Person"),
            SliderCategory("https://static1.thegamerimages.com/wordpress/wp-content/uploads/2023/03/top-down-or-isometric-games-metal-gear-solid-disco-elysium-and-stardew-valley.jpg", "Top-Down"),
            SliderCategory("https://meliorgames.com/wp-content/uploads/2019/11/3-d-games.jpg", "3D Graphics"),
            SliderCategory("https://cdn.prod.website-files.com/5f6c71314a8db82b6218d0ea/66a796f02d40efdcb2e5388d_65dda9a2aa58214484619d39_1-unliving.webp", "2D Graphics"),
            SliderCategory("https://static01.nyt.com/images/2011/09/17/arts/SUB-TANKS1/SUB-TANKS1-articleLarge.jpg", "Tank"),
            SliderCategory("https://cdn.mos.cms.futurecdn.net/rTYnkSNCtMHK2vQdKYwD2Y.jpg", "Space"),
            SliderCategory("https://substackcdn.com/image/fetch/w_1456,c_limit,f_auto,q_auto:good,fl_progressive:steep/https%3A%2F%2Fsubstack-post-media.s3.amazonaws.com%2Fpublic%2Fimages%2F3671411a-3d92-41cc-b762-7d66bb8e28f7_3840x2160.jpeg", "Sailing"),
            SliderCategory("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSaOHc3K829WfIT-w4N4wwDtoC0h5sjYC5Ehg&s", "3Side Scroller"),
            SliderCategory("https://cdn3.whatculture.com/images/2015/07/tw4CZUnH.jpg", "Superhero"),
            SliderCategory("https://cdn3.whatculture.com/images/2018/06/ec4480e139b35b78-600x338.jpg", "Permadeath")

        )
    }
}