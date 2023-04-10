package ng.devtamuno.unsplash.compose.ui.navigation

const val ARG_PHOTO_ID = "movie_id"

enum class PhotoScreen(val route: String) {
    PHOTO_LIST("photo_list"),
    PHOTO_DETAIL("photo/{$ARG_PHOTO_ID}/detail");

    fun createPath(vararg args: Any): String {
        var route = route
        require(args.size == route.argumentCount) {
            "Provided ${args.count()} parameters, was expected ${route.argumentCount} parameters!"
        }
        route.arguments().forEachIndexed { index, matchResult ->
            route = route.replace(matchResult.value, args[index].toString())
        }
        return route
    }
}
