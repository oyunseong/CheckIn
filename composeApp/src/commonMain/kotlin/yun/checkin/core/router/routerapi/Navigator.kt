package yun.checkin.core.router.routerapi

import yun.checkin.core.router.routerapi.model.Route

interface Navigator {

    suspend fun navigate(route: Route)

    suspend fun navigateBack()
}
