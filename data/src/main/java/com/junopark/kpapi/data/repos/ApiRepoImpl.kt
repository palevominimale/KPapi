@file:Suppress("UNCHECKED_CAST")

package com.junopark.kpapi.data.repos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.gson.Gson
import com.junopark.kpapi.data.api.ApiRequest
import com.junopark.kpapi.domain.interfaces.ApiRepo
import com.junopark.kpapi.domain.models.ApiResult
import com.junopark.kpapi.entities.ListResponse
import com.junopark.kpapi.entities.awards.AwardsResponse
import com.junopark.kpapi.entities.boxoffice.BoxOfficeResponse
import com.junopark.kpapi.entities.distribution.DistributionResponse
import com.junopark.kpapi.entities.facts.FactsResponse
import com.junopark.kpapi.entities.films.FilmItemBig
import com.junopark.kpapi.entities.filter.FilterResponse
import com.junopark.kpapi.entities.filteredsearch.FilteredSearchResponse
import com.junopark.kpapi.entities.keywordsearch.KeywordSearchResponse
import com.junopark.kpapi.entities.seasons.SeasonsResponse
import com.junopark.kpapi.entities.similar.SimilarResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiRepoImpl : ApiRepo {

    private val scope = CoroutineScope(Dispatchers.IO)
    private val apiState = MutableStateFlow<ApiResult>(ApiResult.ApiSuccess.Empty)
    private var loader : suspend (Int) -> ApiResult = { ApiResult.ApiSuccess.FilmList(emptyList()) }
    override val state : StateFlow<ApiResult> get() = apiState
    override var listFlow: Flow<PagingData<FilmItemBig>> = Pager(PagingConfig(pageSize = 20)) {
        ListSource {
            when(val res = loader(it)) {
                is ApiResult.ApiSuccess.FilmList -> { res.items }
                else -> emptyList()
            }
        }
    }.flow.cachedIn(scope)

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val request = it.request().newBuilder()
                .addHeader("X-API-KEY", "c9c408e6-3675-408b-9a6a-4027f534b2a8")
                .addHeader("Content-Type", "application/json")
                .build()
            it.proceed(request)
        }
        .build()

    private val gson = Gson().newBuilder().create()
    private val gsonConverterFactory = GsonConverterFactory.create(gson)

    private val retrofit: ApiRequest by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(ApiRequest.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(ApiRequest::class.java)
    }

    private suspend fun <T> handleApi(execute: suspend () -> Call<T>) : ApiResult {
        return try {
            val response = execute.invoke().execute()
            val body = response.body()
            if(response.isSuccessful && body != null) when(body) {
                is ListResponse ->              ApiResult.ApiSuccess.FilmList(items = body.items, pages = body.pagesCount ?: 1)
                is SimilarResponse ->           ApiResult.ApiSuccess.FilmList(items = body.items)
                is KeywordSearchResponse ->     ApiResult.ApiSuccess.FilmList(items = body.films)
                is FilteredSearchResponse ->    ApiResult.ApiSuccess.FilmList(items = body.items)
                is FilmItemBig ->               ApiResult.ApiSuccess.SingleFilm(item = body)
                is SeasonsResponse ->           ApiResult.ApiSuccess.EpisodesList(items = body.items)
                is FactsResponse ->             ApiResult.ApiSuccess.FactsList(items = body.items)
                is DistributionResponse ->      ApiResult.ApiSuccess.DistributionList(items = body.items)
                is BoxOfficeResponse ->         ApiResult.ApiSuccess.BoxOfficeList(items = body.items)
                is AwardsResponse ->            ApiResult.ApiSuccess.AwardsList(items = body.items)
                is FilterResponse ->            ApiResult.ApiSuccess.FiltersList(item = body)
                else ->                         ApiResult.ApiSuccess.Empty
            } else ApiResult.ApiError(code = response.code(), message = response.message())
        } catch (e: HttpException) {
            ApiResult.ApiError(code = e.code(), message = e.localizedMessage)
        } catch (e: Throwable) {
            ApiResult.ApiException(e)
        }
    }

    override suspend fun getTop(type: String, page: Int) {
        apiState.emit(handleApi { retrofit.getTop(type, page) })
        loader = { handleApi { retrofit.getTop(type, it) } }
        listFlow = Pager(PagingConfig(pageSize = 20)) {
            ListSource {
                when(val res = loader(it)) {
                    is ApiResult.ApiSuccess.FilmList -> { res.items }
                    else -> emptyList()
                }
            }
        }.flow.cachedIn(scope)
    }
    override suspend fun getById(id: Int) = apiState.emit(handleApi { retrofit.getById(id) })
    override suspend fun getSeasons(id: Int) = apiState.emit(handleApi { retrofit.getSeasons(id) })
    override suspend fun getFacts(id: Int) = apiState.emit(handleApi { retrofit.getFacts(id) })
    override suspend fun getDistributions(id: Int) = apiState.emit(handleApi { retrofit.getDistributions(id) })
    override suspend fun getBoxOffice(id: Int) = apiState.emit(handleApi { retrofit.getBoxOffice(id) })
    override suspend fun getAwards(id: Int) = apiState.emit(handleApi { retrofit.getAwards(id) })
    override suspend fun getSimilar(id: Int) = apiState.emit(handleApi { retrofit.getSimilar(id) })
    override suspend fun getRelated(id: Int) = apiState.emit(handleApi { retrofit.getRelated(id) })
    override suspend fun getFilters() = apiState.emit(handleApi { retrofit.getFilters() })
    override suspend fun getByFilter(
        countries: String?,
        genres: String?,
        order: String?,
        type: String?,
        ratingFrom: Int?,
        ratingTo: Int?,
        yearFrom: Int?,
        yearTo: Int?,
        imdbId: String?,
        keyword: String?,
        page: Int?
    ) {
        apiState.emit(handleApi {
            retrofit.getByFilter(countries, genres, order, type, ratingFrom, ratingTo, yearFrom, yearTo, imdbId, keyword, page)
        })
        loader = { handleApi {
            retrofit.getByFilter(countries, genres, order, type, ratingFrom, ratingTo, yearFrom, yearTo, imdbId, keyword, it) } }
        listFlow = Pager(PagingConfig(pageSize = 20)) {
            ListSource {
                when(val res = loader(it)) {
                    is ApiResult.ApiSuccess.FilmList -> { res.items }
                    else -> emptyList()
                }
            }
        }.flow.cachedIn(scope)
    }

    override suspend fun getByKeywordSearch(query: String, page: Int) {
        apiState.emit(handleApi { retrofit.getByKeywordSearch(query, page) })
        loader =  { handleApi { retrofit.getByKeywordSearch(query, it) } }
        listFlow = Pager(PagingConfig(pageSize = 20)) {
            ListSource {
                when(val res = loader(it)) {
                    is ApiResult.ApiSuccess.FilmList -> { res.items }
                    else -> emptyList()
                }
            }
        }.flow.cachedIn(scope)
    }

    override suspend fun getReleases(year: Int, month: String, page: Int) {
        apiState.emit(handleApi { retrofit.getReleases(year, month, page) })
        loader =  { handleApi { retrofit.getReleases(year, month, it) } }
        listFlow = Pager(PagingConfig(pageSize = 20)) {
            ListSource {
                when(val res = loader(it)) {
                    is ApiResult.ApiSuccess.FilmList -> { res.items }
                    else -> emptyList()
                }
            }
        }.flow.cachedIn(scope)
    }
}