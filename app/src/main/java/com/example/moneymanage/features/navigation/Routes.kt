package com.example.moneymanage.features.navigation

object Graph {
    const val RootGraph = "rootGraph"
    const val AuthGraph = "authGraph"
    const val MainScreenGraph = "mainScreenGraph"
    const val NotificationGraph = "notificationGraph"
    const val SettingGraph = "settingGraph"
    const val MoneyManageGraph = "moneyManageGraph"
    const val NotesGraph = "notesGraph"
    const val TaskGraph = "taskGraph"
}

sealed class AuthRouteScreen (val route: String) {

    object Login : AuthRouteScreen("login")

    object SignUp: AuthRouteScreen("signUp")

    object Forget: AuthRouteScreen("forget")
}

sealed class MainRouteScreen (var route: String) {

    object Home: MainRouteScreen("home")

    object Notes : MainRouteScreen("notes")

    object Task : MainRouteScreen("task")

    object Notification: MainRouteScreen("notification")

    object Setting: MainRouteScreen("setting")

    object Profile: MainRouteScreen("profile")
}

sealed class SettingRouteScreen(var route: String) {

    object SettingDetails: SettingRouteScreen("settingDetail")
}

sealed class NotificationRouteScreen (var route: String) {

    object NotificationDetail: NotificationRouteScreen("notificationDetail")

}

sealed class MoneyManageRouteScreen (var route: String) {

    object AddMoney: MoneyManageRouteScreen("addMoney")
    object UpdateMoney: MoneyManageRouteScreen("updateMoney")

}

sealed class NotesRouteScreen (var route: String) {

    object AddNote: MoneyManageRouteScreen("addNote")
    object UpdateNote: MoneyManageRouteScreen("updateNote")

}

sealed class TaskRouteScreen (var route: String) {

    object AddTask: MoneyManageRouteScreen("addTask")
    object UpdateTask: MoneyManageRouteScreen("updateTask")

}