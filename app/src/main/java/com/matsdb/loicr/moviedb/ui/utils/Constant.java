package com.matsdb.loicr.moviedb.ui.utils;

/**
 * Classe contenant toutes les constante utilisées
 */

public class Constant {
    /**
     * INTENTs
     */
    public static final String INTENT_SEARCH = "INTENT_SEARCH";
    public static final String INTENT_TYPE_SEARCH = "INTENT_TYPE_SEARCH";
    public static final String INTENT_NUM_PAGE = "INTENT_NUM_PAGE";
    public static final String INTENT_ID_MOVIE = "INTENT_ID_MOVIE";
    public static final String INTENT_ID_PEOPLE = "INTENT_ID_PEOPLE";
    public static final String INTENT_URL_IMAGE_FULLSCREEN = "INTENT_URL_IMAGE_FULLSCREEN";
    public static final String INTENT_ID_TV = "INTENT_ID_TV";
    public static final String INTENT_TYPE_VIDEO = "INTENT_TYPE_VIDEO";
    public static final String INTENT_NUM_SEASON = "INTENT_NUM_SEASON";
    public static final String INTENT_NUM_EPISODE = "INTENT_NUM_EPISODE";

    // Premiere variable = type ; 2e variable = search ; 3e variable = page
    public static final String URL_SEARCH = "https://api.themoviedb.org/3/search/%s?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US&query=%s&page=%s&include_adult=false";

    /**
     * URLs Images / youtube
     */
    public static final String URL_IMAGE = "https://image.tmdb.org/t/p/w185%s";
    public static final String URL_IMAGE_500 = "https://image.tmdb.org/t/p/w500%s";
    public static final String URL_IMG_YOUTUBE = "http://img.youtube.com/vi/%s/0.jpg";

    /**
     * URLs MOVIE
     */
    public static final String URL_MOVIE = "https://api.themoviedb.org/3/movie/%s?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US";
    public static final String URL_UPCOMMING_MOVIE = "https://api.themoviedb.org/3/movie/upcoming?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US&page=%s";
    public static final String URL_POPULAR_MOVIE = "https://api.themoviedb.org/3/movie/popular?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US&page=%s";
    public static final String URL_VIDEO_MOVIE = "https://api.themoviedb.org/3/movie/%s/videos?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US";
    public static final String URL_CREDIT_MOVIE = "https://api.themoviedb.org/3/movie/%s/credits?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US";
    public static final String URL_REVIEWS_MOVIE = "https://api.themoviedb.org/3/movie/%s/reviews?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US";
    public static final String URL_RECOM_MOVIE = "https://api.themoviedb.org/3/movie/%s/recommendations?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US&page=%s";
    public static final String URL_SIMILAR_MOVIE = "https://api.themoviedb.org/3/movie/%s/similar?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US&page=%s";
    public static final String URL_TOPRATED_MOVIE = "https://api.themoviedb.org/3/movie/top_rated?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US&page=%s";

    /**
     * URLs TV
     */
    public static final String URL_TV = "https://api.themoviedb.org/3/tv/%s?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US";
    public static final String URL_TV_SEASON = "https://api.themoviedb.org/3/tv/%s/season/%s?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US";
    public static final String URL_TV_EPISODE = "https://api.themoviedb.org/3/tv/%s/season/%s/episode/%s?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US";
    public static final String URL_TV_SEASON_CREDIT = "https://api.themoviedb.org/3/tv/%s/season/%s/credits?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US";
    public static final String URL_TV_SEASON_VIDEO = "https://api.themoviedb.org/3/tv/%s/season/%s/videos?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US";
    public static final String URL_TV_EPISODE_CREDIT = "https://api.themoviedb.org/3/tv/%s/season/%s/episode/%s/credits?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US";
    public static final String URL_TV_EPISODE_VIDEO = "https://api.themoviedb.org/3/tv/%s/season/%s/episode/%s/videos?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US";
    public static final String URL_ONTHEAIR_TV = "https://api.themoviedb.org/3/tv/on_the_air?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US&page=%s";
    public static final String URL_TOPRATED_TV = "https://api.themoviedb.org/3/tv/top_rated?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US&page=%s";
    public static final String URL_POPULAR_TV = "https://api.themoviedb.org/3/tv/popular?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US&page=%s";
    public static final String URL_CREDIT_TV = "https://api.themoviedb.org/3/tv/%s/credits?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US";

    /**
     * URLs PEOPLE
     */
    public static final String URL_PEOPLE = "https://api.themoviedb.org/3/person/%s?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US";
    public static final String URL_PEOPLE_MOVIE = "https://api.themoviedb.org/3/person/%s/movie_credits?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US";
    public static final String URL_PEOPLE_TV = "https://api.themoviedb.org/3/person/%s/tv_credits?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US";
    public static final String URL_PEOPLE_IMAGE = "https://api.themoviedb.org/3/person/%s/images?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US";
    public static final String URL_POPULAR_PERSON = "https://api.themoviedb.org/3/person/popular?api_key=0b1ff33e0342b12f4621bf7c403996e8&language=en-US&page=%s";
}
