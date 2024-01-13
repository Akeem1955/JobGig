package com.pioneers.jobgig.sealed

sealed interface CourseSection{

    object Home:CourseSection
    object TopCategory:CourseSection
    object Search:CourseSection
    object Popular:CourseSection
    object Category:CourseSection
}