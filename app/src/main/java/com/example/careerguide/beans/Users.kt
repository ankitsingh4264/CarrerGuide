package com.example.careerguide.beans

data class Users (
        val name: String?=null,
        val phone : String? = null,
        val verified: Boolean =false,
        val imagepath:String? = null,
        val headline:String?=null,
        val asMentor:Boolean?=null,
        val fieldsMentoring:String?=null,
        var mentorAbout:String?=null,
        var id:String?=null

)
