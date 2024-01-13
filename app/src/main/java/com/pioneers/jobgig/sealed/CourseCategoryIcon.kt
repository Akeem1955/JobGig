package com.pioneers.jobgig.sealed

import androidx.compose.material.icons.Icons
import com.pioneers.jobgig.R

class CourseCategoryIcon(type:String) {
    var iconRes:Int = 0
        private set
    var keyword =""
        private set
    init {
        when(type){
            "Plumbing" ->{
                iconRes = R.drawable.plumber
                keyword="plumber"
            }
            "Catering_and_Culinary_Arts"->{
                iconRes = R.drawable.catering
                keyword="catering"
            }
            "Carpentry"->{
                iconRes =R.drawable.carpenter
                keyword="Carpentry"
            }
            "Salon_and_Beauty_Services"->{
                iconRes =R.drawable.makeup
                keyword="salon"
            }
            "Electrical_Installation"->{
                iconRes =R.drawable.electrician
                keyword="electric"
            }
            "Automotive_Repair"->{
                iconRes =R.drawable.automotive
                keyword="repair"
            }
            "Masonry_and_Bricklaying"->{
                iconRes =R.drawable.layer
                keyword="brick"
            }
            "Welding_and_Metal_Fabrication"->{
                iconRes =R.drawable.weldder
                keyword="weld"
            }
            "Tailoring_and_Fashion_Design"->{
                iconRes =R.drawable.tailor
                keyword="Fashion"
            }
            "Landscaping_and_Gardening"->{
                iconRes =R.drawable.landscape
                keyword="garden"

            }
            "Air_Conditioning_Maintenance"->{
                iconRes =R.drawable.acrepair
                keyword="ac"
            }
            "Painting_and_Decorating"->{
                iconRes =R.drawable.painter
                keyword=""
            }
            "Jewelry_Making_and_Metalworking"->{
                iconRes =R.drawable.jewelry
                keyword=""
            }
            "Appliance_Repair"->{
                iconRes =R.drawable.mechanic
            }
            "Upholstery_and_Furniture_Restoration"->{
                iconRes =R.drawable.restore
                keyword=""
            }
            "Knitting_and_Textile_Crafts"->{
                iconRes =R.drawable.kniting
                keyword=""
            }
            "Photography"->{
                iconRes =R.drawable.camera
                keyword=""
            }
            "Fabric_Dyeing_and_Tie-Dye_Techniques"->{
                iconRes =R.drawable.tye_dye
                keyword=""

            }
            "Aluminium_Fabrication_and_Fitting"->{
                iconRes =R.drawable.aluminium
                keyword=""
            }
            else ->{iconRes = R.drawable.noto_hammer_and_wrench}
        }
    }



}


