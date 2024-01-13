package com.pioneers.jobgig

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pioneers.jobgig.dataobj.utils.CourseData

val coursesJsonArray = """
    {"courses":[{
          "title": "Mastering Crochet",
          "imageUri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseImage%2Fcrochet2.jpg?alt=media&token=11612c9f-9950-442d-b8de-ec05a941f8ee",
          "requirement": ["Basic knowledge of yarn and crochet hooks"],
          "forWho": ["Beginners", "Intermediate crocheters"],
          "duration": "2 hrs",
          "learners": 100,
          "instructorInfo": [
            {"uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/UserProfile%2Fsister_c.jpg?alt=media&token=b78865d8-cbe1-4c16-bb68-33455a78baff", "name": "Alice Crocheter", "description": "Experienced crochet artist"}
          ],
          "numOfRate": 50,
          "rating": 4.5,
          "comments": [
            {"rating":"4.1","name": "HappyLearner", "comment": "Awesome course!","uri":"null"}
          ],
          "description": "This course covers everything from basic stitches to advanced patterns.",
          "intro": "content://crochet/intro_video",
          "content": [
            {"title": "Introduction to Crochet", "uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseVideo%2Fcrochet_a.mp4?alt=media&token=c3db2c69-8925-40c9-9be4-df359edeb608", "duration": "1 hour"},
            {"title": "Mastering Single Crochet", "uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseVideo%2Fcrochet_b.mp4?alt=media&token=f105953b-c5cf-4dff-94ad-a8e9eeeb8050", "duration": "2 hours"}
          ],
          "whatLearn": ["Basic stitches", "Pattern reading", "Advanced techniques"]
      },
      {
          "title": "Crochet Beyond Basics",
          "imageUri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseImage%2Fcrochet1.jpg?alt=media&token=e7bfe84f-cfe6-432b-b921-68594735b1c2",
          "requirement": ["Proficient in basic crochet stitches"],
          "forWho": ["Intermediate crocheters", "Crochet hobbyists"],
          "duration": "5 hr",
          "learners": 120,
          "instructorInfo": [
            {"uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/UserProfile%2Fsister_b.jpg?alt=media&token=cd01bb5f-8af5-46e3-b748-e268d3c1d6fe", "name": "Amirah Kahn", "description": "Mastering intricate crochet patterns"}
          ],
          "numOfRate": 65,
          "rating": 4.8,
          "comments": [
            {"rating":"4.1","name": "HappyLearner", "comment": "Awesome course!","uri":"null"}
          ],
          "description": "Take your crochet skills to the next level with advanced stitches and intricate patterns.",
          "intro": "content://crochet/advanced_intro_video",
          "content": [
            {"title": "Advanced Stitch Combinations", "uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseVideo%2Fcrochet_a.mp4?alt=media&token=c3db2c69-8925-40c9-9be4-df359edeb608", "duration": "2.5 hours"},
            {"title": "Creating Intricate Crochet Designs", "uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseVideo%2Fcrochet_b.mp4?alt=media&token=f105953b-c5cf-4dff-94ad-a8e9eeeb8050", "duration": "4 hours"}
          ],
          "whatLearn": ["Advanced crochet stitches", "Intricate pattern creation", "Pattern interpretation"]
        
      },
      {
          "title": "Craftsman Carpentry",
          "imageUri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseImage%2Fcarpenter.jpg?alt=media&token=1438d131-742b-4093-b33a-4405794259c7",
          "requirement": ["No prior carpentry experience required"],
          "forWho": ["Beginners", "Woodworking enthusiasts"],
          "duration": "10 weeks",
          "learners": 75,
          "instructorInfo": [
            {"uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/UserProfile%2Fbrother_b.jpg?alt=media&token=9e10ee55-4332-4125-b5ad-019f9df961e0", "name": "Abdul Azeem", "description": "Master carpenter with years of experience"}
          ],
          "numOfRate": 40,
          "rating": 4.2,
          "comments": [
            {"rating":"4.1","name": "HappyLearner", "comment": "Awesome course!","uri":"null"}
          ],
          "description": "Embark on a journey into the world of carpentry and woodworking.",
          "intro": "content://carpenter/intro_video",
          "content": [
            {"title": "Introduction to Woodworking", "uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseVideo%2Fcarpenter.mp4?alt=media&token=e429b633-87bf-437d-93d1-f2f17cc85af9", "duration": "1.5 hours"},
            {"title": "Crafting Wooden Furniture", "uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseVideo%2Fcarpenter.mp4?alt=media&token=e429b633-87bf-437d-93d1-f2f17cc85af9", "duration": "3 hours"}
          ],
          "whatLearn": ["Basic woodworking techniques", "Pattern reading", "Finishing and detailing"]
       
      },
      {
          "title": "Fashion Sewing Techniques",
          "imageUri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseImage%2Ftailor2.png?alt=media&token=1158e975-0715-43cd-8c5b-e765b83ccea3",
          "requirement": ["Basic knowledge of sewing"],
          "forWho": ["Intermediate sewers", "Fashion design enthusiasts"],
          "duration": "10 weeks",
          "learners": 80,
          "instructorInfo": [
            {"uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/UserProfile%2Fsister_a.jpg?alt=media&token=1b2c50c5-b557-4dd8-9275-605bc750d3a8", "name": "Emily Stitcher", "description": "Expert tailor with a passion for fashion"}
          ],
          "numOfRate": 55,
          "rating": 4.6,
          "comments": [
            {"rating":"4.1","name": "HappyLearner", "comment": "Awesome course!","uri":"null"}
          ],
          "description": "Explore advanced sewing techniques and create stylish garments with precision.",
          "intro": "content://tailor/sewing_intro_video",
          "content": [
            {"title": "Advanced Pattern Making", "uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseVideo%2Fsewing.mp4?alt=media&token=e864d60d-9274-4acd-952a-49eed63712b3", "duration": "2.5 hours"},
            {"title": "Creating Custom-Fit Outfits", "uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseVideo%2Fsewing_b.mp4?alt=media&token=c42988a7-f40a-4312-90d0-32a214f71602", "duration": "3.5 hours"}
          ],
          "whatLearn": ["Advanced sewing techniques", "Pattern making", "Custom-fit garment creation"]
        
      },
      {
          "title": "Fashion Tailoring Mastery",
          "imageUri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseImage%2Ftailor3.jpg?alt=media&token=4938b331-b2d5-4fc8-868d-b0f7bda454cb",
          "requirement": ["No prior tailoring experience required"],
          "forWho": ["Beginners", "Fashion enthusiasts"],
          "duration": "12 weeks",
          "learners": 90,
          "instructorInfo": [
            {"uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/UserProfile%2Fsister_c.jpg?alt=media&token=b78865d8-cbe1-4c16-bb68-33455a78baff", "name": "Sara Seamstress", "description": "Renowned tailor with a passion for fashion"}
          ],
          "numOfRate": 45,
          "rating": 4.4,
          "comments": [
            {"rating":"4.1","name": "HappyLearner", "comment": "Awesome course!","uri":"null"}
          ],
          "description": "Discover the art of tailoring and create stylish garments from scratch.",
          "intro": "content://tailor/intro_video",
          "content": [
            {"title": "Basic Sewing Techniques", "uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseVideo%2Fsewing.mp4?alt=media&token=e864d60d-9274-4acd-952a-49eed63712b3", "duration": "2 hours"},
            {"title": "Constructing Stylish Garments", "uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseVideo%2Fsewing_b.mp4?alt=media&token=c42988a7-f40a-4312-90d0-32a214f71602", "duration": "4 hours"}
          ],
          "whatLearn": ["Essential sewing techniques", "Pattern reading", "Garment construction"]
        
      },
      {
          "title": "Brushstroke Brilliance",
          "imageUri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseImage%2Fpainter.jpg?alt=media&token=ec092ef7-af2d-43cb-92b1-d64aec5bec0e",
          "requirement": ["No prior painting experience necessary"],
          "forWho": ["Beginners", "Art enthusiasts"],
          "duration": "8 weeks",
          "learners": 80,
          "instructorInfo": [
            {"uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/UserProfile%2Fbrother_a.jpg?alt=media&token=8011b2a7-c3ce-444f-88d6-369d33a56fc3", "name": "Elena Artistica", "description": "Talented painter specializing in various styles"}
          ],
          "numOfRate": 50,
          "rating": 4.6,
          "comments": [
            {"rating":"4.1","name": "HappyLearner", "comment": "Awesome course!","uri":"null"}
          ],
          "description": "Dive into the captivating world of painting and unleash your artistic expression.",
          "intro": "content://painter/intro_video",
          "content": [
            {"title": "Fundamentals of Color Theory", "uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseVideo%2Fpaint.mp4?alt=media&token=6d72f250-b7e1-4ec6-a27b-0a903307f7ad", "duration": "1.5 hours"},
            {"title": "Exploring Abstract Styles", "uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseVideo%2Fpaint.mp4?alt=media&token=6d72f250-b7e1-4ec6-a27b-0a903307f7ad", "duration": "2.5 hours"}
          ],
          "whatLearn": ["Color theory", "Various painting styles", "Brush handling techniques"]
        
      },
     {
          "title": "Sole Artistry",
          "imageUri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseImage%2Fshoe3.jpg?alt=media&token=9b1937f1-2b15-4637-832c-2e7bd92dc1d5",
          "requirement": ["No prior shoemaking experience necessary"],
          "forWho": ["Beginners", "Footwear enthusiasts"],
          "duration": "10 weeks",
          "learners": 85,
          "instructorInfo": [
            {"uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/UserProfile%2Fbrother_a.jpg?alt=media&token=8011b2a7-c3ce-444f-88d6-369d33a56fc3", "name": "Martin Cobbler", "description": "Master shoemaker with a passion for craftsmanship"}
          ],
          "numOfRate": 48,
          "rating": 4.3,
          "comments": [
            {"rating":"4.1","name": "HappyLearner", "comment": "Awesome course!","uri":"null"}
          ],
          "description": "Step into the world of shoemaking and create footwear that stands out.",
          "intro": "content://shoemaker/intro_video",
          "content": [
            {"title": "Introduction to Shoe Anatomy", "uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseVideo%2Fshoe_a.mp4?alt=media&token=0607ab2f-b014-45c1-8a3a-2a59f55f6167", "duration": "2 hours"},
            {"title": "Crafting Custom-Fit Shoes", "uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseVideo%2Fshoe_b.mp4?alt=media&token=5295d4fc-c5a8-42a8-8c36-8156decf239f", "duration": "3 hours"}
          ],
          "whatLearn": ["Shoe anatomy", "Pattern making", "Custom-fit shoe crafting"]
        
      },
     {
          "title": "Custom Shoe Crafting Mastery",
          "imageUri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseImage%2Fshoe1.jpg?alt=media&token=e2211fe7-d03e-472c-8075-0c7df02a3066",
          "requirement": ["Basic knowledge of shoemaking"],
          "forWho": ["Intermediate shoemakers", "Footwear design enthusiasts"],
          "duration": "14 weeks",
          "learners": 100,
          "instructorInfo": [
            {"uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/UserProfile%2Fbrother_b.jpg?alt=media&token=9e10ee55-4332-4125-b5ad-019f9df961e0", "name": "Olivia Cobbler", "description": "Crafting custom shoes with precision"}
          ],
          "numOfRate": 60,
          "rating": 4.7,
          "comments": [
            {"rating":"4.1","name": "HappyLearner", "comment": "Awesome course!","uri":"null"}
          ],
          "description": "Master the art of crafting custom shoes with intricate design details.",
          "intro": "content://shoemaker/custom_shoe_intro_video",
          "content": [
            {"title": "Designing Custom Shoe Patterns", "uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseVideo%2Fshoe_b.mp4?alt=media&token=5295d4fc-c5a8-42a8-8c36-8156decf239f", "duration": "3 hours"},
            {"title": "Crafting Unique Sole Designs", "uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseVideo%2Fshoe_a.mp4?alt=media&token=0607ab2f-b014-45c1-8a3a-2a59f55f6167", "duration": "5 hours"}
          ],
          "whatLearn": ["Custom shoe pattern design", "Unique sole crafting techniques", "Innovative shoe designs"]
        
      },
      {
          "title": "Beauty Mastery: Salon Skills",
          "imageUri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseImage%2Fsalon1.png?alt=media&token=c0df13c8-d854-4f2b-82d9-088f1aef9341",
          "requirement": ["No prior salon experience necessary"],
          "forWho": ["Beauty enthusiasts", "Aspiring salon professionals"],
          "duration": "6 weeks",
          "learners": 70,
          "instructorInfo": [
            {"uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/UserProfile%2Fsister_b.jpg?alt=media&token=cd01bb5f-8af5-46e3-b748-e268d3c1d6fe", "name": "Sophia Stylist", "description": "Experienced beauty professional with a flair for creativity"}
          ],
          "numOfRate": 55,
          "rating": 4.8,
          "comments": [
            {"rating":"4.1","name": "HappyLearner", "comment": "Awesome course!","uri":"null"}
          ],
          "description": "Embark on a journey into the glamorous world of beauty and salon skills.",
          "intro": "content://salon/intro_video",
          "content": [
            {"title": "Hairstyling Techniques", "uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseVideo%2Fsalon.mp4?alt=media&token=9fafb541-d70c-488b-8051-439c730b714b", "duration": "1.5 hours"},
            {"title": "Skincare and Facial Treatments", "uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseVideo%2Fsalon.mp4?alt=media&token=9fafb541-d70c-488b-8051-439c730b714b", "duration": "2.5 hours"}
          ],
          "whatLearn": ["Haircutting", "Skincare techniques", "Nail care and artistry"]
        
      },
      {
          "title": "Capturing Moments: Photography Masterclass",
          "imageUri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseImage%2Fphoto1.jpg?alt=media&token=5810d6cf-00a6-42e6-85f8-d2b4083efdf4",
          "requirement": ["No prior photography experience required"],
          "forWho": ["Beginners", "Photography enthusiasts"],
          "duration": "8 weeks",
          "learners": 95,
          "instructorInfo": [
            {"uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/UserProfile%2Fbrother_a.jpg?alt=media&token=8011b2a7-c3ce-444f-88d6-369d33a56fc3", "name": "Alex Shutterbug", "description": "Renowned photographer with an eye for creativity"}
          ],
          "numOfRate": 60,
          "rating": 4.7,
          "comments": [
            {"rating":"4.1","name": "HappyLearner", "comment": "Awesome course!","uri":"null"}
          ],
          "description": "Embark on a visual journey into the world of photography and capture stunning moments.",
          "intro": "content://photography/intro_video",
          "content": [
            {"title": "Understanding Camera Settings", "uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseVideo%2Fphoto.mp4?alt=media&token=fb31f550-275f-4bb2-82e6-3fd0340e97aa", "duration": "2 hours"},
            {"title": "Post-Processing Skills", "uri": "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseVideo%2Fphoto.mp4?alt=media&token=fb31f550-275f-4bb2-82e6-3fd0340e97aa", "duration": "3 hours"}
          ],
          "whatLearn": ["Camera settings", "Visual storytelling", "Post-processing techniques"]
        
      }]}
""".trimIndent()

data class Courses(var courses:List<CourseData>)

fun GetCourseDatas(): Courses? {
    val gson = Gson()
    val type = object: TypeToken<List<CourseData>>(){}.type
    return try {
        println("About to decode")
        gson.fromJson(coursesJsonArray, Courses::class.java)
    }catch (e:Exception){
        e.printStackTrace()
        println("Exception Happened ${e.message}")
        Courses(emptyList())
    }
}
