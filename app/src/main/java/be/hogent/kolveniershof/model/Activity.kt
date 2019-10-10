package be.hogent.kolveniershof.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Activity entity
 *
 * @property id
 * @property name
 * @property iconUrl
 */
@Parcelize
data class Activity(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name  = "name")
    val name: String,
    @field:Json(name = "icon")
    val iconUrl: String
) : Parcelable