package com.example.decise.api

interface SecuredApi {
 /*   @GET("get-all-members")
    suspend fun getAllMember(): Response<ResponseAllMember>

    @GET("get_departments")
    suspend fun getDepartments(): Response<ResponseDepartments>

    @GET("get_bloodgroups")
    suspend fun getBloodGroups(): Response<ResponseBloodGroup>

    @GET("get_districts")
    suspend fun getDistricts(): Response<ResponseDistrict>

    @GET("get_occupations")
    suspend fun getOccupations(): Response<ResponseOccupations>

    @GET("get_halls")
    suspend fun getHalls(): Response<ResponseHalls>

    @GET("get-userinfo-profile")
    suspend fun getProfileInfo(): Response<ResponseProfileInfo>

    @POST("search")
    //@Header("AUTHORIZATION") value: String
    suspend fun getSearchResult(
        @Body page: RequestSearch
    ): ResponseSearch

    @POST("logout")
    suspend fun logout(): Response<ResponseLogout>

    @GET("members_dashboard")
    suspend fun getDashboardInfo(): Response<ResponseDashboardInfo>

    @Multipart
    @POST("member-profile-update/{userID}")
    suspend fun uploadProfilePic(
        @Path("userID") userId: Int,
        @Part image: MultipartBody.Part,
    ): Response<ResponseUploadProfilePic>

    @POST("pay-renew")
    fun payRenew(@Body renew: RequestPayRenew): Call<ResponseBody>

    @POST("fund-payment")
    fun fundPayment(@Body fund: RequestFundCollection): Call<ResponseBody>
    @POST("details-profile-update/{userID}")
    suspend fun updateProfile(
        @Path("userID") userId: Int,
        @Body requestProfileUpdate: RequestProfileUpdate
    ): Response<ResponseUploadProfilePic>


    @GET("payment-history")
    suspend fun getTransactionHistory(): Response<TransHistory>

    @Multipart
    @POST("send-voucher")
    suspend fun uploadVoucher(
        @Part image: MultipartBody.Part,
        @Part("date")  date: RequestBody,
        @Part("amount")  amount: RequestBody,
        @Part("voucher_number")  voucher_number: RequestBody,
    ): Response<ResponseVoucherUpload>

    @GET("voucher-list")
    suspend fun getVoucherList(): Response<ResponseVoucherList>

    @POST("set-app-location")
    suspend fun setCurrentLocations(@Body req: RequestSetCLocation): Response<ResponseSetCLocantion>
    //user Locations
    @GET("locations")
    suspend fun getUserLocations(): Response<ResponseUserLocation>
    @GET("fee-list")
    suspend fun getFeeList(): Response<ResponseFeeList>*/

    /* @POST("search")
     suspend fun getUserByDepartment(@Body department: String): Response<ResponseMemberList>

     @POST("search")
     suspend fun getUserByProfession(@Body profession: String): Response<ResponseMemberList>

     @POST("search")
     suspend fun getUserByBloodGroup(@Body bloodGroup: String): Response<ResponseMemberList>


     @POST("search")
     suspend fun getUserByDistrict(@Body district: String): Response<ResponseMemberList>*/

}