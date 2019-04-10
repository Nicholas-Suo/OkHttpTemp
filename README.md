# OkHttpTemp
okhttp的使用相关
https://square.github.io/okhttp/
https://github.com/square/okhttp.git
https://github.com/square/okhttp/wiki/Interceptors


使用okhttp 下载图片。
使用okhttp 断点下载图片，到指定目录和imageView
使用okhttp 同时断点下载多个图片，到指定目录和ImageView. + 进度条

OkHttp解析

OkHttpClient  newCall()--> new RealCall(client,request) 封装client和request, 然后调用RealCall equeue 传递Callback 

RealCall (AsyncCall extends NamedRunable)  -> enqueue(Callback) -->然后用AsyncCall封装Callback 传递给Dispather equeue，

Dispather  equeue   ThreadPoolExecutor
readyAsyncQueue添加一个AysncCall,包含readyAsyncQueue 和runningAsyncQueue分别保存准备的AsyncCall和正在运行的AsyncCall
然后遍历ready队列，有等待的，取出队列，放到runing队列里，然后遍历executable队列，
回调AsyncCall 的executeOn（ThreadPoolExecutor）方法，线程池执行execute(this)执行AsyncCall,回调AsyncCall的execute()方法

获取网络的Reponse， 然后回调AsyncCall 封装的callback，回调到HttpClient的回调方法
