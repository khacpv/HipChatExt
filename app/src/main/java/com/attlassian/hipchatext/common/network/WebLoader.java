package com.attlassian.hipchatext.common.network;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by khacpham on 8/15/15.<br/>
 * Load a webpage via WebView on worker thread and return WebLoaderResult on UIThread.
 */
public class WebLoader {

    /**
     * a webView to load
     * */
    private WebView loadTitleWebView;

    public WebLoader(Context context){
        this.loadTitleWebView = new WebView(context);
    }

    /** onComplete should be called first. Use publishSubject to subcribe load webView:<br/>
     * <img src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/S.PublishSubject.png" width=500 height=300></img>
     * @see <a href="https://groups.google.com/forum/#!topic/rxjava/faiiFd8FgEg">Subrice on PusblishSubject</a>
     * @param link url to load
     * @return Observable<WebLoaderResult> an observable of onLoadComplete or onError
     * */
    public Observable<WebLoaderResult> startLoad(String link){
        final PublishSubject<WebLoaderResult> publishSubject = PublishSubject.create();

        loadTitleWebView.loadUrl(link);
        loadTitleWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                publishSubject.onCompleted();
                publishSubject.onError(new Throwable("can not load title now"));
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                WebLoaderResult result = new WebLoaderResult(view,url);
                publishSubject.onNext(result);
                publishSubject.onCompleted();
            }

        });

        return publishSubject;
    }

    /**
     * The class for handle result from webView loaded
     * */
    public static class WebLoaderResult{
        public WebView mWebView;
        public String url;

        public WebLoaderResult(WebView webView, String url){
            this.mWebView = webView;
            this.url = url;
        }
    }
}
