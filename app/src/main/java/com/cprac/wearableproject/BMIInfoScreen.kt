package com.cprac.wearableproject

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.foundation.lazy.LazyColumn

@Composable
fun BMIInfoScreen(navController: NavHostController, bmi: Float) {
    val bmiCategory = when {
        bmi < 18.5 -> "Underweight"
        bmi in 18.5..24.9 -> "Normal weight"
        bmi in 25.0..29.9 -> "Overweight"
        else -> "Obesity"
    }

    val description = when (bmiCategory) {
        "Underweight" -> """
        You are considered underweight, which may indicate that you are not consuming enough calories or nutrients to maintain a healthy body weight. 
        Being underweight can lead to various health issues, including weakened immune function, decreased muscle strength, fatigue, and increased risk of osteoporosis.

        Tips to gain weight healthily:
        - Increase Caloric Intake: Aim to consume more calories than you burn. Focus on nutrient-dense foods such as avocados, nuts, seeds, whole grains, and lean proteins. Consider adding snacks like nut butter on whole-grain bread or yogurt with fruit between meals.
        - Eat Frequently: Instead of three large meals, try to eat 5-6 smaller meals throughout the day. This can help you consume more calories without feeling overly full.
        - Strength Training: Incorporate strength training exercises into your routine to build muscle mass. Muscle weighs more than fat and can help you gain weight healthily. Activities like weightlifting or resistance training are effective.
        - Smoothies and Shakes: High-calorie smoothies or protein shakes can help you increase your caloric intake without feeling too full. Try blending fruits with yogurt, nut butter, or protein powder for an added boost.
        - Avoid Empty Calories: While it’s tempting to consume sugary snacks to gain weight, focus on nutritious options that provide vitamins and minerals alongside calories.
        - Seek Professional Guidance: If you're struggling to gain weight, consult a healthcare provider or registered dietitian for personalized advice and meal planning.

        Useful Tools:
        - Nutrition Tracking Apps: Utilize apps like MyFitnessPal or Cronometer to track your calorie intake and ensure you meet your nutritional needs.
        - Consult a Dietitian: A registered dietitian can help create a meal plan tailored to your specific needs and preferences.
        - Weight Gain Supplements: If necessary, consider discussing with your doctor the option of weight gain supplements that can provide additional calories and nutrients.

        Remember, gradual weight gain is healthier than rapid increases, so be patient and consistent with your dietary changes. Monitor your progress regularly to ensure you are gaining weight healthily.
        """.trimIndent()

        "Normal weight" -> """
        You are in a healthy weight range, which suggests that you have a balanced intake of calories and nutrients. Maintaining this weight is crucial for overall well-being and can help lower the risk of chronic diseases such as heart disease, diabetes, and certain cancers.

        Tips to maintain your weight:
        - Balanced Diet: Continue eating a variety of foods, including plenty of fruits and vegetables, lean proteins, whole grains, and healthy fats. Aim to fill half your plate with vegetables and fruits at each meal.
        - Regular Physical Activity: Engage in at least 150 minutes of moderate aerobic activity or 75 minutes of vigorous activity each week. Incorporate muscle-strengthening activities on two or more days a week. Activities could include brisk walking, jogging, cycling, swimming, or group sports.
        - Stay Hydrated: Drink plenty of water throughout the day, aiming for at least 8 cups. Proper hydration supports overall health and can help manage appetite.
        - Mindful Eating: Practice mindful eating by paying attention to hunger and fullness cues. Avoid distractions while eating, which can lead to overeating.
        - Get Enough Sleep: Aim for 7-9 hours of quality sleep each night, as lack of sleep can disrupt hormones that regulate appetite and weight.
        - Regular Health Checkups: Schedule regular health checkups to monitor your overall health and address any potential issues early.

        Useful Tools:
        - Fitness Trackers: Consider using fitness trackers like Fitbit or Apple Watch to monitor your daily activity levels and set fitness goals.
        - Meal Planning Apps: Use apps like Yummly or PlateJoy to help with meal planning, ensuring you maintain a balanced diet.
        - Online Communities: Join online communities or forums for support and motivation. Engaging with others can help you stay accountable to your health goals.

        Maintaining a healthy weight is a lifelong commitment, and it's important to develop sustainable habits that promote overall health and wellness.
        """.trimIndent()

        "Overweight" -> """
        You are considered overweight, which may increase your risk for health issues such as heart disease, type 2 diabetes, certain cancers, and joint problems. 
        It’s essential to take proactive steps to reduce your weight for better health outcomes and enhance your overall quality of life.

        Tips to lose weight:
        - Healthy Eating: Focus on a balanced diet that emphasizes whole foods. Limit processed foods, added sugars, and saturated fats. Incorporate more fruits, vegetables, whole grains, lean proteins, and healthy fats into your meals.
        - Increase Physical Activity: Aim for at least 30 minutes of moderate physical activity most days of the week. Activities can include brisk walking, swimming, cycling, or group fitness classes. Find something you enjoy to make it easier to stick with it.
        - Set Realistic Goals: Aim to lose 1-2 pounds per week for sustainable weight loss. Avoid crash diets; instead, focus on making gradual changes that you can maintain long-term.
        - Behavioral Changes: Identify triggers for overeating or unhealthy eating habits and develop strategies to cope. This may include keeping a food diary, practicing stress management techniques, or finding healthy alternatives for your cravings.
        - Portion Control: Pay attention to portion sizes and try to eat smaller portions. Using smaller plates and bowls can help control serving sizes.
        - Stay Accountable: Consider joining a support group or seeking counseling to address emotional eating habits and maintain motivation.

        Useful Tools:
        - Weight Loss Apps: Use apps like Lose It! or Noom to track your food intake, exercise, and progress. These tools can help you stay accountable and motivated.
        - Online Resources: Websites like the CDC or WHO provide valuable information and tips for healthy weight loss and maintenance.
        - Fitness Challenges: Participate in local fitness challenges or events to boost your motivation and meet like-minded individuals.

        Remember, the journey to weight loss can be challenging, but every small step counts. Celebrate your achievements, no matter how small, and stay focused on your health goals.
        """.trimIndent()

        "Obesity" -> """
        You are classified as obese, which significantly increases your risk for serious health conditions, including heart disease, diabetes, high blood pressure, and certain types of cancer. 
        It’s crucial to take action towards a healthier lifestyle, and seeking support is a vital step in this process.

        Tips to manage obesity:
        - Consult Healthcare Professionals: Seek advice from doctors, dietitians, or nutritionists who can guide you on safe weight loss strategies tailored to your individual needs and health conditions.
        - Structured Weight Loss Programs: Consider enrolling in medically supervised weight loss programs that include dietary changes, physical activity, and behavior modification. These programs often provide support and accountability.
        - Stay Active: Incorporate regular physical activities that you enjoy into your routine. Aim for at least 150 minutes of moderate-intensity exercise weekly, combining aerobic activities with strength training exercises.
        - Behavior Modification: Work on identifying triggers for overeating and develop strategies to cope with them. Techniques like cognitive-behavioral therapy (CBT) can be effective in addressing emotional eating habits.
        - Mindful Eating Practices: Practice mindful eating by savoring each bite, eating slowly, and being aware of your body’s hunger and fullness cues. This can help reduce overeating.
        - Build a Support System: Engage with friends, family, or support groups who can provide encouragement and accountability on your journey to better health.

        Useful Tools:
        - Medical Weight Loss Clinics: Look for clinics that offer personalized weight loss plans, including medical supervision and support.
        - Community Support: Joining local or online support groups can provide motivation and accountability. Connecting with others on similar journeys can be incredibly uplifting.
        - Healthy Cooking Classes: Consider taking healthy cooking classes to learn how to prepare nutritious meals that support your weight loss goals.

        Remember, achieving a healthy weight is a journey that requires patience, perseverance, and commitment. Focus on making sustainable lifestyle changes that enhance your overall well-being.
        """.trimIndent()

        else -> "BMI information not available."
    }
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Text(
                    text = "BMI Category: $bmiCategory",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Text(
                    text = description,
                    fontSize = 16.sp
                )
            }
        }
    }
}
