package com.junopark.kpapi.entities.films

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.junopark.kpapi.entities.common.CountryItem
import com.junopark.kpapi.entities.common.GenreItem

@Entity
data class FilmItemBig(
    @SerializedName("kinopoiskId"                ) @PrimaryKey var kinopoiskId                : Int?                 = null,
    @SerializedName("filmId"                     ) var filmId                     : Int?                 = null,
    @SerializedName("imdbId"                     ) var imdbId                     : String?              = null,
    @SerializedName("nameRu"                     ) var nameRu                     : String?              = null,
    @SerializedName("nameEn"                     ) var nameEn                     : String?              = null,
    @SerializedName("nameOriginal"               ) var nameOriginal               : String?              = null,
    @SerializedName("posterUrl"                  ) var posterUrl                  : String?              = null,
    @SerializedName("posterUrlPreview"           ) var posterUrlPreview           : String?              = null,
    @SerializedName("coverUrl"                   ) var coverUrl                   : String?              = null,
    @SerializedName("logoUrl"                    ) var logoUrl                    : String?              = null,
    @SerializedName("reviewsCount"               ) var reviewsCount               : Int?                 = null,
    @SerializedName("ratingGoodReview"           ) var ratingGoodReview           : String?              = null,
    @SerializedName("ratingGoodReviewVoteCount"  ) var ratingGoodReviewVoteCount  : Int?                 = null,
    @SerializedName("ratingKinopoisk"            ) var ratingKinopoisk            : String?              = null,
    @SerializedName("rating"                     ) var rating                     : String?              = null,
    @SerializedName("ratingKinopoiskVoteCount"   ) var ratingKinopoiskVoteCount   : Int?                 = null,
    @SerializedName("ratingImdb"                 ) var ratingImdb                 : String?              = null,
    @SerializedName("ratingImdbVoteCount"        ) var ratingImdbVoteCount        : Int?                 = null,
    @SerializedName("ratingFilmCritics"          ) var ratingFilmCritics          : String?              = null,
    @SerializedName("ratingFilmCriticsVoteCount" ) var ratingFilmCriticsVoteCount : Int?                 = null,
    @SerializedName("ratingAwait"                ) var ratingAwait                : String?              = null,
    @SerializedName("ratingAwaitCount"           ) var ratingAwaitCount           : Int?                 = null,
    @SerializedName("ratingRfCritics"            ) var ratingRfCritics            : String?              = null,
    @SerializedName("ratingRfCriticsVoteCount"   ) var ratingRfCriticsVoteCount   : Int?                 = null,
    @SerializedName("webUrl"                     ) var webUrl                     : String?              = null,
    @SerializedName("year"                       ) var year                       : Int?                 = null,
    @SerializedName("filmLength"                 ) var filmLength                 : String?              = null,
    @SerializedName("slogan"                     ) var slogan                     : String?              = null,
    @SerializedName("description"                ) var description                : String?              = null,
    @SerializedName("shortDescription"           ) var shortDescription           : String?              = null,
    @SerializedName("editorAnnotation"           ) var editorAnnotation           : String?              = null,
    @SerializedName("isTicketsAvailable"         ) var isTicketsAvailable         : Boolean?             = null,
    @SerializedName("productionStatus"           ) var productionStatus           : String?              = null,
    @SerializedName("type"                       ) var type                       : String?              = null,
    @SerializedName("ratingMpaa"                 ) var ratingMpaa                 : String?              = null,
    @SerializedName("ratingAgeLimits"            ) var ratingAgeLimits            : String?              = null,
    @SerializedName("hasImax"                    ) var hasImax                    : Boolean?             = null,
    @SerializedName("has3D"                      ) var has3D                      : Boolean?             = null,
    @SerializedName("lastSync"                   ) var lastSync                   : String?              = null,
    @SerializedName("countries"                  ) var countries                  : ArrayList<CountryItem>   = arrayListOf(),
    @SerializedName("genres"                     ) var genreItems                 : ArrayList<GenreItem>     = arrayListOf(),
    @SerializedName("startYear"                  ) var startYear                  : Int?                 = null,
    @SerializedName("endYear"                    ) var endYear                    : Int?                 = null,
    @SerializedName("serial"                     ) var serial                     : Boolean?             = null,
    @SerializedName("shortFilm"                  ) var shortFilm                  : Boolean?             = null,
    @SerializedName("completed"                  ) var completed                  : Boolean?             = null,
)
