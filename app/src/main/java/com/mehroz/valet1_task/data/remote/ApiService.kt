package com.mehroz.valet1_task.data.remote

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.mehroz.valet1_task.core.Resource
import com.mehroz.valet1_task.data.remote.ApiEndPoint.FETCH_DEVICES_URL
import com.mehroz.valet1_task.data.remote.response.DevicesResponseItem
import retrofit2.http.GET


interface ApiService {
    @GET(FETCH_DEVICES_URL)
    @MockResponse(body = """[
        {
            "Id": "1",
            "Type": "Sensor",
            "Status": "Available",
            "Price": 30999,
            "Currency": "PKR",
            "isFavorite": false,
            "imageUrl": "https://i0.wp.com/newmobiles.com.pk/wp-content/uploads/2021/06/Vivo-Y20s-new-mobiles-400x400.jpg",
            "Title": "Vivo",
            "Description": "Vivo Y20s Price in Pakistan 2022 is Rs. 30,999/-"
        },
        {
            "Id": "2",
            "Type": "Sensor",
            "Status": "Not Available",
            "Price": 24999,
            "Currency": "PKR",
            "isFavorite": false,
            "imageUrl": "https://i0.wp.com/newmobiles.com.pk/wp-content/uploads/2020/08/samsung-galaxy-m11-new-mobiles-400x400.jpg",
            "Title": "Samsung Galaxy M11",
            "Description": "Samsung Galaxy M11 Price in Pakistan is Rs. 24,999. Samsung M11 Specification includes 3GB RAM & 32GB internal storage. The New Galaxy M11 phone comes in three good looking colors of Black, Metallic Blue & Violet. The smartphone release date in Pakistan is 1st September 2020."
        },
        {
            "Id": "3",
            "Type": "Sensor",
            "Status": "Available",
            "Price": 325,
            "Currency": "USD",
            "isFavorite": false,
            "imageUrl": "https://www.gizmochina.com/wp-content/uploads/2019/12/OPPO-F15-400x400.png",
            "Title": "Oppo F15",
            "Description": "Oppo F15 is officially released in January 2020 and the device is built with Glass front (Gorilla Glass 5), plastic back, and plastic frame."
        },
        {
            "Id": "4",
            "Type": "Sensor",
            "Status": "Available",
            "Price": 500,
            "Currency": "USD",
            "isFavorite": false,
            "imageUrl": "https://mobiles.dailypakistan.com.pk/wp-content/uploads/2022/01/te-400x400.jpg",
            "Title": "Tecno",
            "Description": "Tecno  is going to launches its all-new Spark Go 2022 model"
        },
        {
            "Id": "5",
            "Type": "Sensor",
            "Status": "Not Available",
            "Price": 1000,
            "Currency": "USD",
            "isFavorite": false,
            "imageUrl": "https://www.reaganwireless.com/wp-content/uploads/2019/11/apple-iphone-x-400x400.jpg",
            "Title": "Apple iPhone XS",
            "Description": "Apple iPhone XS 256 GB features an all-screen design with a 5.8-inch Super Retina HD display with HDR and True Tone. Designed with the most durable glass ever in a smartphone and a surgical grade stainless steel band. Charges wirelessly."
        },
        {
            "Id": "6",
            "Type": "Sensor",
            "Status": "Available",
            "Price": 700,
            "Currency": "USD",
            "isFavorite": false,
            "imageUrl": "https://www.gizmochina.com/wp-content/uploads/2019/01/Sharp-Android-One-S5-400x400.jpg",
            "Title": "Android One",
            "Description": "Sharp Android One S5 is the latest smartphone released by Sharp in the month of January 2019. The phone has a dimension of 71 x 148 x 8.1 mm and it weighs 149 grams with battery."
        },
        {
            "Id": "7",
            "Type": "Sensor",
            "Status": "Not Available",
            "Price": 800,
            "Currency": "USD",
            "isFavorite": false,
            "imageUrl": "https://cdn.vox-cdn.com/thumbor/xzCFAl8Q0yVRntUZ_uFdSTEbeLA=/400x0/filters:no_upscale()/cdn.vox-cdn.com/uploads/chorus_asset/file/22214200/ajohnson200104_4354_0001sq.jpg",
            "Title": "One Plus",
            "Description": "The OnePlus Nord N10 5G does something unique: bring 5G to a ${'$'}299.99 phone without cutting too many corners. That’s a full ${'$'}200 lower than other budget-oriented competitors like the Google Pixel 4A 5G and Samsung Galaxy A51 5G"
        }
    ]""")
    @Mock
    suspend fun getDevices(): Resource<MutableList<DevicesResponseItem>>
}