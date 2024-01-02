package com.pioneers.jobgig.sealed

sealed interface CourseInfoDesign{
    object  BulletDesign:CourseInfoDesign
    object  CheckDesign:CourseInfoDesign
    object  InstructorDesign:CourseInfoDesign
}