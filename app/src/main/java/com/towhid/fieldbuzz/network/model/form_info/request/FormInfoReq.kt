package com.towhid.fieldbuzz.network.model.form_info.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FormInfoReq(
        @SerializedName("tsync_id")
        @Expose
        private val tsyncId: String,

        @SerializedName("name")
        @Expose
        private val name: String,

        @SerializedName("email")
        @Expose
        private val email: String,

        @SerializedName("phone")
        @Expose
        private val phone: String,

        @SerializedName("full_address")
        @Expose
        private val fullAddress: String?,

        @SerializedName("name_of_university")
        @Expose
        private val nameOfUniversity: String,

        @SerializedName("graduation_year")
        @Expose
        private val graduationYear: Int,

        @SerializedName("cgpa")
        @Expose
        private val cgpa: Double?,

        @SerializedName("experience_in_months")
        @Expose
        private val experienceInMonths: Int?,

        @SerializedName("current_work_place_name")
        @Expose
        private val currentWorkPlaceName: String?,

        @SerializedName("applying_in")
        @Expose
        private val applyingIn: String,

        @SerializedName("expected_salary")
        @Expose
        private val expectedSalary: Int,

        @SerializedName("field_buzz_reference")
        @Expose
        private val fieldBuzzReference: String?,

        @SerializedName("github_project_url")
        @Expose
        private val githubProjectUrl: String,

        @SerializedName("cv_file")
        @Expose
        private val cvFile: CvFile,

        @SerializedName("on_spot_update_time")
        @Expose
        private val onSpotUpdateTime: Long,

        @SerializedName("on_spot_creation_time")
        @Expose
        private val onSpotCreationTime: Long
)

