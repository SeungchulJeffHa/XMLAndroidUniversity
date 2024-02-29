package com.example.xmlandroiduniversity.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xmlandroiduniversity.api.YFRetrofitClient
import com.example.xmlandroiduniversity.api.yfinance.YFInterface
import com.example.xmlandroiduniversity.data.retrofit.YahooFinanceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitViewModel: ViewModel() {

    private val service: YFInterface = YFRetrofitClient.create()
    val yfResponseLiveData: MutableLiveData<YahooFinanceResponse?> = MutableLiveData()

    fun fetchData(query: String) {
        service.search(query).enqueue(object : Callback<YahooFinanceResponse> {
            override fun onResponse(call: Call<YahooFinanceResponse>, response: Response<YahooFinanceResponse>) {
                if (response.isSuccessful) {
                    // 네트워크 요청이 성공했을 때 처리할 작업을 여기에 추가합니다.
                    val data = response.body()
                    // 예를 들어, 받은 데이터를 가지고 UI를 업데이트하거나 필요한 처리를 수행할 수 있습니다.
                    yfResponseLiveData.value = data

                } else {
                    // 요청은 성공하지 않았지만 응답을 수신한 경우
                    // 응답 코드를 기반으로 오류 처리를 수행할 수 있습니다.
                }
            }

            override fun onFailure(call: Call<YahooFinanceResponse>, t: Throwable) {
                // 네트워크 요청이 실패한 경우 처리할 작업을 여기에 추가합니다.
                // 예를 들어, 오류 메시지를 표시하거나 재시도하는 등의 작업을 수행할 수 있습니다.
            }
        })
    }

}
