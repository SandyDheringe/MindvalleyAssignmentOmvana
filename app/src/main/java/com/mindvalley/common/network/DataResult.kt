package com.mindvalley.common.network


/*class DataResult<T> private constructor*/
data class DataResult<T>(var result: T? = null, var error: Throwable? = null, var networkStatus: NetworkStatus, var statusCode: Int = 0)
{

    companion object
    {
        fun <T> setError(error: Throwable?, httpStatusCode: Int) =
            DataResult<T>(error = error,
                networkStatus = NetworkStatus.ERROR, statusCode = httpStatusCode)
        fun <T> setError(error: Throwable) = DataResult<T>(error = error,
            networkStatus = NetworkStatus.ERROR)
        fun <T> setResult(result: T?) = DataResult<T>(result = result,
            networkStatus = NetworkStatus.SUCCESS)
    }

}
