package be.hogent.kolveniershof.api

import be.hogent.kolveniershof.network.*
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface KolvApi {

    /**
     * Signs in existing users
     *
     * @param email
     * @param password
     * @return User with token
     */
    @FormUrlEncoded
    @POST("users/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Single<NetworkUser>

    /**
     * Checks is email is valid and unique
     *
     * @param email
     * @return true if valid
     */
    @FormUrlEncoded
    @POST("users/isValidEmail")
    fun isValidEmail(@Field("email") email: String): Single<Boolean>

    /**
     * Gets user by email
     *
     * @param email
     * @return user
     */
    @GET("users/{email}")
    fun getUserByEmail(@Path("email") email: String): Observable<NetworkUser>

    /**
     * Gets user by id
     *
     * @param id
     * @return user
     */
    @GET("users/id/{id}")
    fun getUserById(@Path("id") id: String): Observable<NetworkUser>

    /**
     * Gets workdays
     *
     * @param authToken
     * @return workdays
     */
    @GET("workdays")
    fun getWorkdays(@Header("Authorization") authToken: String) : Observable<List<NetworkWorkday>>

    /**
     * Gets workday by id
     *
     * @param authToken
     * @param id
     * @return workday
     */
    @GET("workdays/id/{id}")
    fun getWorkdayById(@Header("Authorization") authToken: String, @Path("id") id: String) : Observable<NetworkWorkday>

    /**
     * Gets workday by date by user
     *
     * @param authToken
     * @param dateString
     * @param userId
     * @return
     */
    @GET("workdays/date/{date}/{user}")
    fun getWorkdayByDateByUser(@Header("Authorization") authToken: String, @Path("date") dateString: String, @Path("user") userId: String) : Observable<NetworkWorkday>

    @GET("activities/id/{id}")
    fun getActivityById(@Header("id") id: String) : Observable<NetworkActivity>

    @GET("bus/id/{id}")
    fun getBusById(@Header("id") id: String) : Observable<NetworkBus>
}