# health-logger-android
Health Logger Android App. Personal Project


Health Logger Android App

Product Requirement Document

**Developers**

Eric Santana -- Lead Developer &amp; Designer - @esantana1

Keith Luxeus -- Developer - @keithlxs

**Table Of Contents**

**[1. Introduction](#_itpyzozc3nih) 1**

**[2. App Features](#_kdxuldonrhgx) 1**

[2.1 Introduction](#_rea70jyq46tv) 1

[2.2 Stories](#_mbatkcuc05ac) 1

**[3. App Technical Requirements](#_dupofeq1me8j) 4**

[3.1 Introduction](#_7o2z1dljs9t6) 4

[3.2 Android API/Features](#_z3mq73prnx2u) 4

**[4. Technical Best Practices Resources](#_c3nsdfvojxfr) 5**

[4.1 Introduction](#_7vfvwi9v5e0t) 5

[4.2 Resources](#_twtmvhi6d8c2) 5

# 1. Introduction

The purpose of this application project is to develop an Android Application in the best practice approach in the Android development world and to become experienced with the Android APIs and system. In this particular app, we are building an Health logger that allows users to record their weight, what meals/snack the user ate, exercises done , and see their weight loss/gained progress based on the data the user enters.

# 2. App Features

## 2.1 Introduction

In this section, it will provide the list of Features that the application should have in the end Product. These features are written in an Agile style ([https://www.visual-paradigm.com/guide/agile-software-development/what-is-user-story/](https://www.visual-paradigm.com/guide/agile-software-development/what-is-user-story/))

Note: These stories were inspired by the app **My Fitness Pal**

( [https://play.google.com/store/apps/details?id=com.myfitnesspal.android&amp;hl=en\_US](https://play.google.com/store/apps/details?id=com.myfitnesspal.android&amp;hl=en_US))

## 2.2 Stories

| **Story Number** | **Short Description** | **Description** |
| --- | --- | --- |
| 1 | Record current weight | As a User, I would like to record my weight once a day so that I can later on see my progress over time. Also, if I made a mistake recording in one day, I can update with the correct weight as long as it is the current date (today)
 \*\* EXTRA: As I record my current weight , I can save a picture of myself with the progress |
| 2 | Set Daily Calories Goal/limit | As a User, I would like to set my daily calories limit to help me reach my weight Goal. With the daily calories set, I can see how many calories I can eat until I reach my limit, tracked by my daily meal/snack log
 |
| 3 | Set Weight Goal | As a User, I would like to set my weight goal, so I can compare with my current weight, see my progress, and see how closer I am of achieving my weight goal |
| 4 | Easily See Weight Loss/Gained so far. | As a User, I would like to easily see my weight loss/gained so far, based on my first weight entry and my most recent weight entry |
| 5 | Enter a Daily Text Note | As a User, I would like to be able to record a daily text note of two types per day,, Exercise and Food, to document what I am doing in particular for that day.

 1 day can have 2 notes , one of type Food and the other of Exercise
 |
| 6 | Enter/edit an Exercise Entry | As a user, I would like to enter/edit an exercise entry of one of the following types:

- Cardiovascular
- Strength
- General

 In the day entry, I can enter the following:
- Calories Burned
- How many minutes the exercise took
- Exercise Description
 |
| 7 | Save an pre-configured Exercise | As a user, I would like to save a pre-configured exercise entry, based on an previous Exercise entry(based on the last 10 different entries), so that I don&#39;t have to re-enter the same exercise |
| 8 | Enter/edit a Food Entry | As a User, I would like to enter/edit an Food Entry of one of the following meal types:
- Breakfast
- Lunch
- Dinner
- Snack

 In a food entry, I can enter the following:
- Description (mandatory)
- Calories Eaten
- Carbohydrates
- Fat
- Protein
 |
| 9 | Save a pre-configured Food Entry | As a user, I would like to save a pre-configured food entry, based on an previous Food entry(based on the last 10 different entries), so that I don&#39;t have to re-enter the same food |
| 10 | Calories Remaining Calculator | As a User, I would like to see how many calories are left to eat for the current day[Variable R], based on the daily calories goal set (Story #2)[variable G] , food logged with calories entered [variable F] and exercise logged [variable E] . For a day&#39;s calories remaining shall be calculated in the following way:

 G - F + E = R |
| 11 | Weight Progress Graph | As a User, I would like to see my weight progress from the following options for periods since current time that I check:

- 1 month (30 day)
- 3 months
- 6 months
- 1 Year (365 Days)

 Y axis= Weight in Pounds X axis = Time (in dates) |
| 12 | Reset User Data | As a User, I would like to have the option to delete all my data stored in the application, so I can start new plans and goals from the beginning |
| 13 | First Time Use Set Up | As a user, when I open the app for the first time, I would like to be greeted to configure the following when I begin:

- Set Weight Goal
- Set Daily Calories Goal
- Set Current Weight
- Your name
 |
| 14 | Export Weight Progress Data | As a User, I would like to export my weight progress data in to a CSV (see [here](https://en.wikipedia.org/wiki/Comma-separated_values)) format. |

# 3. App Technical Requirements

## 3.1 Introduction

In this section, the technologies and API&#39;s will be listed here that are needed for the implementation of the app features listed in Section 2.2.

## 3.2 Android API/Features

- Settings (for storing main settings)
  - [https://developer.android.com/guide/topics/ui/settings#java](https://developer.android.com/guide/topics/ui/settings#java)
- Room Persistence Library (for storing food/excercise entries)
  - [https://developer.android.com/topic/libraries/architecture/room](https://developer.android.com/topic/libraries/architecture/room)
  - (Very Concise doc)[https://guides.codepath.com/android/Room-Guide](https://guides.codepath.com/android/Room-Guide)
- ViewModel
  - [https://developer.android.com/topic/libraries/architecture/viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- Intents (Switching Activites/Page in the app)
  - [https://developer.android.com/guide/components/intents-filters](https://developer.android.com/guide/components/intents-filters)
- LiveData
  - [https://developer.android.com/topic/libraries/architecture/livedata](https://developer.android.com/topic/libraries/architecture/livedata)
- Activities
  - [https://developer.android.com/guide/components/activities/intro-activities](https://developer.android.com/guide/components/activities/intro-activities)
- Recyclerview
  - [https://www.youtube.com/watch?v=bOYmnzpLyJQ](https://www.youtube.com/watch?v=bOYmnzpLyJQ)
- \&lt;File Creation for exporting data...\&gt;

# 4. Technical Best Practices Resources

## 4.1 Introduction

This section provides a list of best practices approaches in an Android app development and will be used as a guidance on implementing this Application.

## 4.2 Resources

- How to Start Your Android Development Journey:
  - [https://www.thedroidsonroids.com/blog/how-to-start-your-android-development-journey-5-basic-steps/](https://www.thedroidsonroids.com/blog/how-to-start-your-android-development-journey-5-basic-steps/)
- Android Design + Material Design:
  - [https://developer.android.com/design](https://developer.android.com/design)
  - [https://developer.android.com/guide/topics/ui/look-and-feel](https://developer.android.com/guide/topics/ui/look-and-feel)
  - [https://www.uxpin.com/studio/blog/guide-mobile-app-design-best-practices-2018-beyond/](https://www.uxpin.com/studio/blog/guide-mobile-app-design-best-practices-2018-beyond/)
- App Fundamentals:
  - [https://developer.android.com/guide/components/fundamentals](https://developer.android.com/guide/components/fundamentals)
- Data and file storage overview
  - [https://developer.android.com/training/data-storage](https://developer.android.com/training/data-storage)
- Possible UI Designs:
  - Menu on bottom: [https://www.youtube.com/watch?v=jpaHMcQDaDg](https://www.youtube.com/watch?v=jpaHMcQDaDg)
  -