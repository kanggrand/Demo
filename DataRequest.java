package com.a1000phone.android31myapp.utils;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/28.
 */
public class DataRequest {

    //get请求，url不需要追加，适用于首页数据
    public static void getjsonData(Activity activity, String path, final CallBackData callBackData){
        RequestQueue requestQueue= Volley.newRequestQueue(activity);

        StringRequest stringRequest=new StringRequest(path, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                callBackData.success(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callBackData.fail();
            }
        });
        requestQueue.add(stringRequest);
    }

    //post请求,访问头条数据的接口
    public static void postHeadlineData(Activity activity, String path, final int rows, final int page, final CallBackData callBackData){
        RequestQueue requestQueue=Volley.newRequestQueue(activity);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, path, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                callBackData.success(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callBackData.fail();
            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String> map=new HashMap<>();
                map.put("rows",rows+"");
                map.put("page",page+"");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    //获取右边所示四个页面的数据：资讯 type：52 百科 type：16 数据 type：54 经营 type：53
    public static void commonDate(Activity activity, String path, final int rows, final int page, final int type, final CallBackData callBackData){
        RequestQueue requestQueue=Volley.newRequestQueue(activity);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, path, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                callBackData.success(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callBackData.fail();
            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String> map=new HashMap<>();
                map.put("rows",rows+"");
                map.put("page",page+"");
                map.put("type",type+"");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    //获取搜索页面数据
    public static void searchData(Activity activity, String path, final int rows, final int page, final String search, final CallBackData callBackData){
        RequestQueue requestQueue=Volley.newRequestQueue(activity);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, path, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                callBackData.success(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callBackData.fail();
            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String> map=new HashMap<>();
                map.put("rows",rows+"");
                map.put("page",page+"");
                map.put("search",search);
                return map;
            }
        };
      requestQueue.add(stringRequest);
    }




    //接口定义，通过此接口，用户能够直接在需要访问网络的地方获取结果。
    public interface CallBackData{
        void success(String result);
        void fail();
    }

}
