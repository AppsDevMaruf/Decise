package com.example.decise.api

object APIs {
    const val BASE_URL = "http://170.64.137.92:7777/"

    //const val BASE_URL = "http://192.168.50.25:7777/"
    const val TEST_BASE_URL = "https://mocki.io/v1/"


    //CONTROLLER
    private const val SIGNUP_CONTROLLER = "/signup"
    private const val PROFILE_CONTROLLER = "/profile"
    private const val DESIGNATION_CONTROLLER = "/designation"
    private const val DEPARTMENT_CONTROLLER = "/department"
    private const val DECISION_CONTROLLER = "/decision"
    private const val SUBSCRIPTION_CONTROLLER = "/subscription"
    private const val DECISION_GROUP_CONTROLLER = "/decision-group"
    private const val PEOPLE_CONTROLLER = "/people"
    private const val VERSION = "/v3"

    //SIGNIN
    const val SIGNIN = "signin/"

    //SIGNUP
    const val VERIFY_EMAIL = "$SIGNUP_CONTROLLER/verify-email"
    const val SEND_EMAIL = "$SIGNUP_CONTROLLER/send-email"
    const val COMPLETE_SIGNUP = "$SIGNUP_CONTROLLER/complete-signup"

    // PROFILE
    const val PERSONAL = "$PROFILE_CONTROLLER/personal"
    const val BUSINESS = "$PROFILE_CONTROLLER/business/"
    const val UPDATE_PERSONAL = "$PROFILE_CONTROLLER/update-personal"
    const val UPDATE_BUSINESS = "$PROFILE_CONTROLLER/update-business"
    const val CHOOSE_SUBSCRIPTION_TYPE = "$PROFILE_CONTROLLER/choose-subscription-type"
    const val CHANGE_PASSWORD = "$PROFILE_CONTROLLER/change-password"
    const val FORGOT_PASSWORD = "$PROFILE_CONTROLLER/forgot-password"
    const val UPDATE_SUBSCRIPTION_TYPE = "$PROFILE_CONTROLLER/update-subscription-type"
    const val CHANGE_PROFILE_PIC = "$PROFILE_CONTROLLER/change-profile-pic"
    const val CHANGE_LOGO = "$PROFILE_CONTROLLER/change-logo/{companyId}"
    const val ASSIGN_ROLE = "$PROFILE_CONTROLLER/assign-role"

    //DEPARTMENT
    const val DEPARTMENT_LIST = "$DEPARTMENT_CONTROLLER/list/{companyId}"

    //DESIGNATION
    const val DESIGNATION_LIST = "$DESIGNATION_CONTROLLER/list/{companyId}"

    //DECISION_GROUP
    const val DECISION_GROUP_LIST = "$DECISION_GROUP_CONTROLLER/list/{companyId}"

    //PEOPLE
    const val MEMBER_LIST = "$PEOPLE_CONTROLLER/member/list"
    //DECISION
    const val DECISION_LIST = "$DECISION_CONTROLLER/get-home-dashboard-data"



}