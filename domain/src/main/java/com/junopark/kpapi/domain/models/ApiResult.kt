package com.junopark.kpapi.domain.models

import com.junopark.kpapi.entities.awards.AwardItem
import com.junopark.kpapi.entities.boxoffice.BoxOfficeItem
import com.junopark.kpapi.entities.distribution.DistributionItem
import com.junopark.kpapi.entities.facts.FactItem
import com.junopark.kpapi.entities.films.FilmItemBig
import com.junopark.kpapi.entities.filter.FilterResponse
import com.junopark.kpapi.entities.seasons.EpisodesItem
import kotlinx.coroutines.flow.Flow

sealed interface ApiResult {
    sealed interface ApiSuccess {
        data class FilmList             (val items: List<FilmItemBig>, val pages: Int = 1   ) : ApiResult
        data class SingleFilm           (val item: FilmItemBig                              ) : ApiResult
        data class EpisodesList         (val items: List<EpisodesItem>                      ) : ApiResult
        data class FiltersList          (val item: FilterResponse                           ) : ApiResult
        data class FactsList            (val items: List<FactItem>                          ) : ApiResult
        data class AwardsList           (val items: List<AwardItem>                         ) : ApiResult
        data class DistributionList     (val items: List<DistributionItem>                  ) : ApiResult
        data class BoxOfficeList        (val items: List<BoxOfficeItem>                     ) : ApiResult
        object Empty : ApiResult
    }
    data class ApiError     (val code: Int? = null,     val message: String? = null ) : ApiResult
    data class ApiException (val e: Throwable? = null,  val message: String? = null ) : ApiResult
}