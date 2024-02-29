package com.example.xmlandroiduniversity.data.retrofit

import com.google.gson.annotations.SerializedName

data class Quote(
    @SerializedName("exchange")
    val exchange: String,
    @SerializedName("shortname")
    val shortname: String,
    @SerializedName("quoteType")
    val quoteType: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("index")
    val index: String,
    @SerializedName("score")
    val score: Double,
    @SerializedName("typeDisp")
    val typeDisp: String,
    @SerializedName("longname")
    val longname: String,
    @SerializedName("exchDisp")
    val exchDisp: String,
    @SerializedName("sector")
    val sector: String?,
    @SerializedName("sectorDisp")
    val sectorDisp: String?,
    @SerializedName("industry")
    val industry: String?,
    @SerializedName("industryDisp")
    val industryDisp: String?,
    @SerializedName("dispSecIndFlag")
    val dispSecIndFlag: Boolean?,
    @SerializedName("isYahooFinance")
    val isYahooFinance: Boolean?,
)

data class News(
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("publisher")
    val publisher: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("providerPublishTime")
    val providerPublishTime: Long,
    @SerializedName("type")
    val type: String,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail?,
    @SerializedName("relatedTickers")
    val relatedTickers: List<String>?,
)

data class Thumbnail(
    @SerializedName("resolutions")
    val resolutions: List<Resolution>,
)

data class Resolution(
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
)

data class YahooFinanceResponse(
    @SerializedName("explains")
    val explains: List<Any>?,
    @SerializedName("count")
    val count: Int?,
    @SerializedName("quotes")
    val quotes: List<Quote>?,
    @SerializedName("news")
    val news: List<News>?,
    @SerializedName("nav")
    val nav: List<Any>?,
    @SerializedName("lists")
    val lists: List<Any>?,
    @SerializedName("researchReports")
    val researchReports: List<Any>?,
    @SerializedName("screenerFieldResults")
    val screenerFieldResults: List<Any>?,
    @SerializedName("totalTime")
    val totalTime: Int?,
    @SerializedName("timeTakenForQuotes")
    val timeTakenForQuotes: Int?,
    @SerializedName("timeTakenForNews")
    val timeTakenForNews: Int?,
    @SerializedName("timeTakenForAlgowatchlist")
    val timeTakenForAlgowatchlist: Int?,
    @SerializedName("timeTakenForPredefinedScreener")
    val timeTakenForPredefinedScreener: Int?,
    @SerializedName("timeTakenForCrunchbase")
    val timeTakenForCrunchbase: Int?,
    @SerializedName("timeTakenForNav")
    val timeTakenForNav: Int?,
    @SerializedName("timeTakenForResearchReports")
    val timeTakenForResearchReports: Int?,
    @SerializedName("timeTakenForScreenerField")
    val timeTakenForScreenerField: Int?,
    @SerializedName("timeTakenForCulturalAssets")
    val timeTakenForCulturalAssets: Int?,
)

/*
   ddd
 */

data class ChartData(
    @SerializedName("chart")
    val chart: Chart
)

data class Chart(
    @SerializedName("result")
    val result: List<ChartResult>,
    @SerializedName("error")
    val error: Any?
)

data class ChartResult(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("timestamp")
    val timestamp: List<Long>,
    @SerializedName("indicators")
    val indicators: Indicators
)

data class Meta(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("exchangeName")
    val exchangeName: String,
    @SerializedName("instrumentType")
    val instrumentType: String,
    @SerializedName("firstTradeDate")
    val firstTradeDate: Long,
    @SerializedName("regularMarketTime")
    val regularMarketTime: Long,
    @SerializedName("hasPrePostMarketData")
    val hasPrePostMarketData: Boolean,
    @SerializedName("gmtoffset")
    val gmtoffset: Int,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("exchangeTimezoneName")
    val exchangeTimezoneName: String,
    @SerializedName("regularMarketPrice")
    val regularMarketPrice: Double,
    @SerializedName("chartPreviousClose")
    val chartPreviousClose: Double,
    @SerializedName("previousClose")
    val previousClose: Double,
    @SerializedName("scale")
    val scale: Int,
    @SerializedName("priceHint")
    val priceHint: Int,
    @SerializedName("currentTradingPeriod")
    val currentTradingPeriod: CurrentTradingPeriod,
    @SerializedName("tradingPeriods")
    val tradingPeriods: List<List<TradingPeriod>>,
    @SerializedName("dataGranularity")
    val dataGranularity: String,
    @SerializedName("range")
    val range: String,
    @SerializedName("validRanges")
    val validRanges: List<String>
)

data class CurrentTradingPeriod(
    @SerializedName("pre")
    val pre: TradingPeriod,
    @SerializedName("regular")
    val regular: TradingPeriod,
    @SerializedName("post")
    val post: TradingPeriod
)

data class TradingPeriod(
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("start")
    val start: Long,
    @SerializedName("end")
    val end: Long,
    @SerializedName("gmtoffset")
    val gmtoffset: Int
)

data class Indicators(
    @SerializedName("quote")
    val quote: List<ChartQuote>
)

data class ChartQuote(
    @SerializedName("low")
    val low: List<Double>,
    @SerializedName("volume")
    val volume: List<Long>,
    @SerializedName("high")
    val high: List<Double>,
    @SerializedName("close")
    val close: List<Double>,
    @SerializedName("open")
    val open: List<Double>
)